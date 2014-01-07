/**
 * GUI.java
 */

package uk.co.bluettduncanj;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.eclipse.wb.swing.FocusTraversalOnArray;

import java.awt.Font;

/**
 * @author Jonathan Bluett-Duncan
 */
public class GUI {

  private FileAnalyser  fileAnalyser;
  
  private JFrame        frmMainWindow;
  private JButton       btnChooseFile;
  private JTextField    txtChooseFile;
  private JTextField    txtPattern;
  private JButton       btnAnalyse;
  private JLabel        lblAnalyseStatus;
  private JCheckBox     chbxTextOCs;
  private JCheckBox     chbxAvgLens;
  private JCheckBox     chbxFreqs;
  private JCheckBox     chbxSaveLogFile;
  private JTextArea     txtAreaStatsOutput;

  /**
   * Create the application.
   */
  public GUI() {
    this.initialize();
  }

  /**
   * A private method that is called by btnAnalyse's click event ActionListener.
   * It calls the necessary IFileAnalyser API methods to analyse the user's chosen text file and output it
   * to the GUI.
   */
  private synchronized void callFileAnalysis() {
    
    SwingWorker<Void, GUIMode> worker = new SwingWorker<Void, GUIMode>() {

      @Override
      protected Void doInBackground() throws Exception {
        
        // Tell user that analysis is happening - publish the appropriate mode before analysis
        this.publish(GUIMode.ANALYSE_MODE);
        
        // Do analysis...
        
        // Pass user-defined options to the FileAnalyser
        boolean[] options = new boolean[3];
        options[0] = GUI.this.chbxAvgLens.isSelected();
        options[1] = GUI.this.chbxFreqs.isSelected();
        options[2] = GUI.this.chbxTextOCs.isSelected();
        
        String[] pattern = new String[] { GUI.this.txtPattern.getText() };
        
        GUI.this.fileAnalyser.setOptions(options, pattern);

        // Attempt to analyse the file.
        try {
          GUI.this.fileAnalyser.process();
          GUI.this.txtAreaStatsOutput.setText(GUI.this.fileAnalyser.toString());
          
          // Only save the analysis results to a log file if the user requested it
          if (GUI.this.chbxSaveLogFile.isSelected()) 
            GUI.this.fileAnalyser.saveLog();
        }
        
        // If the user has not yet chosen a file, the exception will be caught and
        // the user will be asked to choose a file.
        catch (NullStringException e) {
          String message = "Please select a text file by clicking 'Choose'.";
          JOptionPane.showMessageDialog(GUI.this.frmMainWindow, message, "No file selected", JOptionPane.PLAIN_MESSAGE);
          
          // Developer error message
          e.printStackTrace();
        }
        catch (FileNotFoundException e) {
          String message = "Sorry, there was an error creating the log file. Log file creation has been abandoned.";
          JOptionPane.showMessageDialog(GUI.this.frmMainWindow, message, "Log file error", JOptionPane.ERROR_MESSAGE);
          
          // Developer error message
          e.printStackTrace();
        }
        
        // Analysis finished; set the GUI's default mode
        this.publish(GUIMode.DEFAULT_MODE);
        
        return null;
      }

      /**
       * @param chunks
       *          The list of published GUIMode enum values to update the GUI with.
       *
       * @see javax.swing.SwingWorker#process(java.util.List)
       */
      @Override
      protected void process(List<GUIMode> chunks) {
        GUI.this.applyGUIMode(chunks.get(chunks.size() - 1)); // Get latest published chunk
      }
      
    };
    
    worker.execute();
  }

  /**
   * A private method that is called by the ActionListener managing click events for btnChooseFile. 
   * It pops up a JChooser file dialog to allow the user to select a text file for analysis.
   */
  private void chooseFile() {
    
    // Initialise and set default options for a Java file dialog object (a JChooser)
    JFileChooser chooser = new JFileChooser();
    FileNameExtensionFilter filter = new FileNameExtensionFilter("Text files", "txt", "text");
    chooser.setFileFilter(filter);
    chooser.setDialogTitle("Choose a file to analyse");
    
    // Show the dialog
    int returnVal = chooser.showOpenDialog(this.frmMainWindow);
    
    // If the user chose a file, identify it and get/set the needed information
    if (returnVal == JFileChooser.APPROVE_OPTION) {
      File file = chooser.getSelectedFile();
      
      // Get and set the file's name and directory and set in the appropriate fields of the fileAnalyser.
      String fileName = file.getName();
      String fileDir = file.getParent();
      GUI.this.fileAnalyser.setFileDirectory(fileDir);
      GUI.this.fileAnalyser.setFileName(fileName);
      
      // Set the 'Choose file' text box to the file's absolute path.
      this.txtChooseFile.setText(fileDir + File.separator + fileName);
    }
  }
  
  /**
   * A convenience method to set the GUI to reflect the desired mode.
   */
  private void applyGUIMode(GUIMode mode) {
    switch (mode) {
      case DEFAULT_MODE:
        
        this.lblAnalyseStatus.setVisible(false);
        this.btnAnalyse.setEnabled(true);
        this.btnChooseFile.setEnabled(true);
        this.chbxAvgLens.setEnabled(true);
        this.chbxFreqs.setEnabled(true);
        this.chbxSaveLogFile.setEnabled(true);
        this.chbxTextOCs.setEnabled(true);
        this.txtPattern.setEnabled(this.chbxTextOCs.isSelected());
        
      break;
      case ANALYSE_MODE:
        
        this.lblAnalyseStatus.setVisible(true);
        this.btnAnalyse.setEnabled(false);
        this.btnChooseFile.setEnabled(false);
        this.chbxAvgLens.setEnabled(false);
        this.chbxFreqs.setEnabled(false);
        this.chbxSaveLogFile.setEnabled(false);
        this.chbxTextOCs.setEnabled(false);
        this.txtPattern.setEnabled(false);
        
      break;
      default:
        // Do nothing  
    }
  }

