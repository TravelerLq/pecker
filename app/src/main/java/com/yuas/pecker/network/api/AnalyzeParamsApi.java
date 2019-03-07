package com.yuas.pecker.network.api;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

/**
 * Created by liqing on 2018/12/19.
 *
 */

public interface AnalyzeParamsApi {

    //企业所得税申报表
    @Multipart
    @POST("warningface/exc/read")
    Observable<String> uploadTaxApply(@Part MultipartBody.Part file, @PartMap Map<String, RequestBody> map);

    //budget 新增item
    //http://192.168.1.111:8080/shuidao/quotas/add
    @POST("warningface/exc/save")
    Observable<Response<String>> saveExcels(@Body String body);


    @POST("warningface/fxd/save")
    Observable<Response<String>> saveComfigParams(@Body String body);
}
