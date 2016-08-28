package com.alibaba.webx.autoanswer.app1.model.manager.Impl;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.webx.autoanswer.app1.util.ConfigHelper;
import com.alibaba.webx.autoanswer.app1.util.HttpUtil;
import com.alibaba.webx.autoanswer.app1.util.RequestBody;

@Component("translation")
public class TranslationImpl {


	@Resource
	ConfigHelper configHelper = new ConfigHelper();
	/**
	 * 服务url
	 * */
	private static String url = "https://nlsapi.aliyun.com/transcriptions";
	private static RequestBody body = new RequestBody();
	private static HttpUtil request = new HttpUtil();

	public Map<Integer, String> translation(String ossLink) throws InterruptedException{

		//设置请求参数
		body.setApp_key("nls-service-telephone8khz");
		body.setOss_link(ossLink);
		//body.addValid_time(100,2000,0);       //validtime  可选字段  设置的是语音文件中希望识别的内容,begintime,endtime以及channel
		//body.addValid_time(100,2000,1);
		//阿里云的账号和密码
		String ak_id = configHelper.getACCESS_ID();
		String ak_secret = configHelper.getACCESS_KEY();

		System.out.println("Recognize begin!");

        /*
        * 发送录音转写请求
        * **/
		String bodyString;
		bodyString = JSON.toJSONString(body);
		String postResult = HttpUtil.sendPost(url,bodyString,ak_id,ak_secret);
		System.out.println("response is:"+postResult);


        /*
        * 通过TaskId获取识别结果
        * **/
		String TaskId = JSON.parseObject(postResult).getString("id");
		String status = "RUNNING";

		Map<Integer, String> result = new TreeMap<Integer,String>();
		while (status.equals("RUNNING")){
			Thread.sleep(3000);
			String getResult = HttpUtil.sendGet(url,TaskId,ak_id,ak_secret);
			status = JSON.parseObject(getResult).getString("status");
			if(status.equals("SUCCEED")){
				List<Object> objList = JSON.parseObject(getResult).getJSONArray("result");
				for(Object obj : objList){
					Map<String, Object> map = (Map<String, Object>)obj;
					result.put(Integer.valueOf(map.get("begin_time").toString()), map.get("text").toString());
				}
			}
			System.out.println("response is:"+getResult);
		}
		return result;
	}
	
	/**
	 * 将翻译结果写到文件中
	 * @param ossLink
	 * @param savePath
	 * @param fileName
	 * @return
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	public File write2File(String ossLink,String savePath, String fileName) {
		File saveDir = new File(savePath);  
        if(!saveDir.exists()){  
            saveDir.mkdir();  
        }  
        File file = new File(saveDir+File.separator+fileName); 
        try{
        	FileWriter fw = new FileWriter(file);
            
            Map<Integer, String> result = translation(ossLink);
            
            if(result== null) return null;
            
            for(String str : result.values()){
            	fw.write(str + "\r\n");
            }
            fw.close();
        }catch(Exception e){
        	e.printStackTrace();
        }
        
        return file;
	}
	
	public static void main(String[] args) {
		TranslationImpl trans = new TranslationImpl();
		trans.configHelper.setACCESS_ID("");
		trans.configHelper.setACCESS_KEY("");
		
		String ossLink = "";
//		
//		try {
//			Map<Integer, String> TextRecord = trans.translation(ossLink);
//			System.out.println(TextRecord);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		File file = trans.write2File(ossLink,"../transTxt","record.txt");
	}
	
}
