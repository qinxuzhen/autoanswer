/*
 * Copyright 2016 Alibaba.com All right reserved. This software is the
 * confidential and proprietary information of Alibaba.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Alibaba.com.
 */
package com.alibaba.webx.autoanswer.app1.module.screen.multievent;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.TurbineRunData;
import com.alibaba.citrus.turbine.util.TurbineUtil;
import com.alibaba.webx.autoanswer.app1.common.PaginationResult;
import com.alibaba.webx.autoanswer.app1.dao.RecordDAO;
import com.alibaba.webx.autoanswer.app1.model.RecordDO;
import com.alibaba.webx.autoanswer.app1.model.manager.VoiceRecordOpenSeDAO;

/**
 * ��RecordService.java��ʵ��������TODO ��ʵ������ 
 * @author xuzhen.qxz 2016��7��24�� ����10:17:15
 */
public class RecordService {

	@Autowired
	private HttpServletRequest request;
	@Resource
	RecordDAO recordDAO;
	@Resource
	VoiceRecordOpenSeDAO voiceRecordOpenSeDAO;
	
	DateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
	//��ȡʱ����ڵĺ��м�¼�б�
    public PaginationResult<List<RecordDO>> doGetRecordList(Context context){

    	final TurbineRunData rundata = TurbineUtil.getTurbineRunData(request);
	    final ParameterParser parameterParser = rundata.getParameters();
	    
		Date startDate = parameterParser.getDate("startDate", format);
		Date endDate = parameterParser.getDate("endDate", format);
		String calling_num = parameterParser.getString("callerNum");
		String called_num = parameterParser.getString("calleeNum");
		
		int page = parameterParser.getInt("start");
		int size = parameterParser.getInt("limit");
		
		Map<String, Object> params = new HashMap<String, Object>();
	    params.put("startDate", startDate);
	    params.put("endDate", endDate);
	    params.put("callingNumber", calling_num);
	    params.put("calledNumber",called_num);
	    
	    params.put("start", page * size);
	    params.put("limit", size);
	    
	    List<RecordDO> queryResult = recordDAO.queryByRecordDO(params);
	    Integer total = recordDAO.count(params);
	    
        PaginationResult<List<RecordDO>> result = new PaginationResult<List<RecordDO>>();
        result.setData(queryResult);
        result.setSuccess(true);
        result.setTotal(total);
        return result;
    }
    
    /**
     * ����modelId �����������ı�����
     * @param context
     * @return
     */
    public String doGetFullText(Context context){
    	final TurbineRunData rundata = TurbineUtil.getTurbineRunData(request);
	    final ParameterParser parameterParser = rundata.getParameters();
	    String modelId = parameterParser.getString("id");
	    Map<String, Object> params = new HashMap<String, Object>();
	    params.put("id", modelId);
	    List<RecordDO> queryResult = recordDAO.queryByRecordDO(params);
	    if(!CollectionUtils.isEmpty(queryResult))
	    	return queryResult.get(0).getVoiceText();
    	return null;
    }
    
    /**
     * �����ı��еĹؼ��ֲ�ѯ�绰��¼
     * @param context
     * @return
     */
    public PaginationResult<List<RecordDO>> doGetSearchList(Context context){
    	PaginationResult<List<RecordDO>> result = new PaginationResult<List<RecordDO>>();
    	
    	final TurbineRunData rundata = TurbineUtil.getTurbineRunData(request);
	    final ParameterParser parameterParser = rundata.getParameters();
	    
		String keyWord = parameterParser.getString("keyWord");
		
		int start = parameterParser.getInt("start");
		int size = parameterParser.getInt("limit");
		if(StringUtils.isEmpty(keyWord)) return result;
		
		List<RecordDO> queryResult = voiceRecordOpenSeDAO.queryModelIdByText(keyWord);
		if(CollectionUtils.isEmpty(queryResult)) return result;
		
		List<RecordDO> subList = queryResult.subList(start, Math.min(start + size, queryResult.size()));
		
		result.setTotal(queryResult.size());
        result.setData(subList);
        result.setSuccess(true);
        return result;
    }
}
