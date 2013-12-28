/**
 * Statistics.java
 */

package uk.co.bluettduncanj;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Pattern;


/**
 * Statistics is a component class of FileAnalyser that analyses data structures holding parsed contents of a text file, and 
 * stores the results as statistics for later use.
 * 
 * It contains various methods which calculate different sorts of statistics, depending on the needs of a FileAnalyser object.
 * It also contains a method to return the statistics as a human-readable String [toString()].
 * 
 * @author jb00359
 */
public class Statistics {
  
  /** An easy-to-use constant reference to the operating system's default line terminator string */
  private static final String newline = System.getProperty("line.separator");
  
  /**
   * Compiled version of a whitespace regular expression - Makes multiples uses of this regex faster.
   * 
   * [\\p{Z}\\s] is a Java regular expression that finds all ASCII and Unicode whitespace characters.
   *
   * \s is a standard regex expression for any ASCII whitespace.
   *
   * According to page 120 of the source (1) below, \p{Z} is a Unicode property that can be used
   * by Unicode-compliant regex parsers to catch the 'Separator' quality of Unicode characters that are meant to separate
   * things but have no visual representation.
   *
   * Internet Sources (16/05/2013):
   * 1) http://books.google.com.au/books?id=ucwR4KIvExMC&pg=PA119&lpg=PA119&dq=regular+expression+unicode+whitespace+%22#v=onepage&q=regular%20expression%20unicode%20whitespace%20%22&f=false
   * 2) http://stackoverflow.com/questions/1822772/java-regular-expression-to-match-all-whitespace-characters
   */
  private static final Pattern whitespaceCompiled = Pattern.compile("[\\p{Z}\\s]");
  
  /** 
   * Compiled version of a regular expression - Makes multiple uses of this regex faster.
   * Represents an English alphanumeric character.
   */
  private static final Pattern englishANCompiled = Pattern.compile("[a-zA-Z0-9]");
  
  /** 
   * Compiled version of a regular expression - Makes multiple uses of this regex faster.
   * Represents a non-alphanumeric character (excludes whitespaces).
   */
  private static final Pattern nonANCompiled = Pattern.compile("[`¬!\"£$%^&*()_\\-\\+\\={}\\[\\]\\\\|,.<>/?;:'@#~]");
  
  /** 
   * Compiled version of a regular expression - Makes multiple uses of this regex faster.
   * Represents unicode characters that are not in the printable ASCII range. 
   * Designed to be used as a test for international characters.
   */
  private static final Pattern notASCIIRangeCompiled = Pattern.compile("[^\\u0000-\\u0080]+");
  
  /** Map between characters and their frequencies (out of 100%) in a text file */
  private Map<Character, Double> charFreq = null;
  
  /** 
   * Average sentence length for text file.
   * Note: This and the following class fields are initialised to -1 to tell Statistics that
   *       no corresponding calculations have yet happened.
   */
  private double avgSentenceLen;
  
  /** Average word length for text file */
  private double avgWordLen;
  
  /** Number of English alphanumeric characters [a-zA-z0-9] in text file */
  private int noOfEnglishANs;
  
  /** Number of non-alphanumeric characters. Excludes whitespaces, but includes all other symbols on a QWERTY keyboard */
  private int noOfNonANs;
  
  /** Number of whitespaces e.g. spaces, tabs and line terminators. */
  private int noOfWhitespaces;
  
  /** Number of international characters, including accented Latin characters */
  private int noOfIntlChars;
  
  /** Number of words ending in 'ed', 'ing' and 'ly' */
  private int noOfSuffixes;
  
  /** Number of times a particular text pattern appears in text file (see calcNoOfTextOCs(char[], char[])) */
  private int noOfTextOCs;

  /** A global reference to the pattern used by calcNoOfTextOCs(char[], char[]) */
  private String pattern;

  /**
   * Default constructor
   */
  public Statistics() {
    super();
    this.reset();
  }
  
