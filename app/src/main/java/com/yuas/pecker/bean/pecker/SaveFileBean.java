package com.yuas.pecker.bean.pecker;

import com.yuas.pecker.bean.BaseBean;

import java.util.List;

public class SaveFileBean extends BaseBean {


    /**
     * uid : 24
     * list : ["1546420736900","1546420736900","1546420736900","1546420736900","1546420736900","1546420736900","1546420736900"]
     * yearStr : 2018
     */

    private String uid;
    private String yearStr;
    private List<String> list;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getYearStr() {
        return yearStr;
    }

    public void setYearStr(String yearStr) {
        this.yearStr = yearStr;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }
}
