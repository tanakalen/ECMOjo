package edu.hawaii.jabsom.tri.ecmo.app.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JToggleButton;

/**
 * Represents an image type toggle button. 
 *
 * @author    king 
 * @since     August 22, 2007
 */
public class SwitchButton extends JToggleButton {
  
  /** Image for normal state. */
  private Image normalImage;
  /** Image for rollover state. */
  private Image rolloverImage;

  /** The pressed offset. */
  private int offset;

  /**
   * Constructor for the button.
   * 
   * @param normalImage  The image for the normal state.
   * @param rolloverImage  The rollover image.
   * @param offset  The offset if pressed.
   */
  public SwitchButton(Image normalImage, Image rolloverImage, int offset) {
    this.normalImage = normalImage;
    this.rolloverImage = rolloverImage;
    this.offset = offset;
    
    // set transparent
    setOpaque(false);
    setBorderPainted(false);
        
    // set the size based on image
    Dimension size = new Dimension(normalImage.getWidth(this), normalImage.getHeight(this));
    setSize(size);
    setPreferredSize(size);
    setMinimumSize(size);
    setMaximumSize(size);
    
    // Enable rollover
    setRolloverEnabled(true);
  }

  /**
   * Draws this component.
   *
   * @param g  Graphics context.
   */
  public void paintComponent(Graphics g) {
    Image paintImage;
    if (model.isRollover()) {
      paintImage = this.rolloverImage;  
    } 
    else {
      paintImage = this.normalImage;
    }
    
    if (model.isPressed() || isSelected()) {
      g.drawImage(paintImage, 0, 0, this);
    }
    else {
      g.drawImage(paintImage, 0, offset, this);
    }
  }  
}
