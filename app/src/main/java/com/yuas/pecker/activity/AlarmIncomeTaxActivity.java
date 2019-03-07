package com.yuas.pecker.activity;


//所得税预警

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yuas.pecker.R;
import com.yuas.pecker.bean.pecker.FileInfoBean;
import com.yuas.pecker.bean.pecker.SaveFileBean;
import com.yuas.pecker.network.control.AlarmingAnalyzeControl;
import com.yuas.pecker.network.control.AnalyzeParamsControl;
import com.yuas.pecker.network.control.UploadProgressListener;
import com.yuas.pecker.observer.CommonDialogObserver;
import com.yuas.pecker.observer.RxHelper;
import com.yuas.pecker.utils.Loger;
import com.yuas.pecker.view.widget.SimpleToast;


import java.io.File;

import io.reactivex.Observable;

public class AlarmIncomeTaxActivity extends BaseAlarmActivity {


    private String uploadExcelType;
    private String uploadTimeType;


    @Override
    public void initViewType() {

        excelSize = 3;
        optionalSize = 2;

        for (int i = 0; i < excelSize; i++) {
            excelsList.add(i, "");
        }


    }


    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.tv_analyze_submit_first:
                excelIndex = 0;
                uploadExcelType = "1";
                uploadTimeType = "1";
                requestCode = REQUEST_CODE_FIRST;
                selectExcel1(requestCode);
                break;

//            case R.id.tv_tab_year:
//
//                break;
//            case R.id.tv_tab_month:
//                initYearFragment();
//                break;
        }
    }

    private void selectExcel1(int code) {

        Intent intent = new Intent(this, ExcelRecycleActivity.class);
        startActivityForResult(intent, code);


    }

    @Override
    protected void selectExcel(int code) {

//        Intent intent = new Intent(this, ExcelRecycleActivity.class);
//        startActivityForResult(intent, requestCode);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            FileInfoBean bean = (FileInfoBean) data.getExtras().getSerializable("file");
            if (bean != null) {
                String path = bean.getAbPath();
                Loger.e("excelPAth--" + path);
                //上传啊，

                uploadExcel(excelIndex, uploadExcelType, uploadTimeType, path, uploadProgressListener);

            }

        }


    }


    /**
     * @param whitch                 上传的位置
     * @param excelType              :所得税 "1" 增值税 "2" 资产负债："3" 利润："4"
     * @param type                   "1":本年度 "2"往期月度 "3"："往期"年度
     * @param path
     * @param uploadProgressListener
     */
    private void uploadExcel(final int whitch, final String excelType, String type, String path,
                             UploadProgressListener uploadProgressListener) {
        final File file = new File(path);
        Loger.i("subFile = " + path);
        Observable<String> observable = new AlarmingAnalyzeControl().uploadExcelByNameAndTime(excelType, type, userId, file, uploadProgressListener);
        CommonDialogObserver<String> observer = new CommonDialogObserver<String>(AlarmIncomeTaxActivity.this) {
            @Override
            public void onNext(String s) {
                super.onNext(s);
                Loger.i("subExcelFile" + s + "");
                refreshResult(whitch, file.getName());
                //"response":"success","message":"成功","data":{"dateStr":"1547123759541"}}
                JSONObject jsonObject = JSON.parseObject(s);
                JSONObject jsonData = JSON.parseObject(jsonObject.getString("data"));
                String fileId = jsonData.getString("dateStr");
                excelsList.set(excelIndex, fileId);

                for (int i = 0; i < excelsList.size(); i++) {
                    String itemStr = excelsList.get(i);
                    Loger.e("---uploadexcelId" + itemStr);

                }

            }

            @Override
            public void onError(Throwable t) {
                super.onError(t);
                SimpleToast.toastMessage(getResources().getString(R.string.failed), Toast.LENGTH_SHORT);


            }


        };
        RxHelper.bindOnUI(observable, observer);


    }

    private void refreshResult(int code, String fileName) {
        switch (code) {
            case 0:
                setText(tvAnalyzeSubmitFirst, tvStateFirst, fileName);

                break;
            case 1:
                //setText(tvSubmitIncomeSecond, tvStateIncomeSecond, fileName);
                break;

            case 2:
                //   setText(tvSubmitIncomeThrid, tvStateIncomeThird, fileName);
                break;

            default:
                break;
        }
    }


    private void setText(TextView tvSubmitIncomeFirst, TextView tvStateIncomeFirst, String fileName) {
        tvSubmitIncomeFirst.setText(getResources().getText(R.string.re_upload));
        tvStateIncomeFirst.setText(fileName);
    }

    @Override
    protected void analyze() {
        checkData();

    }

    @Override
    protected void flTabState(Boolean expand) {
        Loger.e("--expand" + expand);
        if (expand) {
            //是展开，则收起
            llTab.setVisibility(View.GONE);

        } else {
            llTab.setVisibility(View.VISIBLE);
            initYearFragment();
        }

        isExpand = !expand;
    }

    //
    private void checkData() {

        //检查 又一个为""则sheet上传不完整
        for (int i = 0; i < excelSize - optionalSize; i++) {
            String itemStr = excelsList.get(i);
            if (TextUtils.isEmpty(itemStr)) {
                SimpleToast.toastMessage(getResources().getString(R.string.report_icomplete), Toast.LENGTH_SHORT);
                return;
            }
        }

        SaveFileBean bean = new SaveFileBean();
        bean.setUid(userId);
        bean.setList(excelsList);

        excuteAnalyze(bean);
    }


    //保存
    private void excuteAnalyze(SaveFileBean bean) {

        Observable<Boolean> observable = new AnalyzeParamsControl().saveExcels(bean);
        CommonDialogObserver<Boolean> observer = new CommonDialogObserver<Boolean>(AlarmIncomeTaxActivity.this) {
            @Override
            public void onNext(Boolean s) {
                super.onNext(s);
                Loger.i("save-excel--" + s);
                if (s) {
                    finish();
                }
            }

            @Override
            public void onError(Throwable t) {
                super.onError(t);


            }


        };
        RxHelper.bindOnUI(observable, observer);


    }
}
