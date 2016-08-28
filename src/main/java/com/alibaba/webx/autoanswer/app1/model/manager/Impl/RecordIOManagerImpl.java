/*
 * Copyright 2016 Alibaba.com All right reserved. This software is the
 * confidential and proprietary information of Alibaba.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Alibaba.com.
 */
package com.alibaba.webx.autoanswer.app1.model.manager.Impl;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



/**
 * 类RecordIOManagerImpl.java的实现描述：TODO 类实现描述 
 * @author xuzhen.qxz 2016年7月20日 下午10:20:47
 */

public class RecordIOManagerImpl {

	static Pattern pattern = Pattern.compile("<model>(.*)</model>");
	
    /**
     * 从URL下载文件,并保存
     * @param urlStr
     * @param fileName
     * @param savePath
     * @throws IOException
     */
    public static File downLoadFileFromURL(String urlStr,String fileName,String savePath)  throws IOException{
    	
        URL url = new URL(urlStr); 
        HttpURLConnection conn = (HttpURLConnection)url.openConnection(); 
        
        //设置超时间为3秒  
        conn.setConnectTimeout(3*1000);  
        //防止屏蔽程序抓取而返回403错误  
//        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)"); 
        //得到输入流  
        InputStream inputStream = conn.getInputStream(); 
        System.out.println(conn.getResponseCode());
        System.out.println(conn.getResponseMessage());
        //获取自己数组  
        byte[] getData = readInputStream(inputStream);   
        
        //文件保存位置  
        File saveDir = new File(savePath);  
        if(!saveDir.exists()){  
            saveDir.mkdir();  
        }  
        File file = new File(saveDir+File.separator+fileName);      
        FileOutputStream fos = new FileOutputStream(file);       
        fos.write(getData);   
        if(fos!=null){  
            fos.close();    
        }  
        if(inputStream!=null){  
            inputStream.close();  
        }  
  
        
        System.out.println("info:"+url);
        System.out.println(" download success");  
        return file.getAbsoluteFile();
    }

    /**
     * 检查文件是否生成
     * 1.没有生成返回NULL
     * 2.生成返回文件链接
     * @param urlStr
     * @return
     * @throws IOException
     */
    public static String checkFileExistence(String urlStr) throws IOException{
    	
    	String result = "";
        BufferedReader in = null;
        
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();

        //设置超时间为3秒
        conn.setConnectTimeout(3*1000);
        //防止屏蔽程序抓取而返回403错误
        conn.setRequestProperty("accept", "*/*");
        conn.setRequestProperty("connection", "Keep-Alive");
        conn.setRequestProperty("user-agent",
                "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
        conn.connect();
        // 定义 BufferedReader输入流来读取URL的响应
        in = new BufferedReader(new InputStreamReader(
                conn.getInputStream(),"utf-8"));
        String line;
        while ((line = in.readLine()) != null) {
            result += line;
        }
//        System.out.println(result);
       
        Matcher matcher = pattern.matcher(result);
    	if(matcher.find()){
    		return matcher.group(1).replace("amp;", "");
    	}else{
    		return null;
    	}
    }

    /** 
     * 从输入流中获取字节数组 
     * @param inputStream 
     * @return 
     * @throws IOException 
     */  
    public static  byte[] readInputStream(InputStream inputStream) throws IOException {    
        byte[] buffer = new byte[1024];    
        int len = 0;    
        ByteArrayOutputStream bos = new ByteArrayOutputStream();    
        while((len = inputStream.read(buffer)) != -1) {    
            bos.write(buffer, 0, len);    
        }    
        bos.close();    
        return bos.toByteArray();    
    }    
}
