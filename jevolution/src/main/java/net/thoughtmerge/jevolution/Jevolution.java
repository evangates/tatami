/*
 * ------------------------------------------------------------------
 *             (C) Copyright 2014, EVAN GATES
 *                     ALL RIGHTS RESERVED
 *             THIS NOTICE DOES NOT IMPLY PUBLICATION
 * ------------------------------------------------------------------
 */

package net.thoughtmerge.jevolution;

import java.awt.Color;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author evan.gates
 */
public class Jevolution {

  private final static Logger logger = LoggerFactory.getLogger(Jevolution.class);

  public final static int DEFAULT_WIDTH = 1024;
  public final static int DEFAULT_HEIGHT = (int)((double)DEFAULT_WIDTH * 9.0 / 16.0);
  public final static String DEFAULT_TITLE = "Jevolution";
  public final static Color QUAD_COLOR = Color.WHITE;
  public final static int TARGET_FPS = 60;

  private long lastTime;
  private int fps;
  private long lastFPS;

  public static void main(String[] args) {
    Jevolution game = null;

    try {
      game = new Jevolution();

      game.create();
      game.run();
    }
    catch(LWJGLException ex) {
      logger.error("Exception: {}", ex.getMessage(), ex);
    }
    finally {
      if (game != null) {
        game.cleanup();
      }
    }

  }

  private Jevolution() {
    // no constructor
  }

  private void create() throws LWJGLException {
    Display.setDisplayMode(new DisplayMode(DEFAULT_WIDTH, DEFAULT_HEIGHT));
    Display.setFullscreen(false);
    Display.setTitle(DEFAULT_TITLE);
    Display.create();

    Keyboard.create();

    Mouse.setGrabbed(false);
    Mouse.create();

    initGL();
    resizeGL();

    getDelta();
    lastFPS = Timing.getTimeInMillis();
  }

  private void run() {
    while (!Display.isCloseRequested()) {

      handleInput();

      render(getDelta());

      Display.update();
      Display.sync(TARGET_FPS);
    }
  }

  private void cleanup() {
    logger.info("Cleaning up...");

    logger.info("\tDestroying mouse...");
    Mouse.destroy();

    logger.info("\tDestroying keyboard...");
    Keyboard.destroy();

    logger.info("\tDestroying display...");
    Display.destroy();

    logger.info("Cleaning complete");
  }

  private void handleInput() {
    while(Mouse.next()) {
      handleMouseEvent();
    }

    while(Keyboard.next()) {
      handleKeyboardEvent();
    }
  }

  private void handleMouseEvent() {
    int button = Mouse.getEventButton();

    int x = Mouse.getX();
    int y = Mouse.getY();

    logger.info("Mouse button {} @ ({}, {})", button, x, y);
  }

  private void handleKeyboardEvent() {
    String key = Keyboard.getKeyName(Keyboard.getEventKey());
    boolean wasPressed = Keyboard.getEventKeyState();

    logger.info("{} was {}", key, wasPressed ? "pressed" : "released");
  }

  private void initGL() {
    GL11.glMatrixMode(GL11.GL_PROJECTION);
    GL11.glLoadIdentity();
    GL11.glOrtho(0, DEFAULT_WIDTH, 0, DEFAULT_HEIGHT, 1, -1);
    GL11.glMatrixMode(GL11.GL_MODELVIEW);
  }

  private void render(int delta) {
    // clear the screen and depth buffer
    GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

    // set the color of the quad (R, G, B, A)
    GL11.glColor3f(QUAD_COLOR.getRed()/255.0f, QUAD_COLOR.getGreen()/255.0f, QUAD_COLOR.getBlue()/255.0f);

    int llx = 0;
    int lly = 0;
    int width = DEFAULT_WIDTH/2;
    int height = DEFAULT_HEIGHT/2;

    // draw the quad
    GL11.glBegin(GL11.GL_QUADS);
      GL11.glVertex2f(llx, lly);
      GL11.glVertex2f(llx+width, lly);
      GL11.glVertex2f(llx+width, lly+height);
      GL11.glVertex2f(llx, lly+height);
    GL11.glEnd();

    updateFPS();
  }

  private int getDelta() {
    long time = Timing.getTimeInMillis();
    int delta = (int)(time - lastTime);
    lastTime = time;

    return delta;
  }

  private void updateFPS() {
    if (Timing.getTimeInMillis() - lastFPS > 1000) {
      Display.setTitle(String.format("%s (FPS: %d)", DEFAULT_TITLE, fps));
      fps = 0;
      lastFPS += 1000; // add one second
    }
    fps++;
  }

  private void resizeGL() {}
}
