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

import king.lib.access.ImageLoader;

import edu.hawaii.jabsom.tri.ecmo.app.gui.ImageToggleButton;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.ACTComponent;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.ACTComponent.ACT;

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
  private AbstractButton selectionButton;

  
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
    Image normalImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-Act.png");
    Image rolloverImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-ActRol.png");
    Image selectedImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-ActSel.png");
    selectionButton = new ImageToggleButton(normalImage, rolloverImage, selectedImage, selectedImage);
    selectionButton.setToolTipText(component.getName());
    selectionButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        notifyActivationListeners();
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
