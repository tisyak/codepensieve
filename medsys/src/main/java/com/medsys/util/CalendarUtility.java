package com.medsys.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class CalendarUtility {
	
	public static Date getStartDateForLastThreeMonths() {
		Date referenceDate = new Date();
		Calendar cal = Calendar.getInstance(); 
		cal.setTime(referenceDate); 
		cal.add(Calendar.MONTH, -3);
		cal.set(Calendar.DAY_OF_MONTH,1); // month start
		setTimeToBeginningOfDay(cal);
		return cal.getTime();
	}
	
	public static Date getEndDateAndTimeOfToday() {
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(new Date());
		setTimeToEndofDay(cal);
		return cal.getTime();
	}

	public static Date getFirstDateOfYear() {
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(new Date());
		cal.set(Calendar.DAY_OF_YEAR, 1);
		setTimeToBeginningOfDay(cal);
		return cal.getTime();
	}

	public static Date getLastDateOfYear() {
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(new Date());
		// set date to last day of year
		cal.set(Calendar.MONTH, 11); // 11 = december
		cal.set(Calendar.DAY_OF_MONTH, 31); // new years eve
		setTimeToEndofDay(cal);
		return cal.getTime();

	}

	public static Date getFirstDateOfMonth() {
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(new Date());
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
		setTimeToBeginningOfDay(cal);
		return cal.getTime();
	}

	public static Date getLastDateOfMonth() {
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(new Date());
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		setTimeToEndofDay(cal);
		return cal.getTime();

	}

	public static void setTimeToBeginningOfDay(Calendar calendar) {
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
	}

	public static void setTimeToEndofDay(Calendar calendar) {
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
	}

}
