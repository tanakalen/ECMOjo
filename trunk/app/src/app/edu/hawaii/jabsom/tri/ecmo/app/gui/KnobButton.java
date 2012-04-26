package edu.hawaii.jabsom.tri.ecmo.app.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

/**
 * Represents an image panel with image background. 
 *
 * @author    king 
 * @since     August 25, 2007
 */
public class KnobButton extends JPanel implements MouseListener, MouseMotionListener {
  
  /** Listener for button updates. */
  public static interface RotationListener {
    
    /**
     * Called when the rotation changed.
     * 
     * @param angle  The angle.
     * @param valueAdjusting  True if the value is still adjusting.
     */
    void handleRotation(double angle, boolean valueAdjusting);
  };
  
  /** Listeners for changes. */
  private List<RotationListener> listeners = new ArrayList<RotationListener>();

  /** Image for normal state. */
  private Image knobImage;  
  /** Image for normal state. */
  private Image dialImage;  
  /** Image for rollover state. */
  private Image rolloverImage;
  
  /** The pressed offset. */
  private int offset;
  /** The radius. */
  private int radius;
  
  /** True for rollover. */
  private boolean rollover = false;
  /** True for pressed. */
  private boolean pressed = false;
  
  /** The last X. */
  private int lastX;
  /** The last Y. */
  private int lastY;
  
  /** The angle. */
  private double angle = 0.0;
  /** The minimum angle. */
  private double minAngle = Double.MIN_VALUE;
  /** The maximum angle. */
  private double maxAngle = Double.MAX_VALUE;
  
  
  /**
   * Constructor for the button.
   * 
   * @param knobImage  The knob image.
   * @param dialImage  The dial image.
   * @param rolloverImage  The rollover image.
   * @param offset  The offset if pressed.
   * @param radius  The radius.
   */
  public KnobButton(Image knobImage, Image dialImage, Image rolloverImage, int offset, int radius) {
    this.knobImage = knobImage;
    this.dialImage = dialImage;
    this.rolloverImage = rolloverImage;
    this.offset = offset;
    this.radius = radius;
    
    // set transparent
    setOpaque(false);

    // set the size based on image
    Dimension size = new Dimension(knobImage.getWidth(this), knobImage.getHeight(this));
    setSize(size);
    setPreferredSize(size);
    setMinimumSize(size);
    setMaximumSize(size);
    
    // add listeners
    addMouseListener(this);
    addMouseMotionListener(this);
  }
  
  /**
   * Returns the angle. 
   *
   * @return  The angle in radians [-infinity, infinity]. 2 * PI represents a full turn.
   */
  public double getAngle() {
    return angle;
  }

  /**
   * Sets the angle.
   *
   * @param angle  The angle in radians [-infinity, infinity]. 2 * PI represents a full turn.
   */
  public void setAngle(double angle) {
    this.angle = angle;
    
    if (angle < minAngle) {
      this.angle = minAngle;
    }
    else if (angle > maxAngle) {
      this.angle = maxAngle;
    }
  }
  
  /**
   * Returns the max angle.
   *
   * @return  The max angle in radians [-infinity, infinity]. 2 * PI represents a full turn.
   */
  public double getMaxAngle() {
    return maxAngle;
  }

  /**
   * Sets the max angle.
   *
   * @param maxAngle  The max angle in radians [-infinity, infinity]. 2 * PI represents a full turn.
   */
  public void setMaxAngle(double maxAngle) {
    this.maxAngle = maxAngle;
  }

  /**
   * Returns the min angle.
   *
   * @return  The min angle in radians [-infinity, infinity]. 2 * PI represents a full turn.
   */
  public double getMinAngle() {
    return minAngle;
  }

  /**
   * Sets the min angle.
   *
   * @param minAngle  The min angle in radians [-infinity, infinity]. 2 * PI represents a full turn.
   */
  public void setMinAngle(double minAngle) {
    this.minAngle = minAngle;
  }

