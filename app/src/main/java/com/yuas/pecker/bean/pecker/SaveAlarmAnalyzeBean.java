package com.yuas.pecker.bean.pecker;

import com.yuas.pecker.bean.BaseBean;

import java.util.List;

public class SaveAlarmAnalyzeBean extends BaseBean {


    /**
     * uid : 24
     * list : ["1546420736900","1546420736900","1546420736900","1546420736900","1546420736900","1546420736900","1546420736900"]
     * yearStr : 2018
     */

    private int uid;
    private String type="";
    private List<String> list;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }
}
