package com.yuas.pecker.network.api;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

/**
 * Created by liqing on 2018/12/19.
 */

public interface AlarmingAnalyzeApi {

    //导入企业所得税年度申报表 /bar/read
    @Multipart
    @POST("warningface/bar/read")
    Observable<String> uploadIncomeTax(@Part MultipartBody.Part file, @PartMap Map<String, RequestBody> map);

    //导入增值税申报表
    //http://192.168.1.111:8080/shuidao/quotas/add
    @Multipart
    @POST("warningface/bar/readss")
    Observable<String> uploadVat(@Part MultipartBody.Part file, @PartMap Map<String, RequestBody> map);

    //3导入资产负债表
    @Multipart
    @POST("warningface/bar/assetLiability")
    Observable<String> uploadAssetsBalance(@Part MultipartBody.Part file, @PartMap Map<String, RequestBody> map);


    //4 导入利润表
    @Multipart
    @POST("warningface/bar/profit")
    Observable<String> uploadProfit(@Part MultipartBody.Part file, @PartMap Map<String, RequestBody> map);

    //保存数据
    @POST("warningface/bar/analysis")
    Observable<Response<String>> saveAlarmAnalyzeData(@Body String body);

    //设置缓存
    @Headers("Cache-Control: public,max-age:3600")
    @POST("warningface/bar/analysis")
    Observable<Response<String>>testCache(@Body String body);


}
