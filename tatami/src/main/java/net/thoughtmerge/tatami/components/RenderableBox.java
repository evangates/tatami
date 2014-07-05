/*
 * ------------------------------------------------------------------
 *             (C) Copyright 2014, EVAN GATES
 *                     ALL RIGHTS RESERVED
 *             THIS NOTICE DOES NOT IMPLY PUBLICATION
 * ------------------------------------------------------------------
 */

package net.thoughtmerge.tatami.components;

import com.artemis.Component;
import java.awt.Color;
import net.thoughtmerge.ColorUtils;

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

  public RenderableBox(float width, float height, Color color) {
    this.width = width;
    this.height = height;

    this.alpha = ColorUtils.toFloat(color.getAlpha());
    this.red = ColorUtils.toFloat(color.getRed());
    this.blue = ColorUtils.toFloat(color.getBlue());
    this.green = ColorUtils.toFloat(color.getGreen());
  }
}
