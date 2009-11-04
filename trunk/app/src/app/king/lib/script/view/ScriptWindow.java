package king.lib.script.view;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;

import king.lib.script.model.Script;
import king.lib.script.model.ScriptType;

/**
 * A script window.
 *
 * @author noblemaster
 * @since November 2, 2009
 */
public class ScriptWindow extends JFrame {

  /** The script editor panel. */
  private ScriptEditorPanel editorPanel;
  
  
  /**
   * The constructor.
   */
  public ScriptWindow() {
    // set title and close behavior
    setTitle("Script Writer");
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
    ScriptWindow window = new ScriptWindow();
    window.setSize(400, 400);
    window.setLocationRelativeTo(null);
    
    // set the script
    Script script = new Script();
    script.setLang(ScriptType.PNUTS);
    window.setScript(script);
    
    // show the window...
    window.setVisible(true); 
  }
}
