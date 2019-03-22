package com.yuas.pecker.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.yuas.pecker.R;

import butterknife.BindView;

/**
 * 预警结果详情
 */

public class AlarmingDetailActivity extends BaseActivity {

    @BindView(R.id.button_back)
    ImageButton buttonBack;

    @BindView(R.id.textview_title)
    TextView tvTitle;

    @BindView(R.id.tv_alarming_result)
    TextView tvResult;
    private String resultIntent; //预警结果str


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarming_detail_layout);
        tvTitle.setText(getResources().getString(R.string.alarm_detial));
        initViewEvent();
        if (getIntent() != null) {
            resultIntent = getIntent().getStringExtra("result");
        }

        if (TextUtils.isEmpty(resultIntent)) {
            tvResult.setText(getResources().getString(R.string.no_alarming_result));
        } else {
            tvResult.setText(resultIntent);
        }

    }

    @Override
    protected void initViewEvent() {
        buttonBack.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

    }

}
