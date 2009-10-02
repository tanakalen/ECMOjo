package edu.hawaii.jabsom.tri.ecmo.app.model.goal;

import java.io.Serializable;

import edu.hawaii.jabsom.tri.ecmo.app.control.Action;
import edu.hawaii.jabsom.tri.ecmo.app.model.Game;

/**
 * The goal. 
 *
 * @author   king
 * @since    August 20, 2008
 */
public abstract class Goal implements Serializable {
    
  /**
   * Returns the name of the test.
   * 
   * @return  The name.
   */
  public abstract String getName();

  /**
   * Handles an action.
   * 
   * @param game  The game.
   * @param action  The action to handle.
   */
  public abstract void handle(Game game, Action action);

  /**
   * Returns true if the goal is reached.
   * 
   * @param game  The game.
   * @return  True if goal has been reached.
   */
  public abstract boolean isReached(Game game);
  
  /**
   * Returns true if the "game" is "won". I.e. if the player succeeded.
   * 
   * @param game  The game.
   * @return  True for won.
   */
  public abstract boolean isWon(Game game);
  
  /**
   * Returns the textual representation of this component.
   * 
   * @return  The textual representation of this component.
   */
  public String toString() {
    return getName();
  }
}
