package com.yuas.pecker.network.control;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yuas.pecker.bean.pecker.CustomMadeRequestBean;
import com.yuas.pecker.bean.pecker.IndustryTypeBean;
import com.yuas.pecker.constant.AppConstant;
import com.yuas.pecker.exception.IApiException;
import com.yuas.pecker.network.api.CustomMadeApi;
import com.yuas.pecker.utils.Loger;


import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by liqing on 18/7/11.
 * 私人定制 Control
 */

public class CustomMadeControl extends BaseControl {


    //获取行业分类

    public Observable<List<IndustryTypeBean>> getIndustryList() {
        Retrofit retrofit = builderRetrofitByType(3);


        CustomMadeApi api = retrofit.create(CustomMadeApi.class);
        Observable<Response<String>> observable = api.getIndustry();
        return observable.map(new Function<Response<String>, List<IndustryTypeBean>>() {

            @Override
            public List<IndustryTypeBean> apply(Response<String> stringResponse) throws Exception {
                String body = stringResponse.body();
                JSONObject jsonObject = JSON.parseObject(body);
                String response = jsonObject.getString(AppConstant.JSON_RESPONSE);
                if (response.equals(AppConstant.JSON_SUCCESS)) {
                    List<IndustryTypeBean> list = JSON.parseArray(jsonObject.getString(AppConstant.JSON_DATA), IndustryTypeBean.class);
                    return list;
                }
                throw new IApiException("行业分类获取", jsonObject.getString(AppConstant.JSON_MESSAGE));
            }
        });


    }


    //提交私人订制申请

    public Observable<Boolean> sumitCustomMade(CustomMadeRequestBean bean) {

        Retrofit retrofit = builderJsonType(3);
        String paramStr = JSON.toJSONString(bean);
        Loger.e("---paramsCustomMade--" + paramStr);

        CustomMadeApi api = retrofit.create(CustomMadeApi.class);
        Observable<Response<String>> observable = api.submitCustomMade(paramStr);
        return observable.map(new Function<Response<String>, Boolean>() {
            @Override
            public Boolean apply(Response<String> stringResponse) throws Exception {
                String responseStr = stringResponse.body();
                JSONObject jsonObject = JSON.parseObject(responseStr);
                String status = jsonObject.getString(AppConstant.JSON_RESPONSE);
                if (status.equals(AppConstant.JSON_SUCCESS)) {
                    return true;
                }

                throw new IApiException("提交私人定制", jsonObject.getString(AppConstant.JSON_MESSAGE));


            }


        });
    }


}
