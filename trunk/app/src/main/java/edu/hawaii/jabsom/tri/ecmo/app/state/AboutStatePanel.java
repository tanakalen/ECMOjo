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
import king.lib.util.Translator;

/**
 * The about state panel. 
 *
 * @author Christoph Aschwanden
 * @since August 19, 2008
 */
public class AboutStatePanel extends JPanel {

  /** The panel image. */
  private Image background = ImageLoader.getInstance().getImage("conf/image/interface/Background.png");
    
  /** The info text. */
//  private final String infoText;

    
  /**
   * Constructor for panel. 
   * 
   * @param state  The state for this panel.
   */
  public AboutStatePanel(final AboutState state) {
    // set look
    setOpaque(true);
    
    // set layout
    setLayout(null);
    
    // add text label
    TextLabel textLabel = new TextLabel(Translator.getString("title.AboutECMOjo[i18n]: About ECMOjo"));
    textLabel.setHorizontalAlignment(JLabel.CENTER);
    textLabel.setFont(textLabel.getFont().deriveFont(Font.BOLD, 36f)); 
    textLabel.setLocation(100, 16);
    textLabel.setSize(600, 50);
    add(textLabel);
    
    // add text panel
    // TODO: Can this text panel come from README and stitched in?
    InternetTextPane infoArea = new InternetTextPane();
    infoArea.setDefaultLineSpacing(0.25f);
    infoArea.addHTML(
        Translator.getString("text.infoText[i18n]: "
            + "<h1>Information &amp; Overview</h1>"  
            + "ECMOjo is a simulator and trainer for extracorporeal membrane oxygenation (ECMO). " 
            + "It consists of a graphical user interface to allow interaction and train ECMO practitioners, "
            + "and has been developed by Telehealth Research Institute (TRI), University of Hawaii. "
            + "<font color=\"#e00000\"><b>ECMOjo</b></font> contains the word ECMO and mojo "
            + "(<b><font color=\"#e00000\">magic charm</font></b>). "
            + "In intensive care medicine, extracorporeal membrane " 
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
            + "Christoph Aschwanden, PhD, Developer<br>"
            + "Kin Lik Wang (Alex), Developer<br>"
            + "Len Y. Tanaka, MD, Developer<br>"
            + "Kaleiohu Lee, Artist<br>"
            + "Donald McCurnin, MD, Godfather<br>"
            + "Kristen Costales, CCP, Advisor<br>"
            + "Melinda Hamilton, MD, Advisor<br>"
            + "Kent Kelly, CCP, Advisor<br>"
            + "Melody Kilcommons, RNC, Advisor<br>"
            + "John Lutz, Advisor<br>"
            + "Takanari Ikeyama, MD, Translation (Japanese)<br>"
            + "Yuko Shiima, MD, Translation (Japanese)<br>"
            + "Felipe Amaya, Translation (Spanish)<br>"
            + "<br>"
            + "<h1>Acknowledgment</h1>"
            + "This project has been supported by grant No. W81XWH-06-2-0061 awarded by Department of Defense (DoD), "
            + "United States of America.<br><br>"
            )
        +
        Translator.getString("text.contact[i18n]: "
            + "<h1>General Feedback</h1>"  
            + "For general feedback and feature requests, contact the various team members via email or join our "
            + "discussion forum:<br>"
            + " - <a href=\"http://sourceforge.net/projects/ecmojo/forums/forum/874361\">Open Discussion &amp; "
            + " Feature Requests</a><br>"
            + " - <a href=\"http://ecmojo.sourceforge.net/about.php\">Contact List</a><br>"
            + "<br>"
            + "<h1>Bug Reports &amp; Support</h1>"  
            + "For bug reports and support, visit the technical support forum, contact us via email or submit "
            + "problems to our bug tracker:<br>"
            + " - <a href=\"https://sourceforge.net/projects/ecmojo/forums/forum/874362\">Technical Support</a><br>"
            + " - <a href=\"https://sourceforge.net/tracker/?func=browse&group_id=241657&atid=1116905\">"
            + "Bug Tracker</a><br>"
            + " - Email: <a href=\"mailto:808ecmojo@gmail.com\">808ecmojo@gmail.com</a><br>"
            + "<br>"
            + "<h1>Contribute</h1>"  
            + "ECMOjo is open source software hosted on SourceForge. Contact us to join the effort:<br>"
            + " - <a href=\"https://sourceforge.net/projects/ecmojo/forums/forum/874363\">Developers Forum</a><br>"
            + " - Email: <a href=\"mailto:808ecmojo@gmail.com\">808ecmojo@gmail.com</a><br>"
            + "<br>"
            + "<h1>Website &amp; Email</h1>"
            + "Visit our website: "
            + "<a href=\"http://ecmojo.sourceforge.net\">http://ecmojo.sourceforge.net</a><br>"
            + "Contact us at: "
            + "<a href=\"mailto:808ecmojo@gmail.com\">808ecmojo@gmail.com</a><br>"
            + "<a href=\"http://ecmojo.sourceforge.net\">"
            + "<img src=\"http://ecmojo.sourceforge.net/image/interface/minilogo.png\"></a><br>"
            )
    );
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
