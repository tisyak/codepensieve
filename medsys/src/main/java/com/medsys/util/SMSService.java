/*
 * 
 */
package com.medsys.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


/**
 * The Class SMSService.
 */
@Component
public class SMSService {

	/** The master config service. */
	/*@Autowired
	MasterConfigBD masterConfigService;*/

	/** The connection. */
	static HttpURLConnection connection = null;

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory
			.getLogger(SMSService.class);

	/**
	 * Send sms.
	 * 
	 * @param mobile
	 *            the mobile
	 * @param smsContent
	 *            the sms content
	 * @return true, if successful
	 */
	public boolean sendSMS(String mobileNumber, String smsContent,String username,String password,String senderid,URL url) {
		
	    logger.debug("Sending SMS to: "+mobileNumber);
	
		try {
			if (mobileNumber != null && !mobileNumber.isEmpty()) {
			    
			    /*
				String username = masterConfigService.getConfigPara(
						ConfigParaKey.SMS_USERNAME).getParavalue();
				String password = masterConfigService.getConfigPara(
						ConfigParaKey.SMS_PASSWORD).getParavalue();
				String senderid = masterConfigService.getConfigPara(
						ConfigParaKey.SMS_SENDER_ID).getParavalue();*/
			    
				String mobileNumberWithZeroPrefix = "0" + mobileNumber;
				// String message_content = labels.getString(smsContent) + " " +
				// otp;
				/*URL url = new URL(masterConfigService.getConfigPara(
						ConfigParaKey.SMS_URL).getParavalue());*/
				connection = (HttpURLConnection) url.openConnection();
				connection.setDoInput(true);
				connection.setDoOutput(true);
				connection.setRequestMethod("POST");
				HttpURLConnection.setFollowRedirects(true);
				connection = sendSingleSMS(username, password, senderid,
				        mobileNumberWithZeroPrefix, smsContent);
				if (connection != null) {
					logger.debug("SMS sent successfully to "+mobileNumber);
					return true;
				} else {
					logger.error("Error while sending SMS via MSDG gateway.");
					return false;
				}
			} else {
				logger.error("Error: No Mobile Number found.");
				return false;
			}
		} catch (MalformedURLException e) {
			logger.error("MalformedURLException:  "+e.toString());
			return false;
		} catch (IOException e) {
			logger.error("IOException :"+e.toString());
			return false;
		}
		 catch (Exception e) {
	            logger.error("Exception :"+e.toString());
	            return false;
	        }

	}

	/*
	 * Method for sending single SMS.
	 */
	/**
	 * Send single sms.
	 * 
	 * @param username
	 *            the username
	 * @param password
	 *            the password
	 * @param senderId
	 *            the sender id
	 * @param mobileNo
	 *            the mobile no
	 * @param message
	 *            the message
	 * @return the http url connection
	 */
	public static HttpURLConnection sendSingleSMS(String username,
			String password, String senderId, String mobileNo, String message) {
		try {
			String smsservicetype = "singlemsg"; // For single message.
			@SuppressWarnings("deprecation")
			String query = "username=" + URLEncoder.encode(username)
					+ "&password=" + URLEncoder.encode(password)
					+ "&smsservicetype=" + URLEncoder.encode(smsservicetype)
					+ "&content=" + URLEncoder.encode(message) + "&mobileno="
					+ URLEncoder.encode(mobileNo) + "&senderid="
					+ URLEncoder.encode(senderId);

			connection.setRequestProperty("Contentlength",
					String.valueOf(query.length()));
			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			connection.setRequestProperty("User-Agent",
					"Mozilla/4.0 (compatible; MSIE 5.0;	Windows 98; DigExt)");

			// open up the output stream of the connection
			DataOutputStream output = new DataOutputStream(
					connection.getOutputStream());

			// write out the data
			/* int queryLength = query.length(); */
			output.writeBytes(query);
			// output.close();

			// get ready to read the response from the cgi script
			DataInputStream input = new DataInputStream(
					connection.getInputStream());

			// read in each character until end-of-stream is detected
			for (int c = input.read(); c != -1; c = input.read())
				// logger.debug(""+(char) c);
				input.close();
		} catch (Exception e) {

			logger.error(e.toString());
			return null;
		}

		return connection;
	}

}
