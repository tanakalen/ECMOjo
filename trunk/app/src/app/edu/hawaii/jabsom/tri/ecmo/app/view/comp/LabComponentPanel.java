package edu.hawaii.jabsom.tri.ecmo.app.view.comp;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;

import edu.hawaii.jabsom.tri.ecmo.app.gui.ImageToggleButton;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.LabComponent;
import edu.hawaii.jabsom.tri.ecmo.app.model.lab.BloodGasLabTest;
import edu.hawaii.jabsom.tri.ecmo.app.model.lab.ChemistryLabTest;
import edu.hawaii.jabsom.tri.ecmo.app.model.lab.HematologyLabTest;
import edu.hawaii.jabsom.tri.ecmo.app.model.lab.ImagingLabTest;
import edu.hawaii.jabsom.tri.ecmo.app.model.lab.LabTest;

import king.lib.access.ImageLoader;
import king.lib.util.Translator;

/**
 * The lab component. 
 *
 * @author   Christoph Aschwanden
 * @since    September 30, 2008
 */
public class LabComponentPanel extends ComponentPanel {
  
  /** The selection button. */
  private AbstractButton selectionButton;

  
  /**
   * Constructor for panel.
   * 
   * @param component  The associated component.
   */
  protected LabComponentPanel(LabComponent component) {
    super(component);

    // set transparent
    setOpaque(false);
    
    // set layout
    setLayout(null);
    
    // determine parameters
    Image normalImage;
    Image rolloverImage;
    Image selectedImage;
    Class<? extends LabTest> labTest = component.getLabTest();
    if (labTest.equals(BloodGasLabTest.class)) {
      normalImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-Abg.png");
      rolloverImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-AbgRol.png");
      selectedImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-AbgSel.png");
      setLocation(0, 464);
    }
    else if (labTest.equals(ChemistryLabTest.class)) {
      normalImage = ImageLoader.getInstance().getImage(
          Translator.getString("image.ButtonChem[i18n]: conf/image/interface/game/Btn-Chem.png"));
      rolloverImage = ImageLoader.getInstance().getImage(
          Translator.getString("image.ButtonChemRol[i18n]: conf/image/interface/game/Btn-ChemRol.png"));
      selectedImage = ImageLoader.getInstance().getImage(
          Translator.getString("image.ButtonChemSel[i18n]: conf/image/interface/game/Btn-ChemSel.png"));
      setLocation(0, 488);
    }
    else if (labTest.equals(HematologyLabTest.class)) {
      normalImage = ImageLoader.getInstance().getImage(
          Translator.getString("image.ButtonHeme[i18n]: conf/image/interface/game/Btn-Heme.png"));
      rolloverImage = ImageLoader.getInstance().getImage(
          Translator.getString("image.ButtonHemeRol[i18n]: conf/image/interface/game/Btn-HemeRol.png"));
      selectedImage = ImageLoader.getInstance().getImage(
          Translator.getString("image.ButtonHemeSel[i18n]: conf/image/interface/game/Btn-HemeSel.png"));
      setLocation(0, 512);
    }
    else if (labTest.equals(ImagingLabTest.class)) {
      normalImage = ImageLoader.getInstance().getImage(
          Translator.getString("image.ButtonImg[i18n]: conf/image/interface/game/Btn-Img.png"));
      rolloverImage = ImageLoader.getInstance().getImage(
          Translator.getString("image.ButtonImgRol[i18n]: conf/image/interface/game/Btn-ImgRol.png"));
      selectedImage = ImageLoader.getInstance().getImage(
          Translator.getString("image.ButtonImgSel[i18n]: conf/image/interface/game/Btn-ImgSel.png"));
      setLocation(0, 536);
    }
    else {
      throw new RuntimeException("Lab test not defined: " + labTest);
    }
    setSize(48, 24);

    // add button
    selectionButton = new ImageToggleButton(normalImage, rolloverImage, selectedImage, selectedImage);
    selectionButton.setToolTipText(component.getName());
    selectionButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        notifyActivationListeners();
      }
    });
    selectionButton.setLocation(0, 0);
    selectionButton.setSize(48, 24);
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
