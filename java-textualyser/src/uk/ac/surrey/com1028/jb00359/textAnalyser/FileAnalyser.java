/**
 * FileAnalyser.java
 */

package uk.ac.surrey.com1028.jb00359.textAnalyser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;


/**
 * FileAnalyser is a file-handling class that is specifically designed to work as a logical back-end for
 * the Text Analyser program's GUI front-end.
 * 
 * It uses the Singleton GoF (Gang of Four) Design Pattern to ensure only one instance of FileAnalyser is active
 * at any one time.
 * 
 * It takes data obtained from the GUI to find and analyse a text file specified by the user, to search for 
 * various simple patterns (the results of which are known as statistics).
 * 
 * @author jb00359
 */
public class FileAnalyser extends AbstractFileHandler implements IFileAnalyser {

  /** Singleton instance of FileAnalyser */
  private static FileAnalyser instance = null;
  
  /** List of sentences parsed from the text file */
  private List<String> sentences;
  
  /** List of words parsed from the text file */
  private List<String> words;
  
  /** Array of all characters in the text file */
  private char[] characters;
  
  /** Object that analyses sentences, words and characters to calculate statistics */
  private Statistics stats;
  
  /** Object that writes statistics to a log file */
  private LogFileHandler log;
  
  /** 
   * Set of true/false options that determine what categories of statistics will be analysed.
   * See the Javadoc for setOptions(boolean[], String[]) for more information.
   */
  private boolean[] options;
  
  /** Contains a text pattern that will be used by Statistics.calcNoOfTextOCs */
  private String pattern;

  /** A counter for the number of line terminators in the text file, that will be used by Statistics.calcNoOfWhitespaces */
  private int noOfLineTerminators;
  
  /**
   * Private constructor. Initialises state.
   */
  private FileAnalyser() {
    this.setFileDirectory(null);
    this.setFileName(null);
    this.stats = new Statistics();
    //this.sentences = new ArrayList<String>();
    //this.words = new ArrayList<String>();
    this.sentences = null;
    this.words = null;
    this.characters = null;
    this.log = null;
    this.options = null;
    this.pattern = null;
    this.noOfLineTerminators = 0;
  }
  
  /** 
   * @return The Singleton instance of FileAnalyser.
   */
  public static synchronized FileAnalyser getInstance() {
    if (instance == null) {
      instance = new FileAnalyser();
    }
    return instance;
  }
  
  /**
   * Setter of the name of the file which the FileAnalyser will call analysis upon.
   * 
   * @param fileName: The name of the file to set.
   *
   * @see uk.ac.surrey.com1028.jb00359.textAnalyser.AbstractFileHandler#setFileName(java.lang.String)
   */
  /**
  public void setFileName(String fileName) {
    super.setFileName(fileName);
  }
  */
  
  /**
   * Setter of the directory (absolute path) of the file which the FileAnalyser will call analysis upon.
   * 
   * @param fileName: The directory of the file to set.
   *
   * @see uk.ac.surrey.com1028.jb00359.textAnalyser.AbstractFileHandler#setFileName(java.lang.String)
   */
  /**
  public void setFileDirectory(String fileDir) {
    super.setFileDirectory(fileDir);
  }
  */
  
  /**
   * Setter of various options and other String arguments that will be used to customise file analysis.
   * 
   * For this implementation of the IFileAnalyser interface, it is required that the first index [0] of args is a String 
   * that either contains a text pattern or is null. If the third index [2] of options is equal to true, this text pattern 
   * will be used to find the number of times it appears in the text file set by setFileName(String) and 
   * setFileDirectory(String).
   * 
   * It is also required that options is a boolean array with exactly three (3) indexes. 
   * The first index [0] should be an option that determines whether the category of statistics called
   * 'Average Lengths' will be calculated. The second index [1] should be an option that determines whether the category
   * of statistics called 'Frequencies' will be calculated. The third index [2] should be an option that determines whether
   * the number of times that the String pattern (args[0]) appears in the text file will be calculated. The category 
   * shorthand name of this statistic is 'Text Occurrences'.
   * 
   * @param options: The three-element size boolean array of options that determines custom analysis output.
   * @param args: String arguments that should contain a text pattern in its first index.
   * 
   * @throws IllegalArgumentException: This will be thrown if options is not exactly 3 indexes long (options.length != 3). 
   * It will also be thrown if options[2] (the option determining whether 'Text Occurrences' will be calculated) == true 
   * && args[0] (the pattern argument) == null.
   *
   * @see uk.ac.surrey.com1028.jb00359.textAnalyser.IFileAnalyser#setOptions(boolean[], java.lang.Object)
   */
  public void setOptions(boolean[] options, String[] args) throws IllegalArgumentException {
    if (options.length != 3) {
      throw new IllegalArgumentException("The number of options is invalid.");
    }
    
    String pattern = args[0];
    
    if (options[2] == true && pattern == null) {
      throw new IllegalArgumentException("The option to analyse 'Text Occurrences' is true, but the given text pattern is null.");
    }
    
    this.options = options;
    this.pattern = pattern;
  }
  
