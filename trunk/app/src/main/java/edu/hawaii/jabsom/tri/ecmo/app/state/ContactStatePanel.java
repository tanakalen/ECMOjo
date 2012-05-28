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
        "<h1>General Feedback</h1>"  
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
      + " - <a href=\"https://sourceforge.net/tracker/?func=browse&group_id=241657&atid=1116905\">Bug Tracker</a><br>"
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
    TextLabel textLabel = new TextLabel(Translator.getString("title.ContactUs[i18n]: Contact Us"));
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
