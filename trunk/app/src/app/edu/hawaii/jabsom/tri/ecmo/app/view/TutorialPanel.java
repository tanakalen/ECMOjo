package edu.hawaii.jabsom.tri.ecmo.app.view;

import java.awt.Graphics;

import javax.swing.JPanel;

import king.lib.out.Error;

import edu.hawaii.jabsom.tri.ecmo.app.model.goal.TutorialGoal;
import edu.hawaii.jabsom.tri.ecmo.app.model.goal.TutorialGoal.Item;
import edu.hawaii.jabsom.tri.ecmo.app.model.goal.TutorialGoal.TutorialListener;

/**
 * The tutorial overlay. 
 *
 * @author   Christoph Aschwanden
 * @since    Jan 12, 2009
 */
public class TutorialPanel extends JPanel implements TutorialListener, Runnable {

  /** The goal. */
  private TutorialGoal goal;
  
  /** The tutorial box. */
  private TutorialBox box;
  
  /** The updater thread. */
  private Thread thread;
  /** True for blink. */
  private boolean blink;
  
  
  /**
   * Constructor for panel. 
   * 
   * @param goal  The goal.
   */
  public TutorialPanel(final TutorialGoal goal) {
    this.goal = goal;
    
    // set look
    setOpaque(false);
    
    // no layout
    setLayout(null);

    // add the tutorial box
    box = new TutorialBox();
    box.addTutorialBoxListener(new TutorialBox.TutorialBoxListener() {
      public void handleAction() {
        // proceed to the next state
       goal.proceed();
      }
    });
    box.setLocation(293, 2);
    box.setSize(312, 76);
    add(box);
    box.setVisible(false);
    
    // no blinking for now
    blink = false;
    
    // update the box
    updateBox();
  }
  
  /**
   * Called when the component got updated.
   */
  public void handleUpdate() {
    repaint();
  }
    
  /**
   * Called when panel is added.
   */
  @Override
  public void addNotify() {
    super.addNotify();
        
    // add listener
    goal.addTutorialListener(this);
    
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
    
    // remove listener
    goal.removeTutorialListener(this);
    
    super.removeNotify();
  }
  
  /**
   * Called when the tutorial changed.
   */
  public void handleTutorial() {
    updateBox();
  }
  
  /**
   * Updates the tutorial box.
   */
  private void updateBox() {
    Item item = goal.getItem();
    if (item != null) {
      box.setText(item.getText());
      box.setShowNext(item.getTrigger() == null);
      box.setVisible(true);
      
      // make it blinking
      thread = new Thread(this);
      thread.start();
    }
    else {
      box.setVisible(false);
    }
    
    // let's update the display...
    repaint();
  }
  
  /**
   * The time updater thread.
   */
  public void run() {
    try {
      // blink 3 times
      for (int i = 0; i < 3; i++) {
        blink = true;
        repaint();
        Thread.sleep(500);
  
        blink = false;
        repaint();
        Thread.sleep(500);
      }
    }
    catch (InterruptedException e) {
      Error.out(e);
    }
  }
  
  /**
   * Paints this component.
   * 
   * @param g  Where to draw to.
   */
  @Override
  public void paintComponent(Graphics g) {
    Item item = goal.getItem();
    if (item != null) {
      box.setText(blink ? "" : item.getText());
      box.setShowNext(item.getTrigger() == null);
      box.setVisible(true);
    }
    else {
      box.setVisible(false);
    }
  }
}
