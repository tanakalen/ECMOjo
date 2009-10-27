package king.lib.util;

import java.util.HashSet;
import java.util.Set;

/**
 * The string set.
 * 
 * @author noblemaster
 * @since October 26, 2009
 * @access Public
 */
public class StringSet extends BaseSet {
 
  /** The set. */
  private Set set = new HashSet();
  
  
  /**
   * The default constructor.
   */
  public StringSet() {
    // default
  }
  
  /**
   * The constructor.
   *
   * @param set  The set to initialize this set with.
   */
  public StringSet(StringSet set) {
    addAll(set);
  }
  
  /**
   * Adds an object.
   * 
   * @param object  The object to add.
   */
  public void add(String object) {
    set.add(object);
  }
  
  /**
   * Adds a set.
   * 
   * @param set  The set to add.
   */
  public void addAll(StringSet set) {
    set.addAll(set); 
  }
  
  /**
   * Returns true if an object is contained.
   * 
   * @param object  The object.
   * @return  True for contained.
   */
  public boolean contains(String object) {
    return set.contains(object);
  }
  
  /**
   * Returns an array containing all the elements of the set.
   * 
   * @return  The array.
   */
  public String[] toArray() {
    return (String[])set.toArray(new String[0]);
  }
  
  /**
   * Clears the set.
   */
  public void clear() {
    set.clear(); 
  }
  
  /**
   * Removes an object.
   * 
   * @param object  The object to remove.
   */
  public void remove(String object) {
    set.remove(object);
  }
  
  /**
   * Returns the size.
   * 
   * @return  The size.
   */
  public int size() {
    return set.size(); 
  }
  
  /**
   * Returns the underlying set.
   * 
   * @return  The set.
   */
  protected Set set() {
    return set;
  }
}
