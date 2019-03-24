package com.yuas.pecker.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.yuas.pecker.R;
import com.yuas.pecker.bean.pecker.ConfigParamsRequestBean;
import com.yuas.pecker.network.control.AnalyzeParamsControl;
import com.yuas.pecker.observer.CommonDialogObserver;
import com.yuas.pecker.observer.RxHelper;
import com.yuas.pecker.utils.Loger;
import com.yuas.pecker.view.widget.SimpleToast;

import butterknife.BindView;
import io.reactivex.Observable;

//（分析参数配置）--其他数据

public class ConfigOtherDataActivity extends BaseActivity {

    @BindView(R.id.edt_total_tax_import)
    EditText edtTotalTaxImport;

    @BindView(R.id.edt_total_tax_sale)
    EditText edtTotalTaxSale;

    @BindView(R.id.edt_total_tax_payable)
    EditText edtTotalTaxPayable;

    @BindView(R.id.edt_total_applicability_rate)
    EditText edtTotalTaxApplicabilityRate;

    @BindView(R.id.edt_total_tax_sale_by_simple)
    EditText edtTotalTaxSaleBySimple;


    @BindView(R.id.edt_total_tax_sale_export)
    EditText edtTotalTaxSaleExport;


    @BindView(R.id.edt_total_tax_sale_free)
    EditText edtTotalTaxSaleFree;
    @BindView(R.id.edt_total_tax_tansport_entry)
    EditText edtTotalTaxTransportEntry;


    @BindView(R.id.edt_cost_industry)
    EditText edtCostIndutry;
    @BindView(R.id.edt_main_business_average)
    EditText edtMainBusinessAverage;

    @BindView(R.id.button_back)
    ImageButton buttonBack;

    @BindView(R.id.textview_title)
    TextView tvTitle;

    @BindView(R.id.tv_sure)
    TextView tvSure;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_other_data);
        tvTitle.setText(R.string.other_data);
        initViewEvent();
    }


    @Override
    protected void initViewEvent() {
        buttonBack.setOnClickListener(this);
        tvTitle.setOnClickListener(this);
        tvSure.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

        commitData();
    }


    private void commitData() {
        String totalTaxSaleStr = edtTotalTaxSale.getText().toString();
        String totalTaxImportStr = edtTotalTaxImport.getText().toString();
        String totalTaxPayable = edtTotalTaxPayable.getText().toString();
        String totalTaxApplicabilityRateStr = edtTotalTaxApplicabilityRate.getText().toString();
        String totalTaxSaleBySimple = edtTotalTaxSaleBySimple.getText().toString();

        String totalTaxSaleExport = edtTotalTaxSaleExport.getText().toString();

        String totalTaxSaleFree = edtTotalTaxSaleFree.getText().toString();

        String totalTaxTransportEntry = edtTotalTaxTransportEntry.getText().toString();
        String costIndutryStr = edtCostIndutry.getText().toString();
        String mainBusinessAverageStr = edtMainBusinessAverage.getText().toString();

        if (!TextUtils.isEmpty(totalTaxSaleStr) && !TextUtils.isEmpty(totalTaxImportStr)
                && !TextUtils.isEmpty(totalTaxPayable)
                && !TextUtils.isEmpty(totalTaxApplicabilityRateStr) && !TextUtils.isEmpty(totalTaxSaleBySimple)
                && !TextUtils.isEmpty(totalTaxSaleExport)

                && !TextUtils.isEmpty(totalTaxSaleFree)
                && !TextUtils.isEmpty(totalTaxTransportEntry) && !TextUtils.isEmpty(costIndutryStr)
                && !TextUtils.isEmpty(mainBusinessAverageStr)
                ) {
            submit();
        } else {
            SimpleToast.toastMessage("填写信息不完整，请检查！", Toast.LENGTH_SHORT);
        }



    }

    private void submit() {
        ConfigParamsRequestBean bean = new ConfigParamsRequestBean();


        Observable<Boolean> observable = new AnalyzeParamsControl().saveCongfigParams(bean);
        CommonDialogObserver<Boolean> observer = new CommonDialogObserver<Boolean>(ConfigOtherDataActivity.this) {
            @Override
            public void onNext(Boolean s) {
                super.onNext(s);
                Loger.i("save-excel--" + s);
                SimpleToast.toastMessage("成功", Toast.LENGTH_SHORT);
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
