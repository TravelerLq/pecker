package com.yuas.pecker.network.api;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by Yso on 2017/11/13.ring
 */

public interface CodeApi {

//    @GET("api/PersonalInfo/BindingCard")
//    Observable<String> bindCard(@QueryMap Map<String, String> map);

//    @POST("api/PersonalInfo/PDAUpdatePassword")
//    Observable<String> updatePassword(@QueryMap Map<String, String> map);
//
//    @GET("api/PersonalInfo/PesonalInformation")
//    Observable<String> personalInformation(@QueryMap Map<String, String> map);
//
//    //yuanshen/public/login
//    @POST("yuanshen/public/login")
//    Observable<String> newLogin(@Body String str);
//
//    //Response 的返回
//    //http://192.168.1.111:8080/shuidao/tokens/login
//    @POST("shuidao/tokens/login")
//    Observable<Response<String>> loginResponse(@Body String str);

    //填写邀请码
    @POST("shuidao/notoken/verqr")
    Observable<Response<String>> submitCode(@Body String body);
//http://192.168.31.109:8080/shuidao/users/joincom
    //填写邀请码
    @POST("shuidao/users/joincom")
    Observable<Response<String>> submitAgainCode(@Body String body);

    //获取邀请码
    @GET("/shuidao/users/qr")
    Observable<Response<String>> getCode();


}
