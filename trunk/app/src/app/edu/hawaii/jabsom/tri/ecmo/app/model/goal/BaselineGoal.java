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
  
  /** The time init to show the baseline values. */
  private long timeInit = 30 * 1000;
  /** The time limit for the goal, i.e. when the goal is not reached. */
  private long timeLimit = Long.MAX_VALUE;
  
  
  /**
   * Returns the time init.
   *
   * @return  The time init in milliseconds.
   */
  public long getTimeInit() {
    return timeInit;
  }

  /**
   * Sets the time init.
   *
   * @param timeInit  The time init in milliseconds.
   */
  public void setTimeInit(long timeInit) {
    this.timeInit = timeInit;
  }

  /**
   * Returns the time limit.
   *
   * @return  The time limit in milliseconds.
   */
  public long getTimeLimit() {
    return timeLimit;
  }

  /**
   * Sets the time limit.
   *
   * @param timeLimit  The time limit in milliseconds.
   */
  public void setTimeLimit(long timeLimit) {
    this.timeLimit = timeLimit;
  }

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
   * Returns true if we are in the init phase.
   * 
   * @param game  The game.
   * @return  True for baseline init phase.
   */
  public boolean isInit(Game game) {
    return game.getElapsedTime() < timeInit;
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
    return (!isInit(game)) && ((!game.getPatient().isAlive()) || (isTimeout(game)) || (isBaseline(game)));
  }

  /**
   * Returns true if the "game" is "won". I.e. if the player succeeded.
   * 
   * @param game  The game.
   * @return  True for won.
   */
  @Override
  public boolean isWon(Game game) {
    return (!isInit(game)) && (isBaseline(game));
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
