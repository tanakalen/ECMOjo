package edu.hawaii.jabsom.tri.ecmo.app.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JToggleButton;

/**
 * Represents an image type toggle button.
 *
 * @author    Len Tanaka
 * @since     September 16, 2016
 */
public final class Toggle extends JToggleButton {

  /** Image for normal state. */
  private final Image normalImage;
  /** Image for rollover non toggle state. */
  private final Image rolloverNonToggleImage;
  /** Image for rollover toggle state. */
  private final Image rolloverToggleImage;
  /** Image for pressed state. */
  private final Image pressedImage;

  /** The text y offset for normal. */
  private final int normalYOffset;
  /** The text y offset for rollover non toggle. */
  private final int rolloverNonToggleYOffset;
  /** The text y offset for rollover toggle. */
  private final int rolloverToggleYOffset;
  /** The text y offset for pressed. */
  private final int pressedYOffset;

  /** The color of text. */
  private final Color normalColor;
  /** The color of text for rollover. */
  private final Color rollColor;
  /** The color of text when pressed. */
  private final Color pressColor;

  /**
   * Constructor for the button.
   *
   * @param builder  The builder for Toggle class.
   */
  private Toggle(ToggleBuilder builder) {
    this.normalImage = builder.normalImage;
    this.normalYOffset = builder.normalYOffset;
    this.rolloverNonToggleImage = builder.rolloverImage;
    this.rolloverNonToggleYOffset = builder.rolloverYOffset;
    this.rolloverToggleImage = builder.rolloverToggleImage;
    this.rolloverToggleYOffset = builder.rolloverToggleYOffset;
    this.pressedImage = builder.pressedImage;
    this.pressedYOffset = builder.pressedYOffset;
    this.normalColor = builder.nmlColor;
    this.rollColor = builder.rolColor;
    this.pressColor = builder.presColor;

    // set transparent
    setOpaque(false);
    setBorderPainted(false);

    // set the size based on image
    Dimension size = new Dimension(
            pressedImage.getWidth(this),
            pressedImage.getHeight(this));
    setSize(size);
    setPreferredSize(size);
    setMinimumSize(size);
    setMaximumSize(size);

    // Enable rollover
    setRolloverEnabled(true);
  }

  /**
   * Builder pattern for Toggle button.
   */  
  public static class ToggleBuilder {
    /** Image for normal state. */
    private Image normalImage;
    /** REQUIRED Image for rollover non toggle state. */
    private final Image rolloverImage;
    /** REQUIRED Image for rollover toggle state. */
    private final Image rolloverToggleImage;
    /** REQUIRED Image for pressed state. */
    private final Image pressedImage;

    /** The text y offset for normal. */
    private int normalYOffset;
    /** The text y offset for rollover non toggle. */
    private int rolloverYOffset;
    /** The text y offset for rollover toggle. */
    private int rolloverToggleYOffset;
    /** The text y offset for pressed. */
    private int pressedYOffset;
    
    /** The color of text for rollover. */
    private Color nmlColor;
    /** The color of text for rollover. */
    private Color rolColor;
    /** The color of text for rollover. */
    private Color presColor;
  
    /**
     * Build the toggle using builder pattern.
     * 
     * @param rol Image for rollover.
     * @param roltog Image for rollover toggled.
     * @param press Image for pressed.
     */
    public ToggleBuilder(Image rol, Image roltog, Image press) {
      this.rolloverImage = rol;
      this.rolloverToggleImage = roltog;
      this.pressedImage = press;
    }
    
    /**
     * Set optional image for normal state.
     * 
     * @param img image for normal state.
     * @return Toggle update class with normal image.
     */
    public ToggleBuilder normalImage(Image img) {
      this.normalImage = img;
      return this;
    }
    
    /**
     * Set optional Y offset for normal state.
     * 
     * @param offset num of pixels from top Y-axis.
     * @return Toggle update class with normal Y offset in pixels.
     */
    public ToggleBuilder normalYOffset(int offset) {
      this.normalYOffset = offset;
      return this;
    }
    
