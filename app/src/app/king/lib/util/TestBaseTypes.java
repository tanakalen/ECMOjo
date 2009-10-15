package king.lib.util;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.TimeZone;

import org.junit.Test;

/**
 * Test for base types.
 * 
 * @author Christoph Aschwanden
 * @since October 14, 2009
 */
public class TestBaseTypes {

  /**
   * Test for the date/time object.
   * 
   * @throws Exception  If there is a problem.
   */
  @Test
  public void testDateTime() throws Exception {
    // force Japan time zone for testing
    String zoneName = "Asia/Tokyo";
    System.setProperty("user.timezone", zoneName);
    TimeZone zone = TimeZone.getTimeZone(zoneName);
    
    // create current time and date
    DateTime now = new DateTime();
    Calendar calendar = Calendar.getInstance();
    assertEquals("The year should match.", calendar.get(Calendar.YEAR), now.getLocalYear());
    assertEquals("The month should match.", calendar.get(Calendar.MONTH) + 1, now.getLocalMonth());
    assertEquals("The day should match.", calendar.get(Calendar.DAY_OF_MONTH), now.getLocalDay());
    assertEquals("There should be a 9h difference to UTC.", now.getUTCHour(), ((now.getLocalHour() + 24) - 9) % 24);
    assertEquals("Offset is 9 * 3600000 milliseconds.", 9 * 3600000, now.getOffset());
    assertEquals("Offset is 9 * 3600000 milliseconds.", 9 * 3600000, now.getOffset(zone));
    assertEquals("Offset is -10 * 3600000 milliseconds.", -10 * 3600000, now.getOffset(TimeZone.getTimeZone("HST")));
    assertEquals("The hour in the same time zone should match.", now.getLocalHour(), now.getHour(zone));
    DateTime now2 = new DateTime(now.getTimestamp());
    assertEquals("We should have the same date and time (create new).", now, now2);
    DateTime now3 = now.clone();
    assertEquals("We should have the same date and time (clone).", now, now3);
    DateTime now4 = DateTime.createUTC(1814, 1, 1, 17, 45, 9, 64);
    assertEquals("We are in 1814.", 1814, now4.getUTCYear());
    now4.setTimestamp(now.getTimestamp());
    assertEquals("We have the correct year.", now.getUTCYear(), now4.getUTCYear());
    assertEquals("We should have the same date and time (timestamp).", now.getTimestamp(), now4.getTimestamp());
    
    // create same date/time but different time zone
    int hour = 20;
    DateTime tUTC = DateTime.createUTC(2009, 10, 13, hour, 34, 56, 897);
    DateTime tLocal = DateTime.createLocal(2009, 10, 13, hour, 34, 56, 897);
    assertFalse("Times are different.", tUTC.equals(tLocal));
    assertTrue("UTC is after local time.", tUTC.after(tLocal));
    assertTrue("Local is before local time.", tLocal.before(tUTC));
    assertTrue("Same time zone same time.", tLocal.equals(DateTime.create(zone, 2009, 10, 13, hour, 34, 56, 897)));
    assertTrue("Hour is the same for UTC-UTC.", hour == tUTC.getUTCHour());
    assertFalse("Hour is not the same for UTC-local.", hour == tUTC.getLocalHour());
    assertFalse("Hour is not the same for local-UTC.", hour == tLocal.getUTCHour());
    assertTrue("Hour is the same for local-local.", hour == tLocal.getLocalHour());
    
    // verify days and months can be different for different time zones
    DateTime tYearSwitch = DateTime.createUTC(2008, 12, 31, 21, 34, 56, 789);
    assertEquals("Year is 2008 for UTC.", 2008, tYearSwitch.getUTCYear());
    assertEquals("Year is 2009 for local.", 2009, tYearSwitch.getLocalYear());
    
    // try various values for years (negative and positive)
    DateTime tZERO = DateTime.createUTC(0, 12, 31, 21, 34, 56, 789);
    assertEquals("Year can be 0.", 0, tZERO.getUTCYear());
    DateTime tNEG = DateTime.createUTC(-12345, 12, 31, 21, 34, 56, 789);
    assertEquals("Year can be negative.", -12345, tNEG.getUTCYear());
    DateTime tP1 = DateTime.createUTC(1, 12, 31, 21, 34, 56, 789);
    assertEquals("Year can be 1.", 1, tP1.getUTCYear());
    DateTime tN1 = DateTime.createUTC(-1, 12, 31, 21, 34, 56, 789);
    assertEquals("Year can be -1.", -1, tN1.getUTCYear());
    DateTime tNEGLocal = DateTime.createLocal(-348008, 4, 30, 21, 11, 56, 789);
    assertEquals("Year can be negative (local).", -348008, tNEGLocal.getUTCYear());
    DateTime tNEGLocalCopy = new DateTime(tNEGLocal.getTimestamp());
    assertEquals("Can copy negative year.", -348008, tNEGLocalCopy.getUTCYear());
    
    // test weekday function
    DateTime monday = DateTime.createLocal(2009, 10, 12, 0, 0, 0, 0);
    assertEquals("We have a Monday.", 1, monday.getLocalWeekday());
    DateTime sunday = DateTime.createLocal(1981, 1, 25, 23, 59, 59, 999);
    assertEquals("We have a Sunday.", 7, sunday.getLocalWeekday());
    DateTime thursday = DateTime.create(zone, 1783, 9, 18, 0, 59, 0, 0);
    assertEquals("We have a Thursday.", 4, thursday.getWeekday(zone));
    DateTime friday = DateTime.createUTC(2054, 6, 19, 17, 59, 0, 0);
    assertEquals("We have a Friday.", 5, friday.getUTCWeekday());

    // test day of year function
    assertEquals("It's the end of the year.", 366, tYearSwitch.getUTCDayOfYear());
    assertEquals("It's the beginning of the year.", 1, tYearSwitch.getLocalDayOfYear());
    DateTime tYearSwitch365 = DateTime.createUTC(2100, 12, 31, 21, 34, 56, 789);
    assertEquals("It's the end of the year.", 365, tYearSwitch365.getUTCDayOfYear());
    
    // test adding and subtracting date values
    DateTime tAddSub = DateTime.create(zone, 2008, 12, 31, 21, 34, 56, 789);
    assertEquals("It's day 31.", 31, tAddSub.getDay(zone));
    tAddSub.addMinutes(3 * 60);
    assertEquals("It's day 1.", 1, tAddSub.getDay(zone));
    assertEquals("It's day 31.", 31, tAddSub.getUTCDay());
    tAddSub.addMinutes(-3 * 60);
    assertEquals("It's day 31.", 31, tAddSub.getDay(zone));
    assertEquals("It's month 12.", 12, tAddSub.getUTCMonth());
    tAddSub.addMilliseconds(-16 * 24 * 3600 * 1000);
    assertEquals("It's month 12.", 12, tAddSub.getUTCMonth());
    tAddSub.addMilliseconds(-16 * 24 * 3600 * 1000);
    assertEquals("It's month 11.", 11, tAddSub.getUTCMonth());
    DateTime tAddSubCopy = new DateTime(tAddSub.getTimestamp());
    assertEquals("It's month 11.", 11, tAddSubCopy.getUTCMonth());    
    tAddSub.addYears(-2007);
    assertEquals("Year is 1", 1, tAddSub.getUTCYear());
    tAddSub.addYears(-1);
    assertEquals("Year is 0.", 0, tAddSub.getUTCYear());
    tAddSub.addYears(-1);
    assertEquals("Year is -1.", -1, tAddSub.getUTCYear());
    tAddSub.addYears(-78349);
    assertEquals("Year is -78350.", -78350, tAddSub.getUTCYear());
    tAddSub.addYears(79350);
    assertEquals("Year is 1000.", 1000, tAddSub.getUTCYear());
    
    // test leap year function
    assertTrue("2008 is a leap year.", tYearSwitch.isUTCLeapYear());
    assertFalse("2100 is not leap year.", tYearSwitch365.isUTCLeapYear());
    assertFalse("1187 is not leap year.", DateTime.createLocal(1187, 1, 1, 0, 0, 0, 0).isLocalLeapYear());
    
    // test formatter
    DateTime tFormat = DateTime.createUTC(2007, 11, 3, 13, 1, 56, 991);
    assertEquals("UTC correctly formated.", "2007.11.03 AD at 13:01:56 UTC", tFormat.toString());
    assertEquals("Local correctly formated.", "07.11.3 AD at 22:1:56", tFormat.formatLocal("yy.M.d G 'at' H:m:s"));
    assertEquals("Zone correctly formated.", "07.11.3 AD at 22:1:56", tFormat.format(zone, "yy.M.d G 'at' H:m:ss"));
    
    // try to add illegal dates
    DateTime.createUTC(2000, 12, 1, 0, 0, 0, 0); 
    try {
      DateTime.createUTC(2000, 13, 1, 0, 0, 0, 0); 
      fail("Illegal month entered, should throw error.");
    }
    catch (Exception e) {
      // should come here
    }
    DateTime.createUTC(2000, 2, 29, 0, 0, 0, 0); 
    try {
      DateTime.createUTC(2001, 2, 29, 0, 0, 0, 0); 
      fail("Illegal day entered, should throw error.");
    }
    catch (Exception e) {
      // should come here
    }
    DateTime.createUTC(-30888, 2, 29, 0, 0, 0, 0); 
    try {
      DateTime.createUTC(-30888, 2, 29, 0, 60, 0, 0); 
      fail("Illegal minute entered, should throw error.");
    }
    catch (Exception e) {
      // should come here
    }
    DateTime.createUTC(381, 2, 1, 0, 0, 0, 422); 
    try {
      DateTime.createUTC(381, 2, 1, 0, 60, 0, 83100); 
      fail("Illegal millisecond entered, should throw error.");
    }
    catch (Exception e) {
      // should come here
    }
  }
}
