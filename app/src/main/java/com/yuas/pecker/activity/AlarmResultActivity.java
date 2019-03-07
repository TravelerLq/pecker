package com.yuas.pecker.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.yuas.pecker.R;
import com.yuas.pecker.adapter.StringTextOnlyAdapter;
import com.yuas.pecker.utils.Loger;
import com.yuas.pecker.view.LinearLayoutColorDivider;
import com.yuas.pecker.view.widget.SimpleToast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class AlarmResultActivity extends BaseActivity {

    @BindView(R.id.recycle_excel)
    RecyclerView recyclerView;
    private Context context;
    private StringTextOnlyAdapter mAdapter;
    private List<String> listResults = new ArrayList<>();
    private String[] arrayList;


    @BindView(R.id.button_back)
    ImageButton buttonBack;

    @BindView(R.id.textview_title)
    TextView tvTitle;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_recycle);
        context = AlarmResultActivity.this;
//        list
        listResults = getIntent().getStringArrayListExtra("list");
        Loger.e("--listResults--" + listResults.size());
        // getIntent().getStringArrayExtra("list");
//      // arrayList=new String[]{"根据企业实际情况，核实是否存在少计收入、多列成本等问题","可能存在不计或少计营业收入、多列成本费用、扩大税前扣除范围等问题。",
//               "多列成本费用、扩"};

        tvTitle.setText(getResources().getString(R.string.alarm));
        initRecycleView();
        initViewEvent();

        if (listResults.size() == 0) {
            SimpleToast.toastMessage(getResources().getString(R.string.health_notice), Toast.LENGTH_SHORT);
        }

    }


    @Override
    protected void initViewEvent() {
        buttonBack.setOnClickListener(this);
    }


    private void initRecycleView() {
        //showdialog


        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(context, LinearLayout.VERTICAL, false);
        linearLayoutManager.setRecycleChildrenOnDetach(false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new LinearLayoutColorDivider(getResources(), R.color.white, R.dimen.dimen_2, DividerItemDecoration.VERTICAL));
        mAdapter = new StringTextOnlyAdapter(context, listResults);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new StringTextOnlyAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, int pos) {
                Loger.e("---AlarmresultPos" + pos);
            }
        });
    }


}
