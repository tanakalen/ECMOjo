package king.lib.notifier;

import java.lang.ref.WeakReference;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.Timer;

/**
 * Action listener that has a weak reference to the source action listener so it 
 * doesn't prevent it to be garbage collected. The calls to the actionPerformed 
 * are automatically propagated to the source action listener.
 *
 * @author Christoph Aschwanden (minor updates)
 * @author Miloslav Metelka
 * @version 1.0
 */
public class WeakTimerListener implements ActionListener {

  /** Weak reference to the source we check if it was garbage collected. */
  private WeakReference<ActionListener> sourceReference;

  /** If true, the timer will also be automatically stopped when garbage collection kicks in. */
  private boolean stopTimer;


  /** 
   * Construct new listener with automatic timer stopping.
   * 
   * @param source source action listener to which this listener delegates.
   */
  public WeakTimerListener(ActionListener source) {
    this(source, true);
  }

  /** 
   * Construct new listener.
   * 
   * @param source source action listener to which this listener delegates.
   * @param stopTimer whether the timer should be stopped automatically when
   *        the timer fires and the source listener was garbage collected.
   */
  public WeakTimerListener(ActionListener source, boolean stopTimer) {
    this.sourceReference = new WeakReference<ActionListener>(source);
    this.stopTimer = stopTimer;
  }

  /**
   * Action listener method which forwars messages to source or garbage collects itself
   * if no source anymore.
   * 
   * @param event  The event that occured.
   */
  public void actionPerformed(ActionEvent event) {
    ActionListener source = sourceReference.get();
    if (source != null) {
      // forward event
      source.actionPerformed(event);
    } 
    else { 
      // source listener was garbage collected
      if (event.getSource() instanceof Timer) {
        Timer timer = (Timer)event.getSource();
        timer.removeActionListener(this);

        // if auto-stop timer, then also stop timer
        if (stopTimer) {
          timer.stop();
        }
      }
    }
  }
}

