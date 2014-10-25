package com.misys.stockmarket.utility;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

	public final static String DD_MM_YYYY = "dd/MM/yyyy";

	public static final long SECONDS_IN_MINUTE = 60;

	public static final long MINUTES_IN_HOUR = 60;

	public static final long HOURS_IN_DAY = 24;

	public static final long DAYS_IN_WEEK = 7;

	public static final String DISPLAY_DATE_TIME_FORMAT = "MM/dd/yyyy, HH:mm";

	/**
	 * Returns a Timestamp object standing for the given String date interpreted
	 * using the localized DATE_TIME_FORMAT. Creation date: (8/31/00 8:16:15 AM)
	 * 
	 * @return java.sql.Timestamp
	 * @param dateString
	 *            java.lang.String
	 * @param language
	 *            java.lang.String
	 */
	public static Date stringDateToDate(String dateString, String inputFormat) {
		SimpleDateFormat formatter = new SimpleDateFormat(inputFormat);
		Date date = formatter.parse(dateString, new ParsePosition(0));
		return date;
	}

	public static Date getPastMonthFromCurrentDate(int months) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.add(Calendar.MONTH, -months);
		return calendar.getTime();
	}

	public static Date getPastDateFromCurrentDate(int days) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.add(Calendar.DATE, -days);
		return calendar.getTime();
	}

	public static int dateDifference(Calendar endDate, Calendar beginDate) {
		Calendar truncatedBegin = (Calendar) beginDate.clone();
		Calendar truncatedEnd = (Calendar) endDate.clone();
		truncatedBegin.set(Calendar.HOUR_OF_DAY, 0);
		truncatedBegin.set(Calendar.MINUTE, 0);
		truncatedBegin.set(Calendar.SECOND, 0);
		truncatedBegin.set(Calendar.MILLISECOND, 0);
		truncatedEnd.set(Calendar.HOUR_OF_DAY, 0);
		truncatedEnd.set(Calendar.MINUTE, 0);
		truncatedEnd.set(Calendar.SECOND, 0);
		truncatedEnd.set(Calendar.MILLISECOND, 0);
		long differenceInMillis = truncatedEnd.getTime().getTime()
				- truncatedBegin.getTime().getTime();
		return (int) (differenceInMillis / 1000 / SECONDS_IN_MINUTE
				/ MINUTES_IN_HOUR / HOURS_IN_DAY);
	}

	public static int differenceInDays(Date date1, Date date2) {
		Calendar calendar1 = Calendar.getInstance();
		calendar1.setTime(date1);
		Calendar calendar2 = Calendar.getInstance();
		calendar2.setTime(date2);
		return dateDifference(calendar1, calendar2);
	}

	public static String getFormattedDateTimeString(Date inputDate) {
		SimpleDateFormat format = new SimpleDateFormat(DISPLAY_DATE_TIME_FORMAT);
		return format.format(inputDate);
	}
}
