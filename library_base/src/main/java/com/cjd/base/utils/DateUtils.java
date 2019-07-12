package com.cjd.base.utils;


import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.util.Log;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;


public class DateUtils {

    public static final String FORMAT_DATETIME = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_DATETIME_yMdHm = "yyyy-MM-dd HH:mm";
    public static final String FORMAT_DATE = "yyyy-MM-dd";
    public static final String FORMAT_MONTH = "yyyy-MM";
    public static final String FORMAT_HOUR = "HH:mm";

    private static long SECOND_MILLIS = 1000L;
    private static long MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static long HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static long DAY_MILLIS = 24 * HOUR_MILLIS;
    public static long WEEK_MILLIS = 7 * DAY_MILLIS;


    private static long ONE_HOUR_MILLIS = 60 * 60 * 1000; // 一小时的毫秒数
    private static long ONE_DAY_MILLIS = 24 * ONE_HOUR_MILLIS; // 一天的毫秒数

    public static String getYyyyMmDd(Date date) {
        SimpleDateFormat format = new SimpleDateFormat(FORMAT_DATE);
        return format.format(date);
    }

    public static String getHHmm(String date) {
        SimpleDateFormat format = new SimpleDateFormat(FORMAT_HOUR);
        Date date1 = getDateFull(date);
        return date1 == null ? "" : format.format(date1);
    }

    public static String getDateFull(Date date) {
        SimpleDateFormat format = new SimpleDateFormat(FORMAT_DATETIME);
        return format.format(date);
    }

    public static Date getDateFull(String date) {
        SimpleDateFormat format = new SimpleDateFormat(FORMAT_DATETIME);
        try {
            return format.parse(date);
        } catch (Exception e) {
            return null;
        }
    }

    public static String getMmDd(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("MM-dd");
        return format.format(date);
    }

    public static String getChineseMmDd(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("MM月dd日");
        return format.format(date);
    }

