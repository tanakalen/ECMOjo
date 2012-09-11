package king.lib.script.view;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;

import king.lib.script.model.Language;
import king.lib.script.model.Script;

/**
 * A script window.
 *
 * @author noblemaster
 * @since November 2, 2009
 */
public class ScriptEditorWindow extends JFrame {

  /** The script editor panel. */
  private ScriptEditorPanel editorPanel;
  
  
  /**
   * The constructor.
   */
  public ScriptEditorWindow() {
    // set title and close behavior
    setTitle("Script Editor");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // set layout
    getContentPane().setBackground(Color.BLACK);
    getContentPane().setLayout(new BorderLayout());
    
    // create the editor panel
    editorPanel = new ScriptEditorPanel();
    getContentPane().add(editorPanel, BorderLayout.CENTER);
  }
  
  /**
   * Returns the script.
   * 
   * @return  The script.
   */
  public Script getScript() {
    return editorPanel.getScript();
  }
  
  /**
   * Sets a script.
   * 
   * @param script  The script.
   */
  public void setScript(Script script) {
    editorPanel.setScript(script);
  }

  /**
   * Creates and displays a window for scripting.
   * 
   * @param args  The arguments.
   */
  public static void main(String[] args) {
    // create the window
    ScriptEditorWindow window = new ScriptEditorWindow();
    window.setSize(600, 400);
    window.setLocationRelativeTo(null);
    
    // set the script
    Script script = new Script();
    script.setLang(Language.PNUTS.getName());
    window.setScript(script);
    
    // show the window...
    window.setVisible(true); 
  }
}
