package com.yuas.pecker.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.yuas.pecker.R;
import com.yuas.pecker.adapter.ExpertsReycleAdapter;
import com.yuas.pecker.bean.pecker.ExpertsBean;
import com.yuas.pecker.network.control.ExpertsConsultControl;
import com.yuas.pecker.observer.CommonDialogObserver;
import com.yuas.pecker.observer.RxHelper;
import com.yuas.pecker.utils.Loger;
import com.yuas.pecker.view.widget.SimpleToast;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;

/**
 * 专家咨询
 */
public class ExpertsRecycleViewActivity extends BaseActivity implements OnRefreshListener, OnLoadMoreListener {
    private ExpertsReycleAdapter mAdapter;
    SwipeToLoadLayout swipeToLoadLayout;
    private Context context;
    @BindView(R.id.button_back)
    ImageButton buttonBack;

    @BindView(R.id.textview_title)
    TextView tvTitle;


    private List<ExpertsBean> listResults = new ArrayList<>();//专家列表

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_more);
        context = ExpertsRecycleViewActivity.this;
        tvTitle.setText(getResources().getString(R.string.expert_advice));
        swipeToLoadLayout = (SwipeToLoadLayout) findViewById(R.id.swipeToLoadLayout);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.swipe_target);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);
//        getData();
        mAdapter = new ExpertsReycleAdapter(context, listResults);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new ExpertsReycleAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, int pos) {
                String id = listResults.get(pos).getId();
                Loger.e("---AlarmresultPos" + id);
                toActivityWithType(WeiXinPayActivity.class, id);
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

    private String pageSize = "6";
    private int pageCount = 1;

    @Override
    public void onLoadMore() {
        //加载更多啊
        pageCount++;

        Observable<List<ExpertsBean>> observable = new ExpertsConsultControl().getAllExperts(pageSize, pageCount + "");
        CommonDialogObserver<List<ExpertsBean>> observer = new CommonDialogObserver<List<ExpertsBean>>(ExpertsRecycleViewActivity.this) {
            @Override
            public void onNext(final List<ExpertsBean> list) {
                super.onNext(list);
                // listResults.addAll(list);
                swipeToLoadLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (list.size() == 0) {
                            SimpleToast.toastMessage(getResources().getString(R.string.no_more_data), Toast.LENGTH_SHORT);
                        } else {
                            mAdapter.setList(list);
                        }
                        //pageCount++;
                        swipeToLoadLayout.setLoadingMore(false);

                    }
                }, 20);

            }

            @Override
            public void onError(Throwable t) {
                super.onError(t);


            }


        };
        RxHelper.bindOnUI(observable, observer);


    }

    //下拉刷新

    @Override
    public void onRefresh() {
        Loger.e("----onRefrsh---");
        if (pageCount == 1) {
            swipeToLoadLayout.setRefreshing(true);
            Observable<List<ExpertsBean>> observable = new ExpertsConsultControl().getAllExperts(pageSize, pageCount + "");
            CommonDialogObserver<List<ExpertsBean>> observer = new CommonDialogObserver<List<ExpertsBean>>(ExpertsRecycleViewActivity.this) {
                @Override
                public void onNext(final List<ExpertsBean> list) {
                    super.onNext(list);

                    // listResults.addAll(list);
                    swipeToLoadLayout.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            swipeToLoadLayout.setRefreshing(false);
                            //  mAdapter.refreshList(list);
                            mAdapter.refreshList(list);
                            for (int i = 0; i < list.size(); i++) {
                                Loger.e("expert.name" + list.get(i).getTeachName());
                            }
                        }
                    }, 20);


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
