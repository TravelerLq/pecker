package com.yuas.pecker.model.imodel;

import com.yuas.pecker.bean.UserResponseBean;

public interface ILoginModel {
    void login(String name, String psw, INetCallback<UserResponseBean> callback);
}
