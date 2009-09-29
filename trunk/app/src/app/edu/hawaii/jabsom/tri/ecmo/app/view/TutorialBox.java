package edu.hawaii.jabsom.tri.ecmo.app.view;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import king.lib.access.ImageLoader;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import edu.hawaii.jabsom.tri.ecmo.app.gui.ImageButton;

/**
 * The tutorial box. 
 *
 * @author   Christoph Aschwanden
 * @since    Jan 12, 2009
 */
public class TutorialBox extends JPanel {

  /** The tutorial background image. */
  private Image image = ImageLoader.getInstance().getImage("conf/gui/tutorialbox.png");
  
  /** Listener for changes. */
  public static interface TutorialBoxListener {
    
    /**
     * Called when the "next" button was pressed.
     */
    void handleAction();
  };
  
  /** Listeners for changes. */
  private List<TutorialBoxListener> listeners = new ArrayList<TutorialBoxListener>();


  /** The text. */
  private JTextArea textArea;
  
  /** The button. */
  private ImageButton button;
  
  
  /**
   * Constructor for panel. 
   */
  public TutorialBox() {    
    // set look
    setOpaque(false);
    
    // no layout
    setLayout(new FormLayout("4px, fill:360px, 2px, 75px, 8px"
                           , "4px, fill:36px, 2px, 30px, 4px"
      ));
    CellConstraints cc = new CellConstraints();
      
    // add the text area
    textArea = new JTextArea();
    textArea.setWrapStyleWord(true);
    textArea.setLineWrap(true);
    textArea.setEditable(false);
    textArea.setOpaque(false);
    textArea.setFont(textArea.getFont().deriveFont(Font.BOLD, 12f));
    textArea.setMargin(new Insets(5, 5, 5, 5));
    JScrollPane scrollPane = new JScrollPane(textArea);
    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    scrollPane.setOpaque(false);
    scrollPane.getViewport().setOpaque(false);
    add(textArea, cc.xyw(2, 2, 3));
    
    // add the button
    Image nextButtonImage = ImageLoader.getInstance().getImage("conf/gui/Btn-Next.png");    
    Image nextButtonRolloverImage = ImageLoader.getInstance().getImage("conf/gui/Btn-NextRol.png");
    Image nextButtonSelectedImage = ImageLoader.getInstance().getImage("conf/gui/Btn-NextSel.png");
    button = new ImageButton(nextButtonImage, nextButtonRolloverImage, nextButtonSelectedImage);
    button.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        for (TutorialBoxListener listener: listeners) {
          listener.handleAction();
        }
      }     
    });
    add(button, cc.xy(4, 4));
  }
  
  /**
   * Returns the text displayed.
   * 
   * @return  The text.
   */
  public String getText() {
    return textArea.getText();
  }
  
  /**
   * Updates the text.
   * 
   * @param text  The text.
   */
  public void setText(String text) {
    textArea.setText(text);
  }
  
  /**
   * Returns true if the next button is shown.
   * 
   * @return  True for shown.
   */
  public boolean isShowNext() {
    return button.isVisible();
  }
  
  /**
   * True to show the next button.
   * 
   * @param show  True to show.
   */
  public void setShowNext(boolean show) {
    button.setVisible(show);
  }
  
  /**
   * Adds a listener.
   * 
   * @param listener  The listener to add.
   */
  public void addTutorialBoxListener(TutorialBoxListener listener) {
    listeners.add(listener);
  }
  
  /**
   * Removes a listener.
   * 
   * @param listener  The listener to add.
   */
  public void removeTutorialBoxListener(TutorialBoxListener listener) {
    listeners.remove(listener);
  }
  
  /**
   * Paints this component.
   * 
   * @param g  Where to draw to.
   */
  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    // draw base
    g.drawImage(image, 0, 0, this);
  }
}
