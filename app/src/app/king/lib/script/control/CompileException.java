package king.lib.script.control;

/**
 * The exception.
 * 
 * @author noblemaster
 * @since November 3, 2009
 */
public class CompileException extends Exception {
 
  /** The line number. */
  private long line;
  
  
  /**
   * Constructor for exception.
   * 
   * @param message  The message.
   */
  public CompileException(String message) {
    this(message, 0); 
  }
  
  /**
   * Constructor for exception.
   * 
   * @param message  The message.
   * @param line  The line number starting with 1. 0 if there was no line number.
   */
  public CompileException(String message, long line) {
    super(message); 
    this.line = line;
  }
  
  /**
   * Constructor for exception.
   * 
   * @param message  The message.
   * @param cause  The cause
   */
  public CompileException(String message, Throwable cause) {
    super(message, cause); 
    this.line = 0;
  }
  
  /**
   * Constructor for exception.
   * 
   * @param cause  The cause
   */
  public CompileException(Throwable cause) {
    super(cause); 
    this.line = 0;
  }

  /**
   * Returns the line number.
   * 
   * @return  The line number starting with 1. 0 if there was no line number.
   */
  public long getLine() {
    return line;
  }
}
