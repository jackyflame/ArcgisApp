package com.haozi.baselibrary.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public abstract class DateUtil {

    private static SimpleDateFormat simpleDateFormat;

    public static SimpleDateFormat getSimpleDateFormat(String format) {
        if (simpleDateFormat == null) simpleDateFormat = new SimpleDateFormat();
        simpleDateFormat.applyPattern(format);
        return simpleDateFormat;
    }

    public static String formatDate(long time) {
        Date date = new Date(time);
        SimpleDateFormat formatter = getSimpleDateFormat("yyyy-MM-dd-EEEE");
        return formatter.format(date);
    }

    /**
     * 格式化日期：yyyy-MM-dd
     * @param time
     * @return String
     * */
    public static String formatDay(long time) {
        Date date = new Date(time);
        SimpleDateFormat formatter = getSimpleDateFormat("yyyy-MM-dd");
        return formatter.format(date);
    }

    public static String formatDayOnly(long time) {
        Date date = new Date(time);
        SimpleDateFormat formatter = getSimpleDateFormat("dd");
        return formatter.format(date);
    }

    public static String formatYearOnly(long time) {
        Date date = new Date(time);
        SimpleDateFormat formatter = getSimpleDateFormat("yyyy");
        return formatter.format(date);
    }

    public static String formatMonthOnly(long time) {
        Date date = new Date(time);
        SimpleDateFormat formatter = getSimpleDateFormat("MM月");
        return formatter.format(date);
    }

    public static String formatNextMonthOnly(long time) {
        Date date = new Date(time);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, 1);
        SimpleDateFormat formatter = getSimpleDateFormat("MM月");
        return formatter.format(cal.getTime());
    }

    public static String formatRecordDate(long time) {
        Date date = new Date(time);
        SimpleDateFormat formatter = getSimpleDateFormat("MM/dd HH:mm");
        return formatter.format(date);
    }

    public static String formatDateStamp(long time) {
        Date date = new Date(time);
        SimpleDateFormat formatter = getSimpleDateFormat("yyyyMMddHHmm");
        return formatter.format(date);
    }

    public static String formatDateyyMMddHHmm(long time) {
        Date date = new Date(time);
        SimpleDateFormat formatter = getSimpleDateFormat("HH:mm:ss");
        return formatter.format(date);
    }

    public static String formatDateHHmm(long time) {
        Date date = new Date(time);
        SimpleDateFormat formatter = getSimpleDateFormat("HH:mm");
        return formatter.format(date);
    }

    public static String formatDateMMddHHmm(Date time) {
        return formatDateMMddHHmm(time.getTime());
    }

    public static String formatDateMMddHHmm(long time) {
        Date date = new Date(time);
        SimpleDateFormat formatter = getSimpleDateFormat("MM/dd HH:mm");
        return formatter.format(date);
    }

    public static String formatDateMMdd(Date date) {
        SimpleDateFormat formatter = getSimpleDateFormat("MM/dd");
        return formatter.format(date);
    }

    /**
     * 格式化日期：yyyy/MM/dd HH:mm
     * @param time
     * @return String
     * */
    public static String formatDateOnYMDHourMins(Date time){
        if(time == null){
            return "";
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        return format.format(time);
    }

    /**
     * 格式化日期：yyyy/MM/dd HH:mm
     * @param times
     * @return String
     * */
    public static String formatDateOnYMDHourMinSeconds(long times){
        if(times <= 0){
            return "";
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(new Date(times));
    }

    /**
     * 格式化日期：yyyy-MM-dd HH:mm
     * @param time
     * @return String
     * */
    public static String formatDateOnYMDHourMins2(long time){
        if(time <= 0){
            return "";
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(new Date(time));
    }

    /**
     * 格式化日期：yyyy年MM月dd日 HH:mm
     * @param time
     * @return String
     * */
    public static String formatDateOnYMDHourMinsChinese(long time){
        if(time <= 0){
            return "";
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        return format.format(new Date(time));
    }

    public static String formatDateYYYYMM(long time) {
        Date date = new Date(time);
        SimpleDateFormat formatter = getSimpleDateFormat("yyyy/MM");
        return formatter.format(date);
    }

    public static String formatDateYYMM(long time) {
        Date date = new Date(time);
        SimpleDateFormat formatter = getSimpleDateFormat("yyMM");
        return formatter.format(date);
    }

    public static int getDayOfMonth(long time) {
        Date date = new Date(time);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    public static int getMonthOfYear(long time) {
        Date date = new Date(time);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MONTH);
    }

    public static boolean isSameDay(long leftTime, long rightTime) {
        return getDiffDay(leftTime, rightTime) == 0;
    }

    public static int getYear(long time) {
        Date date = new Date(time);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.YEAR);
    }

    public static long getDiffDay(long start, long end) {
        long diff = end - start;
        return diff / (1000 * 60 * 60 * 24);
    }


    public static long getFinalLeftTime(long lastModified, long leftTime) {
        long period = System.currentTimeMillis() - lastModified;
        return leftTime - period / 1000;
    }

    public static String getFormatSendTime(long lastTime) {
        long leftSecondsMM = System.currentTimeMillis() - lastTime;
        if (leftSecondsMM > 0) {
            long leftSeconds = leftSecondsMM / 1000;
            long minutes = leftSeconds / 60;
            long hours = minutes / 60;
            long day = hours / 24;
//            if (day >= 1)
            return formatDateMMddHHmm(lastTime);
//            if (hours >= 1)
//                return DateUtil.formatDateHHmm(lastTime);
//            if (minutes >= 1)
//                return minutes + "分钟前";
//            return "刚刚";
        }
        return "刚刚";
    }

    /**
     * @param timeStr yyyy-MM-dd HH:mm:ss
     * @return Date
     * */
    public static Date parseFromYMDHMS(String timeStr) {
        try {
            if(StringUtil.isEmpty(timeStr)){
                return null;
            }
            SimpleDateFormat formatter = getSimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return formatter.parse(timeStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param timeStr yyyy/MM/dd HH:mm
     * @return Date
     * */
    public static Date parseFromYMDHM(String timeStr) {
        try {
            if(StringUtil.isEmpty(timeStr)){
                return null;
            }
            SimpleDateFormat formatter = getSimpleDateFormat("yyyy/MM/dd HH:mm");
            return formatter.parse(timeStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static long getTodayStartTime(){
        Calendar currentDate = new GregorianCalendar();
        currentDate.set(Calendar.HOUR_OF_DAY, 0);
        currentDate.set(Calendar.MINUTE, 0);
        currentDate.set(Calendar.SECOND, 0);
        return currentDate.getTimeInMillis();
    }
}
