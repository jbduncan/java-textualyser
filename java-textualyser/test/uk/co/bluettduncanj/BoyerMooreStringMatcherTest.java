/**
 * BoyerMooreTest.java
 */

package uk.co.bluettduncanj;

import static org.junit.Assert.*;

import org.junit.Test;

import uk.co.bluettduncanj.controller.BoyerMooreStringMatcher;


/**
 * @author Jonathan Bluett-Duncan
 */
public class BoyerMooreStringMatcherTest {

  /**
   * Test method for {@link uk.co.bluettduncanj.controller.BoyerMooreStringMatcher#BoyerMoore(char[], int)}.
   */
  @Test
  public void testBoyerMoore() {
    int alphabetSize = 256;
    String pattern = "badhdgdcsgsretbafvwb";
    BoyerMooreStringMatcher bm = new BoyerMooreStringMatcher(pattern.toCharArray(), alphabetSize);
    
    // Make sure the patternEquals and getBadCharTableSize methods are not commented out before testing!
    assertEquals("Patterns not equal!", true, bm.patternEquals(pattern));
    assertEquals("Unequal array sizes", alphabetSize, bm.getBadCharTableSize());
  }

  /**
   * Test method for {@link uk.co.bluettduncanj.controller.BoyerMooreStringMatcher#search(char[])}.
   */
  @Test
  public void testSearch() {
    int alphabetSize = 256;
    String text = "bbbabbaabaababbcdfgbbababababbbbaaaaaaaaabaaa";
    
    String pattern = "b";
    String message = "The number of " + pattern + "s is incorrect";
    BoyerMooreStringMatcher bm = new BoyerMooreStringMatcher(pattern.toCharArray(), alphabetSize);
    assertEquals(message, 19, bm.search(text.toCharArray()));
    
    pattern = "aaaaaaaa";
    message = "The number of " + pattern + "s is incorrect";
    bm = new BoyerMooreStringMatcher(pattern.toCharArray(), alphabetSize);
    assertEquals(message, 2, bm.search(text.toCharArray()));
    
    pattern = "aaaaaaa";
    message = "The number of " + pattern + "s is incorrect";
    bm = new BoyerMooreStringMatcher(pattern.toCharArray(), alphabetSize);
    assertEquals(message, 3, bm.search(text.toCharArray()));
    
    pattern = "aaaaaa";
    message = "The number of " + pattern + "s is incorrect";
    bm = new BoyerMooreStringMatcher(pattern.toCharArray(), alphabetSize);
    assertEquals(message, 4, bm.search(text.toCharArray()));
    
    pattern = "aaaaa";
    message = "The number of " + pattern + "s is incorrect";
    bm = new BoyerMooreStringMatcher(pattern.toCharArray(), alphabetSize);
    assertEquals(message, 5, bm.search(text.toCharArray()));
    
    pattern = "aaaa";
    message = "The number of " + pattern + "s is incorrect";
    bm = new BoyerMooreStringMatcher(pattern.toCharArray(), alphabetSize);
    assertEquals(message, 6, bm.search(text.toCharArray()));
    
    pattern = "a";
    message = "The number of " + pattern + "s is incorrect";
    bm = new BoyerMooreStringMatcher(pattern.toCharArray(), alphabetSize);
    assertEquals(message, 22, bm.search(text.toCharArray()));
    
    pattern = "ab";
    message = "The number of " + pattern + "s is incorrect";
    bm = new BoyerMooreStringMatcher(pattern.toCharArray(), alphabetSize);
    assertEquals(message, 9, bm.search(text.toCharArray()));
    
    pattern = "ba";
    message = "The number of " + pattern + "s is incorrect";
    bm = new BoyerMooreStringMatcher(pattern.toCharArray(), alphabetSize);
    assertEquals(message, 10, bm.search(text.toCharArray()));
    
    pattern = "cdfg";
    message = "The number of " + pattern + "s is incorrect";
    bm = new BoyerMooreStringMatcher(pattern.toCharArray(), alphabetSize);
    assertEquals(message, 1, bm.search(text.toCharArray()));
    
    pattern = "bbbabbaabaababbcdfgbbababababbbbaaaaaaaaabaaa";
    message = "The number of " + pattern + "s is incorrect";
    bm = new BoyerMooreStringMatcher(pattern.toCharArray(), alphabetSize);
    assertEquals(message, 1, bm.search(text.toCharArray()));
  }

}
