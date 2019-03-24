package com.yuas.pecker.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


import com.yuas.pecker.R;
import com.yuas.pecker.activity.CustomMadeActivity;
import com.yuas.pecker.activity.ExamComprehensiveActivity;
import com.yuas.pecker.activity.ExpertsRecycleViewActivity;
import com.yuas.pecker.activity.LearnFinancialReportActivity;
import com.yuas.pecker.observer.DialogObserverHolder;

import org.reactivestreams.Subscription;

import io.reactivex.disposables.Disposable;

/**
 * Created by liqing on 18/6/25.
 * <p>
 * 商会管理的主页fragment
 */

public class HomePeckerFragment extends BaseFragment implements View.OnClickListener, DialogObserverHolder {

    private LinearLayout llExamination;

    private LinearLayout llExpert;
    private LinearLayout llCompreExam;
    private LinearLayout llExamReport;
    private LinearLayout llLearnReport;
    private LinearLayout llPolicy;
    private LinearLayout llCustomMade;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       // View view = inflater.inflate(R.layout.fragment_home_pecker, container, false);
        View view = inflater.inflate(R.layout.fragment_home_pecker, container, false);
        llExamination = (LinearLayout) view.findViewById(R.id.ll_financial_examination);
        llExpert = (LinearLayout) view.findViewById(R.id.ll_expert);
        llCompreExam = (LinearLayout) view.findViewById(R.id.ll_compre_exam);
        llExamReport = (LinearLayout) view.findViewById(R.id.ll__exam_report);

        llLearnReport = (LinearLayout) view.findViewById(R.id.ll_learn_report);
        llPolicy = (LinearLayout) view.findViewById(R.id.ll_policy);
        llCustomMade = (LinearLayout) view.findViewById(R.id.ll_custom_made);

        initEvent();
        return view;
    }

    private void initEvent() {
        llExamination.setOnClickListener(this);
        llExpert.setOnClickListener(this);
        llCompreExam.setOnClickListener(this);
        llExamReport.setOnClickListener(this);

        llLearnReport.setOnClickListener(this);
        llPolicy.setOnClickListener(this);
        llCustomMade.setOnClickListener(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    private void initData() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_compre_exam:
                //财税全面体检
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                toActivity(HomePeckerFragment.this, ExamComprehensiveActivity.class);
                break;
            case R.id.ll_expert:
                //专家咨询
                toActivity(HomePeckerFragment.this, ExpertsRecycleViewActivity.class);
                break;
            case R.id.ll_learn_report:
                //财税报表学习
                toActivity(HomePeckerFragment.this, LearnFinancialReportActivity.class);
                break;
            case R.id.ll_custom_made:
                //个性化定制
                toActivity(HomePeckerFragment.this, CustomMadeActivity.class);
                break;
            default:
                break;
        }

    }

    @Override
    public void addDisposable(Disposable disposable) {

    }

    @Override
    public void addSubscription(Subscription subscription) {

    }

    @Override
    public void removeDisposable(Disposable disposable) {

    }

    @Override
    public FragmentManager getSupportFragmentManager() {
        return null;
    }
}