  /**
   * A public API method that parses, analyses and generates statistics for the contents of the file defined by the user
   * through the other API methods setFileName(String) and setFileDirectory(String).
   * 
   * @throws NullStringException: This exception will be thrown if the file name or directory are null (not set).
   *
   * @see uk.ac.surrey.com1028.jb00359.textAnalyser.IFileAnalyser#process()
   */
  @Override
  public void process() throws NullStringException {
    this.stats.reset();
    this.parse();
    this.analyse();
  }

  /**
   * This method parses the contents of the text file whose file name and directory are stored in this class as
   * fileName and fileDir.
   * 
   * The contents will be parsed into appropriate sentences, words and characters for later use by a call to analyse().
   * 
   * @throws NullStringException: This exception will be thrown if the file name or directory are null (not set).
   */
  private void parse() throws NullStringException {
    if (this.getFileName() == null && this.getFileDirectory() == null) {
      throw new NullStringException("File name and directory are not set.");
    }
    if (this.getFileName() == null) {
      throw new NullStringException("File name is not set.");
    }
    if (this.getFileDirectory() == null) {
      throw new NullStringException("File directory is not set.");
    }
    
    this.parseChars();
    this.parseWords();
    this.parseSentences();
  }

  /**
   * Private helper method for parse() that parses the contents of the text file at 'fileDir/fileName' into sentences, 
   * and stores it in this.sentences.
   */
  private void parseSentences() {
    // Reset the sentences data structure
    this.sentences = new ArrayList<String>();
    /* 
     * Get a fresh batch of character text from the text file.
     * This allows us to properly deal with line terminator characters (which are considered whitespace).
     * This time, read in each character at a time, rather than line-after-line.
     * Use a list of Characters for easy adding.
     */
    List<Character> charsList = new ArrayList<Character>();
    BufferedReader read;
    String filePath = this.getFileDirectory() + File.separator + this.getFileName();
    File file = new File(filePath);
    int value = 0;
    try {
      read = new BufferedReader(new FileReader(file));
      // A flag that listens for '\r' carriage returns to allow Windows line terminators (LT) '\r\n' to be recognised
      boolean possibleWindowsLT = false;
      while ((value = read.read()) != -1) {
        char c = (char) value;
        /* 
         * For some inexplicable reason, unexpected behaviour occurs when a line terminator-related character is added to
         * the list of characters and gets analysed by the parser algorithm below this TRY-CATCH block.
         * Therefore, we replace line terminators with spaces, which appear to solve the problem.
         */
        /* 
         * If the previous char was a carriage return '\r', and the current char is a line feed '\n', add ONE
         * space to represent these two line terminators (because "\r\n" is a SINGLE Windows line terminator).
         */
        if (possibleWindowsLT == true && c == '\n') {
          charsList.add(' ');
          possibleWindowsLT = false;
        }
        else if (possibleWindowsLT == true && c == '\r') {
          /* 
           * Then both the previous and current chars are old Mac OSX style line terminators. 
           * 'Add' them both as spaces to the chars list.
           */
          charsList.add(' ');
          charsList.add(' ');
          possibleWindowsLT = false;
        }
        else if (possibleWindowsLT == true && c != '\n') {
          /* 
           * Then it turns out the previous carriage return was not part of a Windows line terminator.
           * The previous char was in fact an old Mac OSX way of line terminating, so 'add' it to the characters list as a space
           * before adding the current character, which we've determined is NOT a line terminator.
           */
          charsList.add(' ');
          charsList.add(c);
          possibleWindowsLT = false;
        }
        else if (c == '\n') {
          charsList.add(' ');
        }
        else if (c == '\r') {
          // Listen for a Windows line terminator string
          possibleWindowsLT = true;
        }
        else {
          charsList.add(c);
        }
      }
    }
    catch (IOException e) {
      e.printStackTrace();
      String errorMessage = "There was an unexpected problem reading the file! This program will most likely crash or respond in an unusual way.";
      JOptionPane.showMessageDialog(null, errorMessage, "File I/O Error", JOptionPane.ERROR_MESSAGE);
    }
    
    // Now convert the temporary character list to an array for easy manipulation
    char[] chars = this.toCharArray(charsList);
    
    // Start parser algorithm
    
    // We can only parse sentences if we have characters to begin with
    if (chars.length > 0) {
      // Keep track of a counter through all of the parsed characters
      int charIndex = 0;
      // Reference to current sentence being constructed
      String currentSentence = null;
      /* 
       * Use an enum class called SentenceParseState to simulate some of the states in the state machine diagram
       * jb00359_sentenceParseStateDiagram.state.violet.
       * 
       * Initialise to the state for listening for the start of a new sentence.
       */
      SentenceParseState state = SentenceParseState.LISTEN_FOR_NEW_SENTENCE;
      // Look through each character
      while (charIndex < chars.length) {
        // Create an easy-to-use reference to the current character
        char c = chars[charIndex];
        // Make decisions based on the current state
        switch (state) {
          case LISTEN_FOR_NEW_SENTENCE:
            
            /* 
             * If the current char is neither a whitespace nor a full stop, make it the start of the next sentence and
             * change the state so we start reading and adding more characters to it.
             */
            if (!Statistics.isWhitespace(c) && c != '.') {
              currentSentence = Character.toString(c);
              state = SentenceParseState.READ_AND_STORE_CHARS;
            }
            /* 
             * If we are at the final character out of all characters parsed from the text file
             * (i.e. we've reached the end of the WHILE loop), 
             * then we need to add the current sentence as it is to the sentences list.
             */
            if (charIndex == chars.length - 1 && currentSentence != null) {
              this.sentences.add(currentSentence);
            }
            
          break;
          case READ_AND_STORE_CHARS:
          
            // Add the current char onto the current sentence
            currentSentence += Character.toString(c);
            /* 
             * If the current char is a full stop, or if we are at the final character (i.e. end of WHILE loop), then
             * our current sentence is complete. Therefore, we need to save it to the sentences list.
             */
            if (c == '.' || charIndex == chars.length - 1) {
              this.sentences.add(currentSentence);
              currentSentence = null;
            }
            /*
             * If the current char is a full stop but we still have characters left to look through, change the state to 
             * start listening for the start of another sentence. 
             */
            if (c == '.' && charIndex < chars.length - 1) {
              state = SentenceParseState.LISTEN_FOR_NEW_SENTENCE;
            }
            
          break;
        } 
        // Increment the counter to the next character.
        charIndex++;
      }
    }
  }
  
