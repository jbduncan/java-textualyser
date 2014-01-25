/**
 * RequirementsTest.java
 */

package uk.co.bluettduncanj;

import java.io.FileNotFoundException;

import org.junit.Test;

import uk.co.bluettduncanj.controller.FileAnalyser;


/**
 * @author Jonathan Bluett-Duncan
 */
public class ReqsTest {

  @Test
  public void testS1() throws NullPointerException, FileNotFoundException {
    
    // Set up output for first file
    FileAnalyser fileAnalyser = new FileAnalyser();
    fileAnalyser.setFilePath("test/uk/co/bluettduncanj/test.txt");
    fileAnalyser.setOptions(new boolean[]{ false, false, false }, null);
    fileAnalyser.process();
    // Please make sure datStructPrint() is not commented out before testing!
    System.out.println(fileAnalyser.datStructPrint());
    
    // Set up output for second file
    fileAnalyser.setFilePath("test/uk/co/bluettduncanj/test2.txt");
    fileAnalyser.process();
    System.out.println(fileAnalyser.datStructPrint());
  }

}
