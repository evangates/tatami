/*
 * ------------------------------------------------------------------
 *             (C) Copyright 2014, EVAN GATES
 *                     ALL RIGHTS RESERVED
 *             THIS NOTICE DOES NOT IMPLY PUBLICATION
 * ------------------------------------------------------------------
 */

package net.thoughtmerge.jevolution.components;

import com.artemis.Component;
import java.awt.Color;

/**
 *
 * @author evan.gates
 */
public class RenderableBox extends Component {
  public float width;
  public float height;

  // valid valuese are 0-1 for these
  public float alpha;
  public float red;
  public float blue;
  public float green;

  public static float cameraX = 0;
  public static float cameraY = 0;

  public RenderableBox(float width, float height, Color color) {
    this.width = width;
    this.height = height;

    this.alpha = toFloat(color.getAlpha());
    this.red = toFloat(color.getRed());
    this.blue = toFloat(color.getBlue());
    this.green = toFloat(color.getGreen());
  }

  private float toFloat(int colorComponent) {
    return colorComponent / 255.0f;
  }
}
