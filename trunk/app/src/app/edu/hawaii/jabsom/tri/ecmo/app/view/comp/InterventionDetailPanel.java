package edu.hawaii.jabsom.tri.ecmo.app.view.comp;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import king.lib.access.ImageLoader;
import king.lib.out.Error;
import king.lib.util.Translator;

import edu.hawaii.jabsom.tri.ecmo.app.control.action.InterventionAction;
import edu.hawaii.jabsom.tri.ecmo.app.gui.ImageButton;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.InterventionComponent;
import edu.hawaii.jabsom.tri.ecmo.app.model.engage.BloodIntervention;
import edu.hawaii.jabsom.tri.ecmo.app.model.engage.HeparinBolusIntervention;
import edu.hawaii.jabsom.tri.ecmo.app.model.engage.CatecholamineIntervention;
import edu.hawaii.jabsom.tri.ecmo.app.model.engage.FFPIntervention;
import edu.hawaii.jabsom.tri.ecmo.app.model.engage.AlbuminIntervention;
import edu.hawaii.jabsom.tri.ecmo.app.model.engage.Intervention;
import edu.hawaii.jabsom.tri.ecmo.app.model.engage.InterventionLocation;
import edu.hawaii.jabsom.tri.ecmo.app.model.engage.PlateletsIntervention;
import edu.hawaii.jabsom.tri.ecmo.app.model.engage.SedationIntervention;
import edu.hawaii.jabsom.tri.ecmo.app.model.engage.Tracker;
import edu.hawaii.jabsom.tri.ecmo.app.view.dialog.StandardDialog;
import edu.hawaii.jabsom.tri.ecmo.app.view.dialog.StandardDialog.DialogOption;
import edu.hawaii.jabsom.tri.ecmo.app.view.dialog.StandardDialog.DialogType;

/**
 * The detail panel. 
 *
 * @author   Christoph Aschwanden
 * @since    September 5, 2008
 */
public class InterventionDetailPanel extends DetailPanel {

  /** The component. */
  private InterventionComponent component;
  /** The tracker. */
  private Tracker tracker;
  
  /** The detail image. */
  private Image detailImage 
    = ImageLoader.getInstance().getImage("conf/image/interface/game/Detail-Intervention.png");
  
  /** The detail image. */
  private Image labelImage;
  
