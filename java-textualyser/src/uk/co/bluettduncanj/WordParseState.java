/**
 * WordParseStates.java
 */

package uk.co.bluettduncanj;


/**
 * This Enum class simulates two important states in the Text Analyser project's state machine design document
 * jb00359_wordParseStateDiagram.state.violet. These two 'states' are used to aid an algorithm in
 * FileAnalyser's process() method (in particular, it's private helper method parseWords()) that parses words from
 * characters obtained from the user-chosen text file.
 * 
 * @author jb00359
 */
public enum WordParseState {
  LISTEN_FOR_NEW_WORD, READ_AND_STORE_CHARS;
}
