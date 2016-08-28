package com.alibaba.webx.autoanswer.app1.model;

import java.sql.Date;

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
	 * 开始时间
	 */
	
	private Date startTime;
	
	/**
	 * 结束时间
	 */
	private Date endTime;
	
	/**
	 * 主叫人号码
	 */
	private String numberCaller;
	
	/**
	 * 被叫人号码
	 */
	private String numberCallee;
	
	/**
	 * 通话时长
	 */
	private Long timeLength;
	
	/**
	 * 通话录音存储地址
	 */
	private String attachPath;
	
	/**
	 * 翻译文本地址
	 */
	private String textPath;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the startTime
	 */
	public Date getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the endTime
	 */
	public Date getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return the numberCaller
	 */
	public String getNumberCaller() {
		return numberCaller;
	}

	/**
	 * @param numberCaller the numberCaller to set
	 */
	public void setNumberCaller(String numberCaller) {
		this.numberCaller = numberCaller;
	}

	/**
	 * @return the numberCallee
	 */
	public String getNumberCallee() {
		return numberCallee;
	}

	/**
	 * @param numberCallee the numberCallee to set
	 */
	public void setNumberCallee(String numberCallee) {
		this.numberCallee = numberCallee;
	}

	/**
	 * @return the timeLength
	 */
	public Long getTimeLength() {
		return timeLength;
	}

	/**
	 * @param timeLength the timeLength to set
	 */
	public void setTimeLength(Long timeLength) {
		this.timeLength = timeLength;
	}

	/**
	 * @return the attachPath
	 */
	public String getAttachPath() {
		return attachPath;
	}

	/**
	 * @param attachPath the attachPath to set
	 */
	public void setAttachPath(String attachPath) {
		this.attachPath = attachPath;
	}
	
	/**
	 * @return the textPath
	 */
	public String getTextPath() {
		return textPath;
	}

	/**
	 * @param textPath the textPath to set
	 */
	public void setTextPath(String textPath) {
		this.textPath = textPath;
	}
}
