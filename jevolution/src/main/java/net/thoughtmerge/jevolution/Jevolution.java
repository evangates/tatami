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
import java.awt.Color;
import java.util.Arrays;
import java.util.List;
import net.thoughtmerge.ColorUtils;
import net.thoughtmerge.jevolution.systems.BoundedMovement;
import net.thoughtmerge.jevolution.systems.BoxRendering;
import net.thoughtmerge.jevolution.systems.CameraInputSystem;
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

  private final int width;
  private final int height;

  private final static Color bgColor = new Color(0x44, 0x44, 0x44);
  private final static float bgRed = ColorUtils.toFloat(bgColor.getRed());
  private final static float bgGreen = ColorUtils.toFloat(bgColor.getGreen());
  private final static float bgBlue = ColorUtils.toFloat(bgColor.getBlue());

  public Jevolution(float minx, float maxx, float miny, float maxy) {
    width = (int)(maxx - minx);
    height = (int)(maxy - miny);

    inputSystem = new CameraInputSystem(width, height);

    world = new World();
    timer = new Timer();

    world.setSystem(inputSystem);
    world.setSystem(new BoundedMovement());
    world.setSystem(new BoxRendering());
    world.setSystem(new WrappedMovement());

    world.initialize();
  }
  private final CameraInputSystem inputSystem;

  public void start() {
    try {
      create();

      while(!finished()) {
        // clear the screen and depth buffer
        GL11.glClearColor(bgRed, bgGreen, bgBlue, 1);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

        final int delta = timer.getDelta();
        world.setDelta(delta);

        world.process();
        timer.updateFPS();

        Display.update();
        Display.sync(TARGET_FPS);

        Display.setTitle(String.format("%s: %d fps", DEFAULT_TITLE, timer.getFps()));
      }
    }
    catch(LWJGLException ex) {
      ex.printStackTrace(System.err);
    }
    finally {
      cleanup();
    }
  }

  private boolean finished() {
    return Display.isCloseRequested() || inputSystem.isExitRequested();
  }

  private void create() throws LWJGLException {
    Display.setDisplayMode(new DisplayMode(width, height));
    Display.setFullscreen(false);
    Display.setTitle(DEFAULT_TITLE);
    Display.setResizable(false);
    Display.create();

    List<Entity> entities = Arrays.asList(
        EntityFactory.createBox(world, 10, 15, 0, 20, 30, Color.GREEN)
        ,EntityFactory.createBox(world, width / 2.0f, height / 2.0f, 0, 50, 50, Color.ORANGE)
    );

    for (Entity entity : entities) {
      entity.addToWorld();
    }
  }

  private void cleanup() {
    Display.destroy();
  }
}
