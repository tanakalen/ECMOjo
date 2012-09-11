package edu.hawaii.jabsom.tri.ecmo.app.model.goal;

import edu.hawaii.jabsom.tri.ecmo.app.control.Action;
import edu.hawaii.jabsom.tri.ecmo.app.model.Game;

/**
 * Simulation run without an actual goal. 
 *
 * @author   king
 * @since    April 22, 2009
 */
public class SimulationGoal extends Goal {
  
  /**
   * Handles an action.
   * 
   * @param game  The game.
   * @param action  The action to handle.
   */
  @Override
  public void handle(Game game, Action action) {
    // not used
  }
    
  /**
   * Returns true if the goal is reached.
   * 
   * @param game  The game.
   * @return  True for reached.
   */
  @Override
  public boolean isReached(Game game) {
    return false;
  }

  /**
   * Returns true if the "game" is "won". I.e. if the player succeeded.
   * 
   * @param game  The game.
   * @return  True for won.
   */
  @Override
  public boolean isWon(Game game) {
    return false;
  }

  /**
   * Returns the name of the component.
   * 
   * @return  The name.
   */
  @Override
  public String getName() {
    return "Simulation";
  }
}
