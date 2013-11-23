/**
 * AbstractFileHandler.java
 */

package uk.ac.surrey.com1028.jb00359.textAnalyser;


/**
 * AbstractFileHandler is an abstract class that holds data about file system pathnames. 
 * It is used by concrete classes like FileAnalyser and LogFileHandler to help them
 * store and manage basic file-related information.
 * 
 * It is the responsibility of classes which extend AbstractFileHandler to implement constructors/setters
 * that obtain file system file names and directories (absolute paths).
 * 
 * @author jb00359
 */
public abstract class AbstractFileHandler {
  
  /** The name of a file */
  private String fileName;
  
  /** The directory (absolute path) of a file */
  private String fileDir;
  
  /** Default super constructor for children of AbstractFileHandler */
  public AbstractFileHandler() {
    super();
  }
  
  /**
   * Super setter of file name for children of AbstractFileHandler.
   * 
   * @param fileName: The file name to set.
   */
  public void setFileName(String fileName) {
    this.fileName = fileName;
  }
  
  /**
   * Super setter of file directory for children of AbstractFileHandler.
   * 
   * @param fileName: The file directory to set.
   */
  public void setFileDirectory(String fileDir) {
    this.fileDir = fileDir;
  }
   
  /**
   * Super getter of file name for children of AbstractFileHandler.
   * 
   * @return the name of the file associated with this AbstractFileHandler.
   */
  public String getFileName() {
    return this.fileName;
  }

  /**
   * Super getter of file directory for children of AbstractFileHandler.
   * 
   * @return the file directory associated with this AbstractFileHandler.
   */
  public String getFileDirectory() {
    return this.fileDir;
  }
  
}