  /**
   * A method that sets or resets the default state.
   */
  public void reset() {
    this.charFreq = null;
    this.avgSentenceLen = -1.0;
    this.avgWordLen = -1.0;
    this.noOfEnglishANs = -1;
    this.noOfNonANs = -1;
    this.noOfWhitespaces = -1;
    this.noOfIntlChars = -1;
    this.noOfSuffixes = -1;
    this.noOfTextOCs = -1;
    this.pattern = null;
  }
 
  /**
   * Calculate the frequency of every character in the passed-in character array, out of a 
   * percentage of all characters (out of 100%).
   * 
   * @param characters: The list of characters to analyse.
   */
  public void calcCharFreq(char[] characters) {
    
    // Use a Map implementation that will keep Character keys in order
    this.charFreq = new TreeMap<Character, Double>();
    int size = characters.length; // Keep track of size of characters
    double charCount = 0.0; // Keep track of the number of times a particular character appears in characters
    for (char c : characters) {
      
      // Identify new unique characters
      if (!this.charFreq.containsKey(c)) {
        
        // Find number of times c appears in characters
        charCount = 0.0;
        for (char charToCompare : characters)
          if (c == charToCompare) // Then we have found an occurrence of c in characters; increment a counter
            charCount++;
        
        // Calculate frequency out of 100(%), to 2 decimal places
        double freq = (charCount / size) * 100.0;
        
        // Store character with its frequency as a respective key and value pair in this.charFreq for later use
        this.charFreq.put(c, freq);
      }
    }  
  }
  
  /**
   * Calculate the average length of all sentences in the passed-in sentence list.
   * 
   * @param sentences: The list of sentences to analyse.
   */
  public void calcAvgSentenceLen(List<String> sentences) {
    double total = 0.0;
    for (String sentence : sentences) total += sentence.length();
    this.avgSentenceLen = total / sentences.size();
  }
  
  /**
   * Calculate the average length of all words in the passed-in word list.
   * 
   * @param words: The list of words to analyse.
   */
  public void calcAvgWordLen(List<String> words) {
    double total = 0.0;
    for (String word : words) total += word.length();
    this.avgWordLen = total / words.size();
  }
  
  /**
   * Calculate the number of times that English alphanumeric characters [a-zA-Z0-9] appear in the passed-in character array.
   * 
   * Abbreviation: AN is short for Alphanumeric character.
   * 
   * @param characters: The group of characters to analyse.
   */
  public void calcNoOfEnglishANs(char[] characters) {
    int count = 0;
    for (char c : characters) if (this.isEnglishAN(c)) count++;
    this.noOfEnglishANs = count;
  }
  
  /**
   * Private helper method for calcNoOfEmglishANs(char[]) that takes a character and returns whether
   * it is an English alphanumeric character.
   * 
   * @param c: The character to check.
   * 
   * @return true if c is an English alphanumeric character, otherwise false.
   */
  private boolean isEnglishAN(char c) {
    return englishANCompiled.matcher(Character.toString(c)).matches();
  }
  
  /**
   * Calculate the number of times that non-alphanumeric characters appear in the passed-in character array.
   * Non-alphanumeric characters exclude whitespaces, but include all other symbols on a United Kingdom QWERTY keyboard.
   * 
   * Abbreviation: AN is short for Alphanumeric character.
   * 
   * @param characters: The group of characters to analyse.
   */
  public void calcNoOfNonANs(char[] characters) {
    int count = 0;
    for (char c : characters) if (this.isNonAN(c)) count++;
    this.noOfNonANs = count;
  }
  
  /**
   * Private helper method for calcNoOfNonANs(char[]) that takes a character and returns whether
   * it is an non-alphanumeric character (excluding whitespaces but including all other symbols
   * on a United Kingdom QWERTY keyboard).
   * 
   * @param c: The character to check.
   * 
   * @return true if c is a non-alphanumeric character, otherwise false.
   */
  private boolean isNonAN(char c) {
    return nonANCompiled.matcher(Character.toString(c)).matches();
  }

