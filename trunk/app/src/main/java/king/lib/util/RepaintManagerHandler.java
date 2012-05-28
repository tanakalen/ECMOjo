package king.lib.util;

import javax.swing.RepaintManager;

/**
 * The handler for repaint managers available.
 * 
 * @author Christoph Aschwanden
 * @since September 21, 2007
 */
public final class RepaintManagerHandler {

  /** The previous manager. */
  private static RepaintManager previousRepaintManager = null;
  /** The current manager. */
  private static RepaintManager currentRepaintManager = null;
  
  
  /**
   * Private constructor to prevent instantiation.
   */
  private RepaintManagerHandler() {
    // not used
  }
  
  /**
   * Uninstalls the NullRepaintManger.
   */
  public static void installDefaultRepaintManager() {
    previousRepaintManager = currentRepaintManager;
    currentRepaintManager = new RepaintManager();
    RepaintManager.setCurrentManager(currentRepaintManager);
  }
  
  /**
   * Installs the NullRepaintManager.
   */
  public static void installThreadCheckingRepaintManager() {
    previousRepaintManager = currentRepaintManager;
    currentRepaintManager = new ThreadCheckingRepaintManager();
    RepaintManager.setCurrentManager(currentRepaintManager);
  }

  /**
   * Installs the NullRepaintManager.
   */
  public static void installNullRepaintManager() {
    previousRepaintManager = currentRepaintManager;
    currentRepaintManager = new NullRepaintManager();
    currentRepaintManager.setDoubleBufferingEnabled(false);
    RepaintManager.setCurrentManager(currentRepaintManager);
  }

  /**
   * Uninstalls the current and restores the previous repaint manager.
   */
  public static void uninstall() {
    currentRepaintManager = null;
    if (previousRepaintManager == null) {
      installDefaultRepaintManager();
    }
    else {
      // restore
      RepaintManager.setCurrentManager(previousRepaintManager);
      previousRepaintManager = null;
    }
  }
}