package com.yuas.pecker.network.api;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by liqing on 2018/12/19.
 * 教授咨询
 */

public interface ExpertsConsultApi {

    //获取教授专家
    @POST("warningface/profe/qryAll")
    Observable<Response<String>> getAllExperts(@Body String body);

    //上传图片（多图 -暂时不用）

    @POST("warningface/img/add")
    Observable<String> uploadMultiPics(@Body MultipartBody multipartBody);

    //上传图片（单图）
    @Multipart
    @POST("warningface/img/add")
    Observable<String> uploadSinglePic(@Part MultipartBody.Part image);

    //生成订单
    @POST("warningface/order")
    Observable<Response<String>> order(@Body String body);

    //提价问题
    @POST("warningface/ques/add")
    Observable<Response<String>> submit(@Body String body);

    //申请前状态查询

    @POST("warningface//profe/sto")
    Observable<Response<String>> getProfessorState(@Body String body);

    // 申请

    @POST("warningface/profe/reg")
    Observable<Response<String>> apply(@Body String body);
}
