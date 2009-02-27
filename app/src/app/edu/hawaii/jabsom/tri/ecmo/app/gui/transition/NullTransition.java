package edu.hawaii.jabsom.tri.ecmo.app.gui.transition;

import java.awt.Component;
import java.awt.Graphics;

/**
 * The null transition which does nothing.
 *
 * @author   king
 * @since    October 3, 2008
 */
public class NullTransition implements Transition {

  /**
   * Inits the transition with the old component.
   * 
   * @param component  The old component.
   */
  public void init(Component component) {
    // not used
  }

  /**
   * Deinits the transition and cleans up resources.
   */
  public void deinit() {
    // not used
  }

  /**
   * Returns true if the transition has completed.
   * 
   * @return  True for completed.
   */
  public boolean isCompleted() {
    return true;
  }
  
  /**
   * Updates the transition. The transition has to keep track of time itself.
   */
  public void update() {
    // not used
  }

  /**
   * Draws the transition. The transition is drawn over the new component!
   * 
   * @param g  Where to draw to.
   */
  public void paint(Graphics g) {
    // not used
  }
}
