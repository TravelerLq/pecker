package com.yuas.pecker.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yuas.pecker.R;
import com.yuas.pecker.bean.pecker.FileInfoBean;
import com.yuas.pecker.bean.pecker.SaveFileBean;
import com.yuas.pecker.network.control.AnalyzeParamsControl;
import com.yuas.pecker.network.control.UploadProgressListener;
import com.yuas.pecker.observer.CommonDialogObserver;
import com.yuas.pecker.observer.RxHelper;
import com.yuas.pecker.utils.Loger;
import com.yuas.pecker.view.widget.SimpleToast;


import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import cn.addapp.pickers.listeners.OnItemPickListener;
import cn.addapp.pickers.listeners.OnSingleWheelListener;
import cn.addapp.pickers.picker.SinglePicker;
import io.reactivex.Observable;

// 体验数据上传
public class ExperienceDataActivity extends BaseActivity {

    @BindView(R.id.tv_select_year)
    TextView tvSelectYear;

    @BindView(R.id.tv_first_sheet_title)
    TextView tvFirstSheetTitle;
    @BindView(R.id.submit_income_first)
    TextView tvSubmitIncomeFirst;
    @BindView(R.id.state_income_first)
    TextView tvStateIncomeFirst;

    @BindView(R.id.tv_second_sheet_title)
    TextView tvSecondSheetTitle;
    @BindView(R.id.submit_income_second)
    TextView tvSubmitIncomeSecond;
    @BindView(R.id.state_income_second)
    TextView tvStateIncomeSecond;

    @BindView(R.id.tv_third_sheet_title)
    TextView tvThirdSheetTitle;
    @BindView(R.id.submit_income_third)
    TextView tvSubmitIncomeThrid;
    @BindView(R.id.state_income_third)
    TextView tvStateIncomeThird;


    @BindView(R.id.tv_fourth_sheet_title)
    TextView tvFourthSheetTitle;
    @BindView(R.id.submit_income_fourth)
    TextView tvSubmitIncomeFourth;
    @BindView(R.id.state_income_fourth)
    TextView tvStateIncomeFourth;

//  增值税

    @BindView(R.id.tv_vat_title_first)
    TextView tvVatTitleFirst;
    @BindView(R.id.tv_submit_vat_first)
    TextView tvSubmitVatFirst;
    @BindView(R.id.tv_state_vat_first)
    TextView tvStateVatFirst;

    @BindView(R.id.tv_vat_title_second)
    TextView tvVatTitleSecond;
    @BindView(R.id.tv_submit_vat_second)
    TextView tvSubmitVatSecond;
    @BindView(R.id.tv_state_vat_second)
    TextView tvStateVatSecond;

    @BindView(R.id.tv_vat_title_third)
    TextView tvVatTitleThird;
    @BindView(R.id.tv_submit_vat_third)
    TextView tvSubmitVatThird;
    @BindView(R.id.tv_state_vat_third)
    TextView tvStateVatThird;


    @BindView(R.id.tv_save)
    TextView tvSave;

    @BindView(R.id.button_back)
    ImageButton buttonBack;

    @BindView(R.id.textview_title)
    TextView tvTitle;
    private int year;//选择的年
    private String incomeTaxEndStr;//title str
    private String vatTaxEndStr;//second tile str
    private List<String> yearList;//年份集合
    private String redRequired;//红色* 的html str
    private String uploadStr;//上传str
    private int whitch; //标志哪个位置的textview
    private int requestCode;//报表返回码

    private static final int REQURST_CODE_INCOME_FIRST = 1031;//year年所得税报表返回码
    private static final int REQURST_CODE_INCOME_SECOND = 1032;//year-1年所得税报表返回码
    private static final int REQURST_CODE_INCOME_THIRD = 1033;//year-2年所得税报表返回码
    private static final int REQURST_CODE_INCOME_FOURTH = 1034;//year-3年所得税报表返回码

    private static final int REQURST_CODE_VAT_FIRST = 1041;//year-1年增值税报表返回码
    private static final int REQURST_CODE_VAT_SECOND = 1042;//year-2年增值税报表返回码
    private static final int REQURST_CODE_VAT_THIRD = 1043;//year-3年增值税报表返回码
    private String userId;
    private boolean yearSelect = false;//is year selected

    private List<String> excelsList = new ArrayList<>();//报表集合
    private int excelSize = 7;//固定7个

