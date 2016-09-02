package com.alibaba.webx.autoanswer.app1.dao;

import java.util.List;
import java.util.Map;

import com.alibaba.webx.autoanswer.app1.model.RecordDO;

public interface RecordDAO {

	/**
	 * 增加一条录音记录
	 * @param record
	 * @return
	 */
	public Long addRecord(RecordDO recordDO);
	
	/**
	 * 按照条件筛选通话录音
	 */
	public List<RecordDO> queryByRecordDO(Map<String, Object> params);
	
	/**
	 * 将翻译结果写到数据库
	 * @param params
	 * @return
	 */
	public Integer upDateRecord(Map<String, Object> params);
}
