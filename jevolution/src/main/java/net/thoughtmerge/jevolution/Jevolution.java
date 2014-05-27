/*
 * ------------------------------------------------------------------
 *             (C) Copyright 2014, EVAN GATES
 *                     ALL RIGHTS RESERVED
 *             THIS NOTICE DOES NOT IMPLY PUBLICATION
 * ------------------------------------------------------------------
 */

package net.thoughtmerge.jevolution;

import com.artemis.Entity;
import com.artemis.World;
import net.thoughtmerge.jevolution.systems.BoundedMovement;
import net.thoughtmerge.jevolution.systems.BoxRendering;
import net.thoughtmerge.jevolution.systems.WrappedMovement;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

/**
 *
 * @author evan.gates
 */
public class Jevolution {
  private final World world;
  private final Timer timer;
  private final static String DEFAULT_TITLE = "Jevolution";
  private final static int TARGET_FPS = 60;

  private int width;
  private int height;

  public Jevolution(float minx, float maxx, float miny, float maxy) {
    width = (int)(maxx - minx);
    height = (int)(maxy - miny);

    world = new World();
    timer = new Timer();

    world.setSystem(new BoundedMovement());
    world.setSystem(new BoxRendering());
    world.setSystem(new WrappedMovement());

    world.initialize();
  }

  public void start() {
    try {
      create();

      while(!Display.isCloseRequested()) {
        tick();
      }
    }
    catch(LWJGLException ex) {
      ex.printStackTrace(System.err);
    }
    finally {
      cleanup();
    }
  }

  private void create() throws LWJGLException {
    Display.setDisplayMode(new DisplayMode(width, height));
    Display.setFullscreen(false);
    Display.setTitle(DEFAULT_TITLE);
    Display.setResizable(false);
    Display.create();

    GL11.glMatrixMode(GL11.GL_PROJECTION);
    GL11.glLoadIdentity();
    GL11.glOrtho(0, width, 0, height, 1, -1);
    GL11.glMatrixMode(GL11.GL_MODELVIEW);

    Entity thing = EntityFactory.createWrappingBox(world, 0, 0, 0, width, 0, height, 0, 360);
    thing.addToWorld();
  }

  /**
   * call this in the main loop
   *
   * updates timer, processes entities
   */
  private void tick() {
    // clear the screen and depth buffer
    GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

    world.setDelta(timer.getDelta());
    world.process();
    timer.updateFPS();

    Display.update();
    Display.sync(TARGET_FPS);
  }

  private void cleanup() {
    Display.destroy();
  }
}
