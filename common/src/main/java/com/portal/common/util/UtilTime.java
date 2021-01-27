//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.portal.common.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class UtilTime {
    public static final SimpleDateFormat FORMAT;
    public static final SimpleDateFormat FORMAT_DATE;
    public static final SimpleDateFormat FORMAT_TIME;
    private static final Calendar INSTANCE;

    public UtilTime() {
    }

    public static int getYear() {
        return INSTANCE.get(1);
    }

    public static int getMonth() {
        return INSTANCE.get(2);
    }

    public static int getDay() {
        return INSTANCE.get(5);
    }

    public static int getHours() {
        return INSTANCE.get(11);
    }

    public static int getMinutes() {
        return INSTANCE.get(12);
    }

    public static int getSeconds() {
        return INSTANCE.get(13);
    }

    public static Date getAddDay(Date nowDate, int dayAddNum) {
        GregorianCalendar tday = new GregorianCalendar();
        tday.setTime(nowDate);
        tday.add(5, dayAddNum);
        Date preDate = tday.getTime();
        return preDate;
    }

    public static Date getAddDay(long nowDate, int dayAddNum) {
        return getAddDay(new Date(nowDate), dayAddNum);
    }

    public static String formatDate(String format, Long time) {
        return formatDate(new SimpleDateFormat(format, Locale.CHINA), time);
    }

    public static String formatDate(SimpleDateFormat format, Long time) {
        return null != time && time.longValue() > 0L?format.format(new Date(String.valueOf(time).length() == 13?time.longValue():time.longValue() * 1000L)):"";
    }

    public static String getTimeFromInt(int time) {
        if(time <= 0) {
            return "00:00";
        } else {
            int secondnd = time / 60;
            int million = time % 60;
            String f = secondnd >= 10?String.valueOf(secondnd):"0" + String.valueOf(secondnd);
            String m = million >= 10?String.valueOf(million):"0" + String.valueOf(million);
            return f + ":" + m;
        }
    }

    public static int getMaxDayByMonth(int year, int month) {
        Calendar time = Calendar.getInstance();
        time.clear();
        time.set(1, year);
        time.set(2, month);
        int day = time.getActualMaximum(5);
        return day;
    }

    public static String getWeek(Date d) {
        String[] dayNames = new String[]{"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        int dayOfWeek = calendar.get(7) - 1;
        if(dayOfWeek < 0) {
            dayOfWeek = 0;
        }

        return dayNames[dayOfWeek];
    }

    public static String getShortTime(long time) {
        String shortstring = "";
        if(time == 0L) {
            return shortstring;
        } else {
            long now = Calendar.getInstance().getTimeInMillis();
            long datetime = (now - time) / 1000L;
            if(datetime > 31536000L) {
                shortstring = FORMAT_DATE.format(new Date(time));
            } else if(datetime > 86400L && (int)(datetime / 86400L) == 2) {
                shortstring = "前天";
            } else if(datetime > 86400L && (int)(datetime / 86400L) == 1) {
                shortstring = "昨天";
            } else if(datetime > 3600L) {
                shortstring = (int)(datetime / 3600L) + "小时前";
            } else if(datetime > 60L) {
                shortstring = (int)(datetime / 60L) + "分钟前";
            } else {
                shortstring = "刚刚";
            }

            return shortstring;
        }
    }

    public static String getShortTime2(long time) {
        String shortstring = "";
        if(time == 0L) {
            return shortstring;
        } else {
            long now = Calendar.getInstance().getTimeInMillis();
            long datetime = (now - time) / 1000L;
            if(datetime > 86400L) {
                shortstring = (int)(datetime / 86400L) + "天";
            } else if(datetime > 3600L) {
                shortstring = (int)(datetime / 3600L) + "小时";
            } else if(datetime > 60L) {
                shortstring = (int)(datetime / 60L) + "分钟";
            } else {
                shortstring = (int)datetime + "秒";
            }

            return shortstring;
        }
    }

    public static String previousWeekByDate(String date) {
        int year = Integer.parseInt(date.substring(0, 4));
        int month = Integer.parseInt(date.substring(4, 6));
        int day = Integer.parseInt(date.substring(6));
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day);
        Date newdate = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(newdate);
        int dayWeek = cal.get(7);
        if(1 == dayWeek) {
            cal.add(5, -1);
        }

        cal.setFirstDayOfWeek(1);
        int s = cal.get(7);
        cal.add(5, cal.getFirstDayOfWeek() - s);
        cal.add(3, -1);
        String imptimeBegin = sdf.format(cal.getTime());
        return imptimeBegin;
    }

    public static String previousWeekEndDayByDate(String date) {
        int year = Integer.parseInt(date.substring(0, 4));
        int month = Integer.parseInt(date.substring(4, 6));
        int day = Integer.parseInt(date.substring(6));
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day);
        Date newdate = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(newdate);
        int dayWeek = cal.get(7);
        if(1 == dayWeek) {
            cal.add(5, -1);
        }

        cal.setFirstDayOfWeek(1);
        int s = cal.get(7);
        cal.add(5, cal.getFirstDayOfWeek() + (6 - s));
        cal.add(3, -1);
        String imptimeBegin = sdf.format(cal.getTime());
        return imptimeBegin;
    }

    public static String getCurrentWeekFirstDayByDate(String date) {
        int year = Integer.parseInt(date.substring(0, 4));
        int month = Integer.parseInt(date.substring(4, 6));
        int day = Integer.parseInt(date.substring(6));
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day);
        Date newdate = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(newdate);
        int dayWeek = cal.get(7);
        if(1 == dayWeek) {
            cal.add(5, -1);
        }

        cal.setFirstDayOfWeek(1);
        int s = cal.get(7);
        cal.add(5, cal.getFirstDayOfWeek() - s);
        String imptimeBegin = sdf.format(cal.getTime());
        return imptimeBegin;
    }

    public static String getCurrentWeekEndDayByDate(String date) {
        int year = Integer.parseInt(date.substring(0, 4));
        int month = Integer.parseInt(date.substring(4, 6));
        int day = Integer.parseInt(date.substring(6));
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day);
        Date newdate = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(newdate);
        int dayWeek = cal.get(7);
        if(1 == dayWeek) {
            cal.add(5, -1);
        }

        cal.setFirstDayOfWeek(1);
        int s = cal.get(7);
        cal.add(5, cal.getFirstDayOfWeek() + (6 - s));
        String imptimeBegin = sdf.format(cal.getTime());
        return imptimeBegin;
    }

    public static String previousMonthByDate(String date) {
        int year = Integer.parseInt(date.substring(0, 4));
        int month = Integer.parseInt(date.substring(4, 6));
        int day = Integer.parseInt(date.substring(6));
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 2, 1);
        Date newdate = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(newdate);
        String imptimeBegin = sdf.format(cal.getTime());
        return imptimeBegin;
    }

    public static String nextMonthByDate(String date) {
        int year = Integer.parseInt(date.substring(0, 4));
        int month = Integer.parseInt(date.substring(4, 6));
        int day = Integer.parseInt(date.substring(6));
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, 1);
        Date newdate = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(newdate);
        String imptimeBegin = sdf.format(cal.getTime());
        return imptimeBegin;
    }

    public static String getCurrentMonthFirstDayByDate(String date) {
        int year = Integer.parseInt(date.substring(0, 4));
        int month = Integer.parseInt(date.substring(4, 6));
        int day = Integer.parseInt(date.substring(6));
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, 1);
        Date newdate = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(newdate);
        String imptimeBegin = sdf.format(cal.getTime());
        return imptimeBegin;
    }

    public static String millisToString(long millis, boolean isWhole, boolean isFormat) {
        String h = "";
        String m = "";
        String s = "";
        String mi = "";
        if(isWhole) {
            h = isFormat?"00小时":"0小时";
            m = isFormat?"00分":"0分";
            s = isFormat?"00秒":"0秒";
            mi = isFormat?"00毫秒":"0毫秒";
        }

        long hper = 3600000L;
        long mper = 60000L;
        long sper = 1000L;
        if(millis / hper > 0L) {
            if(isFormat) {
                h = millis / hper < 10L?"0" + millis / hper:millis / hper + "";
            } else {
                h = millis / hper + "";
            }

            h = h + "小时";
        }

        long temp = millis % hper;
        if(temp / mper > 0L) {
            if(isFormat) {
                m = temp / mper < 10L?"0" + temp / mper:temp / mper + "";
            } else {
                m = temp / mper + "";
            }

            m = m + "分";
        }

        temp %= mper;
        if(temp / sper > 0L) {
            if(isFormat) {
                s = temp / sper < 10L?"0" + temp / sper:temp / sper + "";
            } else {
                s = temp / sper + "";
            }

            s = s + "秒";
        }

        temp %= sper;
        mi = temp + "";
        if(isFormat) {
            if(temp < 100L && temp >= 10L) {
                mi = "0" + temp;
            }

            if(temp < 10L) {
                mi = "00" + temp;
            }
        }

        mi = mi + "毫秒";
        return h + m + s + mi;
    }

    static {
        FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        FORMAT_DATE = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        FORMAT_TIME = new SimpleDateFormat("HH:mm", Locale.CHINA);
        INSTANCE = Calendar.getInstance();
    }
}
