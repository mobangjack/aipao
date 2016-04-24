package me.aipao.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * 
 * @author 帮杰
 *
 */
public class DateUtil {

    public static final String FORMAT_DATETIME = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_DATE = "yyyy-MM-dd";

    public static String formatDateTime(Date date) {
        return formatDateTime(date, FORMAT_DATETIME);
    }

    public static String formatCurrent() {
		return formatDateTime(new Date(), FORMAT_DATETIME);
	}
    
    public static String formatCurrent(String format) {
		return formatDateTime(new Date(), format);
	}
    
    public static String formatDateTime(Date date,String patern) {
        if(date == null) return null;
        SimpleDateFormat sdf = new SimpleDateFormat(patern);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        return sdf.format(date);
    }
    
    public static String formatDate(Date date) {
        return formatDate(date, FORMAT_DATE);
    }

    public static String formatDate(Date date,String patern) {
        if(date == null) return null;
        SimpleDateFormat sdf = new SimpleDateFormat(patern);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        return sdf.format(date);
    }
    
    /**
     * 字符串转时间
     * @param dateString
     * @param style
     * @return
     */
    public static Date string2Date(String dateString, String style) {
        if (StrUtil.isBlank(dateString)) return null;
        Date date = new Date();
        SimpleDateFormat strToDate = new SimpleDateFormat(style);
        try {
            date = strToDate.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static boolean isExpire(Date date, long expiredIn) {
    	if (date == null) {
			return true;
		}
        return new Date().getTime() - date.getTime() > expiredIn;
    }

    public static boolean isToday(Date date) {
		return isTheSameDay(date, new Date());
	}
    
    public static boolean notToday(Date date) {
		return !isToday(date);
	}
    
    @SuppressWarnings("deprecation")
	public static boolean isTheSameDay(Date date1, Date date2) {
    	if (date1 == null || date2 == null) {
			return false;
		}
		return (date1.getYear()==date2.getYear())&&(date1.getMonth()==date2.getMonth())&&(date1.getDate()==date2.getDate());
	}
    
    public static Date getHourAfter(Date date, int hour) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.roll(11, hour);
        return calendar.getTime();
    }

    public static Date getHourBefore(Date date, int hour) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.roll(11, -hour);
        return calendar.getTime();
    }

    public static Date getDateBefore(Date date, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.roll(5, -day);
        return calendar.getTime();
    }

    public static Date getDateAfter(Date date, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.set(5, now.get(5) + day);
        return now.getTime();
    }

    public static Date getMinuteAfter(Date date, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, minute);
        return calendar.getTime();
    }

    public static Date getMinuteBefore(Date date, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, -minute);
        return calendar.getTime();
    }

}
