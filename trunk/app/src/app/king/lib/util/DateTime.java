package king.lib.util;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * The date and time class. The time is stored in UTC from the epoch. Please note that the timestamp is time
 * zone independent. Use getTimestamp() and setTimestamp(...) for time zone independence.
 * <p>
 * UTC from Epoch: A millisecond value that is an offset from the Epoch, January 1, 1970 00:00:00.000 UTC.
 *                 
 * @author Christoph Aschwanden
 * @since September 2, 2008
 */
public class DateTime implements Serializable, Comparable {

  /** The underlying calendar object. */
  private GregorianCalendar calendar;
  
  
  /**
   * Constructor for date/time using the current time in the current time zone. The current time is
   * converted internally to UTC.
   */
  public DateTime() {
    calendar = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
  }
  
  /**
   * Constructor for date/time with a timestamp in UTC milliseconds from the epoch.
   * 
   * @param timestamp  The timestamp in UTC milliseconds from the epoch.
   */
  public DateTime(long timestamp) {
    this();
    
    // and set the timestamp
    setTimestamp(timestamp);
  }
  
  /**
   * Creator for date/time using the inputed time in UTC.
   * Throws an IllegalArgumentException if the input is invalid.
   * <p>
   * IMPORTANT: The inputed time has to be in UTC.
   *
   * @param year  The year [-1000000, 1000000] including year 0!
   * @param month  The month [1, 12]. 1 = January, 2 = February etc. 
   * @param day  The day of the month [1, 31].
   * @param hour  The hour of the day in 24h format [0, 23].
   * @param minute  The minute [0, 59].
   * @param second  The second [0, 59].
   * @param millisecond  The millisecond [0, 999].
   */
  public static DateTime createUTC(int year, int month, int day
                                 , int hour, int minute, int second, int millisecond) {
    DateTime dateTime = new DateTime();
    dateTime.setUTC(year, month, day, hour, minute, second, millisecond);
    return dateTime;
  }
  
  /**
   * Creator for date/time using the inputed time in the local time zone.
   * Throws an IllegalArgumentException if the input is invalid.
   * <p>
   * IMPORTANT: The inputed time has to be in the local time zone.
   *
   * @param year  The year [-1000000, 1000000] including year 0!
   * @param month  The month [1, 12]. 1 = January, 2 = February etc. 
   * @param day  The day of the month [1, 31].
   * @param hour  The hour of the day in 24h format [0, 23].
   * @param minute  The minute [0, 59].
   * @param second  The second [0, 59].
   * @param millisecond  The millisecond [0, 999].
   */
  public static DateTime createLocal(int year, int month, int day
                                   , int hour, int minute, int second, int millisecond) {
    DateTime dateTime = new DateTime();
    dateTime.setLocal(year, month, day, hour, minute, second, millisecond);
    return dateTime;
  }

  /**
   * Creator for date/time using the inputed time in the inputed time zone.
   * Throws an IllegalArgumentException if the input is invalid.
   * <p>
   * IMPORTANT: The inputed time has to be in the inputed time zone.
   *
   * @param zone  The time zone.
   * @param year  The year [-1000000, 1000000] including year 0!
   * @param month  The month [1, 12]. 1 = January, 2 = February etc. 
   * @param day  The day of the month [1, 31].
   * @param hour  The hour of the day in 24h format [0, 23].
   * @param minute  The minute [0, 59].
   * @param second  The second [0, 59].
   * @param millisecond  The millisecond [0, 999].
   */
  public static DateTime create(TimeZone zone, int year, int month, int day
                                             , int hour, int minute, int second, int millisecond) {
    DateTime dateTime = new DateTime();
    dateTime.set(zone, year, month, day, hour, minute, second, millisecond);
    return dateTime;
  }

  /**
   * Sets a timestamp.
   *
   * @param timestamp  The timestamp in UTC milliseconds from the epoch.
   */
  public void setTimestamp(long timestamp) {
    calendar.setTimeInMillis(timestamp);
  }
  
  /**
   * Returns the timestamp.
   *
   * @return  The timestamp in UTC milliseconds from the epoch.
   */
  public long getTimestamp() {
    return calendar.getTimeInMillis();
  }
  
