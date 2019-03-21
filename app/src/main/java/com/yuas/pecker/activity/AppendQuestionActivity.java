package com.yuas.pecker.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.yuas.pecker.R;
import com.yuas.pecker.bean.pecker.RequestAppendQueBean;
import com.yuas.pecker.constant.AppConstant;
import com.yuas.pecker.network.control.QuesAnswerControl;
import com.yuas.pecker.observer.CommonDialogObserver;
import com.yuas.pecker.observer.RxHelper;
import com.yuas.pecker.view.widget.SimpleToast;

import butterknife.BindView;
import io.reactivex.Observable;

/**
 * 回复
 */

public class AppendQuestionActivity extends BaseActivity {


    @BindView(R.id.button_back)
    ImageButton buttonBack;

    @BindView(R.id.textview_title)
    TextView tvTitle;

    @BindView(R.id.edt_question_append)
    EditText edtQuestionAppend;

    @BindView(R.id.tv_sure)
    TextView tvSure;
    private String questionId = "";//回复问题ID
    private String userId = "24";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_append_question_layout);
        tvTitle.setText(getResources().getString(R.string.question_reply));
        initViewEvent();
        if(getIntent()!=null){
            questionId=getIntent().getStringExtra(AppConstant.KEY_TYPE);
        }


    }

    @Override
    protected void initViewEvent() {
        buttonBack.setOnClickListener(this);
        tvSure.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        if (view.getId() == R.id.tv_sure) {
            //提交问题
            checkData();
        }
    }

    private void checkData() {
        String contentStr = edtQuestionAppend.getText().toString();
        if (TextUtils.isEmpty(contentStr)) {
            SimpleToast.toastMessage(getResources().getString(R.string.no_empty_content),
                    Toast.LENGTH_SHORT);
            return;
        }

        appendQuestion(contentStr);

    }



    //追问回复

    private void appendQuestion(String content) {
        RequestAppendQueBean bean = new RequestAppendQueBean();
        bean.setMsgValue(content);
        bean.setQueId(questionId);
        bean.setUid(userId);
        Observable<Boolean> observable = new QuesAnswerControl().appendQuestion(bean);
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
                SimpleToast.toastMessage(t.getMessage(), Toast.LENGTH_SHORT);

            }
        };
        RxHelper.bindOnUI(observable, observer);

    }
}
