package edu.hawaii.jabsom.tri.ecmo.app.report;

import java.io.IOException;

import edu.hawaii.jabsom.tri.ecmo.app.model.Game;
import edu.hawaii.jabsom.tri.ecmo.app.report.Mailer.Protocol;

/**
 * The email result reporter.
 * 
 * @author caschwan
 */
public class EmailResultReporter extends ResultReporter {

  /** Where to send the email to. */
  private String email;
  
  
  /**
   * Constructor for reporter.
   * 
   * @param email  The email address to send the results to.
   */
  public EmailResultReporter(String email) {
    this.email = email;
  }
  
  /**
   * Reports the results from the game inputted.
   * 
   * @param game  The game to report the results for.
   * @throws IOException  If something goes wrong.
   */
  public void report(Game game) throws IOException {
    // get the report
    String report = ResultReporter.getResult(game);
      
    // prepare the message
    String senderEmail = "ecmojo.tri@gmail.com";
    String senderName = "ECMOjo";
    String recipientEmail = email;
    String recipientName = "ECMOjo Recorder";
    String subject = "ECMOjo Report";
    String body = report;    
    String host = "smtp.gmail.com";
    int port = 465;
    Protocol protocol = Protocol.SSL;
    String user = "ecmojo.tri@gmail.com";
    String password = "ecmostud";
    
    // and send the message...
    try {
      Mailer.send(senderEmail, senderName
          , recipientEmail, recipientName
          , subject, body
          , host, port, protocol, user, password);
    }
    catch (Exception e) {
      throw new IOException("Error sending out report: " + e);
    }
  }
}
