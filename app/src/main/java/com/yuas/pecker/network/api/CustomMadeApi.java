package com.yuas.pecker.network.api;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by liqing on 2018/12/19.
 */

public interface CustomMadeApi {

    //获取行业分类
    @GET("warningface/indt/qry")
    Observable<Response<String>> getIndustry();

    //提价私人定制申请
    @POST("warningface/pszd/add")
    Observable<Response<String>> submitCustomMade(@Body String body);

}
