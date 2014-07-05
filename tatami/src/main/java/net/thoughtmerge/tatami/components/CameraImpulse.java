/*
 * ------------------------------------------------------------------
 *             (C) Copyright 2014, EVAN GATES
 *                     ALL RIGHTS RESERVED
 *             THIS NOTICE DOES NOT IMPLY PUBLICATION
 * ------------------------------------------------------------------
 */

package net.thoughtmerge.tatami.components;

import com.artemis.Component;

/**
 *
 * @author evan.gates
 */
public final class CameraImpulse extends Component {
  public final float accelX;
  public final float accelY;

  public final float rotAccelInDegrees;

  public CameraImpulse(float accelX, float accelY, float rotAccelInDegrees) {
    this.accelX = accelX;
    this.accelY = accelY;

    this.rotAccelInDegrees = rotAccelInDegrees;
  }
}
