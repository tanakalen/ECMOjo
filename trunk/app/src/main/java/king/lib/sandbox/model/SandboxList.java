package king.lib.sandbox.model;

import java.util.ArrayList;
import java.util.List;

import king.lib.util.BaseList;

/**
 * The Sandbox list.
 *         
 * @author Christoph Aschwanden
 * @since August 11, 2009
 * @access Public
 */
public class SandboxList extends BaseList {

  /** The list. */
  private List list = new ArrayList();
  
  
  /**
   * The default constructor.
   */
  public SandboxList() {
    // default
  }
  
  /**
   * The constructor.
   *
   * @param list  The list to initialize this list with.
   */
  public SandboxList(SandboxList list) {
    addAll(list);
  }
  
  /**
   * Adds a object.
   *
   * @param object  The object.
   */
  public void add(Sandbox object) {
    list.add(object);
  }
  
  /**
   * Adds a list.
   *
   * @param list  The list.
   */
  public void addAll(SandboxList list) {
    this.list.addAll(list.list);
  }
  
  /**
   * Returns true if an object is contained.
   *
   * @param object  The object.
   * @return  True for contained.
   */
  public boolean contains(Sandbox object) {
    return list.contains(object);
  }
  
  /**
   * Returns the index of an object.
   *
   * @param object  The object.
   * @return  The index.
   */
  public int indexOf(Sandbox object) {
    return list.indexOf(object);
  }
  
  /**
   * Returns an array containing all of the elements in this list in proper sequence.
   *
   * @return  The array.
   */
  public Sandbox[] toArray() {
    return (Sandbox[])list.toArray(new Sandbox[0]);
  }
  
  /**
   * Clears the list.
   */
  public void clear() {
    list.clear();
  }
  
  /**
   * Sets a user.
   *
   * @param index  The index.
   * @param object  The object.
   */
  public void set(int index, Sandbox object) {
    list.set(index, object);
  }
  
  /**
   * Removes an object. 
   *
   * @param object  The object to remove.
   */
  public void remove(Sandbox object) {
    list.remove(object); 
  }
  
  /**
   * Removes the object at the given index.
   *
   * @param index  The index.
   */
  public void remove(int index) {
    list.remove(index);
  }
  
  /**
   * Returns an object.
   *
   * @param index  The object index.
   * @return  The object.
   */
  public Sandbox get(int index) {
    return (Sandbox)list.get(index);
  }
  
  /**
   * Returns the size.
   *
   * @return  The size.
   */
  public int size() {
    return list.size();
  }
  
  /**
   * Returns the underlying list.
   *
   * @return  The list.
   */
  protected List list() {
    return list;
  }
}