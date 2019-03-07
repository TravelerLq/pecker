package com.yuas.pecker.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;


import com.yuas.pecker.R;
import com.yuas.pecker.network.control.CompreExamControl;
import com.yuas.pecker.observer.CommonDialogObserver;
import com.yuas.pecker.observer.RxHelper;

import butterknife.BindView;
import io.reactivex.Observable;

//财税全面体检

public class ExamComprehensiveActivity extends BaseActivity {

    @BindView(R.id.fl_assess_income_tax)
    FrameLayout flAssessIncomeTax;

    @BindView(R.id.fl_assess_vat)
    FrameLayout flAssessVat;

    @BindView(R.id.fl_assess_stamp)
    FrameLayout flAssessStamp;

    @BindView(R.id.fl_assess_income)
    FrameLayout flAssessIncome;

    @BindView(R.id.fl_assess_cost)
    FrameLayout flAssessCost;

    @BindView(R.id.fl_assess_fee)
    FrameLayout flAssessFee;

    @BindView(R.id.fl_assess_profit)
    FrameLayout flAssessProfit;

    @BindView(R.id.fl_assess_assets)
    FrameLayout flAssessAssets;

    @BindView(R.id.fl_analyze_all)
    FrameLayout flAnalyzeAll;

    @BindView(R.id.button_back)
    ImageButton buttonBack;

    @BindView(R.id.textview_title)
    TextView tvTitle;
    private String userId;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_examination_comprehensive);
        initViewEvent();
        tvTitle.setText(getResources().getString(R.string.anayze_examination));
        userId = "24";
        hasConfig(userId);

    }


    @Override
    protected void initViewEvent() {

        buttonBack.setOnClickListener(this);

        flAssessIncomeTax.setOnClickListener(this);
        flAssessVat.setOnClickListener(this);
        flAssessStamp.setOnClickListener(this);
        flAssessIncome.setOnClickListener(this);
        flAssessCost.setOnClickListener(this);
        flAssessFee.setOnClickListener(this);
        flAssessProfit.setOnClickListener(this);
        flAssessAssets.setOnClickListener(this);
        flAnalyzeAll.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.fl_assess_income_tax:
                // 所得税
                toActivityWithTwoType(AlarmAnalyzeWithTypeActivity.class, "1", "6");
                break;
            case R.id.fl_assess_vat:
                // 赠值税
                toActivityWithTwoType(AlarmAnalyzeWithTypeActivity.class, "2", "7");
                break;

            case R.id.fl_assess_stamp:
                // 印花税
                toActivityWithTwoType(AlarmAnalyzeWithTypeActivity.class, "3", "8");
                break;

            case R.id.fl_assess_income:
                // 收入类
                toActivityWithTwoType(AlarmAnalyzeWithTypeActivity.class, "4", "1");
                break;

            case R.id.fl_assess_cost:
                // 成本类
                toActivityWithTwoType(AlarmAnalyzeWithTypeActivity.class, "5", "2");
                break;

            case R.id.fl_assess_fee:
                // 费用咧
                toActivityWithTwoType(AlarmAnalyzeWithTypeActivity.class, "6", "3");
                break;

            case R.id.fl_assess_profit:
                //利润
                toActivityWithTwoType(AlarmAnalyzeWithTypeActivity.class, "7", "4");
                break;

            case R.id.fl_assess_assets:
                //资产
                toActivityWithTwoType(AlarmAnalyzeWithTypeActivity.class, "8", "5");
                break;

            case R.id.fl_analyze_all:
                //全面
                toActivityWithTwoType(AlarmAnalyzeWithTypeActivity.class, "9", "9");
                break;
            default:
                break;
        }
    }


    private void hasConfig(String userId) {
        Observable<Boolean> observable = new CompreExamControl().hasConfig(userId);
        CommonDialogObserver<Boolean> observer =
                new CommonDialogObserver<Boolean>(this) {

                    @Override
                    public void onNext(Boolean isConfig) {
                        super.onNext(isConfig);
                        if (!isConfig) {
                            //如果没有配置，弹出警告
                            warningDialog(getResources().getString(R.string.analyze_warning));

                        }

                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                    }
                };

        RxHelper.bindOnUIActivityLifeCycle(observable, observer, ExamComprehensiveActivity.this);
    }

    protected void warningDialog(String message) {
        new AlertDialog.Builder(ExamComprehensiveActivity.this)
                .setTitle(getResources().getString(R.string.notice))
                .setMessage(message)
                .setPositiveButton(getResources().getString(R.string.sure), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        toActivity(AnalyzeParamsConfigActivity.class);


                    }
                })
                .setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .create().show();

    }

}
