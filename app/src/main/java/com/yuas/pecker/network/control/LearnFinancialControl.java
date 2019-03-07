package com.yuas.pecker.network.control;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yuas.pecker.bean.pecker.LearnFinancialBean;
import com.yuas.pecker.bean.pecker.WordExplainBean;
import com.yuas.pecker.bean.pecker.WordVideoBean;
import com.yuas.pecker.constant.AppConstant;
import com.yuas.pecker.exception.IApiException;
import com.yuas.pecker.network.api.LearnFinancialApi;


import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by liqing on 18/7/11.
 * 财税学习 Control
 */

public class LearnFinancialControl extends BaseControl {


    //获得所有报表
    public Observable<List<LearnFinancialBean>> getReportsName() {

        Retrofit retrofit = builderJsonType(3);
        LearnFinancialApi api = retrofit.create(LearnFinancialApi.class);
        Observable<Response<String>> observable = api.getReportsName();


        return observable.map(new Function<Response<String>, List<LearnFinancialBean>>() {

            @Override
            public List<LearnFinancialBean> apply(Response<String> stringResponse) throws Exception {
                String body = stringResponse.body();
                JSONObject jsonObject = JSON.parseObject(body);
                String respose = jsonObject.getString(AppConstant.JSON_RESPONSE);
                if (respose.equals(AppConstant.JSON_SUCCESS)) {
                    List<LearnFinancialBean> list = JSON.parseArray(jsonObject.getString(AppConstant.JSON_DATA), LearnFinancialBean.class);
                    return list;
                }
                throw new IApiException("财务报表名称获取", jsonObject.getString(AppConstant.JSON_MESSAGE));

            }

        });
    }

    public Observable<String> getWords(String id, int pageCount, int pageSize) {

        Retrofit retrofit = builderRetrofitByType(3);
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("statementId", id);
        paramMap.put("pageCount", pageCount);
        paramMap.put("pageSize", pageSize);
        LearnFinancialApi api = retrofit.create(LearnFinancialApi.class);
        Observable<Response<String>> observable = api.getLearnSecondData(paramMap);
        return observable.map(new Function<Response<String>, String>() {

            @Override
            public String apply(Response<String> stringResponse) throws Exception {
                String body = stringResponse.body();
                JSONObject jsonObject = JSON.parseObject(body);
                String response = jsonObject.getString(AppConstant.JSON_RESPONSE);
                if (response.equals(AppConstant.JSON_SUCCESS)) {
                    String data = jsonObject.getString(AppConstant.JSON_DATA);
                    return data;
                }
                throw new IApiException("词条获取", jsonObject.getString(AppConstant.JSON_MESSAGE));
            }
        });
    }

    //获得词条解释详情

    public Observable<WordExplainBean> getWordExplainBean(String id) {

        Retrofit retrofit = builderRetrofitByType(3);
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("lemmaId", id);

        LearnFinancialApi api = retrofit.create(LearnFinancialApi.class);
        Observable<Response<String>> observable = api.getWordExplainDetail(paramMap);
        return observable.map(new Function<Response<String>, WordExplainBean>() {

            @Override
            public WordExplainBean apply(Response<String> stringResponse) throws Exception {
                String body = stringResponse.body();
                JSONObject jsonObject = JSON.parseObject(body);
                String response = jsonObject.getString(AppConstant.JSON_RESPONSE);
                if (response.equals(AppConstant.JSON_SUCCESS)) {
                    WordExplainBean bean = JSON.parseObject(jsonObject.getString(AppConstant.JSON_DATA), WordExplainBean.class);
                    return bean;
                }
                throw new IApiException("词条解释获取", jsonObject.getString(AppConstant.JSON_MESSAGE));
            }
        });
    }

    //（词条详情里的）上传视频

    public Observable<String> uploadVideo(File file,
                                          final UploadProgressListener uploadProgressListener) {
        Retrofit retrofit = builderUploadPicRetrofitWithType(3);
        LearnFinancialApi api = retrofit.create(LearnFinancialApi.class);

        RequestBody requestBody = RequestBody.create(MediaType.parse("video/*"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("videoFile", file.getName(), requestBody);

        return api.uplaodVideo(part);
    }


    //提交词条和相关视频数据

    public Observable<Boolean> submitWordVideo(WordVideoBean wordVideoBean) {

        Retrofit retrofit = builderJsonType(3);
        LearnFinancialApi api = retrofit.create(LearnFinancialApi.class);
        String params = JSON.toJSONString(wordVideoBean);

        Observable<Response<String>> observable = api.submitWordVideo(params);
        return observable.map(new Function<Response<String>, Boolean>() {
            @Override
            public Boolean apply(Response<String> stringResponse) throws Exception {
                String body = stringResponse.body();
                JSONObject jsonObject = JSON.parseObject(body);
                String msg = jsonObject.getString(AppConstant.JSON_MESSAGE);
                String response = jsonObject.getString(AppConstant.JSON_RESPONSE);
                if (response.equals(AppConstant.JSON_SUCCESS)) {
                    return true;
                }
                throw new IApiException("词条视频提交", msg);
            }
        });
    }


}
