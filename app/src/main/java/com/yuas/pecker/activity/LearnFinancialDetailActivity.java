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
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yuas.pecker.R;
import com.yuas.pecker.adapter.VedioAdapter;
import com.yuas.pecker.bean.pecker.PicBean;
import com.yuas.pecker.bean.pecker.RequestProblemBean;
import com.yuas.pecker.bean.pecker.WordExplainBean;
import com.yuas.pecker.network.control.ExpertsConsultControl;
import com.yuas.pecker.network.control.LearnFinancialControl;
import com.yuas.pecker.network.control.UploadProgressListener;
import com.yuas.pecker.observer.CommonDialogObserver;
import com.yuas.pecker.observer.RxHelper;
import com.yuas.pecker.utils.Loger;
import com.yuas.pecker.view.LinearLayoutColorDivider;
import com.yuas.pecker.view.widget.SimpleToast;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;

//词条解释详情
public class LearnFinancialDetailActivity extends BaseActivity implements UploadProgressListener {


    @BindView(R.id.textview_title)
    TextView tvTitle;
    @BindView(R.id.button_back)
    ImageButton buttonBack;

    @BindView(R.id.tv_word_title)
    TextView tvWordTitle;

    @BindView(R.id.iv_word_image)
    ImageView ivWordImage;


    @BindView(R.id.tv_word_explain)
    TextView tvWordExplain;
    @BindView(R.id.tv_word_from)
    TextView tvWordFrom;

    @BindView(R.id.tv_upload_vedio)
    TextView tvUploadVedio;

    @BindView(R.id.recycleview_vedio)
    RecyclerView recyclerView;


    private List<PicBean> list;
    List<String> uploadPicsStr;
    private ArrayList<String> selectedPhotos = new ArrayList<>();
    private List<WordExplainBean.VideoListBean> vedioList;
    private VedioAdapter mAdater;
    private String id;

    private Context context;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_financial_detail_layout);
        context = LearnFinancialDetailActivity.this;
        if (getIntent() != null) {
            id = getIntent().getStringExtra("id");
        }

        Loger.e("--wordExplainId" + id);
        list = new ArrayList<>();
        vedioList = new ArrayList<>();
        uploadPicsStr = new ArrayList<>();
        tvTitle.setText(getResources().getString(R.string.word_explain));
        initViewEvent();


        getData();
        initRecycleview();

    }


    private void initRecycleview() {

        mAdater = new VedioAdapter(vedioList);
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(LearnFinancialDetailActivity.this, LinearLayout.VERTICAL, false);
        linearLayoutManager.setRecycleChildrenOnDetach(false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new LinearLayoutColorDivider(getResources(), R.color.white, R.dimen.dimen_2, DividerItemDecoration.VERTICAL));


        recyclerView.setAdapter(mAdater);
        mAdater.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //点击播放视频 8.0 系统可以
                playVedio(vedioList.get(position).getVideoUrl());
                // toActivityWithParams(TestVedioActivity.class,"url",vedioList.get(position).getVideoUrl());
            }
        });

    }


    private void playVedio(String url) {
        openSystemVideoPlayer(url, context);//视频未下载，播放网络视频

//        if (fileVedio.exists()) {
//            String videoFilePath = fileVedio.getAbsolutePath();
//            openSystemVideoPlayer(videoFilePath);
//        } else {
//            openSystemVideoPlayer(baseUrl+videoUrl);//视频未下载，播放网络视频
//
//        }
    }

    //获取词条信息数据的详情

    private void getData() {
        //
        Observable<WordExplainBean> observable = new LearnFinancialControl().getWordExplainBean(id);
        CommonDialogObserver<WordExplainBean> observer = new CommonDialogObserver<WordExplainBean>(this) {

            @Override
            public void onNext(WordExplainBean bean) {
                super.onNext(bean);
                if (bean != null) {
                    tvWordTitle.setText(bean.getLemmaName());
                    tvWordExplain.setText(bean.getLemmaExplain());
                    loadImage(context, ivWordImage, bean.getPictureUrl());
                    tvWordFrom.setText("出自" + bean.getStatementName());
                    vedioList.clear();
                    vedioList.addAll(bean.getVideoList());
                    mAdater.notifyDataSetChanged();
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
        tvUploadVedio.setOnClickListener(this);
        buttonBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        if (view.getId() == R.id.tv_upload_vedio) {
            //上传视频
            ///selectVedioFromLocal(LearnFinancialDetailActivity.this);
            toActivityWithParams(UploadVideoActivity.class, "id", id);

        }
    }


    //检查问题填写完整
    private void chedata() {
//        String content = edtInputProblem.getText().toString().trim();
//        if (TextUtils.isEmpty(content)) {
//            SimpleToast.toastMessage(getResources().getString(R.string.no_empty_content), Toast.LENGTH_SHORT);
//            return;
//        }

        //reduce the memory usage
        RequestProblemBean requestProblemBean = new RequestProblemBean();


        submitProlem(requestProblemBean);
    }


    //提交问题
    private void submitProlem(RequestProblemBean requestProblemBean) {
        Observable<Boolean> observable = new ExpertsConsultControl().submitProblems(requestProblemBean);
        CommonDialogObserver<Boolean> observer = new CommonDialogObserver<Boolean>(this) {
            @Override
            public void onNext(Boolean aBoolean) {
                super.onNext(aBoolean);
                if (aBoolean) {
                    SimpleToast.toastMessage(getResources().getString(R.string.success), Toast.LENGTH_SHORT);
                    //付费提交成功--咨询结果 -问答列表
                    toActivity(QueAnswerRecycleViewActivity.class);
                    finish();
                }
            }

            @Override
            public void onError(Throwable t) {
                super.onError(t);
                SimpleToast.toastMessage(getResources().getString(R.string.failed), Toast.LENGTH_SHORT);

            }
        };

        RxHelper.bindOnUI(observable, observer);
    }

    UploadProgressListener uploadProgressListener = new UploadProgressListener() {
        @Override
        public void onProgress(long currentBytesCount, long totalBytesCount) {
            Loger.i("uploadProgressListener = " + currentBytesCount);
        }
    };

    @Override
    public void onProgress(long currentBytesCount, long totalBytesCount) {

    }
}
