package com.alibaba.webx.autoanswer.app1.model;

import java.util.Date;


/**
 * 录音记录
 * @author xuzhen.qxz
 *
 */
public class RecordDO {
	
	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 创建时间
	 */
	private Date gmtCreate;
	
	/**
	 * 修改时间
	 */
	private Date gmtModified;
	
	/**
	 * 呼叫时间
	 */
	private Date callTime;
	

	/**
	 * 主叫人号码
	 */
	private String callingNumber;
	
	/**
	 * 被叫人号码
	 */
	private String calledNumber;
	
	/**
	 * 文本内容
	 */
	private String voiceText;
	
	/**
	 * 录音文件地址
	 */
	private String voiceFileUrl;
	
	/**
	 * 呼叫ID
	 */
	private String modelId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	public Date getCallTime() {
		return callTime;
	}

	public void setCallTime(Date callTime) {
		this.callTime = callTime;
	}

	public String getCallingNumber() {
		return callingNumber;
	}

	public void setCallingNumber(String callingNumber) {
		this.callingNumber = callingNumber;
	}

	public String getCalledNumber() {
		return calledNumber;
	}

	public void setCalledNumber(String calledNumber) {
		this.calledNumber = calledNumber;
	}

	public String getVoiceText() {
		return voiceText;
	}

	public void setVoiceText(String voiceText) {
		this.voiceText = voiceText;
	}

	public String getVoiceFileUrl() {
		return voiceFileUrl;
	}

	public void setVoiceFileUrl(String voiceFileUrl) {
		this.voiceFileUrl = voiceFileUrl;
	}

	public String getModelId() {
		return modelId;
	}

	public void setModelId(String modelId) {
		this.modelId = modelId;
	}
	
	
}
