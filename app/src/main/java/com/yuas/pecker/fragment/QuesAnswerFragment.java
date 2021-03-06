package com.yuas.pecker.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.yuas.pecker.R;
import com.yuas.pecker.activity.QueAnsDetailActivity;
import com.yuas.pecker.adapter.QueAnswerRecycleAdapter;
import com.yuas.pecker.bean.pecker.QueAnswerBean;
import com.yuas.pecker.network.control.QuesAnswerControl;
import com.yuas.pecker.observer.CommonDialogObserver;
import com.yuas.pecker.observer.DialogObserverHolder;
import com.yuas.pecker.observer.RxHelper;
import com.yuas.pecker.utils.Loger;
import com.yuas.pecker.view.LinearLayoutColorDivider;
import com.yuas.pecker.view.widget.SimpleToast;


import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * 咨询结果fragment
 */

public class QuesAnswerFragment extends BaseFragment implements View.OnClickListener, DialogObserverHolder, OnRefreshListener, OnLoadMoreListener {
    private QueAnswerRecycleAdapter mAdapter;
    SwipeToLoadLayout swipeToLoadLayout;
    private Context context;
    private String pageSize = "6";//一页数据大小
    private int pageCount = 1;//页下标
    private String userId = "24";

    private List<QueAnswerBean> listQueAnswers = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_question_answer, container, false);
       // TextView tvTitle = (TextView) view.findViewById(R.id.textview_title);
        context = QuesAnswerFragment.this.getActivity();
      //  tvTitle.setText(getResources().getString(R.string.question_answer));
        swipeToLoadLayout = (SwipeToLoadLayout) view.findViewById(R.id.swipeToLoadLayout);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.swipe_target);

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
                Loger.e("---fragmnet id" + id);

                toActivityWithId(QuesAnswerFragment.this, QueAnsDetailActivity.class, id);
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

        return view;
    }

    private void autoRefresh() {
        swipeToLoadLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeToLoadLayout.setRefreshing(true);
            }
        });
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public FragmentManager getSupportFragmentManager() {
        return getActivity().getSupportFragmentManager();
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
    public void onLoadMore() {

        //加载更多啊
        pageCount++;

        Observable<List<QueAnswerBean>> observable = new QuesAnswerControl().getAllQueAnswe(pageSize, pageCount + "", userId);
        CommonDialogObserver<List<QueAnswerBean>> observer = new CommonDialogObserver<List<QueAnswerBean>>(this) {
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
                }, 20);

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
            CommonDialogObserver<List<QueAnswerBean>> observer = new CommonDialogObserver<List<QueAnswerBean>>(this) {
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


}
