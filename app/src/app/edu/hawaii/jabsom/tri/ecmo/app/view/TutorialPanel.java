package edu.hawaii.jabsom.tri.ecmo.app.view;

import java.awt.Component;
import java.awt.Container;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

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
  
  /** The glass pane. */
  private Component glassPane;
  /** The mouse listener. */
  private MouseListener mouseListener;
  /** The mouse motion listener. */
  private MouseMotionListener mouseMotionListener;
  /** True, if mouse click trapper is active. */
  private boolean mouseClickTrapperActive;
  
  
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

    // the mouse click trapper
    mouseListener = new MouseListener() {
      public void mouseClicked(MouseEvent event) {        
        redispatchMouseEvent(event);
      }
      public void mousePressed(MouseEvent event) {
        redispatchMouseEvent(event);
      }
      public void mouseReleased(MouseEvent event) {
        redispatchMouseEvent(event);
      }
      public void mouseEntered(MouseEvent event) {
        // do nothing
      }
      public void mouseExited(MouseEvent event) {
        // do nothing
      }           
    };
    mouseMotionListener = new MouseMotionListener() {
      public void mouseDragged(MouseEvent event) {
        redispatchMouseEvent(event);
      }
      public void mouseMoved(MouseEvent event) {
        redispatchMouseEvent(event);
      }    
    };
    mouseClickTrapperActive = false;
 
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
  }
  
  /**
   * Re-dispatches a mouse event.
   * 
   * @param event  The original mouse event.
   */
  private void redispatchMouseEvent(MouseEvent event) {
    // calculate container point
    Point glassPanePoint = event.getPoint();
    Container container = TutorialPanel.this;
    Point containerPoint = SwingUtilities.convertPoint(glassPane, glassPanePoint, container);

    // if we are over the next button etc., let's re-dispatch the event
    if (containerPoint.y < 300) {
      // Forward events over the check box.
      Component component = SwingUtilities.getDeepestComponentAt(container, containerPoint.x, containerPoint.y);
      Point componentPoint = SwingUtilities.convertPoint(glassPane, glassPanePoint, component);
      component.dispatchEvent(new MouseEvent(component, event.getID(), event.getWhen(), event.getModifiers()
                            , componentPoint.x, componentPoint.y, event.getClickCount(), event.isPopupTrigger()));
    }
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
    
    // add glass pane as needed
    glassPane = getRootPane().getGlassPane();
    updateBox();
  }
  
  /**
   * Called when panel is removed.
   */
  @Override
  public void removeNotify() {
    // remove listeners from glass pane
    glassPane.removeMouseListener(mouseListener);
    glassPane.removeMouseMotionListener(mouseMotionListener);
    glassPane.setVisible(false);
    
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
      boolean showNext = item.getTrigger() == null;
      
      // setup the box
      box.setText(item.getText());
      box.setShowNext(showNext);
      box.setVisible(true);
      blink = true;
      
      // prevent mouse clicks to pass through as needed
      if (showNext) {
        if (!mouseClickTrapperActive) {
          glassPane.addMouseListener(mouseListener);
          glassPane.addMouseMotionListener(mouseMotionListener);
          glassPane.setVisible(true);
          mouseClickTrapperActive = true;
        }
      }
      else {
        if (mouseClickTrapperActive) {
          mouseClickTrapperActive = false;
          glassPane.removeMouseListener(mouseListener);
          glassPane.removeMouseMotionListener(mouseMotionListener);
          glassPane.setVisible(false);
        }
      }
    }
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
          for (int i = 0; i < 3; i++) {
            box.setTextVisible(false);
            Thread.sleep(250);
      
            box.setTextVisible(true);
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
