package king.lib.util;

import javax.swing.RepaintManager;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;

/**
 * The ThreadCheckingRepaintManager is a RepaintManager that checks if Swing stuff is
 * correctly executed on the EDT (event dispatch thread).
 * <p>
 * See code by Scott Delap for details: http://www.clientjava.com/blog/2004/08/20/1093059428000.html
 * 
 * @author Christoph Aschwanden
 * @since September 21, 2007
 */
public class ThreadCheckingRepaintManager extends RepaintManager {

  /**
   * Package private to prevent instantiation.
   */
  ThreadCheckingRepaintManager() {
    // not used
  }

  /**
   * Checks if code is correctly executed on the EDT (Swing event dispatch thread).
   */
  private void checkThread() {
    if (!SwingUtilities.isEventDispatchThread()) {
      // outputs error if current thread is not the EDT
      System.out.println("Wrong Thread (not EDT):");
      Thread.dumpStack();
    }
  }

  /**
   * Adds an invalid component.
   * 
   * @param component  The component to add.
   */
  public void addInvalidComponent(JComponent component) {
    checkThread();
    super.addInvalidComponent(component);
  }

  /**
   * Adds a dirty region.
   * 
   * @param component  Component.
   * @param x  X.
   * @param y  Y.
   * @param w  Width.
   * @param h  Height.
   */
  public void addDirtyRegion(JComponent component, int x, int y, int w, int h) {
    checkThread();
    super.addDirtyRegion(component, x, y, w, h);
  }
}