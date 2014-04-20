package edu.hawaii.jabsom.tri.ecmo.app.view.comp;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;

import king.lib.access.ImageLoader;
import king.lib.out.Error;
import king.lib.util.Translator;
import edu.hawaii.jabsom.tri.ecmo.app.control.action.VentilatorAction;
import edu.hawaii.jabsom.tri.ecmo.app.gui.ImageToggleButton;
import edu.hawaii.jabsom.tri.ecmo.app.gui.TextLabel;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.VentilatorComponent;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.VentilatorComponent.ConventionalSubtype;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.VentilatorComponent.HighFrequencySubtype;

/**
 * The component panel. 
 *
 * @author   Christoph Aschwanden
 * @author   Kin Lik Wang
 * @since    September 9, 2008
 */
public class VentilatorComponentPanel extends ComponentPanel implements Runnable {

  /** The rollover image. */
  private Image rolloverImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-VentilatorRol.png");
  /** The emergency none image. */
  private Image selectedImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-VentilatorSel.png");
  
  /** The ventilator formatter. */
  private final DecimalFormat ventilatorFormatter = new DecimalFormat("0.0");
  
  /** The panel image. */
  private Image image;

  /** The emergency normal image. */
  private Image emergencyNormalImage = ImageLoader.getInstance().getImage(
      "conf/image/interface/game/BtnEmergency.png");
  /** The emergency rollover image. */
  private Image emergencyRolloverImage = ImageLoader.getInstance().getImage(
      "conf/image/interface/game/Btn-EmergencyRol.png");
  /** The emergency selected image. */
  private Image emergencySelectedImage = ImageLoader.getInstance().getImage(
      "conf/image/interface/game/BtnEmergencySel.png");
  /** The emergency none image. */
  private Image emergencyNoneImage = ImageLoader.getInstance().getImage(
      "conf/image/interface/game/Btn-EmergencyNone.png");
  
  /** The font color. */
  private final Color textColor = new Color(0.2f, 0.2f, 0.2f);
  
  /** The component. */
  private VentilatorComponent component;

  /** The selection button. */
  private AbstractButton selectionButton;
  
  /** The updater thread. */
  private Thread thread;
  
  /** The emergency button. */
//  private JToggleButton emergencyButton;
  private ImageToggleButton emergencyButton;
  
  /**
   * Constructor for panel.
   * 
   * @param component  The associated component.
   */
  protected VentilatorComponentPanel(VentilatorComponent component) {
    super(component);
    this.component = component;
    
    // set size and location
    setLocation(0, 256);
    setSize(315, 96);
    
    // set layout
    setLayout(null);
    
    // load image
    if (component.getSubtype() instanceof ConventionalSubtype) {
      image = ImageLoader.getInstance().getImage("conf/image/interface/game/Vtr-ConventionalVentilator.png");
    }
    else {
      image = ImageLoader.getInstance().getImage("conf/image/interface/game/Vtr-HighFrequencyVentilator.png");
    }
    
    // add Emergency Vent toggle button
    emergencyButton 
      = new ImageToggleButton(null, emergencyRolloverImage, emergencyRolloverImage, emergencyNoneImage);
    emergencyButton.setText(
        Translator.getString("button.Emergency[i18n]: Emergency Vent"));
    emergencyButton.setForeground(Color.WHITE);
    emergencyButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        VentilatorAction action = new VentilatorAction();
        action.setEmergencyFunction(emergencyButton.isSelected());
        notifyActionListeners(action);  
      }
    });
    emergencyButton.setSelected(component.isEmergencyFuction());
    emergencyButton.setLocation(145, 60);
    emergencyButton.setSize(128, 32);
    add(emergencyButton);
    
    // add toggle button
    selectionButton = new ImageToggleButton(null, rolloverImage, selectedImage, selectedImage);
    selectionButton.setToolTipText(component.getName());
    selectionButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        notifyActivationListeners();
      }
    });
    selectionButton.setLocation(0, 0);
    selectionButton.setSize(315, 96);
    add(selectionButton);
    
    // add label
    TextLabel lblVent = createTextLabels(
        Translator.getString("label.Ventilator[i18n]: Ventilator"),
        12f, 20, 0);
    add(lblVent);
  }

  /**
   * Private method to simplify & consolidate rendering config box text labels.
   *
   * @param text  String for TextLabel.
   * @param fontSize  Size of font.
   * @param x  X-coordinate.
   * @param y  Y-coordinate.
   * @return TextLabel object.
   */
  private TextLabel createTextLabels(String text, float fontSize, int x, int y) {
    TextLabel label = new TextLabel(text);
    label.setHorizontalAlignment(JLabel.LEFT);
    label.setFont(label.getFont().deriveFont(Font.BOLD, fontSize));
    label.setDrawBorder(false);
    label.setLocation(x, y);
    label.setSize(120, 45);
    return label;
  }

 /**
   * Called when the component got updated.
   */
  public void handleUpdate() {
    repaint();
  }

  /**
   * Called when panel is added.
   */
  @Override
  public void addNotify() {
    super.addNotify();
    
    // start thread
    this.thread = new Thread(this);
    this.thread.setPriority(Thread.MIN_PRIORITY);
    this.thread.start();
  }
  
  /**
   * Called when panel is removed.
   */
  @Override
  public void removeNotify() {
    // stop thread
    this.thread = null;
    
    super.removeNotify();
  }
  
  /**
   * The time updater thread.
   */
  public void run() {
    Thread currentThread = Thread.currentThread();
    while (this.thread == currentThread) {
      // update
      repaint();
      
      // wait for next update
      try {
        Thread.sleep(50);
      }
      catch (InterruptedException e) {
        Error.out(e);
      }
    }
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

    // set text properties
    g.setColor(textColor);
    g.setFont(g.getFont().deriveFont(Font.BOLD, 16f));
        
    // draw text
    String text = String.valueOf((int)(component.getFiO2() * 100)) + "%";
    g.drawString(text, 212, 34);
    
    // draws the image
    g.drawImage(image, 0, 0, this);
 
    // draw pressure
    text = ventilatorFormatter.format(component.getMeanPressure());
    g.drawString(text, 212, 60);

    // draw depending on subtype 
    if (component.getSubtype() instanceof ConventionalSubtype) {
      ConventionalSubtype subtype = (ConventionalSubtype)component.getSubtype();
      text = String.valueOf((int)subtype.getPip());
      g.drawString(text, 88, 34);
      text = String.valueOf((int)subtype.getPeep());
      g.drawString(text, 88, 60);
      text = String.valueOf((int)subtype.getRate());
      g.drawString(text, 88, 84);
    }
    else {
      HighFrequencySubtype subtype = (HighFrequencySubtype)component.getSubtype();
      text = String.valueOf((int)subtype.getAmp());
      g.drawString(text, 88, 34);
      text = String.valueOf((int)subtype.getHz());
      g.drawString(text, 88, 60);  
    }
    
    // draw blinking emergency button
    g.drawImage(emergencyNormalImage, 145, 60, this);
    if (emergencyButton.isSelected()) {
      if ((((System.nanoTime()) / 500000000) % 2) == 0) {
        g.drawImage(emergencySelectedImage, 145, 60, this);
      }
    }
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
