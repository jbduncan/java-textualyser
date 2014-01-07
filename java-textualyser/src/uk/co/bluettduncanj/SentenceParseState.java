/**
 * SentenceParseState.java
 */

package uk.co.bluettduncanj;


/**
 * This Enum class simulates two important 'states' that are used to aid an algorithm used in FileAnalyser's process() method 
 * (in particular, it's private helper method parseSentences()) that parses sentences from
 * characters obtained from the user-chosen text file.
 * 
 * @author Jonathan Bluett-Duncan
 */
public enum SentenceParseState {
  LISTEN_FOR_NEW_SENTENCE, READ_AND_STORE_CHARS;
}
