/*
 * ------------------------------------------------------------------
 *             (C) Copyright 2014, EVAN GATES
 *                     ALL RIGHTS RESERVED
 *             THIS NOTICE DOES NOT IMPLY PUBLICATION
 * ------------------------------------------------------------------
 */

package net.thoughtmerge.jevolution.systems;

import com.artemis.systems.VoidEntitySystem;
import java.awt.Color;
import net.thoughtmerge.ColorUtils;
import net.thoughtmerge.jevolution.EntityFactory;
import net.thoughtmerge.jevolution.InputState;
import net.thoughtmerge.jevolution.components.Transform2D;
import org.apache.commons.lang.Validate;
import org.lwjgl.opengl.GL11;

/**
 *
 * @author evan.gates
 */
public class CameraInputSystem extends VoidEntitySystem {

  public final static float SPEED = 0.1f;
  public final static float SPEED_ROT = 0.2f;
  public final static int DURATION_IN_MILLIS = 1000;

  private final Transform2D camera;

  private final static Color bgColor = new Color(0x44, 0x44, 0x44);
  private final static float bgRed = ColorUtils.toFloat(bgColor.getRed());
  private final static float bgGreen = ColorUtils.toFloat(bgColor.getGreen());
  private final static float bgBlue = ColorUtils.toFloat(bgColor.getBlue());

  private final int width;
  private final int height;

  private final InputState inputState;

  public CameraInputSystem(int width, int height, InputState inputState, Transform2D camera) {
    Validate.isTrue(width > 0, "width must be greater than 0");
    Validate.isTrue(height > 0, "height must be greater than 0");
    Validate.notNull(inputState, "inputState cannot be null");

    this.camera = camera;

    this.width = width;
    this.height = height;

    this.inputState = inputState;
  }

  @Override
  protected void processSystem() {
    final float delta = world.getDelta();
    final float adjustment = delta * SPEED;
    final float rotAdjustment = delta * SPEED_ROT;

    if (inputState.moveCameraUp) {
      world.addEntity(
          EntityFactory.createDampedCameraImpulse(world, 0, -adjustment, 0, DURATION_IN_MILLIS)
      );
    }
    if (inputState.moveCameraLeft) {
      world.addEntity(
          EntityFactory.createDampedCameraImpulse(world, adjustment, 0, 0, DURATION_IN_MILLIS)
      );
    }
    if (inputState.moveCameraDown) {
      world.addEntity(
          EntityFactory.createDampedCameraImpulse(world, 0, adjustment, 0, DURATION_IN_MILLIS)
      );
    }
    if (inputState.moveCameraRight) {
      world.addEntity(
          EntityFactory.createDampedCameraImpulse(world, -adjustment, 0, 0, DURATION_IN_MILLIS)
      );
    }

    if (inputState.rotateCameraCounterClockwise) {
      world.addEntity(
          EntityFactory.createDampedCameraImpulse(world, 0, 0, rotAdjustment, DURATION_IN_MILLIS)
      );
    }
    if (inputState.rotateCameraClockwise) {
      world.addEntity(
          EntityFactory.createDampedCameraImpulse(world, 0, 0, -rotAdjustment, DURATION_IN_MILLIS)
      );
    }

    doCamera();
  }

  private void doCamera() {
    // clear the screen and depth buffer
    GL11.glClearColor(bgRed, bgGreen, bgBlue, 1);
    GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

    // set the camera location
    GL11.glMatrixMode(GL11.GL_PROJECTION);
    GL11.glLoadIdentity();
    GL11.glOrtho(camera.x, camera.x + width, camera.y, camera.y + height, 1, -1);
    GL11.glRotatef(camera.rotationInDegrees, 0, 0, 1);
    GL11.glMatrixMode(GL11.GL_MODELVIEW);
  }
}
