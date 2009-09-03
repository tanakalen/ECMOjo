package edu.hawaii.jabsom.tri.ecmo.app.view.comp;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;

import king.lib.access.ImageLoader;

import edu.hawaii.jabsom.tri.ecmo.app.gui.ImageToggleButton;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.InterventionComponent;
import edu.hawaii.jabsom.tri.ecmo.app.model.engage.InterventionLocation;

/**
 * The intervention component panel. 
 *
 * @author   Christoph Aschwanden
 * @since    September 30, 2008
 */
public class InterventionComponentPanel extends ComponentPanel {

  /** The selection button. */
  private AbstractButton selectionButton;
  /** The rollover image. */
  private Image rolloverImage
    = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-InterventionRol.png");
  /** The selected image. */
  private Image selectedImage
    = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-InterventionSel.png"); 
  
  /**
   * Constructor for panel.
   * 
   * @param component  The associated component.
   */
  protected InterventionComponentPanel(InterventionComponent component) {
    super(component);

    // set transparent
    setOpaque(false);
    
    // set layout
    setLayout(null);
    
    // determine parameters
    InterventionLocation interventionLocation = component.getInterventionLocation();    
    if (interventionLocation == InterventionLocation.PATIENT) {   
      setLocation(292, 364);  
    }
    else if (interventionLocation == InterventionLocation.BEFORE_PUMP) {
      setLocation(512, 484);
    }
    else if (interventionLocation == InterventionLocation.BEFORE_OXYGENATOR) {
      setLocation(628, 428);
    }
    else if (interventionLocation == InterventionLocation.AFTER_OXYGENATOR) {
      setLocation(515, 348);
    }
    else {
      throw new RuntimeException("Intervention location not defined: " + interventionLocation);
    }
    setSize(24, 72);
    
    // add toggle button
    selectionButton = new ImageToggleButton(null, rolloverImage, selectedImage, selectedImage);
    selectionButton.setToolTipText(component.getName());
    selectionButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        notifyActivationListeners();
      }
    });
    selectionButton.setLocation(0, 0);
    selectionButton.setSize(24, 72);
    add(selectionButton); 
  }

  /**
   * Called when the component got updated.
   */
  public void handleUpdate() {
    repaint();
  }
  
  /**
   * Assigns the component to a group.
   * 
   * @param group  The group.
   */
  public void assign(ButtonGroup group) {
    group.add(selectionButton);
  }
}
