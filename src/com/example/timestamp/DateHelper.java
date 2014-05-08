package com.example.timestamp;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class DateHelper {

	public static long DAY_IN_MILLIS = 1000 * 3600 * 24;
	public static long WEEK_IN_MILLIS = 1000 * 3600 * 24 * 7;
	
	public static long getStartOfWeek()
	{
		GregorianCalendar time = new GregorianCalendar();
		time.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		time.set(Calendar.HOUR_OF_DAY, 0);
		time.set(Calendar.MINUTE, 0);
		
		return time.getTimeInMillis();
	}
	
	public static long getStartOfMonth(int monthToGoBack)
	{
		GregorianCalendar time = new GregorianCalendar();
		int year = time.get(Calendar.YEAR);
		int month = time.get(Calendar.MONTH);
		time.set(Calendar.DAY_OF_MONTH, 0);
		time.set(Calendar.HOUR_OF_DAY, 0);
		time.set(Calendar.MINUTE, 0);
		if (month < monthToGoBack) {
			time.set(Calendar.YEAR, year-1);
			time.set(Calendar.MONTH, month + 12 - monthToGoBack);
		}
		else
			time.set(Calendar.MONTH, month - monthToGoBack);
		return time.getTimeInMillis();
	}
	
}
