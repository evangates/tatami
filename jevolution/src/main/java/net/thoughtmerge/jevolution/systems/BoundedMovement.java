/*
 * ------------------------------------------------------------------
 *             (C) Copyright 2014, EVAN GATES
 *                     ALL RIGHTS RESERVED
 *             THIS NOTICE DOES NOT IMPLY PUBLICATION
 * ------------------------------------------------------------------
 */

package net.thoughtmerge.jevolution.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import net.thoughtmerge.jevolution.components.Transform2D;
import net.thoughtmerge.jevolution.components.Transform2DBounds;

/**
 *
 * @author evan.gates
 */
public class BoundedMovement extends EntityProcessingSystem {
  @Mapper ComponentMapper<Transform2D> transformMapper;
  @Mapper ComponentMapper<Transform2DBounds> boundsMapper;

  private final static float rotationRate = 10f / 360f;
  private final static float xRate = 0.15f;
  private final static float yRate = 0.15f;

  @SuppressWarnings("unchecked")
  public BoundedMovement() {
    super(Aspect.getAspectForAll(Transform2D.class, Transform2DBounds.class));
  }

  @Override
  protected void process(final Entity entity) {
    final Transform2D tx = transformMapper.get(entity);
    final Transform2DBounds bounds = boundsMapper.get(entity);

    final float delta = world.getDelta();

    tx.x = clamp(tx.x + xRate * delta, bounds.minX, bounds.maxX);
    tx.y = clamp(tx.y + yRate * delta, bounds.minY, bounds.maxY);
    tx.rotationInDegrees = clamp(tx.rotationInDegrees + rotationRate * delta,
        bounds.minRotationInDegrees, bounds.maxRotationInDegrees);
  }

  private float clamp(float value, float min, float max) {
    if (value < min) {
      return min;
    }

    if (value > max) {
      return max;
    }

    return value;
  }
}
