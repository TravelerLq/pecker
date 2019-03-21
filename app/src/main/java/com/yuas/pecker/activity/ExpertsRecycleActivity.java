package com.yuas.pecker.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;


import com.yuas.pecker.R;
import com.yuas.pecker.adapter.ExpertsReycleAdapter;
import com.yuas.pecker.bean.pecker.ExpertsBean;
import com.yuas.pecker.utils.Loger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

//专家列表
public class ExpertsRecycleActivity extends BaseActivity {

    @BindView(R.id.recycle_excel)
    RecyclerView recyclerView;
    private Context context;
    private ExpertsReycleAdapter mAdapter;
    private List<ExpertsBean> listResults = new ArrayList<>();


    @BindView(R.id.button_back)
    ImageButton buttonBack;

    @BindView(R.id.textview_title)
    TextView tvTitle;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experts_recycle);
        context = ExpertsRecycleActivity.this;
        Loger.e("--listResults--" + listResults.size());
        // getIntent().getStringArrayExtra("list");
//      // arrayList=new String[]{"根据企业实际情况，核实是否存在少计收入、多列成本等问题","可能存在不计或少计营业收入、多列成本费用、扩大税前扣除范围等问题。",
//               "多列成本费用、扩"};

        tvTitle.setText(getResources().getString(R.string.expert_advice));
        getData();
        initRecycleView();
        initViewEvent();


    }

    String urlPic2 = "https://wx.qlogo.cn/mmopen/vi_32/mNUpBaxyF4Wf59n9VcxHdz7Hy9Ut37eicSysOg7fk6cQpK8Fs6kp6SB6s7eB3IJfFacAY0lXyKDeBVZNMnyB64Q/132";

    private void getData() {
        ExpertsBean bean;
        for (int i = 0; i < 13; i++) {
            bean = new ExpertsBean();
            bean.setPhoto(urlPic2);
            bean.setTerritory("金融");
            bean.setPrice("30");
            listResults.add(bean);
        }


    }


    @Override
    protected void initViewEvent() {
        buttonBack.setOnClickListener(this);
    }


    boolean loading = false;

    private void initRecycleView() {
        //showdialog


//        LinearLayoutManager linearLayoutManager =
//                new LinearLayoutManager(context, LinearLayout.VERTICAL, false);
//        linearLayoutManager.setRecycleChildrenOnDetach(false);

//        GridLayoutManager gridLayoutManager =new GridL
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        int totalItemCount = gridLayoutManager.getItemCount();
        int lastVisibleItem = gridLayoutManager.findLastVisibleItemPosition();

        //  recyclerView.addItemDecoration(new LinearLayoutColorDivider(getResources(), R.color.white, R.dimen.dimen_2, DividerItemDecoration.VERTICAL));
        mAdapter = new ExpertsReycleAdapter(context, listResults);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new ExpertsReycleAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, int pos) {
                Loger.e("---AlarmresultPos" + pos);
            }
        });


        //滑动监听
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                int totalItemCount = layoutManager.getItemCount();

                int lastVisibleItem = layoutManager.findLastVisibleItemPosition();
//                if (!loading && totalItemCount < (lastVisibleItem + VISIBLE_THRESHOLD)) {
//                    //请求数据
//                    loading = true;
//                    Loger.e("load more......");
//                }

                if (lastVisibleItem == listResults.size() - 1) {
                    //请求数据
                    if (loading)
                        loading = false;
                    Loger.e("load more......");
                    loadMore();

                }


//                if (!loading && totalItemCount < (lastVisibleItem + Constant.VISIBLE_THRESHOLD)) {
//                    new ArticleTask(mActivity).execute(mAdapter.getBottomArticleId());
//                    loading = true;
//                }

            }
        });
    }

    private void loadMore() {
        loading = true;
        getData();
    }

}
