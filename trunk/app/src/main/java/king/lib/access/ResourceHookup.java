package king.lib.access;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.InputStream;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.jar.JarFile;

/**
 * Provides access to resources in jar files.
 * 
 * @author   king
 * @since    October 30, 2004
 */
public final class ResourceHookup extends Hookup {
  
  /** The instance of this class. */
  private static ResourceHookup resourceAccess = new ResourceHookup();
  
  /** To load images and other things. */
  private ClassLoader classLoader = this.getClass().getClassLoader();

  
  /**
   * Private constructor to disable instantiation from outside.
   */
  private ResourceHookup() {
    // to disable instantiation of this class from outside.
  }
  
  /**
   * Returns an instance of this class.
   * 
   * @return  The instance of this class.
   */
  public static ResourceHookup getInstance() {
    return resourceAccess;
  }
  
  /**
   * Returns the image for a certain path.
   * 
   * @param path  Path and name to the image.
   * @return      The requested image or null if it couldn't be found.
   */
  Image getImage(String path) {
    URL url = classLoader.getResource(path);
    if (url == null) {
      return null;
    }
    else {
      return Toolkit.getDefaultToolkit().createImage(url); 
    }
  }
 
  /**
   * Returns the media for a certain path.
   * 
   * @param path  Path and name to the media.
   * @return      The requested media or null if it couldn't be found.
   */
  URL getMedia(String path) {
    URL url = classLoader.getResource(path);
    if (url == null) {
      return null;
    }
    else {
      return url;
    }
  }
 
  /** 
   * Gets the input stream for a given path.
   * 
   * @param path          Path to use.
   * @return              The input stream.
   * @throws IOException  If the input steam cannot be obtained.
   */
  public InputStream getInputStream(String path) throws IOException {
    return classLoader.getResourceAsStream(path);
  }
  
  /**
   * Returns a list of all files in the given path.
   * 
   * @param path  The path in where to look for files.
   * @return      A list of path strings. Or null if they couldn't be found.
   */  
  public String[] getFiles(String path) {
    try {
      String jarFilePath = classLoader.getResource(path).toString();
      jarFilePath = jarFilePath.substring(0, jarFilePath.indexOf("!") + 2);
      URL jarFileURL = new URL(jarFilePath);
      JarURLConnection jarFileConnection = (JarURLConnection)jarFileURL.openConnection();
      JarFile jarFile = jarFileConnection.getJarFile();
      Enumeration enumeration = jarFile.entries();
      ArrayList<String> paths = new ArrayList<String>();
      while (enumeration.hasMoreElements()) {
        String file = enumeration.nextElement().toString();
        if (file.startsWith(path))  {
          if ((file.charAt(file.length() - 1)) != '/') {
            paths.add(file);
          }
        }
      }
      return (String[])paths.toArray(new String[0]); 
    }
    catch (Exception e) {
      String[] error = new String[1];
      error[0] = "" + e;
      return error;
    }
  }
  
  /**
   * Returns a list of all directories in the given path.
   * 
   * @param path  The path in where to look for sub directories.
   * @return      A list of path strings. Or null if they couldn't be found.
   */  
  public String[] getDirectories(String path) {
    // remove trailing '/', otherwise there is a endlessloop!
    if (path.charAt(path.length() - 1) == '/') {
      path = path.substring(0, path.length() - 1);
    }
    
    // find sub directories
    try {
      String jarFilePath = classLoader.getResource(path).toString();
      jarFilePath = jarFilePath.substring(0, jarFilePath.indexOf("!") + 2);
      URL jarFileURL = new URL(jarFilePath);
      JarURLConnection jarFileConnection = (JarURLConnection)jarFileURL.openConnection();
      JarFile jarFile = jarFileConnection.getJarFile();
      Enumeration enumeration = jarFile.entries();
      ArrayList<String> paths = new ArrayList<String>();
      while (enumeration.hasMoreElements()) {
        String file = enumeration.nextElement().toString();
        if ((file.startsWith(path)) && (!file.equals(path + "/"))) {
          if ((file.charAt(file.length() - 1)) == '/') {
            paths.add(file);
          }
        }
      }
      return (String[])paths.toArray(new String[0]); 
    }
    catch (Exception e) {
      String[] error = new String[1];
      error[0] = "" + e;
      return error;
    }
  }
}
