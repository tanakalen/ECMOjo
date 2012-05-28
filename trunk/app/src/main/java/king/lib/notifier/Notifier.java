package king.lib.notifier;

/**
 * Notifier interface which allows to add and remove
 * listeners. Allows to send out events/changes through this class to the registered
 * listeners. The listeners are implemented as weak references in the JVM model.
 *
 * Usage as interface:<p>
 * <code>
 * class Model implements Notifier<Event> {
 *   Notifier<Event> notifier = new NotifierAdapter<Event>();
 *   public void addNotifierListener(NotifierListener<Event> listener) {
 *     notifier.addNotifierListener(listener);
 *   }    
 *   public void removeNotifierListener(NotifierListener<Event> listener) {
 *     notifier.removeNotifierListener(listener);
 *   }    
 *   public void fire(Event event) {
 *     notifier.fire(event);
 *   }    
 * }
 * </code>
 * 
 * @param <E> What we are listening for.
 * 
 * @author  king
 * @since   December 1, 2004
 */
public interface Notifier<E> {
  
  /**
   * Adds a listener to this object. The listener will be added as weak reference.
   * 
   * @param listener  The listener to add.
   */
  void addNotifierListener(NotifierListener<E> listener);

  /**
   * Removes a listener from this object. Also clears out all the garbage 
   * colleced listeners.
   * 
   * @param listener  The listener to remove.
   */
  void removeNotifierListener(NotifierListener<E> listener);

  /**
   * Fires an object to all the registered listeners. Also clears out all the garbage 
   * colleced listeners.
   * 
   * @param event  The event to fire to all the registered listeners.
   */
  void fire(E event);
} 




