package edu.hawaii.jabsom.tri.ecmo.app.gui;

import javax.swing.border.Border;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;

/**
 * The true blue border.
 *
 * @author    king 
 * @since     January 10, 2008
 */
public class TrueBlueBorder implements Border {

  /** True for vertical orientation. */
  private boolean vertical;
  
  /** The blue effect. */
  private final Color darkBlue = new Color(0x41D9FF);
  /** The blue effect. */
  private final Color lightBlue = new Color(0x84E8FF);

  /** The blue effect. */
  private final Color blue0 = new Color(0x00B8F8);
  /** The blue effect. */
  private final Color blue1 = new Color(0x00B8F8);
  /** The blue effect. */
  private final Color blue2 = new Color(0x00A1E7);
  /** The blue effect. */
  private final Color blue3 = new Color(0x0079C4);

  /** The box effect. */
  private final Color boxDark0 = new Color(0x2C2D31);
  /** The box effect. */
  private final Color boxDark1 = new Color(0x3A3B3D);
  /** The box effect. */
  private final Color boxDark2 = new Color(0x565960);
  /** The box effect. */
  private final Color boxDark3 = new Color(0x3A3B3D);

  /** The light 3D effect. */
  private final Color effect3DLight = new Color(0xECEDEF);
  /** The light 3D effect. */
  private final Color effect3DDark = new Color(0x797E84);
  
  
  /**
   * Default constructor with horizontal orientation.
   */
  public TrueBlueBorder() {
    this(false);
  }
  
  /**
   * Constructor.
   * 
   * @param vertical  True for vertical.
   */
  public TrueBlueBorder(boolean vertical) {
    this.vertical = vertical;
  }
  
  /**
   * Returns the border insets for the given component.
   * 
   * @param component  The component to return the insets for.
   * @return The insets.
   * @see javax.swing.border.Border#getBorderInsets(java.awt.Component)
   */
  public Insets getBorderInsets(Component component) {
    int top = 3;
    int left = 12;
    int bottom = 14;
    int right = 3;
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
    if (vertical) {
      Graphics2D g2 = (Graphics2D)g;
      g2.rotate(-Math.PI / 2, x, y);
      g2.translate(1 - height, 0);
      
      int temp = width;
      width = height;
      height = temp;
    }
    
    // draw blue
    g.setColor(blue0);
    g.drawLine(x + 3,         y + 3,          x + width - 4, y + 3);

    g.setColor(darkBlue);
    g.drawLine(x + 3,         y + 4,          x + width - 4, y + 4);
    g.setColor(lightBlue);
    g.drawLine(x + 3,         y + 5,          x + width - 4, y + 5);
    g.setColor(darkBlue);
    g.drawLine(x + 3,         y + 6,          x + width - 4, y + 6);
    g.setColor(lightBlue);
    g.drawLine(x + 3,         y + 7,          x + width - 4, y + 7);
    g.setColor(darkBlue);
    g.drawLine(x + 3,         y + 8,          x + width - 4, y + 8);
    g.setColor(lightBlue);
    g.drawLine(x + 3,         y + 9,          x + width - 4, y + 9);
    g.setColor(darkBlue);
    g.drawLine(x + 3,         y + 10,         x + width - 4, y + 10);
    g.setColor(lightBlue);
    g.drawLine(x + 3,         y + 11,         x + width - 4, y + 11);

    g.setColor(lightBlue);
    g.drawLine(x + 3,         y + height - 14, x + width - 4, y + height - 14);
    g.setColor(darkBlue);
    g.drawLine(x + 3,         y + height - 13, x + width - 4, y + height - 13);
    g.setColor(lightBlue);
    g.drawLine(x + 3,         y + height - 12, x + width - 4, y + height - 12);
    g.setColor(darkBlue);
    g.drawLine(x + 3,         y + height - 11, x + width - 4, y + height - 11);
    g.setColor(lightBlue);
    g.drawLine(x + 3,         y + height - 10, x + width - 4, y + height - 10);
    g.setColor(darkBlue);
    g.drawLine(x + 3,         y + height - 9, x + width - 4, y + height - 9);
    g.setColor(lightBlue);
    g.drawLine(x + 3,         y + height - 8, x + width - 4, y + height - 8);
    g.setColor(darkBlue);
    g.drawLine(x + 3,         y + height - 7, x + width - 4, y + height - 7);

    g.setColor(blue1);
    g.drawLine(x + 3,         y + height - 6, x + width - 4, y + height - 6);
    g.setColor(blue2);
    g.drawLine(x + 3,         y + height - 5, x + width - 4, y + height - 5);
    g.setColor(blue3);
    g.drawLine(x + 3,         y + height - 4, x + width - 4, y + height - 4);

    // draw dark box (inside)
    g.setColor(boxDark0);
    g.drawLine(x + 1,         y + 1,          x + width - 2, y + 1);
    g.setColor(boxDark1);
    g.drawLine(x + 1,         y + 2,          x + width - 2, y + 2);
    g.setColor(boxDark2);
    g.drawLine(x + 1,         y + 3,          x + 1,         y + height - 4);
    g.drawLine(x + 2,         y + 3,          x + 2,         y + height - 4);
    g.drawLine(x + width - 3, y + 3,          x + width - 3, y + height - 4);
    g.drawLine(x + width - 2, y + 3,          x + width - 2, y + height - 4);
    g.setColor(boxDark3);
    g.drawLine(x + 1,         y + height - 3, x + width - 2, y + height - 3);
    g.drawLine(x + 1,         y + height - 2, x + width - 2, y + height - 2);
    
    // draw 3D effect (outside)
    g.setColor(effect3DLight);
    g.drawLine(x,             y + height - 1, x + width - 1, y + height - 1);
    g.drawLine(x + width - 1, y,              x + width - 1, y + height - 1);
    g.setColor(effect3DDark);
    g.drawLine(x,             y,              x,             y + height - 1);
    g.drawLine(x,             y,              x + width - 1, y);
    
    if (vertical) {
      Graphics2D g2 = (Graphics2D)g;
      g2.translate(-height, -width);
      g2.rotate(-Math.PI / 2, x, y);
    }
  }
}
