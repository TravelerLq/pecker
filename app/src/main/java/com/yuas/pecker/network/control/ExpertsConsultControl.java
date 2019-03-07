package com.yuas.pecker.network.control;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yuas.pecker.bean.ProApplyReuqestBean;
import com.yuas.pecker.bean.pecker.ExpertsBean;
import com.yuas.pecker.bean.pecker.ProfessorStateBean;
import com.yuas.pecker.bean.pecker.RequestProblemBean;
import com.yuas.pecker.constant.AppConstant;
import com.yuas.pecker.exception.IApiException;
import com.yuas.pecker.network.api.ExpertsConsultApi;
import com.yuas.pecker.utils.Loger;


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
 * Created by liqing on 2018/12/19.
 * 专家咨询
 */

public class ExpertsConsultControl extends BaseControl {


    public Observable<List<ExpertsBean>> getAllExperts(String pageSize, String pageCount) {
        Observable observable = null;
        Retrofit retrofit = builderJsonType(3);

        ExpertsConsultApi api = retrofit.create(ExpertsConsultApi.class);
        Map<String, Object> params = new HashMap<>();

        params.put("pageSize", pageSize);
        params.put("pageCount", pageCount);

        String paramsStr = JSON.toJSONString(params);
        observable = api.getAllExperts(paramsStr);


        return observable.map(new Function<Response<String>, List<ExpertsBean>>() {
            @Override
            public List<ExpertsBean> apply(Response<String> stringResponse) throws Exception {
                int code = stringResponse.code();
                if (code == 200) {
                    String body = stringResponse.body();
                    JSONObject jsonObject = JSON.parseObject(body);
                    String status = jsonObject.getString(AppConstant.JSON_RESPONSE);
                    if (AppConstant.JSON_SUCCESS.equals(status)) {
                        JSONObject jsonObject1 = jsonObject.getJSONObject(AppConstant.JSON_DATA);
                        List<ExpertsBean> list = JSON.parseArray(jsonObject1.getString("list"), ExpertsBean.class);

                        return list;
                    }
                }


                throw new IApiException("专家列表获取", "失败" + code);
            }
        });

    }

    //上传多图（刚开始以为上传多图，最后发现是一张一张上传的)
    public Observable<String> uploadMultiPics(List<File> files, final UploadProgressListener uploadProgressListener) {

        Retrofit retrofit = builderJsonType(3);
        ExpertsConsultApi api = retrofit.create(ExpertsConsultApi.class);
        MultipartBody.Builder builder = new MultipartBody.Builder();
        for (File file : files) {
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), file);
            builder.addFormDataPart("uploadFile", file.getName(), requestBody);

        }
        builder.setType(MultipartBody.FORM);
        MultipartBody multipartBody = builder.build();
        return api.uploadMultiPics(multipartBody);
    }


    public Observable<String> uploadSinglePic(File file, final UploadProgressListener uploadProgressListener) {

        Retrofit retrofit = builderJsonType(3);
        ExpertsConsultApi api = retrofit.create(ExpertsConsultApi.class);
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("uploadFile", file.getName(), requestBody);
        Loger.e("路径图片 " + file.getName());
        return api.uploadSinglePic(part);
    }


    public Observable<Boolean> submitProblems(RequestProblemBean bean) {
        Retrofit retrofit = builderJsonType(3);
        ExpertsConsultApi api = retrofit.create(ExpertsConsultApi.class);
        String jsonParams = JSON.toJSONString(bean);
        Observable<Response<String>> observable = api.submit(jsonParams);
        return observable.map(new Function<Response<String>, Boolean>() {
            @Override
            public Boolean apply(Response<String> stringResponse) throws Exception {
                int code = stringResponse.code();
                if (code == 200) {
                    String body = stringResponse.body();
                    JSONObject jsonObject = JSON.parseObject(body);
                    String response = jsonObject.getString("response");
                    if (response.equals(AppConstant.JSON_SUCCESS)) {
                        return true;
                    }

                }
                throw new IApiException("问题提交", "失败" + code);
            }
        });
    }

    //专家申请前的状态查询

    public Observable<ProfessorStateBean> getProfessorState(String id) {
        Retrofit retrofit = builderJsonType(3);
        ExpertsConsultApi api = retrofit.create(ExpertsConsultApi.class);
        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        String params = JSON.toJSONString(map);
        Observable<Response<String>> observable = api.getProfessorState(params);

        return observable.map(new Function<Response<String>,ProfessorStateBean>(){

            @Override
            public ProfessorStateBean apply(Response<String> stringResponse) throws Exception {

                String body =stringResponse.body();
                JSONObject jsonObject =JSON.parseObject(body);
                String response =jsonObject.getString(AppConstant.JSON_RESPONSE);
                if(response.equals(AppConstant.JSON_SUCCESS)){
                    ProfessorStateBean bean =JSON.parseObject(
                            jsonObject.getString(AppConstant.JSON_DATA),ProfessorStateBean.class);
                    return bean;
                }
                throw new IApiException("认证状态查询",jsonObject.getString(AppConstant.JSON_MESSAGE));

            }
        });
    }

    //申请
    public Observable<Boolean> apply(ProApplyReuqestBean bean) {
        Retrofit retrofit = builderJsonType(3);
        ExpertsConsultApi api = retrofit.create(ExpertsConsultApi.class);
        String jsonParams = JSON.toJSONString(bean);
        Observable<Response<String>> observable = api.apply(jsonParams);
        return observable.map(new Function<Response<String>, Boolean>() {
            @Override
            public Boolean apply(Response<String> stringResponse) throws Exception {
                int code = stringResponse.code();
                if (code == 200) {
                    String body = stringResponse.body();
                    JSONObject jsonObject = JSON.parseObject(body);
                    String response = jsonObject.getString("response");
                    if (response.equals(AppConstant.JSON_SUCCESS)) {
                        return true;
                    }
                }
                throw new IApiException("申请", "失败" + code);
            }
        });
    }

    public Observable<Boolean> order(RequestProblemBean bean) {
        Retrofit retrofit = builderJsonType(3);
        ExpertsConsultApi api = retrofit.create(ExpertsConsultApi.class);
        String jsonParams = JSON.toJSONString(bean);
        Observable<Response<String>> observable = api.submit(jsonParams);
        return observable.map(new Function<Response<String>, Boolean>() {
            @Override
            public Boolean apply(Response<String> stringResponse) throws Exception {
                int code = stringResponse.code();
                if (code == 200) {
                    String body = stringResponse.body();
                    JSONObject jsonObject = JSON.parseObject(body);
                    String response = jsonObject.getString("response");
                    if (response.equals(AppConstant.JSON_SUCCESS)) {
                        return true;
                    }

                }
                throw new IApiException("生成订单", "失败" + code);
            }
        });
    }


}

