/**
 * NullStringException.java
 */

package uk.ac.bluettduncanj;


/**
 * @author jb00359
 */
public class NullStringException extends Exception {

  /** serialVersionUID */
  private static final long serialVersionUID = 3334956944941836062L;
  
  /**
   * Default Exception constructor. Outputs default message.
   */
  public NullStringException() {
    super("A string is null.");
  }
  
  /**
   * Exception constructor that uses a programmer- or user-defined error message.
   * 
   * @param message: The message that explains the Exception in more detail.
   */
  public NullStringException(String message) {
    super(message);
  }

}
