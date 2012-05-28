package king.lib.access;

/**
 * Exception thrown if something goes wrong during access operations.
 * 
 * @author   king
 * @since    November 2, 2004
 */
public class AccessException extends Exception {
 
  /**
   * The textual representation of what went wrong.
   * 
   * @param text  Describes what went wrong.
   */
  public AccessException(String text) {
    super(text);
  }
}
