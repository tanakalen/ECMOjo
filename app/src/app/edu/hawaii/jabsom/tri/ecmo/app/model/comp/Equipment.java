package edu.hawaii.jabsom.tri.ecmo.app.model.comp;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * The equipment. 
 *
 * @author   king
 * @since    August 13, 2008
 */
public class Equipment extends ArrayList<Component> implements Serializable {

  /**
   * Returns the first component that matches the type.
   * 
   * @param componentType  The component type.
   * @return  The first component that matched the type or null if not found
   */
  public Component getComponent(Class<? extends Component> componentType) {
    for (Component component: this) {
      if (componentType.isAssignableFrom(component.getClass())) {
        return component;
      }
    }
    
    // not found
    return null;
  }
}
