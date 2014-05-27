/*
 * ------------------------------------------------------------------
 *             (C) Copyright 2014, EVAN GATES
 *                     ALL RIGHTS RESERVED
 *             THIS NOTICE DOES NOT IMPLY PUBLICATION
 * ------------------------------------------------------------------
 */

package net.thoughtmerge.jevolution;

/**
 *
 * @author evan.gates
 */
public class App {

  public final static int WIDTH = 800;
  public final static int HEIGHT = 600;

  public static void main(String[] args) {
    new Jevolution(0, WIDTH, 0, HEIGHT).start();
  }
}