  /**
   * Calculate the number of times that whitespaces appear in the passed-in character array.
   * Whitespaces include many characters that do not have a visual representation, such as spaces, tabs and line terminators.
   * Note that the number of line terminators in char[] characters needs to be passed in independently, since characters
   * is expected to be an array of all characters from a text source excluding any line terminators.
   * 
   * @param characters: The group of characters to analyse.
   * @param noOfLineTerminators: A number of line terminators that will be added to the number of whitespaces.
   */
  public void calcNoOfWhitespaces(char[] characters, int noOfLineTerminators) {
    int count = noOfLineTerminators;
    for (char c : characters) if (Statistics.isWhitespace(c)) count++;
    this.noOfWhitespaces = count;
  }
  
  /**
   * Public helper method for calcNoOfWhitespaces(char[]) and various methods in FileAnalyser that takes a character 
   * and returns whether it is a whitespace character (including spaces, tabs and line terminators).
   * 
   * @param c: The character to check.
   * 
   * @return true if c is whitespace, otherwise false.
   */
  public static boolean isWhitespace(char c) {
    return whitespaceCompiled.matcher(Character.toString(c)).matches();
  }

  /**
   * Calculate the number of times that international characters and accented latin characters appear in the
   * passed-in character array.
   * 
   * Abbreviation: Intl is short for International.
   * 
   * @param characters: The group of characters to analyse.
   */
  public void calcNoOfIntlChars(char[] characters) {
    int count = 0;
    for (char c : characters) if (this.isIntl(c)) count++;
    this.noOfIntlChars = count;
  }
  
  /**
   * Private helper method for calcNoOfIntlChars(char[]) that takes a character and returns whether it is an
   * international or accented character.
   * 
   * @param c: The character to check.
   * 
   * @return true if c is an international or accented character, otherwise false.
   */
  private boolean isIntl(char c) {
    return notASCIIRangeCompiled.matcher(Character.toString(c)).matches();
  }
  
  /**
   * Calculate the number of times a word in the passed-in String list ends with one of the suffixes 'ed', 'ing' and 'ly'.
   * 
   * @param words: The list of words to analyse.
   */
  public void calcNoOfSuffixes(List<String> words) {
    int count = 0;
    for (String w : words)
      if (w.endsWith("ed") || w.endsWith("ing") || w.endsWith("ly"))
        count++;
    this.noOfSuffixes = count;
  }

  /**
   * Calculate the number of times that a char[] pattern appears in a char[] body of text.
   * 
   * This method implements Boyer-Moore's string-search algorithm to efficiently find the number of occurrences
   * of the needle in the body.
   * 
   * Abbreviation: TextOC is short for Text Occurrence.
   * 
   * @param body: The group of character text to search through.
   * @param pattern: The group of character text to find in the body.
   */
  public void calcNoOfTextOCs(char[] body, char[] pattern) {
    
    // Store the pattern as a class field for later referral by toString()
    StringBuilder s = new StringBuilder(pattern.length);
    for (Character c : pattern) s.append(c);
    this.pattern = s.toString();

    // Search for the number of matches of the pattern against the body
    BoyerMoore bm = new BoyerMoore(pattern, 256);
    this.noOfTextOCs = bm.search(body);
  }

  /**
   * This overridden implementation of toString() returns the state of this Statistics object as a human-readable
   * String.
   * 
   * @return a human-readable representation of the statistics, or null if no statistics have yet been calculated.
   *
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    String output = "";
    
    // Only return a non-null String if any analysis has actually happened, i.e. if any of the corresponding results
    // class fields no longer contain their default values.
    if (this.charFreq != null || this.avgSentenceLen != -1.0 || this.avgWordLen != -1.0 || 
        this.noOfEnglishANs != -1 || this.noOfNonANs != -1 || this.noOfWhitespaces != -1 ||
        this.noOfIntlChars != -1 || this.noOfSuffixes != -1 || this.noOfTextOCs != -1) {
      
      // Firstly, output the date and time at the time this method was invoked
      output += this.dateTimeToString();
       
      // A section of the output String will contain stats about Average Lengths... 
      output += this.avgLensToString();

      // Another section of the output String will contain stats about Frequencies....
      output += this.frequenciesToString();
      
      // Yet another section of the output String will contain a statistic about the number of times
      // a user-defined pattern string appears in the text file (where this stat is called Text Occurrences)...
      output += this.textOCsToString();
    }
    
    return output;
  }
  
  /**
   * A private helper method for toString().
   * 
   * @return a sub-string containing the current date and time.
   */
  private String dateTimeToString() {
    String output = "";
    Date now = Calendar.getInstance().getTime();
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY");
    String time = dateFormat.format(now);
    SimpleDateFormat timeFormat = new SimpleDateFormat("KK:mm:ss a");
    String date = timeFormat.format(now);
    SimpleDateFormat timeZoneFormat = new SimpleDateFormat("z");
    String timeZone = timeZoneFormat.format(now);
    
    output += 
        time + ", " +
        date + " " +
        timeZone + newline;
    return output;
  }
  
