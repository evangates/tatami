/*
 * ------------------------------------------------------------------
 *             (C) Copyright 2014, EVAN GATES
 *                     ALL RIGHTS RESERVED
 *             THIS NOTICE DOES NOT IMPLY PUBLICATION
 * ------------------------------------------------------------------
 */

package net.thoughtmerge.tatami;

/**
 *
 * @author evan.gates
 */
public final class InputState {
  public volatile boolean moveCameraLeft;
  public volatile boolean moveCameraRight;
  public volatile boolean moveCameraUp;
  public volatile boolean moveCameraDown;

  public volatile boolean rotateCameraClockwise;
  public volatile boolean rotateCameraCounterClockwise;

  public InputState() {
    clear();
  }

  public void clear() {
    moveCameraDown = false;
    moveCameraLeft = false;
    moveCameraRight = false;
    moveCameraUp = false;

    rotateCameraClockwise = false;
    rotateCameraCounterClockwise = false;
  }
}
