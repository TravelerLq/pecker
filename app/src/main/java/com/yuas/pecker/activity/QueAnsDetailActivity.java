package com.yuas.pecker.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yuas.pecker.R;
import com.yuas.pecker.adapter.GridViewPicsShowAdapter;
import com.yuas.pecker.adapter.StringTextOnlyAdapter;
import com.yuas.pecker.bean.pecker.PicBean;
import com.yuas.pecker.bean.pecker.QueAnswerDetailResponseBean;
import com.yuas.pecker.bean.pecker.RequestAppendQueBean;
import com.yuas.pecker.constant.AppConstant;
import com.yuas.pecker.network.control.QuesAnswerControl;
import com.yuas.pecker.observer.CommonDialogObserver;
import com.yuas.pecker.observer.RxHelper;
import com.yuas.pecker.view.LinearLayoutColorDivider;
import com.yuas.pecker.view.ScrollGridView;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;


//问答列表详情
public class QueAnsDetailActivity extends BaseActivity {

    @BindView(R.id.tv_question_details_content)
    TextView tvProblemContent;
    @BindView(R.id.grid_view_pics)
    ScrollGridView gridViewPics;

//        @BindView(R.id.grid_view_pics)
//    RecyclerView gridViewPics;

    @BindView(R.id.tv_question_details_time)
    TextView tvQuestionTime;
    @BindView(R.id.recycle_view_append_problems)
    RecyclerView recycleAppendProblems;

    @BindView(R.id.img_answer_icon)
    ImageView imgAnserIcon;
    @BindView(R.id.tv_answer_name)
    TextView tvAnswerName;
    @BindView(R.id.tv_answer_time)
    TextView tvAnswerTime;

    @BindView(R.id.recycler_view_answer)
    RecyclerView recycleAnswers;


    @BindView(R.id.tv_question_details_reply)
    TextView tvQuestionReply;

    @BindView(R.id.tv_no_answer)
    TextView tvNOAnswer;
    @BindView(R.id.ll_appends)
    LinearLayout llAppends;
    @BindView(R.id.tv_no_append)
    TextView tvNoAppend;
    @BindView(R.id.textview_title)
    TextView tvTitle;
    //
    private GridViewPicsShowAdapter adapter;
    private List<PicBean> listPics = new ArrayList<>();//图片集合

    //追加问题 recycleview
    private StringTextOnlyAdapter recycleAppendAdapter;
    private List<String> listAppends = new ArrayList<>();

    //回答 recycleview
    private StringTextOnlyAdapter recycleAnswersAapter;
    private List<String> listAnswers = new ArrayList<>();
    private Context context;
    private LinearLayoutManager llManagerAppend;
    private LinearLayoutManager llManagerAnswer;

    private String userId = "24";
    private String questionId = "";//问题id