  /**
   * Private helper method for parse() that parses the contents of the text file at 'fileDir/fileName' into words,
   * and stores it in this.words.
   */
  private void parseWords() {
    // Reset the contents of the words data structure
    this.words = new ArrayList<String>();
    /* 
     * This is a compiled regular expression. It matches 'punctuation' marks as defined by requirement S5 in the 
     * revised requirements list document.
     */
    final Pattern punctuationCompiled = Pattern.compile("[!?/:;,.]");
    
    /* 
     * Get a fresh batch of character text from the text file.
     * This allows us to properly deal with line terminator characters (which are considered whitespace).
     * This time, read in each character at a time, rather than line-after-line.
     * Use a list of Characters temporarily for easy adding.
     */
    List<Character> charsList = new ArrayList<Character>();
    BufferedReader read;
    String filePath = this.getFileDirectory() + File.separator + this.getFileName();
    File file = new File(filePath);
    int value = 0;
    try {
      read = new BufferedReader(new FileReader(file));
      while ((value = read.read()) != -1) {
        char c = (char) value;
        /* 
         * For some inexplicable reason, unexpected behaviour occurs when a line terminator-related character is added to
         * the list of characters and gets analysed by the parser algorithm below this TRY-CATCH block.
         * Therefore, we replace line terminators with spaces, which appear to solve the problem.
         */
        if (c == '\n' || c == '\r') {
          charsList.add(' ');
        }
        else {
          charsList.add(c);
        }
      }
    }
    catch (IOException e) {
      e.printStackTrace();
      String errorMessage = "There was an unexpected problem reading the file! This program will most likely crash or respond in an unusual way.";
      JOptionPane.showMessageDialog(null, errorMessage, "File I/O Error", JOptionPane.ERROR_MESSAGE);
    }
    
    // Now convert the temporary character list to an array for easy manipulation
    char[] chars = toCharArray(charsList);
    
    // Start parser algorithm.
    
    // We can only parse words if we have characters to begin with
    if (chars.length > 0) {
      // Keep track of a counter through all of the parsed characters
      int charIndex = 0;
      // Reference to current word being constructed
      String currentWord = null;
      /* 
       * Use an enum class called WordParseState to simulate the states in the state machine diagram
       * jb00359_sentenceParseStateDiagram.state.violet.
       * 
       * Initialise to the state of listening for the start of a new word.
       */
      WordParseState state = WordParseState.LISTEN_FOR_NEW_WORD;
      // Look through each character
      while (charIndex < chars.length) {
        // Create an easy-to-use reference to the current character
        char c = chars[charIndex];
        String cString = Character.toString(c);
        // Make decisions based on the current state
        switch (state) {
          case LISTEN_FOR_NEW_WORD:
            
            /*
             * If the current char is neither a whitespace nor a punctuation mark, make it the start of the next word and
             * change the state so that we start reading and adding more characters to it.
             */
            if (!Statistics.isWhitespace(c) && !punctuationCompiled.matcher(cString).matches()) {
              currentWord = cString;
              state = WordParseState.READ_AND_STORE_CHARS;
            }
            /* 
             * If we are at the final character out of all characters parsed from the text file
             * (i.e. we've reached the end of the WHILE loop), 
             * then we need to add the current word as it is to the words list.
             */
            if (charIndex == chars.length - 1 && currentWord != null) {
              this.words.add(currentWord);
            }
            
          break;
          case READ_AND_STORE_CHARS:
            
            // Add the current char onto the current word
            //currentWord += cString;
            /* 
             * If the current char is a whitespace or punctuation character, or if we are at the final character 
             * (i.e. end of WHILE loop), then our current word is complete. 
             * Therefore, we need to save it to the words list.
             */
            if ((Statistics.isWhitespace(c) || punctuationCompiled.matcher(cString).matches()) || 
                charIndex == chars.length - 1) {
              this.words.add(currentWord);
              currentWord = null;
            }
            else {
              /* 
               * If the current char is NOT a whitespace or punctuation mark, then it is part of the current word.
               * Therefore add the current char onto the current word.
               */
              currentWord += cString;
            }
            /*
             * If the current char is a whitespace or punctuation char, but we still have characters left to look through, 
             * then change the state to start listening for the start of another word. 
             */
            if ((Statistics.isWhitespace(c) || punctuationCompiled.matcher(cString).matches())
                && charIndex < chars.length - 1) {
              state = WordParseState.LISTEN_FOR_NEW_WORD;
            }
            
          break;
        }
        // Increment the counter to the next character.
        charIndex++;
      }
    }
    
    
  }

