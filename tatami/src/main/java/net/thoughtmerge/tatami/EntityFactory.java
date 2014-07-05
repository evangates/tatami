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
import net.thoughtmerge.tatami.components.CameraImpulse;
import net.thoughtmerge.tatami.components.RenderableBox;
import net.thoughtmerge.tatami.components.Transform2D;
import net.thoughtmerge.tatami.components.Transform2DBounds;
import net.thoughtmerge.tatami.components.Transform2DWrapping;
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

  public static Entity createCameraImpulse(World world, float accelX, float accelY, float rotAccelInDegrees) {
    Entity dampedCameraImpulse = world.createEntity();

    logger.debug("Creating camera impulse: (accelx: {}, accely: {}, accelrot: {}, id: {})", accelX, accelY, rotAccelInDegrees, dampedCameraImpulse.getId());

    dampedCameraImpulse.addComponent(new CameraImpulse(accelX, accelY, rotAccelInDegrees));

    return dampedCameraImpulse;
  }
}
