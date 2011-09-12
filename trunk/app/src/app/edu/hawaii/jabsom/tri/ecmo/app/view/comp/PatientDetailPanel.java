package edu.hawaii.jabsom.tri.ecmo.app.view.comp;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import king.lib.access.ImageLoader;
import king.lib.util.Translator;

import edu.hawaii.jabsom.tri.ecmo.app.control.action.PatientAction;
import edu.hawaii.jabsom.tri.ecmo.app.gui.ImageButton;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.Patient;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.TubeComponent;
import edu.hawaii.jabsom.tri.ecmo.app.view.dialog.DialogBase;
import edu.hawaii.jabsom.tri.ecmo.app.view.dialog.DialogBase.*;

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
    Image suctionETTNormalImage = ImageLoader.getInstance().getImage(
        Translator.getString("image.ButtonSuctionETT[i18n]: conf/image/interface/game/Btn-SuctionETT.png"));
    Image suctionETTRolloverImage = ImageLoader.getInstance().getImage(
        Translator.getString("image.ButtonSuctionETTRol[i18n]: conf/image/interface/game/Btn-SuctionETTRol.png"));
    Image suctionETTSelectedImage = ImageLoader.getInstance().getImage(
        Translator.getString("image.ButtonSuctionETTSel[i18n]: conf/image/interface/game/Btn-SuctionETTSel.png"));
    ImageButton suctionETTButton 
      = new ImageButton(suctionETTNormalImage, suctionETTRolloverImage, suctionETTSelectedImage);
    suctionETTButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        boolean problem = tubeComponent.isSuctionETT();
        
        // output dialog
        if (problem) {
          // fixed
          DialogBase.showDialog(PatientDetailPanel.this, DialogType.SUCCESS, DialogOption.OK
              , "Problem Fixed"
              , "Good catch! The ETT suction was broken and has been fixed.",
              new DialogListener() {
                public void handleResult(DialogResult result) {
                  handlePatientAction(PatientAction.Check.SUCTION_ETT);
                }
          });
        }
        else {
          // no problem = nothing done
          DialogBase.showDialog(PatientDetailPanel.this, DialogType.PLAIN, DialogOption.OK,
              "No Problem Detected", "No problem has been detected.",
              new DialogListener() {
                public void handleResult(DialogResult result) {
                  handlePatientAction(PatientAction.Check.SUCTION_ETT);
                }      
          });
        }
      }
    });
    suctionETTButton.setLocation(22, 28);
    suctionETTButton.setSize(192, 32);
    add(suctionETTButton);
  
    Image checkCannulaSiteNormalImage = ImageLoader.getInstance().getImage(
        Translator.getString("image.ButtonCheckCannulaSite[i18n]: " 
            + "conf/image/interface/game/Btn-CheckCannulaSite.png"));
    Image checkCannulaSiteRolloverImage = ImageLoader.getInstance().getImage(
        Translator.getString("image.ButtonCheckCannulaSiteRol[i18n]: " 
            + "conf/image/interface/game/Btn-CheckCannulaSiteRol.png"));
    Image checkCannulaSiteSelectedImage = ImageLoader.getInstance().getImage(
        Translator.getString("image.ButtonCheckCannulaSiteSel[i18n]: " 
            + "conf/image/interface/game/Btn-CheckCannulaSiteSel.png"));
    ImageButton checkCannulaSiteButton 
      = new ImageButton(checkCannulaSiteNormalImage, checkCannulaSiteRolloverImage, checkCannulaSiteSelectedImage);
    checkCannulaSiteButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        boolean problem = tubeComponent.isBrokenCannula();
        
        // output dialog
        if (problem) {
          // fixed
          DialogBase.showDialog(PatientDetailPanel.this, DialogType.SUCCESS, DialogOption.OK
              , "Problem Fixed"
              , "Good catch! The cannula site was kinked and has been fixed.",
              new DialogListener() {
                public void handleResult(DialogResult result) {
                  handlePatientAction(PatientAction.Check.CANNULA_SITE);
                }
          });
        }
        else {
          // no problem = nothing done
          DialogBase.showDialog(PatientDetailPanel.this, DialogType.PLAIN, DialogOption.OK,
              "No Problem Detected", "No problem has been detected.",
              new DialogListener() {
                public void handleResult(DialogResult result) {
                  handlePatientAction(PatientAction.Check.CANNULA_SITE);
                }
          });
        }
      }
    });
    checkCannulaSiteButton.setLocation(22, 60);
    checkCannulaSiteButton.setSize(192, 32);
    add(checkCannulaSiteButton);
    
    Image checkforBleedingNormalImage = ImageLoader.getInstance().getImage(
        Translator.getString("image.ButtonCheckforBleeding[i18n]: "
            + "conf/image/interface/game/Btn-CheckforBleeding.png"));
    Image checkforBleedingRolloverImage = ImageLoader.getInstance().getImage(
        Translator.getString("image.ButtonCheckforBleedingRol[i18n]: "
            + "conf/image/interface/game/Btn-CheckforBleedingRol.png"));
    Image checkforBleedingSelectedImage = ImageLoader.getInstance().getImage(
        Translator.getString("image.ButtonCheckforBleedingSel[i18n]: "
            + "conf/image/interface/game/Btn-CheckforBleedingSel.png"));
    ImageButton checkforBleedingButton 
      = new ImageButton(checkforBleedingNormalImage, checkforBleedingRolloverImage, checkforBleedingSelectedImage);
    checkforBleedingButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        boolean problem = patient.isBleeding();
        
        // output dialog
        if (problem) {
          // fixed
          DialogBase.showDialog(PatientDetailPanel.this, DialogType.SUCCESS, DialogOption.OK
              , "Problem Fixed"
              , "Good catch! The bleeding has been stopped.",
              new DialogListener() {
                public void handleResult(DialogResult result) {
                  handlePatientAction(PatientAction.Check.BLEEDING);
                }
          });
        }
        else {
          // no problem = nothing done
          DialogBase.showDialog(PatientDetailPanel.this, DialogType.PLAIN, DialogOption.OK,
              "No Problem Detected", "No problem has been detected.",
              new DialogListener() {
                public void handleResult(DialogResult result) {
                  handlePatientAction(PatientAction.Check.BLEEDING);
                }
          });
        }
      }
    });
    checkforBleedingButton.setLocation(22, 92);
    checkforBleedingButton.setSize(192, 32);
    add(checkforBleedingButton);
    
    Image checkUrineOutputNormalImage = ImageLoader.getInstance().getImage(
        Translator.getString("image.ButtonCheckUrineOutput[i18n]: "
            + "conf/image/interface/game/Btn-CheckUrineOutput.png"));
    Image checkUrineOutputRolloverImage = ImageLoader.getInstance().getImage(
        Translator.getString("image.ButtonCheckUrineOutputRol[i18n]: "
            + "conf/image/interface/game/Btn-CheckUrineOutputRol.png"));
    Image checkUrineOutputSelectedImage = ImageLoader.getInstance().getImage(
        Translator.getString("image.ButtonCheckUrineOutputSel[i18n]: "
            + "conf/image/interface/game/Btn-CheckUrineOutputSel.png"));
    ImageButton checkUrineOutputButton
      = new ImageButton(checkUrineOutputNormalImage, checkUrineOutputRolloverImage, checkUrineOutputSelectedImage);
    checkUrineOutputButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        boolean problem = tubeComponent.isBrokenETT();
        
        // output dialog
        if (problem) {
          // fixed
          DialogBase.showDialog(PatientDetailPanel.this, DialogType.SUCCESS, DialogOption.OK
              , "Problem Fixed"
              , "Good catch! The urine output has been fixed.",
              new DialogListener() {
                public void handleResult(DialogResult result) {
                  handlePatientAction(PatientAction.Check.URINE_OUTPUT);
                }
          });
        }
        else {
          // no problem = nothing done
          DialogBase.showDialog(PatientDetailPanel.this, DialogType.PLAIN, DialogOption.OK,
              "No Problem Detected", "No problem has been detected.",
              new DialogListener() {
              public void handleResult(DialogResult result) {
                handlePatientAction(PatientAction.Check.URINE_OUTPUT);
              }
          });
        }
      }
    });
    checkUrineOutputButton.setLocation(22, 124);
    checkUrineOutputButton.setSize(192, 32);
    add(checkUrineOutputButton);
        
    Image checkDiaperNormalImage = ImageLoader.getInstance().getImage(
        Translator.getString("image.ButtonCheckDiaper[i18n]: conf/image/interface/game/Btn-CheckDiaper.png"));
    Image checkDiaperRolloverImage = ImageLoader.getInstance().getImage(
        Translator.getString("image.ButtonCheckDiaperRol[i18n]: conf/image/interface/game/Btn-CheckDiaperRol.png"));
    Image checkDiaperSelectedImage = ImageLoader.getInstance().getImage(
        Translator.getString("image.ButtonCheckDiaperSel[i18n]: conf/image/interface/game/Btn-CheckDiaperSel.png"));
    ImageButton checkDiaperButton
      = new ImageButton(checkDiaperNormalImage, checkDiaperRolloverImage, checkDiaperSelectedImage);    
    checkDiaperButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        // output dialog
        DialogBase.showDialog(PatientDetailPanel.this, DialogType.PLAIN, DialogOption.OK
            , "Strange Smell"
            , "A unpleasant odor has been detected.",
            new DialogListener() {
              public void handleResult(DialogResult result) {
                handlePatientAction(PatientAction.Check.DIAPER);
              }
        });
      }
    });
    checkDiaperButton.setLocation(22, 156);
    checkDiaperButton.setSize(192, 32);
    add(checkDiaperButton);
    
    Image checkSedationNormalImage = ImageLoader.getInstance().getImage(
        Translator.getString("image.ButtonCheckSedation[i18n]: conf/image/interface/game/Btn-CheckSedation.png"));
    Image checkSedationRolloverImage = ImageLoader.getInstance().getImage(
        Translator.getString("image.ButtonCheckSedationRol[i18n]: conf/image/interface/game/Btn-CheckSedationRol.png"));
    Image checkSedationSelectedImage = ImageLoader.getInstance().getImage(
        Translator.getString("image.ButtonCheckSedationSel[i18n]: conf/image/interface/game/Btn-CheckSedationSel.png"));
    ImageButton checkSedationButton = new ImageButton(checkSedationNormalImage
        , checkSedationRolloverImage, checkSedationSelectedImage);    
    checkSedationButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        boolean sedated = patient.isSedated();
        
        // output dialog
        if (!sedated) {
          // not sedated!
          DialogBase.showDialog(PatientDetailPanel.this, DialogType.WARNING, DialogOption.OK
              , "Not Sedated"
              , "The patient is moving, wiggling and breathing.",
              new DialogListener() {
                public void handleResult(DialogResult result) {
                  handlePatientAction(PatientAction.Check.SEDATION);
                }
          });
        }
        else {
          // the patient is sleeping
          DialogBase.showDialog(PatientDetailPanel.this, DialogType.PLAIN, DialogOption.OK
              , "Sedated"
              , "The patient is fully sedated.",
              new DialogListener() {
                public void handleResult(DialogResult result) {
                  handlePatientAction(PatientAction.Check.SEDATION);
                }
          });
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
   * Called when dialog result occurs. Handles patient actions.
   * 
   * @param check PatientAction Enum item for goal check.
   */
  private void handlePatientAction(PatientAction.Check check) {
    PatientAction action = new PatientAction();
    action.setCheck(check);
    notifyActionListeners(action);    
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
