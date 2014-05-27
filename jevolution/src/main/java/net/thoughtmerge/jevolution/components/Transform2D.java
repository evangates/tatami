/*
 * ------------------------------------------------------------------
 *             (C) Copyright 2014, EVAN GATES
 *                     ALL RIGHTS RESERVED
 *             THIS NOTICE DOES NOT IMPLY PUBLICATION
 * ------------------------------------------------------------------
 */

package net.thoughtmerge.jevolution.components;

import com.artemis.Component;

/**
 * Component containing information about position and orientation.
 *
 * @author evan.gates
 */
public class Transform2D extends Component {
  public float x;
  public float y;
  public float rotationInDegrees;

  public Transform2D(float x, float y, float rotation) {
    this.x = x;
    this.y = y;
    this.rotationInDegrees = rotation;
  }

  public Transform2D(float x, float y) {
    this(x, y, 0);
  }
}
