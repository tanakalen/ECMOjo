package edu.hawaii.jabsom.tri.ecmo.app.view.comp;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import king.lib.access.ImageLoader;

import edu.hawaii.jabsom.tri.ecmo.app.control.action.PatientAction;
import edu.hawaii.jabsom.tri.ecmo.app.gui.ImageButton;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.Patient;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.TubeComponent;
import edu.hawaii.jabsom.tri.ecmo.app.view.dialog.DialogBase;
import edu.hawaii.jabsom.tri.ecmo.app.view.dialog.DialogBase.DialogOption;
import edu.hawaii.jabsom.tri.ecmo.app.view.dialog.DialogBase.DialogType;

/**
 * The detail panel. 
 *
 * @author   Christoph Aschwanden
 * @since    September 5, 2008
 */
public class PatientDetailPanel extends DetailPanel {

  /** The detail image. */
  private Image detailImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Detail-Patient.png");
  
  
  /**
   * Constructor for panel.
   * 
   * @param patient  The associated patient.
   * @param tubeComponent  The tube component.
   */
  protected PatientDetailPanel(final Patient patient, final TubeComponent tubeComponent) {
    super(patient);
    
    // set size and location
    setLocation(289, 81);
    setSize(320, 320);
    
    // set layout and look
    setLayout(null);
    setOpaque(false);

    // patient buttons and actions
    Image suctionETTNormalImage 
      = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-SuctionETT.png");
    Image suctionETTRolloverImage 
      = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-SuctionETTRol.png");
    Image suctionETTSelectedImage 
      = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-SuctionETTSel.png");
    ImageButton suctionETTButton 
      = new ImageButton(suctionETTNormalImage, suctionETTRolloverImage, suctionETTSelectedImage);
    suctionETTButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        boolean problem = tubeComponent.isSuctionETT();
        
        // send the action
        PatientAction action = new PatientAction();
        action.setCheck(PatientAction.Check.SUCTION_ETT);
        notifyActionListeners(action);
            
        // output dialog
        if (problem) {
          // fixed
          DialogBase.showDialog(PatientDetailPanel.this, DialogType.SUCCESS, DialogOption.OK
              , "Problem Fixed"
              , "Good catch! The ETT suction was broken and has been fixed.");
        }
        else {
          // no problem = nothing done
          DialogBase.showDialog(PatientDetailPanel.this, DialogType.PLAIN, DialogOption.OK
              , "No Problem Detected"
              , "No problem has been detected.");
        }
      }
    });
    suctionETTButton.setLocation(22, 28);
    suctionETTButton.setSize(192, 32);
    add(suctionETTButton);
  
    Image checkCannulaSiteNormalImage 
      = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-CheckCannulaSite.png");
    Image checkCannulaSiteRolloverImage 
      = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-CheckCannulaSiteRol.png");
    Image checkCannulaSiteSelectedImage 
      = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-CheckCannulaSiteSel.png");
    ImageButton checkCannulaSiteButton 
      = new ImageButton(checkCannulaSiteNormalImage, checkCannulaSiteRolloverImage, checkCannulaSiteSelectedImage);
    checkCannulaSiteButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        boolean problem = tubeComponent.isBrokenCannula();
        
        // send the action
        PatientAction action = new PatientAction();
        action.setCheck(PatientAction.Check.CANNULA_SITE);
        notifyActionListeners(action);
            
        // output dialog
        if (problem) {
          // fixed
          DialogBase.showDialog(PatientDetailPanel.this, DialogType.SUCCESS, DialogOption.OK
              , "Problem Fixed"
              , "Good catch! The cannula site was kinked and has been fixed.");
        }
        else {
          // no problem = nothing done
          DialogBase.showDialog(PatientDetailPanel.this, DialogType.PLAIN, DialogOption.OK
              , "No Problem Detected"
              , "No problem has been detected.");
        }
      }
    });
    checkCannulaSiteButton.setLocation(22, 60);
    checkCannulaSiteButton.setSize(192, 32);
    add(checkCannulaSiteButton);
    
    Image checkforBleedingNormalImage 
      = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-CheckforBleeding.png");
    Image checkforBleedingRolloverImage 
      = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-CheckforBleedingRol.png");
    Image checkforBleedingSelectedImage 
      = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-CheckforBleedingSel.png");
    ImageButton checkforBleedingButton 
      = new ImageButton(checkforBleedingNormalImage, checkforBleedingRolloverImage, checkforBleedingSelectedImage);
    checkforBleedingButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        boolean problem = patient.isBleeding();
        
        // send the action
        PatientAction action = new PatientAction();
        action.setCheck(PatientAction.Check.BLEEDING);
        notifyActionListeners(action);
            
        // output dialog
        if (problem) {
          // fixed
          DialogBase.showDialog(PatientDetailPanel.this, DialogType.SUCCESS, DialogOption.OK
              , "Problem Fixed"
              , "Good catch! The bleeding has been stopped.");
        }
        else {
          // no problem = nothing done
          DialogBase.showDialog(PatientDetailPanel.this, DialogType.PLAIN, DialogOption.OK
              , "No Problem Detected"
              , "No problem has been detected.");
        }
      }
    });
    checkforBleedingButton.setLocation(22, 92);
    checkforBleedingButton.setSize(192, 32);
    add(checkforBleedingButton);
    
    Image checkUrineOutputNormalImage 
      = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-CheckUrineOutput.png");
    Image checkUrineOutputRolloverImage 
      = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-CheckUrineOutputRol.png");
    Image checkUrineOutputSelectedImage 
      = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-CheckUrineOutputSel.png");
    ImageButton checkUrineOutputButton
      = new ImageButton(checkUrineOutputNormalImage, checkUrineOutputRolloverImage, checkUrineOutputSelectedImage);
    checkUrineOutputButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        boolean problem = tubeComponent.isBrokenETT();
        
        // send the action
        PatientAction action = new PatientAction();
        action.setCheck(PatientAction.Check.URINE_OUTPUT);
        notifyActionListeners(action);
            
        // output dialog
        if (problem) {
          // fixed
          DialogBase.showDialog(PatientDetailPanel.this, DialogType.SUCCESS, DialogOption.OK
              , "Problem Fixed"
              , "Good catch! The urine output has been fixed.");
        }
        else {
          // no problem = nothing done
          DialogBase.showDialog(PatientDetailPanel.this, DialogType.PLAIN, DialogOption.OK
              , "No Problem Detected"
              , "No problem has been detected.");
        }
      }
    });
    checkUrineOutputButton.setLocation(22, 124);
    checkUrineOutputButton.setSize(192, 32);
    add(checkUrineOutputButton);
        
    Image checkDiaperNormalImage 
      = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-CheckDiaper.png");
    Image checkDiaperRolloverImage
      = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-CheckDiaperRol.png");
    Image checkDiaperSelectedImage
      = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-CheckDiaperSel.png");
    ImageButton checkDiaperButton
      = new ImageButton(checkDiaperNormalImage, checkDiaperRolloverImage, checkDiaperSelectedImage);    
    checkDiaperButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        // send the action
        PatientAction action = new PatientAction();
        action.setCheck(PatientAction.Check.DIAPER);
        notifyActionListeners(action);
            
        // output dialog
        DialogBase.showDialog(PatientDetailPanel.this, DialogType.PLAIN, DialogOption.OK
            , "Strange Smell"
            , "A unpleasant odor has been detected.");
      }
    });
    checkDiaperButton.setLocation(22, 156);
    checkDiaperButton.setSize(192, 32);
    add(checkDiaperButton);
    
    Image checkSedationNormalImage 
      = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-CheckSedation.png");
    Image checkSedationRolloverImage 
      = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-CheckSedationRol.png");
    Image checkSedationSelectedImage 
      = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-CheckSedationSel.png");
    ImageButton checkSedationButton = new ImageButton(checkSedationNormalImage
        , checkSedationRolloverImage, checkSedationSelectedImage);    
    checkSedationButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        boolean sedated = patient.isSedated();
        
        // send the action
        PatientAction action = new PatientAction();
        action.setCheck(PatientAction.Check.SEDATION);
        notifyActionListeners(action);
            
        // output dialog
        if (!sedated) {
          // not sedated!
          DialogBase.showDialog(PatientDetailPanel.this, DialogType.WARNING, DialogOption.OK
              , "Not Sedated"
              , "The patient is moving, wiggling and breathing.");
        }
        else {
          // the patient is sleeping
          DialogBase.showDialog(PatientDetailPanel.this, DialogType.PLAIN, DialogOption.OK
              , "Sedated"
              , "The patient is fully sedated.");
        }
      }
    });
    checkSedationButton.setLocation(22, 188);
    checkSedationButton.setSize(192, 32);
    add(checkSedationButton);
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
    g2.drawImage(detailImage, 0, 0, this);
  }
}
