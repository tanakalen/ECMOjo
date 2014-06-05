package king.lib.access;

import java.applet.Applet;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Provides basic functionality for applet programs.
 *
 * @author   king
 * @since    January 10, 2007
 */
public class AccessApplet extends Access {
  
  /** The applet to be used for access to images etc. */
  private Applet applet;
  
  /** The scenario dir for this application. */
  private String scenarioDir;
  /** The extra dir. */
  private String extraDir;
  /** The extra remote dir (remote URL). */
  private String extraRemoteDir;

  
  /**
   * Constructor for this object. Enables access to applet functionality. Hides
   * the applet.
   * 
   * @param applet  The applet where to get access to images etc.
   * @throws AccessException  If something goes wrong.
   */
  public AccessApplet(Applet applet) throws AccessException {
    this.applet = applet;
    
    // find the scenario dir
    this.scenarioDir = System.getProperty("user.home") 
                     + File.separator + applet.getParameter("jnlp.webstart.dir")
                     + File.separator + "scenario";
    this.extraDir = System.getProperty("user.home") 
                     + File.separator + applet.getParameter("jnlp.webstart.dir")
                     + File.separator + "extra";
    this.extraRemoteDir = applet.getParameter("jnlp.webstart.extra.dir");
    
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
   * Opens the given URL in the browser.
   * 
   * @param url  The URL to open.
   * @throws AccessException  If something goes wrong.
   */
  public void openURL(String url) throws AccessException {
    try {
      applet.getAppletContext().showDocument(new URL(url));
    }
    catch (java.net.MalformedURLException e) {
      throw new AccessException("Malformed URL: " + e);
    }
  }
}
