package edu.hawaii.jabsom.tri.ecmo.app.gui;

import java.awt.Color;
import java.awt.Graphics;

import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JToggleButton;

/**
 * Represents an textual type toggle button. 
 *
 * @author    king 
 * @since     August 22, 2007
 */
public class TextToggleButton extends JToggleButton {
  
  /** Text for button to color. */
  private String text;
  /** Color for normal state. */
  private Color normal;
  /** Color for rollover non toggle state. */
  private Color rolloverNonToggle;
  /** Color for rollover toggle state. */
  private Color rolloverToggle;
  /** Color for pressed state. */
  private Color pressed;
  
  /** The text y offset for normal. */
  private int normalYOffset;
  /** The text y offset for rollover non toggle. */
  private int rolloverNonToggleYOffset;
  /** The text y offset for rollover toggle. */
  private int rolloverToggleYOffset;
  /** The text y offset for pressed. */
  private int pressedYOffset;
  
  /** True to draw border. */
  boolean drawBorder = false;
  /** Border color. */
  private Color borderColor = Color.GRAY;
  /** True to draw shadow. */
  boolean drawShadow = true;
  
  /**
   * Builder for the button. Refer to Effective Java by Bloch.
   */
  public static class Builder {
    /** The text string for button, required. */
    private String text;
    /** The color of text on button. */
    private Color normal;
    /** The color of text with rollover in non-toggle state. */
    private Color rolloverNonToggle;
    /** The color of text with rollover in toggle state. */
    private Color rolloverToggle;
    /** The color of text when pressed. */
    private Color pressed;
    
    /**
     * Builder constructor with requirement of String for button.
     * @param item  Text for button.
     */
    public Builder(String item) {
      this.text = item;
    }
    
    /**
     * Build optional text color.
     * @param textColor  Color object for text.
     * @return Builder
     */
    public Builder normal(Color textColor) {
      this.normal = textColor;
      return this;
    }
    
    /**
     * Build optional text color when roll over in non-toggle state.
     * @param textColor  Color object for rollover of text in non-toggle state.
     * @return Builder
     */
    public Builder rolloverNonToggle(Color textColor) {
      this.rolloverNonToggle = textColor;
      return this;
    }
    
    /**
     * Build optional text color when roll over in Toggle state.
     * @param textColor  Color object for rollover of text in toggle state.
     * @return Builder
     */
    public Builder rolloverToggle(Color textColor) {
      this.rolloverToggle = textColor;
      return this;
    }
    
    /**
     * Build optional text color when mouse rolled over.
     * @param textColor  Color object for rollover of text.
     * @return Builder
     */
    public Builder rollover(Color textColor) {
      this.rolloverNonToggle = textColor;
      this.rolloverToggle = textColor;
      return this;
    }
    
    /**
     * Build optional text color when button pressed.
     * @param textColor  Color object for pressed button.
     * @return Builder
     */
    public Builder pressed(Color textColor) {
      this.pressed = textColor;
      return this;
    }
    
    /**
     * Lastly build function for Builder for TextToggleButton.
     * @return TextToggleButton
     */
    public TextToggleButton build() {
      return new TextToggleButton(this);
    }
  }
  
  /**
   * Constructor using builder for the button.
   * 
   * @param b  The Builder class for button
   */
  private TextToggleButton(Builder b) {
    this.text = b.text;
    this.normal = b.normal;
    this.rolloverNonToggle = b.rolloverNonToggle;
    this.rolloverToggle = b.rolloverToggle;
    this.pressed = b.pressed;
    
    this.normalYOffset = 0;
    this.rolloverNonToggleYOffset = 0;
    this.rolloverToggleYOffset = 0;
    this.pressedYOffset = 0;
    
    // set transparent
    setOpaque(false);
    setBorderPainted(false);
    
    setText(this.text);
    
    // Enable rollover
    setRolloverEnabled(true);
    
    //    this(b.text, b.normal, b.rolloverNonToggle, b.rolloverToggle, b.pressed);
  }
  
  /**
   * Constructor for the button.
   * 
   * @param text  The text for button to color.
   * @param normal  The color for the normal state.
   * @param rollover  The color for the rollover state.
   * @param pressed  The color for the pressed state.
   */
  public TextToggleButton(String text, Color normal, Color rollover, Color pressed) {
    this(text, normal, rollover, rollover, pressed);
  }
  
