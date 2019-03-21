package com.yuas.pecker.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yuas.pecker.R;
import com.yuas.pecker.adapter.WordRecycleAdapter;
import com.yuas.pecker.bean.pecker.WordBean;
import com.yuas.pecker.network.control.LearnFinancialControl;
import com.yuas.pecker.observer.CommonDialogObserver;
import com.yuas.pecker.observer.RxHelper;
import com.yuas.pecker.utils.Loger;
import com.yuas.pecker.view.widget.SimpleToast;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;

//财务报表学习 二级列表 UI（数据后台拉取！） --（下面的详情也是拉取，视频上传成功后，提示上传成功，可在我的视频查看）

public class LearnFinancialSecondRecActivity extends BaseActivity implements OnRefreshListener, OnLoadMoreListener {
    private WordRecycleAdapter mAdapter;
    SwipeToLoadLayout swipeToLoadLayout;
    private Context context;
    @BindView(R.id.button_back)
    ImageButton buttonBack;

    @BindView(R.id.textview_title)
    TextView tvTitle;

    private List<WordBean> listResults = new ArrayList<>();//词条集合
    private String id;//报表id

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_more);
        context = LearnFinancialSecondRecActivity.this;
        id = getIntent().getStringExtra("id");
        //标题 从上级传入的title
       tvTitle.setText(getResources().getString(R.string.learn_financial_report));
        swipeToLoadLayout = (SwipeToLoadLayout) findViewById(R.id.swipeToLoadLayout);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.swipe_target);

        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(context, LinearLayout.VERTICAL, false);
        linearLayoutManager.setRecycleChildrenOnDetach(false);
        recyclerView.setLayoutManager(linearLayoutManager);

        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);
        //根据id 获取财务报表学习的二级列表数据

        mAdapter = new WordRecycleAdapter(listResults);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                int id = listResults.get(position).getId();
                toActivityWithParams(LearnFinancialDetailActivity.class, "id",id + "");
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
        autoRefresh();
        initViewEvent();
    }

    @Override
    protected void initViewEvent() {
        buttonBack.setOnClickListener(this);
    }

    private int pageSize = 8;
    private int pageCount = 1;

    @Override
    public void onLoadMore() {
        //加载更多啊
        pageCount++;

        Observable<String> observable = new LearnFinancialControl().getWords(id, pageCount, pageSize);
        CommonDialogObserver<String> observer = new CommonDialogObserver<String>(LearnFinancialSecondRecActivity.this) {
            @Override
            public void onNext(final String string) {
                super.onNext(string);

                JSONObject jsonObject = JSON.parseObject(string);

                final List<WordBean> list = JSON.parseArray(jsonObject.getString("content"), WordBean.class);

                swipeToLoadLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (list.size() == 0) {
                            SimpleToast.toastMessage(getResources().getString(R.string.no_more_data), Toast.LENGTH_SHORT);
                        } else {
                            // mAdapter.setList(list);
                            listResults.addAll(list);
                            mAdapter.notifyDataSetChanged();
                        }

                        swipeToLoadLayout.setLoadingMore(false);

                    }
                }, 2000);

            }

            @Override
            public void onError(Throwable t) {
                super.onError(t);


            }


        };
        RxHelper.bindOnUI(observable, observer);


    }

    @Override
    public void onRefresh() {
        Loger.e("--onRefresh pageCount"+pageCount);
        if (pageCount == 1) {
            Loger.e("--network");
            swipeToLoadLayout.setRefreshing(true);
            Observable<String> observable = new LearnFinancialControl().getWords(id, pageCount, pageSize);
            CommonDialogObserver<String> observer = new CommonDialogObserver<String>(LearnFinancialSecondRecActivity.this) {
                @Override
                public void onNext(final String string) {
                    super.onNext(string);
                    JSONObject jsonObject = JSON.parseObject(string);
                    List<WordBean> list = JSON.parseArray(jsonObject.getString("content"), WordBean.class);

                    listResults.addAll(list);
                    swipeToLoadLayout.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            swipeToLoadLayout.setRefreshing(false);
                            mAdapter.notifyDataSetChanged();

                        }
                    }, 500);


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
