package king.lib.util;

import javax.swing.RepaintManager;
import javax.swing.JComponent;

/**
 * The NullRepaintManager is a RepaintManager that doesn't do any repainting.
 * Useful when all the rendering is done manually by the application.
 * 
 * @author Christoph Aschwanden
 * @since April 7, 2007
 */
public class NullRepaintManager extends RepaintManager {

  /**
   * Package private to prevent instantiation.
   */
  NullRepaintManager() {
    // not used
  }
  
  /**
   * Adds an invalid component.
   * 
   * @param c  The component to add.
   */
  public void addInvalidComponent(JComponent c) {
    // do nothing
  }

  /**
   * Adds a dirty region.
   * 
   * @param c  Component.
   * @param x  X.
   * @param y  Y.
   * @param w  Width.
   * @param h  Height.
   */
  public void addDirtyRegion(JComponent c, int x, int y, int w, int h) {
    // do nothing
  }

  /**
   * Mark dirty.
   * 
   * @param c  The component.
   */
  public void markCompletelyDirty(JComponent c) {
    // do nothing
  }

  /**
   * Paint dirty regions.
   */
  public void paintDirtyRegions() {
    // do nothing
  }
}