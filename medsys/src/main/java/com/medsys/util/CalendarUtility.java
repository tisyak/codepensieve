package com.medsys.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CalendarUtility {

	private static final int    FIRST_FISCAL_MONTH  = Calendar.APRIL;
	private static final int    LAST_FISCAL_MONTH  = Calendar.MARCH;
	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(CalendarUtility.class);

	
	public static Date getStartDateForLastThreeMonths() {
		Date referenceDate = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(referenceDate);
		cal.add(Calendar.MONTH, -3);
		cal.set(Calendar.DAY_OF_MONTH, 1); // month start
		setTimeToBeginningOfDay(cal);
		return cal.getTime();
	}

	public static Date getEndDateAndTimeOfToday() {
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(new Date());
		setTimeToEndofDay(cal);
		return cal.getTime();
	}

	public static int getFiscalMonth() {
		Calendar cal = GregorianCalendar.getInstance();
        int month = cal.get(Calendar.MONTH);
        int result = ((month - FIRST_FISCAL_MONTH - 1) % 12) + 1;
        if (result < 0) {
            result += 12;
        }
        return result;
    }

    public static int getFiscalYear() {
    	Calendar cal = GregorianCalendar.getInstance();
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);
        int fiscalYear = (month >= FIRST_FISCAL_MONTH) ? year : year - 1;
        logger.debug("Fiscal Year Start: "+fiscalYear);
        return fiscalYear ;
    }

	
	public static Date getFirstDateOfFinancialYear() {
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(new Date());
		// set date to first day of financial year
		cal.set(Calendar.YEAR,getFiscalYear());
		cal.set(Calendar.MONTH, FIRST_FISCAL_MONTH); 
		cal.set(Calendar.DAY_OF_MONTH, 1); // 1st 
		setTimeToBeginningOfDay(cal);
		return cal.getTime();
	}

	public static Date getLastDateOfFinancialYear() {
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(new Date());
		// set date to last day of year
		cal.set(Calendar.YEAR,getFiscalYear()+1);
		cal.set(Calendar.MONTH, LAST_FISCAL_MONTH); 
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH)); // Maximum Date possible in the month
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
