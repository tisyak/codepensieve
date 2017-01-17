package com.medsys.util.queue.email;

import java.util.LinkedHashMap;

public class EmailQueueMessageObject implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private LinkedHashMap<String, String> emailLinkedHashMap;

    //, String smtpHost, String smtpPort, String smtpUserId
    
    public EmailQueueMessageObject(String email, String emailSubject,
            String emailContent) {

        emailLinkedHashMap = new LinkedHashMap<String, String>();
        emailLinkedHashMap.put("email", email);
        emailLinkedHashMap.put("emailSubject", emailSubject);
        emailLinkedHashMap.put("emailContent", emailContent);
       /* emailLinkedHashMap.put("smtpHost", smtpHost);
        emailLinkedHashMap.put("smtpPort", smtpPort);
        emailLinkedHashMap.put("smtpUserId", smtpUserId);*/

    }

    public LinkedHashMap<String, String> getEmailLinkedHashMap() {
        return emailLinkedHashMap;
    }

    @Override
    public String toString() {
        return "EmailQueueMessageObject [emailLinkedHashMap="
                + emailLinkedHashMap + "]";
    }

}
