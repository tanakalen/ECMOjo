package king.lib.script.control;

/**
 * The exception.
 * 
 * @author  noblemaster
 * @since   October 21, 2009
 */
public class SandboxException extends Exception {
 
  /**
   * Constructor for exception.
   * 
   * @param message  The message.
   * @param cause  The cause
   */
  public SandboxException(String message, Throwable cause) {
    super(message, cause); 
  }
  
  /**
   * Constructor for exception.
   * 
   * @param cause  The cause
   */
  public SandboxException(Throwable cause) {
    super(cause); 
  }

  /**
   * Constructor for exception.
   * 
   * @param message  The message.
   */
  public SandboxException(String message) {
    super(message); 
  }
}