  /**
   * A private helper method for toString().
   * 
   * @return a sub-string containing a human-readable form of the 'Average Lengths' statistics.
   */
  private String avgLensToString() {
    String output = "";
    
    // Execute this method only if any of the related calculation methods were called e.g. calcAvgSentenceLen(List<String>).
    if (this.avgSentenceLen != -1 || this.avgWordLen != -1) {
      
      // Add the category of stats to do with Average Lengths.
      output += newline + "=== Average Lengths ===" + newline;
      if (this.isAnalysed(this.avgSentenceLen)) {
        
        // Add the result from calcAvgSentenceLen(...) to the output string in a readable form
        output += "Average sentence length: " + this.to2DecimalPlaces(this.avgSentenceLen) + newline;
      }
      if (this.isAnalysed(this.avgWordLen)) {
        
        // Add the result from calcAvgWordLen(...) to the output string in a readable form
        output += "Average word length: " + this.to2DecimalPlaces(this.avgWordLen) + newline;
      }
    }
    return output;
  }
  
  /**
   * A private helper method for toString(). 
   * 
   * @return a sub-string containing a human-readable form of the 'Frequencies' statistics.
   */
  private String frequenciesToString() {
    
    // Execute this method only if any of the related calculation methods were called e.g. calcCharFreq(char[]) or 
    // calcNoOfIntlChars(char[]).
    String output = "";
    if (this.charFreq != null || this.noOfEnglishANs != -1 || this.noOfNonANs != -1 || this.noOfIntlChars != -1 ||
        this.noOfWhitespaces != -1 || this.noOfSuffixes != -1) {
      
      // Add the category of stats to do with frequencies.
      output += newline + "=== Frequencies ===" + newline;
      if (this.charFreq != null) {
        
        // Add the results from calcCharFreq(...) to the output string in a readable form
        output += "--- Character frequencies (out of 100%) ---" + newline;
        
        // We want to treat whitespaces differently; whitespaces include a whole range of possible
        // non-visible characters e.g. spaces and tabs. 
        //
        // Therefore we will want keep track of the total percentage frequency of all whitespaces. 
        // 
        // We will add the number of whitespaces to the end of the output string
        // when all the other characters in this.charFreq have been dealt with.
        double totalWhitespaceFreq = 0.0;
        for (Character key : this.charFreq.keySet()) {
          double charFreq = this.charFreq.get(key);
          
          // Output the frequencies of each non-whitespace character
          if (!Statistics.isWhitespace(key))
            output += key + ": " + this.to2DecimalPlaces(charFreq) + "%" + newline;
          
          // If we come across a whitespace character, add its frequency to the local record
          else totalWhitespaceFreq += charFreq;
        }
        
        // Now output the total frequency of whitespace characters
        output += "Whitespaces: " + this.to2DecimalPlaces(totalWhitespaceFreq) + "%" + newline;
      }
      if (this.charFreq != null || this.noOfEnglishANs != -1 || this.noOfNonANs != -1 || this.noOfIntlChars != -1 ||
          this.noOfWhitespaces != -1 || this.noOfSuffixes != -1) {
        
        // Add the sub-category of stats to do with whole numbers of specific characters/strings e.g. noOfIntlChars
        output += "--- Other numbers ---" + newline;
        if (this.isAnalysed(this.noOfEnglishANs)) {
          
          // Add the result from calcNoOfEnglishANs(...) to the output string in a readable form
          output += "English alphanumeric characters: " + this.toWholeNumber(this.noOfEnglishANs) + newline;
        }
        if (this.isAnalysed(this.noOfNonANs)) {
          
          // Add the result from calcNoOfNonANs(...) to the output string in a readable form
          output += "Non-alphanumeric characters: " + this.toWholeNumber(this.noOfNonANs) + newline;
        }
        if (this.isAnalysed(this.noOfWhitespaces)) {
          
          // Add the result from calcNoOfWhitespaces(...) to the output string in a readable form
          output += "Whitespaces: " + this.toWholeNumber(this.noOfWhitespaces) + newline;
        }
        if (this.isAnalysed(this.noOfIntlChars)) {
          
          // Add the result from calcNoOfIntlChars(...) to the output string in a readable form
          output += "International/Accented characters: " + this.toWholeNumber(this.noOfIntlChars) + newline;
        }
        if (this.isAnalysed(this.noOfSuffixes)) {
          
          // Add the result from calcNoOfSuffixes(...) to the output string in a readable form
          output += "No. of suffixes 'ed' 'ing' 'ly': " + this.toWholeNumber(this.noOfSuffixes) + newline;
        }
      }
    }
    return output;
  }
  
