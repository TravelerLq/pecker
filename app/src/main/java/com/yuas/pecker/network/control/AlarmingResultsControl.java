package com.yuas.pecker.network.control;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yuas.pecker.bean.pecker.AlarmingResultBean;
import com.yuas.pecker.constant.AppConstant;
import com.yuas.pecker.exception.IApiException;
import com.yuas.pecker.network.api.AlarmingResultsApi;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by liqing on 2018/12/19.
 * 预警结果
 */

public class AlarmingResultsControl extends BaseControl {

    //获取预警结果列表

    public Observable<List<AlarmingResultBean>> getAlarmingResults(String pageSize, String pageCount, String uid) {
        Observable observable = null;
        Retrofit retrofit = builderJsonType(3);

        AlarmingResultsApi api = retrofit.create(AlarmingResultsApi.class);
        Map<String, Object> params = new HashMap<>();

        params.put("pageSize", pageSize);
        params.put("pageCount", pageCount);
        params.put("uid", uid);

        String paramsStr = JSON.toJSONString(params);
        observable = api.getAlarmingResults(paramsStr);

        return observable.map(new Function<Response<String>, List<AlarmingResultBean>>() {
            @Override
            public List<AlarmingResultBean> apply(Response<String> stringResponse) throws Exception {
                int code = stringResponse.code();
                String responseStr = stringResponse.body();
                if (code == 200) {
                    String body = stringResponse.body();
                    JSONObject jsonObject = JSON.parseObject(body);
                    String status = jsonObject.getString(AppConstant.JSON_RESPONSE);
                    if (AppConstant.JSON_SUCCESS.equals(status)) {
                        JSONObject jsonObject1 = jsonObject.getJSONObject(AppConstant.JSON_DATA);
                        List<AlarmingResultBean> list = JSON.parseArray(jsonObject1.getString("list"), AlarmingResultBean.class);

                        return list;
                    }
                }


                throw new IApiException("预警结果获取", "失败" + code);
            }
        });

    }


}

