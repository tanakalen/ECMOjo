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
import edu.hawaii.jabsom.tri.ecmo.app.control.action.PatientAction.Check;
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
    
    // Load Intervention button images
    Image normalImage = ImageLoader.getInstance().getImage(
        "conf/image/interface/game/BtnLong.png");
    Image rolloverImage = ImageLoader.getInstance().getImage(
        "conf/image/interface/game/BtnLongRol.png");
    Image selectedImage = ImageLoader.getInstance().getImage(
        "conf/image/interface/game/BtnLongSel.png");
    
    // patient buttons and actions
    // Add Suction ETT button
    ImageButton suctionETTButton 
        = new ImageButton(normalImage, rolloverImage, selectedImage);
    suctionETTButton.setText(
        Translator.getString("button.SuctionETT[i18n]: Suction ETT"));
    suctionETTButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        boolean problem = tubeComponent.isSuctionETT();
        
        // output dialog
        if (problem) {
          // fixed
          DialogBase.showDialog(PatientDetailPanel.this, DialogType.SUCCESS, DialogOption.OK
              , Translator.getString("title.ProblemFixed[i18n]: Problem Fixed")
              , Translator.getString("text.PatientDialogETT[i18n]: "
                  + "Good catch! The ETT suction was broken and has been fixed."),
              new DialogListener() {
                public void handleResult(DialogResult result) {
                  handlePatientAction(PatientAction.Check.SUCTION_ETT);
                }
          });
        }
        else {
          // no problem = nothing done
          showOKDialog(PatientAction.Check.SUCTION_ETT);
        }
      }
    });
    suctionETTButton.setLocation(22, 28);
    suctionETTButton.setSize(192, 32);
    add(suctionETTButton);
    
    // Add Check Cannula Site button
    ImageButton checkCannulaSiteButton 
        = new ImageButton(normalImage, rolloverImage, selectedImage);
    checkCannulaSiteButton.setText(
        Translator.getString("button.CheckCannula[i18n]: Check Cannula Site"));
    checkCannulaSiteButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        boolean problem = tubeComponent.isBrokenCannula();
        
        // output dialog
        if (problem) {
          // fixed
          DialogBase.showDialog(PatientDetailPanel.this, DialogType.SUCCESS, DialogOption.OK
              , Translator.getString("title.ProblemFixed[i18n]: Problem Fixed")
              , Translator.getString("text.PatientDialogCannula[i18n]: "
                  + "Good catch! The cannula site was kinked and has been fixed."),
              new DialogListener() {
                public void handleResult(DialogResult result) {
                  handlePatientAction(PatientAction.Check.CANNULA_SITE);
                }
          });
        }
        else {
          // no problem = nothing done
          showOKDialog(PatientAction.Check.CANNULA_SITE);
        }
      }
    });
    checkCannulaSiteButton.setLocation(22, 60);
    checkCannulaSiteButton.setSize(192, 32);
    add(checkCannulaSiteButton);
    
    // Add Check for Bleeding button
    ImageButton checkforBleedingButton 
        = new ImageButton(normalImage, rolloverImage, selectedImage);
    checkforBleedingButton.setText(
        Translator.getString("button.CheckBleed[i18n]: Check for Bleeding"));
    checkforBleedingButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        boolean problem = patient.isBleeding();
        
        // output dialog
        if (problem) {
          // fixed
          DialogBase.showDialog(PatientDetailPanel.this, DialogType.SUCCESS, DialogOption.OK
              , Translator.getString("title.ProblemFixed[i18n]: Problem Fixed")
              , Translator.getString("text.PatientDialogBleeding[i18n]: "
                  + "Good catch! The bleeding has been stopped."),
              new DialogListener() {
                public void handleResult(DialogResult result) {
                  handlePatientAction(PatientAction.Check.BLEEDING);
                }
          });
        }
        else {
          // no problem = nothing done
          showOKDialog(PatientAction.Check.BLEEDING);
        }
      }
    });
    checkforBleedingButton.setLocation(22, 92);
    checkforBleedingButton.setSize(192, 32);
    add(checkforBleedingButton);
    
    // Add Check Urine Output button
    ImageButton checkUrineOutputButton
        = new ImageButton(normalImage, rolloverImage, selectedImage);
    checkUrineOutputButton.setText(
        Translator.getString("button.CheckUOP[i18n]: Check Urine Output"));
    checkUrineOutputButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        boolean problem = tubeComponent.isBrokenETT();
        
        // output dialog
        if (problem) {
          // fixed
          DialogBase.showDialog(PatientDetailPanel.this, DialogType.SUCCESS, DialogOption.OK
              , Translator.getString("title.ProblemFixed[i18n]: Problem Fixed")
              , Translator.getString("text.PatientDialogUrine[i18n]: "
                  + "Good catch! The urine output has been fixed."),
              new DialogListener() {
                public void handleResult(DialogResult result) {
                  handlePatientAction(PatientAction.Check.URINE_OUTPUT);
                }
          });
        }
        else {
          // no problem = nothing done
          showOKDialog(PatientAction.Check.URINE_OUTPUT);
        }
      }
    });
    checkUrineOutputButton.setLocation(22, 124);
    checkUrineOutputButton.setSize(192, 32);
    add(checkUrineOutputButton);
    
    // Add Check Diaper button
    ImageButton checkDiaperButton
      = new ImageButton(normalImage, rolloverImage, selectedImage);
    checkDiaperButton.setText(
        Translator.getString("button.CheckDiaper[i18n]: Check Diaper"));
    checkDiaperButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        // output dialog
        DialogBase.showDialog(PatientDetailPanel.this, DialogType.PLAIN, DialogOption.OK
            , Translator.getString("title.PatientDialogDiaper[i18n]: Strange Smell")
            , Translator.getString("text.PatientDialogDiaper[i18n]: "
                + "An unpleasant odor has been detected."),
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
    
    // Add Check Sedation button
    ImageButton checkSedationButton 
        = new ImageButton(normalImage, rolloverImage, selectedImage);
    checkSedationButton.setText(
        Translator.getString("button.CheckSedation[i18n]: Check Sedation"));
    checkSedationButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        boolean sedated = patient.isSedated();
        
        // output dialog
        if (!sedated) {
          // not sedated!
          DialogBase.showDialog(PatientDetailPanel.this, DialogType.WARNING, DialogOption.OK
              , Translator.getString("title.PatientDialogNotSedated[i18n]: Not Sedated")
              , Translator.getString("text.PatientDialogNotSedated[i18n]: "
                  + "The patient is moving, wiggling and breathing."),
              new DialogListener() {
                public void handleResult(DialogResult result) {
                  handlePatientAction(PatientAction.Check.SEDATION);
                }
          });
        }
        else {
          // the patient is sleeping
          DialogBase.showDialog(PatientDetailPanel.this, DialogType.PLAIN, DialogOption.OK
              , Translator.getString("title.PatientDialogSedated[i18n]: Sedated")
              , Translator.getString("text.PatientDialogSedated[i18n]: "
                  + "The patient is fully sedated."),
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
   * Called when dialog needed and no problem detected.
   * 
   * @param action PatientAction Enum item for goal check.
   */
  private void showOKDialog(final Check action) {
    // no problem = nothing done
    DialogBase.showDialog(PatientDetailPanel.this, DialogType.PLAIN, DialogOption.OK
        , Translator.getString("title.NoProblemDetected[i18n]: No Problem Detected")
        , Translator.getString("text.PatientDialogOK[i18n]: No problem has been detected."),
        new DialogListener() {
          public void handleResult(DialogResult result) {
            handlePatientAction(action);
          }
    }
    );
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
