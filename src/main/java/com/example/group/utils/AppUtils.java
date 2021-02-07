package com.example.group.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import com.example.group.Application;

/**
 * Application utility
 * 
 * @author k.yanagida
 *
 */
public final class AppUtils {

	public static final String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_FORMAT = "yyyy-MM-dd";

	private AppUtils() {
	}

	/**
	 * Get today
	 * 
	 * @return Date
	 */
	public static Date getToday() {
		final Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTime();
	}

	/**
	 * Get path of resource file
	 * 
	 * @param name
	 * @return String
	 */
	public static String getResourcePath(final String name) {
		return Application.class.getClassLoader().getResource(name).getPath();
	}

	/**
	 * Check valid environment
	 * 
	 * @return
	 */
	public static boolean isValidEnvironmeent() {
		return PlatformUtils.isWindows() || PlatformUtils.isMac();
	}

	/**
	 * Calculate difference minute
	 * 
	 * @param dateStr1
	 * @param dateStr2
	 * @return difference
	 */
	public static long getDifferenceMinute(final String dateStr1,
			final String dateStr2) {
		final Date date1 = getFormattedDate(dateStr1, TIME_FORMAT);
		final Date date2 = getFormattedDate(dateStr2, TIME_FORMAT);
		final long differenceMSec = date1.getTime() - date2.getTime();
		return TimeUnit.MILLISECONDS.toMinutes(differenceMSec);
	}

	/**
	 * Get formatted date of String
	 * 
	 * @param date
	 * @param format
	 * @return String
	 */
	public static String getFormattedDateStr(final Date date,
			final String format) {
		final SimpleDateFormat sdf = new SimpleDateFormat(format);
		sdf.setLenient(false);
		return sdf.format(date);
	}

	/**
	 * Get formatted date
	 * 
	 * @param dateStr
	 * @param format
	 * @return Date
	 */
	public static Date getFormattedDate(final String dateStr,
			final String format) {
		final SimpleDateFormat sdf = new SimpleDateFormat(format);
		sdf.setLenient(false);
		try {
			return sdf.parse(dateStr);
		} catch (ParseException e) {
			AppLogger.error(e, "error occurred during formatting date");
			return null;
		}
	}

}
