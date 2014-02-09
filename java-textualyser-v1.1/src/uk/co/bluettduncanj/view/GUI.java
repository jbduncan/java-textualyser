/**
 * GUI.java
 */

package uk.co.bluettduncanj.view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;


/**
 * @author Jonathan Bluett-Duncan
 */
public class GUI extends JFrame {

  /** serialVersionUID */
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
    GridBagLayout gbl_contentPane = new GridBagLayout();
    gbl_contentPane.columnWidths = new int[]{0};
    gbl_contentPane.rowHeights = new int[]{0};
    gbl_contentPane.columnWeights = new double[]{Double.MIN_VALUE};
    gbl_contentPane.rowWeights = new double[]{Double.MIN_VALUE};
    contentPane.setLayout(gbl_contentPane);
  }

}
