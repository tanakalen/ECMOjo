package edu.hawaii.jabsom.tri.ecmo.app.report;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Calendar;

import king.lib.util.Translator;
import edu.hawaii.jabsom.tri.ecmo.app.control.Action;
import edu.hawaii.jabsom.tri.ecmo.app.control.ActionList;
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
   * Returns an overview in HTML format.
   * 
   * @param game  The game.
   * @return  The report in HTML format.
   */
  public static String getHTMLOverview(Game game) {
    StringBuilder builder = new StringBuilder();
    String linebreak = "<br>";
    String sSuccess = "<font color=\"#00c000\">"
                    + Translator.getString("label.Success[i18n]: Success")
                    + "</font>";
    String sFail = "<font color=\"#f00000\">"
                 + Translator.getString("label.Failure[i18n]: Failure")
                 + "</font>";
    
    // add introduction
    builder.append("<u>");
    builder.append(Translator.getString(
      "text.ResultsIntro[i18n]: The following results were recorded while you "
        + "were running the selected ECMOjo scenario"));
    builder.append("</u>:").append(linebreak);
    builder.append(linebreak);
    
    // add overview
    builder.append("<b>");
    builder.append(Translator.getString("text.Scenario[i18n]: Scenario"));
    builder.append("</b>: ").append(game.getName()).append(linebreak);
    builder.append("<b>");
    builder.append(Translator.getString("text.Result[i18n]: Result"));
    builder.append("</b>: ");
    builder.append(game.getGoal().isWon(game) ? sSuccess
                                              : sFail)
                                              .append(linebreak);
    builder.append(linebreak);
    
    // add detail
    builder.append(Translator.getString("text.ElapsedTime[i18n]: Elapsed Time"));
    builder.append(": ");
    builder.append(game.getElapsedTime() / 1000);
    builder.append(Translator.getString("label.Sec[i18n]: s"));
    builder.append(linebreak);
    
    builder.append(Translator.getString("text.Patient[i18n]: Patient"));
    builder.append(": ");
    builder.append(game.getPatient().isAlive() 
        ? Translator.getString("label.Alive[i18n]: Alive") 
        : Translator.getString("label.Dead[i18n]: Dead"));
    builder.append(linebreak);
    
    builder.append(Translator.getString("text.ActionsExecuted[i18n]: Actions Executed"));
    builder.append(": ").append(game.getActions().size()).append(linebreak);
    
    // and return the overview ...
    return builder.toString();
  }

  /**
   * Returns an overview in text format.
   * 
   * @param game  The game.
   * @return  The report in text format.
   */
  public static String getOverview(Game game) {
    String overview = getHTMLOverview(game);
    overview = overview.replaceAll("<br>", "\n");
    overview = overview.replaceAll("<.*?>", "");
    return overview;
  }
  
  /**
   * Returns the detail result in text format.
   * 
   * @param game  The game.
   * @return  The detail result in text format.
   */
  public static String getResult(Game game) {
    StringBuilder builder = new StringBuilder();
    String linebreak = "\n";
    
    // add overview
    builder.append(getOverview(game));
    builder.append(linebreak);
   
    // add user info actions executed
    builder.append("User: ").append(game.getUser());
    builder.append(linebreak);
    builder.append("Date/Time: ").append(DateFormat.getInstance().format(Calendar.getInstance().getTime()));
    builder.append(linebreak);
    ActionList actions = game.getActions();
    for (Action action: actions) {
      builder.append(action.toString()).append(linebreak);
    }
    
    // and return the result ...
    return builder.toString();
  }
}
