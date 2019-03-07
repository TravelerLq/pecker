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
import android.widget.Toast;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.yuas.pecker.R;
import com.yuas.pecker.adapter.QueAnswerRecycleAdapter;
import com.yuas.pecker.bean.pecker.QueAnswerBean;
import com.yuas.pecker.network.control.QuesAnswerControl;
import com.yuas.pecker.observer.CommonDialogObserver;
import com.yuas.pecker.observer.RxHelper;
import com.yuas.pecker.utils.Loger;
import com.yuas.pecker.view.LinearLayoutColorDivider;
import com.yuas.pecker.view.widget.SimpleToast;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;

//问答列表 （即：问答tab选中后的页面，也是问题付费提交后的跳转页面）
public class QueAnswerRecycleViewActivity extends BaseActivity implements OnRefreshListener, OnLoadMoreListener {
    private QueAnswerRecycleAdapter mAdapter;
    SwipeToLoadLayout swipeToLoadLayout;
    private Context context;
    @BindView(R.id.button_back)
    ImageButton buttonBack;

    @BindView(R.id.textview_title)
    TextView tvTitle;

    private List<QueAnswerBean> listQueAnswers = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_more);
        context = QueAnswerRecycleViewActivity.this;
        tvTitle.setText(getResources().getString(R.string.question_answer));
        swipeToLoadLayout = (SwipeToLoadLayout) findViewById(R.id.swipeToLoadLayout);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.swipe_target);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayout.VERTICAL, false);
        linearLayoutManager.setRecycleChildrenOnDetach(false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new LinearLayoutColorDivider(getResources(), R.color.grey_f0f0f3, R.dimen.dimen_2, DividerItemDecoration.VERTICAL));
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);
        //  getData();
        mAdapter = new QueAnswerRecycleAdapter(context, listQueAnswers);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new QueAnswerRecycleAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, int pos) {
                String id = String.valueOf(listQueAnswers.get(pos).getId());
                Loger.e("---getQuizzerId" + id);
                toActivityWithType(QueAnsDetailActivity.class, id);
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
        QueAnswerBean bean;
        for (int i = 0; i < 13; i++) {
            bean = new QueAnswerBean();
            bean.setPhoto(urlPic2);
            bean.setTerritory("金融");
            bean.setPrice("30");
            listQueAnswers.add(bean);
        }

    }


    @Override
    protected void initViewEvent() {
        buttonBack.setOnClickListener(this);
    }

    private String pageSize = "5";
    private int pageCount = 1;
    private String userId = "24";

    @Override
    public void onLoadMore() {
        //加载更多啊
        pageCount++;

        Observable<List<QueAnswerBean>> observable = new QuesAnswerControl().getAllQueAnswe(pageSize, pageCount + "", userId);
        CommonDialogObserver<List<QueAnswerBean>> observer = new CommonDialogObserver<List<QueAnswerBean>>(QueAnswerRecycleViewActivity.this) {
            @Override
            public void onNext(final List<QueAnswerBean> list) {
                super.onNext(list);
                // listResults.addAll(list);
                swipeToLoadLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (list.size() == 0) {
                            SimpleToast.toastMessage(getResources().getString(R.string.no_more_data), Toast.LENGTH_SHORT);
                        } else {
                            //
                            mAdapter.setList(list);
                        }
                        //pageCount++;
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

        if (pageCount == 1) {
            swipeToLoadLayout.setRefreshing(true);
            Observable<List<QueAnswerBean>> observable = new QuesAnswerControl().getAllQueAnswe(pageSize, pageCount + "", userId);
            CommonDialogObserver<List<QueAnswerBean>> observer = new CommonDialogObserver<List<QueAnswerBean>>(QueAnswerRecycleViewActivity.this) {
                @Override
                public void onNext(final List<QueAnswerBean> list) {
                    super.onNext(list);
                    // listResults.addAll(list);
                    swipeToLoadLayout.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            swipeToLoadLayout.setRefreshing(false);
                            mAdapter.setList(list);
                        }
                    }, 2000);


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
