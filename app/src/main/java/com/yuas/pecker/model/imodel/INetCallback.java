package com.yuas.pecker.model.imodel;


public interface INetCallback<T> {
    void getErrorCode(int code, String msg);

    void getSucess(T data);

    void netError(Throwable e);

    void startRequest();

    void endRequest();
}
