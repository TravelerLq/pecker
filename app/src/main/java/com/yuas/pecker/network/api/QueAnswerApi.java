package com.yuas.pecker.network.api;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by liqing on 2018/12/19.
 * 教授咨询
 */

public interface QueAnswerApi {

    //获取问答列表
    @POST("warningface/ques/qry")
    Observable<Response<String>> getAllQueAnswer(@Body String body);


    //获取问答详情
    @POST("warningface/ques/qryOne")
    Observable<Response<String>> getQueAnswerDetail(@Body String body);


    //追加问题
    @POST("warningface/excg/add")
    Observable<Response<String>> appendQuestion(@Body String body);
}
