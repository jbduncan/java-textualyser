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
 * @author Jonathan Bluett-Duncan
 */
public class FileAnalyserTest {
  
  private FileAnalyser fileAnalyser;

  /**
   * @throws java.lang.Exception
   */
  @Before
  public void setUp() throws Exception {
    fileAnalyser = new FileAnalyser();
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
  public void testFileAnalyser() {
    assertNotNull("Instance is null.", fileAnalyser);
    // Make sure the isInitialStateOK method is not commented out before testing!
    assertEquals("fileAnalyser internal state not properly set.", true, fileAnalyser.isInitialStateOK());
  }

}
