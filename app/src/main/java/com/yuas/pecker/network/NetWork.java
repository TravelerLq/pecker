package com.yuas.pecker.network;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.yuas.pecker.constant.AppConstant;
import com.yuas.pecker.network.factory.StringConverterFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;

public class NetWork {

    private static final int SOCKET_TIMEOUT = 600;
    private static final int CONNECT_TIMEOUT = 600;


    private static NetWork instance;

    private NetApi netApi;

    public static NetWork getInstance(String type) {
        if (instance == null)
            instance = new NetWork(type);
        return instance;
    }


    private NetWork(String type) {
        //QueryMap 结构请求
        Retrofit retrofit = null;
        if (type.equals("1")) {
            retrofit = builderRetrofit();
        }


        netApi = retrofit.create(NetApi.class);
    }


    protected Retrofit builderRetrofit() {
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder()
                .connectTimeout(SOCKET_TIMEOUT, TimeUnit.SECONDS)//设置超时时间
                .readTimeout(SOCKET_TIMEOUT, TimeUnit.SECONDS)//设置读取超时时间
                .writeTimeout(SOCKET_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {

                        Request request = chain.request()
                                .newBuilder()
                                .build();
                        return chain.proceed(request);
                    }
                });//设置写入超时时间
        OkHttpClient client = builder.build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConstant.getURL())
                //这个方法必须有,这样才能返回含有数据的实体类
                .addConverterFactory(StringConverterFactory.create())
                //要使用retroift和rexjava配合使用这个方法必须有,不然会报Unable to create call adapter
                // for rx.Observable<com.ethanco.retrofit2_0test.HomeTopBean>异常
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)//整合ok
                .build();
        return retrofit;
    }

    public NetApi getNetApi() {
        return netApi;
    }
}
