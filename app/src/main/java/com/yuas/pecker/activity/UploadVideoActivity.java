package com.yuas.pecker.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yuas.pecker.R;
import com.yuas.pecker.bean.VedioUploadResponseBean;
import com.yuas.pecker.bean.pecker.WordVideoBean;
import com.yuas.pecker.constant.AppConstant;
import com.yuas.pecker.network.control.LearnFinancialControl;
import com.yuas.pecker.network.control.UploadProgressListener;
import com.yuas.pecker.observer.CommonDialogObserver;
import com.yuas.pecker.observer.RxHelper;
import com.yuas.pecker.utils.Loger;
import com.yuas.pecker.utils.UriUtil;
import com.yuas.pecker.view.widget.SimpleToast;


import java.io.File;

import butterknife.BindView;
import io.reactivex.Observable;

public class UploadVideoActivity extends BaseActivity implements UploadProgressListener {

    @BindView(R.id.img_upload)
    ImageView imgUpload;

    //
    @BindView(R.id.edt_title_input)
    EditText edtTitleInput;

    @BindView(R.id.frame_video)
    FrameLayout frameVideo;

    @BindView(R.id.img_video_thumbnail)
    ImageView imgVideoThumbnail;

    @BindView(R.id.textview_title)
    TextView tvTitle;
    @BindView(R.id.button_back)
    ImageButton buttonBack;

    @BindView(R.id.tv_sure)
    TextView tvSure;

    private Context context;
    private boolean isSuccess = false;
    private VedioUploadResponseBean bean;
    private String userId = "24";
    private String lemmaId = "";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_video);
        context = UploadVideoActivity.this;
        tvTitle.setText(getResources().getString(R.string.upload_vedio));
        if (getIntent() != null) {
            lemmaId = getIntent().getStringExtra("id");
        }
        initViewEvent();


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case BaseActivity.REQUEST_CODE_SELECT_VIDEO_FILE:
                    //从选择video返回，返回后上传服务器
                    Uri videoUri = data.getData();
                    String videoPath = UriUtil.getPath(context, videoUri);
                    uploadVideo(videoPath, this);
                    break;

                default:
                    break;
            }
        }
    }

    @Override
    protected void initViewEvent() {
        buttonBack.setOnClickListener(this);
        imgUpload.setOnClickListener(this);
        tvSure.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.img_upload:
                selectVedioFromLocal(context);
                break;
            case R.id.tv_sure:
                //testAubmit();
                checkData();
                break;
            default:
                break;
        }
    }

    String videoUrl = "http://cs.royalsecurity.cn/dcim/video/statement/video-9f97cdc7cd194f2e9ac43f116d385d4d.mp4";
    String picUrl = "http://cs.royalsecurity.cn/dcim/video/statement/video-9f97cdc7cd194f2e9ac43f116d385d4d.jpg";

    private void testAubmit() {
        WordVideoBean wordVideoBean = new WordVideoBean();
        wordVideoBean.setTitle("test_video_1");
        wordVideoBean.setUserId(userId);
        wordVideoBean.setLemmaId("1");
        wordVideoBean.setVideoThumbnailUrl(picUrl);
        wordVideoBean.setVideoUrl(videoUrl);
        submit(wordVideoBean);
    }


    //上传视频到服务器
    private void uploadVideo(final String path, UploadProgressListener uploadProgressListener) {
        File fileVideo = new File(path);
        Observable<String> observable = new LearnFinancialControl().uploadVideo(fileVideo, this);
        CommonDialogObserver<String> observer = new CommonDialogObserver<String>(this) {
            @Override
            public void onNext(String s) {
                super.onNext(s);
                Loger.i("uploadVideo--" + s);
                JSONObject jsonObject = JSON.parseObject(s);
                if (AppConstant.JSON_SUCCESS.equals(jsonObject.getString(AppConstant.JSON_RESPONSE))) {
                    SimpleToast.toastMessage("上传成功", Toast.LENGTH_SHORT);
                    isSuccess = true;
                    bean = JSON.parseObject(jsonObject.getString(AppConstant.JSON_DATA), VedioUploadResponseBean.class);
                    //   frameVideo.setVisibility(View.VISIBLE);
//                    im.setVisibility(View.GONE);
                    if (bean != null) {
//                        String videoUrl = bean.getOriginalUrl();
                        String videoThumnailUrl = bean.getVideoPicuture();
                        loadImage(context, imgUpload, videoThumnailUrl);
                    }

                    // setVideoThumbnail();


                } else {
                    isSuccess = false;
                    SimpleToast.toastMessage("上传失败，请重试", Toast.LENGTH_SHORT);
                }
            }

            @Override
            public void onError(Throwable t) {
                super.onError(t);
            }
        };

        RxHelper.bindOnUI(observable, observer);
    }

//提交前，判空

    private void checkData() {
        String title = edtTitleInput.getText().toString().trim();

        if (!isSuccess) {
            SimpleToast.toastMessage(getResources().getString(R.string.upload_video_first), Toast.LENGTH_SHORT);
            return;
        }
        if (TextUtils.isEmpty(title)) {
            SimpleToast.toastMessage(getResources().getString(R.string.hint_input_video_name), Toast.LENGTH_SHORT);
            edtTitleInput.requestFocus();
            return;
        }

//

        WordVideoBean wordVideoBean = new WordVideoBean();
        wordVideoBean.setTitle(title);
        wordVideoBean.setUserId(userId);
        wordVideoBean.setLemmaId(lemmaId);
        wordVideoBean.setVideoThumbnailUrl(bean.getVideoPicuture());
        wordVideoBean.setVideoUrl(bean.getOriginalUrl());
        submit(wordVideoBean);


    }

    //提交词条以及相关视频
    private void submit(WordVideoBean requestBean) {

        Observable<Boolean> observable = new LearnFinancialControl().submitWordVideo(requestBean);
        CommonDialogObserver<Boolean> observer = new CommonDialogObserver<Boolean>(this) {
            @Override
            public void onNext(Boolean aBoolean) {
                super.onNext(aBoolean);
                if (aBoolean) {
                    SimpleToast.toastMessage(getResources().getString(R.string.success), Toast.LENGTH_SHORT);
                    finish();
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
    public void onProgress(long currentBytesCount, long totalBytesCount) {

    }
}