    @BindView(R.id.button_back)
    ImageButton buttonBack;

    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_que_ans_detail);
        tvTitle.setText(getResources().getText(R.string.question_answer_detail));
        initViewEvent();
        // initTestData();
        context = QueAnsDetailActivity.this;
        llManagerAnswer = new LinearLayoutManager(context, LinearLayout.VERTICAL, false);
        llManagerAppend = new LinearLayoutManager(context, LinearLayout.VERTICAL, false);

        //adapterGridView
        // recycleview adapter two
        adapter = new GridViewPicsShowAdapter(QueAnsDetailActivity.this, listPics);
        gridViewPics.setAdapter(adapter);

        initRecycleAppend();
        initRecycleAnswer();

        if (getIntent() != null) {
            questionId = getIntent().getStringExtra("id");
        }

    }


    private void initRecycleAppend() {

        recycleAppendProblems.setLayoutManager(llManagerAppend);
        recycleAppendProblems.addItemDecoration(new LinearLayoutColorDivider(getResources(), R.color.white, R.dimen.dimen_2, DividerItemDecoration.VERTICAL));
        recycleAppendAdapter = new StringTextOnlyAdapter(context, listAppends);
        recycleAppendProblems.setAdapter(recycleAppendAdapter);

    }

    private void initRecycleAnswer() {

        recycleAnswers.setLayoutManager(llManagerAnswer);
        recycleAnswers.addItemDecoration(new LinearLayoutColorDivider(getResources(), R.color.white, R.dimen.dimen_3, DividerItemDecoration.VERTICAL));
        recycleAnswersAapter = new StringTextOnlyAdapter(context, listAnswers);
        recycleAnswers.setAdapter(recycleAnswersAapter);
    }

    @Override
    protected void initViewEvent() {
        tvQuestionReply.setOnClickListener(this);
        buttonBack.setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        if (view.getId() == R.id.tv_question_details_reply) {
            toActivityWithType(AppendQuestionActivity.class, questionId);
        }
    }


    //从网络获取数据
    private void getData() {
        Observable<QueAnswerDetailResponseBean> observable = new QuesAnswerControl().getQueAnsweDetail(userId, questionId);

        CommonDialogObserver<QueAnswerDetailResponseBean> observer = new CommonDialogObserver<QueAnswerDetailResponseBean>(this) {

            @Override
            public void onNext(QueAnswerDetailResponseBean responseBean) {
                super.onNext(responseBean);
                listPics.clear();
                listAppends.clear();
                listAnswers.clear();

                //
                // queAppendId = String.valueOf(responseBean.getId());
                tvProblemContent.setText(responseBean.getDescription());
                List<String> picList = responseBean.getImageList();
                PicBean picBean;

                for (int i = 0; i < picList.size(); i++) {
                    picBean = new PicBean();
                    picBean.setPhotoPath(AppConstant.BASE_URL_PIC + picList.get(i));
                    listPics.add(picBean);
                }
                adapter.notifyDataSetChanged();

                //追加问题
                List<QueAnswerDetailResponseBean.ExchangeListBean> exchangeListBeans = responseBean.getExchangeList();
                for (int i = 0; i < exchangeListBeans.size(); i++) {
                    if (exchangeListBeans.get(i).getType() == 2) {
                        //追问
                        listAppends.add(exchangeListBeans.get(i).getMsgValue());

                    } else {
                        listAnswers.add(exchangeListBeans.get(i).getMsgValue());
                    }
                }
               // testData();
                //没有回复，提示
                if (listAnswers.size() == 0) {
                    tvNOAnswer.setVisibility(View.VISIBLE);
                } else {
                    tvNOAnswer.setVisibility(View.GONE);
                }

                //没有问题追加：
                if (listAppends.size() == 0) {
                    tvNoAppend.setVisibility(View.VISIBLE);
                } else {
                    tvNoAppend.setVisibility(View.GONE);
                }

//                //没有追加，则隐藏追加布局
//                if (listAppends.size() == 0) {
//                    llAppends.setVisibility(View.GONE);
//                } else {
//                    llAppends.setVisibility(View.VISIBLE);
//                }

                recycleAnswersAapter.notifyDataSetChanged();
                recycleAppendAdapter.notifyDataSetChanged();

                //发问者
                tvQuestionTime.setText(responseBean.getCreatetime());

                //回答者头像
                RequestOptions options = new RequestOptions()
                        .placeholder(R.drawable.ic_default_user_circle)
                        .error(R.drawable.ic_default_user_circle)
                        .fallback(R.drawable.ic_default_user_circle)
                        .override(com.bumptech.glide.request.target.Target.SIZE_ORIGINAL);
                Glide.with(context).
                        load(AppConstant.BASE_URL_PIC + responseBean.getPhoto())
                        .apply(options)
                        .into(imgAnserIcon);

                //回答者擅长领域
                tvAnswerName.setText(responseBean.getIdentity());
                //职称
                tvAnswerTime.setText(responseBean.getTitle());

            }

            @Override
            public void onError(Throwable t) {
                super.onError(t);
            }
        };

        RxHelper.bindOnUI(observable, observer);
    }

    private void testData() {
        for(int i=0;i<10;i++){
            listAppends.add("problem i"+i);
            listAnswers.add("problemi"+i);
        }

    }


}
