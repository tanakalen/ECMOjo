package edu.hawaii.jabsom.tri.ecmo.app.state;

import edu.hawaii.jabsom.tri.ecmo.app.model.Game;


/**
 * The result state. 
 *
 * @author   Christoph Aschwanden
 * @since    August 19, 2008
 */
public class HelpState extends State {

  /** The game. */
  private Game game;
  
  
  /**
   * Constructor for the state.
   */
  public HelpState() {
    this.game = game;
  }
  
  /** 
   * Called during state init.
   */
  void init() {

  }
  
  /**
   * Called after state deinit.
   */
  void deinit() {
    // not used
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
   * Proceeds to the menu state.
   */
  public void menuState() {
    transition(new MenuState());
  }
}
