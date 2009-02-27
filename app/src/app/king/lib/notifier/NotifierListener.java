package king.lib.notifier;

/**
 * Listener for objects/events sent by notifier.
 *
 * @param <E> The change or event that occured.
 * 
 * @author  king
 * @since   December 1, 2004
 */
public interface NotifierListener<E> {

  /**
   * Called with changes that occured in the model.
   * 
   * @param event  The change/event that occured and has been sent.
   */
  void update(E event);
} 



