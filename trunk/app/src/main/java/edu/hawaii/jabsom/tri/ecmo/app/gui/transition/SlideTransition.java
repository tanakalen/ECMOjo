package edu.hawaii.jabsom.tri.ecmo.app.gui.transition;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

/**
 * The slide-type transition.
 *
 * @author   kinwang
 * @since    December 5, 2008
 */
public class SlideTransition implements Transition {

  /** True, if the transition is completed. */
  private boolean completed;
  
  /** The transition image. */
  private BufferedImage image = null;
  /** The start time. */
  private long startTime;
  
  /** Coordinates of start line points. */
  private Point startPoint;


  /**
   * Inits the transition with the old component.
   * 
   * @param component  The old component.
   */
  public void init(Component component) {
    completed = false;
    
    // copy the current component image
    image = new BufferedImage(component.getWidth(), component.getHeight(), BufferedImage.TYPE_INT_ARGB);
    Graphics g = image.getGraphics();
    component.paint(g);
    g.dispose();
    
    // gaussian image blur
    int radius = 3;
    boolean horizontal = false;      
    int size = radius * 2 + 1;
    float[] data = new float[size];
    float sigma = radius / 3.0f;
    float twoSigmaSquare = 2.0f * sigma * sigma;
    float sigmaRoot = (float) Math.sqrt(twoSigmaSquare * Math.PI);
    float total = 0.0f;
    for (int i = -radius; i <= radius; i++) {
      float distance = i * i;
      int index = i + radius;
      data[index] = (float) Math.exp(-distance / twoSigmaSquare) / sigmaRoot;
      total += data[index];
    }
    for (int i = 0; i < data.length; i++) {
      data[i] /= total;
    }
    Kernel kernel = null;
    if (horizontal) {
      kernel = new Kernel(size, 1, data);
    } 
    else {
      kernel = new Kernel(1, size, data);
    }
    ConvolveOp filter = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
    image = filter.filter(image, null);
    
    // set parameters
    this.startPoint = new Point(0, 0); 

    // the start time
    startTime = System.nanoTime();
  }

  /**
   * Deinits the transition and cleans up resources.
   */
  public void deinit() {
    // cleanup image
    image = null;
  }

  /**
   * Returns true if the transition has completed.
   * 
   * @return  True for completed.
   */
  public boolean isCompleted() {
    return completed;
  }

  /** 
   * Updates the transition. The transition has to keep track of time itself.
   */
  public void update() {
    // calculate length
    int elapsed = (int)((System.nanoTime() - startTime) / 1000000);
    int length = (int)Math.pow(2, elapsed / 50);
    if (length > 2000) {
      completed = true;
    }
    
    // puts a layers of translucent brown paint on top
    Graphics2D g2 = (Graphics2D)image.getGraphics();
    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.05f));
    g2.setColor(new Color(0xa06219));
    g2.fillRect(0, 0, image.getWidth(), image.getHeight());

    // draw holes into image
    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING
                      , RenderingHints.VALUE_ANTIALIAS_ON);
    g2.setColor(Color.GRAY);
    
    g2.drawRect(startPoint.x, startPoint.y, length, 600);
    g2.setComposite(AlphaComposite.Clear);
    g2.fillRect(startPoint.x, startPoint.y, length, 600);
    g2.dispose();
  }
  
  /**
   * Draws the transition. The transition is drawn over the new component!
   * 
   * @param g  Where to draw to.
   */
  public void paint(Graphics g) {
    // draw image
    Image image = this.image;
    if (image != null) {
      g.drawImage(image, 0, 0, null);
    }
  }
}
