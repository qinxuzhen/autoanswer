/*
 * Copyright 2016 Alibaba.com All right reserved. This software is the
 * confidential and proprietary information of Alibaba.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Alibaba.com.
 */
package com.alibaba.webx.autoanswer.app1.common.service;

import java.io.File;
import java.io.FileWriter;
import java.util.Properties;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import com.alibaba.citrus.util.StringUtil;
import com.alibaba.webx.autoanswer.app1.common.RecordConstants;
import com.alibaba.webx.autoanswer.app1.dao.RecordDAO;
import com.alibaba.webx.autoanswer.app1.model.manager.Impl.OSSManagerImpl;
import com.alibaba.webx.autoanswer.app1.model.manager.Impl.RecordFileManagerImpl;
import com.alibaba.webx.autoanswer.app1.model.manager.Impl.RecordIOManagerImpl;
import com.alibaba.webx.autoanswer.app1.model.manager.Impl.TranslationImpl;
import com.alibaba.webx.autoanswer.app1.util.ConfigHelper;
import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Consumer;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.PropertyKeyConst;

/**
 * @author xuzhen.qxz 2016.07.21 11:07:17
 */
public class MQConsumerService implements InitializingBean {

	private static final Logger log = LoggerFactory
			.getLogger(MQConsumerService.class);

//	private static File logs= new File("/home/qinxuzhen/log/logs.txt");
//	private static FileWriter fw ;
	@Resource
	ConfigHelper configHelper;
	@Resource
	OSSManagerImpl ossManager;
	@Resource
	RecordFileManagerImpl recordFileManager;
	@Resource
	TranslationImpl translation;
	@Resource
	RecordDAO recordDAO;

	private static Properties properties = new Properties();
	private static Consumer consumer;

	private String CID;
	private String AccessKey;
	private String SecretKey;

	@Override
	public void afterPropertiesSet() throws Exception {
		// // TODO Auto-generated method stub
		properties.put(PropertyKeyConst.ConsumerId, CID);//
		properties.put(PropertyKeyConst.AccessKey, AccessKey);//
		properties.put(PropertyKeyConst.SecretKey, SecretKey);//

		consumer = ONSFactory.createConsumer(properties);
		
		
		consumer.subscribe(RecordConstants.MQ_TOPIC, "*",
				new MessageListener() {
					@Override
					public Action consume(Message message,
							ConsumeContext context) {
						// TODO Auto-generated method stub
						if (message == null) {
							return Action.CommitMessage;
						}
						// 收到打电话的消息
						if (message.getTag()
								.equals(RecordConstants.MAKE_A_CALL)) {
							String model = message.getKey();
							String url = recordFileManager.getUrl(model);
							try {
								// 检查语音文件是否生成
								String result = RecordIOManagerImpl
										.checkFileExistence(url);

								if (StringUtil.isBlank(result)) {
									Thread.sleep(60000);
									return Action.ReconsumeLater;
								}
								//获取下载链接
								String urlStr = configHelper.getDaYuURL() + result;
								String fileName = model.replace('^', '0') + ".wav";
								
								File fileDownload = RecordIOManagerImpl.downLoadFileFromURL(urlStr, fileName,"../records");
								if(fileDownload == null) {
									Thread.sleep(60000);
									return Action.ReconsumeLater;
								}
//			                    //将文件上传到OSS
								//上传到OSS
								String ossPath = ossManager.uploadFile(fileDownload);
								//进行翻译并且更新数据库
								Integer updateRes = translation.write2DB(ossPath, model);
								if(updateRes == 0) return Action.ReconsumeLater;
							} catch (Exception e) {
								System.out.println(e);
								return Action.ReconsumeLater;
							}

							return Action.CommitMessage;
							
						} 
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
