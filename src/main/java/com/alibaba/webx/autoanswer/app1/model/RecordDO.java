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
	 * 呼叫ID
	 */
	private String modelId;
	
	/**
	 * 创建时间
	 */
	private Date gmtCreate;
	
	/**
	 * 主叫人号码
	 */
	private String numberCaller;
	
	/**
	 * 被叫人号码
	 */
	private String numberCallee;
	
	
	/**
	 * 录音文件名称
	 */
	private String fileName;
	
	
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
	 * @return the gmtCreate
	 */
	public Date getGmtCreate() {
		return gmtCreate;
	}

	/**
	 * @param gmtCreate the startTime to set
	 */
	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
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
	
	
    public String getModelId() {
        return modelId;
    }

    
    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    
    public String getFileName() {
        return fileName;
    }

    
    public void setFileName(String fileName) {
        this.fileName = fileName;
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