    /**
     * Set optional Y offset for rollover non-toggle state.
     * 
     * @param offset num of pixels from top Y-axis.
     * @return Toggle update class with rollover Y offset in pixels.
     */
    public ToggleBuilder rolloverYOffset(int offset) {
      this.rolloverYOffset = offset;
      return this;
    }
    
    /**
     * Set optional Y offset for rollover toggle state.
     * 
     * @param offset num of pixels from top Y-axis.
     * @return Toggle updated class with rollover Y offset in pixels.
     */
    public ToggleBuilder rolloverToggleYOffset(int offset) {
      this.rolloverToggleYOffset = offset;
      return this;
    }
    
    /**
     * Set optional Y offset for pressed state.
     * 
     * @param offset num of pixels from top Y-axis.
     * @return Toggle updated class with pressed Y offset in pixels.
     */
    public ToggleBuilder pressedYOffset(int offset) {
      this.pressedYOffset = offset;
      return this;
    }
    
    /**
     * Set optional normal text color.
     * 
     * @param color Set normal text color.
     * @return Toggle updated class with normal text color.
     */
    public ToggleBuilder normalColor(Color color) {
      this.nmlColor = color;
      return this;
    }
    
    /**
     * Set optional rollover color.
     * 
     * @param color Set rollover text color.
     * @return Toggle updated class with rollover color.
     */
    public ToggleBuilder rolloverColor(Color color) {
      this.rolColor = color;
      return this;
    }
    
    /**
     * Set optional pressed color.
     * 
     * @param color Set pressed button text color.
     * @return Toggle updated class with pressed color.
     */
    public ToggleBuilder pressedColor(Color color) {
      this.presColor = color;
      return this;
    }
    
    /**
     * build method for Toggle class.
     * 
     * @return Toggle from builder.
     */
    public Toggle build() {
      return new Toggle(this);
    }
  }

  /**
   * Draws this component.
   *
   * @param g  Graphics context.
   */
  @Override
  public void paintComponent(final Graphics g) {
    super.paintComponent(g);

    int width = getSize().width;
    int height = getSize().height;
    int imageWidth = this.pressedImage.getWidth(this);
    int imageHeight = this.pressedImage.getHeight(this);

    // highlight depending on rollover or button pressed
    if (isEnabled()) {
      g.setColor(normalColor);
    }
    else {
      g.setColor(normalColor.darker());
    }
    g.setFont(getFont());
    int textWidth = g.getFontMetrics().stringWidth(getText());
    int textHeight = g.getFontMetrics().getHeight();
    int ascent = g.getFontMetrics().getAscent();
    int x = (width - textWidth) / 2;
    int y = (height - textHeight) / 2 + ascent;

    if (this.normalImage != null) {
      g.drawImage(this.normalImage,
              (width - imageWidth) / 2,
              (height - imageHeight) / 2,
              this);
    }
    g.setColor(normalColor);
    g.drawString(getText(), x, y + normalYOffset);
    if (getModel().isRollover()) {
      if (getModel().isSelected()) {
        g.drawImage(this.rolloverToggleImage,
                (width - imageWidth) / 2,
                (height - imageHeight) / 2,
                this);
        g.setColor(pressColor);
        g.drawString(getText(), x, y + rolloverToggleYOffset);
      }
      else {
        g.drawImage(this.rolloverNonToggleImage,
                (width - imageWidth) / 2,
                (height - imageHeight) / 2,
                this);
        g.setColor(rollColor);
        g.drawString(getText(), x, y + rolloverNonToggleYOffset);
      }
    }
    else if (getModel().isPressed()) {
      g.drawImage(this.pressedImage,
              (width - imageWidth) / 2,
              (height - imageHeight) / 2,
              this);
      g.setColor(pressColor);
      g.drawString(getText(), x, y + pressedYOffset);
    }
    else {
      if (this.normalImage != null) {
        g.drawImage(this.normalImage,
                (width - imageWidth) / 2,
                (height - imageHeight) / 2,
                this);
      }
      g.setColor(normalColor);
      g.drawString(getText(), x, y + normalYOffset);
    }
  }
}