  /**
   * Initialise the contents of the main window frame.
   */
  private void initialize() {
    
    this.fileAnalyser = new FileAnalyser();
    
    try {
      // Set the native system Look and Feel
      UIManager.setLookAndFeel(
          UIManager.getSystemLookAndFeelClassName());
    } 
    catch (UnsupportedLookAndFeelException e) {
      // Do nothing - let the Look and Feel gracefully fall back to default
    }
    catch (ClassNotFoundException e) {
      // ...
    }
    catch (InstantiationException e) {
      // ...
    }
    catch (IllegalAccessException e) {
      // ...
    }
    
    this.frmMainWindow = new JFrame();
    this.frmMainWindow.setTitle("Text Analyser");
    this.frmMainWindow.setResizable(false);
    this.frmMainWindow.setBounds(100, 100, 440, 420);
    this.frmMainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.frmMainWindow.getContentPane().setLayout(null);

    this.txtChooseFile = new JTextField();
    this.txtChooseFile.setEditable(false);
    this.txtChooseFile.setColumns(10);
    this.txtChooseFile.setBounds(11, 12, 273, 20);
    this.frmMainWindow.getContentPane().add(this.txtChooseFile);

    this.btnChooseFile = new JButton("Choose");
    this.btnChooseFile.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        GUI.this.chooseFile();
      }

    });
    btnChooseFile.setBounds(294, 11, 127, 23);
    this.frmMainWindow.getContentPane().add(btnChooseFile);

    this.btnAnalyse = new JButton("Analyse");
    this.btnAnalyse.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent arg0) {
        GUI.this.callFileAnalysis();
      }
      
    });
    btnAnalyse.setBounds(11, 188, 136, 23);
    this.frmMainWindow.getContentPane().add(btnAnalyse);

    JPanel pnlOptions = new JPanel();
    pnlOptions.setLayout(null);
    pnlOptions.setBorder(BorderFactory.createTitledBorder("Options"));
    pnlOptions.setBounds(11, 69, 413, 108);
    this.frmMainWindow.getContentPane().add(pnlOptions);

    this.chbxAvgLens = new JCheckBox("Analyse Average Lengths");
    this.chbxAvgLens.setSelected(true);
    this.chbxAvgLens.setBounds(10, 19, 206, 23);
    pnlOptions.add(this.chbxAvgLens);

    this.chbxFreqs = new JCheckBox("Analyse Frequencies");
    this.chbxFreqs.setSelected(true);
    this.chbxFreqs.setBounds(218, 19, 176, 23);
    pnlOptions.add(this.chbxFreqs);

    this.chbxTextOCs = new JCheckBox("Analyse number of occurrences of a pattern in file");
    this.chbxTextOCs.setBounds(10, 45, 386, 23);
    this.chbxTextOCs.addItemListener(new ItemListener() {

      @Override
      public void itemStateChanged(ItemEvent arg0) {
        GUI.this.txtPattern.setEnabled(GUI.this.chbxTextOCs.isSelected());
      }

    });
    pnlOptions.add(this.chbxTextOCs);

    this.txtPattern = new JTextField();
    this.txtPattern.setEnabled(false);
    this.txtPattern.setColumns(10);
    this.txtPattern.setBounds(10, 75, 273, 20);
    pnlOptions.add(this.txtPattern);

    JScrollPane scrlStatsOutput = new JScrollPane();
    scrlStatsOutput.setBounds(11, 222, 410, 159);
    this.frmMainWindow.getContentPane().add(scrlStatsOutput);

    this.txtAreaStatsOutput = new JTextArea();
    this.txtAreaStatsOutput.setFont(new Font("Courier New", Font.PLAIN, 13));
    this.txtAreaStatsOutput.setEditable(false);
    scrlStatsOutput.setViewportView(this.txtAreaStatsOutput);

    this.chbxSaveLogFile = new JCheckBox("Save statistics to a log file");
    this.chbxSaveLogFile.setBounds(11, 39, 273, 23);
    this.frmMainWindow.getContentPane().add(chbxSaveLogFile);

    this.lblAnalyseStatus = new JLabel("Analysing ...");
    this.lblAnalyseStatus.setBounds(157, 192, 264, 14);
    this.lblAnalyseStatus.setVisible(false);
    this.frmMainWindow.getContentPane().add(this.lblAnalyseStatus);
    this.frmMainWindow.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[] { this.txtChooseFile, btnChooseFile,
        chbxSaveLogFile, pnlOptions, this.chbxAvgLens, this.chbxFreqs, this.chbxTextOCs, this.txtPattern, btnAnalyse,
        this.txtAreaStatsOutput, scrlStatsOutput, this.frmMainWindow.getContentPane() }));
  }

  /**
   * Launch the application.
   */
  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {

      @Override
      public void run() {
        try {
          GUI window = new GUI();
          window.frmMainWindow.setVisible(true);
        }
        catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
  }

  /**
   * This JUnit 4 specific test method should be commented out or made private before building the program for
   * public release!
   * 
   * This method allows JUnit tests to be run on the internal state of a GUI object.
   * 
   * @return true if the state was properly initialised, otherwise false.
   */
  public boolean isInitialStateOK() {
    return (this.frmMainWindow != null && this.btnAnalyse != null && this.btnChooseFile != null &&
        this.chbxAvgLens != null && this.chbxFreqs != null && this.chbxSaveLogFile != null &&
        this.chbxTextOCs != null && this.lblAnalyseStatus != null && this.txtAreaStatsOutput != null && 
        this.txtChooseFile != null && this.txtPattern != null);
  }
}
