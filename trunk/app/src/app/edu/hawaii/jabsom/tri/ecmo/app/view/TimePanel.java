package edu.hawaii.jabsom.tri.ecmo.app.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

import king.lib.out.Error;

import edu.hawaii.jabsom.tri.ecmo.app.model.Game;

/**
 * The time panel. 
 *
 * @author   Christoph Aschwanden
 * @since    September 4, 2008
 */
public class TimePanel extends JPanel implements Runnable {

  /** The font color. */
  private final Color textColor = new Color(0.2f, 0.2f, 0.2f);

  /** The game. */
  private Game game;
  
  /** The updater thread. */
  private Thread thread;
  
  
  /**
   * Default constructor.
   * 
   * @param game  The game
   */
  public TimePanel(Game game) {
    this.game = game;
    
    // set transparent
    setOpaque(false);
    
    // set size and location
    setLocation(608, 15);
    setSize(128, 50);
  }

  /**
   * Called when panel is added.
   */
  @Override
  public void addNotify() {
    super.addNotify();
    
    // start thread
    this.thread = new Thread(this);
    this.thread.setPriority(Thread.MIN_PRIORITY);
    this.thread.start();
  }
  
  /**
   * Called when panel is removed.
   */
  @Override
  public void removeNotify() {
    // stop thread
    this.thread = null;
    
    super.removeNotify();
  }
  
  /**
   * The time updater thread.
   */
  public void run() {
    Thread currentThread = Thread.currentThread();
    while (this.thread == currentThread) {
      // update
      repaint();
      
      // wait for next update
      try {
        Thread.sleep(50);
      }
      catch (InterruptedException e) {
        Error.out(e);
      }
    }
  }
  
  /**
   * Paints this component.
   * 
   * @param g  Where to draw to.
   */
  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    // set antialised text
    Graphics2D g2 = (Graphics2D)g;
    g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                        RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

    // set text properties
    g.setColor(textColor);
    g.setFont(g.getFont().deriveFont(Font.BOLD, 24f));
    
    // draw text
    int time = (int)(game.getElapsedTime() / 1000);
    int hours = time / 3600;
    int minutes = (time / 60) % 60;
    int seconds = time % 60;
    String text = hours + " : " + ((minutes < 10) ? "0" : "") + minutes
                        + " : " + ((seconds < 10) ? "0" : "") + seconds;
    g.drawString(text, 10, 34);
  }
}