  /**
   * The mouse clicked.
   * 
   * @param event  The event.
   */
  public void mouseClicked(MouseEvent event) {
    // not used
  }

  /**
   * The mouse entered.
   * 
   * @param event  The event.
   */
  public void mouseEntered(MouseEvent event) {
    rollover = true;
    repaint();
  }

  /**
   * The mouse exited.
   * 
   * @param event  The event.
   */
  public void mouseExited(MouseEvent event) {
    rollover = false;
    repaint();
  }

  /**
   * The mouse pressed.
   * 
   * @param event  The event.
   */
  public void mousePressed(MouseEvent event) {
    pressed = true;
    
    // init the angle
    initAngle(event.getX(), event.getY());
  }
  
  /**
   * Inits the angle.
   * 
   * @param x  The mouse x coordinate.
   * @param y  The mouse y coordinate.
   */
  private void initAngle(int x, int y) {
    lastX = x - knobImage.getWidth(this) / 2;
    lastY = y - knobImage.getHeight(this) / 2;
    repaint();
  }

  /**
   * The mouse released.
   * 
   * @param event  The event.
   */
  public void mouseReleased(MouseEvent event) {
    pressed = false;
    
    // update the angle
    updateAngle(event.getX(), event.getY());
    
    // and notify
    for (RotationListener listener: listeners) {
      listener.handleRotation(angle, false);
    }
  }

  /**
   * The mouse released.
   * 
   * @param event  The event.
   */
  public void mouseDragged(MouseEvent event) {
    // update the angle
    updateAngle(event.getX(), event.getY());
    
    // and notify
    for (RotationListener listener: listeners) {
      listener.handleRotation(angle, true);
    }    
  }
  
  /**
   * Updates the angle.
   * 
   * @param x  The mouse x coordinate.
   * @param y  The mouse y coordinate.
   */
  private void updateAngle(int x, int y) {
    double angle1 = Math.atan2(lastY, lastX);
    x -= knobImage.getWidth(this) / 2;
    y -= knobImage.getHeight(this) / 2;
    double angle2 = Math.atan2(y, x);
    if (angle1 < 0 && angle2 > Math.PI / 2) {
      angle1 += 2 * Math.PI;
    }
    if (angle1 > Math.PI / 2 && angle2 < 0) {
      angle2 += 2 * Math.PI;
    }
    angle += angle2 - angle1;
    if (angle < minAngle) {
      angle = minAngle;
    }
    else if (angle > maxAngle) {
      angle = maxAngle;
    }
    lastX = x;
    lastY = y;
    repaint();
  }

  /**
   * The mouse released.
   * 
   * @param event  The event.
   */
  public void mouseMoved(MouseEvent event) {
    // not used       
  }
  
  /**
   * Draws this component.
   *
   * @param g  Graphics context.
   */
  public void paintComponent(Graphics g) {    
    // set antialised
    Graphics2D g2 = (Graphics2D)g;
    g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
    g2.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
    g2.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);

    // draw knob
    g2.drawImage(knobImage, 0, (pressed ? offset : 0), this);   
    
    // draw dial
    int tx = (int) (radius * Math.sin(angle)) + knobImage.getWidth(this) / 2;
    int ty = (int) (-radius * Math.cos(angle)) + knobImage.getHeight(this) / 2 + (pressed ? offset : 0);
    g2.drawImage(dialImage, tx - dialImage.getWidth(this) / 2, ty - dialImage.getHeight(this) / 2, this); 
    
    // determine if rollover
    if ((rollover) || (pressed)) {
      g2.drawImage(rolloverImage, 0, (pressed ? offset : 0), this);   
    }
  }
  
  /**
   * Adds a listener.
   * 
   * @param listener  The listener to add.
   */
  public void addRotationListener(RotationListener listener) {
    listeners.add(listener);
  }
  
  /**
   * Removes a listener.
   * 
   * @param listener  The listener to add.
   */
  public void removeRotationListener(RotationListener listener) {
    listeners.remove(listener);
  }
}
