package edu.hawaii.jabsom.tri.ecmo.app.view.comp;

import java.awt.Graphics;
import java.awt.Image;

import king.lib.access.ImageLoader;

import edu.hawaii.jabsom.tri.ecmo.app.model.comp.LabComponent;
import edu.hawaii.jabsom.tri.ecmo.app.model.lab.BloodGasLabTest;
import edu.hawaii.jabsom.tri.ecmo.app.model.lab.ChemistryLabTest;
import edu.hawaii.jabsom.tri.ecmo.app.model.lab.HematologyLabTest;
import edu.hawaii.jabsom.tri.ecmo.app.model.lab.ImagingLabTest;
import edu.hawaii.jabsom.tri.ecmo.app.model.lab.LabTest;
import edu.hawaii.jabsom.tri.ecmo.app.view.lab.BloodGasLabTestPanel;
import edu.hawaii.jabsom.tri.ecmo.app.view.lab.ChemistryLabTestPanel;
import edu.hawaii.jabsom.tri.ecmo.app.view.lab.HematologyLabTestPanel;
import edu.hawaii.jabsom.tri.ecmo.app.view.lab.ImagingLabTestPanel;

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
    Class<? extends LabTest> labTest = component.getLabTest();
    if (labTest.equals(BloodGasLabTest.class)) {
      return new BloodGasLabTestPanel(component);
    }
    else if (labTest.equals(ChemistryLabTest.class)) {
      return new ChemistryLabTestPanel(component);
    }
    else if (labTest.equals(HematologyLabTest.class)) {
      return new HematologyLabTestPanel(component);
    }
    else if (labTest.equals(ImagingLabTest.class)) {
      return new ImagingLabTestPanel(component);
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
