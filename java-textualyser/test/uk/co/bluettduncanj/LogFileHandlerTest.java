/**
 * LogFileHandlerTest.java
 */

package uk.co.bluettduncanj;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import uk.co.bluettduncanj.LogFileHandler;


/**
 * @author Jonathan Bluett-Duncan
 */
public class LogFileHandlerTest {
  
  private LogFileHandler log;
  //private String fileName = "file name";
  //private String fileDir = "directory";
  private File file = new File("test/uk/co/bluettduncanj/log_test.txt");
  private String statsString = "Statistics string";

  /**
   * @throws java.lang.Exception
   */
  @Before
  public void setUp() throws Exception {
    String logFilePath = file.getAbsolutePath();
    log = new LogFileHandler(logFilePath, statsString);
  }

  /**
   * @throws java.lang.Exception
   */
  @After
  public void tearDown() throws Exception {
    log = null;
  }

  /**
   * Test method for {@link uk.co.bluettduncanj.LogFileHandler#LogFileHandler(java.lang.String, java.lang.String, java.lang.String)}.
   * @throws FileNotFoundException 
   */
  @Test
  public void testLogFileHandler() throws FileNotFoundException {
    assertNotNull("The log handler is null", log);
    //assertEquals("File name not correctly set", fileName, log.getFileName());
    //assertEquals("File directory not correctly set", fileDir, log.getFileDirectory());
    assertEquals("File path not correctly set", file.getAbsolutePath(), log.getFilePath());
    
    // Make sure the logStringEquals method is not commented out before testing!
    assertEquals("String to save to file not correctly set", true, log.logStringEquals(statsString));
    
  }

}
