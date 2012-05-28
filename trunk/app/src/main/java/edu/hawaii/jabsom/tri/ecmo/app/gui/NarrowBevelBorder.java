package edu.hawaii.jabsom.tri.ecmo.app.gui;

import javax.swing.border.Border;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;

/**
 * The narrow bevel border.
 *
 * @author    king 
 * @since     August 25, 2007
 */
public class NarrowBevelBorder implements Border {
  
  /** True for down. */
  private boolean down;
  
  
  /**
   * The bevel border.
   * 
   * @param down  True, for going down.
   */
  public NarrowBevelBorder(boolean down) {
    this.down = down;
  }
  
  /**
   * The bevel border. Defaults to down!
   */
  public NarrowBevelBorder() {
    this(true);
  }

  /**
   * Returns the border insets for the given component.
   * 
   * @param component  The component to return the insets for.
   * @return The insets.
   * @see javax.swing.border.Border#getBorderInsets(java.awt.Component)
   */
  public Insets getBorderInsets(Component component) {
    int top = 1;
    int left = 1;
    int bottom = 1;
    int right = 1;
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
    // top + left
    if (down) {
      g.setColor(c.getBackground().darker());
    }
    else {
      g.setColor(c.getBackground().brighter());
    }
    g.drawLine(x, y, x + width - 1, y);
    g.drawLine(x, y, x, y + height - 1);
    
    // bottom + right
    if (down) {
      g.setColor(c.getBackground().brighter());
    }
    else {
      g.setColor(c.getBackground().darker());
    }
    g.drawLine(x + width - 1, y, x + width - 1, y + height -1);
    g.drawLine(x, y + height -1, x + width - 1, y + height - 1);
  }
}
