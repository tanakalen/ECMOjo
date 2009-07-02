package edu.hawaii.jabsom.tri.ecmo.app.state;

import java.io.IOException;
import king.lib.out.Error;
import king.lib.access.Access;
import king.lib.access.LocalHookup;
import edu.hawaii.jabsom.tri.ecmo.app.model.Game;
import edu.hawaii.jabsom.tri.ecmo.app.report.FileResultReporter;

/**
 * The result state. 
 *
 * @author   Christoph Aschwanden
 * @since    August 19, 2008
 */
public class ResultState extends State {

  /** The game. */
  private Game game;
  
  
  /**
   * Constructor for the state.
   * 
   * @param game  The game.
   */
  public ResultState(Game game) {
    this.game = game;
  }
  
  /** 
   * Called during state init.
   */
  void init() {
    // report | save the score
    try {
      // to file
      String path = Access.getInstance().getScenarioDir() + "/scores/score-" + System.currentTimeMillis() + ".txt";
      FileResultReporter fileReporter = new FileResultReporter(LocalHookup.getInstance(), path);
      fileReporter.report(game);
      
      // to email
      // EmailResultReporter emailReporter = new EmailResultReporter("ecmojo.tri@gmail.com");
      // emailReporter.report(game);
    }
    catch (IOException e) {
      Error.out(e);
    }
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
