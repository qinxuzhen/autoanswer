package com.alibaba.webx.autoanswer.app1.model.manager.Impl;

import com.alibaba.webx.autoanswer.app1.util.ConfigHelper;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.annotation.Resource;

/**
 * Created by jingdao on 16/8/13.
 */
@Component("ossManager")
public class OSSManagerImpl {

	@Resource
	ConfigHelper configHelper;
    
	private static OSSClient client = null;


    @SuppressWarnings("deprecation")
	public String uploadFile(File file) {
    	if(client== null)
    		client = new OSSClient(configHelper.getACCESS_ID(), configHelper.getACCESS_KEY());
    	client.setEndpoint(configHelper.getEND_POINT());
        String fileKey = file.getName();
        try {
//            ensureBucket(bucketName);
            ObjectMetadata objectMeta = new ObjectMetadata();
            objectMeta.setContentLength(file.length());
            InputStream input = new FileInputStream(file);
            client.putObject(configHelper.getRECORD_BUCKET(), fileKey, input, objectMeta);
        } catch (Exception e) {
//            log.error("Failed to uploadFile", e);
            System.out.println("upload file error");
            return null;
        }
        String ossAdr =  "http://"+configHelper.getRECORD_BUCKET() + "." + configHelper.getEND_POINT() + "/"
        		+ fileKey;
        return ossAdr;
    }

    public static void main(String args[]) {
        OSSManagerImpl ossManager = new OSSManagerImpl();
         File file=new File("/Users/admin/code/testoss.txt");
         String key=ossManager.uploadFile(file);
    }

}
