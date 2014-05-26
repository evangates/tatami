/*
 * ------------------------------------------------------------------
 *             (C) Copyright 2014, EVAN GATES
 *                     ALL RIGHTS RESERVED
 *             THIS NOTICE DOES NOT IMPLY PUBLICATION
 * ------------------------------------------------------------------
 */

package net.thoughtmerge.jevolution;

import com.artemis.World;

/**
 *
 * @author evan.gates
 */
public class Jevolution {
  private World world;

  public Jevolution() {
    world = new World();

    world.initialize();

    while(true) {
      world.setDelta(delta);
    }
  }
}
