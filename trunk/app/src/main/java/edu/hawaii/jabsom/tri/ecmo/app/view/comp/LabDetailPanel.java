package edu.hawaii.jabsom.tri.ecmo.app.view.comp;

import java.awt.Graphics;
import java.awt.Image;

import king.lib.access.ImageLoader;

import edu.hawaii.jabsom.tri.ecmo.app.model.comp.LabComponent;
import edu.hawaii.jabsom.tri.ecmo.app.model.lab.BloodGasLab;
import edu.hawaii.jabsom.tri.ecmo.app.model.lab.ChemistryLab;
import edu.hawaii.jabsom.tri.ecmo.app.model.lab.HematologyLab;
import edu.hawaii.jabsom.tri.ecmo.app.model.lab.ImagingLab;
import edu.hawaii.jabsom.tri.ecmo.app.model.lab.Lab;
import edu.hawaii.jabsom.tri.ecmo.app.view.lab.BloodGasLabPanel;
import edu.hawaii.jabsom.tri.ecmo.app.view.lab.ChemistryLabPanel;
import edu.hawaii.jabsom.tri.ecmo.app.view.lab.HematologyLabPanel;
import edu.hawaii.jabsom.tri.ecmo.app.view.lab.ImagingLabPanel;

/**
 * The detail panel. 
 *
 * @author   Christoph Aschwanden
 * @since    September 30, 2008
 */
public abstract class LabDetailPanel extends DetailPanel {

  /** The detail image. */
  private Image detailImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Detail-Lab.png");
  
  
  /**
   * Constructor for panel.
   * 
   * @param component  The associated component.
   */
  protected LabDetailPanel(final LabComponent component) {
    super(component);
    
    // set size and location
    setLocation(289, 81);
    setSize(320, 320);
    
    // set layout and look
    setLayout(null);
    setOpaque(false);
  }

  /**
   * Creates an component panel instance for the inputted component.
   * 
   * @param component  The component.
   * @return  The panel for the component or null if none matched.
   */
  protected static LabDetailPanel createInstance(LabComponent component) {
    Class<? extends Lab> labTest = component.getLabTest();
    if (labTest.equals(BloodGasLab.class)) {
      return new BloodGasLabPanel(component);
    }
    else if (labTest.equals(ChemistryLab.class)) {
      return new ChemistryLabPanel(component);
    }
    else if (labTest.equals(HematologyLab.class)) {
      return new HematologyLabPanel(component);
    }
    else if (labTest.equals(ImagingLab.class)) {
      return new ImagingLabPanel(component);
    }
    else {
      // not found
      return null;
    }
  }
  
  /**
   * Called when the component got updated.
   */
  public void handleUpdate() {
    repaint();
  }
  
  /**
   * Paints this component.
   * 
   * @param g  Where to draw to.
   */
  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    // draw base
    g.drawImage(detailImage, 0, 0, this);
  }
}
