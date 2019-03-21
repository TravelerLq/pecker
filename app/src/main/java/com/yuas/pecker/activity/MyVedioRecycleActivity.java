package com.yuas.pecker.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yuas.pecker.R;
import com.yuas.pecker.adapter.MyvedioRecycleAdapter;
import com.yuas.pecker.bean.pecker.MyvedioResponseBean;
import com.yuas.pecker.network.control.MyVedioControl;
import com.yuas.pecker.observer.CommonDialogObserver;
import com.yuas.pecker.observer.RxHelper;
import com.yuas.pecker.view.LinearLayoutColorDivider;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;

//我的视频列表

public class MyVedioRecycleActivity extends BaseActivity implements OnRefreshListener, OnLoadMoreListener {
//    @BindView(R.id.recycle_my_vedio)
//    RecyclerView recycleViewVedio;
//
//    @BindView(R.id.swipeToLoadLayout)
//    SwipeToLoadLayout swipeToLoadLayout;
//

    @BindView(R.id.button_back)
    ImageButton buttonBack;

    @BindView(R.id.textview_title)
    TextView tvTitle;

    @BindView(R.id.tv_no_data)
    TextView tvNodata;

    SwipeToLoadLayout swipeToLoadLayout;
    private Context context;
    private String userId = "24";
    private int pageCount = 1;//页码
    private int pageSize = 5;//页大小

    private MyvedioRecycleAdapter adapter;
    private List<MyvedioResponseBean.ContentBean> listVedios;//vedio集合


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myvedio_recylce);
        tvTitle.setText(getResources().getString(R.string.my_vedio));
        initViewEvent();
        listVedios = new ArrayList<>();
        swipeToLoadLayout = (SwipeToLoadLayout) findViewById(R.id.swipeToLoadLayout);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.swipe_target);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayout.VERTICAL, false);
        linearLayoutManager.setRecycleChildrenOnDetach(false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new LinearLayoutColorDivider(getResources(), R.color.grey_f0f0f3, R.dimen.dimen_15, DividerItemDecoration.VERTICAL));
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);
        adapter = new MyvedioRecycleAdapter(listVedios);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                toAtivityWithParams(MyVedioDetailActivity.class, listVedios.get(position));
                //toActivityWithParams(MyVedioDetailActivity.class, "url", listVedios.get(position).getVideoUrl());
            }
        });

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

        getData(pageCount, pageSize);
        autoRefresh();

    }


    //获取视频
    private void getData(int pageCount, int pageSize) {
        Observable<MyvedioResponseBean> observable = new MyVedioControl().getVedios(userId, pageCount, pageSize);
        CommonDialogObserver<MyvedioResponseBean> observer = new CommonDialogObserver<MyvedioResponseBean>(this) {
            @Override
            public void onNext(MyvedioResponseBean myvedioResponseBean) {
                super.onNext(myvedioResponseBean);
                if (myvedioResponseBean.getContent().size() == 0) {
                    tvNodata.setVisibility(View.VISIBLE);
                } else {
                    tvNodata.setVisibility(View.GONE);
                    listVedios.clear();
                    listVedios.addAll(myvedioResponseBean.getContent());
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
    protected void onResume() {
        super.onResume();
        autoRefresh();
    }

    @Override
    public void onLoadMore() {

        pageCount++;
        swipeToLoadLayout.setRefreshing(true);
        Observable<MyvedioResponseBean> observable = new MyVedioControl().getVedios(userId, pageCount, pageSize);
        CommonDialogObserver<MyvedioResponseBean> observer = new CommonDialogObserver<MyvedioResponseBean>(this) {
            @Override
            public void onNext(final MyvedioResponseBean myvedioResponseBean) {
                super.onNext(myvedioResponseBean);

                swipeToLoadLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (myvedioResponseBean.getContent().size() == 0) {
                            tvNodata.setVisibility(View.VISIBLE);
                        } else {
                            tvNodata.setVisibility(View.GONE);
                            adapter.setList(myvedioResponseBean.getContent());
                        }
                        swipeToLoadLayout.setLoadingMore(false);

                    }
                }, 500);
            }

            @Override
            public void onError(Throwable t) {
                super.onError(t);
            }
        };

        RxHelper.bindOnUI(observable, observer);

    }

    // 刚加载时候，刷新
    @Override
    public void onRefresh() {

        if (pageCount == 1) {
            swipeToLoadLayout.setRefreshing(true);
            Observable<MyvedioResponseBean> observable = new MyVedioControl().getVedios(userId, pageCount, pageSize);
            CommonDialogObserver<MyvedioResponseBean> observer = new CommonDialogObserver<MyvedioResponseBean>(this) {
                @Override
                public void onNext(final MyvedioResponseBean myvedioResponseBean) {
                    super.onNext(myvedioResponseBean);
                    swipeToLoadLayout.post(new Runnable() {
                        @Override
                        public void run() {
                            swipeToLoadLayout.setRefreshing(false);
                            if (myvedioResponseBean.getContent().size() == 0) {
                                tvNodata.setVisibility(View.VISIBLE);
                            } else {
                                tvNodata.setVisibility(View.GONE);
                            }

                            adapter.refreshList(myvedioResponseBean.getContent());
                        }
                    });
                }

                @Override
                public void onError(Throwable t) {
                    super.onError(t);
                }
            };

            RxHelper.bindOnUI(observable, observer);
        } else {
            swipeToLoadLayout.setRefreshing(false);
        }

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
