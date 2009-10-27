package king.lib.script.control;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import king.lib.util.StringSet;

/**
 * The sandbox class loader.
 * 
 * @author noblemaster
 * @since October 26, 2009
 * @access Public
 */
public class SandboxClassLoader extends URLClassLoader {
 
  /** The classes we have legal access to. */
  private StringSet legalClasses;
  
  
  /**
   * The constructor.
   *
   * @param url  The class file URL. For example: "file:///C:\\temp\\".
   * @param parent  The parent class loader.
   * @param legalClasses  A set of classes such as: { "java.lang.Math", "java.lang.String" }.
   * @throws SandboxException  If there is a problem instantiating the class loader.
   */
  public SandboxClassLoader(String url, ClassLoader parent, StringSet legalClasses) throws SandboxException {
    super(new URL[0], parent);
   
    // set the url
    try {
      addURL(new URL(url));
    }
    catch (MalformedURLException e) {
      throw new SandboxException(e);
    }
    
    // the classes that can be legally used
    this.legalClasses = legalClasses;
  }

  /**
   * Loads the actual classes. Throws an IllegalArgumentException if a class is not allowed to be used.
   * 
   * @param name  The name of the class to load.
   * @param resolve  True to resolve the class.
   * @throws ClassNotFoundException  If the class cannot be found.
   * @return The class loaded.
   */
  @Override
  protected synchronized Class loadClass(String name, boolean resolve) throws ClassNotFoundException {
    if (legalClasses.contains(name)) {
      // it's a legal class, let's load it...
      return super.loadClass(name, resolve); 
    }
    else {
      throw new IllegalArgumentException("No permission to load class: " + name);
    }
  }
}
