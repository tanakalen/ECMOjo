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
import edu.hawaii.jabsom.tri.ecmo.app.view.dialog.StandardDialog;
import edu.hawaii.jabsom.tri.ecmo.app.view.dialog.StandardDialog.DialogOption;
import edu.hawaii.jabsom.tri.ecmo.app.view.dialog.StandardDialog.DialogType;

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
    Image checkCanulaSiteNormalImage 
      = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-CheckCanulaSite.png");
    Image checkCanulaSiteRolloverImage 
      = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-CheckCanulaSiteRol.png");
    Image checkCanulaSiteSelectedImage 
      = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-CheckCanulaSiteSel.png");
    ImageButton checkCanulaSiteButton 
      = new ImageButton(checkCanulaSiteNormalImage, checkCanulaSiteRolloverImage, checkCanulaSiteSelectedImage);
    checkCanulaSiteButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        boolean problem = tubeComponent.isBrokenCannula();
        
        // send the action
        PatientAction action = new PatientAction();
        action.setCheck(PatientAction.Check.CANULA_SITE);
        notifyActionListeners(action);
            
        // output dialog
        if (problem) {
          // fixed
          StandardDialog.showDialog(PatientDetailPanel.this, DialogType.SUCCESS, DialogOption.OK
              , "Problem Fixed"
              , "Good catch! The canula site was broken and has been fixed.");
        }
        else {
          // no problem = nothing done
          StandardDialog.showDialog(PatientDetailPanel.this, DialogType.PLAIN, DialogOption.OK
              , "No Problem Detected"
              , "No problem has been detected.");
        }
      }
    });
    checkCanulaSiteButton.setLocation(31, 37);
    checkCanulaSiteButton.setSize(192, 32);
    add(checkCanulaSiteButton);
    
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
          StandardDialog.showDialog(PatientDetailPanel.this, DialogType.SUCCESS, DialogOption.OK
              , "Problem Fixed"
              , "Good catch! The bleeding has been stopped.");
        }
        else {
          // no problem = nothing done
          StandardDialog.showDialog(PatientDetailPanel.this, DialogType.PLAIN, DialogOption.OK
              , "No Problem Detected"
              , "No problem has been detected.");
        }
      }
    });
    checkforBleedingButton.setLocation(31, 69);
    checkforBleedingButton.setSize(192, 32);
    add(checkforBleedingButton);
    
    Image checkETTNormalImage 
      = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-CheckETT.png");
    Image checkETTRolloverImage 
      = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-CheckETTRol.png");
    Image checkETTSelectedImage 
      = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-CheckETTSel.png");
    ImageButton checkETTButton
      = new ImageButton(checkETTNormalImage, checkETTRolloverImage, checkETTSelectedImage);
    checkETTButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        boolean problem = tubeComponent.isBrokenETT();
        
        // send the action
        PatientAction action = new PatientAction();
        action.setCheck(PatientAction.Check.BROKEN_ETT);
        notifyActionListeners(action);
            
        // output dialog
        if (problem) {
          // fixed
          StandardDialog.showDialog(PatientDetailPanel.this, DialogType.SUCCESS, DialogOption.OK
              , "Problem Fixed"
              , "Good catch! The ETT was broken and has been fixed.");
        }
        else {
          // no problem = nothing done
          StandardDialog.showDialog(PatientDetailPanel.this, DialogType.PLAIN, DialogOption.OK
              , "No Problem Detected"
              , "No problem has been detected.");
        }
      }
    });
    checkETTButton.setLocation(31, 101);
    checkETTButton.setSize(192, 32);
    add(checkETTButton);
    
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
          StandardDialog.showDialog(PatientDetailPanel.this, DialogType.SUCCESS, DialogOption.OK
              , "Problem Fixed"
              , "Good catch! The ETT suction was broken and has been fixed.");
        }
        else {
          // no problem = nothing done
          StandardDialog.showDialog(PatientDetailPanel.this, DialogType.PLAIN, DialogOption.OK
              , "No Problem Detected"
              , "No problem has been detected.");
        }
      }
    });
    suctionETTButton.setLocation(31, 133);
    suctionETTButton.setSize(192, 32);
    add(suctionETTButton);
    
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
        StandardDialog.showDialog(PatientDetailPanel.this, DialogType.PLAIN, DialogOption.OK
            , "Strange Smell"
            , "A unpleasant odor has been detected.");
      }
    });
    checkDiaperButton.setLocation(31, 165);
    checkDiaperButton.setSize(192, 32);
    add(checkDiaperButton);
    
    Image stimulateBabyNormalImage 
      = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-StimulateBaby.png");
    Image stimulateBabyRolloverImage 
      = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-StimulateBabyRol.png");
    Image stimulateBabySelectedImage 
      = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-StimulateBabySel.png");
    ImageButton stimulateBabyButton 
      = new ImageButton(stimulateBabyNormalImage, stimulateBabyRolloverImage, stimulateBabySelectedImage);    
    stimulateBabyButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        // send the action
        PatientAction action = new PatientAction();
        action.setCheck(PatientAction.Check.STIMULATE);
        notifyActionListeners(action);
            
        // output dialog
        StandardDialog.showDialog(PatientDetailPanel.this, DialogType.PLAIN, DialogOption.OK
            , "Patient Stimulated"
            , "The patient has been stimulated.");
      }
    });
    stimulateBabyButton.setLocation(31, 197);
    stimulateBabyButton.setSize(192, 32);
    add(stimulateBabyButton);
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
