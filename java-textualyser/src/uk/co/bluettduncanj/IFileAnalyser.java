/**
 * IFileAnalyser.java
 */

package uk.co.bluettduncanj;

import java.io.FileNotFoundException;


/**
 * IFileAnalyser is an interface which specifies a contract that all text file analyser implementations
 * must obey. 
 * 
 * @author jb00359
 */
public interface IFileAnalyser {

  /**
   * setFileName(String) defines the condition that all file analysers must be able to 
   * use file names to identify text files.
   * 
   * @param fileName: The name of the file to set.
   */
  public void setFileName(String fileName);
  
  /**
   * setFileDirectory(String) defines the condition that all file analysers must be able to 
   * use file directories to identify the filesystem locations of text files.
   * 
   * @param fileName: The directory of the file to set.
   */
  public void setFileDirectory(String fileDir);
  
  /**
   * process() defines the condition that all file analysers must have a means of
   * parsing and analysing the contents of a text file.
   * 
   * @throws NullStringException: This exception will be thrown if the file name or directory are null (not set).
   */
  public void process() throws NullStringException;
  
  /**
   * saveLog() defines the condition that all file analysers must be able to save analysis results (statistics)
   * for a text file to a different log file.
   * 
   * @throws NullStringException: This exception will be thrown if the file name or directory are null (not set).
   * It will also be thrown if no statistics are found i.e. process() has not been called.
   * @throws FileNotFoundException: This will be thrown if the file path "does not denote an existing, writable 
   * regular file and a new regular file of that name cannot be created, or if some other error occurs while 
   * opening or creating the file" (see the throws definition for FileNotFoundException in java.io.PrintWriter).
   */
  public void saveLog() throws NullStringException, FileNotFoundException;
  
  /**
   * toString() defines the condition that all file analysers must be able to return analysis results (statistics)
   * as a human-readable String.
   * 
   * @return a human-readable representation of the statistics, or null if no statistics exist (i.e. if process() was not called).
   */
  @Override
  public String toString();

  /**
   * setOptions() defines the condition that all file analysers must be able to accept options parameters that
   * can customise analysis results.
   * 
   * @param options: The options to set.
   * @param args: Other String-based arguments which different file analyser implementations may use or require from callers 
   *              to further customise analyses.
   *              
   * @throws IllegalArgumentException: This exception will be thrown if the file analyser implementation reports
   * a program-breaking error with any of the parameter arguments (options and/or args).
   */
  public void setOptions(boolean[] options, String[] args) throws IllegalArgumentException;
  
}
