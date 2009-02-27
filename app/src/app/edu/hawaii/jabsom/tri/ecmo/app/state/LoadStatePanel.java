package edu.hawaii.jabsom.tri.ecmo.app.state;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import king.lib.access.ImageLoader;

/**
 * The load state panel. 
 *
 * @author   Christoph Aschwanden
 * @since    August 13, 2008
 */
public class LoadStatePanel extends JPanel implements Runnable {

  /** The panel image. */
  private Image background = ImageLoader.getInstance().getImage("conf/image/interface/load/Base.jpg");

  /** The state. */
  private LoadState state;
  
  /** The thread that updates the time. */
  private Thread thread;

  
  /**
   * Constructor for panel. 
   * 
   * @param state  The state for this panel.
   */
  public LoadStatePanel(LoadState state) {
    this.state = state;
    
    // set look
    setOpaque(true);
    
    // set layout
    setLayout(new BorderLayout());
    
    // start loader thread
    this.thread = new Thread(this);
    this.thread.start();
  }
  
  /**
   * Updater thread.
   */
  public void run() {
    // wait a while to show title
    try {
      Thread.sleep(500);
    }
    catch (InterruptedException e) {
      throw new IllegalThreadStateException(e.getMessage());
    }
    
    // proceed to the menu state
    state.menuState();
  }
  
  /**
   * Paints this component.
   * 
   * @param g  Where to draw to.
   */
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    
    // draws the image as background
    g.drawImage(background, 0, 0, this);
  }
}
