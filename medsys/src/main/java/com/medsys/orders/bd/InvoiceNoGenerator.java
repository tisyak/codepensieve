package com.medsys.orders.bd;

public class InvoiceNoGenerator {
	
	public static synchronized  String getNextInvoiceNumber(String prevInvoiceNumber) throws Exception {

		String[] arrSplitInvNumber = prevInvoiceNumber.split("-");

		char[] str = arrSplitInvNumber[2].toCharArray();
		int n = Integer.parseInt(arrSplitInvNumber[3]);

		
		/*int yearInPrevInvoiceNumber = Integer.parseInt(arrSplitInvNumber[1]);

		int currentYear = GregorianCalendar.getInstance().get(GregorianCalendar.YEAR);

		if (yearInPrevInvoiceNumber != currentYear) {
			// The year is changed. Hence, resetting all following counters to
			 // initial values
			 

			n = 0;
			str = new char[] { 'A', 'A', 'A' };
		}*/
		

		if (n == 999) { // Once the number reaches 9999, increase the
			// letter by one and reset number to 0.
			n = 0;
			incrementString(str);
		}

		n++; // Letters can be incremented the same as numbers. Adding 1
		// to "AAA" prints out "AAB".
		String id = String.format("%03d", n); // Create "id". pads the number to
		// make it 4 digits.
		return (arrSplitInvNumber[0] + "-" + arrSplitInvNumber[1] + "-" + new String(str) + "-" + id);
	}
	
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
