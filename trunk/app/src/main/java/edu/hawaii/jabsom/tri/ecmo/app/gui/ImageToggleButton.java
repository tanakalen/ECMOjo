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
public class ImageToggleButton extends JToggleButton {
  
  /** Image for normal state. */
  private Image normalImage;
  /** Image for rollover non toggle state. */
  private Image rolloverNonToggleImage;
  /** Image for rollover toggle state. */
  private Image rolloverToggleImage;
  /** Image for pressed state. */
  private Image pressedImage;
  
  /** The text y offset for normal. */
  private int normalYOffset;
  /** The text y offset for rollover non toggle. */
  private int rolloverNonToggleYOffset;
  /** The text y offset for rollover toggle. */
  private int rolloverToggleYOffset;
  /** The text y offset for pressed. */
  private int pressedYOffset;

  
  /**
   * Constructor for the button.
   * 
   * @param normalImage  The image for the normal state.
   * @param rolloverImage  The image for the rollover state.
   * @param pressedImage  The image for the pressed state.
   */
  public ImageToggleButton(Image normalImage, Image rolloverImage, Image pressedImage) {
    this(normalImage, rolloverImage, rolloverImage, pressedImage);
  }
  
  /**
   * Constructor for the button.
   * 
   * @param normalImage  The image for the normal state.
   * @param rolloverNonToggleImage  The image for the rollover state.
   * @param rolloverToggleImage  The image for the rollover state.
   * @param pressedImage  The image for the pressed state.
   */
  public ImageToggleButton(Image normalImage, Image rolloverNonToggleImage
                         , Image rolloverToggleImage, Image pressedImage) {
    this(normalImage, 0, rolloverNonToggleImage, 0, rolloverToggleImage, 0, pressedImage, 0);
  }
  
  /**
   * Constructor for the button.
   * 
   * @param normalImage  The image for the normal state.
   * @param normalYOffset  Y offset for normal.
   * @param rolloverNonToggleImage  The image for the rollover non toggle state.
   * @param rolloverNonToggleYOffset  Y offset for rollover non toggle.
   * @param rolloverToggleImage  The image for the rollover toggle state.
   * @param rolloverToggleYOffset  Y offset for rollover toggle.
   * @param pressedImage  The image for the pressed state.
   * @param pressedYOffset  Y offset for pressed.
   */
  public ImageToggleButton(Image normalImage, int normalYOffset
                         , Image rolloverNonToggleImage, int rolloverNonToggleYOffset
                         , Image rolloverToggleImage, int rolloverToggleYOffset
                         , Image pressedImage, int pressedYOffset) {
    this.normalImage = normalImage;
    this.normalYOffset = normalYOffset;
    this.rolloverNonToggleImage = rolloverNonToggleImage;
    this.rolloverNonToggleYOffset = rolloverNonToggleYOffset;
    this.rolloverToggleImage = rolloverToggleImage;
    this.rolloverToggleYOffset = rolloverToggleYOffset;
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
    //super.paintComponent(g);  //BUG: duplicates button text when uncommented.
   
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
    
    if (model.isRollover()) {
      if (model.isSelected()) {
        g.drawImage(this.rolloverToggleImage, (width - imageWidth) / 2, (height - imageHeight) / 2, this);
        g.drawString(getText(), x, y + rolloverToggleYOffset);
      }
      else {
        g.drawImage(this.rolloverNonToggleImage, (width - imageWidth) / 2, (height - imageHeight) / 2, this);
        g.drawString(getText(), x, y + rolloverNonToggleYOffset);
      }
    }
    else if (model.isPressed() || isSelected()) {
      g.drawImage(this.pressedImage, (width - imageWidth) / 2, (height - imageHeight) / 2, this);
      g.drawString(getText(), x, y + pressedYOffset);
    }
    else {
      if (this.normalImage != null) {
        g.drawImage(this.normalImage, (width - imageWidth) / 2, (height - imageHeight) / 2, this);
      }
      g.drawString(getText(), x, y + normalYOffset);
    }
  }  
}
