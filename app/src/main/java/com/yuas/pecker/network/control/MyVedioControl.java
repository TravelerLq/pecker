package com.yuas.pecker.network.control;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yuas.pecker.bean.pecker.MyvedioResponseBean;
import com.yuas.pecker.constant.AppConstant;
import com.yuas.pecker.exception.IApiException;
import com.yuas.pecker.network.api.MyVedioApi;


import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MyVedioControl extends BaseControl {

     public Observable<MyvedioResponseBean> getVedios(String userId, int pageCount, int pageSize){

        Retrofit retrofit =builderRetrofitByType(3);
        MyVedioApi api =retrofit.create(MyVedioApi.class);
        Map<String,Object > map =new HashMap<>();
        map.put("userId",userId);
        map.put("pageCount",pageCount);
        map.put("pageSize",pageSize);

        Observable<Response<String>> observable =api.getVedios(map);

        return observable.map(new Function<Response<String>, MyvedioResponseBean>() {
            @Override
            public MyvedioResponseBean apply(Response<String> stringResponse) throws Exception {
                String body= stringResponse.body();
                JSONObject jsonObject =JSON.parseObject(body);
                String response =jsonObject.getString(AppConstant.JSON_RESPONSE);
                if(response.equals(AppConstant.JSON_SUCCESS)){
                    MyvedioResponseBean bean = JSON.parseObject(jsonObject.getString(AppConstant.JSON_DATA),
                            MyvedioResponseBean.class);
                    return bean;
                }
                throw new IApiException("获取我的视频",jsonObject.getString(AppConstant.JSON_MESSAGE));
            }
        });
    }


    //delete vedio by vedioId

  public   Observable<Boolean> deleteVedio(int videoId){

        Retrofit retrofit =builderRetrofitByType(3);
        MyVedioApi api =retrofit.create(MyVedioApi.class);
        Map<String,Object > map =new HashMap<>();
        map.put("videoId",videoId);
        Observable<Response<String>> observable =api.deleteVedio(map);
        return observable.map(new Function<Response<String>, Boolean>() {
            @Override
            public Boolean apply(Response<String> stringResponse) throws Exception {
                String body= stringResponse.body();
                JSONObject jsonObject =JSON.parseObject(body);
                String response =jsonObject.getString(AppConstant.JSON_RESPONSE);
                if(response.equals(AppConstant.JSON_SUCCESS)){

                    return true;
                }
                throw new IApiException("删除我的视频",jsonObject.getString(AppConstant.JSON_MESSAGE));
            }
        });
    }

}
