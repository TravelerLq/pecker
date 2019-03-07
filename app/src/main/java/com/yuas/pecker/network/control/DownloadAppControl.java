package com.yuas.pecker.network.control;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yuas.pecker.application.App;
import com.yuas.pecker.bean.download.CheckUpdateResponse;
import com.yuas.pecker.bean.download.FileCallBack;
import com.yuas.pecker.bean.download.FileSubscriber;
import com.yuas.pecker.constant.AppConstant;
import com.yuas.pecker.exception.IApiException;
import com.yuas.pecker.network.api.DownloadApi;
import com.yuas.pecker.utils.Loger;
import com.yuas.pecker.utils.Utils;
import com.yuas.pecker.view.widget.SimpleToast;


import java.util.HashMap;
import java.util.Map;


import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/11/20 0020.
 */

public class DownloadAppControl extends BaseControl {

    // using
    public rx.Observable<ResponseBody> downloadApp1(String url) {
        Retrofit retrofit = buildDownloadRetrofit();
        DownloadApi downloadApi = retrofit.create(DownloadApi.class);
        return downloadApi.download1(url);
    }




    public void downloadApp(Context context, String url, final FileCallBack<ResponseBody> callBack) {
        Loger.i("DownloadAppControl");
        Retrofit retrofit = buildDownloadRetrofit();
        DownloadApi downloadApi = retrofit.create(DownloadApi.class);
        downloadApi.download(url)
                .subscribeOn(Schedulers.io())//请求网络 在调度者的io线程
                .observeOn(Schedulers.io()) //指定线程保存文件
                .doOnNext(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody body) {
                        Loger.i("downloadApp = =downloadApp" + Thread.currentThread().getId());
                        try {
                            callBack.saveFile(body);
                        } catch (Exception e) {
                            e.printStackTrace();
                            callBack.onError(e);
                        }
                    }
                }).observeOn(AndroidSchedulers.mainThread()) //指定线程保存文件
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Loger.i("eeeeeeeeeeeeeeee" + Thread.currentThread().getId());
                        SimpleToast.ToastMessage("下载接口异常");
                    }
                }).observeOn(AndroidSchedulers.mainThread()) //在主线程中更新ui
                .subscribe(new FileSubscriber<ResponseBody>(App.getInstance(), callBack));
    }

    //subscribeOn(Schedulers.io()) .observeOn().subscribe()

    public void downloadFile(Context context, String url, final FileCallBack<ResponseBody> callBack) {
        Loger.i("DownloadAppControl");
        Retrofit retrofit = buildDownloadRetrofit();
        DownloadApi downloadApi = retrofit.create(DownloadApi.class);
        downloadApi.download(url)
                .subscribeOn(Schedulers.io())//请求网络 在调度者的io线程
                .observeOn(Schedulers.io()) //指定线程保存文件
                .doOnNext(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody body) {
                        Loger.i("downloadFile = =downloadApp" + Thread.currentThread().getId());
                        try {
                            callBack.saveFile(body);
                        } catch (Exception e) {
                            e.printStackTrace();
                            callBack.onError(e);
                        }
                    }
                }).observeOn(AndroidSchedulers.mainThread()) //指定线程保存文件
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Loger.i("downlaodFile...error..." + Thread.currentThread().getId());
                        SimpleToast.ToastMessage("下载接口异常");
                    }
                }).observeOn(AndroidSchedulers.mainThread()) //在主线程中更新ui
                .subscribe(new FileSubscriber<ResponseBody>(App.getInstance(), callBack));
    }



    //BGAUpdateDownloadApp
//    public Observable<File> downLoadApkFile(final String apkUrl, String version){
//
//       return Observable.defer(new Func0<Observable<InputStream>>(){
//
//           @Override
//           public Observable<InputStream> call() {
//               try {
//                   return Observable.just((buildDownloadBGARetrofit().create(DownloadApi.class)).downloadBGATest(apkUrl).execute().body().byteStream());
//               } catch (IOException e) {
//                   return Observable;
//               }
//           }
//       });
//    }



    public Observable<CheckUpdateResponse> getNewAppVersion() {
        String version = Utils.getVersionCode();
        String versionName = Utils.getVersionName();
        Loger.e("---version " + version + "versionName" + versionName);

        //  String version = "0.9.8";
        Retrofit retrofit = builderRetrofitWithHeader();
        DownloadApi downloadApi = retrofit.create(DownloadApi.class);
        Map<String, String> map = new HashMap<>();
        map.put("version", versionName);
        Observable<String> observable = downloadApi.checkUpdate(map);
        return observable.map(new Function<String, CheckUpdateResponse>() {
            @Override
            public CheckUpdateResponse apply(@NonNull String s) throws Exception {
                Loger.i("getNewAppVersion = " + s);
                JSONObject jsonObject = JSON.parseObject(s);
                if (200 == jsonObject.getIntValue(AppConstant.JSON_CODE)) {
                    JSONObject jsonData = JSON.parseObject(jsonObject.getString(AppConstant.JSON_DATA));
                    CheckUpdateResponse checkUpdateResponse = JSON.parseObject(jsonData.getString("ver"), CheckUpdateResponse.class);
                    return checkUpdateResponse;
                }
                throw new IApiException("获取更新版本信息", jsonObject.getString(AppConstant.JSON_MESSAGE));
            }
        });
    }

}
