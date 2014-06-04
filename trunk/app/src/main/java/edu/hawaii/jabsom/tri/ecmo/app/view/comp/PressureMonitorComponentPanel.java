package edu.hawaii.jabsom.tri.ecmo.app.view.comp;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;

import king.lib.access.ImageLoader;
import king.lib.out.Error;
import king.lib.util.Translator;
import edu.hawaii.jabsom.tri.ecmo.app.gui.ImageToggleButton;
import edu.hawaii.jabsom.tri.ecmo.app.gui.TextLabel;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.PressureMonitorComponent;

/**
 * The ACT component panel. 
 *
 * @author   Christoph Aschwanden
 * @since    September 4, 2008
 */
public class PressureMonitorComponentPanel extends ComponentPanel implements Runnable {

  /** The rollover image. */
  private Image rolloverImage 
    = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-PressureMonitorRol.png");
  /** The selected image. */
  private Image selectedImage 
    = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-PressureMonitorSel.png");

  /** The font color. */
  private final Color textColor = new Color(0.2f, 0.2f, 0.2f);

  /** The red alert image. */
  private Image redAlertImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Alrt-RedSmall.png");
  /** The yellow alert image. */
  private Image yellowAlertImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Alrt-YellowSmall.png");

  /** The black alert image. */
  private Image blackAlertImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Alrt-BlackSmall.png");
  
  /** The component. */
  private PressureMonitorComponent component;

  /** The selection button. */
  private AbstractButton selectionButton;

  /** The updater thread. */
  private Thread thread;
  
  /**
   * Constructor for panel.
   * 
   * @param component  The associated component.
   */
  protected PressureMonitorComponentPanel(PressureMonitorComponent component) {
    super(component);
    this.component = component;
    
    // set size and location
    setLocation(645, 94);
    setSize(132, 154);
    
    // set layout
    setLayout(null);
    setOpaque(false);
    
    // add toggle button
    selectionButton = new ImageToggleButton(null, rolloverImage, selectedImage, selectedImage);
    selectionButton.setToolTipText(component.getName());
    selectionButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        notifyActivationListeners();
      }
    });
    selectionButton.setLocation(0, 0);
    selectionButton.setSize(132, 154);
    add(selectionButton);
    
    // add labels: venous, pre, & post
    TextLabel lblVenous = createTextLabels(
        Translator.getString("label.PressureMonitorVenous[i18n]: veno"),
        8f, 7, 25);
    add(lblVenous);
    TextLabel lblPre = createTextLabels(
        Translator.getString("label.PressureMonitorPre[i18n]: pre"),
        8f, 7, 65);
    add(lblPre);
    TextLabel lblPost = createTextLabels(
        Translator.getString("label.PressureMonitorPost[i18n]: post"),
        8f, 7, 105);
    add(lblPost);
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
    label.setHorizontalAlignment(JLabel.CENTER);
    label.setFont(label.getFont().deriveFont(Font.BOLD, fontSize));
    label.setDrawShadow(true);
    label.setDrawBorder(false);
    label.setLocation(x, y);
    label.setSize(30, 15);
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
    // set antialised text
    Graphics2D g2 = (Graphics2D)g;
    g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                        RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

    if (component.isVenousPressureAlarm()){
      // draw blinking red light
      if ((((System.nanoTime()) / 500000000) % 2) == 0) {
        g.drawImage(redAlertImage, 14, 33, this);
      }
      else {
        g.drawImage(blackAlertImage, 14, 33, this);
      }
    }
    else if (component.isVenousPressureWarning()){
      // draw blinking yellow light
      if ((((System.nanoTime()) / 500000000) % 2) == 0) {
        g.drawImage(yellowAlertImage, 14, 33, this);
      }
      else {
        g.drawImage(blackAlertImage, 14, 33, this);
      }
    }
    
    if (component.isPreMembranePressureAlarm()){
      // draw blinking red light
      if ((((System.nanoTime()) / 500000000) % 2) == 0) {
        g.drawImage(redAlertImage, 14, 73, this);
      }
      else {
        g.drawImage(blackAlertImage, 14, 73, this);
      }
    }
    else if (component.isPreMembranePressureWarning()){
      // draw blinking yellow light
      if ((((System.nanoTime()) / 500000000) % 2) == 0) {
        g.drawImage(yellowAlertImage, 14, 73, this);
      }
      else {
        g.drawImage(blackAlertImage, 14, 73, this);
      }
    }
    
    if (component.isPostMembranePressureAlarm()){
      // draw blinking red light
      if ((((System.nanoTime()) / 500000000) % 2) == 0) {
        g.drawImage(redAlertImage, 14, 113, this);
      }
      else {
        g.drawImage(blackAlertImage, 14, 113, this);
      }
    }
    else if (component.isPostMembranePressureWarning()){
      // draw blinking yellow light
      if ((((System.nanoTime()) / 500000000) % 2) == 0) {
        g.drawImage(yellowAlertImage, 14, 113, this);
      }
      else {
        g.drawImage(blackAlertImage, 14, 113, this);
      }
    }
    
    // draw text for max and min
    g.setColor(textColor);
    g.setFont(g.getFont().deriveFont(Font.BOLD, 16f));
    
    // draw text
    String text = String.valueOf((int)component.getVenousPressure());
    g.drawString(text, 42, 40);
    text = String.valueOf((int)component.getPreMembranePressure());
    g.drawString(text, 42, 80);
    text = String.valueOf((int)component.getPostMembranePressure());
    g.drawString(text, 42, 120);
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