  /**
   * Sets date/time using the inputed time in UTC. 
   * Throws an IllegalArgumentException if the input is invalid.
   * <p>
   * IMPORTANT: The inputed time has to be in UTC.
   *
   * @param year The year [-1000000, 1000000] including year 0!
   * @param month  The month [1, 12]. 1 = January, 2 = February etc. 
   * @param day  The day of the month [1, 31].
   * @param hour  The hour of the day in 24h format [0, 23].
   * @param minute  The minute [0, 59].
   * @param second  The second [0, 59].
   * @param millisecond  The millisecond [0, 999].
   */
  public void setUTC(int year, int month, int day, int hour, int minute, int second, int millisecond) {
    validate(year, month, day, hour, minute, second, millisecond);
    if (year > 0) {
      calendar.set(Calendar.ERA, GregorianCalendar.AD);
    }
    else {
      calendar.set(Calendar.ERA, GregorianCalendar.BC);
      year = -(year - 1);
    }
    calendar.set(year, month - 1, day, hour, minute, second);
    calendar.set(Calendar.MILLISECOND, millisecond);
  }
  
  /**
   * Sets date/time using the inputed time in the local time zone. The time is converted internally to UTC.
   * Throws an IllegalArgumentException if the input is invalid.
   * <p>
   * IMPORTANT: The inputed time has to be in the local time zone. 
   *
   * @param year  The year [-1000000, 1000000] including year 0!
   * @param month  The month [1, 12]. 1 = January, 2 = February etc. 
   * @param day  The day of the month [1, 31].
   * @param hour  The hour of the day in 24h format [0, 23].
   * @param minute  The minute [0, 59].
   * @param second  The second [0, 59].
   * @param millisecond  The millisecond [0, 999].
   */
  public void setLocal(int year, int month, int day, int hour, int minute, int second, int millisecond) {
    validate(year, month, day, hour, minute, second, millisecond);
    GregorianCalendar calendar = new GregorianCalendar();
    if (year > 0) {
      calendar.set(Calendar.ERA, GregorianCalendar.AD);
    }
    else {
      calendar.set(Calendar.ERA, GregorianCalendar.BC);
      year = -(year - 1);
    }
    calendar.set(year, month - 1, day, hour, minute, second);
    calendar.set(Calendar.MILLISECOND, millisecond);
    this.calendar.setTimeInMillis(calendar.getTimeInMillis());
  }
  
  /**
   * Sets date/time using the inputed time in the inputed time zone. The time is converted internally to UTC.
   * Throws an IllegalArgumentException if the input is invalid.
   * <p>
   * IMPORTANT: The inputed time has to be in the inputed time zone. 
   *
   * @param zone  The time zone.
   * @param year  The year [-1000000, 1000000] including year 0!
   * @param month  The month [1, 12]. 1 = January, 2 = February etc. 
   * @param day  The day of the month [1, 31].
   * @param hour  The hour of the day in 24h format [0, 23].
   * @param minute  The minute [0, 59].
   * @param second  The second [0, 59].
   * @param millisecond  The millisecond [0, 999].
   */
  public void set(TimeZone zone, int year, int month, int day, int hour, int minute, int second, int millisecond) {
    validate(year, month, day, hour, minute, second, millisecond);
    GregorianCalendar calendar = new GregorianCalendar(zone);
    if (year > 0) {
      calendar.set(Calendar.ERA, GregorianCalendar.AD);
    }
    else {
      calendar.set(Calendar.ERA, GregorianCalendar.BC);
      year = -(year - 1);
    }
    calendar.set(year, month - 1, day, hour, minute, second);
    calendar.set(Calendar.MILLISECOND, millisecond);
    this.calendar.setTimeInMillis(calendar.getTimeInMillis());
  }

  /**
   * Returns true if the inputed date and time is valid..
   *
   * @param year  The year [-1000000, 1000000] including year 0!
   * @param month  The month [1, 12]. 1 = January, 2 = February etc. 
   * @param day  The day of the month [1, 31].
   * @param hour  The hour of the day in 24h format [0, 23].
   * @param minute  The minute [0, 59].
   * @param second  The second [0, 59].
   * @param millisecond  The millisecond [0, 999].
   * @return  True for valid.
   */
  public boolean isValid(int year, int month, int day, int hour, int minute, int second, int millisecond) {
    try {
      validate(year, month, day, hour, minute, second, millisecond);
      return true;
    }
    catch (IllegalArgumentException e) {
      return false;
    }
  }

