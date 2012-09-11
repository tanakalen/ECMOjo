package edu.hawaii.jabsom.tri.ecmo.app.gui.transition;

import java.awt.Component;
import java.awt.Graphics;

/**
 * The transition.
 *
 * @author   king
 * @since    October 3, 2008
 */
public interface Transition {

  /**
   * Inits the transition with the old component.
   * 
   * @param component  The old component.
   */
  void init(Component component);

  /**
   * Deinits the transition and cleans up resources.
   */
  void deinit();

  /**
   * Returns true if the transition has completed.
   * 
   * @return  True for completed.
   */
  boolean isCompleted();
  
  /**
   * Updates the transition. The transition has to keep track of time itself.
   */
  void update();
  
  /**
   * Draws the transition. The transition is drawn over the new component!
   * 
   * @param g  Where to draw to.
   */
  void paint(Graphics g);
}
