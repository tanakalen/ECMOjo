package edu.hawaii.jabsom.tri.ecmo.app.gui.transition;

import java.awt.Component;
import java.awt.Graphics;

/**
 * The random transition uses any of the available transition effects.
 *
 * @author   king
 * @since    Jan 5, 2008
 */
public class RandomTransition implements Transition {

  /** The actual transition. */
  private Transition transition = new NullTransition();
  
  
  /**
   * Inits the transition with the old component.
   * 
   * @param component  The old component.
   */
  public void init(Component component) {
    // choose a random transition
    int random = (int)(Math.random() * 3);
    switch (random) {
      case 0:
        transition = new BubbleTransition();
        break;
      case 1:
        transition = new SlideTransition();
        break;
      case 2:
        transition = new ClockTransition();
        break;
      default:
        transition = new NullTransition();      
    }
    
    // do it...
    transition.init(component);
  }

  /**
   * Deinits the transition and cleans up resources.
   */
  public void deinit() {
    transition.deinit();
  }

  /**
   * Returns true if the transition has completed.
   * 
   * @return  True for completed.
   */
  public boolean isCompleted() {
    return transition.isCompleted();
  }
  
  /**
   * Updates the transition. The transition has to keep track of time itself.
   */
  public void update() {
    transition.update();
  }

  /**
   * Draws the transition. The transition is drawn over the new component!
   * 
   * @param g  Where to draw to.
   */
  public void paint(Graphics g) {
    transition.paint(g);
  }
}
