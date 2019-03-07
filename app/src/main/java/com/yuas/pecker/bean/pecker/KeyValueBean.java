package com.yuas.pecker.bean.pecker;

import com.yuas.pecker.bean.BaseBean;

public class KeyValueBean extends BaseBean {

    String keyModel;
    String keyValue;

    public String getKeyModel() {
        return keyModel;
    }

    public void setKeyModel(String keyModel) {
        this.keyModel = keyModel;
    }

    public String getKeyValue() {
        return keyValue;
    }

    public void setKeyValue(String keyValue) {
        this.keyValue = keyValue;
    }

    public KeyValueBean(String keyModel, String keyValue) {
        this.keyModel = keyModel;
        this.keyValue = keyValue;
    }
}
