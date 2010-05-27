package king.lib.access;

/**
 * Provides basic functionality independant of GUI. Supported are Applications and
 * Java Web Start. Always call init(...) at program start to initialize this class.
 * 
 * @author   king
 * @since    April 23, 2003
 */
public abstract class Access {
  
  /** The class that returns basic access defined by abstract functions in this class. */
  private static Access access = null;

  
  /** 
   * Static "constructor" for this class. Initialization determines automatically if
   * Java web start or a regular application. Has to be called before using the Access
   * class.
   * 
   * @throws AccessException  If application couldn't be inited!
   */
  public static void init() throws AccessException {
    // find access based on type
    if (System.getProperty("webstart") != null) {
      access = new AccessWebstart();
    }
    else {
      access = new AccessApplication();
    }
  }

  /** 
   * Static "constructor" for this class. Use this method if you are running an applet.
   * 
   * @param applet  The applet.
   * @throws AccessException  If application couldn't be inited!
   */
  public static void init(java.applet.Applet applet) throws AccessException {
    access = new AccessApplet(applet);
  }

  /**
   * Returns an instance of this access object.
   * 
   * @return  The access object. There is only one access object in the system! Returns
   *          null if Access.init() wasn't called previously to initialize this 
   *          class.
   */
  public static Access getInstance() {
    return access;
  }
  
  /** 
   * Returns the scenario directory. Depending if started e.g. with web start (user.home)
   * or as application ("scenario").
   * 
   * @return  The scenario directory with the scenario files without "/" at the end.
   */
  public abstract String getScenarioDir();
  
  /**
   * Returns the extra directory. Depending if started e.g. with web start (user.home)
   * or as application ("extra").
   * 
   * @return  The extra directory with the extra files without "/" at the end.
   */
  public abstract String getExtraDir();
  
  /**
   * Returns the remote location for extra files.
   * 
   * @return  Extra dir remote location or null if not available.
   */
  public abstract String getExtraRemoteDir();
  
  /**
   * Opens the given URL in the browser.
   * 
   * @param url  The URL to open.
   * @throws AccessException  If something goes wrong.
   */
  public abstract void openURL(String url) throws AccessException;
}
