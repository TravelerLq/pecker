package com.yuas.pecker.network.control;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yuas.pecker.constant.AppConstant;
import com.yuas.pecker.exception.IApiException;
import com.yuas.pecker.network.api.ComprehenseiveExaminationApi;


import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by liqing on 2018/12/19.
 */

public class CompreExamControl extends BaseControl {


    public Observable<Boolean> hasConfig(String userId) {
        Observable observable = null;
        Retrofit retrofit = builderJsonType(3);
        ComprehenseiveExaminationApi api = retrofit.create(ComprehenseiveExaminationApi.class);
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("uid", userId);
        String paramStr = JSON.toJSONString(paramMap);
        observable = api.hasConfig(paramStr);

        return observable.map(new Function<Response<String>, Boolean>() {
            @Override
            public Boolean apply(Response<String> stringResponse) throws Exception {
                String body = stringResponse.body();
                JSONObject jsonObject = JSON.parseObject(body);
                String status = jsonObject.getString(AppConstant.JSON_RESPONSE);
                if (AppConstant.JSON_SUCCESS.equals(status)) {
                    int data = jsonObject.getIntValue(AppConstant.JSON_DATA);
                    data = 2;
                    if (data == 1) {
                        return true;
                    } else {
                        return false;
                    }


                }

                throw new IApiException("体验体检", jsonObject.getString(AppConstant.JSON_MESSAGE));
            }
        });

    }

}

