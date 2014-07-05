/*
 * ------------------------------------------------------------------
 *             (C) Copyright 2014, EVAN GATES
 *                     ALL RIGHTS RESERVED
 *             THIS NOTICE DOES NOT IMPLY PUBLICATION
 * ------------------------------------------------------------------
 */

package net.thoughtmerge.tatami.systems;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import net.thoughtmerge.tatami.components.Transform2D;
import org.lwjgl.input.Keyboard;

/**
 *
 * @author evan.gates
 */
public class EntityInputSystem extends EntityProcessingSystem {
  
  public final static float SPEED = 0.1f;

  @SuppressWarnings("unchecked")
  public EntityInputSystem() {
    super(Aspect.getAspectForAll(Transform2D.class));
  }

  @Override
  protected void process(Entity e) {
    Transform2D tx = e.getComponent(Transform2D.class);

    final float delta = world.getDelta();
    final float adjustment = delta * SPEED;

    if (Keyboard.isKeyDown(Keyboard.KEY_W) || Keyboard.isKeyDown(Keyboard.KEY_UP)) {
      tx.y += adjustment;
    }
    if (Keyboard.isKeyDown(Keyboard.KEY_A) || Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
      tx.x -= adjustment;
    }
    if (Keyboard.isKeyDown(Keyboard.KEY_S) || Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
      tx.y -= adjustment;
    }
    if (Keyboard.isKeyDown(Keyboard.KEY_D) || Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
      tx.x += adjustment;
    }
  }
}
