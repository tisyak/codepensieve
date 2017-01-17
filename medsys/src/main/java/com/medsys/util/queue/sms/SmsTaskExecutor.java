package com.medsys.util.queue.sms;

import java.net.MalformedURLException;
import java.net.URL;
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
import com.medsys.util.EpSystemError;
import com.medsys.util.SMSService;

@Component
public class SmsTaskExecutor {

    @Autowired
    TaskExecutor taskExecutor;

    @Autowired
    Environment environment;
    
    @Autowired
    SMSService smsService;

    final static Logger logger = LoggerFactory.getLogger(SmsTaskExecutor.class);

    private class QueueObjectProcessor implements Runnable {
        private SmsQueueMessageObject message;
       

        public QueueObjectProcessor(SmsQueueMessageObject message) {
           // logger.debug("SMS object created ");

            this.message = message;
        }

        public void run() {
          
            String mobile = null;
            String smsContent = null;
            String username = null;
            String password = null;
            String senderid = null;
            URL url = null;
            SmsQueueMessageObject smsDetail = null;

            try {

                smsDetail = this.message;
                logger.debug("Executing to SMS service.");
                LinkedHashMap<String, String> smsLinkedHashMap = smsDetail
                        .getSmsLinkedHashMap();
                //logger.debug("SMS linkedHashMap : " + smsLinkedHashMap);

                username = environment.getProperty(ConfigKeys.SMS_USERNAME);
                logger.debug("UserName : " + username);
                password = environment.getProperty(ConfigKeys.SMS_PASSWORD);
                logger.debug("Password : " + password);
                senderid = environment.getProperty(ConfigKeys.SMS_SENDER_ID);
                logger.debug("senderid : "
                        + environment.getProperty(ConfigKeys.SMS_SENDER_ID));

                url = new URL(environment.getProperty(ConfigKeys.SMS_URL));
                logger.debug("SMS Service URL : " + url);

                Set<Entry<String, String>> set = smsLinkedHashMap.entrySet();
                // Get an iterator
                Iterator<Entry<String, String>> i = set.iterator();
                // Display elements
                while (i.hasNext()) {
                    Entry<String, String> me = i.next();
                    if (me.getKey().equals("mobile")) {
                        logger.debug("" + me.getValue());
                        mobile = me.getValue();
                    }
                    if (me.getKey().equals("smsContent")) {
                        logger.debug("" + me.getValue());
                        smsContent = me.getValue();
                    }
                }
               // logger.debug(" Mobile in consumer : " + mobile
                //        + "\n SMSSubject in consumer :" + smsContent);
                smsService.sendSMS(mobile, smsContent, username, password,
                        senderid, url);               
                logger.debug("Ending of SMSService ");

            } catch (MalformedURLException excp) {
                logger.debug("Queue Interrupted " + excp);

            }
            catch (Exception excp) {
                logger.debug("Queue Interrupted " + excp);

            }

        }

    }

    public void addToQueue(String mobileNumber, String smsContent)
            throws SysException {
        //logger.debug("Calling print message");
        try {
            //logger.debug("TaskExecutor: " + taskExecutor);
            taskExecutor.execute(new QueueObjectProcessor(
                    new SmsQueueMessageObject(mobileNumber, smsContent)));

        } catch (Exception e) {
            logger.error("Error in adding sms message object in blocking Queue: "
                    + e.getMessage());
            throw new SysException("SMS to Mobile Number", mobileNumber,
                    EpSystemError.QUEUEING_EXCEPTION);
        }
        //logger.debug("SMS successfully added to SMS_QUEUE");
    }

}
