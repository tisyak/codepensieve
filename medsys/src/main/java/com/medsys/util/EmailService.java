/*
 * 
 */
package com.medsys.util;

import java.net.HttpURLConnection;
import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.medsys.common.model.EmailLinkParameters;
import com.medsys.common.model.Response;

/**
 * The Class EmailService.
 */
@Component
public class EmailService {


	//static String scheduledTime = "20110701 02:27:00";
	/** The connection. */
	static HttpURLConnection connection = null;
	
	/** The Constant logger. */
	private static final Logger logger = LoggerFactory
			.getLogger(EmailService.class);

	/**
	 * Send email.
	 *
	 * @param email the email
	 * @param emailSubject the email subject
	 * @param emailContent the email content
	 * @return true, if successful
	 */
	public boolean sendEmail(String email, String emailSubject, String emailContent,String smtpHost,String smtpPort,String smtpUserId,String smtpPassword) {

	   final String userId=smtpUserId;
	    final String userPassword=smtpPassword;
		Properties props = new Properties();
		/*logger.debug(" Master Config is :"+masterConfigService);
		logger.debug("Para Value is :"+masterConfigService.getConfigPara(
				ConfigParaKey.SMTP_HOST));*/
		/*String smtpHost = masterConfigService.getConfigPara(
				ConfigParaKey.SMTP_HOST).getParavalue();
		String smtpPort = masterConfigService.getConfigPara(
				ConfigParaKey.SMTP_PORT).getParavalue();
		String smtpUserId = masterConfigService.getConfigPara(
				ConfigParaKey.SMTP_USER_ID).getParavalue();*/
		props.put("mail.smtp.host", smtpHost);
		//props.put("mail.smtp.port", smtpPort);
		//startttls is required  true, set smtp auth true and set start enable false for SMTP Authentication 
		props.put("mail.smtp.starttls.enable","false");
		props.put("mail.smtp.starttls.required", "true");  
		props.put("mail.smtp.auth", "true");  
		// If you need to authenticate
		String email_from = smtpUserId;
		String email_to = email;
		
	//For Authentication pass UserId and Password.	
	Session session = Session.getDefaultInstance(props,
	            new javax.mail.Authenticator() {
	                protected PasswordAuthentication getPasswordAuthentication() {
	                    return new PasswordAuthentication(userId,userPassword);
	                }
	            });
		
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(email_from));
			message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(email_to));
			message.setSubject(emailSubject);
			message.setContent(emailContent,"text/html");
			logger.debug("Sending  email through transport.");
			Transport.send(message);
			//session.getTransport().
			logger.debug("Email sent to " + email_to);
			return true;
		} catch (MessagingException e) {
			logger.error("Unable to send email: "+ e.toString());
			return false;
		}
	}
	
	public EmailLinkParameters evaluateEmailQueryString(String queryString) {
		EmailLinkParameters linkParameters = new EmailLinkParameters();
		//The format expected is that the first parameters value will be the key in the query
		//The second parameter is expected to be a code
		String[] split = queryString.split("&");
		String[] splitemail = split[0].split("=");
		linkParameters.setKey(splitemail[1]);
		logger.debug("Verification id from query string : " + linkParameters.getKey());
		String[] splitverificationcode = split[1].split("=");
		linkParameters.setCode(splitverificationcode[1]);
		
		return linkParameters;
	}
	
	
	public EmailResponse receiveEmail(String emailProtocol, String emailHost, String user, String password) {
		
		EmailResponse emailResponse = new EmailResponse();
		
		Properties props = new Properties();
        props.setProperty("mail.store.protocol", emailProtocol);
        try {
            Session session = Session.getInstance(props, null);
            Store store = session.getStore();
            store.connect(emailHost, user, password);
            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);
            System.out.println("Number of mails: " + inbox.getMessageCount());
            System.out.println("Number of unread mails: " + inbox.getUnreadMessageCount());
            emailResponse.setTotalMailCount(inbox.getMessageCount());
            emailResponse.setTotalUnreadMailCount(inbox.getUnreadMessageCount());
            emailResponse.setResponse(new Response(true, null));
            
            /*Message msg = inbox.getMessage(inbox.getMessageCount());
            Address[] in = msg.getFrom();
            for (Address address : in) {
                System.out.println("FROM:" + address.toString());
            }
            Multipart mp = (Multipart) msg.getContent();
            BodyPart bp = mp.getBodyPart(0);
            System.out.println("SENT DATE:" + msg.getSentDate());
            System.out.println("SUBJECT:" + msg.getSubject());
            System.out.println("CONTENT:" + bp.getContent());*/
        } catch (Exception mex) {
        	emailResponse.setResponse(new Response(false, EpSystemError.SYSTEM_INTERNAL_ERROR));
            mex.printStackTrace();
        }
        
        return emailResponse;

		
	}
	
	
}
