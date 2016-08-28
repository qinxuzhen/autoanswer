package com.alibaba.webx.autoanswer.app1.common.service;

import com.aliyun.openservices.ons.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import java.util.Properties;

/*
 * Copyright 2016 Alibaba.com All right reserved. This software is the
 * confidential and proprietary information of Alibaba.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Alibaba.com.
 */
/**
 * 类MQProducer.java的实现描述：TODO 类实现描述
 * 
 * @author xuzhen.qxz 2016年7月21日 下午10:22:27
 */

public class MQProducerService implements InitializingBean {

    private static final Logger log        = LoggerFactory.getLogger(MQProducerService.class);
    private  Properties   properties = new Properties();
    private  static Producer     producer;

    /*
     * (non-Javadoc)
     * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        // TODO Auto-generated method stub
        properties.put(PropertyKeyConst.ProducerId, "PID_autoanswer");// 您在MQ控制台创建的Producer ID
        properties.put(PropertyKeyConst.AccessKey, "02tHrZPmtBEzEWqY");// 鉴权用AccessKey，在阿里云服务器管理控制台创建
        properties.put(PropertyKeyConst.SecretKey, "eXmNTOctyDlprsGTYy0gXQgexWZNI0");
        producer = ONSFactory.createProducer(properties);
        producer.start();

        registerShutdownHook();
    }

    public static SendResult send(String topic, String tag, String msKey, String body) {
        try {
            Message msg = new Message(topic,// topic
                                      tag,// tag
                                      msKey,//
                                      body.getBytes("utf-8"));// body

            SendResult sendResult = producer.send(msg);
            return sendResult;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * jvm hook
     */
    public static void registerShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {

            public void run() {
                try {
                    producer.shutdown();
                } catch (Exception e) {
                    log.error("MetaProducer shutdown error", e.getMessage());
                }
            }
        }));

    }
}
