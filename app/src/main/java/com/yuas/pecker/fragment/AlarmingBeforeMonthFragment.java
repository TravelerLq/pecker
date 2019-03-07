package com.yuas.pecker.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yuas.pecker.R;


public class AlarmingBeforeMonthFragment extends BaseFragment implements View.OnClickListener {
    LinearLayout rlFirst;

    LinearLayout rlSecond;
    LinearLayout rlThird;
    LinearLayout rlFourth;

    TextView tvSubmitFrist;
    TextView tvSubmitSecond;
    TextView tvSubmitThird;
    TextView tvSubmitFourth;


    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alarming_analyze_before, container, false);
        rlFirst = (LinearLayout) view.findViewById(R.id.rl_before_year_third);
        rlSecond = (LinearLayout) view.findViewById(R.id.rl_before_year_second);
        rlThird = (LinearLayout) view.findViewById(R.id.rl_before_year_third);
        rlFourth = (LinearLayout) view.findViewById(R.id.rl_before_year_fourth);

        tvSubmitFrist = (TextView) view.findViewById(R.id.tv_before_year_submit_first);
        tvSubmitSecond = (TextView) view.findViewById(R.id.tv_before_year_submit_second);
        tvSubmitThird = (TextView) view.findViewById(R.id.tv_before_year_submit_third);
        tvSubmitFourth = (TextView) view.findViewById(R.id.tv_before_year_submit_fourth);

        tvSubmitFrist.setOnClickListener(this);
        tvSubmitSecond.setOnClickListener(this);
        tvSubmitThird.setOnClickListener(this);
        tvSubmitFourth.setOnClickListener(this);

        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_before_year_submit_first:

                break;
            case R.id.tv_before_year_submit_second:
                break;
            case R.id.tv_before_year_submit_third:
                break;
            case R.id.tv_before_year_submit_fourth:
                break;
            default:
                break;

        }

    }
}
