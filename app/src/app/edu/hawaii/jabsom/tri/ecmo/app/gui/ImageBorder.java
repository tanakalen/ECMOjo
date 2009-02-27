package edu.hawaii.jabsom.tri.ecmo.app.gui;

import javax.swing.border.Border;
import java.awt.Component;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.Insets;

/**
 * The image border. Requires the following slice configuration:
 * <pre>
 *  ------------------------------
 *  | 0     |  <- 1 ->  | 2      |  1 adjustable in width
 *  ------------------------------
 *  | 3  |                  |  4 |
 *  ------                  ------
 *  |    |                  |    |
 *  |    |                  |    |
 *  | 5  |                  |  6 |  5 and 6 adjustable too in height
 *  |    |                  |    |
 *  |    |                  |    |
 *  ------                  ------
 *  | 7  |                  |  8 |
 *  ------------------------------
 *  | 9     |  <- 10 ->  | 11    |  10 adjustable in width
 *  ------------------------------
 * </pre>
 *
 * @author    king 
 * @since     August 23, 2007
 */
public class ImageBorder implements Border {

  /** The slices. */
  private Image[] slices;
  
  
  /**
   * Constructor.
   * 
   * @param slices  The slices to use. Total number of slices is 12!
   */
  public ImageBorder(Image[] slices) {
    if (slices.length != 12) {
      throw new IllegalArgumentException("Wrong number of slices. 12 slices are required for image border.");
    }
    else {
      this.slices = slices;
    }
  }
  
  /**
   * Returns the border insets for the given component.
   * 
   * @param component  The component to return the insets for.
   * @return The insets.
   * @see javax.swing.border.Border#getBorderInsets(java.awt.Component)
   */
  public Insets getBorderInsets(Component component) {
    int top = slices[0].getHeight(component);
    int left = slices[3].getWidth(component);
    int bottom = slices[9].getHeight(component);
    int right = slices[4].getWidth(component);
    return new Insets(top, left, bottom, right);
  }

  /**
   * Return if border is opaque.
   * @see javax.swing.border.Border#isBorderOpaque()
   * @return  True, for opaque border (if opaque, needs to draw bg!).
   */
  public boolean isBorderOpaque() {
    return true;
  }

  /**
   * Paints the border.
   * 
   * @param c  The component.
   * @param g  Where to draw to.
   * @param x  The x position.
   * @param y  The y position.
   * @param width  The drawing with.
   * @param height  The drawing height.
   * @see javax.swing.border.Border#paintBorder(java.awt.Component, java.awt.Graphics, int, int, int, int)
   */
  public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
    int top = slices[0].getHeight(c);
    //int left = slices[3].getWidth(c); - not used
    int bottom = slices[9].getHeight(c);
    int right = slices[4].getWidth(c);
    
    // draw TOP horizontal slices
    int width0 = width - slices[0].getWidth(c) - slices[2].getWidth(c);
    for (int i = 0; i < width0; i += slices[1].getWidth(c)) {
      int w0 = slices[1].getWidth(c);
      int h0 = slices[1].getHeight(c);
      if ((i + w0) > width0) {
        w0 = width0 - i;
      }
      int x0 = x + slices[0].getWidth(c) + i;
      int y0 = y;
      g.drawImage(slices[1], x0, y0, x0 + w0, y0 + h0
                           , 0, 0, w0, h0
                           , c);
    }

    // draw BOTTOM horizontal slices
    width0 = width - slices[9].getWidth(c) - slices[11].getWidth(c);
    for (int i = 0; i < width0; i += slices[10].getWidth(c)) {
      int w0 = slices[10].getWidth(c);
      int h0 = slices[10].getHeight(c);
      if ((i + w0) > width0) {
        w0 = width0 - i;
      }
      int x0 = x + slices[9].getWidth(c) + i;
      int y0 = y + height - bottom;
      g.drawImage(slices[10], x0, y0, x0 + w0, y0 + h0
                            , 0, 0, w0, h0
                            , c);
    }
    
    // draw LEFT vertical slices
    int height0 = height - top - bottom - slices[3].getHeight(c) - slices[7].getHeight(c);
    for (int i = 0; i < height0; i += slices[5].getHeight(c)) {
      int w0 = slices[5].getWidth(c);
      int h0 = slices[5].getHeight(c);
      if ((i + h0) > height0) {
        h0 = height0 - i;
      }
      int x0 = x;
      int y0 = y + top + slices[3].getHeight(c) + i;
      g.drawImage(slices[5], x0, y0, x0 + w0, y0 + h0
                           , 0, 0, w0, h0
                           , c);
    }

    // draw RIGHT vertical slices
    height0 = height - top - bottom - slices[4].getHeight(c) - slices[8].getHeight(c);
    for (int i = 0; i < height0; i += slices[6].getHeight(c)) {
      int w0 = slices[6].getWidth(c);
      int h0 = slices[6].getHeight(c);
      if ((i + h0) > height0) {
        h0 = height0 - i;
      }
      int x0 = x + width - right;
      int y0 = y + top + slices[4].getHeight(c) + i;
      g.drawImage(slices[6], x0, y0, x0 + w0, y0 + h0
                           , 0, 0, w0, h0
                           , c);
    }

    // draw fixed top edges
    g.drawImage(slices[0], x, y, c);
    g.drawImage(slices[2], x + width - slices[2].getWidth(c), y, c);
    g.drawImage(slices[3], x, y + top, c);
    g.drawImage(slices[4], x + width - slices[4].getWidth(c), y + top, c);
    
    // draw fixed bottom edges
    g.drawImage(slices[7], x, y + height - slices[7].getHeight(c) - bottom, c);
    g.drawImage(slices[8], x + width - slices[8].getWidth(c), y + height - slices[8].getHeight(c) - bottom, c);
    g.drawImage(slices[9], x, y + height - slices[9].getHeight(c), c);
    g.drawImage(slices[11], x + width - slices[11].getWidth(c), y + height - slices[11].getHeight(c), c);
  }
}
