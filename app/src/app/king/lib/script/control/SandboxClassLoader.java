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
   * @param parent  The parent class loader.
   * @param legalClasses  A set of classes such as: { "java.lang.Math", "java.lang.String" }.
   * @throws SandboxException  If there is a problem.
   */
  public SandboxClassLoader(ClassLoader parent, StringSet legalClasses) throws SandboxException {
    super(new URL[0], parent);
   
    // the classes that can be legally used
    this.legalClasses = legalClasses;
  }

  /**
   * Adds an URL.
   * 
   * @param url  The class file URL. For example: "file:///C:\\temp\\".
   * @throws SandboxException  If there is a problem.
   */
  public void addURL(String url) throws SandboxException {
    try {
      addURL(new URL(url));
    }
    catch (MalformedURLException e) {
      throw new SandboxException(e);
    }
  }
  
  /**
   * Adds a class.
   * 
   * @param name  The name, e.g. "com.company.SomeClass".
   * @param b  The bytes.
   * @throws SandboxException  If there is a problem.
   */
  public void addClass(String name, byte[] b) throws SandboxException {
    if (!legalClasses.contains(name)) {
      throw new SandboxException("Class name is not allowed: " + name);
    }
    else {
      
    }
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
