package com.alibaba.webx.autoanswer.app1.common;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.sun.istack.internal.NotNull;


public class Query<T> extends Pagination implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 8059501262453809347L;
	
	/**
	 * 时间范围是否为修改时间
	 * false为修改时间，true为创建时间
	 */
	private Boolean isDurationModified = false;
	
	private Date startDate;
    
    private Date endDate;
    /**
     * 对象中没有的值 可以放到这里
     * 具体：例如库存表的 types,storeCodes等等
     */
    private Map<String, Object> otherValue = new HashMap<String, Object>();
    
    @NotNull
    private T queryObject;

    private String orderField;

    private boolean desc = true;

    public T getQueryObject() {
        return queryObject;
    }

    public void setQueryObject(T queryObject) {
        this.queryObject = queryObject;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getOrderField() {
        if (StringUtils.isNotBlank(orderField)) {
        	return orderField;
        } else {
        	return null;
        }
    }
    
    public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
    public Boolean getIsDurationModified() {
		return isDurationModified;
	}

	public void setIsDurationModified(Boolean isDurationModified) {
		this.isDurationModified = isDurationModified;
	}

	public void setOrderField(String orderField) {
        this.orderField = orderField;
    }

    public boolean isDesc() {
        return desc;
    }

    /**
     * @return 升降序排序方式的sql关键字
     */
    public String getDesc() {
    	return desc ? "DESC" : "ASC";
    }

    public void setDesc(boolean desc) {
        this.desc = desc;
    }

    /**
     * @deprecated 语义上要将排序的字段名和升降序拆分开
     */
    @Deprecated
    public String getOrderByClause() {
        if (orderField != null && !orderField.equals("")) {
            return orderField + (desc ? " DESC" : " ");
        } else {
            return null;
        }
    }

    public void copy(@SuppressWarnings("rawtypes") Query src, @SuppressWarnings("rawtypes") Query tgt) {

        tgt.setPageSize(src.getPageSize());
        tgt.setOrderField(src.getOrderField());
        tgt.setPageIndex(src.getPageIndex());

    }

    public Map<String, Object> getOtherValue() {
        return otherValue;
    }

    public void setOtherValue(Map<String, Object> otherValue) {
        this.otherValue = otherValue;
    }

    public void addOtherValue(String key, Object value) {
        if(otherValue==null){
            otherValue = new HashMap<String, Object>();
        }
        otherValue.put(key, value);
    }
}