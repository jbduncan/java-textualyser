/**
 * BoyerMoore.java
 */

package uk.co.bluettduncanj;


/**
 * This is a variation of the BoyerMoore class found at algs4.cs.princeton.edu/53substring/BoyerMoore.java.html.
 * This implementation only uses the Bad Character rule to enhance searching speeds.
 * 
 * @author Jonathan Bluett-Duncan
 */
public class BoyerMooreStringMatcher {
  
  // TODO: Improve the running speed of this algorithm by implementing other optimisations.

  /** The bad-character skip array table */
  private int[] badCharTable;
  
  /** Store the pattern as a character array */
  private char[] pattern;

  /**
   * Algorithm constructor, where the pattern is provided as a character array.
   * 
   * @param pattern: The pattern as a character array.
   * @param R: The alphabet size. If you're unsure of your alphabet size, use 256.
   */
  public BoyerMooreStringMatcher(char[] pattern, int R) {
    super();
    init(pattern, R);
  }

  /**
   * Common initialiser for constructors.
   * 
   * @param pattern: The pattern as a character array.
   * @param R: The alphabet size.
   */
  private void init(char[] pattern, int R) {
    
    this.pattern = new char[pattern.length];
    for (int j = 0; j < pattern.length; j++) {
      this.pattern[j] = pattern[j];
    }
    
    // Construct the bad character skip table
    badCharTable = new int[R];
    for (int c = 0; c < R; c++) {
      badCharTable[c] = -1;
    }
    for (int j = 0; j < pattern.length; j++) {
      badCharTable[pattern[j]] = j;
    }
  }

  /**
   * Boyer-Moore string-search algorithm - Return the number of matches of the pattern in the text.
   * 
   * @param text: The text body to search through.
   * 
   * @return the number of matches between the pattern and the text.
   */
  public int search(char[] text) {
      
    // Counter for number of matches between text and pattern
    int matchesFound = 0;
    int M = pattern.length;
    int N = text.length;
    int skip;
    for (int i = 0; i <= N - M; i += skip) {
      skip = 0;
      for (int j = M-1; j >= 0; j--) {
        if (pattern[j] != text[i+j]) {        
          // Bad character rule skip
          skip = Math.max(1, j - badCharTable[text[i+j]]);
          break;
        }
      }
      if (skip == 0) { 
        // Match found!
        matchesFound++;
        i++;
      }
    }
      
    // Searching done
    return matchesFound;                 
  }
  
  /**
   * Unit test method 1.
   * 
   * This JUnit 4 specific test method should be commented out or made private before building the program for
   * public release!
   * 
   * @return the size of the bad character table array.
   */
  public int getBadCharTableSize() {
    return this.badCharTable.length;
  }
  
  /**
   * Unit test method 2.
   * 
   * This JUnit 4 specific test method should be commented out or made private before building the program for
   * public release!
   * 
   * @param otherPattern: The pattern to compare. 
   * 
   * @return whether the char contents of the pattern passed in at construction time matches those of otherPattern.
   */
  public boolean patternEquals(String otherPattern) {
    StringBuilder s = new StringBuilder(this.pattern.length);
    for (Character c : this.pattern) {
      s.append(c);
    }
    return s.toString().equals(otherPattern);
  }
}
