package com.yuas.pecker.network;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

public interface NetApi {


    //登陆
    @POST("login")
    Observable<Response<String>> login(@QueryMap Map<String, Object> params);

}
