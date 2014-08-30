/**
 * AbstractFileHandler.java
 */

package uk.co.bluettduncanj.model.core;

import java.nio.file.FileSystems;
import java.nio.file.Path;


/**
 * <p>
 * Abstract objects that hold data about and perform operations on file paths. It is used
 * by concrete classes such as {@link TextFileReader} and {@link LogFileWriter} to help them
 * manage basic file-related information.
 * </p>
 * 
 * @author Jonathan Bluett-Duncan
 */
public abstract class AbstractFileHandler {
  
  /** Path to the file to be handled. */
  private final Path path;
  
  /**
   * <p>
   * Parameterised constructor. Creates a new <tt>AbstractFileHandler</tt> from a <tt>String</tt>
   * file path.
   * </p>
   * 
   * @throws NullPointerException If the file path is <tt>null</tt>.
   * @throws InvalidPathException If the file path cannot be converted, if for example it contains illegal
   *     characters, is badly formed or cannot be parsed due to some other restrictions.
   */
  public AbstractFileHandler(String filePath) {
    if (filePath == null) {
      throw new NullPointerException("File path is null.");
    }
    
    this.path = FileSystems.getDefault().getPath(filePath);
  }
  
  /**
   * Returns the absolute path of the file associated with this <tt>AbstractFileHandler</tt> as
   * a <tt>Path</tt> object. This path contains both the name of the file and it's full path on the file system. 
   * If a <tt>String</tt> representing the path is needed, use the {@link Path#toString()} method 
   * of the <tt>Path</tt> returned by this method.
   * 
   * @return the absolute file path.
   */
  public Path getAbsoluteFilePath() {
    return this.path.toAbsolutePath();
  }
  
  /**
   * Returns the name of the file associated with this <tt>AbstractFileHandler</tt> as a <tt>Path</tt>
   * object. If a <tt>String</tt> representing the path is needed, use the {@link Path#toString()} method
   * of the <tt>Path</tt> returned by this method.
   * 
   * @return the name of the file.
   */
  public Path getFileName() {
    return this.path.getFileName();
  }
  
}
