/**
 * FileAnalyserTest.java
 */

package uk.co.bluettduncanj;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import uk.co.bluettduncanj.FileAnalyser;


/**
 * @author jb00359
 */
public class FileAnalyserTest {
  
  private FileAnalyser fileAnalyser;

  /**
   * @throws java.lang.Exception
   */
  @Before
  public void setUp() throws Exception {
    fileAnalyser = FileAnalyser.getInstance();
  }

  /**
   * @throws java.lang.Exception
   */
  @After
  public void tearDown() throws Exception {
    fileAnalyser = null;
  }

  /**
   * Test method for {@link uk.co.bluettduncanj.FileAnalyser#getInstance()}.
   */
  @Test
  public void testGetInstance() {
    assertNotNull("getInstance() returns null.", fileAnalyser);
    // Make sure the isInitialStateOK method is not commented out before testing!
    assertEquals("fileAnalyser internal state not properly set.", true, fileAnalyser.isInitialStateOK());
  }

}