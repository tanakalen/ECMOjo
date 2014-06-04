package edu.hawaii.jabsom.tri.ecmo.app.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

import king.lib.out.Error;
import king.lib.util.Translator;
import edu.hawaii.jabsom.tri.ecmo.app.model.Game;
import edu.hawaii.jabsom.tri.ecmo.app.model.goal.BaselineGoal;

/**
 * The time panel. 
 *
 * @author   Christoph Aschwanden
 * @since    September 4, 2008
 */
public class TimePanel extends JPanel implements Runnable {

  /** The font color. */
  private final Color textColor = new Color(0.25f, 0.25f, 0.25f);

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
    setLocation(400, 5);
    setSize(400, 80);
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
    
    // text color
    g.setColor(textColor);

    // only show when we have a baseline type goal
    if (game.getGoal() instanceof BaselineGoal) {
      String sBaseline = Translator.getString("label.Baseline[i18n]: BASELINE");
      BaselineGoal goal = (BaselineGoal)game.getGoal();
      boolean blink = (((System.nanoTime()) / 500000000) % 2) == 0;
      
      // draw depending on state
      if (goal.isInit(game)) {
        // blink "BASELINE" as needed
        g.setFont(g.getFont().deriveFont(Font.BOLD, 32f));
        if (blink) {
          g.setColor(Color.GRAY);
          g.drawString(sBaseline, 39, 32);
          g.drawString(sBaseline, 41, 32);
          g.drawString(sBaseline, 39, 34);
          g.drawString(sBaseline, 41, 34);
          g.setColor(Color.YELLOW);
          g.drawString(sBaseline, 40, 33);
          g.setColor(textColor);
        }       
        
        // draw time left as to when scenario actually starts
        g.setFont(g.getFont().deriveFont(Font.BOLD, 18f));
        int timeLeft = (int)((goal.getTimeInit() - game.getElapsedTime()) / 1000);
        if (timeLeft < 0) {
          timeLeft = 0;
        }
        String sStart = Translator.getString("text.Time2Start[i18n]: Time to Scenario Start")
                      + ": " + timeLeft
                      + Translator.getString("label.Sec[i18n]: s");
        g.drawString(sStart, 40, 60);
      }
      else {
        // draw time left when scenario ends
        g.setFont(g.getFont().deriveFont(Font.BOLD, 18f));
        int timeLeft = (int)((goal.getTimeLimit() - game.getElapsedTime()) / 1000);
        if (timeLeft < 0) {
          timeLeft = 0;
        }
        int hours = timeLeft / 3600;
        int minutes = (timeLeft / 60) % 60;
        int seconds = timeLeft % 60;
        //TODO: Localization for time?
        String text = hours + ":" + ((minutes < 10) ? "0" : "") + minutes
                            + ":" + ((seconds < 10) ? "0" : "") + seconds;
        String sTimeLeft = Translator.getString("text.TimeLeft[i18n]: Time Left")
                         + ": ";
        g.drawString(sTimeLeft + text, 163, 43);
      }
    }
  }
}
