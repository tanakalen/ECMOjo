package edu.hawaii.jabsom.tri.ecmo.app.view.comp;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import king.lib.access.ImageLoader;
import king.lib.out.Error;
import king.lib.util.Translator;

import edu.hawaii.jabsom.tri.ecmo.app.control.action.HeaterAction;
import edu.hawaii.jabsom.tri.ecmo.app.control.action.ReplaceHeaterAction;
import edu.hawaii.jabsom.tri.ecmo.app.gui.ImageButton;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.HeaterComponent;
import edu.hawaii.jabsom.tri.ecmo.app.view.dialog.DialogBase;
import edu.hawaii.jabsom.tri.ecmo.app.view.dialog.DialogBase.DialogListener;
import edu.hawaii.jabsom.tri.ecmo.app.view.dialog.DialogBase.DialogOption;
import edu.hawaii.jabsom.tri.ecmo.app.view.dialog.DialogBase.DialogResult;
import edu.hawaii.jabsom.tri.ecmo.app.view.dialog.DialogBase.DialogType;

/**
 * The detail panel. 
 *
 * @author   Christoph Aschwanden
 * @since    September 12, 2008
 */
public class HeaterDetailPanel extends DetailPanel implements Runnable {

  /** The detail image. */
  private Image detailImage 
    = ImageLoader.getInstance().getImage("conf/image/interface/game/Detail-Heater.png");

  /** The red alert image. */
  private Image redAlertImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Alrt-RedMedium.png");

  /** The black alert image. */
  private Image blackAlertImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Alrt-BlackMedium.png");
 
  /** The font color. */
  private final Color textColor = new Color(0.2f, 0.2f, 0.2f);
  
  /** The component. */
  private HeaterComponent component;
  
  /** The updater thread. */
  private Thread thread;
  
  /**
   * Constructor for panel.
   * 
   * @param component  The associated component.
   */
  protected HeaterDetailPanel(final HeaterComponent component) {
    super(component);
    this.component = component;
    
    // set size and location
    setLocation(289, 81);
    setSize(320, 320);
    
    // set layout and look
    setLayout(null);
    setOpaque(false);
    
    // add "+" button
    Image incButtonImage 
      = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-Up.png");
    Image incButtonRolloverImage 
      = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-UpRol.png");
    Image incButtonSelectedImage 
      = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-UpSel.png");
    ImageButton incButton = new ImageButton(incButtonImage, incButtonRolloverImage, incButtonSelectedImage);  
    incButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        HeaterAction action = new HeaterAction();
        action.setTemperature(component.getTemperature() + 1);
        notifyActionListeners(action);
      }
    });
    incButton.setLocation(82, 85);
    incButton.setSize(32, 32);
    add(incButton); 

    // add "-" button
    Image decButtonImage 
      = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-Down.png");    
    Image decButtonRolloverImage 
      = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-DownRol.png");
    Image decButtonSelectedImage 
      = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-DownSel.png");
    ImageButton decButton = new ImageButton(decButtonImage, decButtonRolloverImage, decButtonSelectedImage);    
    decButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        HeaterAction action = new HeaterAction();
        action.setTemperature(component.getTemperature() - 1);
        notifyActionListeners(action);
      }
    });
    decButton.setLocation(82, 117);
    decButton.setSize(32, 32);
    add(decButton); 
    
    // add replace heater button
    Image replaceHeaterNormalImage = ImageLoader.getInstance().getImage(
        Translator.getString("image.ButtonReplaceHeater[i18n]: conf/image/interface/game/Btn-ReplaceHeater.png"));
    Image replaceHeaterRolloverImage = ImageLoader.getInstance().getImage(
        Translator.getString("image.ButtonReplaceHeaterRol[i18n]: conf/image/interface/game/Btn-ReplaceHeaterRol.png"));
    Image replaceHeaterSelectedImage = ImageLoader.getInstance().getImage(
        Translator.getString("image.ButtonReplaceHeaterSel[i18n]: conf/image/interface/game/Btn-ReplaceHeaterSel.png"));
    ImageButton replaceHeaterButton 
        = new ImageButton(replaceHeaterNormalImage, replaceHeaterRolloverImage, replaceHeaterSelectedImage);
    replaceHeaterButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        boolean problem = component.isBroken();
        
        // output dialog
        if (problem) {
          // fixed
          DialogBase.showDialog(HeaterDetailPanel.this, DialogType.SUCCESS, DialogOption.OK
              , Translator.getString("title.HeaterDialogProblem[i18n]: Problem Fixed")
              , Translator.getString("text.HeaterDialogProblem[i18n]: Good catch!"
                  + " The heater was broken and has been replaced."),
              new DialogListener() {
                public void handleResult(DialogResult result) {
                  ReplaceHeaterAction action = new ReplaceHeaterAction();
                  notifyActionListeners(action);                  
                }
          });
        }
        else {
          // no problem = nothing done
          DialogBase.showDialog(HeaterDetailPanel.this, DialogType.PLAIN, DialogOption.OK
              , Translator.getString("title.HeaterDialogNoProblem[i18n]: No Problem Detected")
              , Translator.getString("text.HeaterDialogNoProblem[i18n]: "
                  + "Although the heater has now been replaced, no problem was detected. " 
                  + "The old heater was working just fine.",
              new DialogListener() {
                public void handleResult(DialogResult result) {
                  ReplaceHeaterAction action = new ReplaceHeaterAction();
                  notifyActionListeners(action);
                }
          });
        }
      }
    });
    replaceHeaterButton.setLocation(70, 186);
    replaceHeaterButton.setSize(192, 32);
    add(replaceHeaterButton);
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

    // draw base
    g2.drawImage(detailImage, 0, 0, this);

    if (component.isAlarm()){
      // draw blinking red light
      if ((((System.nanoTime()) / 500000000) % 2) == 0) {
        g.drawImage(redAlertImage, 82, 54, this);
      }
      else {
        g.drawImage(blackAlertImage, 82, 54, this);        
      }      
    }
    
    // set text properties
    g.setColor(textColor);
    g.setFont(g.getFont().deriveFont(Font.BOLD, 40f));
    
    // draw text
    int temperature = (int)component.getTemperature();
    if (component.isBroken()) {
      temperature = 37;
    }
    String text = String.valueOf(temperature) + "\u00B0C";
    g.drawString(text, 136, 118);
  }
}
