package com.yuas.pecker.network.control;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yuas.pecker.bean.pecker.ProfessorCertificateRequestBean;
import com.yuas.pecker.bean.pecker.VipRegisterRequestBean;
import com.yuas.pecker.constant.AppConstant;
import com.yuas.pecker.exception.IApiException;
import com.yuas.pecker.network.api.VipLoginApi;


import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Yso on 2017/11/13.
 */

public class VipLoginControl extends BaseControl {

    public Observable<Boolean> vipLogin(String userName, String userPsw) {
        Observable observable = null;
        Retrofit retrofit = builderJsonType(3);

        VipLoginApi api = retrofit.create(VipLoginApi.class);
        Map<String, Object> params = new HashMap<>();

        params.put("username", userName);
        params.put("userpwd", userPsw);

        String paramsStr = JSON.toJSONString(params);
        observable = api.vipLogin(paramsStr);


        return observable.map(new Function<Response<String>, Boolean>() {
            @Override
            public Boolean apply(Response<String> stringResponse) throws Exception {
                int code = stringResponse.code();
                if (code == 200) {
                    String body = stringResponse.body();
                    JSONObject jsonObject = JSON.parseObject(body);
                    String status = jsonObject.getString(AppConstant.JSON_RESPONSE);
                    if (AppConstant.JSON_SUCCESS.equals(status)) {
//                        JSONObject jsonObject1 = jsonObject.getJSONObject(AppConstant.JSON_DATA);
//                        List<ExpertsBean> list = JSON.parseArray(jsonObject1.getString("list"), ExpertsBean.class);

                        return true;
                    }
                }


                throw new IApiException("vip登录", "失败" + code);
            }
        });

    }


    public Observable<Boolean> vipRegister(VipRegisterRequestBean registerRequestBean) {
        Observable observable = null;
        Retrofit retrofit = builderJsonType(3);
        VipLoginApi api = retrofit.create(VipLoginApi.class);
        String paramsStr = JSON.toJSONString(registerRequestBean);
        observable = api.vipRegister(paramsStr);

        return observable.map(new Function<Response<String>, Boolean>() {
            @Override
            public Boolean apply(Response<String> stringResponse) throws Exception {
                int code = stringResponse.code();
                if (code == 200) {
                    String body = stringResponse.body();
                    JSONObject jsonObject = JSON.parseObject(body);
                    String status = jsonObject.getString(AppConstant.JSON_RESPONSE);
                    if (AppConstant.JSON_SUCCESS.equals(status)) {
//                        JSONObject jsonObject1 = jsonObject.getJSONObject(AppConstant.JSON_DATA);
//                        List<ExpertsBean> list = JSON.parseArray(jsonObject1.getString("list"), ExpertsBean.class);

                        return true;
                    }
                }


                throw new IApiException("vip注册", "失败" + code);
            }
        });

    }



    public Observable<Boolean> professorCertificate(ProfessorCertificateRequestBean responseBean) {
        Observable observable = null;
        Retrofit retrofit = builderJsonType(3);
        VipLoginApi api = retrofit.create(VipLoginApi.class);
        String paramsStr = JSON.toJSONString(responseBean);
        observable = api.professorCertificate(paramsStr);

        return observable.map(new Function<Response<String>, Boolean>() {
            @Override
            public Boolean apply(Response<String> stringResponse) throws Exception {
                int code = stringResponse.code();
                if (code == 200) {
                    String body = stringResponse.body();
                    JSONObject jsonObject = JSON.parseObject(body);
                    String status = jsonObject.getString(AppConstant.JSON_RESPONSE);
                    if (AppConstant.JSON_SUCCESS.equals(status)) {
//                        JSONObject jsonObject1 = jsonObject.getJSONObject(AppConstant.JSON_DATA);
//                        List<ExpertsBean> list = JSON.parseArray(jsonObject1.getString("list"), ExpertsBean.class);

                        return true;
                    }
                }


                throw new IApiException("professor认证", "失败" + code);
            }
        });

    }
}