  /**
   * Constructor for panel.
   * 
   * @param component  The associated component.
   * @param tracker  The tracker.
   */
  protected InterventionDetailPanel(final InterventionComponent component, Tracker tracker) {
    super(component);
    this.component = component;
    this.tracker = tracker;
    
    // set size and location
    setLocation(289, 81);
    setSize(320, 320);
    
    // set layout and look
    setLayout(null);
    setOpaque(false);

    // set size and location
    InterventionLocation interventionLocation = component.getInterventionLocation();
    if (interventionLocation == InterventionLocation.PATIENT) {
      labelImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Lbl-A.png");
    }
    else if (interventionLocation == InterventionLocation.BEFORE_PUMP) {
      labelImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Lbl-B.png");
    }
    else if (interventionLocation == InterventionLocation.BEFORE_OXYGENATOR) {
      labelImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Lbl-C.png");
    }
    else if (interventionLocation == InterventionLocation.AFTER_OXYGENATOR) {
      labelImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Lbl-D.png");
    }
    else {
      Error.out("Intervention location not defined: " + interventionLocation);
    }
    
    Image bloodNormalImage = ImageLoader.getInstance().getImage(
        Translator.getString("image.ButtonBlood[i18n]: conf/image/interface/game/Btn-Blood.png"));
    Image bloodRolloverImage = ImageLoader.getInstance().getImage(
        Translator.getString("image.ButtonBloodRol[i18n]: conf/image/interface/game/Btn-BloodRol.png"));
    Image bloodSelectedImage = ImageLoader.getInstance().getImage(
        Translator.getString("image.ButtonBloodSel[i18n]: conf/image/interface/game/Btn-BloodSel.png"));
    ImageButton bloodButton = new ImageButton(bloodNormalImage, bloodRolloverImage, bloodSelectedImage);
    bloodButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        intervene(new BloodIntervention());
      }
    });
    bloodButton.setLocation(22, 28);
    bloodButton.setSize(192, 32);
    add(bloodButton);
    
    Image plateletsNormalImage = ImageLoader.getInstance().getImage(
        Translator.getString("image.ButtonPlatelets[i18n]: conf/image/interface/game/Btn-Platelets.png"));
    Image plateletsRolloverImage = ImageLoader.getInstance().getImage(
        Translator.getString("image.ButtonPlateletsRol[i18n]: conf/image/interface/game/Btn-PlateletsRol.png"));
    Image plateletsSelectedImage = ImageLoader.getInstance().getImage(
        Translator.getString("image.ButtonPlateletsSel[i18n]: conf/image/interface/game/Btn-PlateletsSel.png"));
    ImageButton plateletsButton = new ImageButton(plateletsNormalImage, plateletsRolloverImage, plateletsSelectedImage);
    plateletsButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        intervene(new PlateletsIntervention());
      }
    });
    plateletsButton.setLocation(22, 60);
    plateletsButton.setSize(192, 32);
    add(plateletsButton); 

    Image ffpNormalImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-Ffp.png");
    Image ffpRolloverImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-FfpRol.png");
    Image ffpSelectedImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-FfpSel.png");
    ImageButton ffpButton = new ImageButton(ffpNormalImage, ffpRolloverImage, ffpSelectedImage);
    ffpButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        intervene(new FFPIntervention());
      }
    });
    ffpButton.setLocation(22, 92);
    ffpButton.setSize(192, 32);
    add(ffpButton); 
    
    Image heparinNormalImage = ImageLoader.getInstance().getImage(
        Translator.getString("image.ButtonHeparin[i18n]: conf/image/interface/game/Btn-Heparin.png"));
    Image heparinRollImage = ImageLoader.getInstance().getImage(
        Translator.getString("image.ButtonHeparinRol[i18n]: conf/image/interface/game/Btn-HeparinRol.png"));
    Image heparinSelImage = ImageLoader.getInstance().getImage(
        Translator.getString("image.ButtonHeparinSel[i18n]: conf/image/interface/game/Btn-HeparinSel.png"));
    ImageButton heparinButton = new ImageButton(heparinNormalImage, heparinRollImage, heparinSelImage);
    heparinButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        intervene(new HeparinBolusIntervention());
      }
    });
    heparinButton.setLocation(22, 124);
    heparinButton.setSize(192, 32);
    add(heparinButton); 
 
    Image catecholamineNormalImage = ImageLoader.getInstance().getImage(
        Translator.getString("image.ButtonCatecholamine[i18n]: conf/image/interface/game/Btn-Catecholamine.png"));
    Image catecholamineRolloverImage = ImageLoader.getInstance().getImage(
        Translator.getString("image.ButtonCatecholamineRol[i18n]: conf/image/interface/game/Btn-CatecholamineRol.png"));
    Image catecholamineSelectedImage = ImageLoader.getInstance().getImage(
        Translator.getString("image.ButtonCatecholamineSel[i18n]: conf/image/interface/game/Btn-CatecholamineSel.png"));
    ImageButton catecholamineButton 
      = new ImageButton(catecholamineNormalImage, catecholamineRolloverImage, catecholamineSelectedImage);
    catecholamineButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        intervene(new CatecholamineIntervention());
      }
    });
    catecholamineButton.setLocation(22, 156);
    catecholamineButton.setSize(192, 32);
    add(catecholamineButton); 
 
    Image albuminNormalImage = ImageLoader.getInstance().getImage(
        Translator.getString("image.ButtonAlbumin[i18n]: conf/image/interface/game/Btn-Albumin.png"));
    Image albuminRolloverImage = ImageLoader.getInstance().getImage(
        Translator.getString("image.ButtonAlbuminRol[i18n]: conf/image/interface/game/Btn-AlbuminRol.png"));
    Image albuminSelectedImage = ImageLoader.getInstance().getImage(
        Translator.getString("image.ButtonAlbuminSel[i18n]: conf/image/interface/game/Btn-AlbuminSel.png"));
    ImageButton albuminButton
      = new ImageButton(albuminNormalImage, albuminRolloverImage, albuminSelectedImage);
    albuminButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        intervene(new AlbuminIntervention());
      }
    });
    albuminButton.setLocation(22, 188);
    albuminButton.setSize(192, 32);
    add(albuminButton);    
    
    Image sedationNormalImage = ImageLoader.getInstance().getImage(
        Translator.getString("image.ButtonSedation[i18n]: conf/image/interface/game/Btn-Sedation.png"));
    Image sedationRolloverImage = ImageLoader.getInstance().getImage(
        Translator.getString("image.ButtonSedationRol[i18n]: conf/image/interface/game/Btn-SedationRol.png"));
    Image sedationSelectedImage = ImageLoader.getInstance().getImage(
        Translator.getString("image.ButtonSedationSel[i18n]: conf/image/interface/game/Btn-SedationSel.png"));
    ImageButton sedationButton
      = new ImageButton(sedationNormalImage, sedationRolloverImage, sedationSelectedImage);
    sedationButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        intervene(new SedationIntervention());
      }
    });
    sedationButton.setLocation(22, 220);
    sedationButton.setSize(192, 32);
    add(sedationButton);    
  }

  /**
   * Intervene.
   * 
   * @param intervention  The intervention.
   */
  private void intervene(Intervention intervention) {
    final int interventionLimit = 5;
    if (tracker.getCount(intervention.getClass()) >= interventionLimit) {
      StandardDialog.showDialog(this, DialogType.ERROR, DialogOption.OK
          , "Intervention Usage", "You cannot use this intervention more than " + interventionLimit + " times.");
    }
    else {
      // execute intervention
      InterventionLocation location = component.getInterventionLocation();
      InterventionAction action = new InterventionAction();
      action.setIntervention(intervention);
      action.setLocation(location);
      notifyActionListeners(action);
      
      // output location warning as needed
      if (location != InterventionLocation.PATIENT) {
        if (((intervention instanceof PlateletsIntervention))
         || ((intervention instanceof HeparinBolusIntervention) && (location != InterventionLocation.BEFORE_OXYGENATOR))
         || ((location == InterventionLocation.AFTER_OXYGENATOR))) {
          
          // shows the location warning dialog box when the intervention is executed at the wrong place
          StandardDialog.showDialog(this, DialogType.WARNING, DialogOption.OK
              , "Intervention Location Warning"
              , "The intervention has been executed. The baby's mojo has been reduced by 10%. Please check your "
              + "policies and procedures.");
        }
      }
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

    // set antialised text
    Graphics2D g2 = (Graphics2D)g;
    g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                        RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

    // draw base
    g2.drawImage(detailImage, 0, 0, this);
    g2.drawImage(labelImage, 224, 118, this);    
  }
}
