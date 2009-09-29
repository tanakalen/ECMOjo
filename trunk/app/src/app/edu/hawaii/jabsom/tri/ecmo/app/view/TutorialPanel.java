package edu.hawaii.jabsom.tri.ecmo.app.view;

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
    setSize(800, 600);
    
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
    box.setSize(452, 76);
    add(box);
    box.setVisible(false);
    
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
    
    // start blink thread
    thread = new Thread(this);
    thread.setPriority(Thread.MIN_PRIORITY);
    thread.start();
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
    }
    blink = true;
  }
  
  /**
   * The time updater thread.
   */
  public void run() {
    Thread currentThread = Thread.currentThread();
    while (this.thread == currentThread) {
      // the blink thread
      try {
        // blink 3 times
        if (blink) {
          String text = box.getText();
          for (int i = 0; i < 3; i++) {
            box.setText("");
            Thread.sleep(250);
      
            box.setText(text);
            Thread.sleep(350);
          }
          blink = false;
        }
        
        // wait
        Thread.sleep(50);
      }
      catch (InterruptedException e) {
        Error.out(e);
      }
    }
  }
}
