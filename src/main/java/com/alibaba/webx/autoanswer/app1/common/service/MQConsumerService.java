/*
 * Copyright 2016 Alibaba.com All right reserved. This software is the
 * confidential and proprietary information of Alibaba.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Alibaba.com.
 */
package com.alibaba.webx.autoanswer.app1.common.service;


import com.aliyun.openservices.ons.api.Consumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import java.util.Properties;

/**
 * 类MQComsumerService.java的实现描述：TODO 类实现描述 
 * @author xuzhen.qxz 2016年7月21日 下午11:07:17
 */
public class MQConsumerService implements InitializingBean{
    
    private static final Logger log  = LoggerFactory.getLogger(MQConsumerService.class);
    
    private static Properties properties = new Properties();
    private static Consumer consumer;
    
    
    @Override
    public void afterPropertiesSet() throws Exception {
//        // TODO Auto-generated method stub
//        properties.put(PropertyKeyConst.ConsumerId, "CID_autoanswer");// 您在MQ控制台创建的Producer ID
//        properties.put(PropertyKeyConst.AccessKey, "02tHrZPmtBEzEWqY");// 鉴权用AccessKey，在阿里云服务器管理控制台创建
//        properties.put(PropertyKeyConst.SecretKey, "eXmNTOctyDlprsGTYy0gXQgexWZNI0");// 鉴权用SecretKey，在阿里云服务器管理控制台创建
//
//        if (consumer == null) {
//            consumer  = ONSFactory.createConsumer(properties);
//        }
//
//        consumer.subscribe(RecordConstants.MQ_TOPIC, "*", new MessageListener() {
//            @Override
//            public Action consume(Message message, ConsumeContext context) {
//                // TODO Auto-generated method stub
//                if(message == null){
//                    return Action.CommitMessage;
//                }
//                //打电话的消息
//                if(message.getTag().equals(RecordConstants.MAKE_A_CALL)){
//                    try{
//                        RecordHandleThread thread = new RecordHandleThread();
//                        thread.setModel(message.getTag());
//                        thread.setRetry(10);
//                        thread.start();
//                        return Action.CommitMessage;
//
//                    } catch(Exception e){
//                        System.out.println(e);
//                        return Action.ReconsumeLater;
//                    }
//                }
//                return Action.CommitMessage;
//            }
//        });
//
//        consumer.start();
//        registerShutdownHook();
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



//    class RecordHandleThread extends Thread{
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
//                    //检查文件有没有生成
//                    String result = RecordIOManagerImpl.checkFileExistence(url);
//                    if(StringUtil.isBlank(result)){
//                        Thread.sleep(2 * 60 * 1000);
//                        continue;
//                    }
//                    System.out.print(result);
//                    //文件生成了之后,下载下来并且上传至OSS
//                    RecordIOManagerImpl.downLoadFileFromURL(result,model,"/records/" + model );
//                    //上传到OSS
//                    File file = new File("/records/" + model);
//                    String ossPath = OSSManagerImpl.uploadFile(file);
//
//                    //文件上传之后调用语音识别接口
//                    List<String> transList = TranscriptionImpl.translation(ossPath);
//                    //调用语音识别接口,获取语音识别的结果
//
//                }catch (Exception e){
//                    System.out.println(e);
//                }
//
//            }
//        }
//    }


}
