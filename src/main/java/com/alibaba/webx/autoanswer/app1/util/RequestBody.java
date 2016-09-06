package com.alibaba.webx.autoanswer.app1.util;

import java.util.*;

public class RequestBody {

    private String app_key = null; 
    private String oss_link = null; 
    private List<validTime> valid_times =null; 


    public class validTime{

        private int begin_time;
        private int end_time;
        private int channel_id;

        public int getBegin_time() {
            return begin_time;
        }

        public void setBegin_time(int begin_time) {
            this.begin_time = begin_time;
        }

        public int getEnd_time() {
            return end_time;
        }

        public void setEnd_time(int end_time) {
            this.end_time = end_time;
        }

        public int getChannel_id() {
            return channel_id;
        }

        public void setChannel_id(int channel_id) {
            this.channel_id = channel_id;
        }

    }

    public void setApp_key(String appKey){
        app_key = appKey;
    }
    public void setOss_link(String fileLine){
        oss_link = fileLine;
    }
    public void addValid_time(int beginTime,int endTime,int channelId){

        if(valid_times == null){
            valid_times = new ArrayList<validTime>();
        }

        validTime valid_time = new validTime();

        valid_time.setBegin_time(beginTime);
        valid_time.setEnd_time(endTime);
        valid_time.setChannel_id(channelId);

        valid_times.add(valid_time);
    }

    public List<validTime> getValid_times() {
        return valid_times;
    }

    public String getOss_link() {
        return oss_link;
    }

    public String getApp_key() {
        return app_key;
    }

}