  /**
   * A private helper method for toString().
   * 
   * @return a sub-string containing a human-readable form of the 'Text Occurrences' statistic.
   */
  private String textOCsToString() {
    String output = "";
    
    // Execute this method only if calcNoOfTextOCs(...) was called.
    if (this.isAnalysed(this.noOfTextOCs)) {
      
      // Add the result from calcNoOfTextOCs(...) to the output string in a readable form
      output +=
          newline + "=== Text Occurrences ===" + newline +
          "Number of occurrences of " + this.pattern + " in text file: " + this.noOfTextOCs;
    }
    return output;
  }

  /**
   * Private helper method for toString() that checks whether a chosen numerical statistics-holding class field, 
   * e.g. noOfEnglishANs, has had its value changed from the default of -1.
   * 
   * Any change from the default of -1 implies that the corresponding calculation (analysis) method, 
   * e.g. calcNoOfEnglishANs(char[]), has been executed.
   * 
   * @param classFieldContents: The contents of the chosen numerical class field.
   * 
   * @return true if the class field's content has changed from the default of -1, otherwise false.
   */
  private boolean isAnalysed(double classFieldContents) {
    return (classFieldContents != -1.0);
  }

  /**
   * A private utility method for toString() that converts a double-type number into a String representing the same
   * number to 2 decimal places.
   * 
   * @param number: The number to convert to a 2 decimal place string.
   * 
   * @return the String representing the number to 2 decimal places.
   */
  private String to2DecimalPlaces(double number) {
    return String.format("%.2f", number);
  }
  
  /**
   * A private utility method for toString() that converts a double-type number into a String representing the same
   * number as if it were truncated (floored) to a whole number (integer).
   * 
   * @param number: The number to convert.
   * 
   * @return the String representing the number as a truncated whole number.
   */
  private String toWholeNumber(double number) {
    return String.format("%.0f", number);
  }

  /**
   * This JUnit 4 specific test method should be commented out or made private before building the program for
   * public release!
   * 
   * This method allows JUnit tests to be run on the internal state of a Statistics object, and
   * check it is set to the default (reset) state.
   * 
   * @return true if the state is (re)set to default, otherwise false.
   */
  public boolean isReset() {
    return (this.charFreq == null && this.avgSentenceLen == -1.0 && this.avgWordLen == -1.0 &&
        this.noOfEnglishANs == -1 && this.noOfNonANs == -1 && this.noOfWhitespaces == -1 &&
        this.noOfIntlChars == -1 && this.noOfSuffixes == -1 && this.noOfTextOCs == -1 && this.pattern == null);
  }
  
}
