package com.alibaba.webx.autoanswer.app1.dao.Impl;

import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Component;

import com.alibaba.webx.autoanswer.app1.dao.RecordDAO;
import com.alibaba.webx.autoanswer.app1.model.RecordDO;


@SuppressWarnings("deprecation")
//@Component("recordDAO")
public class RecordDAOImpl extends SqlMapClientDaoSupport implements RecordDAO {

	@SuppressWarnings("deprecation")
	@Override
	public Long addRecord(RecordDO recordDO) {
		return (Long)getSqlMapClientTemplate().insert("record.insert",recordDO);
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	@Override
	public List<RecordDO> queryByRecordDO(Map<String, Object> params) {
		return (List<RecordDO>)getSqlMapClientTemplate().queryForList("record.query",params);
	}
	

}
