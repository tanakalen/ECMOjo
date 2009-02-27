package edu.hawaii.jabsom.tri.ecmo.app.model.goal;

import edu.hawaii.jabsom.tri.ecmo.app.control.Action;
import edu.hawaii.jabsom.tri.ecmo.app.model.Game;

/**
 * Reset the numbers to baseline. 
 *
 * @author   king
 * @since    August 19, 2008
 */
public class BaselineGoal extends Goal {
  
  /** The time limit for the goal, i.e. when the goal is not reached. */
  private long timeLimit = Long.MAX_VALUE;
  
  
  /**
   * Returns the timeLimit.
   *
   * @return  The timeLimit.
   */
  public long getTimeLimit() {
    return timeLimit;
  }

  /**
   * Sets the timeLimit.
   *
   * @param timeLimit  The timeLimit.
   */
  public void setTimeLimit(long timeLimit) {
    this.timeLimit = timeLimit;
  }

  /**
   * Handles an action.
   * 
   * @param action  The action to handle.
   */
  @Override
  public void handle(Action action) {
    // not used
  }
  
  /**
   * Returns true if we reached a timeout.
   * 
   * @param game  The game.
   * @return  True for timeout.
   */
  public boolean isTimeout(Game game) {
    return game.getElapsedTime() > timeLimit;
  }
  
  /**
   * Returns true if we are back to baseline.
   * 
   * @param game  The game.
   * @return  True for baseline.
   */
  public boolean isBaseline(Game game) {
    return game.getBaseline().isReached(game);
  }
  
  /**
   * Returns true if the goal is reached.
   * 
   * @param game  The game.
   * @return  True for reached.
   */
  @Override
  public boolean isReached(Game game) {
    return (!game.getPatient().isAlive()) || (isTimeout(game)) || (isBaseline(game));
  }

  /**
   * Returns true if the "game" is "won". I.e. if the player succeeded.
   * 
   * @param game  The game.
   * @return  True for won.
   */
  @Override
  public boolean isWon(Game game) {
    return (game.getPatient().isAlive()) && (!isTimeout(game)) && (isBaseline(game));
  }

  /**
   * Returns the name of the component.
   * 
   * @return  The name.
   */
  @Override
  public String getName() {
    return "Reset to Baseline";
  }
}
