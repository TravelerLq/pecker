package com.yuas.pecker.network.control;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yuas.pecker.bean.pecker.ExaminationBean;
import com.yuas.pecker.constant.AppConstant;
import com.yuas.pecker.exception.IApiException;
import com.yuas.pecker.network.api.ExaminationApi;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by liqing on 2018/12/19.
 */

public class ExaminationControl extends BaseControl {


    public Observable<ArrayList<String>> submitExamination(ExaminationBean bean) {
        Observable observable = null;
        Retrofit retrofit = builderRetrofit();
        ExaminationApi api = retrofit.create(ExaminationApi.class);
        Map<String, Object> params = new HashMap<>();

        params.put("parameterA", bean.getParameterA());
        params.put("parameterB", bean.getParameterB());
        params.put("parameterC", bean.getParameterC());
        params.put("parameterD", bean.getParameterD());
        params.put("parameterE", bean.getParameterE());
        params.put("parameterF", bean.getParameterF());
        params.put("parameterG", bean.getParameterG());
        params.put("parameterH", bean.getParameterH());
        params.put("parameterI", bean.getParameterI());
        params.put("parameterJ", bean.getParameterJ());
        params.put("parameterK", bean.getParameterK());
        params.put("parameterL", bean.getParameterL());
        params.put("parameterLM", bean.getParameterM());

      String paramsStr = JSON.toJSONString(params);
        observable = api.submitExamination(paramsStr);


        return observable.map(new Function<Response<String>, ArrayList<String>>() {
            @Override
            public ArrayList<String> apply(Response<String> stringResponse) throws Exception {
                int code =stringResponse.code();
                if(code==200){
                    String body = stringResponse.body();
                    JSONObject jsonObject = JSON.parseObject(body);
                    String status = jsonObject.getString(AppConstant.JSON_RESPONSE);
                    if (AppConstant.JSON_SUCCESS.equals(status)) {
                        JSONObject jsonObject1 = jsonObject.getJSONObject(AppConstant.JSON_DATA);
                        List<String> listString = JSON.parseArray(jsonObject1.getString("resList"), String.class);
                        ArrayList<String> arrayList = new ArrayList<>(listString.size());
                        arrayList.addAll(listString);

                        return arrayList;
                    }
                }


                throw new IApiException("体验体检","失败"+code);
            }
        });

    }

}

