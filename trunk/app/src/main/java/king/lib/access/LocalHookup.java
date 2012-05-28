package king.lib.access;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Provides access to local file system.
 * 
 * @author   king
 * @since    October 30, 2004
 */
public final class LocalHookup extends Hookup {
  
  /** The instance of this class. */
  private static LocalHookup localAccess = new LocalHookup();
  
  
  /**
   * Private constructor to disable instantiation from outside.
   */
  private LocalHookup() {
    // to disable instantiation of this class from outside.
  }
  
  /**
   * Returns an instance of this class.
   * 
   * @return  The instance of this class.
   */
  public static LocalHookup getInstance() {
    return localAccess;
  }
  
  /**
   * Returns the image for a certain path.
   * 
   * @param path  Path and name to the image.
   * @return      The requested image or null if it couldn't be found.
   */
  Image getImage(String path) {
    return Toolkit.getDefaultToolkit().createImage(path);
  }

  /** 
   * Gets the input stream for a given path.
   * 
   * @param path          Path to use.
   * @return              The input stream.
   * @throws IOException  If the input steam cannot be obtained.
   */
  public InputStream getInputStream(String path) throws IOException {
    return new FileInputStream(new File(path));
  }

  /** 
   * Gets the output stream for a given path.
   * 
   * @param path          Path to use.
   * @param append        True, to append data, false to create new file.
   * @return              The output stream.
   * @throws IOException  If the output steam cannot be obtained.
   */
  public OutputStream getOutputStream(String path, boolean append) throws IOException {
    return new FileOutputStream(new File(path), append);
  }

  /** 
   * Gets the output stream for a given path. Always creates a new file. Doesn't append.
   * 
   * @param path          Path to use.
   * @return              The output stream.
   * @throws IOException  If the output steam cannot be obtained.
   */
  public OutputStream getOutputStream(String path) throws IOException {
    return getOutputStream(path, false);
  }
  
  /**
   * Returns a list of all files in the given path.
   * 
   * @param path  The path in where to look for files.
   * @return      A list of path strings. Or null if they couldn't be found.
   */  
  public String[] getFiles(String path) {
    // find all the files in the given path
    File file = new File(path);
    File[] files = file.listFiles();
    ArrayList<String> filePaths = new ArrayList<String>();
    for (int i = 0; i < files.length; i++) {
      if (files[i].isFile()) {
        filePaths.add(files[i].toString());
      }
    }
    
    // Returns a list of files.
    return (String[])filePaths.toArray(new String[0]);  
  }
  
  /**
   * Returns a list of all directories in the given path.
   * 
   * @param path  The path in where to look for sub directories.
   * @return      A list of path strings. Or null if they couldn't be found.
   */  
  public String[] getDirectories(String path) {
    // find all the files in the given path
    File file = new File(path);
    File[] files = file.listFiles();
    ArrayList<String> filePaths = new ArrayList<String>();
    for (int i = 0; i < files.length; i++) {
      if (files[i].isDirectory()) {
        filePaths.add(files[i].toString());
      }
    }
    
    // Returns a list of directories.
    return (String[])filePaths.toArray(new String[0]);  
  }
}
