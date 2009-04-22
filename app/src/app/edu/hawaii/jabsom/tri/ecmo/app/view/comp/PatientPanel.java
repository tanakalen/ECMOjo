package edu.hawaii.jabsom.tri.ecmo.app.view.comp;

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;

import king.lib.access.ImageLoader;

import edu.hawaii.jabsom.tri.ecmo.app.gui.ImageToggleButton;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.Patient;

/**
 * The patient panel. 
 *
 * @author   kinwang
 * @since    November 17, 2008
 */
public class PatientPanel extends ComponentPanel {

  /** The rollover image. */
  private Image rolloverImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-PatientRol.png");
  /** The selected image. */
  private Image selectedImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-PatientSel.png");

  /** The patient image. */
  private Image patientImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Patient-Baby.png");
  /** The patient blue image. */
  private Image patientBlueImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Patient-BabyBlue.png");
  
  /** The selection button. */
  private AbstractButton selectionButton;

  
  /**
   * Default constructor.
   * 
   * @param patient  The patient.
   */
  public PatientPanel(Patient patient) {
    super(patient);
    
    // set transparent
    setOpaque(false);
    
    // set size and location
    setLocation(16, 332);
    setSize(256, 256);
    
    // set layout
    setLayout(null);
    
    // add toggle button
    selectionButton = new ImageToggleButton(null, rolloverImage, selectedImage, selectedImage);
    selectionButton.setToolTipText(patient.getName());
    selectionButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        notifyActivationListeners();
      }
    });
    selectionButton.setLocation(33, 46);
    selectionButton.setSize(190, 165);
    add(selectionButton); 
  }

  /**
   * Returns the patient.
   * 
   * @return  The patient.
   */
  public Patient getPatient() {
    return (Patient)getComponent();
  }
  
  /**
   * Sets the patient.
   * 
   * @param patient  The patient.
   */
  public void setPatient(Patient patient) {
    setComponent(patient);
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

    // set antialised text
    Graphics2D g2 = (Graphics2D)g;
    g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                        RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

    // draw base
    g2.drawImage(patientImage, 0, 0, this);

    // do blue overlay
    Patient patient = getPatient();
    float alpha = (float)(0.85 - patient.getO2Saturation()) * 2;
    if (alpha < 0.0f) {
      alpha = 0.0f;
    }
    else if (alpha > 1.0f) {
      alpha = 1.0f;
    }
    
    Composite oldComposite = g2.getComposite();
    Composite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
    g2.setComposite(alphaComposite);
    g2.drawImage(patientBlueImage, 0, 0, this);
    g2.setComposite(oldComposite);   
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
