/*
 * Copyright 2016 Alibaba.com All right reserved. This software is the
 * confidential and proprietary information of Alibaba.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Alibaba.com.
 */
package com.alibaba.webx.autoanswer.app1.module.screen.form;

import com.alibaba.citrus.turbine.Context;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @author xuzhen.qxz 2016.07.24 6:31:19
 */
public class Check {
    
    public void execute(Context context){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, 1);
        context.put("today",sdf.format(cal.getTime()));
        cal.add(Calendar.DAY_OF_YEAR, -7);
        context.put("lastweek", sdf.format(cal.getTime()));
        System.out.println("In checkForm");
    }
}
