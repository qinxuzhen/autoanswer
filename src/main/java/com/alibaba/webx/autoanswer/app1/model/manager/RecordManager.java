package com.alibaba.webx.autoanswer.app1.model.manager;

import java.util.List;

import com.alibaba.webx.autoanswer.app1.common.PaginationResult;
import com.alibaba.webx.autoanswer.app1.model.RecordDO;

/**
 * 录音记录表Manager
 * @author xuzhen.qxz
 *
 */
public interface RecordManager {

	/**
	 * 增加一条录音记录
	 * @param record
	 * @return
	 */
	public Long addRecord(RecordDO recordDO);
	
	/**
	 * 筛选通话录音
	 */
	public PaginationResult<List<RecordDO>> queryByRecordDO(RecordDO recordDO);
}
