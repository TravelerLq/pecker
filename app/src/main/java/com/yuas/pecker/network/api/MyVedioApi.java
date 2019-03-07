package com.yuas.pecker.network.api;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface MyVedioApi {

    @GET("warningface/statement/videos/byuser")
    Observable<Response<String>> getVedios(@QueryMap Map<String, Object> map);

    //@Query("id") int id
    @DELETE("warningface/statement/video/remove")
    Observable<Response<String>> deleteVedio(@QueryMap Map<String, Object> map);


}
