package com.yuas.pecker.network.api;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by liqing on 2018/12/19.
 * vip login
 */

public interface VipLoginApi {


    //vip注册
    @POST("warningface/user/reg")
    Observable<Response<String>> vipRegister(@Body String body);

    //vip登录
    @POST("warningface/user/reg")
    Observable<Response<String>> vipLogin(@Body String body);

    //教授认证申请
    @POST("warningface/profe/reg")
    Observable<Response<String>> professorCertificate(@Body String body);


}
