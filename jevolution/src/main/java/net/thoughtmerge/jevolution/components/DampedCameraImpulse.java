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
public final class DampedCameraImpulse extends Component {
  public final float x;
  public final float y;
  public final float rotInDegrees;

  public final boolean isReversed;

  public final int durationInMillis;
  public int timeLeft;

  public DampedCameraImpulse(float x, float y, float rotInDegrees, int durationInMillis, boolean isReversed) {
    this.x = x;
    this.y = y;
    this.rotInDegrees = rotInDegrees;
    this.durationInMillis = durationInMillis;
    this.isReversed = isReversed;

    this.timeLeft = durationInMillis;
  }
}
