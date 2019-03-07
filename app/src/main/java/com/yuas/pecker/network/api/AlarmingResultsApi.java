package com.yuas.pecker.network.api;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by liqing on 2018/12/19.
 * 预警结果
 */

public interface AlarmingResultsApi {


    //预警结果（列表）
    @POST("warningface/result/query")
    Observable<Response<String>> getAlarmingResults(@Body String body);


}
