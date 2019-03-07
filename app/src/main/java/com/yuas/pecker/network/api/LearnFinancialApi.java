package com.yuas.pecker.network.api;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;

/**
 * Created by liqing on 2019/2/18.
 * 学习财务报表相关
 */

public interface LearnFinancialApi {
//http://192.168.31.86:8080/warningface/statement/all

    //获取报表名称
    @GET("warningface/statement/all")
    Observable<Response<String>> getReportsName();

    //获取二级数据（报表内的词条）
    @GET("warningface/statement/lemma/all")
    Observable<Response<String>> getLearnSecondData(@QueryMap Map<String, Object> map);

    //词条解释详情
    @GET("warningface/statement/lemma/info")
    Observable<Response<String>> getWordExplainDetail(@QueryMap Map<String, Object> map);

    //上传视频
    @Multipart
    @POST("warningface/upload/video")
    Observable<String> uplaodVideo(@Part MultipartBody.Part part);

    //提交词条和关联视频信息
    @POST("warningface/statement/video/save")
    Observable<Response<String>> submitWordVideo(@Body String body);


}
