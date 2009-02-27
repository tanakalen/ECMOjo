package king.lib.access;

import king.lib.browser.Browser;

/**
 * Provides basic functionality for applications.
 *
 * @author   king
 * @since    April 23, 2003
 */
public class AccessApplication extends Access {

  /** True, if browser init was called. */
  private static boolean browserInitDone = false;
  

  /** 
   * Returns the scenario directory. Depending if started e.g. with web start (user.home)
   * or as application ("scenario").
   * 
   * @return  The scenario directory with the scenario files.
   */
  public final String getScenarioDir() {
    // in relative path, so return as relative path.
    return "scenario";
  }
  
  /**
   * Returns the extra directory. Depending if started e.g. with web start (user.home)
   * or as application ("extra").
   * 
   * @return  The extra directory with the extra files.
   */
  public final String getExtraDir() {
    // in relative path, so return as relative path.
    return "extra";
  }
  
  /**
   * Returns the remote location for extra files.
   * 
   * @return  Extra dir remote location or null if not available.
   */
  public String getExtraRemoteDir() {
    return null;
  }
  
  /**
   * Opens the given URL in the browser.
   * 
   * @param url  The URL to open.
   * @throws AccessException  If something goes wrong.
   */
  public void openURL(String url) throws AccessException {
    // init browser if not done yet
    if (!browserInitDone) {
      try {
        // init browser code
        Browser.init();
        browserInitDone = true;
      }
      catch (Exception e) {
        throw new AccessException("Error in browser init: " + e);
      }
    }    

    // open url
    try {
      Browser.displayURL(url);
    }
    catch (Exception e) {
      throw new AccessException("Error opening URL in browser: " + e);
    }
  }
}
