package king.lib.util;

import java.io.File;
import java.util.Hashtable;
import java.util.Enumeration;
import javax.swing.filechooser.*;

/**
 * A convenience implementation of FileFilter that filters out
 * all files except for those type extensions that it knows about.
 *
 * Extensions are of the type ".foo", which is typically found on
 * Windows and Unix boxes, but not on Macinthosh. Case is ignored.
 *
 * Example - create a new filter that filerts out all files
 * but gif and jpg image files:
 *
 *     JFileChooser chooser = new JFileChooser();
 *     SimpleFileFilter filter = new SimpleFileFilter(
 *          new String{"gif", "jpg"}, "JPEG & GIF Images")
 *     chooser.addChoosableFileFilter(filter);
 *     chooser.showOpenDialog(this);
 *
 * @version 1.12 12/03/01
 * @author Jeff Dinkins
 */
public class SimpleFileFilter extends FileFilter {

  /** Hashtable for filter. */
  private Hashtable<String, Object> filters = new Hashtable<String, Object>();
  /** The description. */
  private String description = null;
  /** The full description. */
  private String fullDescription = null;
  /** If extensions to be used in description. */
  private boolean useExtensionsInDescription = true;

  
  /**
   * Creates a file filter that accepts files with the given extension.
   * Example: new ExampleFileFilter("jpg");
   * 
   * @param extension  The extension to create the file filter with.
   */
  public SimpleFileFilter(String extension) {
    this(extension, null);
  }

  /**
   * Creates a file filter that accepts the given file type.
   * Example: new ExampleFileFilter("jpg", "JPEG Image Images");
   *
   * Note that the "." before the extension is not needed. If
   * provided, it will be ignored.
   * 
   * @param extension  The extension to create the file filter with.
   * @param description  The description of the filter.
   */
  public SimpleFileFilter(String extension, String description) {
    if (extension != null) {
      addExtension(extension);
    }
    if (description != null) {
      setDescription(description);
    }
  }

  /**
   * Creates a file filter from the given string array.
   * Example: new ExampleFileFilter(String {"gif", "jpg"});
   *
   * Note that the "." before the extension is not needed adn
   * will be ignored.
   * 
   * @param filters  A list of filters.
   */
  public SimpleFileFilter(String[] filters) {
    this(filters, null);
  }

  /**
   * Creates a file filter from the given string array and description.
   * Example: new ExampleFileFilter(String {"gif", "jpg"}, "Gif and JPG Images");
   *
   * Note that the "." before the extension is not needed and will be ignored.
   * 
   * @param filters  A list of filters.
   * @param description  File filter description.
   */
  public SimpleFileFilter(String[] filters, String description) {
    for (int i = 0; i < filters.length; i++) {
      // add filters one by one
      addExtension(filters[i]);
    }
    if (description != null) {
      setDescription(description);
    }
  }

  /**
   * Return true if this file should be shown in the directory pane,
   * false if it shouldn't.
   *
   * Files that begin with "." are ignored.
   * 
   * @param f  If the filter accepts the file.
   * @return  True, if accepts.
   */
  public boolean accept(File f) {
    if (f != null) {
      if (f.isDirectory()) {
        return true;
      }
      String extension = getExtension(f);
      if (extension != null && filters.get(getExtension(f)) != null) {
        return true;
      }
    }
    return false;
  }

  /**
   * Return the extension portion of the file's name.
   * 
   * @param f  The extension for the file.
   * @return  The extension.
   */
  public String getExtension(File f) {
    if (f != null) {
      String filename = f.getName();
      int i = filename.lastIndexOf('.');
      if (i > 0 && i < filename.length() - 1) {
        return filename.substring(i + 1).toLowerCase();
      }
    }
    return null;
  }

  /**
   * Adds a filetype "dot" extension to filter against.
   *
   * For example: the following code will create a filter that filters
   * out all files except those that end in ".jpg" and ".tif":
   *
   *   ExampleFileFilter filter = new ExampleFileFilter();
   *   filter.addExtension("jpg");
   *   filter.addExtension("tif");
   *
   * Note that the "." before the extension is not needed and will be ignored.
   * 
   * @param extension  The extension to add.
   */
  public void addExtension(String extension) {
    filters.put(extension.toLowerCase(), this);
    fullDescription = null;
  }

  /**
   * Returns the human readable description of this filter. For
   * example: "JPEG and GIF Image Files (*.jpg, *.gif)".
   * 
   * @return  The description.
   */
  public String getDescription() {
    if (fullDescription == null) {
      if (description == null || isExtensionListInDescription()) {
        fullDescription = description == null ? "(" : description + " (";
        // build the description from the extension list
        Enumeration extensions = filters.keys();
        if (extensions != null) {
          fullDescription += "." + (String) extensions.nextElement();
          while (extensions.hasMoreElements()) {
            fullDescription += ", ." + (String) extensions.nextElement();
          }
        }
        fullDescription += ")";
      }
      else {
        fullDescription = description;
      }
    }
    return fullDescription;
  }

  /**
   * Sets the human readable description of this filter. For
   * example: filter.setDescription("Gif and JPG Images");
   * 
   * @param description  The new description.
   */
  public void setDescription(String description) {
    this.description = description;
    fullDescription = null;
  }

  /**
   * Determines whether the extension list (.jpg, .gif, etc) should
   * show up in the human readable description.
   *
   * Only relevent if a description was provided in the constructor
   * or using setDescription();
   * 
   * @param b  True, for using extensions in description.
   */
  public void setExtensionListInDescription(boolean b) {
    useExtensionsInDescription = b;
    fullDescription = null;
  }

  /**
   * Returns whether the extension list (.jpg, .gif, etc) should
   * show up in the human readable description.
   *
   * Only relevent if a description was provided in the constructor
   * or using setDescription();
   * 
   * @return  true, if extension to use in description.
   */
  public boolean isExtensionListInDescription() {
    return useExtensionsInDescription;
  }
}
