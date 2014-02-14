/**
 * Application.java
 */

package uk.co.bluettduncanj;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import uk.co.bluettduncanj.view.GUI;


/**
 * The starting point for the application.
 * 
 * @author Jonathan Bluett-Duncan
 */
public class Application {
  
  public static final String NAME = "java-textualyser";
  
  /**
   * Launch the application.
   */
  public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {

      @Override
      public void run() {
        try {
          new GUI().setVisible(true);
        }
        catch (Exception e) {
          showStackTraceDialog(e);
        }
      }
      
    });
  }
  
  /**
   * Show a dialog containing the stack trace of Throwable <code>e</code>.
   * The stack trace printed is exactly the same as if calling the Throwable's 
   * {@link Throwable#printStackTrace() printStackTrace()} method.
   * 
   * @param e - the Throwable whose stack trace is to be shown on the dialog.
   */
  private static void showStackTraceDialog(Throwable e) {
    StringWriter sw = new StringWriter();
    e.printStackTrace(new PrintWriter(sw));
    String stackTrace = sw.toString();
    
    String errMsg = 
        "Sorry, " + NAME + " had an unexpected crash. It produced the following message:\n\n" + stackTrace;
    
    String title = "Error";
    JOptionPane.showMessageDialog(null, errMsg, title, JOptionPane.ERROR_MESSAGE);
  }

}
