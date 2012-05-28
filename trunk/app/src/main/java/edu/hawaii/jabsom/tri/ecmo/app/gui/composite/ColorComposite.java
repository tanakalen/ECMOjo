/*
 Copyright 2006 Jerry Huxtable

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */

package edu.hawaii.jabsom.tri.ecmo.app.gui.composite;

import java.awt.*;
import java.awt.image.*;

/**
 * Color Composite.
 * 
 * @author Christoph Aschwanden
 * @since June 9, 2008
 */
public final class ColorComposite extends RGBComposite {

  /** The color. */
  private Color color;
  
  
  /**
   * Constructor.
   * 
   * @param alpha  The alpha level.
   * @param color  The color.
   */
  public ColorComposite(float alpha, Color color) {
    super(alpha);
    this.color = color;
  }

  /**
   * Creates the context.
   * 
   * @param srcColorModel  The source color model.
   * @param dstColorModel  The destination color model.
   * @param hints  The rendering hints.
   * @return The context.
   */
  public CompositeContext createContext(ColorModel srcColorModel, ColorModel dstColorModel, RenderingHints hints) {
    return new Context(extraAlpha, color, srcColorModel, dstColorModel);
  }

  /**
   * The context.
   */
  static class Context extends RGBCompositeContext {

    /** The color. */
    private int color;
    
    
    /**
     * Constructor for context.
     * 
     * @param alpha  The alpha level.
     * @param color  The color.
     * @param srcColorModel  The source color model.
     * @param dstColorModel  The destination color model.
     */
    public Context(float alpha, Color color, ColorModel srcColorModel, ColorModel dstColorModel) {
      super(alpha, srcColorModel, dstColorModel);
      
      // save color
      this.color = color.getRGB() & 0x00ffffff;
    }

    /**
     * The composing function. 
     * 
     * @param srcRGB  The source RGB.
     * @param dstRGB  The pre-destination RGB.
     * @param alpha  The alpha level in the range [0, 1].
     * @return  The compbined destination RGB.
     */
    @Override
    public int composeRGB(int srcRGB, int dstRGB, float alpha) {
      int t0 = (srcRGB >> 24) & 0xff;
      if (t0 > 0) {
        int r0 = (this.color >> 16) & 0xff;
        int g0 = (this.color >> 8) & 0xff;
        int b0 = (this.color) & 0xff;    
        
        int t1 = (dstRGB >> 24) & 0xff;
        if (t1 > 0) {
          r0 = r0 * t0 / 255;
          g0 = g0 * t0 / 255;
          b0 = b0 * t0 / 255;
          int r1 = (dstRGB >> 16) & 0xff;
          int g1 = (dstRGB >> 8) & 0xff;
          int b1 = (dstRGB) & 0xff;    
          int revertOpacity = 255 - t0;
          r1 = r1 * revertOpacity / 255;
          g1 = g1 * revertOpacity / 255;
          b1 = b1 * revertOpacity / 255;
          return 0xff000000 | ((r0 + r1) << 16) | ((g0 + g1) << 8) | (b0 + b1);
        }
        else {
          return (t0 << 24) | (r0 << 16) | (g0 << 8) | (b0);
        }
      }
      else {
        // was fully transparent...
        return dstRGB;
      }
    }
  }
}