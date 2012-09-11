package edu.hawaii.jabsom.tri.ecmo.app.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Color;

import javax.swing.*;
import king.lib.out.Error;

/**
 * JButton object that draws a scaled Arrow in one of the cardinal directions.
 * 
 * @author king
 * @since October 13, 2007
 */
public class ArrowButton extends JButton implements SwingConstants {
  
  /** The direction. */
  protected int direction;
  /** The shadow color. */
  private Color shadow;
  /** The dark shadow color. */
  private Color darkShadow;
  /** The highlight color. */
  private Color highlight;

  /**
   * Constructor for arrow button.
   * 
   * @param direction  The direction as in SwingConstants.NORTH, .SOUTH, .EAST and .WEST.
   * @param background  The background color.
   * @param shadow  The shadow color.
   * @param darkShadow  The dark shadow color.
   * @param highlight  The highlight color.
   */
  public ArrowButton(int direction, Color background, Color shadow, Color darkShadow, Color highlight) {
    super();
    setRequestFocusEnabled(false);
    setDirection(direction);
    setBackground(background);
    this.shadow = shadow;
    this.darkShadow = darkShadow;
    this.highlight = highlight;
  }

  /**
   * Constructor for arrow button.
   * 
   * @param direction  The direction as in SwingConstants.NORTH, .SOUTH, .EAST and .WEST.
   */
  public ArrowButton(int direction) {
    this(direction, UIManager.getColor("control")
                  , UIManager.getColor("controlShadow")
                  , UIManager.getColor("controlDkShadow")
                  , UIManager.getColor("controlLtHighlight"));
  }

  /**
   * Returns the arrow direction.
   * 
   * @return  The arrow direction.
   */
  public int getDirection() {
    return direction;
  }

  /**
   * Sets the arrow direction.
   * 
   * @param direction  The direction.
   */
  public void setDirection(int direction) {
    this.direction = direction;
  }

  /**
   * Paints the button.
   * 
   * @param g  Where to paint to.
   */
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    
    int w = getSize().width;
    int h = getSize().height;

    // Draw the arrow
    int size = Math.min((h - 4) / 3, (w - 4) / 3);
    size = Math.max(size, 2);
    paintTriangle(g, (w - size) / 2 + 1, (h - size) / 2, size, direction, isEnabled());
  }

  /**
   * Returns the preferred size.
   * 
   * @return  The preferred size.
   */
  public Dimension getPreferredSize() {
    return new Dimension(16, 16);
  }

  /**
   * Returns the minimum size.
   * 
   * @return  The minimum size.
   */
  public Dimension getMinimumSize() {
    return new Dimension(5, 5);
  }

  /**
   * Returns the maximum size.
   * 
   * @return  The maximum size.
   */
  public Dimension getMaximumSize() {
    return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
  }

  /**
   * Draws the triangle (arrow).
   * 
   * @param g  Where to draw to.
   * @param x  X position.
   * @param y  Y position.
   * @param size  The size.
   * @param direction  The direction.
   * @param isEnabled  True for enabled.
   */
  public void paintTriangle(Graphics g, int x, int y, int size, int direction, boolean isEnabled) {
    Color oldColor = g.getColor();
    int i = 0;
    int j = 0;
    size = Math.max(size, 2);
    int mid = (size / 2) - 1;

    g.translate(x, y);
    if (isEnabled) {
      g.setColor(darkShadow);
    }
    else {
      g.setColor(shadow);
    }
    
    switch (direction) {
      case NORTH:
        for (i = 0; i < size; i++) {
          g.drawLine(mid - i, i, mid + i, i);
        }
        if (!isEnabled) {
          g.setColor(highlight);
          g.drawLine(mid - i + 2, i, mid + i, i);
        }
        break;
        
      case SOUTH:
        if (!isEnabled) {
          g.translate(1, 1);
          g.setColor(highlight);
          for (i = size - 1; i >= 0; i--) {
            g.drawLine(mid - i, j, mid + i, j);
            j++;
          }
          g.translate(-1, -1);
          g.setColor(shadow);
        }
  
        j = 0;
        for (i = size - 1; i >= 0; i--) {
          g.drawLine(mid - i, j, mid + i, j);
          j++;
        }
        break;
        
      case WEST:
        mid++;
        for (i = 0; i < size; i++) {
          g.drawLine(i, mid - i, i, mid + i);
        }
        if (!isEnabled) {
          g.setColor(highlight);
          g.drawLine(i, mid - i + 2, i, mid + i);
        }
        break;
        
      case EAST:
        mid++;
        if (!isEnabled) {
          g.translate(1, 1);
          g.setColor(highlight);
          for (i = size - 1; i >= 0; i--) {
            g.drawLine(j, mid - i, j, mid + i);
            j++;
          }
          g.translate(-1, -1);
          g.setColor(shadow);
        }
  
        j = 0;
        for (i = size - 1; i >= 0; i--) {
          g.drawLine(j, mid - i, j, mid + i);
          j++;
        }
        break;
        
      default:
        Error.out("Illegal arrow button direction: " + direction);
    }
    g.translate(-x, -y);
    g.setColor(oldColor);
  }
}
