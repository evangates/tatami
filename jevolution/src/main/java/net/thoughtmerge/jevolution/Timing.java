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
public final class Timing {
  private Timing() {
    // prevent instantiation
  }

  /**
   * Get time in milliseconds
   *
   * @return system time in milliseconds
   */
  public static long getTimeInMillis() {
    return (Sys.getTime() * 1000) / Sys.getTimerResolution();
  }
}
