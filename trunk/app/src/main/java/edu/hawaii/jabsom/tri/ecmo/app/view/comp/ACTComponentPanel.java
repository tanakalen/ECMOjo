package edu.hawaii.jabsom.tri.ecmo.app.view.comp;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;

import javax.swing.ButtonGroup;

import king.lib.access.ImageLoader;

import edu.hawaii.jabsom.tri.ecmo.app.gui.ImageToggleButton;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.ACTComponent;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.ACTComponent.ACT;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import king.lib.util.Translator;

/**
 * The ACT component panel. 
 *
 * @author   Christoph Aschwanden
 * @since    September 4, 2008
 */
public class ACTComponentPanel extends ComponentPanel {

  /** The font color. */
  private final Color textColor = new Color(0.2f, 0.2f, 0.2f);

  /** The component. */
  private ACTComponent component;
  
  /** The selection button. */
  private ImageToggleButton selectionButton;

  
  /**
   * Constructor for panel.
   * 
   * @param component  The associated component.
   */
  protected ACTComponentPanel(ACTComponent component) {
    super(component);
    this.component = component;
    
    // set size and location
    setLocation(108, 546);
    setSize(136, 52);
    
    // set layout
    setLayout(null);

    // add toggle button
    Image normalImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-ACT.png");
    Image rolloverImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-ACTRol.png");
    Image selectedImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-ACTSel.png");
    selectionButton = new ImageToggleButton(normalImage, 0, rolloverImage, 0, selectedImage, 4, selectedImage, 4);
    selectionButton.setText(Translator.getString("text.ACT[i18n]: ACT"));
    selectionButton.setFont(new Font("Arial", Font.PLAIN, 18));
    selectionButton.setForeground(Color.MAGENTA);
    selectionButton.setRolloverColor(Color.GREEN);
    selectionButton.setToolTipText(component.getName());
    selectionButton.addChangeListener(new ChangeListener() {
        @Override
        public void stateChanged(ChangeEvent e) {
            if (selectionButton.isSelected()) {
              selectionButton.setForeground(Color.GRAY);
              selectionButton.setRolloverColor(Color.GRAY);
              selectionButton.setRolloverEnabled(false);
              notifyActivationListeners();
            }
            else {
              selectionButton.setRolloverEnabled(true);
              selectionButton.setForeground(Color.MAGENTA);
              selectionButton.setRolloverColor(Color.GREEN);
            }            
        }
    });
    selectionButton.setLocation(10, 0);
    selectionButton.setSize(64, 52);
    add(selectionButton);
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
    // set antialised text
    Graphics2D g2 = (Graphics2D)g;
    g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                        RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

    // set text properties
    g.setColor(textColor);
    g.setFont(g.getFont().deriveFont(Font.BOLD, 16f));
    
    // draw text
    ACT act = component.getACT();
    String text;
    if (act != null) {
      text = String.valueOf(Math.round(act.getValue()));
    }
    else {
      text = "---";
    }
    g.drawString(text, 88, 34);
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