    private String excelCode;//貌似无用


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experience_data);
        userId = "24";
        tvTitle.setText(getResources().getString(R.string.experience_data));
        incomeTaxEndStr = getResources().getString(R.string.year_income_tax_application);
        vatTaxEndStr = getResources().getString(R.string.year_vat_sheet);
        uploadStr = getResources().getString(R.string.upload);
        redRequired = "<font color='#ff0000'><big><big>*</big></big></font>";
        initTextView();
        // testList();
        initViewEvent();
        initCalendar();
    }

    private void initTextView() {
        //两次加大字体，设置字体为红色（big会加大字号，font可以定义颜色）

        tvFirstSheetTitle.setText(Html.fromHtml(redRequired + incomeTaxEndStr));
        tvSecondSheetTitle.setText(Html.fromHtml(redRequired + incomeTaxEndStr));
        tvThirdSheetTitle.setText(Html.fromHtml(redRequired + incomeTaxEndStr));
        tvThirdSheetTitle.setText(Html.fromHtml(redRequired + incomeTaxEndStr));

        tvVatTitleFirst.setText(Html.fromHtml(redRequired + vatTaxEndStr));
        tvVatTitleSecond.setText(Html.fromHtml(redRequired + vatTaxEndStr));
        tvVatTitleThird.setText(Html.fromHtml(redRequired + vatTaxEndStr));

        //先未excelList全部赋值为空
        for (int i = 0; i < excelSize; i++) {
            excelsList.add("");
        }

    }


    @Override
    protected void initViewEvent() {

        tvSubmitIncomeFirst.setOnClickListener(this);
        tvSubmitIncomeSecond.setOnClickListener(this);
        tvSubmitIncomeThrid.setOnClickListener(this);
        tvSubmitIncomeFourth.setOnClickListener(this);

        tvStateIncomeFirst.setOnClickListener(this);
        tvStateIncomeSecond.setOnClickListener(this);
        tvStateIncomeThird.setOnClickListener(this);
        tvStateIncomeFourth.setOnClickListener(this);

        tvSubmitVatFirst.setOnClickListener(this);
        tvSubmitVatSecond.setOnClickListener(this);
        tvSubmitVatThird.setOnClickListener(this);

        tvStateVatFirst.setOnClickListener(this);
        tvStateVatSecond.setOnClickListener(this);
        tvStateVatThird.setOnClickListener(this);

        tvSelectYear.setOnClickListener(this);
        tvSave.setOnClickListener(this);
        buttonBack.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        super.onClick(view);
        whitch = -1;


        switch (view.getId()) {
            case R.id.tv_select_year:
                onListPicker(ExperienceDataActivity.this, yearList, tvSelectYear);
                break;
            case R.id.submit_income_first:
                excelCode = "4";
                whitch = 0;
                requestCode = REQURST_CODE_INCOME_FIRST;

                break;
            case R.id.submit_income_second:
                excelCode = "5";
                whitch = 1;
                requestCode = REQURST_CODE_INCOME_SECOND;
                break;

            case R.id.submit_income_third:
                excelCode = "6";
                whitch = 2;
                requestCode = REQURST_CODE_INCOME_THIRD;
                break;

            case R.id.submit_income_fourth:
                excelCode = "7";
                whitch = 3;
                requestCode = REQURST_CODE_INCOME_FOURTH;
                break;

            case R.id.tv_submit_vat_first:
                excelCode = "8";
                whitch = 4;
                requestCode = REQURST_CODE_VAT_FIRST;
                break;
            case R.id.tv_submit_vat_second:
                excelCode = "9";
                whitch = 5;
                requestCode = REQURST_CODE_VAT_SECOND;
                break;
            case R.id.tv_submit_vat_third:
                excelCode = "10";
                whitch = 6;
                requestCode = REQURST_CODE_VAT_THIRD;
                break;


            case R.id.tv_save:
                checkData();

                break;
            default:
                break;

        }


        if (view.getId() != R.id.tv_select_year && view.getId() != R.id.tv_save && view.getId() != R.id.button_back) {
            //只要不是 选择年和保存，都走下面方法
            if (!yearSelect) {
                SimpleToast.toastMessage(getResources().getString(R.string.year_notice), Toast.LENGTH_SHORT);
                return;
            }
            selectExcel1(requestCode);

        }


    }

    private void checkData() {

        //检查 又一个为""则sheet上传不完整
        for (int i = 0; i < excelSize; i++) {
            String itemStr = excelsList.get(i);
            if (TextUtils.isEmpty(itemStr)) {
                SimpleToast.toastMessage(getResources().getString(R.string.report_icomplete), Toast.LENGTH_SHORT);
                return;
            }
        }

        SaveFileBean bean = new SaveFileBean();
        bean.setUid(userId);
        bean.setList(excelsList);
        bean.setYearStr("" + year);
        saveExcels(bean);
    }


    //选择Excel文件
    private void selectExcel1(int code) {

        Intent intent = new Intent(this, ExcelRecycleActivity.class);
        startActivityForResult(intent, code);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            FileInfoBean bean = (FileInfoBean) data.getExtras().getSerializable("file");
            if (bean != null) {
                String path = bean.getAbPath();
                Loger.e("excelPAth--" + path);
                //上传啊，带着requestCode 所得税 4、5、6、7  下面增值税 8、9、10

                uploadExcel(requestCode, excelCode, path, uploadProgressListener);

            }

        }
    }

    // 上传excel表格


    private void uploadExcel(final int code, final  String type, String path, UploadProgressListener uploadProgressListener) {
        final File file = new File(path);
        Loger.i("subFile = " + path);
        Observable<String> observable = new AnalyzeParamsControl().uploadExcel( type, userId, file, uploadProgressListener);
        CommonDialogObserver<String> observer = new CommonDialogObserver<String>(ExperienceDataActivity.this) {
            @Override
            public void onNext(String s) {
                super.onNext(s);
                Loger.i("subExcelFile" + s + "");
                refreshResult(code, file.getName());
                //"response":"success","message":"成功","data":{"dateStr":"1547123759541"}}
                JSONObject jsonObject = JSON.parseObject(s);
                JSONObject jsonData = JSON.parseObject(jsonObject.getString("data"));
                String fileId = jsonData.getString("dateStr");
                excelsList.set(whitch, fileId);

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


    /*

    {"list":["1547173060282","1547173067720","1547173073753","1547173082811","1547173089970",
    "1547173096401","1547173103459","1547173108767"],"uid":"24","yearStr":"2019"}
     */
    //保存
    private void saveExcels(SaveFileBean bean) {

        Observable<Boolean> observable = new AnalyzeParamsControl().saveExcels(bean);
        CommonDialogObserver<Boolean> observer = new CommonDialogObserver<Boolean>(ExperienceDataActivity.this) {
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


    //上传成功后，根据code 刷新View
    private void refreshResult(int code, String fileName) {
        switch (code) {
            case REQURST_CODE_INCOME_FIRST:
                setText(tvSubmitIncomeFirst, tvStateIncomeFirst, fileName);

                break;
            case REQURST_CODE_INCOME_SECOND:
                setText(tvSubmitIncomeSecond, tvStateIncomeSecond, fileName);
                break;

            case REQURST_CODE_INCOME_THIRD:
                setText(tvSubmitIncomeThrid, tvStateIncomeThird, fileName);
                break;


            case REQURST_CODE_INCOME_FOURTH:
                setText(tvSubmitIncomeFourth, tvStateIncomeFourth, fileName);

                break;


            case REQURST_CODE_VAT_FIRST:
                setText(tvSubmitVatFirst, tvStateVatFirst, fileName);
                break;

            case REQURST_CODE_VAT_SECOND:
                setText(tvSubmitVatSecond, tvStateVatSecond, fileName);
                break;
            case REQURST_CODE_VAT_THIRD:
                setText(tvSubmitVatThird, tvStateVatThird, fileName);
                break;
        }
    }

    private void setText(TextView tvSubmitIncomeFirst, TextView tvStateIncomeFirst, String fileName) {
        tvSubmitIncomeFirst.setText(getResources().getText(R.string.re_upload));
        tvStateIncomeFirst.setText(fileName);
    }

    UploadProgressListener uploadProgressListener = new UploadProgressListener() {
        @Override
        public void onProgress(long currentBytesCount, long totalBytesCount) {
            Loger.i("uploadProgressListener = " + currentBytesCount);
        }
    };

    private void initCalendar() {
        yearList = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        int start = calendar.get(Calendar.YEAR) - 10;
        int end = calendar.get(Calendar.YEAR) + 10;
        for (int i = start; i <= end; i++) {
            yearList.add("" + i);
        }
        //onListPicker

    }

    private void setTitleDynamic(int year) {

        tvFirstSheetTitle.setText(Html.fromHtml(redRequired + uploadStr + year + "" + incomeTaxEndStr));
        tvSecondSheetTitle.setText(Html.fromHtml(redRequired + uploadStr + (year - 1) + incomeTaxEndStr));
        tvThirdSheetTitle.setText(Html.fromHtml(redRequired + uploadStr + (year - 2) + incomeTaxEndStr));
        tvFourthSheetTitle.setText(Html.fromHtml(redRequired + uploadStr + (year - 3) + incomeTaxEndStr));

        tvVatTitleFirst.setText(Html.fromHtml(redRequired + uploadStr + (year - 1) + vatTaxEndStr));
        tvVatTitleSecond.setText(Html.fromHtml(redRequired + uploadStr + (year - 2) + vatTaxEndStr));
        tvVatTitleThird.setText(Html.fromHtml(redRequired + uploadStr + (year - 3) + vatTaxEndStr));
    }


    public void onListPicker(Activity context, final List<String> allStatus, final View view) {
        final SinglePicker<String> picker = new SinglePicker<String>(context, allStatus);
        picker.setCanLoop(false);
        picker.setWheelModeEnable(true);
        picker.setTopPadding(15);
        picker.setSelectedIndex(allStatus.size() / 2);
        picker.setOnSingleWheelListener(new OnSingleWheelListener() {
            @Override
            public void onWheeled(int i, String s) {

            }
        });

        picker.setOnItemPickListener(new OnItemPickListener<String>() {
            @Override
            public void onItemPicked(int i, String s) {
                Loger.e("viewName=" + (view instanceof TextView));
                if (view instanceof TextView) {
                    Loger.e("view instanceof TextView---");
                    ((TextView) view).setText(allStatus.get(i));
                    year = Integer.valueOf(tvSelectYear.getText().toString());
                    setTitleDynamic(year);
                    yearSelect = true;

                } else if (view instanceof EditText) {
                    ((EditText) view).setText(allStatus.get(i));
                }

            }
        });
        picker.setWeightEnable(true);

        picker.show();
    }


}
