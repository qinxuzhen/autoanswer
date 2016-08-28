/*
 * Copyright 2016 Alibaba.com All right reserved. This software is the
 * confidential and proprietary information of Alibaba.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Alibaba.com.
 */
package com.alibaba.webx.autoanswer.app1.model.manager.Impl;

import com.alibaba.webx.autoanswer.app1.util.ConfigHelper;
import com.taobao.api.Constants;
import com.taobao.api.internal.util.StringUtils;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 类RecordFileManagerImpl.java的实现描述：TODO 类实现描述 
 * @author xuzhen.qxz 2016年7月16日 下午8:21:57
 */
@Component("recordFileManager")
public class RecordFileManagerImpl {
    
	@Resource
	ConfigHelper configHelper;
	
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    
    public static String signTopRequest(Map<String, String> params, String secret, String signMethod) throws IOException {
        // 第一步：检查参数是否已经排序
        String[] keys = params.keySet().toArray(new String[0]);
        Arrays.sort(keys);
     
        // 第二步：把所有参数名和参数值串在一起
        StringBuilder query = new StringBuilder();
        if (Constants.SIGN_METHOD_MD5.equals(signMethod)) {
            query.append(secret);
        }
        for (String key : keys) {
            String value = params.get(key);
            if (StringUtils.areNotEmpty(key, value)) {
                query.append(key).append(value);
            }
        }
     
        // 第三步：使用MD5/HMAC加密
        byte[] bytes;
        if (Constants.SIGN_METHOD_HMAC.equals(signMethod)) {
            bytes = encryptHMAC(query.toString(), secret);
        } else {
            query.append(secret);
            bytes = encryptMD5(query.toString());
        }
     
        // 第四步：把二进制转化为大写的十六进制
        return byte2hex(bytes);
    }
     
    public static byte[] encryptHMAC(String data, String secret) throws IOException {
        byte[] bytes = null;
        try {
            SecretKey secretKey = new SecretKeySpec(secret.getBytes(Constants.CHARSET_UTF8), "HmacMD5");
            Mac mac = Mac.getInstance(secretKey.getAlgorithm());
            mac.init(secretKey);
            bytes = mac.doFinal(data.getBytes(Constants.CHARSET_UTF8));
        } catch (GeneralSecurityException gse) {
            throw new IOException(gse.toString());
        }
        return bytes;
    }
     
    public static byte[] encryptMD5(String data) throws IOException {
        return encryptMD5(data.getBytes(Constants.CHARSET_UTF8));
    }
    
    public static byte[] encryptMD5(byte[] data){
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return md.digest(data);
    }
     
    public static String byte2hex(byte[] bytes) {
        StringBuilder sign = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() == 1) {
                sign.append("0");
            }
            sign.append(hex.toUpperCase());
        }
        return sign.toString();
    }

    public String getUrl(String model){
        Map<String,String> map = new HashMap<String,String>();
        map.put("method", "alibaba.aliqin.fc.voice.record.geturl");
        map.put("app_key", configHelper.getDaYuAppKey());
        map.put("timestamp", sdf.format(new Date()));
        map.put("v","2.0");
        map.put("sign_method", "md5");
        map.put("call_id", model);
        StringBuilder sb = new StringBuilder();
        boolean flag = false;
        for(Map.Entry<String, String> entry: map.entrySet()){
            if(flag){
                sb.append("&");
            }
            sb.append(entry.getKey());
            sb.append("=");
            try {
                sb.append(URLEncoder.encode(map.get(entry.getKey()),"UTF-8"));
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            flag = true;
        }
        System.out.println(sb.toString());
        String sign = null;
        try {
            sign = signTopRequest(map,configHelper.getDaYuappSecret(),"md5");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
//        System.out.println(sign);


        String url = "http://gw.api.taobao.com/router/rest?"
                + sb.toString()
                + "&sign=" + sign;

    /*    System.out.println("**********************************************");
        System.out.println(url);*/

        return url;
    }
    
    public static void main(String[] args){

//        System.out.print(getUrl("102240431397^100266512301"));
        
        /**
         * <alibaba_aliqin_fc_voice_record_geturl_response>
            <result>
            <code>0</code>
            <model>
            dayudown/defaultScreen.do?event_submit_do_Down_Video_Resource=anything&action=daYuGetResourceAction&_input_charset=UTF-8&fileName=Freeswitch_RU_7e515b72-94db-4869-8fae-e75c9ecda2fa_record.wav&partnerId=99111710001&token=R%2FyunkBD5rFmx67FtN3Ds4N%2FU%2BY94TJ0XS2FyCCABZXEQDSlddKWf9f%2FPSc%2FL4xsHEok2m%2Bf%2FvDALwm0wjW7QrThDq0J8SKpzHhz%2BGpgLjEzDsoLjEAdfje%2BN0B1MiTmQODJdSys8nYS0CQC7gSWmQ%3D%3D
            </model>
            <msg>正常</msg>
            <success>true</success>
            </result>
            <request_id>iv22c72tdqf9</request_id>
            </alibaba_aliqin_fc_voice_record_geturl_response>
            <!-- top010184078143.et2 -->
         */
    }
}
