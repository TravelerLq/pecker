package com.yuas.pecker.activity;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yuas.pecker.R;
import com.yuas.pecker.adapter.ReportsNameRecycleAdapter;
import com.yuas.pecker.bean.pecker.LearnFinancialBean;
import com.yuas.pecker.network.control.LearnFinancialControl;
import com.yuas.pecker.observer.CommonDialogObserver;
import com.yuas.pecker.observer.RxHelper;
import com.yuas.pecker.utils.Loger;
import com.yuas.pecker.view.LinearLayoutColorDivider;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;

//财务报表学习 yiji（数据后台拉取！）

public class LearnFinancialReportActivity extends BaseActivity {


    @BindView(R.id.recycleview)
    RecyclerView recyclerView;

    @BindView(R.id.button_back)
    ImageButton buttonBack;

    @BindView(R.id.tv_no_data)
    TextView tvNoData;

    @BindView(R.id.textview_title)
    TextView tvTitle;
    private String userId;
    private ReportsNameRecycleAdapter mAdater;
    private List<LearnFinancialBean> data;
    private int reportNameId;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_financial_report);
        data = new ArrayList<>();
        initViewEvent();
        tvTitle.setText(getResources().getString(R.string.learn_financial_report));
        userId = "24";
        // initTestData();
        initRecycleView();
        getData();
    }

    private void initTestData() {
        LearnFinancialBean bean;
        for (int i = 0; i < 10; i++) {
            bean = new LearnFinancialBean();
            bean.setId(i);
            bean.setStatementName("reportName" + i);
            data.add(bean);
        }
    }

    private void initRecycleView() {
        mAdater = new ReportsNameRecycleAdapter(data);
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(LearnFinancialReportActivity.this, LinearLayout.VERTICAL, false);
        linearLayoutManager.setRecycleChildrenOnDetach(false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new LinearLayoutColorDivider(getResources(), R.color.white, R.dimen.dimen_2, DividerItemDecoration.VERTICAL));


        recyclerView.setAdapter(mAdater);
        mAdater.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                reportNameId = data.get(position).getId();
                Loger.e("---AlarmresultPos" + reportNameId);
                toActivityWithParams(LearnFinancialSecondRecActivity.class, "id", reportNameId + "");
            }
        });


    }

    //获取数据
    private void getData() {
        Observable<List<LearnFinancialBean>> observable = new LearnFinancialControl().getReportsName();
        CommonDialogObserver<List<LearnFinancialBean>> observer = new CommonDialogObserver<List<LearnFinancialBean>>(LearnFinancialReportActivity.this) {
            @Override
            public void onNext(List<LearnFinancialBean> learnFinancialBeans) {
                super.onNext(learnFinancialBeans);
                if (learnFinancialBeans.size() == 0) {
                    tvNoData.setVisibility(View.VISIBLE);
                } else {
                    data.clear();
                    data.addAll(learnFinancialBeans);
                    mAdater.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(Throwable t) {
                super.onError(t);
            }
        };
        RxHelper.bindOnUI(observable, observer);
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
