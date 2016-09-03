package com.alibaba.webx.autoanswer.app1.model.manager.Impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.webx.autoanswer.app1.model.RecordDO;
import com.alibaba.webx.autoanswer.app1.model.manager.VoiceRecordOpenSeDAO;
import com.alibaba.webx.autoanswer.app1.util.ConfigHelper;
import com.aliyun.opensearch.CloudsearchClient;
import com.aliyun.opensearch.CloudsearchSearch;
import com.aliyun.opensearch.object.KeyTypeEnum;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component("voiceRecordOpenSeDAO")
public class VoiceRecordDAOImp implements VoiceRecordOpenSeDAO {

	@Resource
	ConfigHelper configHelper;

	@Override
	public List<RecordDO> queryModelIdByText(String voiceTextFrag) {
		Map<String,Object> opts = new HashMap<String,Object>();
		List<RecordDO> result = new ArrayList<RecordDO>();
		CloudsearchClient client;
		try {
			client = new CloudsearchClient(configHelper.getACCESS_ID(), configHelper.getACCESS_KEY(),
					configHelper.getOPENSEARCH_HOST(), opts, KeyTypeEnum.ALIYUN);
			CloudsearchSearch search = new CloudsearchSearch(client);
			search.setFormat("json");
			search.addIndex(configHelper.getOPENSEARCH_APP_NAME());
			search.setQueryString("voice_text:'" + voiceTextFrag + "'");
			
			JSONObject queryResult = (JSONObject) JSON.parse(search.search());
			JSONArray voiceRecordArray = JSON.parseArray(((JSONObject)JSON.parse(queryResult.getString("result"))).getString("items"));
			//System.out.println("### queryDO = " + voiceRecordArray);
			if(voiceRecordArray != null) {
				for(Object voiceRecord : voiceRecordArray) {
					JSONObject recordObj = ((JSONObject)voiceRecord);
					RecordDO recordDO = new RecordDO();
					recordDO.setId(recordObj.getLong("id"));
					recordDO.setCalledNumber(recordObj.getString("called_number"));
					recordDO.setCallingNumber(recordObj.getString("calling_number"));
					recordDO.setCallTime(recordObj.getDate("call_time"));
					recordDO.setModelId(recordObj.getString("model_id"));
					recordDO.setVoiceFileUrl(recordObj.getString("voice_file_url"));
					recordDO.setVoiceText(recordObj.getString("voice_text"));
					result.add(recordDO);
				}
			} 
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}

}
