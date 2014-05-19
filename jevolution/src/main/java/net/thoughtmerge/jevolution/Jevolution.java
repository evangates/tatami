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

/**
 *
 * @author evan.gates
 */
public class Jevolution {

  public final static int DEFAULT_WIDTH = 1024;
  public final static int DEFAULT_HEIGHT = (int)((double)DEFAULT_WIDTH * 16.0 / 9.0);
  public final static String DEFAULT_TITLE = "Jevolution";

  public static void main(String[] args) {
    Jevolution game = null;

    try {
      game = new Jevolution();

      game.create();
      game.run();
    }
    catch(Exception ex) {
      ex.printStackTrace(System.err);
//      Sys
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
  }
  private void run() {}
  private void cleanup() {}
  private void initGL() {}
  private void resizeGL() {}
}
