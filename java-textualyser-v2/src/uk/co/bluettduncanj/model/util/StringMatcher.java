/**
 * SubstringMatcher.java
 */

package uk.co.bluettduncanj.model.util;


/**
 * 
 * <p>String matcher objects. They can count the number of times a pattern string occurs in a text string.</p>
 * 
 * <p>These objects are only extended-ASCII-aware. This means they are only expected to work with characters
 * in the integer range 0-255. (See <a href="http://www.ascii-code.com/">http://www.ascii-code.com/</a>).</p>
 * 
 * <p>This class uses the Boyer-Moore string searching algorithm to perform string occurrence counts.</p>
 * 
 * @author Jonathan Bluett-Duncan
 */
public class StringMatcher {
  
  /** 
   * The maximum number of characters supported. Assumes extended ASCII, i.e.
   * only supports 1-byte characters in the range 0-255. 
   */
  private static final int ALPHABET_SIZE = 256;
  
  /** The substring to search for. */
  private final String pattern;
  
  /** The bad-character skip array table. */
  private final int[] badCharTable;
  
  /** The good-suffix skip array table. */
  private final int[] goodSuffixTable;
  
  /**
   * Constructor. Create a new <tt>StringMatcher</tt> from a pattern string.
   * 
   * @param pattern
   *          The substring to search for.
   */
  public StringMatcher(String pattern) {
    this.pattern = pattern;
    this.badCharTable = initBadCharTable(pattern);
    this.goodSuffixTable = initGoodSuffixTable(pattern);
  }
  
  /**
   * Initialise the bad-character skip array table.
   * 
   * @param pattern
   *          The pattern string.
   * @return the bad-character skip array table.
   */
  private int[] initBadCharTable(String pattern) {
    int[] table = new int[ALPHABET_SIZE];
    int m = pattern.length();
    
    for (int i = 0; i < table.length; i++) {
      table[i] = m;
    }
    
    int mMinusOne = m - 1;
    for (int i = 0; i < mMinusOne; i++) {
      table[pattern.charAt(i)] = mMinusOne - i;
    }
    
    return table;
  }
  
  /**
   * Initialise the good-suffix skip array table.
   * 
   * @param pattern
   *          The pattern string.
   * @return the good-suffix skip array table.
   */
  private int[] initGoodSuffixTable(String pattern) {
    int pLen = pattern.length();
    int pLenMinusOne = pLen - 1;
    int[] table = new int[pLen];
    int lastPrefixPosition = pLen;
    
    for (int i = pLenMinusOne; i >= 0; i--) {
      if (isPrefix(pattern, i + 1)) {
        lastPrefixPosition = i + 1;
      }
      int j = pLenMinusOne - i;
      table[j] = lastPrefixPosition + j;
    }
    
    for (int i = 0; i < pLenMinusOne; i++) {
      int sLen = suffixLength(pattern, i);
      table[sLen] = pLenMinusOne - i + sLen;
    }
    
    return table;
  }
  
  /**
   * Is <code>pattern[p:end]</code> equal to a prefix of pattern?
   * 
   * @param pattern
   *          The pattern string to search in.
   * @param p
   *          The index of the first character of the substring of pattern which we want to start searching from.
   * @return <code>true</code> if <code>pattern[p:end]</code> is equal to a prefix of pattern, <code>false</code> otherwise.
   */
  private boolean isPrefix(String pattern, int p) {
    for (int i = p, j = 0; i < pattern.length(); i++, j++) {
      if (pattern.charAt(i) != pattern.charAt(j)) {
        return false;
      }
    }
    return true;
  }
  
  /**
   * Returns the maximum length of a substring of pattern that ends at index p and which is a suffix of pattern. 
   * If there does not exist any such substring, then a length of 0 is returned.
   * 
   * @param pattern
   *          The pattern string to search in for the maximum suffix length.
   * @param p
   *          The index where substrings we are potentially interested in end.
   * @return
   */
  private int suffixLength(String pattern, int p) {
    int len = 0;
    for (int i = p, j = pattern.length(); 
        i >= 0 && pattern.charAt(i) == pattern.charAt(j); i--, j--) {
      len++;
    }
    return len;
  }
  
  /**
   * <p>Counts the number of overlapping occurrences (or matches) of the String <tt>pattern</tt>
   * given at construction time in the given String <tt>text</tt>.</p>
   * 
   * <p><b><i>Examples</i></b></p>
   * 
   * <ol>
   * <li><tt>pattern</tt>: "hello", <tt>text</tt>: "hello, world" returns 1 occurrence.</li>
   * <li><tt>pattern</tt>: "abab", <tt>text</tt>: "ababab" returns 2 occurrences.</li>
   * </ol>
   * 
   * @param text
   *          The text to search through.
   * @return The number of times the <tt>pattern</tt> occurs in the <tt>text</tt>.
   */
  public int countMatches(String text) {
    
    int pLen = pattern.length();
    int tLen = text.length();
    
    // Count number of times the pattern appears in the text
    int matchesFound = 0;
    
    for (int i = pLen - 1, j; i < tLen;) {
      for (j = pLen - 1; pattern.charAt(i) == pattern.charAt(j); --i, --j) {
        if (j == 0) {
          
          // Match found!
          matchesFound++;
        }
      }
      
      i += Math.max(goodSuffixTable[pLen - 1 - j], badCharTable[text.charAt(i)]);
    }
    
    return matchesFound;
  }
  
}
