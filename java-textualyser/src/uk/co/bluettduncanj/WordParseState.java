/**
 * WordParseStates.java
 */

package uk.co.bluettduncanj;


/**
 * This Enum class simulates two important 'states' that are used to aid an algorithm used in FileAnalyser's process() method 
 * (in particular, it's private helper method parseSentences()) that parses words from
 * characters obtained from the user-chosen text file.
 * 
 * @author Jonathan Bluett-Duncan
 */
public enum WordParseState {
  LISTEN_FOR_NEW_WORD, READ_AND_STORE_CHARS;
}