/**
 * Application.java
 */

package uk.co.bluettduncanj;

import java.awt.EventQueue;

import uk.co.bluettduncanj.view.GUI;


/**
 * @author Jonathan Bluett-Duncan
 */
public class Application {
  
  // TODO: Implement java-textualyser from scratch as a new version, using TODOs littered throughout this project's source code files.
  
  /**
   * Launch the application.
   */
  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {

      @Override
      public void run() {
        try {
          new GUI().show();
        }
        catch (Exception e) {
          e.printStackTrace();
        }
      }
      
    });
  }
  
}
