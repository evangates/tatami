/*
 * ------------------------------------------------------------------
 *             (C) Copyright 2014, EVAN GATES
 *                     ALL RIGHTS RESERVED
 *             THIS NOTICE DOES NOT IMPLY PUBLICATION
 * ------------------------------------------------------------------
 */

package net.thoughtmerge.jevolution;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
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
    Display.setVSyncEnabled(true);
    Display.setTitle(DEFAULT_TITLE);
    Display.create();

    Keyboard.create();

    Mouse.setGrabbed(false);
    Mouse.create();

    initGL();
    resizeGL();
  }

  private void run() {
    while (!Display.isCloseRequested()) {

      handleInput();

      Display.update();
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

  private void initGL() {}
  private void resizeGL() {}
}
