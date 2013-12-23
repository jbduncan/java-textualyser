/**
 * SentenceParseState.java
 */

package uk.co.bluettduncanj;


/**
 * This Enum class simulates two important states in the Text Analyser project's state machine design document
 * jb00359_sentenceParseStateDiagram.state.violet. These two 'states' are used to aid an algorithm in
 * FileAnalyser's process() method (in particular, it's private helper method parseSentences()) that parses sentences from
 * characters obtained from the user-chosen text file.
 * 
 * @author jb00359
 */
public enum SentenceParseState {
  LISTEN_FOR_NEW_SENTENCE, READ_AND_STORE_CHARS;
}
