package king.lib.access;

/**
 * Exception thrown if something goes wrong during audio operations such
 * as play audio.
 * 
 * @author   king
 * @since    November 4, 2004
 */
public class AudioException extends Exception {
 
  /**
   * The textual representation of what went wrong.
   * 
   * @param text  Describes what went wrong.
   */
  public AudioException(String text) {
    super(text);
  }
}
