package edu.hawaii.jabsom.tri.ecmo.app.state;

import edu.hawaii.jabsom.tri.ecmo.app.control.Manager;
import edu.hawaii.jabsom.tri.ecmo.app.model.Game;
import edu.hawaii.jabsom.tri.ecmo.app.model.Scenario;

/**
 * The game state. 
 *
 * @author   Christoph Aschwanden
 * @since    August 13, 2008
 */
public class GameState extends State {

  /** The manager. */
  private Manager manager;
  
  
  /** 
   * Constructor for game state.
   * 
   * @param scenario  The scenario selected.
   * @param user  The user that is executing the game.
   */
  public GameState(Scenario scenario, String user) {
    this(scenario, user, false);
  }
  
  /** 
   * Constructor for game state.
   * 
   * @param scenario  The scenario selected.
   * @param user  The user that is executing the game.
   * @param monitor  True to monitor simulation.
   */
  public GameState(Scenario scenario, String user, boolean monitor) {
    // create the actual game
    Game game = new Game(scenario, user);
    
    // create the game manager
    manager = new Manager(game);
  }
  
  /** 
   * Called during state init.
   */
  void init() {
    manager.start();
    
    // make it play
    manager.play();
  }
  
  /**
   * Called after state deinit.
   */
  void deinit() {
    manager.stop();
  }
  
  
  /**
   * Proceeds to the result state.
   */
  public void resultState() {
    transition(new ResultState(manager.getGame()));
  }

  /**
   * Proceeds to the menu state.
   */
  public void menuState() {
    transition(new MenuState());
  }

  /**
   * Returns the manager.
   * 
   * @return  The manager.
   */
  public Manager getManager() {
    return manager;
  }
}
