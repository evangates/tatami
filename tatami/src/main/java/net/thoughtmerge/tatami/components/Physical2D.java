/*
 * ------------------------------------------------------------------
 *             (C) Copyright 2014, EVAN GATES
 *                     ALL RIGHTS RESERVED
 *             THIS NOTICE DOES NOT IMPLY PUBLICATION
 * ------------------------------------------------------------------
 */

package net.thoughtmerge.tatami.components;

/**
 *
 * @author evan.gates
 */
public class Physical2D {
  public float x;
  public float y;

  public float velx;
  public float vely;

  public float accelx;
  public float accely;

  public float rotInDegrees;
  public float rotVelInDegrees;
  public float rotAccelInDegrees;

  public Physical2D(float x, float y, float rotInDegrees) {
    this.x = x;
    this.y = y;

    this.rotInDegrees = rotInDegrees;
  }
}
