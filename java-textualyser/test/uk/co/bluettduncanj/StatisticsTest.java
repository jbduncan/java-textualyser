/**
 * StatisticsTest.java
 */

package uk.co.bluettduncanj;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import uk.co.bluettduncanj.Statistics;


/**
 * @author jb00359
 */
public class StatisticsTest {
  
  private Statistics stats;
  private List<ArrayList<String>> testSs;
  private List<ArrayList<String>> testWs;
  private List<char[]> testCs;

  /**
   * @throws java.lang.Exception
   */
  @Before
  public void setUp() throws Exception {
    stats = new Statistics();
    
    testCs = new ArrayList<char[]>();
    String cTest = "This is a test.";
    String cTest2 = "qwertyuiopasdfghjklzxcvbnm";
    //String cTest3 = "Another reflective report note:\n" +
    //               "Mention class from web for Boyer Moore.";
    String cTest4 = "This phrase. contains. multiple sentences.";
    //String cTest5 = "The cat sat on the mat.";
    //String cTest6 = "The dog sat on the cat.";
    testCs.add(cTest.toCharArray());
    testCs.add(cTest2.toCharArray());
    //testCs.add(cTest3.toCharArray());
    testCs.add(cTest4.toCharArray());
    //testCs.add(cTest5.toCharArray());
    //testCs.add(cTest6.toCharArray());
    
    testWs = new ArrayList<ArrayList<String>>();
    ArrayList<String> wTest = new ArrayList<String>();
    wTest.add("This");
    wTest.add("is");
    wTest.add("a");
    wTest.add("test");
    ArrayList<String> wTest2 = new ArrayList<String>();
    wTest2.add("qwertyuiopasdfghjklzxcvbnm");
    //ArrayList<String> wTest3 = new ArrayList<String>();
    //wTest3.add("Another");
    //wTest3.add("reflective");
    //wTest3.add("report");
    //wTest3.add("note:");
    //wTest3.add("Mention");
    //wTest3.add("class");
    //wTest3.add("from");
    //wTest3.add("web");
    //wTest3.add("for");
    //wTest3.add("Boyer");
    //wTest3.add("Moore.");
    ArrayList<String> wTest4 = new ArrayList<String>();
    wTest4.add("This");
    wTest4.add("phrase.");
    wTest4.add("contains.");
    wTest4.add("multiple");
    wTest4.add("sentences.");
    //ArrayList<String> wTest5 = new ArrayList<String>();
    //wTest5.add("The");
    //wTest5.add("cat");
    //wTest5.add("sat");
    //wTest5.add("on");
    //wTest5.add("mat.");
    //ArrayList<String> wTest6 = new ArrayList<String>();
    //wTest6.add("The");
    //wTest6.add("dog");
    //wTest6.add("sat");
    //wTest6.add("on");
    //wTest6.add("cat.");
    testWs.add(wTest);
    testWs.add(wTest2);
    //testWs.add(wTest3);
    testWs.add(wTest4);
    //testWs.add(wTest5);
    //testWs.add(wTest6);
    
    testSs = new ArrayList<ArrayList<String>>();
    ArrayList<String> sTest = new ArrayList<String>();
    sTest.add("This is a test.");
    ArrayList<String> sTest2 = new ArrayList<String>();
    sTest2.add("qwertyuiopasdfghjklzxcvbnm");
    //ArrayList<String> sTest3 = new ArrayList<String>();
    //sTest3.add("Another reflective report note:\n" +
    //    "Mention class from web for Boyer Moore.");
    ArrayList<String> sTest4 = new ArrayList<String>();
    sTest4.add("This phrase.");
    sTest4.add("contains.");
    sTest4.add("multiple sentences");
    //ArrayList<String> sTest5 = new ArrayList<String>();
    //sTest5.add("The cat sat on the mat.");
    //ArrayList<String> sTest6 = new ArrayList<String>();
    //sTest6.add("The dog sat on the cat.");
    testSs.add(sTest);
    testSs.add(sTest2);
    //testSs.add(sTest3);
    testSs.add(sTest4);
    //testSs.add(sTest5);
    //testSs.add(sTest6);
  }

