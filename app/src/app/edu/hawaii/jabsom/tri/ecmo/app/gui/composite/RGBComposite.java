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
 * RGB Composite from http://www.koders.com/java/fid4FA09DE7FFB0427DC50EEE998278F4DB28F523A9.aspx.
 *
 * @author Christoph Aschwanden
 * @since December 17, 2007
 */
public abstract class RGBComposite implements Composite {

  /** The extra alpha value. */
  protected float extraAlpha;

  /**
   * Constructor.
   */
  public RGBComposite() {
    this(1.0f);
  }

  /**
   * Constructor.
   * 
   * @param alpha  The alpha level.
   */
  public RGBComposite(float alpha) {
    if (alpha < 0.0f || alpha > 1.0f) {
      throw new IllegalArgumentException("RGBComposite: alpha must be between 0 and 1");
    }
    this.extraAlpha = alpha;
  }

  /**
   * Returns alpha.
   * 
   * @return  Alpha level.
   */
  public float getAlpha() {
    return extraAlpha;
  }

  /**
   * Returns the hash code.
   * 
   * @return  The hash code.
   */
  public int hashCode() {
    return Float.floatToIntBits(extraAlpha);
  }

  /**
   * True if equal. 
   * 
   * @param object  The other object to compare this to.
   * @return  True for equal.
   */
  public boolean equals(Object object) {
    if (!(object instanceof RGBComposite)) {
      return false;
    }
    RGBComposite c = (RGBComposite)object;

    if (extraAlpha != c.extraAlpha) {
      return false;
    }
    return true;
  }

  /**
   * The context.
   */
  public abstract static class RGBCompositeContext implements CompositeContext {

    /** The alpha level. */
    private float alpha;
    /** The source model. */
    private ColorModel srcColorModel;
    /** The destination model. */
    private ColorModel dstColorModel;
    
    
    /**
     * Constructor for context.
     * 
     * @param alpha  The alpha level.
     * @param srcColorModel  The source color model.
     * @param dstColorModel  The destination color model.
     */
    public RGBCompositeContext(float alpha, ColorModel srcColorModel, ColorModel dstColorModel) {
      this.alpha = alpha;
      this.srcColorModel = srcColorModel;
      this.dstColorModel = dstColorModel;
    }

    /**
     * Dispose function. 
     */
    public void dispose() {
      // not used
    }

    /**
     * The composing function. 
     * 
     * @param srcRGB  The source RGB.
     * @param dstRGB  The pre-destination RGB.
     * @param alpha  The alpha level.
     * @return  The compbined destination RGB.
     */
    public abstract int composeRGB(int srcRGB, int dstRGB, float alpha);

    /**
     * Composer.
     * 
     * @param src  The source.
     * @param dstIn  The destination in.
     * @param dstOut  The destination out.
     */
    public void compose(Raster src, Raster dstIn, WritableRaster dstOut) {
      float alpha = this.alpha;

      int x0 = dstOut.getMinX();
      int x1 = x0 + dstOut.getWidth();
      int y0 = dstOut.getMinY();
      int y1 = y0 + dstOut.getHeight();

      for (int x = x0; x < x1; x++) {
        for (int y = y0; y < y1; y++) {
          int srcRGB = srcColorModel.getRGB(src.getDataElements(x, y, null));
          int dstInRGB = dstColorModel.getRGB(dstIn.getDataElements(x, y, null));
          int dstOutRGB = composeRGB(srcRGB, dstInRGB, alpha);
          dstOut.setDataElements(x, y, dstColorModel.getDataElements(dstOutRGB, null));
        }
      }  
    }
  }
}