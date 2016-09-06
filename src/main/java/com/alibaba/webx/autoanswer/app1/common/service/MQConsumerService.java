/*
 * Copyright 2016 Alibaba.com All right reserved. This software is the
 * confidential and proprietary information of Alibaba.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Alibaba.com.
 */
package com.alibaba.webx.autoanswer.app1.common.service;


import com.alibaba.webx.autoanswer.app1.common.RecordConstants;
import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Consumer;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.PropertyKeyConst;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import java.util.Properties;

/**
 * @author xuzhen.qxz 2016.07.21 11:07:17
 */
public class MQConsumerService implements InitializingBean{
    
    private static final Logger log  = LoggerFactory.getLogger(MQConsumerService.class);
    
    private static Properties properties = new Properties();
    private static Consumer consumer;
    
    private String CID;
    private String AccessKey;
    private String SecretKey;
    
    @Override
    public void afterPropertiesSet() throws Exception {
//        // TODO Auto-generated method stub
        properties.put(PropertyKeyConst.ConsumerId, CID);// 
        properties.put(PropertyKeyConst.AccessKey, AccessKey);// 
        properties.put(PropertyKeyConst.SecretKey, SecretKey);//

        consumer  = ONSFactory.createConsumer(properties);

        consumer.subscribe(RecordConstants.MQ_TOPIC, "*", new MessageListener() {
            @Override
            public Action consume(Message message, ConsumeContext context) {
                // TODO Auto-generated method stub
                if(message == null){
                    return Action.CommitMessage;
                }
//                if(message.getTag().equals(RecordConstants.MAKE_A_CALL)){
//                    try{
//                        RecordHandleThread thread = new RecordHandleThread();
////                        thread.setModel(message.getTag());
////                        thread.setRetry(10);
//                        thread.start();
//                        return Action.CommitMessage;
//
//                    } catch(Exception e){
//                        System.out.println(e);
//                        return Action.ReconsumeLater;
//                    }
//                }
                log.info("receive a message");
                return Action.CommitMessage;
            }
        });

        consumer.start();
        registerShutdownHook();
    }
    

    /**
     * jvm hook
     */
    public static void registerShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {

            public void run() {
                try {
                    consumer.shutdown();
                } catch (Exception e) {
                    log.error("MetaProducer shutdown error", e.getMessage());
                }
            }
        }));

    }



    class RecordHandleThread extends Thread{
//        private String model;
//        private int retry;
//
//        public void setModel(String model){
//            this.model = model;
//        }
//
//        public void setRetry(int retry){
//            this.retry = retry;
//        }
//
//        @Override
//        public void run() {
//           String url =  RecordFileManagerImpl.getUrl(model);
//            int count = 0;
//            while(true){
//                if(count > retry) break;
//                try {
//                    String result = RecordIOManagerImpl.checkFileExistence(url);
//                    if(StringUtil.isBlank(result)){
//                        Thread.sleep(2 * 60 * 1000);
//                        continue;
//                    }
//                    System.out.print(result);
//                    RecordIOManagerImpl.downLoadFileFromURL(result,model,"/records/" + model );
//                    File file = new File("/records/" + model);
//                    String ossPath = OSSManagerImpl.uploadFile(file);
//
//                    List<String> transList = TranscriptionImpl.translation(ossPath);
//
//                }catch (Exception e){
//                    System.out.println(e);
//                }
//
//            }
//        }
    }



	public String getCID() {
		return CID;
	}


	public void setCID(String cID) {
		CID = cID;
	}


	public String getAccessKey() {
		return AccessKey;
	}


	public void setAccessKey(String accessKey) {
		AccessKey = accessKey;
	}


	public String getSecretKey() {
		return SecretKey;
	}


	public void setSecretKey(String secretKey) {
		SecretKey = secretKey;
	}
}
