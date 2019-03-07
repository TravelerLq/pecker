package com.yuas.pecker.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;


import com.yuas.pecker.R;

import butterknife.BindView;

/*
  琢税鸟相关Activity
 */


public class PeckerAaboutActivity extends BaseActivity {


    @BindView(R.id.textview_title)
    TextView tvTitle;

    @BindView(R.id.button_back)
    ImageButton buttonBack;

    @BindView(R.id.tv_pecker_content)
    TextView tvPeckerContent;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pecker_about);
        tvTitle.setText(getResources().getString(R.string.about_pecker));
        initViewEvent();
        tvPeckerContent.setText(getResources().getString(R.string.about_pecker_content));

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
