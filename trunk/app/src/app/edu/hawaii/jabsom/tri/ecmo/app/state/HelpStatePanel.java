package edu.hawaii.jabsom.tri.ecmo.app.state;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import edu.hawaii.jabsom.tri.ecmo.app.gui.ImageButton;
import edu.hawaii.jabsom.tri.ecmo.app.gui.TextLabel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import king.lib.access.ImageLoader;
import king.lib.out.AdvancedTextPane;
import king.lib.out.InternetTextPane;

/**
 * The result state panel. 
 *
 * @author   Christoph Aschwanden
 * @since    August 19, 2008
 */
public class HelpStatePanel extends JPanel {

  /** The panel image. */
  private Image background = ImageLoader.getInstance().getImage("conf/image/interface/result/Base.jpg");
    
  /** The info text. */
  private final String infoText = 
        "INFORMATION & OVERVIEW\n"  
      + "ECMOjo is a simulator and trainer for extracorporeal membrane oxygenation (ECMO). " 
      + "It consists of a graphical user interface to allow interaction and train ECMO practitioners, and has been " 
      + "developed by Telehealth Research Institute (TRI), University of Hawaii. ECMOjo contains the word ECMO and " 
      + "mojo (magic charm). In intensive care medicine, extracorporeal membrane oxygenation (ECMO) is an " 
      + "extracorporeal technique of providing both cardiac and respiratory support oxygen to patients whose " 
      + "heart and lungs are so severely diseased or damaged that they can no longer serve their function.\n"
      + "\n"
      + "ECMOjo is open source software hosted on SourceForge. The program has been implemented in Java and "
      + "can be run as either a standalone desktop application or via the Internet. "
      + "ECMOjo has been developed by Telehealth Research Institute, University of Hawaii.\n"
      + "\n"
      + "CREDITS\n"
      + "Lawrence P. Burgess, MD, Principal Investigator\n"
      + "Mark T. Ogino, MD, Project Manager\n"
      + "Christoph Aschwanden, PhD, Lead Developer\n"
      + "Kin Lik Wang (Alex), Developer\n"
      + "Len Tanaka, MD, Developer\n"
      + "Kaleiohu Lee, Artist\n"
      + "Donald McCurnin, MD, Godfather\n"
      + "Kristen Costales, CCP, Advisor\n"
      + "Melinda Hamilton, MD, Advisor\n"
      + "Kent Kelly, CCP, Advisor\n"
      + "Melody Kilcommons, RNC, Advisor\n"
      + "John Lutz, Advisor\n"
      + "\n"
      + "ACKNOWLEDGEMENT\n"
      + "This project has been supported by grant No. W81XWH-06-2-0061 awarded by Department of Defense (DoD), "
      + "United States of America.\n"
      + "\n"
      + "WEBSITE\n"
      + "Please visit the web site for details:\n";

    
  /**
   * Constructor for panel. 
   * 
   * @param state  The state for this panel.
   */
  public HelpStatePanel(final HelpState state) {
    // set look
    setOpaque(true);
    
    // set layout
    setLayout(null);
    
    // add text label
    TextLabel textLabel = new TextLabel("ECMO Help");
    textLabel.setHorizontalAlignment(JLabel.CENTER);
    textLabel.setFont(textLabel.getFont().deriveFont(Font.BOLD, 36f)); 
    textLabel.setLocation(100, 16);
    textLabel.setSize(600, 50);
    add(textLabel);
    
    // add text panel
    InternetTextPane infoArea = new InternetTextPane();
    infoArea.setDefaultSize(13);
    infoArea.setDefaultFont(AdvancedTextPane.FONT_TYPE_SERIF);
    infoArea.addHTML(infoText);
    infoArea.addLink("http://ecmojo.sourceforge.net/", Color.BLUE, AdvancedTextPane.DECORATION_UNDERLINE);
    JScrollPane infoScroll = new JScrollPane(infoArea);
    infoScroll.setLocation(15, 95);
    infoScroll.setSize(770, 450);
    add(infoScroll);
    
    // add done button
    Image okButtonImage = ImageLoader.getInstance().getImage("conf/gui/Btn-Ok.png");    
    Image okButtonRolloverImage = ImageLoader.getInstance().getImage("conf/gui/Btn-OkRol.png");
    Image okButtonSelectedImage = ImageLoader.getInstance().getImage("conf/gui/Btn-OkSel.png");
    ImageButton okButton = new ImageButton(okButtonImage, okButtonRolloverImage, okButtonSelectedImage);
    okButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        // proceed to the next state
        state.menuState();
      }
    });
    okButton.setSize(75, 30);
    okButton.setLocation(665, 560);
    add(okButton);
  }
  
  /**
   * Paints this component.
   * 
   * @param g  Where to draw to.
   */
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    
    // draws the image as background
    g.drawImage(background, 0, 0, this);
  }
}