  /**
   * Constructor for the button.
   * 
   * @param text  The text for button to color.
   * @param normal  The color for the normal state.
   * @param rolloverNonToggle  The color for the rollover state.
   * @param rolloverToggle  The color for the rollover state.
   * @param pressed  The color for the pressed state.
   */
  public TextToggleButton(String text
                         , Color normal, Color rolloverNonToggle
                         , Color rolloverToggle, Color pressed) {
    this(text, normal, 0, rolloverNonToggle, 0, rolloverToggle, 0, pressed, 0);
  }
  
  /**
   * Constructor for the button.
   * 
   * @param text  The text for the button to color.
   * @param normal  The color for the normal state.
   * @param normalYOffset  Y offset for normal.
   * @param rolloverNonToggle  The color for the rollover non toggle state.
   * @param rolloverNonToggleYOffset  Y offset for rollover non toggle.
   * @param rolloverToggle  The color for the rollover toggle state.
   * @param rolloverToggleYOffset  Y offset for rollover toggle.
   * @param pressed  The color for the pressed state.
   * @param pressedYOffset  Y offset for pressed.
   */
  public TextToggleButton(String text
                         , Color normal, int normalYOffset
                         , Color rolloverNonToggle, int rolloverNonToggleYOffset
                         , Color rolloverToggle, int rolloverToggleYOffset
                         , Color pressed, int pressedYOffset) {
    this.text = text;
    this.normal = normal;
    this.normalYOffset = normalYOffset;
    this.rolloverNonToggle = rolloverNonToggle;
    this.rolloverNonToggleYOffset = rolloverNonToggleYOffset;
    this.rolloverToggle = rolloverToggle;
    this.rolloverToggleYOffset = rolloverToggleYOffset;
    this.pressed = pressed;
    this.pressedYOffset = pressedYOffset;
    
    // set transparent
    setOpaque(false);
    setBorderPainted(false);
    
    setText(this.text);
    
    // Enable rollover
    setRolloverEnabled(true);
  }

  /**
   * Draws this component.
   *
   * @param g  Graphics context.
   */
  public void paintComponent(Graphics g) {
//    super.paintComponent(g);
   
    int width = getSize().width;
    int height = getSize().height;
    
    // highlight depending on rollover or button pressed
    if (isEnabled()) {
      // g.setColor(getForeground());
      g.setColor(this.normal);
    }
    else {
      // g.setColor(getForeground().darker());
      g.setColor(this.normal.darker());
    }
    g.setFont(getFont());
    int textWidth = g.getFontMetrics().stringWidth(getText());
    int textHeight = g.getFontMetrics().getHeight();
    int ascent = g.getFontMetrics().getAscent();
    int x = (width - textWidth) / 2;
    int y = (height - textHeight) / 2 + ascent;
    
    Graphics2D g2 = (Graphics2D)g;
    // set antialiased
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING
                     , RenderingHints.VALUE_ANTIALIAS_ON);
//    Paint fillPaint = new GradientPaint(0, 0, gradientTopColor
//      , 0, getFont().getSize(), gradientBottomColor);
    
    if (model.isRollover()) {
      if (model.isSelected()) {
        if (drawBorder) {
          TextRenderer.renderOutline(g2, text, x, y + rolloverToggleYOffset
              , rolloverToggle, borderColor, drawShadow);
        } 
        else {
          TextRenderer.renderOutline(g2, text, x, y + rolloverToggleYOffset
              , rolloverToggle, null, drawShadow);
        }
      }
      else {
        if (drawBorder) {
          TextRenderer.renderOutline(g2, text, x, y + rolloverNonToggleYOffset
              , rolloverNonToggle, borderColor, drawShadow);
        } 
        else {
          TextRenderer.renderOutline(g2, text, x, y + rolloverNonToggleYOffset
              , rolloverNonToggle, null, drawShadow);
        }
      }
    }
    else if (model.isPressed() || isSelected()) {
      if (drawBorder) {
        TextRenderer.renderOutline(g2, text, x, y + pressedYOffset
            , pressed, borderColor, drawShadow);
      } 
      else {
        TextRenderer.renderOutline(g2, text, x, y + pressedYOffset
            , pressed, null, drawShadow);
      }
    }
    else {
      if (this.normal != null) {
      // Draws text, normal color.
        if (drawBorder) {
          TextRenderer.renderOutline(g2, text, x, y + normalYOffset
              , normal, borderColor, drawShadow);
        } 
        else {
          TextRenderer.renderOutline(g2, text, x, y + normalYOffset
              , normal, null, drawShadow);
        }
      }
    }
  }  
}
