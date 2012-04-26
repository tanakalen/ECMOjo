package king.lib.util;

import java.io.Serializable;
import java.text.SimpleDateFormat;
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

  /** The timestamp. */
  private long timestamp;
  
  /** The underlying calendar object is only created when needed. */
  private GregorianCalendar calendar;
  
  
  /**
   * Constructor for date/time using the current time in the current time zone. The current time is
   * converted internally to UTC.
   */
  public DateTime() {
    this(System.currentTimeMillis());
  }
  
  /**
   * Constructor for date/time with a timestamp in UTC milliseconds from the epoch.
   * 
   * @param timestamp  The timestamp in UTC milliseconds from the epoch.
   */
  public DateTime(long timestamp) {
    this.timestamp = timestamp;
  }
  
  /**
   * Returns the calendar.
   * 
   * @return  The calendar object. 
   */
  private synchronized GregorianCalendar calendar() {
    if (calendar == null) {
      calendar = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
      calendar.setTimeInMillis(timestamp);
    }
    return calendar;
  }
  
  /**
   * Creator for date/time using the inputed time in UTC. 
   * Hour, minute, second and millisecond will be set to 0.
   * Throws an IllegalArgumentException if the input is invalid.
   * <p>
   * IMPORTANT: The inputed time has to be in UTC.
   *
   * @param year  The year [-1000000, 1000000] including year 0!
   * @param month  The month [1, 12]. 1 = January, 2 = February etc. 
   * @param day  The day of the month [1, 31].
   * @return  The date time object created.
   */
  public static DateTime createUTC(int year, int month, int day) {
    return createUTC(year, month, day, 0, 0, 0, 0);
  }
  
  /**
   * Creator for date/time using the inputed time in the local time zone. 
   * Hour, minute, second and millisecond will be set to 0.
   * Throws an IllegalArgumentException if the input is invalid.
   * <p>
   * IMPORTANT: The inputed time has to be in the local time zone.
   *
   * @param year  The year [-1000000, 1000000] including year 0!
   * @param month  The month [1, 12]. 1 = January, 2 = February etc. 
   * @param day  The day of the month [1, 31].
   * @return  The date time object created.
   */
  public static DateTime createLocal(int year, int month, int day) {
    return createLocal(year, month, day, 0, 0, 0, 0);
  }

  /**
   * Creator for date/time using the inputed time in the inputed time zone.
   * Hour, minute, second and millisecond will be set to 0.
   * Throws an IllegalArgumentException if the input is invalid.
   * <p>
   * IMPORTANT: The inputed time has to be in the inputed time zone.
   *
   * @param zone  The time zone.
   * @param year  The year [-1000000, 1000000] including year 0!
   * @param month  The month [1, 12]. 1 = January, 2 = February etc. 
   * @param day  The day of the month [1, 31].
   * @return  The date time object created.
   */
  public static DateTime create(TimeZone zone, int year, int month, int day) {
    return create(zone, year, month, day, 0, 0, 0, 0);
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
   * @return  The date time object created.
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
   * @return  The date time object created.
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
   * @return  The date time object created.
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
    this.timestamp = timestamp;
    if (calendar != null) {
      calendar.setTimeInMillis(timestamp);
    }
  }
  
  /**
   * Returns the timestamp.
   *
   * @return  The timestamp in UTC milliseconds from the epoch.
   */
  public long getTimestamp() {
    return timestamp;
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
    GregorianCalendar calendar = calendar();
    if (year > 0) {
      calendar.set(Calendar.ERA, GregorianCalendar.AD);
    }
    else {
      calendar.set(Calendar.ERA, GregorianCalendar.BC);
      year = -(year - 1);
    }
    calendar.set(year, month - 1, day, hour, minute, second);
    calendar.set(Calendar.MILLISECOND, millisecond);
    timestamp = calendar.getTimeInMillis();
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
    calendar().setTimeInMillis(calendar.getTimeInMillis());
    timestamp = calendar.getTimeInMillis();
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
    calendar().setTimeInMillis(calendar.getTimeInMillis());
    timestamp = calendar.getTimeInMillis();
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
      if (calendar().isLeapYear(year)) {
        days = new int[] {31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
      }
      else {
        days = new int[] {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
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
    GregorianCalendar calendar = calendar();
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
    calendar.setTimeInMillis(calendar().getTimeInMillis());
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
    calendar.setTimeInMillis(calendar().getTimeInMillis());
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
    return calendar().get(Calendar.MONTH) + 1;
  }
  
  /**
   * Returns the month for the local time zone.
   *
   * @return  The month [1, 12]. 1 = January, 2 = February etc. 
   */
  public int getLocalMonth() {
    GregorianCalendar calendar = new GregorianCalendar();
    calendar.setTimeInMillis(calendar().getTimeInMillis());
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
    calendar.setTimeInMillis(calendar().getTimeInMillis());
    return calendar.get(Calendar.MONTH) + 1;
  }

  /**
   * Returns the day of the month in UTC.
   *
   * @return  The day of the month [1, 31].
   */
  public int getUTCDay() {
    return calendar().get(Calendar.DAY_OF_MONTH);
  }
  
  /**
   * Returns the day of the month for the local time zone.
   *
   * @return  The day of the month [1, 31].
   */
  public int getLocalDay() {
    GregorianCalendar calendar = new GregorianCalendar();
    calendar.setTimeInMillis(calendar().getTimeInMillis());
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
    calendar.setTimeInMillis(calendar().getTimeInMillis());
    return calendar.get(Calendar.DAY_OF_MONTH);
  }

  /**
   * Returns the weekday in UTC.
   *
   * @return  The weekday [1, 7]. 1 = Monday, 2 = Tuesday, 3 = Wednesday etc.
   */
  public int getUTCWeekday() {
    int day = calendar().get(Calendar.DAY_OF_WEEK);
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
    calendar.setTimeInMillis(calendar().getTimeInMillis());
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
    calendar.setTimeInMillis(calendar().getTimeInMillis());
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
    return calendar().get(Calendar.DAY_OF_YEAR);
  }
  
  /**
   * Returns the day of the year for the local time zone.
   *
   * @return  The day of the year [1, 365]. First day starting with 1.
   */
  public int getLocalDayOfYear() {
    GregorianCalendar calendar = new GregorianCalendar();
    calendar.setTimeInMillis(calendar().getTimeInMillis());
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
    calendar.setTimeInMillis(calendar().getTimeInMillis());
    return calendar.get(Calendar.DAY_OF_YEAR);
  }
  
  /**
   * Returns true for leap year in UTC.
   * 
   * @return  True for leap year. A normal year has 365 days. A leap year has 366 days. 
   */
  public boolean isUTCLeapYear() {
    int year = getUTCYear();
    return calendar().isLeapYear(year);
  }
  
  /**
   * Returns true for leap year for the local time zone.
   *
   * @return  True for leap year. A normal year has 365 days. A leap year has 366 days. 
   */
  public boolean isLocalLeapYear() {
    int year = getLocalYear();
    return calendar().isLeapYear(year);
  }

  /**
   * Returns true for leap year for the inputed time zone.
   *
   * @param zone  The time zone.
   * @return  True for leap year. A normal year has 365 days. A leap year has 366 days. 
   */
  public boolean isLeapYear(TimeZone zone) {
    int year = getYear(zone);
    return calendar().isLeapYear(year);
  }

  /**
   * Returns the hour of the day in UTC.
   *
   * @return  The hour of the day in 24h format [0, 23].
   */
  public int getUTCHour() {
    return calendar().get(Calendar.HOUR_OF_DAY);
  }
  
  /**
   * Returns the hour for the local time zone.
   *
   * @return  The hour of the day in 24h format [0, 23].
   */
  public int getLocalHour() {
    GregorianCalendar calendar = new GregorianCalendar();
    calendar.setTimeInMillis(calendar().getTimeInMillis());
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
    calendar.setTimeInMillis(calendar().getTimeInMillis());
    return calendar.get(Calendar.HOUR_OF_DAY);
  }

  /**
   * Returns the minute in UTC.
   *
   * @return  The minute [0, 59].
   */
  public int getUTCMinute() {
    return calendar().get(Calendar.MINUTE);
  }
  
  /**
   * Returns the minute for the local time zone.
   *
   * @return   The minute [0, 59].
   */
  public int getLocalMinute() {
    GregorianCalendar calendar = new GregorianCalendar();
    calendar.setTimeInMillis(calendar().getTimeInMillis());
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
    calendar.setTimeInMillis(calendar().getTimeInMillis());
    return calendar.get(Calendar.MINUTE);
  }

  /**
   * Returns the second in UTC.
   *
   * @return  The second [0, 59].
   */
  public int getUTCSecond() {
    return calendar().get(Calendar.SECOND);
  }
  
  /**
   * Returns the second for the local time zone.
   *
   * @return   The second [0, 59].
   */
  public int getLocalSecond() {
    GregorianCalendar calendar = new GregorianCalendar();
    calendar.setTimeInMillis(calendar().getTimeInMillis());
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
    calendar.setTimeInMillis(calendar().getTimeInMillis());
    return calendar.get(Calendar.SECOND);
  }

  /**
   * Returns the millisecond in UTC.
   *
   * @return  The millisecond [0, 999].
   */
  public int getUTCMillisecond() {
    return calendar().get(Calendar.MILLISECOND);
  }

  /**
   * Returns the millisecond for the local time zone.
   *
   * @return   The millisecond [0, 999].
   */
  public int getLocalMillisecond() {
    GregorianCalendar calendar = new GregorianCalendar();
    calendar.setTimeInMillis(calendar().getTimeInMillis());
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
    calendar.setTimeInMillis(calendar().getTimeInMillis());
    return calendar.get(Calendar.MILLISECOND);
  }
  
  /**
   * Adds years to the time.
   *
   * @param years  The years to add. Negative numbers will set the time back.
   */
  public void addYears(int years) {
    GregorianCalendar calendar = calendar();
    calendar.add(Calendar.YEAR, years);
    timestamp = calendar.getTimeInMillis();
  }

  /**
   * Adds months to the time.
   *
   * @param months  The months to add. Negative numbers will set the time back.
   */
  public void addMonths(int months) {
    GregorianCalendar calendar = calendar();
    calendar.add(Calendar.MONTH, months);
    timestamp = calendar.getTimeInMillis();
  }

  /**
   * Adds days to the time.
   *
   * @param days  The days to add. Negative numbers will set the time back.
   */
  public void addDays(int days) {
    GregorianCalendar calendar = calendar();
    calendar.add(Calendar.DAY_OF_MONTH, days);
    timestamp = calendar.getTimeInMillis();
  }

  /**
   * Adds hours to the time.
   *
   * @param hours  The hours to add. Negative numbers will set the time back.
   */
  public void addHours(int hours) {
    GregorianCalendar calendar = calendar();
    calendar().add(Calendar.HOUR, hours);
    timestamp = calendar.getTimeInMillis();
  }

  /**
   * Adds minutes to the time.
   *
   * @param minutes  The minutes to add. Negative numbers will set the time back.
   */
  public void addMinutes(int minutes) {
    GregorianCalendar calendar = calendar();
    calendar.add(Calendar.MINUTE, minutes);
    timestamp = calendar.getTimeInMillis();
  }

  /**
   * Adds seconds to the time.
   *
   * @param seconds  The seconds to add. Negative numbers will set the time back.
   */
  public void addSeconds(int seconds) {
    GregorianCalendar calendar = calendar();
    calendar.add(Calendar.SECOND, seconds);
    timestamp = calendar.getTimeInMillis();
  }
  
  /**
   * Adds milliseconds to the time.
   *
   * @param milliseconds  The milliseconds to add. Negative numbers will set the time back.
   */
  public void addMilliseconds(int milliseconds) {
    GregorianCalendar calendar = calendar();
    calendar.add(Calendar.MILLISECOND, milliseconds);
    timestamp = calendar.getTimeInMillis();
  }
  
  /**
   * Returns true if this date/time is before the inputed date/time.
   * 
   * @param dateTime  The other date/time.
   * @return  True if this date/time is before the inputed date/time.
   */
  public boolean before(DateTime dateTime) {
    return timestamp < dateTime.timestamp;
  }
  
  /**
   * Returns true if this date/time is after the inputed date/time.
   * 
   * @param dateTime  The other date/time.
   * @return  True if this date/time is after the inputed date/time.
   */
  public boolean after(DateTime dateTime) {
    return timestamp > dateTime.timestamp;
  }

  /**
   * Outputs a formated date in UTC.
   * <p>
   * The following examples show how date and time patterns are interpreted in
   * the U.S. locale. The given date and time are 2001-07-04 12:08:56 local time
   * in the U.S. Pacific Time time zone.
   * <blockquote>
   * <table border=0 cellspacing=3 cellpadding=0 summary="Examples of date/time patterns interpreted in the US locale">
   *     <tr bgcolor="#ccccff">
   *         <th align=left>Date and Time Pattern
   *         <th align=left>Result
   *     <tr>
   *         <td><code>"yyyy.MM.dd G 'at' HH:mm:ss z"</code>
   *         <td><code>2001.07.04 AD at 12:08:56 PDT</code>
   *     <tr bgcolor="#eeeeff">
   *         <td><code>"EEE, MMM d, ''yy"</code>
   *         <td><code>Wed, Jul 4, '01</code>
   *     <tr>
   *         <td><code>"h:mm a"</code>
   *         <td><code>12:08 PM</code>
   *     <tr bgcolor="#eeeeff">
   *         <td><code>"hh 'o''clock' a, zzzz"</code>
   *         <td><code>12 o'clock PM, Pacific Daylight Time</code>
   *     <tr>
   *         <td><code>"K:mm a, z"</code>
   *         <td><code>0:08 PM, PDT</code>
   *     <tr bgcolor="#eeeeff">
   *         <td><code>"yyyyy.MMMMM.dd GGG hh:mm aaa"</code>
   *         <td><code>02001.July.04 AD 12:08 PM</code>
   *     <tr>
   *         <td><code>"EEE, d MMM yyyy HH:mm:ss Z"</code>
   *         <td><code>Wed, 4 Jul 2001 12:08:56 -0700</code>
   *     <tr bgcolor="#eeeeff">
   *         <td><code>"yyMMddHHmmssZ"</code>
   *         <td><code>010704120856-0700</code>
   *     <tr>
   *         <td><code>"yyyy-MM-dd'T'HH:mm:ss.SSSZ"</code>
   *         <td><code>2001-07-04T12:08:56.235-0700</code>
   * </table>
   * </blockquote>
   * <p>
   * See SimpleDateFormat for more information about patterns.
   * 
   * @param pattern  The pattern for formatting.
   * @return  The formated date and time.
   */
  public String formatUTC(String pattern) {
    GregorianCalendar calendar = calendar();
    SimpleDateFormat formatter = new SimpleDateFormat(pattern);
    formatter.setTimeZone(calendar.getTimeZone());
    return formatter.format(calendar.getTime());
  }
  
  /**
   * Outputs a formated date for the local time zone.
   * <p>
   * The following examples show how date and time patterns are interpreted in
   * the U.S. locale. The given date and time are 2001-07-04 12:08:56 local time
   * in the U.S. Pacific Time time zone.
   * <blockquote>
   * <table border=0 cellspacing=3 cellpadding=0 summary="Examples of date/time patterns interpreted in the US locale">
   *     <tr bgcolor="#ccccff">
   *         <th align=left>Date and Time Pattern
   *         <th align=left>Result
   *     <tr>
   *         <td><code>"yyyy.MM.dd G 'at' HH:mm:ss z"</code>
   *         <td><code>2001.07.04 AD at 12:08:56 PDT</code>
   *     <tr bgcolor="#eeeeff">
   *         <td><code>"EEE, MMM d, ''yy"</code>
   *         <td><code>Wed, Jul 4, '01</code>
   *     <tr>
   *         <td><code>"h:mm a"</code>
   *         <td><code>12:08 PM</code>
   *     <tr bgcolor="#eeeeff">
   *         <td><code>"hh 'o''clock' a, zzzz"</code>
   *         <td><code>12 o'clock PM, Pacific Daylight Time</code>
   *     <tr>
   *         <td><code>"K:mm a, z"</code>
   *         <td><code>0:08 PM, PDT</code>
   *     <tr bgcolor="#eeeeff">
   *         <td><code>"yyyyy.MMMMM.dd GGG hh:mm aaa"</code>
   *         <td><code>02001.July.04 AD 12:08 PM</code>
   *     <tr>
   *         <td><code>"EEE, d MMM yyyy HH:mm:ss Z"</code>
   *         <td><code>Wed, 4 Jul 2001 12:08:56 -0700</code>
   *     <tr bgcolor="#eeeeff">
   *         <td><code>"yyMMddHHmmssZ"</code>
   *         <td><code>010704120856-0700</code>
   *     <tr>
   *         <td><code>"yyyy-MM-dd'T'HH:mm:ss.SSSZ"</code>
   *         <td><code>2001-07-04T12:08:56.235-0700</code>
   * </table>
   * </blockquote>
   * <p>
   * See SimpleDateFormat for more information about patterns.
   * 
   * @param pattern  The pattern for formatting.
   * @return  The formated date and time.
   */
  public String formatLocal(String pattern) {
    GregorianCalendar calendar = calendar();
    SimpleDateFormat formatter = new SimpleDateFormat(pattern);
    formatter.setTimeZone(new GregorianCalendar().getTimeZone());
    return formatter.format(calendar.getTime());
  }

  /**
   * Outputs a formated date for the inputed time zone.
   * <p>
   * The following examples show how date and time patterns are interpreted in
   * the U.S. locale. The given date and time are 2001-07-04 12:08:56 local time
   * in the U.S. Pacific Time time zone.
   * <blockquote>
   * <table border=0 cellspacing=3 cellpadding=0 summary="Examples of date/time patterns interpreted in the US locale">
   *     <tr bgcolor="#ccccff">
   *         <th align=left>Date and Time Pattern
   *         <th align=left>Result
   *     <tr>
   *         <td><code>"yyyy.MM.dd G 'at' HH:mm:ss z"</code>
   *         <td><code>2001.07.04 AD at 12:08:56 PDT</code>
   *     <tr bgcolor="#eeeeff">
   *         <td><code>"EEE, MMM d, ''yy"</code>
   *         <td><code>Wed, Jul 4, '01</code>
   *     <tr>
   *         <td><code>"h:mm a"</code>
   *         <td><code>12:08 PM</code>
   *     <tr bgcolor="#eeeeff">
   *         <td><code>"hh 'o''clock' a, zzzz"</code>
   *         <td><code>12 o'clock PM, Pacific Daylight Time</code>
   *     <tr>
   *         <td><code>"K:mm a, z"</code>
   *         <td><code>0:08 PM, PDT</code>
   *     <tr bgcolor="#eeeeff">
   *         <td><code>"yyyyy.MMMMM.dd GGG hh:mm aaa"</code>
   *         <td><code>02001.July.04 AD 12:08 PM</code>
   *     <tr>
   *         <td><code>"EEE, d MMM yyyy HH:mm:ss Z"</code>
   *         <td><code>Wed, 4 Jul 2001 12:08:56 -0700</code>
   *     <tr bgcolor="#eeeeff">
   *         <td><code>"yyMMddHHmmssZ"</code>
   *         <td><code>010704120856-0700</code>
   *     <tr>
   *         <td><code>"yyyy-MM-dd'T'HH:mm:ss.SSSZ"</code>
   *         <td><code>2001-07-04T12:08:56.235-0700</code>
   * </table>
   * </blockquote>
   * <p>
   * See SimpleDateFormat for more information about patterns.
   * 
   * @param zone  The time zone.
   * @param pattern  The pattern for formatting.
   * @return  The formated date and time.
   */
  public String format(TimeZone zone, String pattern) {
    GregorianCalendar calendar = calendar();
    SimpleDateFormat formatter = new SimpleDateFormat(pattern);
    formatter.setTimeZone(zone);
    return formatter.format(calendar.getTime());
  }

  /**
   * Returns the date time as text in UTC. See formatUTC(...) for details. 
   * Pattern used: "yyyy.MM.dd G 'at' HH:mm:ss z".
   * 
   * @return  The formated date/time.
   */
  public String toString() {
    return formatUTC("yyyy.MM.dd G 'at' HH:mm:ss z");
  }
  
  /**
   * Returns an exact copy of this object.
   * 
   * @return  An exact copy of this object.
   */
  public DateTime copy() {
    return new DateTime(timestamp);
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
        return timestamp == ((DateTime)object).timestamp;
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
    return (int)(timestamp ^ (timestamp >>> 32));
  }
  
  /**
   * Compares this object to the inputed object and returns the natural order. 
   * 
   * @param object  The other object to compare this object to.
   * @return  The natural order. Returns one of -1, 0, or 1.
   */
  public int compareTo(Object object) {
    return Long.valueOf(timestamp).compareTo(Long.valueOf(((DateTime)object).timestamp));
  }
}
