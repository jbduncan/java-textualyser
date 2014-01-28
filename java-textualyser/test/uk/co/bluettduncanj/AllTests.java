/**
 * AllTests.java
 */

package uk.co.bluettduncanj;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


/**
 * @author Jonathan Bluett-Duncan
 */
@RunWith(Suite.class)
@SuiteClasses({ AbstractFileHandlerTest.class, BoyerMooreStringMatcherTest.class, FileAnalyserTest.class, GUITest.class,
    LogFileHandlerTest.class, OtherRequirementsTest.class, StatisticsTest.class })
public class AllTests {

}
