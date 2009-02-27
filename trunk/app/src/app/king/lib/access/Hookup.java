package king.lib.access;

import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * Provides access to various sources such as jar files, web or the local
 * file system.
 * 
 * @author   king
 * @since    October 30, 2004
 */
public abstract class Hookup {
  
  /** Tracks images and waits until they are available. */
  private JPanel trackerComponent = new JPanel();
  
  
  /**
   * Returns an image, however waits until it is fully loaded. Dito getImage(String path)
   * except it waits for the image to load completly.
   * 
   * @param path  Path and name to the image.
   * @return      The requested image or null if it couldn't be loaded.
   */
  Image getTrackedImage(String path) {
    Image image = getImage(path);
    if (image == null) {
      return null;
    }
    else {
      try {
        MediaTracker tracker = new MediaTracker(this.trackerComponent);
        int trackerId = 0;
        tracker.addImage(image, trackerId);
        tracker.waitForID(trackerId);
        if (tracker.isErrorID(trackerId)) {
          return null;
        }
        else {
          return image;
        }
      }
      catch (InterruptedException e) {
        // if image couldn't be loaded
        return null;
      }
    }
  }
  
  /**
   * Returns the image from a given input stream. IMPORTANT: do not use this method
   * unless there is no other way than an input stream to read the image from. 
   * 
   * @param inputStream  The input stream to read the image from.
   * @return  The image or null if it couldn't be loaded.
   */
  public static Image getImage(InputStream inputStream) {
    BufferedImage bufferedImage = getBufferedImage(inputStream);
    if (bufferedImage != null) {
      return Toolkit.getDefaultToolkit().createImage(bufferedImage.getSource());
    }
    else {
      return null;
    }
  }
  
  /**
   * Returns the buffered image for the given input stream.
   * 
   * @param inputStream  The input stream to read the image from.
   * @return  The buffered image or null if it couldn't be loaded.
   */
  public static BufferedImage getBufferedImage(InputStream inputStream) {
    try {
      return ImageIO.read(inputStream);
    }
    catch (IOException e) {
      return null;
    }
  }
  
  /**
   * Returns the image for a certain path.
   * 
   * @param path  Path and name to the image.
   * @return      The requested image or null if it couldn't be found.
   */
  abstract Image getImage(String path);

  /** 
   * Gets the input stream for a path.
   * 
   * @param path          Path to use.
   * @return              The input stream.
   * @throws IOException  If the input steam cannot be obtained.
   */
  public abstract InputStream getInputStream(String path) throws IOException;
  
  /**
   * Returns a list of all files in the given path.
   * 
   * @param path  The path in where to look for files.
   * @return      A list of path strings. Or null if they couldn't be found.
   */  
  public abstract String[] getFiles(String path);  
  
  /**
   * Returns a list of all directories in the given path.
   * 
   * @param path  The path in where to look for sub directories.
   * @return      A list of path strings. Or null if they couldn't be found.
   */  
  public abstract String[] getDirectories(String path);
}
