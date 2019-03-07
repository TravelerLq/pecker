package com.yuas.pecker.model;

import android.util.Log;

import com.yuas.pecker.bean.UserResponseBean;
import com.yuas.pecker.model.imodel.ILoginModel;
import com.yuas.pecker.model.imodel.INetCallback;
import com.yuas.pecker.network.NetWork;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class LoginModel implements ILoginModel {
    @Override
    public void login(String name, String psw, final INetCallback<UserResponseBean> callback) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("lemmaId", 1);
        NetWork.getInstance("1").getNetApi().login(paramMap)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {

                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        if (callback != null)
                            callback.startRequest();
                        Log.e("--", "--doOnSubscribe.accept");
                    }
                })

                .observeOn(AndroidSchedulers.mainThread());


    }
}
