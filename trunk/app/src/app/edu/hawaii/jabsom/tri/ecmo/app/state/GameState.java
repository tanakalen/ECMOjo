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

  /** The scenario. */
  protected Scenario scenario;
  /** The user. */
  protected String user;
  
  /** The manager. */
  private Manager manager;
  
  
  /** 
   * Constructor for game state.
   */
  protected GameState() {
    this(null, null);
  }
  
  /** 
   * Constructor for game state.
   * 
   * @param scenario  The scenario selected.
   * @param user  The user that is executing the game.
   */
  public GameState(Scenario scenario, String user) {
    this.scenario = scenario;
    this.user = user;
  }
  
  /** 
   * Called during state init.
   */
  void init() {
    // create the actual game
    Game game = new Game(scenario, user);
    
    // create and start the game manager
    manager = new Manager(game);
    manager.start();
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
