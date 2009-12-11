package edu.hawaii.jabsom.tri.ecmo.app.control.script;

/**
 * A console output.
 *
 * @author noblemaster
 * @since December 11, 2009
 */
public class Output {

  /** The message. */
  private String message;
  /** True for error message. */
  private boolean error;
  
  
  /**
   * The default constructor.
   */
  public Output() {
    this(null, false);
  }
  
  /**
   * The constructor.
   *
   * @param message  The message.
   * @param error  True for error message.
   */
  public Output(String message, boolean error) {
    this.message = message;
    this.error = error;
  }
  
  /**
   * Returns the message.
   *
   * @return  The message.
   */
  public String getMessage() {
    return message;
  }
  
  /**
   * Sets the message.
   *
   * @param message  The message.
   */
  public void setMessage(String message) {
    this.message = message;
  }
  
  /**
   * Returns true for error.
   *
   * @return  True for error message.
   */
  public boolean isError() {
    return error;
  }
  
  /**
   * Set true for the error.
   *
   * @param error  True for error message.
   */
  public void setError(boolean error) {
    this.error = error;
  }
}
