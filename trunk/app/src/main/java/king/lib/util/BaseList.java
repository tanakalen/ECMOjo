package king.lib.util;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * The base list.
 *         
 * @author Christoph Aschwanden
 * @since August 11, 2009
 * @access Public
 */
public abstract class BaseList {

  /**
   * Sorts the list.
   */
  public void sort() {
    Collections.sort(list());
  }
  
  /**
   * Sorts the list using a comparator.
   *
   * @param comparator  The comparator.
   */
  public void sort(Comparator comparator) {
    Collections.sort(list(), comparator);
  }
  
  /**
   * Returns the underlying list.
   *
   * @return  The list.
   */
  protected abstract List list();
}