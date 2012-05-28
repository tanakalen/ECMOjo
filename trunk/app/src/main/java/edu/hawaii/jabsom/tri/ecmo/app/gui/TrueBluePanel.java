package edu.hawaii.jabsom.tri.ecmo.app.gui;

import java.awt.Color;

import javax.swing.JPanel;

/**
 * The true blue panel.
 * 
 * @author king
 * @since January 10, 2008
 */
public class TrueBluePanel extends JPanel {

  /** The background color. */
  private final Color backgroundColor = new Color(0xAAEBFF);
  
  /** The border. */
  private TrueBlueBorder border;
  
  
  /**
   * Default constructor.
   */
  public TrueBluePanel() {
    this(false);
  }
  
  /**
   * The true blue panel.
   * 
   * @param vertical  True for vertical orientation.
   */
  public TrueBluePanel(boolean vertical) {
    // set looks
    setOpaque(true);
    setLayout(null);
    setBackground(backgroundColor);
    
    // set border
    border = new TrueBlueBorder(vertical);
    setBorder(border);
  }
}
