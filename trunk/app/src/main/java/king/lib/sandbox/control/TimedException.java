package king.lib.sandbox.control;

/**
 * The exception.
 * 
 * @author noblemaster
 * @since October 30, 2009
 */
public class TimedException extends Exception {
 
  /**
   * Constructor for exception.
   * 
   * @param message  The message.
   * @param cause  The cause
   */
  public TimedException(String message, Throwable cause) {
    super(message, cause); 
  }
  
  /**
   * Constructor for exception.
   * 
   * @param cause  The cause
   */
  public TimedException(Throwable cause) {
    super(cause); 
  }

  /**
   * Constructor for exception.
   * 
   * @param message  The message.
   */
  public TimedException(String message) {
    super(message); 
  }
}
