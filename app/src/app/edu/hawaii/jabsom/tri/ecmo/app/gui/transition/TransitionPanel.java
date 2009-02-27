package edu.hawaii.jabsom.tri.ecmo.app.gui.transition;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.RepaintManager;

import king.lib.out.Error;

/**
 * The transition panel to fade in components. Use fadeIn(Component component).
 * <p>
 * Found on http://www.javagaming.org/index.php/topic,19243.0
 *
 * @author   king
 * @since    October 2, 2008
 */
public class TransitionPanel extends JPanel implements Runnable {

  /** The current component or null for none. */
  private Component component = null;

  /** The transition. */
  private Transition transition;
  
  /** The fade thread. */
  private Thread thread = null;
  
  
  /**
   * Constructor for fade panel.
   */
  public TransitionPanel() {
    // set layout
    setLayout(new BorderLayout());
    setOpaque(true);
    
    // set default transition
    transition = new NullTransition();
  }
  
  /**
   * Returns the transition.
   *
   * @return  The transition.
   */
  public Transition getTransition() {
    return transition;
  }

  /**
   * Sets the transition.
   *
   * @param transition  The transition to set.
   */
  public void setTransition(Transition transition) {
    this.transition = transition;
  }

  /**
   * Fades in a new component.
   * 
   * @param component  The component to fade in.
   */
  public synchronized void fadeIn(Component component) {
    if (this.component == null) {
      // add the new component
      add(component, BorderLayout.CENTER);
      this.component = component;
    }
    else {
      // wait for previous fade to finish
      while (this.thread != null) {
        try {
          Thread.sleep(25);
        }
        catch (InterruptedException e) {
          Error.out(e);
        }
      }
      
      // and init the transition
      transition.init(this);
      
      // put new component
      remove(this.component);
      add(component, BorderLayout.CENTER);
      this.component = component;
  
      // start fader
      if (!transition.isCompleted()) {
        this.thread = new Thread(this);
        this.thread.start();  
      }
      else {        
        // update view
        revalidate();
        repaint();
      }
    }
  }
  
  /**
   * The fade animation.
   */
  public void run() {
    final TransitionPanel transitionPanel = this;
    RepaintManager oldRepaintManager = RepaintManager.currentManager(this);
    RepaintManager.setCurrentManager(new RepaintManager() {
      public void addInvalidComponent(JComponent c) {
        super.addInvalidComponent(transitionPanel);
      }
      public void addDirtyRegion(JComponent c, int x, int y, int w, int h) {
        super.addDirtyRegion(transitionPanel, 0, 0, transitionPanel.getWidth(), transitionPanel.getHeight());
      }
      public void markCompletelyDirty(JComponent c) {
        super.markCompletelyDirty(transitionPanel);
      }
    });
    
    while (!transition.isCompleted()) {
      // update the transition
      transition.update();
      
      // update view
      revalidate();
      repaint();

      try {
        Thread.sleep(50);
      }
      catch (InterruptedException e) {
        Error.out(e);
      }
    }
    
    // restore original repaint manager
    RepaintManager.setCurrentManager(oldRepaintManager);

    // cleanup
    transition.deinit();
    thread = null;
    
    // and redraw
    revalidate();
    repaint();
  }
  
  /**
   * Draws this component.
   * 
   * @param g  Where to draw to.
   */
  public void paint(Graphics g) {
    super.paint(g);
    
    // draw transition
    if (!transition.isCompleted()) {
      transition.paint(g);
    }
  }
}
