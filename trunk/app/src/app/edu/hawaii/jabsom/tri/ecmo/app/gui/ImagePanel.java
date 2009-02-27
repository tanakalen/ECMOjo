package edu.hawaii.jabsom.tri.ecmo.app.gui;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JPanel;

/**
 * Represents an image panel with image background. 
 *
 * @author    king 
 * @since     August 25, 2007
 */
public class ImagePanel extends JPanel {
  
  /** The image. */
  private Image image;
  
  
  /**
   * Constructor for the panel.
   * 
   * @param image  The image.
   */
  public ImagePanel(Image image) {
    this.image = image;
  }

  /**
   * Draws this component.
   *
   * @param g  Graphics context.
   */
  public void paintComponent(Graphics g) {
    g.drawImage(image, 0, 0, this);
  }  
}
