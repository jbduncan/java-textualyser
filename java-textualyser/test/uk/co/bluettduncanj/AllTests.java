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
@SuiteClasses({ AbstractFileHandlerTest.class, BoyerMooreTest.class, FileAnalyserTest.class, GUITest.class,
    LogFileHandlerTest.class, ReqsTest.class, StatisticsTest.class })
public class AllTests {

}
