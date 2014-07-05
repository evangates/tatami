/*
 * ------------------------------------------------------------------
 *             (C) Copyright 2014, EVAN GATES
 *                     ALL RIGHTS RESERVED
 *             THIS NOTICE DOES NOT IMPLY PUBLICATION
 * ------------------------------------------------------------------
 */

package net.thoughtmerge.tatami.systems;

import com.artemis.systems.VoidEntitySystem;
import java.awt.Color;
import net.thoughtmerge.ColorUtils;
import net.thoughtmerge.tatami.EntityFactory;
import net.thoughtmerge.tatami.InputState;
import net.thoughtmerge.tatami.components.Physical2D;
import org.apache.commons.lang.Validate;
import org.lwjgl.opengl.GL11;

/**
 *
 * @author evan.gates
 */
public class CameraInputSystem extends VoidEntitySystem {

  public final static float ACCEL = 0.1f / 10;
  public final static float ACCEL_ROT = 0.2f / 10;
  public final static float TOLERANCE = 0.00001f;

  private final Physical2D camera;

  private final static Color bgColor = new Color(0x44, 0x44, 0x44);
  private final static float bgRed = ColorUtils.toFloat(bgColor.getRed());
  private final static float bgGreen = ColorUtils.toFloat(bgColor.getGreen());
  private final static float bgBlue = ColorUtils.toFloat(bgColor.getBlue());

  private final int width;
  private final int height;

  private final InputState inputState;

  public CameraInputSystem(int width, int height, InputState inputState, Physical2D camera) {
    Validate.isTrue(width > 0, "width must be greater than 0");
    Validate.isTrue(height > 0, "height must be greater than 0");
    Validate.notNull(inputState, "inputState cannot be null");
    Validate.notNull(camera, "camera cannot be null");

    this.camera = camera;

    this.width = width;
    this.height = height;

    this.inputState = inputState;
  }

  @Override
  protected void processSystem() {
    final float adjustment = ACCEL;
    final float rotAdjustment = ACCEL_ROT;

    if (inputState.moveCameraUp) {
      world.addEntity(
          EntityFactory.createCameraImpulse(world, 0, -adjustment, 0)
      );
    }
    if (inputState.moveCameraLeft) {
      world.addEntity(
          EntityFactory.createCameraImpulse(world, adjustment, 0, 0)
      );
    }
    if (inputState.moveCameraDown) {
      world.addEntity(
          EntityFactory.createCameraImpulse(world, 0, adjustment, 0)
      );
    }
    if (inputState.moveCameraRight) {
      world.addEntity(
          EntityFactory.createCameraImpulse(world, -adjustment, 0, 0)
      );
    }

    if (inputState.rotateCameraCounterClockwise) {
      world.addEntity(
          EntityFactory.createCameraImpulse(world, 0, 0, rotAdjustment)
      );
    }
    if (inputState.rotateCameraClockwise) {
      world.addEntity(
          EntityFactory.createCameraImpulse(world, 0, 0, -rotAdjustment)
      );
    }

    doCamera();
  }

  private void doCamera() {

    camera.velx += camera.accelx;
    camera.vely += camera.accely;

    camera.rotVelInDegrees += camera.rotAccelInDegrees;

    camera.x += camera.velx;
    camera.y += camera.vely;

    camera.rotInDegrees += camera.rotVelInDegrees;

    moveCameraTowardOrigin();

    // clear the screen and depth buffer
    GL11.glClearColor(bgRed, bgGreen, bgBlue, 1);
    GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

    // set the camera location
    GL11.glMatrixMode(GL11.GL_PROJECTION);
    GL11.glLoadIdentity();
    GL11.glOrtho(camera.x, camera.x + width, camera.y, camera.y + height, 1, -1);
    GL11.glRotatef(camera.rotInDegrees, 0, 0, 1);
    GL11.glMatrixMode(GL11.GL_MODELVIEW);
  }

  private void moveCameraTowardOrigin() {
    float mag = (float)Math.sqrt(camera.x * camera.x + camera.y * camera.y);

    if (mag <= TOLERANCE) {
      camera.x = 0;
      camera.y = 0;
    }
    else {
      camera.accelx = -camera.x / mag;
      camera.accely = -camera.y / mag;
    }

    if (camera.rotInDegrees <= TOLERANCE) {
      camera.rotInDegrees = 0;
    }
    else {
      camera.rotAccelInDegrees = -Math.signum(camera.rotInDegrees);
    }
  }
}