    public static Date getDate(String date) {
        SimpleDateFormat format = new SimpleDateFormat(FORMAT_DATE);
        try {
            return format.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    public static String getDateInString(String stringDate) {
        String today = getYyyyMmDd(new Date());
        Date one = getDate(stringDate);
        Date two = getDate(today);
        int i = (int) (Math.abs(one.getTime() - two.getTime()) / ONE_DAY_MILLIS);
        if (i == 0) {
            return "今天";
        } else if (i == 1) {
            return "明天";
        } else if (i == 2) {
            return "后天";
        } else {
            return dayForWeek(stringDate);
        }
    }

    public static int getBetweenDayNumber(Date dateA, Date dateB) {
        long dayNumber;
        dayNumber = (dateB.getTime() - dateA.getTime()) / ONE_DAY_MILLIS;
        return (int) dayNumber > 0 ? (int) dayNumber : 1;
    }

    /**
     * 判断当前日期是星期几
     *
     * @param pTime 修要判断的时间
     * @return dayForWeek 判断结果
     * @Exception 发生异常
     */
    public static String dayForWeek(String pTime) {
        Calendar c = Calendar.getInstance();
        c.setTime(getDate(pTime));
        String dayStr = "";
        switch (c.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.MONDAY:
                dayStr = "周一";
                break;
            case Calendar.TUESDAY:
                dayStr = "周二";
                break;
            case Calendar.WEDNESDAY:
                dayStr = "周三";
                break;
            case Calendar.THURSDAY:
                dayStr = "周四";
                break;
            case Calendar.FRIDAY:
                dayStr = "周五";
                break;
            case Calendar.SATURDAY:
                dayStr = "周六";
                break;
            case Calendar.SUNDAY:
                dayStr = "周日";
                break;
            default:
                dayStr = "";
                break;
        }

        return dayStr;
    }

    /**
     * 判断是否是 周末
     *
     * @param dateA 开始时间必须小于 dateB 否则返回false
     * @param dateB
     * @return
     */
    public static boolean getWorkday(Date dateA, Date dateB) {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(dateA);
        c2.setTime(dateB);

        if (dateA.getTime() < dateB.getTime()
                && ((c1.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY && (c2.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || c2.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY))
                || ((c1.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) && c2.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)))
            return true;
        return false;
    }


    public static DateInterval interval(Date beginDate, Date endDate) {
        try {
            SimpleDateFormat df = new SimpleDateFormat(FORMAT_DATETIME);
            Date now = df.parse(getDateFull(endDate));
            Date date = df.parse(getDateFull(beginDate));
            DateInterval detail = new DateInterval();
            long l = now.getTime() - date.getTime();
            long day = l / (24 * 60 * 60 * 1000);
            long hour = (l / (60 * 60 * 1000) - day * 24);
            long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
            long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
            detail.day = day;
            detail.hour = hour;
            detail.minute = min;
            detail.second = s;
            return detail;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static class DateInterval {
        public long day;
        public long hour;
        public long minute;
        public long second;
    }

    /**
     * 用户自己定义日期和格式，进行格式化
     *
     * @param date    用户指定的日期
     * @param pattern 用户指定的时间格式
     * @return 返回指定的格式化后的时间字符串
     */
    public static String formatDate(Date date, String pattern) {
        if (date == null || TextUtils.isEmpty(pattern)) {
            return null;
        }
        SimpleDateFormat datePattern = new SimpleDateFormat(pattern);

        return datePattern.format(date);
    }

    /**
     * 对指定的日期，使用 yyyy-MM 形式进行格式化
     *
     * @param date 指定的日期
     * @return 返回 yyyy-MM 格式的字符串
     */
    public static String getMonthStr(Date date) {
        if (date == null) {
            return null;
        }
        return new SimpleDateFormat(FORMAT_MONTH).format(date);
    }

    /**
     * 对指定的日期，使用 yyyy-MM-dd 形式进行格式化
     *
     * @param date 指定的日期
     * @return 返回 yyyy-MM-dd 格式的字符串
     */
    public static String getDateStr(Date date) {
        if (date == null) {
            return null;
        }
        return new SimpleDateFormat(FORMAT_DATE).format(date);
    }

    /**
     * 对指定的毫秒数，使用 yyyy-MM-dd 形式进行格式化
     *
     * @param timeMillis 指定的毫秒数
     * @return 返回 yyyy-MM-dd 格式的字符串
     */
    public static String getDateStr(long timeMillis) {
        return getDateStr(new Date(timeMillis));
    }

    /**
     * 对指定的日期，使用 yyyy-MM-dd HH:mm:ss 形式进行格式化
     *
     * @param date 指定的日期
     * @return 返回 yyyy-MM-dd HH:mm:ss 格式的字符串
     */
    public static String getDateTimeStr(Date date) {
        if (date == null) {
            return null;
        }
        return new SimpleDateFormat(FORMAT_DATETIME).format(date);
    }

    /**
     * 对指定的日期，使用 yyyy-MM-dd HH:mm 形式进行格式化
     *
     * @param date 指定的日期
     * @return 返回 yyyy-MM-dd HH:mm 格式的字符串
     */
    public static String getDateTimeyMdHmStr(Date date) {
        if (date == null) {
            return null;
        }
        return new SimpleDateFormat(FORMAT_DATETIME_yMdHm).format(date);
    }

    /**
     * 对指定的毫秒数，使用 yyyy-MM-dd HH:mm:ss 形式进行格式化
     *
     * @param timeMillis 指定的毫秒数
     * @return 返回 yyyy-MM-dd HH:mm:ss 格式的字符串
     */
    public static String getDateTimeStr(long timeMillis) {
        return getDateTimeStr(new Date(timeMillis));
    }

    /**
     * @return 返回当前时间的 yyyy-MM-dd 格式的字符串
     */
    public static String getCurrentDateStr() {
        return getDateStr(new Date());
    }

    /**
     * @return 返回当前时间的 yyyy-MM-dd 格式的字符串
     */
    public static Date getCurrentDate() {
        return convert(getCurrentTimestamp());
    }

    /**
     * @return 返回当前时间的 yyyy-MM-dd HH:mm:ss 格式的字符串
     */
    public static String getCurrentDateTimeStr() {
        return getDateTimeStr(new Date());
    }

    /**
     * @return 返回当前时间的 yyyy-MM 格式的字符串
     */
    public static String getCurrentMonthStr() {
        return getMonthStr(new Date());
    }

    /**
     * 在指定的日期的基础上添加指定单位的数值，然后格式化成 yyyy-MM-dd HH:mm:ss 的字符串后返回
     *
     * @param date     指定的日期
     * @param diffTime 指定的时间数值（如果需要减，则使用负数即可）
     * @param unit     指定的时间单位
     * @return 返回 yyyy-MM-dd HH:mm:ss 格式的字符串
     */
    public static String timeAddToStr(Date date, long diffTime, TimeUnit unit) {
        if (date == null) {
            return null;
        }
        long resultTime = date.getTime() + unit.toMillis(diffTime);

        return getDateTimeStr(resultTime);
    }


    /**
     * 在指定的日期的基础上添加指定单位的数值，并返回
     *
     * @param date     指定的日期
     * @param diffTime 指定的时间数值，可以为负数
     * @param unit     指定的时间单位
     * @return 返回计算之后的日期
     */
    public static Date timeAdd(Date date, long diffTime, TimeUnit unit) {
        if (date == null) {
            return null;
        }
        long resultTime = date.getTime() + unit.toMillis(diffTime);

        return new Date(resultTime);
    }

    /**
     * 在指定的日期上添加指定days天数，然后返回
     *
     * @param date 指定的日期
     * @param days 需要添加的天数，可以为负数
     * @return 在指定的日期上添加指定days天数，然后返回
     */
    @SuppressLint("NewApi")
    public static Date timeAddByDays(Date date, int days) {
        return timeAdd(date, days, TimeUnit.DAYS);
    }

    /**
     * @param date   日期
     * @param months 需要添加的月份数，可以为负数
     * @return 在指定的日期上添加指定months个月，然后返回
     */
    public static Date timeAddByMonth(Date date, int months) {
        if (date == null) {
            return null;
        }
        if (months == 0) {
            return date;
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, months);

        return cal.getTime();
    }

    /**
     * 返回指定日期所在月份的第一天的日期
     *
     * @param date
     * @return 返回指定日期所在月份的第一天的日期
     */
    public static String getFirstDayOfMonth(Date date) {
        if (date == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, 1);

        return getDateStr(cal.getTime());
    }

    /**
     * @return 返回昨天的日期字符串，格式为 yyyy-MM-dd
     */
    @SuppressLint("NewApi")
    public static String getYestoday() {
        return timeAddToStr(new Date(), -1, TimeUnit.DAYS).split(" ")[0];
    }


    /**
     * 按照 yyyy-MM-dd 的格式解析给定的日期字符串
     *
     * @param dateStr 给定的日期字符串
     * @return 返回解析后的日期，如果解析失败，则返回null
     */
    public static Date parseDate(String dateStr) {
        try {
            return new SimpleDateFormat(FORMAT_DATE).parse(dateStr);
        } catch (ParseException e) {
            Log.e("dateUtil", "parseDate exception : ", e);
        }

        return null;
    }

    /**
     * 按照 yyyy-MM-dd HH:mm:ss 的格式解析给定的日期字符串
     *
     * @param dateTimeStr 给定的日期字符串
     * @return 返回解析后的日期，如果解析失败，则返回null
     */
    public static Date parseDateTime(String dateTimeStr) {
        try {
            return new SimpleDateFormat(FORMAT_DATETIME).parse(dateTimeStr);
        } catch (ParseException e) {
            Log.e("dateUtil", "parseDateTime exception : ", e);
        }

        return null;
    }

    /**
     * 按照指定的format格式解析给定的日期字符串
     *
     * @param dateStr 给定的日期字符串
     * @param format  指定的日期格式
     * @return 将日期字符串解析成Date对象
     */
    public static Date parseToDate(String dateStr, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            Log.e("dateUtil", "parseToDate exception : ", e);
        }
        return date;
    }

    /**
     * 将给定的日期字符串按照 yyyy-MM-dd HH:mm:ss 格式解析成Timestamp对象
     *
     * @param dateTimeStr 给定的日期字符串
     * @return 返回解析成功后的Timestamp对象
     */
    public static Timestamp parseTimestamp(String dateTimeStr) {
        Date date = parseDateTime(dateTimeStr);

        return convert(date);
    }

    /**
     * @return 返回当前时间的Timestamp对象
     */
    public static Timestamp getCurrentTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    /**
     * @param date 指定的Date对象
     * @return 将指定的Date对象转换成Timestamp对象
     */
    public static Timestamp convert(Date date) {
        if (date == null) {
            return null;
        }
        return new Timestamp(date.getTime());
    }

    /**
     * @param timestamp 指定的Timestamp对象
     * @return 将指定的Timestamp对象转换成Date对象
     */
    public static Date convert(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }
        return new Date(timestamp.getTime());
    }

    /**
     * 对给定的两个日期进行比较，如果date1 比 date2 大，则返回1；如果相等，则返回0；否则返回-1
     *
     * @param date1
     * @param date2
     * @return 对给定的两个日期进行比较，如果date1 比 date2 大，则返回1；如果相等，则返回0；否则返回-1
     */
    public static int compare(Date date1, Date date2) {
        if (date1 == null) {
            return -1;
        }
        if (date2 == null) {
            return 1;
        }
        long timeDiff = date1.getTime() - date2.getTime();

        return timeDiff == 0 ? 0 : (int) (timeDiff / Math.abs(timeDiff));
    }


    // （版本2）得出的结果是1天，我们一般用的方法都会推荐版本2

    public static int daysOfTwo(Date fDate, Date oDate) {
        Calendar aCalendar = Calendar.getInstance();
        aCalendar.setTime(fDate);
        int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);
        aCalendar.setTime(oDate);
        int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);
        return day2 - day1;

    }

    /**
     * 获取当前系统之前N天的时间 times几天前
     *
     * @return 返回字符串型的时间 "yyyy-MM-dd HH:mm:ss"
     */
    public static String getYesterdayTime(int times) {
        Calendar c = Calendar.getInstance();// 把当前时间中的data自减1
        c.add(Calendar.DAY_OF_MONTH, -times);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String mDateTime = formatter.format(c.getTime());
        return mDateTime;
    }
}
