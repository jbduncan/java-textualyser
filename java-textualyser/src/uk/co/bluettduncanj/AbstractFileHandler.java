/**
 * AbstractFileHandler.java
 */

package uk.co.bluettduncanj;

import java.io.File;


/**
 * AbstractFileHandler is an abstract class that holds data about file system pathnames. 
 * It is used by concrete classes like FileAnalyser and LogFileHandler to help them
 * store and manage basic file-related information.
 * 
 * It is the responsibility of classes which extend AbstractFileHandler to implement constructors/setters/getters
 * for file system absolute paths, file names and directories.
 * 
 * @author Jonathan Bluett-Duncan
 */
public abstract class AbstractFileHandler {
  
  /** The file to be handled */
  private File file;
  
  /** Default super constructor for subclasses of AbstractFileHandler */
  public AbstractFileHandler() {
    super();
  }
  
  /**
   * Super setter of file path for subclasses of AbstractFileHandler.
   * 
   * This method sets the file path by calling the java.io.File(String) constructor, and so throws the same Exceptions
   * as that constructor. (See the Javadocs for java.io.File and java.io.File(String) for more details). However, this
   * method also throws an additional FileNotFoundException depending on whether the file at the file path exists and is readable.
   * 
   * @throws NullPointerException if the argument is <code>null</code>.
   */
  public void setFilePath(String filePath) throws NullPointerException {
    if (filePath == null) {
      throw new NullPointerException("The file path cannot be null.");
    }
    
    this.file = new File(filePath);
  }
  
  /**
   * Super getter of the absolute (full) file path for subclasses of AbstractFileHandler.
   * 
   * @return the absolute file path of the file associated with this AbstractFileHandler.
   * @throws NullPointerException if no file path has been set.
   */
  public String getFilePath() throws NullPointerException {
    return this.file.getAbsolutePath();
  }
   
  /**
   * Super getter of file name for subclasses of AbstractFileHandler.
   * 
   * @return the name of the file associated with this AbstractFileHandler.
   * @throws NullPointerException if no file path has been set.
   */
  public String getFileName() throws NullPointerException {
    return this.file.getName();
  }

  /**
   * Super getter of the directory from the file path, for subclasses of AbstractFileHandler.
   * 
   * @return the parent file directory associated with this AbstractFileHandler, or <code>null</code> if the 
   * AbstractFileHandler doesn't have an associated parent directory.
   * @throws NullPointerException if no file path has been set.
   */
  public String getFileDirectory() throws NullPointerException {
    return this.file.getParent();
  }
  
  /**
   * Checks whether a file path has been set for the current AbstractFileHandler.
   * 
   * @return true if a file path is set, otherwise false.
   */
  public boolean isFilePathSet() {
    if (this.file == null) {
      return false;
    }
    return true;
  }
  
  /**
   * Checks whether the file at the file path given through setFilePath(String) exists and can be read.
   * 
   * @return true if the file exists and can be read, otherwise false.
   */
  public boolean fileExistsAndReadable() {
    try {
      return this.file.exists();
    }
    catch (SecurityException e) {
      return false;
    }
  }
  
}
