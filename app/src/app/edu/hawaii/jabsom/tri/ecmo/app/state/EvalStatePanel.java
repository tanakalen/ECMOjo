package edu.hawaii.jabsom.tri.ecmo.app.state;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;

import king.lib.script.view.ScriptConsolePanel;

import edu.hawaii.jabsom.tri.ecmo.app.control.script.Output;

/**
 * The eval state panel. 
 *
 * @author Christoph Aschwanden
 * @since December 10, 2009
 */
public class EvalStatePanel extends GameStatePanel {

  /** The console. */
  private JFrame consoleWindow;
  /** The console panel. */
  private ScriptConsolePanel consolePanel;
  
  
  /**
   * Constructor for panel. 
   * 
   * @param state  The state for this panel.
   */
  public EvalStatePanel(EvalState state) {
    super(state);
  }

  /**
   * Called when panel is added.
   */
  @Override
  public void addNotify() {
    super.addNotify();
    
    // display console
    consoleWindow = new JFrame();
    consoleWindow.setTitle("Console Output");
    consoleWindow.getContentPane().setBackground(Color.BLACK);
    consoleWindow.getContentPane().setLayout(new BorderLayout());
    consolePanel = new ScriptConsolePanel();
    consoleWindow.getContentPane().add(consolePanel, BorderLayout.CENTER);
    consoleWindow.setSize(300, 300);
    consoleWindow.setLocationRelativeTo(null);
    consoleWindow.setVisible(true);
  }
  
  /**
   * Called when panel is removed.
   */
  @Override
  public void removeNotify() {  
    // remove console
    consoleWindow.setVisible(false);
    consoleWindow.dispose();
    consoleWindow = null;
    
    super.removeNotify();
  }

  /** 
   * Called when output is available.
   * 
   * @param output  The output.
   */
  @Override
  public void handleOutput(Output output) {
    ScriptConsolePanel consolePanel = this.consolePanel;
    if (consolePanel != null) {
      if (output.isError()) {
        consolePanel.addError(output.getMessage() + "\n");
      }
      else {
        consolePanel.addOutput(output.getMessage() + "\n"); 
      }
    }
  }
}
