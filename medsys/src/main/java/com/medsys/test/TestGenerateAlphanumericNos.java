package com.medsys.test;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;

public class TestGenerateAlphanumericNos {

	public static void main(String[] args) {
		int num_of_ids = 5; // Number of "ids" to generate (26*26*26*999
									// + 1 <-- To get ).
		int i = 0; // Loop counter.
		//PrintWriter writer = null;
		try {
			//writer = new PrintWriter("F:\\theGenerateAlphanumericNos.txt", "UTF-8");
			
			String invNumber = "INV-2016-ZZZ-998";
			
			while (i <= num_of_ids) {

				invNumber = getNextInvoiceNumber(invNumber);
				//writer.print(invNumber + "\t");

				System.out.println(i + " : " + invNumber); // Print out
				// the id.

				i++;

			}

			//writer.close();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Halting process. Exception at " + i + ": " + e.getMessage());
			/*if (writer != null) {
				writer.println("Halting process. Exception at " + i + ": " + e.getMessage());
				writer.close();
			}*/
		}

	}

	@Autowired
	public static String getNextInvoiceNumber(String prevInvoiceNumber) throws Exception {

		String[] arrSplitInvNumber = prevInvoiceNumber.split("-");

		char[] str = arrSplitInvNumber[2].toCharArray();
		int n = Integer.parseInt(arrSplitInvNumber[3]);

		int yearInPrevInvoiceNumber = Integer.parseInt(arrSplitInvNumber[1]);

		int currentYear = GregorianCalendar.getInstance().get(GregorianCalendar.YEAR);

		if (yearInPrevInvoiceNumber != currentYear) {
			/*
			 * The year is changed. Hence, resetting all following counters to
			 * initial values
			 */

			n = 0;
			str = new char[] { 'A', 'A', 'A' };
		}

		

		if (n == 999) { // Once the number reaches 9999, increase the
			// letter by one and reset number to 0.
			n = 0;
			incrementString(str);
		}

		n++; // Letters can be incremented the same as numbers. Adding 1
		// to "AAA" prints out "AAB".
		String id = String.format("%03d", n); // Create "id". pads the number to
		// make it 4 digits.
		return (arrSplitInvNumber[0] + "-" + currentYear + "-" + new String(str) + "-" + id);
	}

	@Autowired
	public static void incrementString(char[] str) throws Exception {
		boolean someCharsAreNotZ = false;
		for (int pos = str.length - 1; pos >= 0; pos--) {
			if (Character.toUpperCase(str[pos]) != 'Z') {
				str[pos]++;
				someCharsAreNotZ = true;
				break;
			} else {
				str[pos] = 'A';
			}
		}
		if (!someCharsAreNotZ) {
			throw new Exception("End of String combinations reached!");
		}
	}
}