  /**
   * @throws java.lang.Exception
   */
  @After
  public void tearDown() throws Exception {
    stats = null;
    testSs = null;
    testWs = null;
    testCs = null;
  }

  /**
   * Test method for {@link uk.co.bluettduncanj.Statistics#Statistics()}.
   */
  @Test
  public void testStatistics() {
    assertNotNull("Statistics object is null", stats);
    // Make sure the isInitialStateOK method is not commented out before testing!
    assertEquals("Statistics object's initial state is not reset", true, stats.isReset());
  }

  /**
   * Test method for {@link uk.co.bluettduncanj.Statistics#reset()}.
   */
  @Test
  public void testReset() {
    stats.reset();
    assertEquals("Statistics object's initial state is not reset", true, stats.isReset());
    // Do an arbitrary calculation that should change the state from 'reset'
    stats.calcAvgSentenceLen(testSs.get(3));
    assertEquals("Statistics object's state is still in reset state!", false, stats.isReset());
  }

  /**
   * Test method for {@link uk.co.bluettduncanj.Statistics#calcCharFreq(char[])}.
   */
  @Test
  public void testCalcCharFreq() {
    
    fail("Not yet implemented");
    // INCOMPLETE
    
    char[] list = testCs.get(0);
    stats.calcCharFreq(list);
    
    
    list = testCs.get(1);
    
    
    list = testCs.get(2);
    
    
    list = testCs.get(3);
    
  }

  /**
   * Test method for {@link uk.co.bluettduncanj.Statistics#calcAvgSentenceLen(java.util.List)}.
   */
  @Test
  public void testCalcAvgSentenceLen() {
    fail("Not yet implemented");
  }

  /**
   * Test method for {@link uk.co.bluettduncanj.Statistics#calcAvgWordLen(java.util.List)}.
   */
  @Test
  public void testCalcAvgWordLen() {
    fail("Not yet implemented");
  }

  /**
   * Test method for {@link uk.co.bluettduncanj.Statistics#calcNoOfEnglishANs(char[])}.
   */
  @Test
  public void testCalcNoOfEnglishANs() {
    fail("Not yet implemented");
  }

  /**
   * Test method for {@link uk.co.bluettduncanj.Statistics#calcNoOfNonANs(char[])}.
   */
  @Test
  public void testCalcNoOfNonANs() {
    fail("Not yet implemented");
  }

  /**
   * Test method for {@link uk.co.bluettduncanj.Statistics#calcNoOfWhitespaces(char[], int)}.
   */
  @Test
  public void testCalcNoOfWhitespaces() {
    fail("Not yet implemented");
  }

  /**
   * Test method for {@link uk.co.bluettduncanj.Statistics#isWhitespace(char)}.
   */
  @Test
  public void testIsWhitespace() {
    fail("Not yet implemented");
  }

  /**
   * Test method for {@link uk.co.bluettduncanj.Statistics#calcNoOfIntlChars(char[])}.
   */
  @Test
  public void testCalcNoOfIntlChars() {
    fail("Not yet implemented");
  }

  /**
   * Test method for {@link uk.co.bluettduncanj.Statistics#calcNoOfSuffixes(java.util.List)}.
   */
  @Test
  public void testCalcNoOfSuffixes() {
    fail("Not yet implemented");
  }

  /**
   * Test method for {@link uk.co.bluettduncanj.Statistics#calcNoOfTextOCs(char[], char[])}.
   */
  @Test
  public void testCalcNoOfTextOCs() {
    fail("Not yet implemented");
  }

  /**
   * Test method for {@link uk.co.bluettduncanj.Statistics#toString()}.
   */
  @Test
  public void testToString() {
    fail("Not yet implemented");
  }

}
