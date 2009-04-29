package edu.hawaii.jabsom.tri.ecmo.app.report;

import java.io.IOException;

import edu.hawaii.jabsom.tri.ecmo.app.model.Game;

/**
 * The result reporter.
 * 
 * @author caschwan
 */
public abstract class ResultReporter {

  /**
   * Reports the results from the game inputted.
   * 
   * @param game  The game to report the results for.
   * @throws IOException  If something goes wrong.
   */
  public abstract void report(Game game) throws IOException;
  
  /**
   * Returns a report in text format.
   * 
   * @param game  The game.
   * @return  The report in text format.
   */
  public static String getReport(Game game) {
    StringBuilder builder = new StringBuilder();
    String linebreak = "\n";
    
    // add overview
    builder.append("Scenario: ").append(game.getName()).append(linebreak);
    builder.append("Result: ").append(game.getGoal().isWon(game) ? "Success" : "Failure").append(linebreak);
    builder.append(linebreak);
    
    // add detail
    builder.append("Elapsed Time: ").append(game.getElapsedTime() / 1000).append("s").append(linebreak);
    builder.append("Patient: ").append(game.getPatient().isAlive() ? "Alive" : "Dead").append(linebreak);
    builder.append("Actions Executed: ").append(game.getActions().size()).append(linebreak);
    
    // and return the report ...
    return builder.toString();
  }
}
