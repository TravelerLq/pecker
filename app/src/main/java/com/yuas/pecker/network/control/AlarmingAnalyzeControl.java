package com.yuas.pecker.network.control;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yuas.pecker.bean.pecker.ConfigParamsRequestBean;
import com.yuas.pecker.bean.pecker.SaveAlarmAnalyzeBean;
import com.yuas.pecker.constant.AppConstant;
import com.yuas.pecker.exception.IApiException;
import com.yuas.pecker.network.api.AlarmingAnalyzeApi;
import com.yuas.pecker.network.api.AnalyzeParamsApi;
import com.yuas.pecker.utils.BaseUtils;
import com.yuas.pecker.utils.Loger;


import java.io.File;
import java.util.HashMap;
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
 * 体验体检数据上传的 Control
 */

public class AlarmingAnalyzeControl extends BaseControl {


    // （预警分析）-上传excel文件

    public Observable<String> uploadExcelByNameAndTime(String excelType, String type, String userId, File file, final UploadProgressListener uploadProgressListener) {

        //所得税 "1" 增值税 "2" 资产负债："3" 利润："4"
        Retrofit retrofit = builderUploadPicRetrofitWithType(3);
        AlarmingAnalyzeApi api = retrofit.create(AlarmingAnalyzeApi.class);

        Map<String, RequestBody> params = new HashMap<>();
        params.put("userId", BaseUtils.convertToRequestBody(userId));
        params.put("type", BaseUtils.convertToRequestBody(type));
//application/vnd.ms-excel
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("uploadFile", file.getName(), requestBody);
        Loger.e("excel路径 " + file.getName());
        if (excelType.equals("1")) {
            return api.uploadIncomeTax(part, params);
        }

        if (excelType.equals("2")) {
            return api.uploadVat(part, params);
        }

        if (excelType.equals("3")) {
            return api.uploadAssetsBalance(part, params);
        }

        if (excelType.equals("4")) {
            return api.uploadProfit(part, params);
        }


        return null;
    }


    //（预警分析保存数据）-保存excel文件

    public Observable<String> saveAlramAnalyzeData(SaveAlarmAnalyzeBean bean) {

        Retrofit retrofit = builderJsonType(3);
        String paramStr = JSON.toJSONString(bean);
        Loger.e("---paramsSaveExcel--" + paramStr);

        AlarmingAnalyzeApi api = retrofit.create(AlarmingAnalyzeApi.class);
        Observable<Response<String>> observable = api.saveAlarmAnalyzeData(paramStr);
        return observable.map(new Function<Response<String>, String>() {
            @Override
            public String apply(Response<String> stringResponse) throws Exception {
                String responseStr = stringResponse.body();
                JSONObject jsonObject = JSON.parseObject(responseStr);
                String status = jsonObject.getString(AppConstant.JSON_RESPONSE);
                int codeHttp = stringResponse.code();
                if (codeHttp == 401) {
                    throw new IApiException("401", "401");
                }
                if(codeHttp==200){
                    return responseStr;
                }


                throw new IApiException("保存excel文件", jsonObject.getString(AppConstant.JSON_MESSAGE));


            }


        });
    }

    //（资产负债表 ）保存数据


    public Observable<Boolean> saveCongfigParams(ConfigParamsRequestBean bean) {

        Retrofit retrofit = builderJsonType(3);
        String paramStr = JSON.toJSONString(bean);
        Loger.e("---paramsSaveExcel--" + paramStr);

        AnalyzeParamsApi api = retrofit.create(AnalyzeParamsApi.class);
        Observable<Response<String>> observable = api.saveComfigParams(paramStr);
        return observable.map(new Function<Response<String>, Boolean>() {
            @Override
            public Boolean apply(Response<String> stringResponse) throws Exception {
                String responseStr = stringResponse.body();
                int codeHttp = stringResponse.code();
                if (codeHttp != 200) {
                    throw new IApiException("error", "" + codeHttp);
                }
                JSONObject jsonObject = JSON.parseObject(responseStr);
                String status = jsonObject.getString(AppConstant.JSON_RESPONSE);
                if (status.equals(AppConstant.JSON_SUCCESS)) {
                    return true;

                }
                throw new IApiException("保存配置", jsonObject.getString(AppConstant.JSON_MESSAGE));


            }


        });
    }

}
