package com.frame.common.utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @desc 日期转换公共类
 */
public class DateUtils {

    /**
     * 获取默认时间格式: yyyy-MM-dd HH:mm:ss
     */
    private static final DateTimeFormatter DEFAULT_DATETIME_FORMATTER = TimeFormat.LONG_DATE_LINE.formatter;

    /**
     * 获取默认时间格式: yyyy-MM-dd
     */
    private static final DateTimeFormatter DEFAULT_DATE_FORMATTER = TimeFormat.SHORT_DATE_LINE.formatter;


    /**
     * String 转时间 yyyy-MM-dd HH:mm:ss
     *
     * @param timeStr
     * @return
     */
    public static Date parseDateTime(String timeStr) {
        LocalDateTime localDateTime = LocalDateTime.parse(timeStr, DEFAULT_DATETIME_FORMATTER);
        Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }

    /**
     * String 转时间 yyyy-MM-dd
     *
     * @param timeStr
     * @return
     */
    public static Date parseDate(String timeStr) {
        LocalDateTime localDateTime = LocalDate.parse(timeStr, DEFAULT_DATE_FORMATTER).atStartOfDay();
        Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }

    /**
     * String 转时间 yyyy-MM-dd HH:mm:ss
     * caution:LocalDateTime.parse方法接收的格式必须包含时间
     * @param timeStr
     * @param format  时间格式
     * @return
     */
    public static Date parseDateTime(String timeStr, TimeFormat format) {
        LocalDateTime localDateTime = LocalDateTime.parse(timeStr, format.formatter);
        Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }

    /**
     * String 转时间
     *
     * @param dateStr
     * @param format  时间格式
     * @return
     */
    public static Date parseDate(String dateStr, TimeFormat format) {
        LocalDate localDate = LocalDate.parse(dateStr, format.formatter);
        Instant instant = localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }

    /**
     * 时间转 String yyyy-MM-dd HH:mm:ss
     *
     * @param date
     * @return
     */
    public static String formatDateTime(Date date) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        return DEFAULT_DATETIME_FORMATTER.format(localDateTime);
    }

    /**
     * 时间转 String yyyy-MM-dd
     *
     * @param date
     * @return
     */
    public static String formatDate(Date date) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        return DEFAULT_DATE_FORMATTER.format(localDateTime);
    }

    public static Date localDate2UtilDate(LocalDate localDate) {
        Instant instant = localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }

    public static LocalDate utilDate2LocalDate(Date date) {
        Instant instant = date.toInstant();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        return localDateTime.toLocalDate();
    }

    /**
     * 时间转 String
     *
     * @param date
     * @param format 时间格式
     * @return
     */
    public static String formatDateTime(Date date, TimeFormat format) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        return format.formatter.format(localDateTime);
    }

    public static Date plusMinute(Date originDate, int minutes) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(originDate.toInstant(), ZoneId.systemDefault());
        return Date.from(localDateTime.plusMinutes(minutes).atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date plusDay(Date originDate, int days) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(originDate.toInstant(), ZoneId.systemDefault());
        return Date.from(localDateTime.plusDays(days).atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date plusMonths(Date originDate, int months) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(originDate.toInstant(), ZoneId.systemDefault());
        return Date.from(localDateTime.plusMonths(months).atZone(ZoneId.systemDefault()).toInstant());
    }

    public static long calcDayDiff(LocalDate fromDate, LocalDate toDate) {
        return toDate.toEpochDay() - fromDate.toEpochDay();
    }

    /**
     * caution:为了适配传入的Date对象为sql.Date实例，才采用Instant.ofEpochMilli(long)方法
     * @param fromDate
     * @param toDate
     * @return
     */
    public static long calcDayDiff(Date fromDate, Date toDate) {
        LocalDate fromLocalDate = LocalDateTime.ofInstant(Instant.ofEpochMilli(fromDate.getTime()), ZoneId.systemDefault()).toLocalDate();
        LocalDate toLocalDate = LocalDateTime.ofInstant(Instant.ofEpochMilli(toDate.getTime()), ZoneId.systemDefault()).toLocalDate();
        return toLocalDate.toEpochDay() - fromLocalDate.toEpochDay();
    }

    /**
     * 获取当前时间 yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static String getCurrentDatetime() {
        return DEFAULT_DATETIME_FORMATTER.format(LocalDateTime.now());
    }

    /**
     * 获取当前时间 yyyy-MM-dd
     *
     * @return
     */
    public static String getCurrentDate() {
        return DEFAULT_DATE_FORMATTER.format(LocalDateTime.now());
    }

    /**
     * 获取当前日期0时刻
     * @return
     */
    public static Date getStartOfToday() {
        return Date.from(LocalDate.now().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 获取当前时间
     *
     * @param format 时间格式
     * @return
     */
    public static String getCurrentDatetime(TimeFormat format) {
        return format.formatter.format(LocalDateTime.now());
    }

    /**
     * 时间格式
     */
    public enum TimeFormat {

        /**
         * 短时间格式
         */
        SHORT_DATE_LINE("yyyy-MM-dd"),
        SHORT_DATE_SLASH("yyyy/MM/dd"),
        SHORT_DATE_NONE("yyyyMMdd"),

        /**
         * 长时间格式
         */
        LONG_DATE_LINE("yyyy-MM-dd HH:mm:ss"),
        LONG_DATE_SLASH("yyyy/MM/dd HH:mm:ss"),
        LONG_DATE_NONE("yyyyMMdd HH:mm:ss"),
        LONG_DATE_NUMBER_ONLY("yyyyMMddHHmmss"),

        /**
         * 长时间格式 带毫秒
         */
        LONG_DATE_WITH_MILSEC_LINE("yyyy-MM-dd HH:mm:ss.SSS"),
        LONG_DATE_WITH_MILSEC_SLASH("yyyy/MM/dd HH:mm:ss.SSS"),
        LONG_DATE_WITH_MILSEC_NONE("yyyyMMdd HH:mm:ss.SSS"),

        /**
         * 时分秒
         */
        TIME("HH:mm:ss"),

        ;

        private transient DateTimeFormatter formatter;

        TimeFormat(String pattern) {
            formatter = DateTimeFormatter.ofPattern(pattern);
        }
    }

}