  /**
   * Private helper method for parse() that parses the contents of the text file at 'fileDir/fileName' into characters, and
   * stores it in this.characters.
   */
  private void parseChars() {
    // Create a file reference to the user-selected file...
    String filePath = this.getFileDirectory() + File.separator + this.getFileName();
    File file = new File(filePath);
    // Initialise the line terminator counter
    this.noOfLineTerminators = 0;
    // Use a List<Character> for easy adding of char elements
    List<Character> tokens = new ArrayList<Character>();
    BufferedReader read;
    // Attempt to read the file
    try {
      read = new BufferedReader(new FileReader(file));
      String line;
      // Add the character contents of each line to the character tokens
      while ((line = read.readLine()) != null) {
        for (char c : line.toCharArray()) {
          tokens.add(c);
        }
        /*
         * Note: I want line terminators to count as whitespaces. Therefore I keep a counter that counts the number of lines,
         * and therefore the number of line terminators, which will be stored in a class field for later use
         * by Statistics.calcNoOfWhitespaces(char[], int).
         */
        this.noOfLineTerminators++;
      }  
    }
    catch (IOException e) {
      e.printStackTrace();
      String errorMessage = "There was an unexpected problem reading the file! This program will most likely crash or respond in an unusual way.";
      JOptionPane.showMessageDialog(null, errorMessage, "File I/O Error", JOptionPane.ERROR_MESSAGE);
    }
    
    // Convert the List<Character> of tokens to a char array to be stored for later use
    this.characters = this.toCharArray(tokens);
  }
  
  /**
   * Private utility method for parseChars() that converts a List<Character> data structure into a char array.
   * 
   * @param chars: The list of Characters to convert.
   * 
   * @return the corresponding char array (char[]).
   */
  private char[] toCharArray(List<Character> chars) {
    StringBuilder s = new StringBuilder(chars.size());
    for (Character c : chars) {
      s.append(c);
    }
    char[] result = s.toString().toCharArray();
    return result;
  }

