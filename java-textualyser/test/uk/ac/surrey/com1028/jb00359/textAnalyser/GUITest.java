/**
 * GUITest.java
 */

package uk.ac.surrey.com1028.jb00359.textAnalyser;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


/**
 * @author jb00359
 */
public class GUITest {
  
  private GUI gui;

  /**
   * @throws java.lang.Exception
   */
  @Before
  public void setUp() throws Exception {
    gui = new GUI();
  }

  /**
   * @throws java.lang.Exception
   */
  @After
  public void tearDown() throws Exception {
    gui = null;
  }

  /**
   * Test method for {@link uk.ac.surrey.com1028.jb00359.textAnalyser.GUI#GUI()}.
   */
  @Test
  public void testGUI() {
    assertNotNull("The GUI is null", gui);
    // Make sure the isInitialStateOK method is not commented out before testing!
    assertEquals("At least one of the internal JComponents are null", true, gui.isInitialStateOK());
  }

}
