package edu.hawaii.jabsom.tri.ecmo.app.report;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

import king.lib.access.LocalHookup;
import edu.hawaii.jabsom.tri.ecmo.app.model.Game;

/**
 * The file result reporter.
 * 
 * @author caschwan
 */
public class FileResultReporter extends ResultReporter {

  /** The hookup. */
  private LocalHookup hookup;
  /** The file path. */
  private String path;
  
  
  /**
   * Constructor for reporter.
   * 
   * @param hookup  The hookup.
   * @param path  The path.
   */
  public FileResultReporter(LocalHookup hookup, String path) {
    this.hookup = hookup;
    this.path = path;
  }
  
  /**
   * Reports the results from the game inputted.
   * 
   * @param game  The game to report the results for.
   * @throws IOException  If something goes wrong.
   */
  public void report(Game game) throws IOException {
    BufferedWriter out = null;
    try {
      // get the report
      String report = ResultReporter.getResult(game);
      
      // and write the report
      out = new BufferedWriter(new OutputStreamWriter(hookup.getOutputStream(path)));
      out.write(report);
    }
    finally {
      if (out != null) {
        out.close();
        out = null;
      }
    }
  }
}
