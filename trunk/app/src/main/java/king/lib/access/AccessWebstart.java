package king.lib.access;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URL;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipEntry;
import java.awt.Desktop;

/**
 * Provides basic functionality for webstart.
 *
 * @author   king
 * @since    April 23, 2003
 */
public class AccessWebstart extends Access {

  /** True, if browser init was called (if service could not be found). */
  private static boolean browserInitDone = false;

  /** The scenario dir for this application. */
  private String scenarioDir;
  /** The extra dir. */
  private String extraDir;
  /** The extra remote dir (remote URL). */
  private String extraRemoteDir;
  
  /**
   * Constructor for this class. Initializes everything to enable use
   * as webstart.
   * 
   * @throws AccessException  If something goes wrong instantiating webstart access.
   */
  public AccessWebstart() throws AccessException {
    // find the scenario dir
    this.scenarioDir = System.getProperty("user.home") 
                     + File.separator + System.getProperty("jnlp.webstart.dir")
                     + File.separator + "scenario";
    this.extraDir = System.getProperty("user.home") 
                     + File.separator + System.getProperty("jnlp.webstart.dir")
                     + File.separator + "extra";
    this.extraRemoteDir = System.getProperty("jnlp.webstart.extra.dir");
      
    // create extra dir
    File extraDir = new File(this.extraDir);
    extraDir.mkdirs();

    // try to find scenario.zip and install if required
    InputStream inputStream;
    try {
      inputStream = ResourceHookup.getInstance().getInputStream("scenario.zip");
    }
    catch (IOException e) {
      // If no input stream, then there is no scenario.zip to be deployed!
      return;
    }
    // unzip scenario file to local file system
    try {
      ZipInputStream zis = new ZipInputStream(new BufferedInputStream(inputStream));
      byte[] data = new byte[2048];
      ZipEntry entry;
      while ((entry = zis.getNextEntry()) != null) {
        String name = entry.getName();
        boolean overwrite = name.startsWith("write-over");
        String path = getScenarioDir() + "/" + name.substring(name.indexOf("/") + 1);
        if (entry.isDirectory()) {
          // create dirs if necessary
          File dir = new File(path);
          dir.mkdirs();
        }
        else {
          // create subdirs if necessary
          File file = new File(path);
          if ((!file.exists()) || (overwrite)) {
            // only copy if file doesn't exist
            File dir = new File(file.getParent());
            dir.mkdirs();
            // write the file to the disk
            OutputStream outputStream = new FileOutputStream(file);
            OutputStream fos = new BufferedOutputStream(outputStream, data.length);
            int count;
            while ((count = zis.read(data, 0, data.length)) != -1) {
              fos.write(data, 0, count);
            }
            fos.flush();
            fos.close();
          }
        }
      }
      zis.close();
    }
    catch (IOException e) {
      throw new AccessException("Cannot deploy scenario: " + e);
    }
  }

  /** 
   * Returns the scenario directory. Depending if started e.g. with web start (user.home)
   * or as application ("scenario").
   * 
   * @return  The scenario directory with the scenario files.
   */
  public String getScenarioDir() {
    return this.scenarioDir;
  }
  
  /**
   * Returns the extra directory. Depending if started e.g. with web start (user.home)
   * or as application ("extra").
   * 
   * @return  The extra directory with the extra files.
   */
  public final String getExtraDir() {
    return this.extraDir;
  }
  
  /**
   * Returns the remote location for extra files.
   * 
   * @return  Extra dir remote location or null if not available.
   */
  public String getExtraRemoteDir() {
    return this.extraRemoteDir;
  }
  
  /**
   * Returns the basic service object to e.g. open URLs for JNLP.
   * 
   * @return  The basic service object. or null if it wasn't found.
   */
  private Object getBasicServiceObject() {
    try {
      Class serviceManagerClass = Class.forName("javax.jnlp.ServiceManager");
      Method lookupMethod = serviceManagerClass.getMethod("lookup", new Class[] {String.class});
      return lookupMethod.invoke(null, new Object[] {"javax.jnlp.BasicService"});
    }
    catch (Exception e) {
      return null;
    }
  }
  
  /**
   * Opens the given URL in the browser.
   * 
   * @param url  The URL to open.
   * @throws AccessException  If something goes wrong.
   */
  public void openURL(String url) throws AccessException {
    // Try to open URL using javax.jnlp.BasicService 
    boolean urlOpened = false;
    Object basicServiceObject = getBasicServiceObject();
    if (basicServiceObject != null) {
      try {
        Method method = basicServiceObject.getClass().getMethod("showDocument", new Class[] {URL.class});
        urlOpened = (Boolean)method.invoke(basicServiceObject, new Object[] {new URL(url)});
      }
      catch (Exception e) {
        // urlOpened is still false...
      }
    }
    
    // if URL not opened yet, try alternative method
    if (!urlOpened) {
//      // init browser if not done yet
//      if (!browserInitDone) {
//        try {
//          // init browser code
//          Browser.init();
//          browserInitDone = true;
//        }
//        catch (Exception e) {
//          throw new AccessException("Error in browser init: " + e);
//        }
//      }    
  
      // open url
      try {
        Desktop.getDesktop().browse(new URI(url));
//        Browser.displayURL(url);
      }
      catch (Exception e) {
        throw new AccessException("Error opening URL in browser: " + e);
      }
    }
  }
}
