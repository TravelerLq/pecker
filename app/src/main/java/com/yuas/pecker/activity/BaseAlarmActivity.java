package com.yuas.pecker.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.yuas.pecker.R;
import com.yuas.pecker.fragment.AlarmingBeforeMonthFragment;
import com.yuas.pecker.fragment.AlarmingBeforeYearFragment;
import com.yuas.pecker.network.control.UploadProgressListener;
import com.yuas.pecker.utils.Loger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

//预警体检的基本页面 其余可以继承此页面

public abstract class BaseAlarmActivity extends BaseActivity {


    @BindView(R.id.ll_tab)
    LinearLayout llTab;
    @BindView(R.id.rl_analyze_first)
    RelativeLayout rlAnalyzeFirst;

    @BindView(R.id.rl_analyze_second)
    RelativeLayout rlAnalyzeSecond;

    @BindView(R.id.rl_analyze_third)
    RelativeLayout rlAnalyzeThird;

    @BindView(R.id.rl_analyze_fourth)
    RelativeLayout rlAnalyzeFourth;

    @BindView(R.id.tv_analyze_title_first)
    TextView tvAnalyzeTitleFirst;

    @BindView(R.id.tv_analyze_title_second)
    TextView tvAnalyzeTitleSeond;

    @BindView(R.id.tv_analyze_title_third)
    TextView tvAnalyzeTitleThird;

    @BindView(R.id.tv_analyze_title_fourth)
    TextView tvAnalyzeTitleFourth;

    @BindView(R.id.tv_analyze_submit_first)
    TextView tvAnalyzeSubmitFirst;

    @BindView(R.id.tv_state_first)
    TextView tvStateFirst;

    @BindView(R.id.tv_analyze_submit_second)
    TextView tvAnalyzeSubmitSecond;

    @BindView(R.id.tv_state_second)
    TextView tvStateSecond;


    @BindView(R.id.tv_analyze_submit_third)
    TextView tvAnalyzeSubmitThird;

    @BindView(R.id.tv_state_third)
    TextView tvStateThird;

    @BindView(R.id.tv_analyze_submit_fourth)
    TextView tvAnalyzeSubmitFourth;

    @BindView(R.id.tv_state_fourth)
    TextView tvStateFourth;


    @BindView(R.id.fl_tab)
    FrameLayout flTab;
    @BindView(R.id.tv_before_info)
    TextView tvBeforeInfo;
    @BindView(R.id.tv_save)
    TextView tvSave;


    AlarmingBeforeYearFragment fragmentYear;
    AlarmingBeforeMonthFragment fragmentMonth;
    @BindView(R.id.textview_title)
    TextView tvTitle;

    @BindView(R.id.tv_tab_year)
    TextView tvTabYear;
    @BindView(R.id.tv_tab_month)
    TextView tvTabMonth;

    protected static final int REQUEST_CODE_FIRST = 1100;

    protected static final int REQUEST_CODE_SECOND = 1101;

    protected static final int REQUEST_CODE_THIRD = 1102;

    protected static final int REQUEST_CODE_FOURTH = 1103;


    protected List<String> excelsList = new ArrayList<>();
    protected int excelSize = 0;
    protected int optionalSize = 0;
    protected int witch = -1;
    protected String userId;
    protected int excelIndex = -1;

    Boolean isExpand = false;
    protected int requestCode = 0;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_alarming);
        userId = "24";
        tvTitle.setText(getResources().getText(R.string.alarming_analyze));
        initViewType();
        initViewEvent();

    }

    @Override
    protected void initViewEvent() {
        tvSave.setOnClickListener(this);
        tvBeforeInfo.setOnClickListener(this);
        tvAnalyzeSubmitFirst.setOnClickListener(this);
        tvAnalyzeSubmitSecond.setOnClickListener(this);
        tvAnalyzeSubmitThird.setOnClickListener(this);
        tvAnalyzeSubmitFourth.setOnClickListener(this);
        tvTabMonth.setOnClickListener(this);
        tvTabYear.setOnClickListener(this);

    }


    //初始化控件
    public abstract void initViewType();


    //1 月度：2  年度：3

    @Override
    public void onClick(View view) {
        int code;
        super.onClick(view);
        code = -1;
        switch (view.getId()) {
            case R.id.tv_before_info:

                flTabState(isExpand);
                break;
            case R.id.tv_save:
                analyze();
                break;
            case R.id.tv_analyze_submit_first:
                code = REQUEST_CODE_FIRST;
                selectExcel(code);
                break;
            case R.id.tv_analyze_submit_second:
                code = REQUEST_CODE_FIRST;
                break;
            case R.id.tv_analyze_submit_third:
                code = REQUEST_CODE_FIRST;
                break;
            case R.id.tv_analyze_submit_fourth:
                code = REQUEST_CODE_FIRST;
                break;
            case R.id.tv_tab_year:
                initYearFragment();
                break;
            case R.id.tv_tab_month:
                initMonthFragment();
                break;

            default:
                break;

        }


    }

    //选中 年
    protected void initYearFragment() {
        if (fragmentYear == null) {
            fragmentYear = new AlarmingBeforeYearFragment();
        }
        addFragmentNotToStack(R.id.fl_tab, fragmentYear);
        //tvStoreCombine.setBackground(getResources().getDrawable(R.drawable.bg_combine_select));
        tvTabYear.setBackgroundColor(getResources().getColor(R.color.white));
        tvTabMonth.setBackgroundColor(getResources().getColor(R.color.grey_f6f9fb));
        tvTabYear.setTextColor(getResources().getColor(R.color.blue_528dd9));
        tvTabMonth.setTextColor(getResources().getColor(R.color.grey_97999e));
    }

    //选中 月
    private void initMonthFragment() {
        if (fragmentMonth == null) {
            fragmentMonth = new AlarmingBeforeMonthFragment();
        }
        addFragmentNotToStack(R.id.fl_tab, fragmentMonth);
        //tvStoreCombine.setBackground(getResources().getDrawable(R.drawable.bg_combine_select));
        tvTabMonth.setBackgroundColor(getResources().getColor(R.color.white));
        tvTabYear.setBackgroundColor(getResources().getColor(R.color.grey_f6f9fb));
        tvTabMonth.setTextColor(getResources().getColor(R.color.blue_528dd9));
        tvTabYear.setTextColor(getResources().getColor(R.color.grey_97999e));
    }

    UploadProgressListener uploadProgressListener = new UploadProgressListener() {
        @Override
        public void onProgress(long currentBytesCount, long totalBytesCount) {
            Loger.i("uploadProgressListener = " + currentBytesCount);
        }
    };

    protected abstract void selectExcel(int code);

    protected abstract void analyze();

    protected abstract void flTabState(Boolean isExpand);
}
