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
import com.yuas.pecker.R;
import com.yuas.pecker.adapter.StringTextOnlyAdapter;
import com.yuas.pecker.utils.Loger;
import com.yuas.pecker.view.LinearLayoutColorDivider;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;

/**
 * 预警结果
 */

public class AlarmResultActivity extends BaseActivity {

    @BindView(R.id.recycle_excel)
    RecyclerView recyclerView;
    @BindView(R.id.button_back)
    ImageButton buttonBack;

    @BindView(R.id.textview_title)
    TextView tvTitle;

    @BindView(R.id.file_searcher_main_no_result_found)
    TextView tvResult;

    private Context context;
    private StringTextOnlyAdapter mAdapter;
    private List<String> listResults = new ArrayList<>();//体检结果集合


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_recycle);
        context = AlarmResultActivity.this;
      //  initTestData();

        if(getIntent()!=null&&getIntent().getStringArrayListExtra("list")!=null){
            listResults = getIntent().getStringArrayListExtra("list");
        }
        tvTitle.setText(getResources().getString(R.string.alarm));
        initRecycleView();
        initViewEvent();


        if (listResults.size() == 0) {
            tvResult.setVisibility(View.VISIBLE);
            tvResult.setText(getResources().getString(R.string.health_notice));

        }else {
            tvResult.setVisibility(View.GONE);
        }

    }

    private void initTestData() {
        listResults.add("result1");
        listResults.add("result21");
        listResults.add("result211");
    }


    @Override
    protected void initViewEvent() {
        buttonBack.setOnClickListener(this);
    }


    private void initRecycleView() {
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(context, LinearLayout.VERTICAL, false);
        linearLayoutManager.setRecycleChildrenOnDetach(false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new LinearLayoutColorDivider(getResources(), R.color.white, R.dimen.dimen_2, DividerItemDecoration.VERTICAL));
        mAdapter = new StringTextOnlyAdapter(context, listResults);
        recyclerView.setAdapter(mAdapter);
//        mAdapter.setOnItemClickListener(new StringTextOnlyAdapter.OnItemClickListener() {
//            @Override
//            public void OnItemClick(View view, int pos) {
//                Loger.e("---AlarmresultPos" + pos);
//            }
//        });
    }


}
