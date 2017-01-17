/*
 * 
 */
package com.medsys.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class ConvertSHA2.
 */
public class ConvertSHA2 {

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory
			.getLogger(ConvertSHA2.class);

	/**
	 * Convert string to sh a2.
	 *
	 * @param clearText the clear text
	 * @return the string
	 * @throws NoSuchAlgorithmException 
	 */
	public String convertStringToSHA2(String clearText) throws NoSuchAlgorithmException {
		logger.info("Convert String to SHA2.");
	 	MessageDigest md = MessageDigest.getInstance("SHA-256");		
		md.update(clearText.getBytes());
		byte byteData[] = md.digest();
		// convert the byte to hex format method 1
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < byteData.length; i++) {
			sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16)
					.substring(1));
		}
		//logger.debug("Hex format : " + sb.toString());
		// convert the byte to hex format method 2
		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < byteData.length; i++) {
			String hex = Integer.toHexString(0xff & byteData[i]);
			if (hex.length() == 1)
				hexString.append('0');
			hexString.append(hex);
		}
		//logger.debug("Hex format : " + hexString.toString());
		return hexString.toString();
		
	}

}
