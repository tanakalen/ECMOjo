package edu.hawaii.jabsom.tri.ecmo.app.view.comp;

import edu.hawaii.jabsom.tri.ecmo.app.model.comp.VentilatorComponent;
import edu.hawaii.jabsom.tri.ecmo.app.view.dialog.StandardDialog;
import edu.hawaii.jabsom.tri.ecmo.app.view.dialog.StandardDialog.DialogOption;
import edu.hawaii.jabsom.tri.ecmo.app.view.dialog.StandardDialog.DialogType;

/**
 * The detail panel. 
 *
 * @author   Christoph Aschwanden
 * @since    September 5, 2008
 */
public class VentilatorDetailPanel extends DetailPanel {
  
  /**
   * Constructor for panel.
   *
   * @param component  The associated component.
   */
  protected VentilatorDetailPanel(final VentilatorComponent component) {
    super(component);
    
    // set layout and look
    setLayout(null);
    setOpaque(false);
    
    StandardDialog.showDialog(this, DialogType.ERROR, DialogOption.OK
        , "Ventilator Settings", "You are advised not to change the Ventilator Settings.");
  }

  /**
   * Called when the component got updated.
   */
  public void handleUpdate() {

  }
   
}
