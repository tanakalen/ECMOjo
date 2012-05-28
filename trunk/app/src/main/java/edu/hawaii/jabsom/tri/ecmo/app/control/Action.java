package edu.hawaii.jabsom.tri.ecmo.app.control;

import edu.hawaii.jabsom.tri.ecmo.app.model.Game;

/**
 * The action. 
 *
 * @author   king
 * @since    August 19, 2008
 */
public abstract class Action {

  /**
   * Executes the action on the game.
   * 
   * @param game  The game.
   */
  public abstract void execute(Game game);
  
  /**
   * Returns the string representation.
   * 
   * @return  The string representation.
   */
  public abstract String toString();
}
