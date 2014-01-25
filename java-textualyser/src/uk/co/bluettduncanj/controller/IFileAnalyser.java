/**
 * IFileAnalyser.java
 */

package uk.co.bluettduncanj.controller;

import java.io.FileNotFoundException;


/**
 * IFileAnalyser is an interface which specifies a contract that all text file analyser implementations
 * must obey. 
 * 
 * @author Jonathan Bluett-Duncan
 */
public interface IFileAnalyser {
  
  // TODO: Make comments here (inc. class description) less verbose 
  // (see implementations in FileAnalyser for more concise descriptions).
  
  // TODO: Change interface (and rest of program) so that it doesn't have setFilePath() and setOptions() [move
  // those to the constructor instead].
  
  /**
   * setFilePath(String) defines the condition that all file analysers must be able to 
   * use file paths to identify text files and their locations on the file system.
   * 
   * @param fileName: The directory of the file to set.
   * @throws NullPointerException if the argument is <code>null</code>.
   */
  public void setFilePath(String filePath) throws NullPointerException;
  
  /**
   * process() defines the condition that all file analysers must have a means of
   * parsing and analysing the contents of a text file.
   * 
   * @throws NullPointerException if the file path is not set.
   * @throws FileNotFoundException if the file cannot be found or read.
   */
  public void process() throws NullPointerException, FileNotFoundException;
  
  /**
   * saveLog() defines the condition that all file analysers must be able to save analysis results (statistics)
   * for a text file to a different log file.
   * 
   * @throws NullPointerException if the file path is not set, or if no statistics are found i.e. process() has not been called.
   * @throws FileNotFoundException if the file path "does not denote an existing, writable 
   * regular file and a new regular file of that name cannot be created, or if some other error occurs while 
   * opening or creating the file" (see the throws definition for FileNotFoundException in java.io.PrintWriter).
   */
  public void saveLog() throws NullPointerException, FileNotFoundException;
  
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
   * @throws IllegalArgumentException if the file analyser implementation reports
   * a program-breaking error with any of the parameter arguments (options and/or args).
   */
  public void setOptions(boolean[] options, String[] args) throws IllegalArgumentException;
  
}
