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
import net.thoughtmerge.tatami.components.CameraImpulse;
import net.thoughtmerge.tatami.components.Physical2D;
import org.apache.commons.lang.Validate;

/**
 *
 * @author evan.gates
 */
public class CameraImpulseSystem extends EntityProcessingSystem {

  private final Physical2D camera;

  @SuppressWarnings("unchecked")
  public CameraImpulseSystem(Physical2D camera) {
    super(Aspect.getAspectForAll(CameraImpulse.class));

    Validate.notNull(camera, "camera cannot be null");

    this.camera = camera;
  }

  @Override
  protected void process(Entity e) {
    CameraImpulse impulse = e.getComponent(CameraImpulse.class);

    camera.accelx += impulse.accelX;
    camera.accely += impulse.accelY;

    camera.rotAccelInDegrees += impulse.rotAccelInDegrees;

    e.deleteFromWorld();
  }
}
