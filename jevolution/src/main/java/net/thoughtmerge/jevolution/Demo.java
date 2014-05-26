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
public class Demo {

  private final static Logger logger = LoggerFactory.getLogger(Demo.class);

  public final static int DEFAULT_WIDTH = 1024;
  public final static float ASPECT_RATIO = 9.0f / 16.0f;
  public final static int DEFAULT_HEIGHT = (int)(DEFAULT_WIDTH * ASPECT_RATIO);
  public final static String DEFAULT_TITLE = "Jevolution";
  public final static Color QUAD_COLOR = Color.WHITE;
  public final static int TARGET_FPS = 60;

  private final Timer timer;

  private float quadX = DEFAULT_WIDTH / 2.0f;
  private float quadWidth = DEFAULT_WIDTH / 10.0F;
  private float quadY = DEFAULT_HEIGHT / 2.0f;
  private float quadHeight = DEFAULT_HEIGHT / (10.0F * ASPECT_RATIO);
  private float quadRotation = 0;

  public static void main(String[] args) {
    Demo game = null;

    try {
      game = new Demo();

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

  private Demo() {
    timer = new Timer();
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

    timer.updateFPS();
  }

  private void run() {
    while (!Display.isCloseRequested()) {

      handleInput();

      updateState(timer.getDelta());
      renderGL();

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

  private void updateState(int delta) {
    // rotate quad
    quadRotation += 0.15f * delta;

    if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
      quadX -= 0.35f * delta;
    }
    if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
      quadX += 0.35f * delta;
    }

    if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
      quadY -= 0.35f * delta;
    }
    if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
      quadY += 0.35f * delta;
    }

    // keep quad on screen
    if (quadX < 0) quadX = 0;
    if (quadX > DEFAULT_WIDTH) quadX = DEFAULT_WIDTH;

    if (quadY < 0) quadY = 0;
    if (quadY > DEFAULT_HEIGHT) quadY = DEFAULT_HEIGHT;

    // make quad bigger
    if (Mouse.isButtonDown(0)) {
      quadWidth += 0.15f * delta;
      quadHeight += 0.15f * delta;
    }

    // make quad smaller
    if (Mouse.isButtonDown(1)) {
      quadWidth -= 0.15f * delta;
      quadHeight -= 0.15f * delta;
    }

    // clamp size
    if (quadWidth < 0.15f || quadHeight < 0.15f) {
      quadWidth = 0.15f;
      quadHeight = 0.15f / ASPECT_RATIO;
    }

    timer.updateFPS();
  }

  private void renderGL() {
    // clear the screen and depth buffer
    GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

    // set the color of the quad (R, G, B, A)
    GL11.glColor3f(QUAD_COLOR.getRed()/255.0f, QUAD_COLOR.getGreen()/255.0f, QUAD_COLOR.getBlue()/255.0f);

    // draw the quad
    GL11.glPushMatrix();
      GL11.glTranslatef(quadX, quadY, 0);
      GL11.glRotatef(quadRotation, 0f, 0f, 1f);
      GL11.glTranslatef(-quadX, -quadY, 0);

      GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2f(quadX - quadWidth/2.0f, quadY - quadHeight/2.0f);
        GL11.glVertex2f(quadX + quadWidth/2.0f, quadY - quadHeight/2.0f);
        GL11.glVertex2f(quadX + quadWidth/2.0f, quadY + quadHeight/2.0f);
        GL11.glVertex2f(quadX - quadWidth/2.0f, quadY + quadHeight/2.0f);
      GL11.glEnd();
    GL11.glPopMatrix();
  }

  private void resizeGL() {}
}
