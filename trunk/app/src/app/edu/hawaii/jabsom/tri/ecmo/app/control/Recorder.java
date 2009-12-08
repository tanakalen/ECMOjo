package edu.hawaii.jabsom.tri.ecmo.app.control;

import edu.hawaii.jabsom.tri.ecmo.app.model.Game;

/**
 * The recorder for scripting. 
 *
 * @author noblemaster
 * @since December 8, 2009
 */
public class Recorder {

  /** The game. */
  private Game game;

  
  /**
   * The constructor.
   *
   * @param game  The game.
   */
  public Recorder(Game game) {
    this.game = game; 
  }
  
  /**
   * Returns the game.
   *
   * @return  The game.
   */
  public Game getGame() {
    return game;
  }

  /**
   * Sets the game.
   *
   * @param game  The game.
   */
  public void setGame(Game game) {
    this.game = game;
  }
}
