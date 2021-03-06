package com.yuas.pecker.network.api;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * Created by liqing on 2018/12/19.
 */

public interface ExaminationApi {

    //体验体检
    @POST("/bar/androidAnalysis")
    Observable<Response<String>> submitExamination(@Body String string);


    //houtaimodel/authority/initposition?frontOrBack=1&id=1
    @GET("authority/initposition")
    Observable<Response<String>> getAthorityByPosition(@QueryMap Map<String, Object> map);


    //ahthority配置提交
    @POST("shuidao/quotas/all")
    Observable<Response<String>> submitAuthority(@QueryMap Map<String, Object> map);


}
