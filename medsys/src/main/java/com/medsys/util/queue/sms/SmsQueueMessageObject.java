package com.medsys.util.queue.sms;

import java.util.LinkedHashMap;

public class SmsQueueMessageObject implements java.io.Serializable{

    private static final long serialVersionUID = 1L;
    private LinkedHashMap<String, String> smsLinkedHashMap;
    
    public SmsQueueMessageObject(String mobile, String smsContent) {

        smsLinkedHashMap = new LinkedHashMap<String, String>();
        smsLinkedHashMap.put("mobile",mobile);
        smsLinkedHashMap.put("smsContent", smsContent);
    
    }

    public LinkedHashMap<String, String> getSmsLinkedHashMap() {
        return smsLinkedHashMap;
    }   
}
