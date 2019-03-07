package com.yuas.pecker.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.yuas.pecker.R;
import com.yuas.pecker.adapter.ExpertsReycleAdapter;
import com.yuas.pecker.bean.pecker.ExpertsBean;


import java.util.ArrayList;
import java.util.List;

public class LoadMoreActivity extends BaseActivity implements OnRefreshListener, OnLoadMoreListener {
    private ExpertsReycleAdapter mAdapter;
    SwipeToLoadLayout swipeToLoadLayout;
    private Context context;
    private List<ExpertsBean> listResults = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_more);
        context = LoadMoreActivity.this;
        swipeToLoadLayout = (SwipeToLoadLayout) findViewById(R.id.swipeToLoadLayout);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.swipe_target);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(gridLayoutManager);

        swipeToLoadLayout.setOnRefreshListener(this);

        swipeToLoadLayout.setOnLoadMoreListener(this);
        getData();
        mAdapter = new ExpertsReycleAdapter(context, listResults);
        recyclerView.setAdapter(mAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (!ViewCompat.canScrollVertically(recyclerView, 1)) {
                        swipeToLoadLayout.setLoadingMore(true);
                    }
                }
            }
        });
        autoRefresh();
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

    }

    @Override
    public void onLoadMore() {
        //加载更多啊

        ExpertsBean bean;
        for (int i = 0; i < 13; i++) {
            bean = new ExpertsBean();
            bean.setPhoto(urlPic2);
            bean.setTerritory("金融");
            bean.setPrice("30");
            listResults.add(bean);
        }
        swipeToLoadLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeToLoadLayout.setLoadingMore(false);
                mAdapter.setList(listResults);
            }
        }, 2000);
    }

    @Override
    public void onRefresh() {

        //
        swipeToLoadLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeToLoadLayout.setRefreshing(false);
//                mAdapter.add("REFRESH:\n" + new Date());

            }
        }, 2000);

    }

    private void autoRefresh() {
        swipeToLoadLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeToLoadLayout.setRefreshing(true);
            }
        });
    }
}