  /**
   * This method analyses the data structures holding the parsed contents of the text file whose file name and directory
   * are stored in this class as fileName and fileDir.
   * 
   * This method is dependent on the outcome of parse(). Therefore parse() needs to be called first.
   */
  private void analyse() {
    // Choose whether to analyse 'Average Lengths' statistics (see Javadoc for setOptions(...))
    if (options[0] == true) {
      this.stats.calcAvgSentenceLen(this.sentences);
      this.stats.calcAvgWordLen(this.words);
    }
    // Choose whether to analyse 'Frequencies' statistics (see Javadoc for setOptions(...))
    if (options[1] == true) {
      this.stats.calcCharFreq(this.characters);
      this.stats.calcNoOfEnglishANs(this.characters);
      this.stats.calcNoOfNonANs(this.characters);
      this.stats.calcNoOfWhitespaces(this.characters, this.noOfLineTerminators);
      this.stats.calcNoOfIntlChars(this.characters);
      this.stats.calcNoOfSuffixes(this.words);
    }
    // Choose whether to analyse the 'Text Occurrences' statistic (see Javadoc for setOptions(...))
    if (options[2] == true) {
      this.stats.calcNoOfTextOCs(this.characters, this.pattern.toCharArray());
    }
  }

  /**
   * A public API method that takes statistical results from a call to process() and saves a human-readable version
   * into a log file in the same directory as the processed text file. The naming convention of log files is
   * 'log_fileName'.
   * 
   * E.g. If the text file 'C:\Documents\Lord of the Rings.txt' was processed, then calling saveLog() would save
   *      the statistics to 'C:\Documents\log_Lord of the Rings.txt'.
   * 
   * @throws NullStringException: This exception will be thrown if the file name or directory are null (not set).
   * It will also be thrown if no statistics are found i.e. process() has not been called.
   * @throws FileNotFoundException: This will be thrown if the file path "does not denote an existing, writable 
   * regular file and a new regular file of that name cannot be created, or if some other error occurs while 
   * opening or creating the file" (see the throws definition for FileNotFoundException in java.io.PrintWriter). 
   *
   * @see uk.ac.surrey.com1028.jb00359.textAnalyser.IFileAnalyser#saveLog()
   */
  @Override
  public void saveLog() throws NullStringException, FileNotFoundException {
    if (this.getFileName() == null && this.getFileDirectory() == null) {
      throw new NullStringException("File name and directory are not set.");
    }
    if (this.getFileName() == null) {
      throw new NullStringException("File name is not set.");
    }
    if (this.getFileDirectory() == null) {
      throw new NullStringException("File directory is not set.");
    }
    
    String logFileName = "log_" + this.getFileName();
    
    this.log = new LogFileHandler(logFileName, this.getFileDirectory(), this.stats.toString());
    this.log.save();
  } 
  
  /**
   * A public API method that takes statistical results from a call to process() and returns it in a human-readable
   * String format.
   * 
   * @return a human-readable representation of the statistics, or null if no statistics exist (i.e. if process() was not called).
   *
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return stats.toString();
  }

  /**
   * This JUnit 4 specific test method should be commented out or made private before building the program for
   * public release!
   * 
   * This method allows JUnit 4 unit tests to be run on the initialisation of a FileAnalyser object's internal state.
   * 
   * @return true if the state was properly initialised, otherwise false.
   */
  /*
  public boolean isInitialStateOK() {
    return (this.stats != null && this.sentences.size() == 0 && this.words.size() == 0 && this.characters == null &&
        this.log == null && this.options == null && this.pattern == null && this.noOfLineTerminators == 0 &&
        this.getFileDirectory() == null && this.getFileName() == null);
  }
  */

  /**
   * This JUnit 4 specific test method should be commented out or made private before building the program for
   * public release!
   * 
   * This method allows JUnit 4 units tests to be run on the contents of the characters, words and sentences data
   * structures held within a FileAnalyser object.
   * 
   * @return a String containing the characters, words and sentences in a FileAnalyser object, in that order.
   */
  /*
  public String datStructPrint() {
    String result = "";
    for (char c : this.characters) {
      result += c + "\n";
    }
    for (String w : this.words) {
      result += w + "\n";
    }
    for (String s : this.sentences) {
      result += s + "\n";
    }
    return result;
  }
  */
}
