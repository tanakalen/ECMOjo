package king.lib.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.IOException;

/**
 * Clone utility, to make deep copies of serializable objects. Found on
 * http://www.javaworld.com/javaworld/javatips/jw-javatip76-p2.html
 * 
 * @author  king
 * @since   November 18, 2004
 */
public final class ObjectCloner {
  
  /**
   * To allow nobody to create an object cloner object. 
   */
  private ObjectCloner() {
    // so that nobody can accidentally create an ObjectCloner object
  }

  /**
   * Returns a deep copy of a serializable object.
   * 
   * @param object  The object to be cloned.
   * @return  The cloned object or null, if cloning didn't work.
   */
  public static Serializable deepCopy(Object object) {
    ObjectOutputStream oos = null;
    ObjectInputStream ois = null;
    try {
      ByteArrayOutputStream bos = new ByteArrayOutputStream(); 
      oos = new ObjectOutputStream(bos); 
      // serialize and pass the object
      oos.writeObject(object); 
      oos.flush(); 
      ByteArrayInputStream bin = new ByteArrayInputStream(bos.toByteArray()); 
      ois = new ObjectInputStream(bin); 
      // return the new object
      return (Serializable)ois.readObject(); 
    } 
    catch (IOException e) {
      return null;
    } 
    catch (ClassNotFoundException e) {
      return null;
    }
    finally {
      try {
        if (oos != null) { oos.close(); }
        if (ois != null) { ois.close(); }
      }
      catch (IOException e) {
        return null;
      }
    }
  }
}
