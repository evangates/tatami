/*
 * ------------------------------------------------------------------
 *             (C) Copyright 2014, EVAN GATES
 *                     ALL RIGHTS RESERVED
 *             THIS NOTICE DOES NOT IMPLY PUBLICATION
 * ------------------------------------------------------------------
 */

package net.thoughtmerge.jevolution.systems;

import com.artemis.systems.VoidEntitySystem;
import net.thoughtmerge.jevolution.components.Transform2D;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

/**
 *
 * @author evan.gates
 */
public class CameraInputSystem extends VoidEntitySystem {

  public final static float SPEED = 0.1f;
  public final static float SPEED_ROT = 0.2f;

  private final Transform2D tx;

  private final int width;
  private final int height;

  private boolean exitRequested;

  public CameraInputSystem(int width, int height) {
    tx = new Transform2D(0, 0, 0);

    this.width = width;
    this.height = height;

    exitRequested = false;
  }

  public boolean isExitRequested() {
    return exitRequested;
  }

  @Override
  protected void processSystem() {
    final float delta = world.getDelta();
    final float adjustment = delta * SPEED;
    final float rotAdjustment = delta * SPEED_ROT;

    if (Keyboard.isKeyDown(Keyboard.KEY_W) || Keyboard.isKeyDown(Keyboard.KEY_UP)) {
      tx.y -= adjustment;
    }
    if (!(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT))
        && (Keyboard.isKeyDown(Keyboard.KEY_A) || Keyboard.isKeyDown(Keyboard.KEY_LEFT))) {
      tx.x += adjustment;
    }
    if (Keyboard.isKeyDown(Keyboard.KEY_S) || Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
      tx.y += adjustment;
    }
    if (!(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT))
        && (Keyboard.isKeyDown(Keyboard.KEY_D) || Keyboard.isKeyDown(Keyboard.KEY_RIGHT))) {
      tx.x -= adjustment;
    }

    if ((Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT))
        && (Keyboard.isKeyDown(Keyboard.KEY_A) || Keyboard.isKeyDown(Keyboard.KEY_LEFT))) {
      tx.rotationInDegrees += rotAdjustment;
    }
    if ((Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT))
        && (Keyboard.isKeyDown(Keyboard.KEY_D) || Keyboard.isKeyDown(Keyboard.KEY_RIGHT))) {
      tx.rotationInDegrees -= rotAdjustment;
    }

    if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
      exitRequested = true;
    }

    doCamera();
  }

  private void doCamera() {
    GL11.glMatrixMode(GL11.GL_PROJECTION);
    GL11.glLoadIdentity();
    GL11.glOrtho(tx.x, tx.x + width, tx.y, tx.y + height, 1, -1);
    GL11.glRotatef(tx.rotationInDegrees, 0, 0, 1);
    GL11.glMatrixMode(GL11.GL_MODELVIEW);
  }
}
