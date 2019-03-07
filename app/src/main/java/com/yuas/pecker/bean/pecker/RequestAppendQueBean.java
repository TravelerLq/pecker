package com.yuas.pecker.bean.pecker;

import com.yuas.pecker.bean.BaseBean;

public class RequestAppendQueBean extends BaseBean {


    /**
     * msgValue : 追问问题-1
     * uid : 24
     * queId : 14
     */

    private String msgValue;
    private String uid;
    private String queId;

    public String getMsgValue() {
        return msgValue;
    }

    public void setMsgValue(String msgValue) {
        this.msgValue = msgValue;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getQueId() {
        return queId;
    }

    public void setQueId(String queId) {
        this.queId = queId;
    }
}
