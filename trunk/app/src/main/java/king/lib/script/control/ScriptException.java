package king.lib.script.control;

/**
 * The exception.
 * 
 * @author  noblemaster
 * @since   October 21, 2009
 */
public class ScriptException extends Exception {
 
  /**
   * Constructor for exception.
   * 
   * @param message  The message.
   * @param cause  The cause
   */
  public ScriptException(String message, Throwable cause) {
    super(message, cause); 
  }
  
  /**
   * Constructor for exception.
   * 
   * @param cause  The cause
   */
  public ScriptException(Throwable cause) {
    super(cause); 
  }

  /**
   * Constructor for exception.
   * 
   * @param message  The message.
   */
  public ScriptException(String message) {
    super(message); 
  }
}
