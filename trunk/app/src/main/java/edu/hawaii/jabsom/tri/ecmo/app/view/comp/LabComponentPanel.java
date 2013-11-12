package edu.hawaii.jabsom.tri.ecmo.app.view.comp;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
  private ImageToggleButton selectionButton;

  
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
    String text;
    Class<? extends LabTest> labTest = component.getLabTest();
    if (labTest.equals(BloodGasLabTest.class)) {
      normalImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn3.png");
      rolloverImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn3Rol.png");
      selectedImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn3Sel.png");
      text = Translator.getString("button.ABG[i18n]: ABG");
      setLocation(0, 464);
      setSize(40, 24);
    }
    else if (labTest.equals(ChemistryLabTest.class)) {
      normalImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn4.png");
      rolloverImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn4Rol.png");
      selectedImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn4Sel.png");
      text = Translator.getString("button.CHEM[i18n]: CHEM");
      setLocation(0, 488);
      setSize(48, 24);
    }
    else if (labTest.equals(HematologyLabTest.class)) {
      normalImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn4.png");
      rolloverImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn4Rol.png");
      selectedImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn4Sel.png");
      text = Translator.getString("button.HEME[i18n]: HEME");
      setLocation(0, 512);
      setSize(48, 24);
    }
    else if (labTest.equals(ImagingLabTest.class)) {
      normalImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn3.png");
      rolloverImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn3Rol.png");
      selectedImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn3Sel.png");
      text = Translator.getString("button.IMG[i18n]: IMG");
      setLocation(0, 536);
      setSize(40, 24);
    }
    else {
      throw new RuntimeException("Lab test not defined: " + labTest);
    }
    
    // add button
    selectionButton = new ImageToggleButton(normalImage, rolloverImage, selectedImage, selectedImage);
    selectionButton.setText(text);
    selectionButton.setForeground(Color.RED);
    selectionButton.setRolloverColor(Color.GREEN);
    selectionButton.setToolTipText(component.getName());
    selectionButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        notifyActivationListeners();
      }
    });
    selectionButton.setLocation(0, 0);
    selectionButton.setSize(this.getWidth(), this.getHeight());
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
