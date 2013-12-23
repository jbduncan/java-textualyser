/**
 * AbstractFileHandlerTest.java
 */

package uk.co.bluettduncanj;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import uk.co.bluettduncanj.AbstractFileHandler;
import uk.co.bluettduncanj.FileAnalyser;
import uk.co.bluettduncanj.LogFileHandler;


/**
 * @author jb00359
 */
public class AbstractFileHandlerTest {
  
  private AbstractFileHandler fileHandler1;
  private AbstractFileHandler fileHandler2;
  private String fileName = "fileName";
  private String fileDir = "fileDir";

  /**
   * @throws java.lang.Exception
   */
  @Before
  public void setUp() throws Exception {
    // Test both children classes of AbstractFileHandler
    fileHandler1 = FileAnalyser.getInstance();
    fileHandler1.setFileDirectory(fileDir);
    fileHandler1.setFileName(fileName);
    fileHandler2 = new LogFileHandler(fileName, fileDir, null);
  }

  /**
   * @throws java.lang.Exception
   */
  @After
  public void tearDown() throws Exception {
    fileHandler1 = null;
    fileHandler2 = null;
  }

  /**
   * Test method for {@link uk.co.bluettduncanj.AbstractFileHandler#AbstractFileHandler()}.
   */
  @Test
  public void testAbstractFileHandler() {
    assertNotNull(fileHandler1);
    assertEquals("AbstractFileHandler 1's file directory not properly set", fileDir, fileHandler1.getFileDirectory());
    assertEquals("AbstractFileHandler 1's file name not properly set", fileName, fileHandler1.getFileName());
    assertNotNull(fileHandler2);
    assertEquals("AbstractFileHandler 2's file directory not properly set", fileDir, fileHandler2.getFileDirectory());
    assertEquals("AbstractFileHandler 2's file name not properly set", fileName, fileHandler2.getFileName());
  }

}
