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
 * Saturation Composite.
 * 
 * @author Christoph Aschwanden
 * @since December 29, 2007
 */
public final class SaturationComposite extends RGBComposite {

  /**
   * Constructor.
   * 
   * @param alpha  The alpha level.
   */
  public SaturationComposite(float alpha) {
    super(alpha);
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
    return new Context(extraAlpha, srcColorModel, dstColorModel);
  }

  /**
   * The context.
   */
  static class Context extends RGBCompositeContext {

    /** Temp source HSB storage. */
    private float[] srcHSB = new float[3];

    
    /**
     * Constructor for context.
     * 
     * @param alpha  The alpha level.
     * @param srcColorModel  The source color model.
     * @param dstColorModel  The destination color model.
     */
    public Context(float alpha, ColorModel srcColorModel, ColorModel dstColorModel) {
      super(alpha, srcColorModel, dstColorModel);
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
      int opacity = (srcRGB >> 24) & 0xff;
      if (opacity > 0) {
        int r0 = (srcRGB >> 16) & 0xff;
        int g0 = (srcRGB >> 8) & 0xff;
        int b0 = (srcRGB) & 0xff;     
        
        Color.RGBtoHSB(r0, g0, b0, srcHSB);
        srcHSB[1] = alpha;
        int newRGB = Color.HSBtoRGB(srcHSB[0], srcHSB[1], srcHSB[2]);
        return 0xff000000 | newRGB;
      }
      else {
        // was transparent...
        return dstRGB;
      }
    }
  }
}