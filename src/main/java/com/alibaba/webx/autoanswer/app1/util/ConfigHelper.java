package com.alibaba.webx.autoanswer.app1.util;


/**
 * Created by jingdao on 16/8/13.
 */
public class ConfigHelper {
    private String daYuAppKey;
    private String daYuappSecret;
    private String daYuappNum;
    private String daYuURL;
    
    private String ACCESS_ID;
    private String ACCESS_KEY;
    private String RECORD_BUCKET;
    private String END_POINT;
    

    public String getDaYuAppKey() {
        return daYuAppKey;
    }

    public void setDaYuAppKey(String daYuAppKey) {
        this.daYuAppKey = daYuAppKey;
    }

    public String getDaYuappSecret() {
        return daYuappSecret;
    }

    public void setDaYuappSecret(String daYuappSecret) {
        this.daYuappSecret = daYuappSecret;
    }

    public String getDaYuappNum() {
        return daYuappNum;
    }

    public void setDaYuappNum(String daYuappNum) {
        this.daYuappNum = daYuappNum;
    }
    
    

	public String getDaYuURL() {
		return daYuURL;
	}

	public void setDaYuURL(String daYuURL) {
		this.daYuURL = daYuURL;
	}

	public String getACCESS_ID() {
		return ACCESS_ID;
	}

	public void setACCESS_ID(String aCCESS_ID) {
		ACCESS_ID = aCCESS_ID;
	}

	public String getACCESS_KEY() {
		return ACCESS_KEY;
	}

	public void setACCESS_KEY(String aCCESS_KEY) {
		ACCESS_KEY = aCCESS_KEY;
	}

	public String getRECORD_BUCKET() {
		return RECORD_BUCKET;
	}

	public void setRECORD_BUCKET(String rECORD_BUCKET) {
		RECORD_BUCKET = rECORD_BUCKET;
	}

	public String getEND_POINT() {
		return END_POINT;
	}

	public void setEND_POINT(String eND_POINT) {
		END_POINT = eND_POINT;
	}
    
    
}
