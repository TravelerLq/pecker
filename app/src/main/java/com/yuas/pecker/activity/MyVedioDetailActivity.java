package com.yuas.pecker.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.yuas.pecker.R;
import com.yuas.pecker.bean.pecker.MyvedioResponseBean;
import com.yuas.pecker.constant.AppConstant;
import com.yuas.pecker.network.control.MyVedioControl;
import com.yuas.pecker.observer.CommonDialogObserver;
import com.yuas.pecker.observer.RxHelper;
import com.yuas.pecker.view.widget.SimpleToast;

import butterknife.BindView;
import io.reactivex.Observable;

/**
 * 视频详情
 */
public class MyVedioDetailActivity extends BaseActivity {

    //视频详情
    @BindView(R.id.button_back)
    ImageButton buttonBack;

    @BindView(R.id.textview_title)
    TextView tvTitle;

    @BindView(R.id.fl_my_vedio_thumnail)
    FrameLayout frameLayoutVedioThumnail;

    @BindView(R.id.tv_delete)
    TextView tvDelete;
    @BindView(R.id.iv_vedio_image_thumnail)
    ImageView ivVedioImageThumnail;


    private int vedioId;//视频ID
    private String vedioUrl;//视频url


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_vedio_detail_layout);
        tvTitle.setText(getResources().getString(R.string.my_vedio_detail));
        initViewEvent();


        if (getIntent() != null) {
            MyvedioResponseBean.ContentBean contentBean = (MyvedioResponseBean.ContentBean) getIntent().getExtras().getSerializable(AppConstant.SERIAL_KEY);
            if (contentBean != null) {
                vedioUrl = contentBean.getVideoUrl();
                vedioId = contentBean.getId();

                loadImage(MyVedioDetailActivity.this, ivVedioImageThumnail, contentBean.getVideoThumbnailUrl());

            }

        }


    }

    @Override
    protected void initViewEvent() {
        buttonBack.setOnClickListener(this);
        frameLayoutVedioThumnail.setOnClickListener(this);
        tvDelete.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.fl_my_vedio_thumnail:
                //播放
                playVedio();
                break;
            case R.id.tv_delete:
                //删除
                delete(vedioId);
                break;
            default:
                break;
        }

    }


    //播放视频
    private void playVedio() {
        openSystemVideoPlayer(vedioUrl, MyVedioDetailActivity.this);
    }


    //删除 vedio
    private void delete(int id) {

        Observable<Boolean> observable = new MyVedioControl().deleteVedio(id);
        CommonDialogObserver<Boolean> observer = new CommonDialogObserver<Boolean>(this) {
            @Override
            public void onNext(Boolean aBoolean) {
                super.onNext(aBoolean);
                SimpleToast.toastMessage(getResources().getString(R.string.success), Toast.LENGTH_SHORT);
                finish();


            }

            @Override
            public void onError(Throwable t) {
                super.onError(t);
            }
        };

        RxHelper.bindOnUI(observable, observer);
    }


}
