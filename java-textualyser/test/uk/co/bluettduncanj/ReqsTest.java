/**
 * RequirementsTest.java
 */

package uk.co.bluettduncanj;

import org.junit.Test;

import uk.co.bluettduncanj.FileAnalyser;
import uk.co.bluettduncanj.NullStringException;


/**
 * @author jb00359
 */
public class ReqsTest {

  @Test
  public void testS1() throws NullStringException {
    // Set up output for first file
    FileAnalyser fileAnalyser = FileAnalyser.getInstance();
    fileAnalyser.setFileDirectory("src");
    fileAnalyser.setFileName("test.txt");
    fileAnalyser.setOptions(new boolean[]{ false, false, false }, new String[]{null});
    fileAnalyser.process();
    // Please make sure datStructPrint() is not commented out before testing!
    System.out.println(fileAnalyser.datStructPrint());
    
    // Set up output for second file
    fileAnalyser.setFileName("test2.txt");
    fileAnalyser.process();
    System.out.println(fileAnalyser.datStructPrint());
  }

}
