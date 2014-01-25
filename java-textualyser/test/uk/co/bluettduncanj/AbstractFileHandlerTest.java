/**
 * AbstractFileHandlerTest.java
 */

package uk.co.bluettduncanj;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import uk.co.bluettduncanj.controller.AbstractFileHandler;
import uk.co.bluettduncanj.controller.FileAnalyser;
import uk.co.bluettduncanj.controller.LogFileHandler;


/**
 * @author Jonathan Bluett-Duncan
 */
public class AbstractFileHandlerTest {
  
  private AbstractFileHandler fileHandler1;
  private AbstractFileHandler fileHandler2;
  /*
  private String fileName = "fileName";
  private String fileDir = "fileDir";
  */
  private File file = new File("test/uk/co/bluettduncanj/test.txt");

  /**
   * @throws java.lang.Exception
   */
  @Before
  public void setUp() throws Exception {
    
    // Test both children classes of AbstractFileHandler
    fileHandler1 = new FileAnalyser();
    fileHandler1.setFilePath(file.getAbsolutePath());
    fileHandler2 = new LogFileHandler(file.getAbsolutePath(), null);
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
   * Test method for {@link uk.co.bluettduncanj.controller.AbstractFileHandler#AbstractFileHandler()}.
   */
  @Test
  public void testAbstractFileHandler() {
    assertNotNull(fileHandler1);
    //assertEquals("AbstractFileHandler 1's file directory not properly set", fileDir, fileHandler1.getFileDirectory());
    //assertEquals("AbstractFileHandler 1's file name not properly set", fileName, fileHandler1.getFileName());
    System.out.println(fileHandler1.getFilePath());
    assertEquals("AbstractFileHandler 1's file path not properly set", file.getAbsolutePath(), fileHandler1.getFilePath());
    assertNotNull(fileHandler2);
    //assertEquals("AbstractFileHandler 2's file directory not properly set", fileDir, fileHandler2.getFileDirectory());
    //assertEquals("AbstractFileHandler 2's file name not properly set", fileName, fileHandler2.getFileName());
    assertEquals("AbstractFileHandler 2's file path not properly set", file.getAbsolutePath(), fileHandler2.getFilePath());
  }

}
