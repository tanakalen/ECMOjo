package king.lib.access;

import java.util.EventListener;

/**
 * Listener for audio events such as error during play that occured.
 * 
 * @author   king
 * @since    November 4, 2004
 */
public interface AudioExceptionListener extends EventListener {
 
  /**
   * Called when an audio exception occured.
   * 
   * @param audioException  The exception that has been thrown.
   */
  void audioExceptionThrown(AudioException audioException);
}
