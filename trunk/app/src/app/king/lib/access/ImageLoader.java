package king.lib.access;

import java.awt.Image;

/**
 * Preloads images and makes them accessible.
 *
 * @author    king 
 * @since     March 22, 2004
 */
public final class ImageLoader {

  /** Makes sure that there is only one image loader available. */
  private static ImageLoader imageLoader = new ImageLoader();
  
  
  /**
   * Constructor for image loader. Private to make sure nobody else
   * can instantiate it. Use getInstance() method instead.
   */
  private ImageLoader() {
    // does nothing, just makes sure nobody else can instantiate it.
  }
  
  /** 
   * The way to get an instance of image loader.
   * 
   * @return  The one and only instance of image loader.
   */
  public static ImageLoader getInstance() {
    return ImageLoader.imageLoader;
  }

  /**
   * Returns the image for the given path. If the image isn't cached, the image
   * gets loaded through the ResouceHookup per default. If not found there, returns
   * null. This method blocks until the image is fully loaded. Images are not 
   * cached by this method.
   * 
   * @param path  The path to return the image for.
   * @return  The image in the cache if available, otherwise loads the image from the
   *          hookup. This method doesn't cache the image. Returns null, if image wasn't
   *          found.
   */
  public Image getImage(String path) {
    return getImage(path, ResourceHookup.getInstance());
  }
  
  /**
   * Returns the image for the given path and hookup. This method blocks until the image
   * is fully loaded. Images are not cached by this method.
   * 
   * @param path  The path to return the image for.
   * @param hookup  The place where the image resides.
   * @return  The image in the cache if available, otherwise loads the image from the
   *          hookup. This method doesn't cache the image. Returns null, if image wasn't
   *          found.
   */
  public Image getImage(String path, Hookup hookup) {
    return hookup.getTrackedImage(path);
  }
}
