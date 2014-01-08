/**
 * LogFileHandler.java
 */

package uk.co.bluettduncanj;

import java.io.FileNotFoundException;
import java.io.PrintWriter;


/**
 * LogFileHandler is a file-handling class that takes a String of text and stores it persistently to the
 * native machine's filesystem as a log file, using a given file name and filesystem directory.
 * 
 * It is designed to work as a component of FileAnalyser that gets instantiated when needed. Its role as such a 
 * component is to save the statistical results of a Statistics object to an appropriately named log file,
 * in the same directory as the text file whose statistics have been calculated.
 * 
 * @author Jonathan Bluett-Duncan
 */
public class LogFileHandler extends AbstractFileHandler {
  
  /** Text to store in the log file */
  private String logString;
  
  /**
   * Parameterised constructor - Initialises state to parameter data
   */
  /*
  public LogFileHandler(String fileName, String fileDir, String logString) {
    super();
    this.setFileName(fileName);
    this.setFileDirectory(fileDir);
    this.logString = logString;
  }
  */
  public LogFileHandler(String filePath, String logString) {
    super();
    this.setFilePath(filePath);
    this.logString = logString;
  }
  
  /**
   * Stores the log String specified at construction time to the log file with name and directory also specified
   * at construction time.
   * 
   * If the log file does not exist, it gets created with the given name at the given directory.
   * 
   * If the log file already exists and contains data, it will be overwritten with the new log String.
   * 
   * @throws FileNotFoundException if the file path "does not denote an existing, writable 
   * regular file and a new regular file of that name cannot be created, or if some other error occurs while 
   * opening or creating the file" (see the throws definition for FileNotFoundException in java.io.PrintWriter).
   */
  public void save() throws FileNotFoundException {
    PrintWriter write = new PrintWriter(this.getFilePath());
    
    // Print the log string to the file
    write.print(this.logString);
    write.flush();
    write.close();
  }

  /**
   * This JUnit 4 specific test method should be commented out or made private before building the program for
   * public release!
   * 
   * This method allows JUnit tests to be run on the internal state of a LogFileHandler object.
   * 
   * @param otherLogString: The string to compare to the contents of this LogFileHandler object's log file.
   * 
   * @return true if the two Strings are equal, otherwise false.
   */
  public boolean logStringEquals(String otherLogString) {
    return this.logString.equals(otherLogString);
  }
}
