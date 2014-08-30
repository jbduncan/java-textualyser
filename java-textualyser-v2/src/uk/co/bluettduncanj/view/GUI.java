/**
 * GUI.java
 */

package uk.co.bluettduncanj.view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.miginfocom.swing.MigLayout;


/**
 * 
 * 
 * @author Jonathan Bluett-Duncan
 */
public class GUI extends JFrame {

  /** An automatically generated serial version identifier. */
  private static final long serialVersionUID = 1433118288495239121L;
  
  private JPanel contentPane;
  
  /**
   * Create the frame.
   */
  public GUI() {
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setBounds(100, 100, 450, 300);
    contentPane = new JPanel();
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    setContentPane(contentPane);
    contentPane.setLayout(new MigLayout("", "[]", "[]"));
  }

}
