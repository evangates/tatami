/*
 * ------------------------------------------------------------------
 *             (C) Copyright 2014, EVAN GATES
 *                     ALL RIGHTS RESERVED
 *             THIS NOTICE DOES NOT IMPLY PUBLICATION
 * ------------------------------------------------------------------
 */

package net.thoughtmerge.tatami;

import com.artemis.Entity;
import com.artemis.World;
import java.awt.Color;
import java.util.Arrays;
import java.util.List;
import net.thoughtmerge.tatami.components.Physical2D;
import net.thoughtmerge.tatami.components.Transform2D;
import net.thoughtmerge.tatami.systems.BoundedMovement;
import net.thoughtmerge.tatami.systems.BoxRendering;
import net.thoughtmerge.tatami.systems.CameraImpulseSystem;
import net.thoughtmerge.tatami.systems.CameraInputSystem;
import net.thoughtmerge.tatami.systems.WrappedMovement;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

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

  private final InputState inputState;
  private final Physical2D camera;

  private boolean exitRequested;

  public Jevolution(float minx, float maxx, float miny, float maxy) {
    width = (int)(maxx - minx);
    height = (int)(maxy - miny);

    inputState = new InputState();
    camera = new Physical2D(0, 0, 0);

    exitRequested = false;

    world = new World();
    timer = new Timer();

    world.setSystem(new CameraInputSystem(width, height, inputState, camera));
    world.setSystem(new CameraImpulseSystem(camera));
    world.setSystem(new BoundedMovement());
    world.setSystem(new BoxRendering());
    world.setSystem(new WrappedMovement());

    world.initialize();
  }

  public void start() {
    try {
      create();

      while(!finished()) {
        final int delta = timer.getDelta();
        world.setDelta(delta);

        handleInput();

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
    return exitRequested || Display.isCloseRequested();
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

  private void handleInput() {
    inputState.clear();

    if (Keyboard.isKeyDown(Keyboard.KEY_W) || Keyboard.isKeyDown(Keyboard.KEY_UP)) {
      inputState.moveCameraUp = true;
    }
    if (!(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT))
        && (Keyboard.isKeyDown(Keyboard.KEY_A) || Keyboard.isKeyDown(Keyboard.KEY_LEFT))) {
      inputState.moveCameraLeft = true;
    }
    if (Keyboard.isKeyDown(Keyboard.KEY_S) || Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
      inputState.moveCameraDown = true;
    }
    if (!(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT))
        && (Keyboard.isKeyDown(Keyboard.KEY_D) || Keyboard.isKeyDown(Keyboard.KEY_RIGHT))) {
      inputState.moveCameraRight = true;
    }

    if ((Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT))
        && (Keyboard.isKeyDown(Keyboard.KEY_A) || Keyboard.isKeyDown(Keyboard.KEY_LEFT))) {
      inputState.rotateCameraCounterClockwise = true;
    }
    if ((Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT))
        && (Keyboard.isKeyDown(Keyboard.KEY_D) || Keyboard.isKeyDown(Keyboard.KEY_RIGHT))) {
      inputState.rotateCameraClockwise = true;
    }

    if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
      exitRequested = true;
    }
  }

  private void cleanup() {
    Display.destroy();
  }
}
