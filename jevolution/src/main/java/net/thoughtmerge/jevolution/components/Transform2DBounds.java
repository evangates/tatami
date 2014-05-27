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
 *
 * @author evan.gates
 */
public class Transform2DBounds extends Component {
  public float minX;
  public float maxX;

  public float minY;
  public float maxY;

  public float minRotationInDegrees;
  public float maxRotationInDegrees;

  public Transform2DBounds(float minX, float maxX, float minY, float maxY, float minRotationInDegrees, float maxRotationInDegrees) {
    this.minX = minX;
    this.maxX = maxX;
    this.minY = minY;
    this.maxY = maxY;
    this.minRotationInDegrees = minRotationInDegrees;
    this.maxRotationInDegrees = maxRotationInDegrees;
  }
}
