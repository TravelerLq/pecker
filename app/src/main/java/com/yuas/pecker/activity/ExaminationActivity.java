package com.yuas.pecker.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;


import com.yuas.pecker.R;
import com.yuas.pecker.bean.pecker.ExaminationBean;
import com.yuas.pecker.network.control.ExaminationControl;
import com.yuas.pecker.observer.CommonDialogObserver;
import com.yuas.pecker.observer.RxHelper;

import java.util.ArrayList;

import butterknife.BindView;
import io.reactivex.Observable;

public class ExaminationActivity extends BaseActivity {

    @BindView(R.id.edt_interest_start)
    EditText edtInterestStart;
    @BindView(R.id.edt_interest_end)
    EditText edtInterestEnd;


    @BindView(R.id.edt_net_profit)
    EditText edtNetProfit;

    //本/上 年度主营业务收入
    @BindView(R.id.edt_current_income)
    EditText edtCurrentIncome;
    @BindView(R.id.edt_last_income)
    EditText edtLastIncome;


    // <!--本/上 年度主营业务成本-->
    @BindView(R.id.edt_current_cost)
    EditText edtCurrentCost;
    @BindView(R.id.edt_last_cost)
    EditText edtLastCost;


    //  <!--本/上 年度应纳所得税额-->
    @BindView(R.id.edt_current_income_tax)
    EditText edtCurrentIncomeTax;
    @BindView(R.id.edt_last_income_tax)
    EditText edtLastIncomeTax;

    //  <!--本/上 年度进项税额-->
    @BindView(R.id.edt_current_import_tax)
    EditText edtCurrentImportTax;
    @BindView(R.id.edt_last_import_tax)
    EditText edtLastImportTax;

    //  <!--本/上 年度销项税额-->
    @BindView(R.id.edt_current_sale_tax)
    EditText edtCurrentSaleTax;
    @BindView(R.id.edt_last_sale_tax)
    EditText edtLastSaleTax;

    @BindView(R.id.button_back)
    ImageButton buttonBack;

    @BindView(R.id.textview_title)
    TextView tvTitle;

    @BindView(R.id.tv_sure)
    TextView tvSure;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_examination_layout);
        initViewEvent();
        tvTitle.setText(getResources().getString(R.string.company_experience_examination));

    }


    @Override
    protected void initViewEvent() {
        tvSure.setOnClickListener(this);
        buttonBack.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        if (view.getId() == R.id.tv_sure) {
            commitData();
        }
    }


    private void commitData() {

        String edtInterestStartStr = edtInterestStart.getText().toString();
        String edtInterestEndStr = edtInterestEnd.getText().toString();

        String edtNetProfitStr = edtNetProfit.getText().toString();


        String edtCurrentIncomeStr = edtCurrentIncome.getText().toString();
        String edtLastIncomeStr = edtLastIncome.getText().toString();


        String edtCurrentCostStr = edtCurrentCost.getText().toString();
        String edtLastCostStr = edtLastCost.getText().toString();

        String edtCurrentIncomeTaxStr = edtCurrentIncomeTax.getText().toString();
        String edtLastIncomeTaxStr = edtLastIncomeTax.getText().toString();

        String edtCurrentImportTaxStr = edtCurrentImportTax.getText().toString();
        String edtLastImportTaxStr = edtLastImportTax.getText().toString();


        String edtCurrentSaleTaxStr = edtCurrentSaleTax.getText().toString();
        String edtLastSaleTaxStr = edtLastSaleTax.getText().toString();


        /*
        {"本期数额所有者权益合计期初数":"1","本期数额所有者权益合计期末数":"1",
        "本期数额净利润本期数":"1",
        "本期数额主营业务收入":"1","往期年度主营业务收入":"1",
        "本期数额主营业务成本":"1","往期年度主营业务成本":"1",
        "本期数额应纳所得税额":"1","往期年度应纳所得税额":"1",
        "本期数额进项税额本月数":"1","往期月度本月数进项税额":"1",
        "本期数额销项税额本月数":"1","往期月度销项税额本月数":"1"}
        mmp 下面字段不友好 无关我事  找后台无果
         */

        ExaminationBean bean = new ExaminationBean();
        bean.setParameterA(edtInterestStartStr);
        bean.setParameterB(edtInterestEndStr);

        bean.setParameterC(edtNetProfitStr);

        bean.setParameterD(edtCurrentIncomeStr);
        bean.setParameterE(edtLastIncomeStr);

        bean.setParameterF(edtCurrentCostStr);
        bean.setParameterG(edtLastCostStr);


        bean.setParameterH(edtCurrentIncomeTaxStr);
        bean.setParameterI(edtLastIncomeTaxStr);

        bean.setParameterJ(edtCurrentImportTaxStr);
        bean.setParameterK(edtLastImportTaxStr);

        bean.setParameterL(edtCurrentSaleTaxStr);
        bean.setParameterM(edtLastSaleTaxStr);


        Observable<ArrayList<String>> observable = new ExaminationControl().submitExamination(bean);
        CommonDialogObserver<ArrayList<String>> observer =
                new CommonDialogObserver<ArrayList<String>>(this) {

                    @Override
                    public void onNext(ArrayList<String> list) {
                        super.onNext(list);
                        //把AlarmResultActivity补充上去，虽然去掉了，但是可能以后会用到
                        //  Intent intent = new Intent(ExaminationActivity.this, AlarmResultActivity.class);()
                        Intent intent = new Intent(ExaminationActivity.this, LearnFinancialDetailActivity.class);
                        intent.putStringArrayListExtra("list", list);
                        startActivity(intent);
                        finish();

                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                    }
                };

        RxHelper.bindOnUIActivityLifeCycle(observable, observer, ExaminationActivity.this);


    }


}
