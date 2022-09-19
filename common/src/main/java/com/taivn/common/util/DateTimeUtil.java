/**
 *
 * Description: The file class                                                 
 *                                                                                
 * Change history:                                                                
 * Date             Defect#             Person             Comments               
 * -------------------------------------------------------------------------------
 * Sep 19, 2022     ********           Taivn            Initialize
 *                                                                                
 */
package com.taivn.common.util;

import com.taivn.common.constant.Constants;
import com.taivn.common.enums.ErrorCodes;
import com.taivn.common.exception.UserDefinedException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/** The Constant log. */
@Slf4j

/**
 * Instantiates a new date time utilities.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DateTimeUtil {
    /**
     * Compare.
     *
     * @param date1 the date 1
     * @param date2 the date 2
     * @return the integer
     */
    public static Integer compare(Date date1, Date date2) {
        LocalDate localDate1 = toLocalDate(date1);
        LocalDate localDate2 = toLocalDate(date2);
        if (localDate1.isBefore(localDate2)) {
            return -1;
        } else if (localDate1.isAfter(localDate2)) {
            return 1;
        }
        return 0;
    }

    public static String getServerDateTime() {
        LocalDateTime dateTime = LocalDateTime.now();
        String date = dateTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss.SSS"));
        return date.replace('-', ' ').replace(':', ' ').replace('.', ' ').replaceAll("\\s+", "");
    };

    public static String getExcuteDateTime(LocalDateTime dateTime) {
        String date = dateTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss.SSS"));
        return date.replace('-', ' ').replace(':', ' ').replace('.', ' ').replaceAll("\\s+", "");
    };

    /**
     * Compare date and time.
     *
     * @param date1 the date 1
     * @param date2 the date 2
     * @return the integer
     */
    public static Integer compareDateAndTime(Date date1, Date date2) {
        if (date1 == null && date2 == null) {
            return 0;
        }

        if (date1 != null) {
            if (date2 == null) {
                return 1;
            }
        } else {
            return -1;
        }

        LocalTime localTime1 = DateTimeUtil.toLocalTime(date1);
        LocalTime localTime2 = DateTimeUtil.toLocalTime(date2);

        return compare(date1, localTime1, date2, localTime2);
    }

    /**
     * Compare time.
     *
     * @param time1 the time 1
     * @param time2 the time 2
     * @return the integer
     */
    public static Integer compareTime(Time time1, Time time2) {
        log.debug("compareTime - time1=[{}], time2=[{}]...", time1, time2);
        Integer result = 0;

        LocalTime localTime1 = toLocalTime(time1);
        LocalTime localTime2 = toLocalTime(time2);

        if (localTime1.isBefore(localTime2)) {
            result = -1;
        } else if (localTime1.isAfter(localTime2)) {
            result = 1;
        }
        log.debug("compareTime - result=[{}]...DONE", result);
        return result;
    }

    /**
     * Compare.
     *
     * @param date1 the date 1
     * @param time1 the time 1
     * @param date2 the date 2
     * @param time2 the time 2
     * @return the integer
     */
    public static Integer compare(Date date1, Time time1, Date date2, Time time2) {
        log.debug("compareDateTime - date1=[{}], time1=[{}], date2=[{}], time2=[{}]...", date1, time1, date2, time2);
        Integer result = compare(date1, date2);
        if (result == 0) {
            LocalTime localTime1 = toLocalTime(time1);
            LocalTime localTime2 = toLocalTime(time2);
            if (localTime1.isBefore(localTime2)) {
                result = -1;
            } else if (localTime1.isAfter(localTime2)) {
                result = 1;
            }
        }
        log.debug("compareDateTime - result=[{}]...DONE", result);
        return result;
    }

    /**
     * Compare.
     *
     * @param date1      the date 1
     * @param localTime1 the local time 1
     * @param date2      the date 2
     * @param localTime2 the local time 2
     * @return the integer
     */
    public static Integer compare(Date date1, LocalTime localTime1, Date date2, LocalTime localTime2) {
        log.debug("compareDateLocalTime - date1=[{}], localTime1=[{}], date2=[{}], localTime2=[{}]...", date1,
                localTime1, date2, localTime2);

        Integer result = compare(date1, date2);

        if (result == 0) {
            if (localTime1.isBefore(localTime2)) {
                result = -1;
            } else if (localTime1.isAfter(localTime2)) {
                result = 1;
            }
        }

        log.debug("compareDateLocalTime - result=[{}]...DONE", result);

        return result;
    }

    /**
     * Checks if is in range.
     *
     * @param checkTime the check time
     * @param fromTime  the from time
     * @param endTime   the end time
     * @return the boolean
     */
    public static Boolean isInRange(LocalTime checkTime, LocalTime fromTime, LocalTime endTime) {

        LocalTime standardCheckTime = LocalTime.of(checkTime.getHour(), checkTime.getMinute());
        LocalTime standardFromTime = LocalTime.of(fromTime.getHour(), fromTime.getMinute());
        LocalTime standardEndTime = LocalTime.of(endTime.getHour(), endTime.getMinute());
        Boolean checkingResult = false;

        if (standardCheckTime.equals(standardFromTime) || standardCheckTime.equals(standardEndTime)
                || (standardCheckTime.isAfter(standardFromTime) && standardCheckTime.isBefore(standardEndTime))) {
            checkingResult = true;
        }

        log.debug("isInRange - checkTime=[{}], fromTime=[{}], endTime=[{}], checkingResult=[{}]...", standardCheckTime,
                standardFromTime, standardEndTime, checkingResult);
        return checkingResult;
    }

    /**
     * To local date.
     *
     * @param date the date
     * @return the local date
     */
    public static LocalDate toLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * To local time.
     *
     * @param date the date
     * @return the local time
     */
    public static LocalTime toLocalTime(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
    }

    /**
     * To local time.
     *
     * @param time the time
     * @return the local time
     */
    public static LocalTime toLocalTime(Time time) {
        return time.toLocalTime();
    }

    /**
     * To time.
     *
     * @param localTime the local time
     * @return the time
     */
    public static Time toTime(LocalTime localTime) {
        return Time.valueOf(localTime);
    }

    /**
     * To string.
     *
     * @param date   the date
     * @param format the format
     * @return the string
     */
    public static String toString(Date date, String format) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat(format);
        return dateTimeFormat.format(date);
    }

    /**
     * To string.
     *
     * @param date the date
     * @return the string
     */
    public static String toString(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
        return dateTimeFormat.format(date);
    }

    /**
     * To string.
     *
     * @param date the date
     * @return the string
     */
    public static String toStringFormat(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat(Constants.DATE_STRING_FORMAT);
        return dateTimeFormat.format(date);
    }

    /**
     * To string format.
     *
     * @param date       the date
     * @param dateFormat the date format
     * @return the string
     */
    public static String toStringFormat(Date date, String dateFormat) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat(dateFormat);
        return dateTimeFormat.format(date);
    }

    /**
     * To standard date str.
     *
     * @param rawDateStr the raw date str
     * @return the string
     */
    public static String toStandardDateStr(String rawDateStr) {
        String adjustedDateStr = rawDateStr;
        if (rawDateStr.contains("%20")) {
            adjustedDateStr = rawDateStr.replace("%20", " ");
        }
        return adjustedDateStr;
    }

    /**
     * To date.
     *
     * @param dateStr the date str
     * @return the date
     */
    public static Date toDateTime(String dateStr) {
        if (StringUtils.isEmpty(dateStr)) {
            return null;
        }
        try {
            SimpleDateFormat dateTimeFormat = new SimpleDateFormat(Constants.DATE_TIME_FORMAT_2);
            return dateTimeFormat.parse(dateStr.replace("T", " "));
        } catch (ParseException e) {
            throw new UserDefinedException(ErrorCodes.INVALID_FORMAT,
                    String.format("Failed to parse date [%s]", dateStr));
        }
    }

    /**
     * To date.
     *
     * @param dateStr the date str
     * @return the date
     */
    public static Date toDate(String dateStr) {
        try {
            SimpleDateFormat dateTimeFormat = new SimpleDateFormat(Constants.DATE_FORMAT);
            return dateTimeFormat.parse(dateStr);
        } catch (ParseException e) {
            throw new UserDefinedException(ErrorCodes.INVALID_FORMAT,
                    String.format("Failed to parse date [%s]", dateStr));
        }
    }

    /**
     * To time.
     *
     * @param dateStr the date str
     * @return the date
     */
    public static Date toTime(String dateStr) {
        try {
            SimpleDateFormat dateTimeFormat = new SimpleDateFormat(Constants.TIME_FORMAT);
            return dateTimeFormat.parse(dateStr);
        } catch (ParseException e) {
            throw new UserDefinedException(ErrorCodes.INVALID_FORMAT,
                    String.format("Failed to parse time [%s]", dateStr));
        }
    }

    /**
     * Minus days.
     *
     * @param date    the date
     * @param numDays the num days
     * @return the date
     */
    public static Date minusDays(Date date, Integer numDays) {
        LocalDate localDate = toLocalDate(date).minusDays(numDays);
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    /**
     * Plus days.
     *
     * @param date    the date
     * @param numDays the num days
     * @return the date
     */
    public static Date plusDays(Date date, Integer numDays) {
        LocalDate localDate = toLocalDate(date).plusDays(numDays);
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    /**
     * Plus minutes.
     *
     * @param date       the date
     * @param numMinutes the num minutes
     * @return the date
     */
    public static Date plusMinutes(Date date, Integer numMinutes) {
        return DateUtils.addMinutes(date, numMinutes);
    }

    /**
     * Plus seconds.
     *
     * @param date       the date
     * @param numSeconds the num seconds
     * @return the date
     */
    public static Date plusSeconds(Date date, Integer numSeconds) {
        return DateUtils.addSeconds(date, numSeconds);
    }

    /**
     * Plus mili seconds.
     *
     * @param date           the date
     * @param numMiliSeconds the num mili seconds
     * @return the date
     */
    public static Date plusMiliSeconds(Date date, Integer numMiliSeconds) {
        return DateUtils.addMilliseconds(date, numMiliSeconds);
    }

    /**
     * Plus minutes.
     *
     * @param time       the time
     * @param numMinutes the num minutes
     * @return the time
     */
    public static Time plusMinutes(Time time, Integer numMinutes) {
        LocalTime localTime = toLocalTime(time).plusMinutes(numMinutes);
        return Time.valueOf(localTime);
    }

    /**
     * Plus seconds.
     *
     * @param time       the time
     * @param numSeconds the num seconds
     * @return the time
     */
    public static Time plusSeconds(Time time, Integer numSeconds) {
        LocalTime localTime = toLocalTime(time).plusSeconds(numSeconds);
        return Time.valueOf(localTime);
    }

    /**
     * To time value.
     *
     * @param time the time
     * @return the time
     */
    public static Time toTimeValue(String time) {
        LocalTime localTime = LocalTime.parse(time);
        return toTime(localTime);
    }

    /**
     * Format DateTime.
     * 
     * @param value
     * @return
     */
    public static LocalDateTime formatDateTime(String value) {
        LocalDateTime dateTime = null;
        Date date = null;
        // convert String to LocalDate
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            date = sdf.parse(value);
        } catch (ParseException e) {
        }

        if (date != null && value.equals(sdf.format(date))) {
            LocalDate localDate = LocalDate.ofInstant(date.toInstant(), ZoneId.systemDefault());
            dateTime = LocalDateTime
                    .parse(localDate.atTime(LocalTime.of(0, 0, 0)).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        } else {
            dateTime = LocalDateTime.parse(value);
        }

        return dateTime;

    }

    /**
     * Format DateTime.
     * 
     * @param value
     * @return
     */
    public static LocalDateTime formatExpiredDate(String value) {
        LocalDateTime dateTime = null;
        Date date = null;
        // convert String to LocalDate
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            date = sdf.parse(value);
        } catch (ParseException e) {
        }

        if (date != null && value.equals(sdf.format(date))) {
            LocalDate localDate = LocalDate.ofInstant(date.toInstant(), ZoneId.systemDefault());
            dateTime = LocalDateTime
                    .parse(localDate.atTime(LocalTime.of(23, 59, 59)).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        } else {
            dateTime = LocalDateTime.parse(value);
        }

        return dateTime;

    }

    /**
     * To local date time.
     *
     * @param time the time
     * @return the local date time
     */
    public static LocalDateTime toLocalDateTime(Timestamp datetime) {
        if (datetime == null) {
            return null;
        }

        return datetime.toLocalDateTime();
    }

    /**
     * To timestamp.
     *
     * @param datetime the datetime
     * @return the timestamp
     */
    public static Timestamp toTimestamp(LocalDateTime datetime) {
        if (datetime == null) {
            return null;
        }

        return Timestamp.valueOf(datetime);
    }
}
