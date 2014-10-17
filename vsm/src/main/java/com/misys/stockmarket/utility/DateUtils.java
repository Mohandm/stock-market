package com.misys.stockmarket.utility;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

	public final static String DD_MM_YYYY = "dd/MM/yyyy";

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

	public static Date getPastDateFromCurrentDate(int months) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.add(Calendar.MONTH, -months);
		return calendar.getTime();
	}

}
