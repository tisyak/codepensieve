package com.medsys.util.queue.email;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import com.medsys.exception.SysException;
import com.medsys.util.ConfigKeys;
import com.medsys.util.EmailService;
import com.medsys.util.EpSystemError;



@Component
public class EmailTaskExecutor {
    final static Logger logger = LoggerFactory
            .getLogger(EmailTaskExecutor.class);
    @Autowired
    TaskExecutor taskExecutor;
    @Autowired
    Environment environment;

    private class QueueObjectProcessor implements Runnable {

        private EmailQueueMessageObject message;

        public QueueObjectProcessor(EmailQueueMessageObject message) {
            this.message = message;

        }

        public void run() {
            //logger.debug("Email message in queue : " + message);
            EmailService emailService;
            String email = null;
            String emailSubject = null;
            String emailContent = null;
            String smtpHost = null;
            String smtpPort = null;
            String smtpUserId = null;
            String smtpPassword=null;
            EmailQueueMessageObject emailDetail = null;

            emailDetail = message;
            //logger.debug("Email details : " + emailDetail);
            LinkedHashMap<String, String> emailLinkedHashMap = emailDetail
                    .getEmailLinkedHashMap();
            //logger.debug("Email linkedHashMap : " + emailLinkedHashMap);

            smtpHost = environment.getProperty(ConfigKeys.SMTP_HOST);
            logger.debug("SMTP HOST : " + smtpHost);
            smtpPort = environment.getProperty(ConfigKeys.SMTP_PORT);
            logger.debug("SMTP PORT : " + smtpPort);
            smtpUserId =environment.getProperty(ConfigKeys.SMTP_USER_ID);
            logger.debug("SMTP USER ID : " + smtpUserId);
           smtpPassword=environment.getProperty(ConfigKeys.SMTP_PASSWORD);
            logger.debug("SMTP PASSWORD : "+smtpPassword);
            
          //Session session = Session.getDefaultInstance(props);
          
            
            Set<Entry<String, String>> set = emailLinkedHashMap.entrySet();

            // Get an iterator
            Iterator<Entry<String, String>> i = set.iterator();
            // Display elements
            while (i.hasNext()) {
                Entry<String, String> me = i.next();

                if (me.getKey().equals("email")) {
                    logger.debug("" + me.getValue());
                    email = me.getValue();
                }
                if (me.getKey().equals("emailSubject")) {
                    logger.debug("" + me.getValue());
                    emailSubject = me.getValue();
                }
                if (me.getKey().equals("emailContent")) {
                    logger.debug("" + me.getValue());
                    emailContent = me.getValue();
                }

            }
            //logger.debug(" Email in consumer : " + email
             //       + "\n EmailSubject in consumer :" + emailSubject
              //      + " \n EmailContent in consumer : " + emailContent);
            // EmailService emailService=new EmailService();
            emailService = new EmailService();
            emailService.sendEmail(email, emailSubject, emailContent, smtpHost,
                    smtpPort, smtpUserId,smtpPassword);
            logger.debug("Ending of EmailService ");

        }

    }

    public void addToQueue(final String email, final String emailSubject,
            final String emailContent) throws SysException {
        //logger.debug("Calling print message");
        try {
            taskExecutor.execute(new QueueObjectProcessor(
                    new EmailQueueMessageObject(email, emailSubject,
                            emailContent)));
        } catch (Exception e) {
            logger.error("Error in adding sms message object in blocking Queue: "
                    + e.getMessage());
            throw new SysException("SMS to Mobile Number", email,
                    EpSystemError.QUEUEING_EXCEPTION);
        }
        logger.debug("Email successfully added to EMAIL_QUEUE");
    }

}
