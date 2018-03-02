package com.medsys.test;

import java.util.*;

public class GregorianCalendarDemo {
   public static void main(String[] args) {

      // create a new calendar
      GregorianCalendar cal = (GregorianCalendar) GregorianCalendar.getInstance();
      cal.set(GregorianCalendar.MONTH, 1);
      // print the current date and time
      System.out.println("" + cal.getTime());

      // get actual maximum for day_of_month
      int max = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
      System.out.println("Actual Maximum:" + max);

      // get actual maximum for YEAR
      max = cal.getActualMaximum(GregorianCalendar.YEAR);
      System.out.println("Actual Maximum:" + max);
   }
}