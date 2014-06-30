/*
 * ------------------------------------------------------------------
 *             (C) Copyright 2014, EVAN GATES
 *                     ALL RIGHTS RESERVED
 *             THIS NOTICE DOES NOT IMPLY PUBLICATION
 * ------------------------------------------------------------------
 */

package net.thoughtmerge.jevolution.systems;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import net.thoughtmerge.jevolution.EntityFactory;
import net.thoughtmerge.jevolution.components.DampedCameraImpulse;
import net.thoughtmerge.jevolution.components.Transform2D;
import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author evan.gates
 */
public class CameraImpulseSystem extends EntityProcessingSystem {

  public final static float SPEED = 0.1f;
  public final static float SPEED_ROT = 0.2f;

  private final Transform2D tx;

  private final static Logger logger = LoggerFactory.getLogger(CameraImpulseSystem.class);

  @SuppressWarnings("unchecked")
  public CameraImpulseSystem(Transform2D camera) {
    super(Aspect.getAspectForAll(DampedCameraImpulse.class));

    Validate.notNull(camera, "camera cannot be null");

    this.tx = camera;
  }

  @Override
  protected void process(Entity e) {
    DampedCameraImpulse impulse = e.getComponent(DampedCameraImpulse.class);

    if (expired(impulse)) {
      logger.debug("deleting {} entity with id: {}", DampedCameraImpulse.class.getSimpleName(), e.getId());
      e.deleteFromWorld();

      // create reverse impulse
      if (!impulse.isReversed) {
        world.addEntity(
            EntityFactory.createReversedDampedCameraImpulse(world, impulse)
        );
      }
    }

    tx.x += impulse.x;
    tx.y += impulse.y;
    tx.rotationInDegrees += impulse.rotInDegrees;

    impulse.timeLeft -= world.getDelta();
  }

  private boolean expired(DampedCameraImpulse impulse) {
    return impulse.timeLeft <= 0;
  }
}
