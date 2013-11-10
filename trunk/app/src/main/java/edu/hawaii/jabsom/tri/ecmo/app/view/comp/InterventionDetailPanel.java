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
    
    // Load Intervention button images
    Image normalImage = ImageLoader.getInstance().getImage(
        "conf/image/interface/game/BtnIntervention.png");
    Image selectedImage = ImageLoader.getInstance().getImage(
        "conf/image/interface/game/BtnInterventionSel.png");
    
    // add Blood intervention button
    final ImageButton bloodButton = new ImageButton(normalImage, normalImage, selectedImage);
    bloodButton.setText(
        Translator.getString("button.Blood[i18n]: Blood Cells"));
    bloodButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        intervene(new BloodIntervention());
      }
    });
    bloodButton.setLocation(22, 28);
    bloodButton.setSize(192, 32);
    add(bloodButton);
    
    // add Platelet intervention button
    final ImageButton plateletsButton = new ImageButton(normalImage, normalImage, selectedImage);
    plateletsButton.setText(
        Translator.getString("button.Platelets[i18n]: Platelets"));
    plateletsButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        intervene(new PlateletsIntervention());
      }
    });
    plateletsButton.setLocation(22, 60);
    plateletsButton.setSize(192, 32);
    add(plateletsButton); 
    
    // add FFP intervention button
    final ImageButton ffpButton = new ImageButton(normalImage, normalImage, selectedImage);
    ffpButton.setText(
        Translator.getString("button.FFP[i18n]: FFP"));
    ffpButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        intervene(new FFPIntervention());
      }
    });
    ffpButton.setLocation(22, 92);
    ffpButton.setSize(192, 32);
    add(ffpButton);
    
    // add Heparin bolus button
    final ImageButton heparinButton = new ImageButton(normalImage, normalImage, selectedImage);
    heparinButton.setText(
        Translator.getString("button.Heparin[i18n]: Heparin Bolus"));
    heparinButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        intervene(new HeparinBolusIntervention());
      }
    });
    heparinButton.setLocation(22, 124);
    heparinButton.setSize(192, 32);
    add(heparinButton);
    
    // add Catecholamine bolus button
    final ImageButton catecholamineButton = new ImageButton(normalImage, normalImage, selectedImage);
    catecholamineButton.setText(
        Translator.getString("button.Catechol[i18n]: Catecholamine"));
    catecholamineButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        intervene(new CatecholamineIntervention());
      }
    });
    catecholamineButton.setLocation(22, 156);
    catecholamineButton.setSize(192, 32);
    add(catecholamineButton);
    
    // add Albumin button
    final ImageButton albuminButton = new ImageButton(normalImage, normalImage, selectedImage);
    albuminButton.setText(
        Translator.getString("button.Albumin[i18n]: 5% Albumin"));
    albuminButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        intervene(new AlbuminIntervention());
      }
    });
    albuminButton.setLocation(22, 188);
    albuminButton.setSize(192, 32);
    add(albuminButton);
    
    // add Sedation button
    final ImageButton sedationButton = new ImageButton(normalImage, normalImage, selectedImage);
    sedationButton.setText(Translator.getString("button.Sedation[i18n]: Sedation"));
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
          , Translator.getString("title.InterventionDialog[i18n]: Intervention Usage")
          , Translator.getString("text.InterventionDialog1[i18n]: "
              + "You cannot use this intervention more than ")
              + interventionLimit
              + Translator.getString("text.InterventionDialog2[i18n]: times."));
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
              , Translator.getString("title.InterventionLocation[i18n]: Intervention Location Warning")
              , Translator.getString("text.InterventionLocation[i18n]: "
                  + "The intervention has been executed. The baby's mojo has been reduced by 10%. "
                  + "Please check your policies and procedures."));
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
