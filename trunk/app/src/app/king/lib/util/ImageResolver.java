package king.lib.util;

import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

import king.lib.out.Error;

/**
 * The image resolver.
 *         
 * @author Christoph Aschwanden
 * @since November 6, 2009
 */
public abstract class ImageResolver {

  /** The default image resolver. */
  private static ImageResolver instance = new ImageResolver() {
    
    /** 
     * Resolves the image using the default resolver.
     * 
     * @param path  The path.
     * @return  The image or null for not found.
     */
    @Override
    public Image resolve(String path) {
      return resolveDefault(path);
    }    
  };

  
  /**
   * Returns the default image resolver.
   * 
   * @return  The default image resolver.
   */
  public static ImageResolver getDefault() {
    return instance;
  }
  
  /**
   * Default image resolver. Uses the Resource class to resolve the image.
   * 
   * @param path  The path.
   * @return  The image or null for not found.
   */
  protected final Image resolveDefault(String path) {
    try {
      return Toolkit.getDefaultToolkit().getImage(new URL(path));
    }
    catch (Exception e) {
      Error.out(e);
      return null;
    }
  }
  
  /**
   * Resolves the image for the given path.
   * 
   * @param path  The path.
   * @return  The image or null for not found.
   */
  public abstract Image resolve(String path);
}