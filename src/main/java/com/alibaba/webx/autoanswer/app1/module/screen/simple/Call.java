/*
 * Copyright 2016 Alibaba.com All right reserved. This software is the
 * confidential and proprietary information of Alibaba.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Alibaba.com.
 */
package com.alibaba.webx.autoanswer.app1.module.screen.simple;

import java.io.File;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alibaba.citrus.util.StringUtil;
import com.alibaba.webx.autoanswer.app1.dao.RecordDAO;
import com.alibaba.webx.autoanswer.app1.model.RecordDO;
import com.alibaba.webx.autoanswer.app1.model.manager.Impl.OSSManagerImpl;
import com.alibaba.webx.autoanswer.app1.model.manager.Impl.RecordFileManagerImpl;
import com.alibaba.webx.autoanswer.app1.model.manager.Impl.RecordIOManagerImpl;
import com.alibaba.webx.autoanswer.app1.model.manager.Impl.TranslationImpl;
import com.alibaba.webx.autoanswer.app1.util.ConfigHelper;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcVoiceNumDoublecallRequest;
import com.taobao.api.response.AlibabaAliqinFcVoiceNumDoublecallResponse;


public class Call {

//    @Resource
//    MQProducerService mqProducerService;
    @Resource
    ConfigHelper configHelper;
    @Resource
    OSSManagerImpl ossManager;
    @Resource
    RecordFileManagerImpl recordFileManager;
    @Resource
    TranslationImpl translation;
    @Resource
    RecordDAO recordDAO;

    @Autowired
    private HttpServletResponse response;
    private static final String url = "http://gw.api.taobao.com/router/rest";

    
    public void execute(@Param("fromNum") String fromNum,@Param("toNum") String toNum ) throws Exception {
//        TaobaoClient client = new DefaultTaobaoClient(url, configHelper.getDaYuAppKey(), configHelper.getDaYuappSecret());
//        AlibabaAliqinFcVoiceNumDoublecallRequest req = new AlibabaAliqinFcVoiceNumDoublecallRequest();
//        req.setSessionTimeOut("120");
//        req.setExtend("12345");
//        req.setCallerNum(fromNum);
//        req.setCallerShowNum(configHelper.getDaYuappNum());
//        req.setCalledNum(toNum);
//        req.setCalledShowNum(configHelper.getDaYuappNum());
//        AlibabaAliqinFcVoiceNumDoublecallResponse rsp = client.execute(req);
//        System.out.println(rsp.getBody());
//		String model = rsp.getResult().getModel();
		String model = "102789385954^100288645120";
        //呼叫信息插入数据库
        RecordDO recordDO = new RecordDO();
        recordDO.setCalledNumber(toNum);
        recordDO.setCallingNumber(fromNum);
        recordDO.setModelId(model);
        recordDAO.addRecord(recordDO);
        
        RecordHandleThread recordHandleThread = new RecordHandleThread();
        recordHandleThread.setRetry(20);
//        recordHandleThread.setModel("102789385954^100288645120");
        recordHandleThread.setModel(model);
        recordHandleThread.start();


        //{"alibaba_aliqin_fc_voice_num_doublecall_response":{"result":{"err_code":"0","model":"102501079459^100279263324","success":true},"request_id":"10fbxqp0fkt26"}}
//        if(rsp.isSuccess())
//            mqProducerService.send(RecordConstants.MQ_TOPIC,RecordConstants.MAKE_A_CALL,rsp.getResult().getModel(),rsp.getBody());
    	
        System.out.println("Call");
    }

    class RecordHandleThread extends Thread{
        private String model;
        private int retry;

        public void setModel(String model){
            this.model = model;
        }

        public void setRetry(int retry){
            this.retry = retry;
        }

        @Override
        public void run() {
            String url =  recordFileManager.getUrl(model);
            System.out.println(url);
            int count = 0;
            while(true){
            	System.out.println("In record thread count:" + count);
            	
                if(count > retry) break;
                try {
                	//检查语音文件是否生成
                    String result = RecordIOManagerImpl.checkFileExistence(url);
                    if(StringUtil.isBlank(result)){
                        Thread.sleep(60000);
                        continue;
                    }
                    System.out.print(result);
                    String urlStr = configHelper.getDaYuURL() + result;
                    String fileName = model.replace('^', '0') + ".wav";
                    //文件已经生成的情况下，下载文件
                    File fileDownload = RecordIOManagerImpl.downLoadFileFromURL(urlStr,fileName,"../records");
                    if(fileDownload == null) continue;
                    //将文件上传到OSS
                    String ossPath = ossManager.uploadFile(fileDownload);
                    System.out.println("ossPath:" + ossPath);
                    //将数据更新到数据库
                    Integer updateRes = translation.write2DB(ossPath, model);
//                    File translateFile = translation.write2File(ossPath,"../transTxt",model.replace('^', '0') + ".txt");
//                    if(updateRes != null)
//                    	ossManager.uploadFile(translateFile);
                    if(updateRes == 0) continue;
                    break;
                }catch (Exception e){
                    System.out.println(e);
                }
                count ++;
                
            }
        }
    }
    
}
