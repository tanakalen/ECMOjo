package edu.hawaii.jabsom.tri.ecmo.app.state;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import edu.hawaii.jabsom.tri.ecmo.app.gui.ImageButton;
import edu.hawaii.jabsom.tri.ecmo.app.gui.TextLabel;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import king.lib.access.ImageLoader;
import king.lib.out.InternetTextPane;

/**
 * The contact state panel. 
 *
 * @author Christoph Aschwanden
 * @since February 22, 2010
 */
public class ContactStatePanel extends JPanel {

  /** The panel image. */
  private Image background = ImageLoader.getInstance().getImage("conf/image/interface/contact/Base.jpg");
    
  /** The info text. */
  private final String infoText = 
        "<h1>Information &amp; Overview</h1>"  
      + "ECMOjo is a simulator and trainer for extracorporeal membrane oxygenation (ECMO). " 
      + "It consists of a graphical user interface to allow interaction and train ECMO practitioners, and has been " 
      + "developed by Telehealth Research Institute (TRI), University of Hawaii. "
      + "<font color=\"#e00000\"><b>ECMOjo</b></font> contains the word ECMO and mojo "
      + "(<b><font color=\"#e00000\">magic charm</font></b>). In intensive care medicine, extracorporeal membrane " 
      + "oxygenation (ECMO) is an extracorporeal technique of providing both cardiac and respiratory support " 
      + "oxygen to patients whose heart and lungs are so severely diseased or damaged that they can no longer "
      + "serve their function.<br>"
      + "<br>"
      + "ECMOjo is open source software hosted on SourceForge. The program has been implemented in Java and "
      + "can be run as either a standalone desktop application or via the Internet. "
      + "ECMOjo has been developed by Telehealth Research Institute, University of Hawaii.<br>"
      + "<br>"
      + "<h1>Credits</h1>"
      + "Lawrence P. Burgess, MD, Principal Investigator<br>"
      + "Mark T. Ogino, MD, Project Manager<br>"
      + "Christoph Aschwanden, PhD, Lead Developer<br>"
      + "Kin Lik Wang (Alex), Developer<br>"
      + "Len Tanaka, MD, Developer<br>"
      + "Kaleiohu Lee, Artist<br>"
      + "Donald McCurnin, MD, Godfather<br>"
      + "Kristen Costales, CCP, Advisor<br>"
      + "Melinda Hamilton, MD, Advisor<br>"
      + "Kent Kelly, CCP, Advisor<br>"
      + "Melody Kilcommons, RNC, Advisor<br>"
      + "John Lutz, Advisor<br>"
      + "<br>"
      + "<h1>Acknowledgement</h1>"
      + "This project has been supported by grant No. W81XWH-06-2-0061 awarded by Department of Defense (DoD), "
      + "United States of America.<br>"
      + "<br>"
      + "<h1>Website &amp; Contact</h1>"
      + "Visit our website: "
      + "<a href=\"http://ecmojo.sourceforge.net\">http://ecmojo.sourceforge.net</a><br>"
      + "Contact us at: "
      + "<a href=\"mailto:telemed@hawaii.edu\">telemed@hawaii.edu</a><br>"
      + "<a href=\"http://ecmojo.sourceforge.net\">"
      + "<img src=\"http://ecmojo.sourceforge.net/image/interface/minilogo.png\"></a><br>";

    
  /**
   * Constructor for panel. 
   * 
   * @param state  The state for this panel.
   */
  public ContactStatePanel(final ContactState state) {
    // set look
    setOpaque(true);
    
    // set layout
    setLayout(null);
    
    // add text label
    TextLabel textLabel = new TextLabel("Contact Us");
    textLabel.setHorizontalAlignment(JLabel.CENTER);
    textLabel.setFont(textLabel.getFont().deriveFont(Font.BOLD, 36f)); 
    textLabel.setLocation(100, 16);
    textLabel.setSize(600, 50);
    add(textLabel);
    
    // add text panel
    InternetTextPane infoArea = new InternetTextPane();
    infoArea.setDefaultLineSpacing(0.25f);
    infoArea.addHTML(infoText);
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
