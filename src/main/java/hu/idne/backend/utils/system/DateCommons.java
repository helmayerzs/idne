package hu.idne.backend.utils.system;

import org.apache.commons.lang3.time.DateUtils;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateCommons {
    // http://commons.apache.org/proper/commons-lang/javadocs/api-3.9/org/apache/commons/lang3/time/DateUtils.html
    private static final Date INIFINITE_DATE = new Date(3471289199000L); // 2079-12-31
    public static final LocalDate INIFINITE_LOCAL_DATE = LocalDate.parse("2079-12-31"); // 2079-12-31
    public static final DateTimeFormatter GENERIC_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter GENERIC_DATETIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm.ss");
    public static final DateTimeFormatter TIMESTAMP_DATETIME_FORMAT = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");

    private DateCommons() {

    }

    public static LocalDate getToday() {
        return LocalDate.now();
    }

    /**
     * Adds a number of days to a date returning a new object.
     * The original {@code Date} is unchanged.
     *
     * @param date   the date, not null
     * @param amount the amount to add, may be negative
     * @return the new {@code Date} with the amount added
     * @throws IllegalArgumentException if the date is null
     */
    public static Date addDays(Date date, int amount) {
        return DateUtils.addDays(date, amount);
    }

    /**
     * <p>Checks if two date objects are on the same day ignoring time.</p>
     *
     * <p>28 Mar 2002 13:45 and 28 Mar 2002 06:01 would return true.
     * 28 Mar 2002 13:45 and 12 Mar 2002 13:45 would return false.
     * </p>
     *
     * @param date1 the first date, not altered, not null
     * @param date2 the second date, not altered, not null
     * @return true if they represent the same day
     * @throws IllegalArgumentException if either date is <code>null</code>
     */
    public static boolean isSameDays(Date date1, Date date2) {
        return DateUtils.isSameDay(date1, date2);
    }

    public static Date getInifiniteDate() {
        return INIFINITE_DATE;
    }

    public static LocalDate getInifiniteLocalDate() {
        return INIFINITE_LOCAL_DATE;
    }

    public static Date asDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date asDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date asDate(LocalTime localTime) {
        return Date.from(localTime.atDate(LocalDate.now()).atZone(ZoneId.systemDefault()).toInstant());
    }


    public static LocalDate asLocalDate(Date date) {
        return asZonedDateTime(date).toLocalDate();
    }

    public static LocalDateTime asLocalDateTime(Date date) {
        return asZonedDateTime(date).toLocalDateTime();
    }

    public static LocalTime asLocalTime(Date date) {
        return asZonedDateTime(date).toLocalTime();
    }

    public static ZonedDateTime asZonedDateTime(Date date) {
        return asInstant(date).atZone(ZoneId.systemDefault());
    }

    public static Instant asInstant(Date date) {
        return Instant.ofEpochMilli(date.getTime());
    }

    public static boolean isWeekend(LocalDate date){
        return date.getDayOfWeek().equals(DayOfWeek.SATURDAY) || date.getDayOfWeek().equals(DayOfWeek.SUNDAY);
    }

}
