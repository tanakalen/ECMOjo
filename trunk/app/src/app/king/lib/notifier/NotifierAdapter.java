package king.lib.notifier;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.lang.ref.WeakReference;

/**
 * Implementation of the notifier interface. Contains and allows to add and remove
 * listeners. Allows to send out events/changes through this class to the registered
 * listeners. The listeners are implemented as weak references in the JVM model.
 * Elements of this class won't be serialized, even thought the class itself is
 * serializable.
 * 
 * @param <E> The change or event that occured.
 * 
 * @author  king
 * @since   December 1, 2004
 */
public class NotifierAdapter<E> implements Notifier<E> {

  /** The listeners to be notified about events/changes that occur. */
  private transient List<WeakReference<NotifierListener<E>>> listeners;

  
  /**
   * Constructor for notifier adapter.
   */
  public NotifierAdapter() {
    listeners = new CopyOnWriteArrayList<WeakReference<NotifierListener<E>>>();
  }
  
  /**
   * Adds a listener to this object. The listener will be added as weak reference.
   * 
   * @param listener  The listener to add.
   */
  public void addNotifierListener(NotifierListener<E> listener) {
    if (listener == null) {
      throw new NullPointerException("listener");
    }
    WeakReference<NotifierListener<E>> weakListenerReference = new WeakReference<NotifierListener<E>>(listener);
    this.listeners.add(weakListenerReference);
  } 

  /**
   * Removes a listener from this object. Also clears out all the garbage 
   * colleced listeners.
   * 
   * @param listener  The listener to remove.
   */
  public void removeNotifierListener(NotifierListener<E> listener) {
    if (listener == null) {
      throw new NullPointerException("listener");
    }
    for (WeakReference<NotifierListener<E>> weakListenerReference : listeners) {
      NotifierListener<E> weakListener = weakListenerReference.get();
      if ((weakListener == null) || (weakListener == listener)) {
        this.listeners.remove(weakListenerReference);
      }
    }
  } 

  /**
   * Fires an object to all the registered listeners. Also clears out all the garbage 
   * colleced listeners.
   * 
   * @param event  The event to fire to all the registered listeners.
   */
  public void fire(E event) {
    for (WeakReference<NotifierListener<E>> weakListenerReference : listeners) {
      NotifierListener<E> weakListener = weakListenerReference.get();
      if (weakListener == null) {
        this.listeners.remove(weakListenerReference);
      }
      else {
        weakListener.update(event);
      }
    }
  } 
} 




