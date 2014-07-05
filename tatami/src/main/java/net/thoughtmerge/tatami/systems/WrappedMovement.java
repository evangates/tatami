/*
 * ------------------------------------------------------------------
 *             (C) Copyright 2014, EVAN GATES
 *                     ALL RIGHTS RESERVED
 *             THIS NOTICE DOES NOT IMPLY PUBLICATION
 * ------------------------------------------------------------------
 */

package net.thoughtmerge.tatami.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import net.thoughtmerge.tatami.components.Transform2D;
import net.thoughtmerge.tatami.components.Transform2DWrapping;

/**
 *
 * @author evan.gates
 */
public class WrappedMovement extends EntityProcessingSystem {
  @Mapper ComponentMapper<Transform2D> transformMapper;
  @Mapper ComponentMapper<Transform2DWrapping> wrappingMapper;

  private final static float rotationRate = 10f / 360f;
  private final static float xRate = 0.15f;
  private final static float yRate = 0.15f;

  @SuppressWarnings("unchecked")
  public WrappedMovement() {
    super(Aspect.getAspectForAll(Transform2D.class, Transform2DWrapping.class));
  }

  @Override
  protected void process(final Entity entity) {
    final Transform2D tx = transformMapper.get(entity);
    final Transform2DWrapping wrapping = wrappingMapper.get(entity);

    final float delta = world.getDelta();

    tx.x = wrap(tx.x + xRate * delta, wrapping.minX, wrapping.maxX);
    tx.y = wrap(tx.y + yRate * delta, wrapping.minY, wrapping.maxY);
    tx.rotationInDegrees = wrap(tx.rotationInDegrees + rotationRate * delta,
        wrapping.minRotationInDegrees, wrapping.maxRotationInDegrees);
  }

  private float wrap(float value, final float min, final float max) {
    while (value < min) {
      value += (max - min);
    }

    while (value > max) {
      value -= (max - min);
    }

    return value;
  }
}
