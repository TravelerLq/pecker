package com.yuas.pecker.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;


import com.yuas.pecker.R;

import butterknife.BindView;

//分析参数配置 Activity

public class AnalyzeParamsConfigActivity extends BaseActivity {


    @BindView(R.id.fl_income_vat_report)
    FrameLayout flIncomeVatReport;


    @BindView(R.id.fl_balace_sheet)
    FrameLayout flBalanceSheet;


    @BindView(R.id.fl_other_data)
    FrameLayout flOtherData;


    @BindView(R.id.button_back)
    ImageButton buttonBack;

    @BindView(R.id.textview_title)
    TextView tvTitle;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analyze_params_config);
        tvTitle.setText(getResources().getString(R.string.analyze_params_configration));
        initViewEvent();
    }

    @Override
    protected void initViewEvent() {
        buttonBack.setOnClickListener(this);
        flIncomeVatReport.setOnClickListener(this);
        flBalanceSheet.setOnClickListener(this);
        flOtherData.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.fl_income_vat_report:
                //体验数据上传
                toActivity(ExperienceDataActivity.class);
                break;
            case R.id.fl_balace_sheet:
                //资产负债表
                toActivity(AssetsBalanceSheetActivity.class);
                break;
            case R.id.fl_other_data:
                //其他数据
                toActivity(ConfigOtherDataActivity.class);
                break;
            default:
                break;
        }
    }
}
