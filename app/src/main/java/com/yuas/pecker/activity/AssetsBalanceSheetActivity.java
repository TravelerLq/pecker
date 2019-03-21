package com.yuas.pecker.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;


import com.yuas.pecker.R;
import com.yuas.pecker.bean.pecker.ConfigParamsRequestBean;
import com.yuas.pecker.network.control.AnalyzeParamsControl;
import com.yuas.pecker.observer.CommonDialogObserver;
import com.yuas.pecker.observer.RxHelper;
import com.yuas.pecker.utils.Loger;

import butterknife.BindView;
import io.reactivex.Observable;

// 分析 资产负债表数据

public class AssetsBalanceSheetActivity extends BaseActivity {
    @BindView(R.id.edt_assets_total_begin_first)
    EditText edtAssetsTotalBeginFirst;
    @BindView(R.id.edt_assets_total_begin_second)
    EditText edtAssetsTotalBeginSecond;
    @BindView(R.id.edt_assets_total_begin_thrird)
    EditText edtAssetsTotalBeginThrid;

    @BindView(R.id.edt_assets_total_end_first)
    EditText edtAssetsTotalEndFirst;
    @BindView(R.id.edt_assets_total_end_second)
    EditText edtAssetsTotalEndSecond;
    @BindView(R.id.edt_assets_total_end_thrid)
    EditText edtAssetsTotalEndThird;


    @BindView(R.id.tv_save)
    TextView tvSave;

    @BindView(R.id.button_back)
    ImageButton buttonBack;

    @BindView(R.id.textview_title)
    TextView tvTitle;


    // /fxd/save
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assets_sheet);
        tvTitle.setText(getResources().getText(R.string.balance_sheet));
        initViewEvent();
    }


    @Override
    protected void initViewEvent() {
        buttonBack.setOnClickListener(this);
        tvSave.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

        if (view.getId() == R.id.tv_save) {
            commitData();
        }
    }

    private void commitData() {

        String assetsTotalBeginFirst = edtAssetsTotalBeginFirst.getText().toString();
        String assetsTotalBeginSecond = edtAssetsTotalBeginSecond.getText().toString();
        String assetsTotalBeginThird = edtAssetsTotalBeginThrid.getText().toString();

        String assetsTotalEndFirst = edtAssetsTotalEndFirst.getText().toString();
        String assetsTotalEndSecond = edtAssetsTotalEndSecond.getText().toString();
        String assetsTotalEndThird = edtAssetsTotalEndThird.getText().toString();

        ConfigParamsRequestBean bean = new ConfigParamsRequestBean();
        Observable<Boolean> observable = new AnalyzeParamsControl().saveCongfigParams(bean);
        CommonDialogObserver<Boolean> observer = new CommonDialogObserver<Boolean>(AssetsBalanceSheetActivity.this) {
            @Override
            public void onNext(Boolean s) {
                super.onNext(s);
                Loger.i("save-excel--" + s);
                if (s) {
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

}
