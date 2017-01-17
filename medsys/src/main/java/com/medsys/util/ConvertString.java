/*
 * 
 */
package com.medsys.util;

/**
 * The Class ConvertString.
 */
public class ConvertString {
	
	/**
	 * Convert to ascii.
	 *
	 * @param val the val
	 * @return the long
	 */
	public long convertToAscii(String val) {
		long longVal = 0;
		for (int i = 0; i < val.length(); i++) {
			char character = val.charAt(i);
			longVal += (int) character;
		}

		return longVal;
	}
}
