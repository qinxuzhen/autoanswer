package com.alibaba.webx.autoanswer.app1.common;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Result<T> implements Serializable {
	
	private static final long serialVersionUID = -7983973666463164408L;
	
	private boolean success;
	
	private String errorCode;
	
	private String errorMsg;
	
	private T data;
	
	private int total = 0;
	
	private Map<String, Object> otherModel = new HashMap<String, Object>();
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public Map<String, Object> getOtherModel() {
		return otherModel;
	}
	public void setOtherModel(Map<String, Object> otherModel) {
		this.otherModel = otherModel;
	}
	
	/**
	 * 泛型方式创建Result比较易用，建议不要直接使用默认的构造方法
	 */
	public static <T> Result<T> create() {
		return new Result<T>();
	}

	/**
	 * 根据返回的数据构建Result
	 */
	public static <T> Result<T> createWithData(T data) {
		Result<T> result = Result.create();
		result.setData(data);
		return result;
	}
	
	@Override
	public String toString() {
		return "Result [success=" + success + ", errorCode=" + errorCode
				+ ", errorMsg=" + errorMsg + ", data=" + data + ", total="
				+ total + ", otherModel=" + otherModel + "]";
	}
	
	/**
	 * 返回拼接好的错误信息，方便客户端做日志
	 * @return
	 */
	public String getFormatErrorDesc(){
		return " success:" + this.isSuccess() + ", errorCode:" + this.getErrorCode() + ", errorMsg:" + this.getErrorMsg();
	}
	
}

