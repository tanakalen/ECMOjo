package edu.hawaii.jabsom.tri.ecmo.app.gui;

import java.awt.Container;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 * Represents a horizontal line. 
 *
 * @author    king 
 * @since     August 25, 2007
 */
public class HorizontalLine extends JPanel {
  
  /** True for 3d. */
  private boolean threeD;
  
  
  /**
   * Default constructor.
   */
  public HorizontalLine() {
    this(100);
  }
  
  /**
   * Constructor for line.
   * 
   * @param width  The width.
   */
  public HorizontalLine(int width) {
    this(width, true);
  }
  
  /**
   * Constructor for line.
   * 
   * @param threeD  True for 3D.
   */
  public HorizontalLine(boolean threeD) {
    this(100, threeD);
  }

  /**
   * Constructor.
   * 
   * @param width  The width.
   * @param threeD  True for 3D.
   */
  public HorizontalLine(int width, boolean threeD) {
    this.threeD = threeD;
    
    setLayout(null);
    setOpaque(true);
    setSize(width, 2);
  }

  /**
   * Draws this component.
   *
   * @param g  Graphics context.
   */
  public void paintComponent(Graphics g) {
    Container parent = getParent();
    if (parent != null) {
      g.setColor(parent.getBackground().darker());
      g.drawLine(0, 0, getWidth() - 1, 0);
      if (threeD) {
        g.setColor(parent.getBackground().brighter());
        g.drawLine(0, 1, getWidth() - 1, 1);
      }
    }
  }  
}
