package edu.hawaii.jabsom.tri.ecmo.app.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;

import javax.swing.JButton;

/**
 * Represents an image type button. 
 *
 * @author    king 
 * @since     August 22, 2007
 */
public class ImageButton extends JButton {
  
  /** Image for normal state. */
  private Image normalImage;
  /** Image for rollover state. */
  private Image rolloverImage;
  /** Image for pressed state. */
  private Image pressedImage;
  
  /** The text y offset for normal. */
  private int normalYOffset;
  /** The text y offset for rollover. */
  private int rolloverYOffset;
  /** The text y offset for pressed. */
  private int pressedYOffset;

  
  /**
   * Constructor for the button.
   * 
   * @param normalImage  The image for the normal state.
   * @param rolloverImage  The image for the rollover state.
   * @param pressedImage  The image for the pressed state.
   */
  public ImageButton(Image normalImage, Image rolloverImage, Image pressedImage) {
    this(normalImage, 0, rolloverImage, 0, pressedImage, 2);
  }
  
  /**
   * Constructor for the button.
   * 
   * @param normalImage  The image for the normal state.
   * @param normalYOffset  Y offset for normal.
   * @param rolloverImage  The image for the rollover state.
   * @param rolloverYOffset  Y offset for rollover.
   * @param pressedImage  The image for the pressed state.
   * @param pressedYOffset  Y offset for pressed.
   */
  public ImageButton(Image normalImage, int normalYOffset
                   , Image rolloverImage, int rolloverYOffset
                   , Image pressedImage, int pressedYOffset) {
    this.normalImage = normalImage;
    this.normalYOffset = normalYOffset;
    this.rolloverImage = rolloverImage;
    this.rolloverYOffset = rolloverYOffset;
    this.pressedImage = pressedImage;
    this.pressedYOffset = pressedYOffset;
    
    // set transparent
    setOpaque(false);
    setBorderPainted(false);
        
    // set the size based on image
    Dimension size = new Dimension(pressedImage.getWidth(this), pressedImage.getHeight(this));
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
    int width = getSize().width;
    int height = getSize().height;
    int imageWidth = this.pressedImage.getWidth(this);
    int imageHeight = this.pressedImage.getHeight(this);
    
    // highlight depending on rollover or button pressed
    if (isEnabled()) {
      g.setColor(getForeground());
    }
    else {
      g.setColor(getForeground().darker());
    }
    g.setFont(getFont());
    int textWidth = g.getFontMetrics().stringWidth(getText());
    int textHeight = g.getFontMetrics().getHeight();
    int ascent = g.getFontMetrics().getAscent();
    int x = (width - textWidth) / 2;
    int y = (height - textHeight) / 2 + ascent;
    
    // set antialised text
    Graphics2D g2 = (Graphics2D)g;
    g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                        RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    
    // text properties
    g2.setFont(g.getFont().deriveFont(Font.BOLD));
    g2.setColor(new Color(3, 174, 195)); // Or, webcolor (#03AEC3)
    
    if (model.isPressed() || isSelected()) {
      g.drawImage(this.pressedImage, (width - imageWidth) / 2, (height - imageHeight) / 2, this);
      g2.drawString(getText(), x, y + pressedYOffset);
    }
    else if (model.isRollover()) {
      g.drawImage(this.rolloverImage, (width - imageWidth) / 2, (height - imageHeight) / 2, this);
      g2.drawString(getText(), x, y + rolloverYOffset);
    }
    else {
      if (this.normalImage != null) {
        g.drawImage(this.normalImage, (width - imageWidth) / 2, (height - imageHeight) / 2, this);
      }
      g2.drawString(getText(), x, y + normalYOffset);
    }
  }  
}
