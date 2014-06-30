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
import net.thoughtmerge.jevolution.components.DampedCameraImpulse;
import net.thoughtmerge.jevolution.components.RenderableBox;
import net.thoughtmerge.jevolution.components.Transform2D;
import net.thoughtmerge.jevolution.components.Transform2DBounds;
import net.thoughtmerge.jevolution.components.Transform2DWrapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author evan.gates
 */
public class EntityFactory {

  private final static Logger logger = LoggerFactory.getLogger(EntityFactory.class);

  public static Entity createBoundedBox(World world, float x, float y, float minX, float maxX, float minY, float maxY, float minRotInDegs, float maxRotInDegs) {
    Entity boundedBox = world.createEntity();

    boundedBox.addComponent(new Transform2D(x, y));
    boundedBox.addComponent(new Transform2DBounds(minX, maxX, minY, maxY, minRotInDegs, maxRotInDegs));
    boundedBox.addComponent(new RenderableBox((maxX - minX) / 10f, (maxY - minY) / 10, Color.WHITE));

    return boundedBox;
  }

  public static Entity createWrappingBox(World world, float x, float y, float minX, float maxX, float minY, float maxY, float minRotInDegs, float maxRotInDegs) {
    Entity boundedBox = world.createEntity();

    boundedBox.addComponent(new Transform2D(x, y));
    boundedBox.addComponent(new Transform2DWrapping(minX, maxX, minY, maxY, minRotInDegs, maxRotInDegs));
    boundedBox.addComponent(new RenderableBox((maxX - minX) / 10f, (maxY - minY) / 10, Color.WHITE));

    return boundedBox;
  }

  public static Entity createBox(World world, float x, float y, float rotation, float width, float height, Color color) {
    Entity boundedBox = world.createEntity();

    boundedBox.addComponent(new Transform2D(x, y));
    boundedBox.addComponent(new RenderableBox(width, height, color));

    return boundedBox;
  }

  public static Entity createDampedCameraImpulse(World world, float x, float y, float rotInDegrees, int durationInMillis) {
    Entity dampedCameraImpulse = world.createEntity();

    logger.debug("Creating damped camera impulse: (x: {}, y: {}, rot: {}, dur: {}, id: {})", x, y, rotInDegrees, durationInMillis, dampedCameraImpulse.getId());

    dampedCameraImpulse.addComponent(new DampedCameraImpulse(x, y, rotInDegrees, durationInMillis, false));

    return dampedCameraImpulse;
  }

  public static Entity createReversedDampedCameraImpulse(World world, DampedCameraImpulse original) {
    Entity reversedImpulse = world.createEntity();

    logger.debug("Creating reversed impulse from {}", original);

    reversedImpulse.addComponent(new DampedCameraImpulse(-original.x, -original.y, -original.rotInDegrees, original.durationInMillis, true));

    return reversedImpulse;
  }
}