  /**
   * Validates the inputed date. Throws an IllegalArgumentException if the input is invalid.
   *
   * @param year  The year [-1000000, 1000000] including year 0!
   * @param month  The month [1, 12]. 1 = January, 2 = February etc. 
   * @param day  The day of the month [1, 31].
   * @param hour  The hour of the day in 24h format [0, 23].
   * @param minute  The minute [0, 59].
   * @param second  The second [0, 59].
   * @param millisecond  The millisecond [0, 999].
   */
  private void validate(int year, int month, int day, int hour, int minute, int second, int millisecond) {
    if ((year < -1000000) || (year > 100000)) {
      throw new IllegalArgumentException("error.YearOutOfRange[i18n]: The year is out of range.");
    }
    else if ((month < 1) || (month > 12)) {
      throw new IllegalArgumentException("error.MonthOutOfRange[i18n]: The month is out of range.");
    }
    else if ((hour < 0) || (hour > 23)) {
      throw new IllegalArgumentException("error.HourOutOfRange[i18n]: The hour is out of range.");
    }
    else if ((minute < 0) || (minute > 59)) {
      throw new IllegalArgumentException("error.MinuteOutOfRange[i18n]: The minute is out of range.");
    }
    else if ((second < 0) || (second > 59)) {
      throw new IllegalArgumentException("error.SecondOutOfRange[i18n]: The second is out of range.");
    }
    else if ((millisecond < 0) || (millisecond > 999)) {
      throw new IllegalArgumentException("error.MillisecondOutOfRange[i18n]: The millisecond is out of range.");
    }
    else {
      // verify day of the month
      final int[] days;
      if (calendar.isLeapYear(year)) {
        days = new int[] { 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
      }
      else {
        days = new int[] { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
      }
      if ((day < 1) || (day > days[month - 1])) {
        throw new IllegalArgumentException("error.DayOutOfRange[i18n]: The day is out of range.");
      }
    }
  }
  
  /**
   * Returns the offset of the local time in the local time zone to UTC.
   * 
   * @return  The local time zone offset to UTC; the time in milliseconds 
   *          to add to UTC to get the time in the local time zone.
   */
  public int getOffset() {
    GregorianCalendar calendar = new GregorianCalendar();
    return calendar.getTimeZone().getRawOffset();
  }
  
  /**
   * Returns the offset of the local time in the inputed time zone to UTC.
   * 
   * @param zone  The time zone.
   * @return  The inputed time zone offset to UTC; the time in milliseconds 
   *          to add to UTC to get the time in the inputed time zone.
   */
  public int getOffset(TimeZone zone) {
    GregorianCalendar calendar = new GregorianCalendar(zone);
    return calendar.getTimeZone().getRawOffset();
  }

  /**
   * Returns the year in UTC.
   *
   * @return  The year [-1000000, 1000000] including year 0!
   */
  public int getUTCYear() {
    if (calendar.get(Calendar.ERA) == GregorianCalendar.AD) {
      return calendar.get(Calendar.YEAR);
    }
    else {
      return -(calendar.get(Calendar.YEAR) - 1);
    }
  }
  
  /**
   * Returns the year for the local time zone.
   *
   * @return  The year [-1000000, 1000000] including year 0!
   */
  public int getLocalYear() {
    GregorianCalendar calendar = new GregorianCalendar();
    calendar.setTimeInMillis(this.calendar.getTimeInMillis());
    if (calendar.get(Calendar.ERA) == GregorianCalendar.AD) {
      return calendar.get(Calendar.YEAR);
    }
    else {
      return -(calendar.get(Calendar.YEAR) - 1);
    }
  }

  /**
   * Returns the year for the inputed time zone.
   *
   * @param zone  The time zone.
   * @return  The year [-1000000, 1000000] including year 0!
   */
  public int getYear(TimeZone zone) {
    GregorianCalendar calendar = new GregorianCalendar(zone);
    calendar.setTimeInMillis(this.calendar.getTimeInMillis());
    if (calendar.get(Calendar.ERA) == GregorianCalendar.AD) {
      return calendar.get(Calendar.YEAR);
    }
    else {
      return -(calendar.get(Calendar.YEAR) - 1);
    }
  }

  /**
   * Returns the month in UTC.
   *
   * @return  The month [1, 12]. 1 = January, 2 = February etc. 
   */
  public int getUTCMonth() {
    return calendar.get(Calendar.MONTH) + 1;
  }
  
  /**
   * Returns the month for the local time zone.
   *
   * @return  The month [1, 12]. 1 = January, 2 = February etc. 
   */
  public int getLocalMonth() {
    GregorianCalendar calendar = new GregorianCalendar();
    calendar.setTimeInMillis(this.calendar.getTimeInMillis());
    return calendar.get(Calendar.MONTH) + 1;
  }

  /**
   * Returns the month for the inputed time zone.
   *
   * @param zone  The time zone.
   * @return  The month [1, 12]. 1 = January, 2 = February etc. 
   */
  public int getMonth(TimeZone zone) {
    GregorianCalendar calendar = new GregorianCalendar(zone);
    calendar.setTimeInMillis(this.calendar.getTimeInMillis());
    return calendar.get(Calendar.MONTH) + 1;
  }

  /**
   * Returns the day of the month in UTC.
   *
   * @return  The day of the month [1, 31].
   */
  public int getUTCDay() {
    return calendar.get(Calendar.DAY_OF_MONTH);
  }
  
  /**
   * Returns the day of the month for the local time zone.
   *
   * @return  The day of the month [1, 31].
   */
  public int getLocalDay() {
    GregorianCalendar calendar = new GregorianCalendar();
    calendar.setTimeInMillis(this.calendar.getTimeInMillis());
    return calendar.get(Calendar.DAY_OF_MONTH);
  }

  /**
   * Returns the day of the month for the inputed time zone.
   *
   * @param zone  The time zone.
   * @return  The day of the month [1, 31].
   */
  public int getDay(TimeZone zone) {
    GregorianCalendar calendar = new GregorianCalendar(zone);
    calendar.setTimeInMillis(this.calendar.getTimeInMillis());
    return calendar.get(Calendar.DAY_OF_MONTH);
  }

  /**
   * Returns the weekday in UTC.
   *
   * @return  The weekday [1, 7]. 1 = Monday, 2 = Tuesday, 3 = Wednesday etc.
   */
  public int getUTCWeekday() {
    int day = calendar.get(Calendar.DAY_OF_WEEK);
    switch (day) {
      case Calendar.MONDAY:
        return 1;
      case Calendar.TUESDAY:
        return 2;
      case Calendar.WEDNESDAY:
        return 3;
      case Calendar.THURSDAY:
        return 4;
      case Calendar.FRIDAY:
        return 5;
      case Calendar.SATURDAY:
        return 6;
      case Calendar.SUNDAY:
        return 7;        
      default:
        // invalid
        throw new RuntimeException("Illegal weekday encountered: " + day);
    }
  }
  
  /**
   * Returns the weekday for the local time zone.
   *
   * @return  The weekday [1, 7]. 1 = Monday, 2 = Tuesday, 3 = Wednesday etc.
   */
  public int getLocalWeekday() {
    GregorianCalendar calendar = new GregorianCalendar();
    calendar.setTimeInMillis(this.calendar.getTimeInMillis());
    int day = calendar.get(Calendar.DAY_OF_WEEK);
    switch (day) {
      case Calendar.MONDAY:
        return 1;
      case Calendar.TUESDAY:
        return 2;
      case Calendar.WEDNESDAY:
        return 3;
      case Calendar.THURSDAY:
        return 4;
      case Calendar.FRIDAY:
        return 5;
      case Calendar.SATURDAY:
        return 6;
      case Calendar.SUNDAY:
        return 7;        
      default:
        // invalid
        throw new RuntimeException("Illegal weekday encountered: " + day);
    }
  }

  /**
   * Returns the weekday for the inputed time zone.
   *
   * @param zone  The time zone.
   * @return  The weekday [1, 7]. 1 = Monday, 2 = Tuesday, 3 = Wednesday etc.
   */
  public int getWeekday(TimeZone zone) {
    GregorianCalendar calendar = new GregorianCalendar(zone);
    calendar.setTimeInMillis(this.calendar.getTimeInMillis());
    int day = calendar.get(Calendar.DAY_OF_WEEK);
    switch (day) {
      case Calendar.MONDAY:
        return 1;
      case Calendar.TUESDAY:
        return 2;
      case Calendar.WEDNESDAY:
        return 3;
      case Calendar.THURSDAY:
        return 4;
      case Calendar.FRIDAY:
        return 5;
      case Calendar.SATURDAY:
        return 6;
      case Calendar.SUNDAY:
        return 7;        
      default:
        // invalid
        throw new RuntimeException("Illegal weekday encountered: " + day);
    }
  }

  /**
   * Returns the day of the year in UTC.
   * 
   * @return  The day of the year [1, 365]. First day starting with 1.
   */
  public int getUTCDayOfYear() {
    return calendar.get(Calendar.DAY_OF_YEAR);
  }
  
  /**
   * Returns the day of the year for the local time zone.
   *
   * @return  The day of the year [1, 365]. First day starting with 1.
   */
  public int getLocalDayOfYear() {
    GregorianCalendar calendar = new GregorianCalendar();
    calendar.setTimeInMillis(this.calendar.getTimeInMillis());
    return calendar.get(Calendar.DAY_OF_YEAR);
  }

  /**
   * Returns the day of the year for the inputed time zone.
   *
   * @param zone  The time zone.
   * @return  The day of the year [1, 365]. First day starting with 1.
   */
  public int getDayOfYear(TimeZone zone) {
    GregorianCalendar calendar = new GregorianCalendar(zone);
    calendar.setTimeInMillis(this.calendar.getTimeInMillis());
    return calendar.get(Calendar.DAY_OF_YEAR);
  }
  
  /**
   * Returns true for leap year in UTC.
   * 
   * @return  True for leap year. A normal year has 365 days. A leap year has 366 days. 
   */
  public boolean isUTCLeapYear() {
    int year = getUTCYear();
    if (year > 0) {
      return calendar.isLeapYear(year);
    }
    else {
      // no leap years for BC
      return false;
    }
  }
  
  /**
   * Returns true for leap year for the local time zone.
   *
   * @return  True for leap year. A normal year has 365 days. A leap year has 366 days. 
   */
  public boolean isLocalLeapYear() {
    int year = getLocalYear();
    if (year > 0) {
      return calendar.isLeapYear(year);
    }
    else {
      // no leap years for BC
      return false;
    }
  }

  /**
   * Returns true for leap year for the inputed time zone.
   *
   * @param zone  The time zone.
   * @return  True for leap year. A normal year has 365 days. A leap year has 366 days. 
   */
  public boolean isLeapYear(TimeZone zone) {
    int year = getYear(zone);
    if (year > 0) {
      return calendar.isLeapYear(year);
    }
    else {
      // no leap years for BC
      return false;
    }
  }

  /**
   * Returns the hour of the day in UTC.
   *
   * @return  The hour of the day in 24h format [0, 23].
   */
  public int getUTCHour() {
    return calendar.get(Calendar.HOUR_OF_DAY);
  }
  
  /**
   * Returns the hour for the local time zone.
   *
   * @return  The hour of the day in 24h format [0, 23].
   */
  public int getLocalHour() {
    GregorianCalendar calendar = new GregorianCalendar();
    calendar.setTimeInMillis(this.calendar.getTimeInMillis());
    return calendar.get(Calendar.HOUR_OF_DAY);
  }

  /**
   * Returns the hour for the inputed time zone.
   *
   * @param zone  The time zone.
   * @return  The hour of the day in 24h format [0, 23].
   */
  public int getHour(TimeZone zone) {
    GregorianCalendar calendar = new GregorianCalendar(zone);
    calendar.setTimeInMillis(this.calendar.getTimeInMillis());
    return calendar.get(Calendar.HOUR_OF_DAY);
  }

  /**
   * Returns the minute in UTC.
   *
   * @return  The minute [0, 59].
   */
  public int getUTCMinute() {
    return calendar.get(Calendar.MINUTE);
  }
  
  /**
   * Returns the minute for the local time zone.
   *
   * @return   The minute [0, 59].
   */
  public int getLocalMinute() {
    GregorianCalendar calendar = new GregorianCalendar();
    calendar.setTimeInMillis(this.calendar.getTimeInMillis());
    return calendar.get(Calendar.MINUTE);
  }

  /**
   * Returns the minute for the inputed time zone.
   *
   * @param zone  The time zone.
   * @return   The minute [0, 59].
   */
  public int getMinute(TimeZone zone) {
    GregorianCalendar calendar = new GregorianCalendar(zone);
    calendar.setTimeInMillis(this.calendar.getTimeInMillis());
    return calendar.get(Calendar.MINUTE);
  }

  /**
   * Returns the second in UTC.
   *
   * @return  The second [0, 59].
   */
  public int getUTCSecond() {
    return calendar.get(Calendar.SECOND);
  }
  
  /**
   * Returns the second for the local time zone.
   *
   * @return   The second [0, 59].
   */
  public int getLocalSecond() {
    GregorianCalendar calendar = new GregorianCalendar();
    calendar.setTimeInMillis(this.calendar.getTimeInMillis());
    return calendar.get(Calendar.SECOND);
  }

  /**
   * Returns the second for the inputed time zone.
   *
   * @param zone  The time zone.
   * @return   The second [0, 59].
   */
  public int getSecond(TimeZone zone) {
    GregorianCalendar calendar = new GregorianCalendar(zone);
    calendar.setTimeInMillis(this.calendar.getTimeInMillis());
    return calendar.get(Calendar.SECOND);
  }

  /**
   * Returns the millisecond in UTC.
   *
   * @return  The millisecond [0, 999].
   */
  public int getUTCMillisecond() {
    return calendar.get(Calendar.MILLISECOND);
  }

  /**
   * Returns the millisecond for the local time zone.
   *
   * @return   The millisecond [0, 999].
   */
  public int getLocalMillisecond() {
    GregorianCalendar calendar = new GregorianCalendar();
    calendar.setTimeInMillis(this.calendar.getTimeInMillis());
    return calendar.get(Calendar.MILLISECOND);
  }

  /**
   * Returns the millisecond for the inputed time zone.
   *
   * @param zone  The time zone.
   * @return   The millisecond [0, 999].
   */
  public int getMillisecond(TimeZone zone) {
    GregorianCalendar calendar = new GregorianCalendar(zone);
    calendar.setTimeInMillis(this.calendar.getTimeInMillis());
    return calendar.get(Calendar.MILLISECOND);
  }
  
  /**
   * Adds years to the time.
   *
   * @param years  The years to add. Negative numbers will set the time back.
   */
  public void addYears(int years) {
    calendar.add(Calendar.YEAR, years);
  }

  /**
   * Adds months to the time.
   *
   * @param months  The months to add. Negative numbers will set the time back.
   */
  public void addMonths(int months) {
    calendar.add(Calendar.MONTH, months);
  }

  /**
   * Adds days to the time.
   *
   * @param days  The days to add. Negative numbers will set the time back.
   */
  public void addDays(int days) {
    calendar.add(Calendar.DAY_OF_MONTH, days);
  }

  /**
   * Adds hours to the time.
   *
   * @param hours  The hours to add. Negative numbers will set the time back.
   */
  public void addHours(int hours) {
    calendar.add(Calendar.HOUR, hours);
  }

  /**
   * Adds minutes to the time.
   *
   * @param minutes  The minutes to add. Negative numbers will set the time back.
   */
  public void addMinutes(int minutes) {
    calendar.add(Calendar.MINUTE, minutes);
  }

  /**
   * Adds seconds to the time.
   *
   * @param seconds  The seconds to add. Negative numbers will set the time back.
   */
  public void addSeconds(int seconds) {
    calendar.add(Calendar.SECOND, seconds);
  }
  
  /**
   * Adds milliseconds to the time.
   *
   * @param milliseconds  The milliseconds to add. Negative numbers will set the time back.
   */
  public void addMilliseconds(int milliseconds) {
    calendar.add(Calendar.MILLISECOND, milliseconds);
  }
  
  /**
   * Returns true if this date/time is before the inputed date/time.
   * 
   * @param dateTime  The other date/time.
   * @return  True if this date/time is before the inputed date/time.
   */
  public boolean before(DateTime dateTime) {
    return getTimestamp() < dateTime.getTimestamp();
  }
  
  /**
   * Returns true if this date/time is after the inputed date/time.
   * 
   * @param dateTime  The other date/time.
   * @return  True if this date/time is after the inputed date/time.
   */
  public boolean after(DateTime dateTime) {
    return getTimestamp() > dateTime.getTimestamp();
  }

  /**
   * Returns true, if this object equals the inputed object. 
   * 
   * @param object  The other object to compare this object to.
   * @return  True, if this and the inputed object are the same.
   */
  public boolean equals(Object object) {
    if (object != null) {
      if (object instanceof DateTime) {
        return this.calendar.equals(((DateTime)object).calendar);
      }
      else {
        return false;
      }
    }
    else {
      return false;
    }
  }
  
  /**
   * Returns the hash code for this object.
   * 
   * @return  The hash code for this object.
   */
  public int hashCode() {
    return this.calendar.hashCode();
  }
  
  /**
   * Compares this object to the inputed object and returns the natural order. 
   * 
   * @param object  The other object to compare this object to.
   * @return  The natural order. Returns one of -1, 0, or 1.
   */
  public int compareTo(Object object) {
    return this.calendar.compareTo(((DateTime)object).calendar);
  }
}
