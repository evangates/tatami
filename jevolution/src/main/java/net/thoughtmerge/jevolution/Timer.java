/*
 * ------------------------------------------------------------------
 *             (C) Copyright 2014, EVAN GATES
 *                     ALL RIGHTS RESERVED
 *             THIS NOTICE DOES NOT IMPLY PUBLICATION
 * ------------------------------------------------------------------
 */

package net.thoughtmerge.jevolution;

import org.lwjgl.Sys;

/**
 *
 * @author evan.gates
 */
public final class Timer {
  private long lastTime;
  private int framesThisSecond;
  private long lastFpsTimeInMillis;
  private int fps;

  public Timer() {
    getDelta();
    lastFpsTimeInMillis = getTimeInMillis();
  }

  /**
   * Get time in milliseconds
   *
   * @return system time in milliseconds
   */
  public static long getTimeInMillis() {
    return (Sys.getTime() * 1000) / Sys.getTimerResolution();
  }

  public int getDelta() {
    long time = getTimeInMillis();
    int delta = (int)(time - lastTime);
    lastTime = time;

    return delta;
  }

  public int getFps() {
    return fps;
  }

  public int getFramesThisSecond() {
    return framesThisSecond;
  }

  public void updateFPS() {
    if (getTimeInMillis() - lastFpsTimeInMillis > 1000) {
      fps = framesThisSecond;
      framesThisSecond = 0;
      lastFpsTimeInMillis += 1000; // add one second
    }
    framesThisSecond++;
  }
}
