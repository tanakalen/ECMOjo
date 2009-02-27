package king.lib.out;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.GregorianCalendar;

/**
 * Template for output units. Output units output text to a defined target. Output units
 * are used for error, debug and info message.
 *
 * @author   king
 * @since    August 1, 2002
 */
public abstract class OutputUnit {

  /** True, if time should be outputted as well. */
  private boolean showTime = false;
  
  
  /**
   * Returns true, if time is outputted as well.
   * 
   * @return  True, for time outputted with message.
   */
  public boolean isShowTime() {
    return this.showTime;
  }
  
  /**
   * Sets if the time should be shown when outputting the message.
   * 
   * @param showTime  True, to also show the time.
   */
  public void setShowTime(boolean showTime) {
    this.showTime = showTime;
  }
  
  /**
   * Outputs a message.
   *
   * @param message  The message to output (plain form).
   */
  public void out(String message) {
    if (this.showTime) {
      // Current time
      GregorianCalendar currentTime = new GregorianCalendar(); 
      message = String.format("%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS", currentTime)
              + ": " + message;
    }
    
    // add line break
    message = message + "\n";
    
    // and output message
    formattedOut(message);
  }
  
  /**
   * Outputs the exception and stack trace.
   * 
   * @param e  The exception to output.
   */
  public void out(Exception e) {
    if (e != null) {
      StringWriter stringWriter = new StringWriter();
      PrintWriter printWriter = new PrintWriter(stringWriter);
      e.printStackTrace(printWriter);
      String message = e.getMessage() + " *** Stack Trace: " + stringWriter.toString();
      out(message);
    }
    else {
      out("Exception is \"null\"");
    }
  }
  
  /**
   * Outputs the formatted message.
   * 
   * @param message  The message to output (message already formatted).
   */
  protected abstract void formattedOut(String message);
}
