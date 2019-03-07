package com.yuas.pecker.network.control;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import com.yuas.pecker.bean.pecker.QueAnswerBean;
import com.yuas.pecker.bean.pecker.QueAnswerDetailResponseBean;
import com.yuas.pecker.bean.pecker.RequestAppendQueBean;
import com.yuas.pecker.constant.AppConstant;
import com.yuas.pecker.exception.IApiException;
import com.yuas.pecker.network.api.QueAnswerApi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by liqing on 2018/12/19.
 * 问答相关
 */

public class QuesAnswerControl extends BaseControl {

    //获取问题列表

    public Observable<List<QueAnswerBean>> getAllQueAnswe(String pageSize, String pageCount, String uid) {
        Observable observable = null;
        Retrofit retrofit = builderJsonType(3);

        QueAnswerApi api = retrofit.create(QueAnswerApi.class);
        Map<String, Object> params = new HashMap<>();

        params.put("pageSize", pageSize);
        params.put("pageCount", pageCount);
        params.put("uid", uid);

        String paramsStr = JSON.toJSONString(params);
        observable = api.getAllQueAnswer(paramsStr);

        return observable.map(new Function<Response<String>, List<QueAnswerBean>>() {
            @Override
            public List<QueAnswerBean> apply(Response<String> stringResponse) throws Exception {
                int code = stringResponse.code();
                if (code == 200) {
                    String body = stringResponse.body();
                    JSONObject jsonObject = JSON.parseObject(body);
                    String status = jsonObject.getString(AppConstant.JSON_RESPONSE);
                    if (AppConstant.JSON_SUCCESS.equals(status)) {
                        // JSONObject jsonObject1 = jsonObject.getJSONObject(AppConstant.JSON_DATA);
                        List<QueAnswerBean> list = JSON.parseArray(jsonObject.getString("data"), QueAnswerBean.class);

                        return list;
                    }
                }


                throw new IApiException("问答列表获取", "失败" + code);
            }
        });

    }

    //问答详情获取　
    public Observable<QueAnswerDetailResponseBean> getQueAnsweDetail(String uid, String questionId) {

        /*
        {"id":"14",
"uid":"24"
}
         */
        Retrofit retrofit = builderJsonType(3);
        QueAnswerApi answerApi = retrofit.create(QueAnswerApi.class);
        Map<String, Object> map = new HashMap<>();
        map.put("id", questionId);
        map.put("uid", uid);
        String jsonStr = JSON.toJSONString(map);
        Observable observable = answerApi.getQueAnswerDetail(jsonStr);


        return observable.map(new Function<Response<String>, QueAnswerDetailResponseBean>() {
            @Override
            public QueAnswerDetailResponseBean apply(Response<String> stringResponse) throws Exception {
                int code = stringResponse.code();

                if (code == 200) {
                    String body = stringResponse.body();
                    JSONObject jsonObject = JSON.parseObject(body);
                    String response = jsonObject.getString(AppConstant.JSON_RESPONSE);
                    if (response.equals(AppConstant.JSON_SUCCESS)) {
                        QueAnswerDetailResponseBean bean = JSON.parseObject(jsonObject.getString("data"),
                                QueAnswerDetailResponseBean.class);
                        return bean;
                    }

                }
                throw new IApiException("问答详情获取失败", "");


            }
        });
    }


    //追加问题
    public Observable<Boolean> appendQuestion(RequestAppendQueBean bean) {
        Retrofit retrofit = builderJsonType(3);
        QueAnswerApi api = retrofit.create(QueAnswerApi.class);
        String jsonParams = JSON.toJSONString(bean);
        Observable<Response<String>> observable = api.appendQuestion(jsonParams);
        return observable.map(new Function<Response<String>, Boolean>() {
            @Override
            public Boolean apply(Response<String> stringResponse) throws Exception {
                int code = stringResponse.code();
                if (code == 200) {
                    String body = stringResponse.body();
                    JSONObject jsonObject = JSON.parseObject(body);
                    String response = jsonObject.getString("response");
                    if (response.equals(AppConstant.JSON_SUCCESS)) {
                        return true;
                    }

                }
                throw new IApiException("问题追加", "失败" + code);
            }
        });
    }


}

