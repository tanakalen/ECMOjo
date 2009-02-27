package edu.hawaii.jabsom.tri.ecmo.app.gui;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JButton;
import javax.swing.SwingUtilities;
import king.lib.out.Error;

/**
 * Represents a nice looking link text. 
 *
 * @author    king 
 * @since     October 24, 2006
 */
public class LinkButton extends JButton implements Runnable {
  
  /** The selected color. */
  private final Color linkSelectedColor = new Color(0x0000ff);
  /** The rollover color. */
  private final Color linkRolloverColor = new Color(0x0000ff);
  /** The normal color. */
  private final Color linkNormalColor = new Color(0x0000ff);
  /** The disabled color. */
  private final Color linkDisabledColor = new Color(0x0000ff);
  
  /** True, if blink color to be used. */
  private boolean blink = false;
  
  /** The blink thread or null if not active. */
  private Thread thread = null;
  
  
  /**
   * Constructor for the link.
   */
  public LinkButton() {
    // set background
    setOpaque(false);
    setBorder(null);
    
    // Enable rollover
    setRolloverEnabled(true);
  }

  /**
   * Returns the preferred size of this link.
   * 
   * @return  The preferred size.
   */
  public Dimension getPreferredSize() {
    FontMetrics metrics = getFontMetrics(getFont());
    int fontHeight = metrics.getHeight() + 6;
    int fontWidth = metrics.stringWidth(getText()) + 10;
    return new Dimension(fontWidth, fontHeight);
  }
  
  /**
   * Makes the button blink a couple of times.
   */
  public void startBlink() {
    this.thread = new Thread(this);
    this.thread.start();
  }
  
  /**
   * Stops the blinking.
   */
  public void stopBlink() {
    this.thread = null;
  }
  
  /** 
   * The blinking thread. 
   */
  public void run() {
    // the blink runner
    Runnable repainter = new Runnable() {
      public void run() {
        repaint();
      }
    };

    int numberOfBlinks = 7;    
    Thread currentThread = Thread.currentThread();
    while ((this.thread == currentThread) && (numberOfBlinks > 0)) {
      try {
        // change blink color
        blink = true;
        SwingUtilities.invokeAndWait(repainter);
                 
        // wait for blink change
        Thread.sleep(150);
        
        // change blink color
        blink = false;
        SwingUtilities.invokeAndWait(repainter);
        
        // wait for blink change
        Thread.sleep(150);
        
        numberOfBlinks--;
      }
      catch (InterruptedException e) {
        Error.out(e);
      }
      catch (InvocationTargetException e) {
        Error.out(e);
      }
    }
  }
  
  /**
   * Draws this component.
   *
   * @param g  Graphics context.
   */
  public void paintComponent(Graphics g) {
    int width = getWidth();
    int height = getHeight();
    
    // Use antialiased text
    Graphics2D g2 = (Graphics2D)g;
    g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                        RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);

    if (getIcon() != null) {
      // draw icon if available
      int iconWidth = getIcon().getIconWidth();
      int iconHeight = getIcon().getIconHeight();
      getIcon().paintIcon(this, g, (width - iconWidth) / 2, (height - iconHeight) / 2);
    }
    else {
      // Draw the button text
      FontMetrics metrics = g.getFontMetrics();
      int fontHeight = metrics.getHeight();
      int fontWidth = metrics.stringWidth(getText());
      int x = (width - fontWidth) / 2 + 1;
      int y = (height + fontHeight) / 2 - 3;
      
      if (model.isPressed() || isSelected()) {
        g.setColor(linkSelectedColor);
        g.drawLine(x, y + 2, x + fontWidth, y + 2);
      }
      else if (model.isRollover() || (blink && isEnabled())) {
        g.setColor(linkRolloverColor);
        g.drawLine(x, y + 2, x + fontWidth, y + 2);
      }
      else if (isEnabled()) {
        g.setColor(linkNormalColor);
      }
      else {
        g.setColor(linkDisabledColor);
      }
      g.drawString(getText(), x, y);
    }
  }  
}
