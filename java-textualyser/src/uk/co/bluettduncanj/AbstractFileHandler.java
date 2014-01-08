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
  
  /** The name of a file */
  //private String fileName;
  
  /** The directory (absolute path) of a file */
  //private String fileDir;
  
  /** Default super constructor for subclasses of AbstractFileHandler */
  public AbstractFileHandler() {
    super();
  }
  
  /**
   * Super setter of file name for subclasses of AbstractFileHandler.
   * 
   * @param fileName: The file name to set.
   */
  /*
  public void setFileName(String fileName) {
    this.fileName = fileName;
  }
  */
  
  /**
   * Super setter of file directory for subclasses of AbstractFileHandler.
   * 
   * @param fileName: The file directory to set.
   */
  /*
  public void setFileDirectory(String fileDir) {
    this.fileDir = fileDir;
  }
  */
  
  /**
   * Super setter of file path for subclasses of AbstractFileHandler.
   * 
   * This method treats the setting of filePath in exactly the same way as calling the java.io.File(String) constructor,
   * and so all appropriate subclasses of Exception will be thrown in the same exceptional situations.
   * (See the Javadocs for java.io.File and java.io.File(String) for more details).
   * 
   * @throws NullPointerException if the argument is <code>null</code>.
   */
  public void setFilePath(String filePath) throws NullPointerException {
    this.file = new File(filePath);
  }
  
  /**
   * Super getter of the absolute (full) file path for subclasses of AbstractFileHandler.
   * 
   * @return the absolute file path of the file associated with this AbstractFileHandler.
   * @throws NullPointerException if no file path has been set.
   */
  public String getFilePath() throws NullPointerException {
    //return this.fileDir + File.separator + this.fileName;
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
  
}
