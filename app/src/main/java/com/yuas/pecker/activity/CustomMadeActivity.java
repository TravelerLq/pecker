package com.yuas.pecker.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.yuas.pecker.R;
import com.yuas.pecker.utils.Loger;
import com.yuas.pecker.view.widget.SimpleToast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 个性化定制选择
 */

public class CustomMadeActivity extends BaseActivity {

    @BindView(R.id.tv_consult)
    TextView tvConsult;
    @BindView(R.id.tv_plan)
    TextView tvPlan;
    @BindView(R.id.tv_audit)
    TextView tvAudit;

    @BindView(R.id.tv_accounting)
    TextView tvAccounting;

    @BindView(R.id.button_back)
    ImageButton buttonBack;

    @BindView(R.id.textview_title)
    TextView tvTitle;
    @BindView(R.id.tv_sure)
    TextView tvSure;
    Boolean isSelectConsult = false; //税务咨询是否选择
    Boolean isSelectPlan = false;//纳税规划是否选择
    Boolean isSelectAudit = false;//纳税审计是否选择
    Boolean isSelectAccounting = false;//代理记账是否选择

    List<String> listCustoms; //存放选择定制项的集合
    String custom, plan, audit, accounting; //string 值见下，易懂

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custome_made);
        listCustoms = new ArrayList<>();
        initViewEvent();
        custom = getResources().getString(R.string.consult);
        plan = getResources().getString(R.string.plan);
        audit = getResources().getString(R.string.audit);
        accounting = getResources().getString(R.string.agency_accounting);

    }


    @Override
    protected void initViewEvent() {
        tvConsult.setOnClickListener(this);
        tvPlan.setOnClickListener(this);
        tvAudit.setOnClickListener(this);
        tvAccounting.setOnClickListener(this);
        tvSure.setOnClickListener(this);
        buttonBack.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {

            case R.id.tv_consult:
                isSelectConsult = !isSelectConsult;
                setSelect(isSelectConsult, tvConsult);
                break;

            case R.id.tv_plan:
                isSelectPlan = !isSelectPlan;
                setSelect(isSelectPlan, tvPlan);
                break;
            case R.id.tv_audit:
                isSelectAudit = !isSelectAudit;
                setSelect(isSelectAudit, tvAudit);
                break;
            case R.id.tv_accounting:
                isSelectAccounting = !isSelectAccounting;
                setSelect(isSelectAccounting, tvAccounting);
                break;
            case R.id.tv_sure:
                checkData();
                break;
            default:
                break;
        }
    }

    private void checkData() {
        //如果全false
        Loger.e("--isSelectConsult"+isSelectConsult);

        Loger.e("--isSelectAudit"+isSelectAudit);

        Loger.e("--isSelectAccounting"+isSelectAccounting);

        Loger.e("--isSelectPlan"+isSelectPlan);


        if (!isSelectConsult && !isSelectAudit && !isSelectAccounting && !isSelectPlan) {
            SimpleToast.toastMessage(getResources().getString(R.string.select_custom), Toast.LENGTH_SHORT);
            return;
        }

        if (isSelectConsult) {
            listCustoms.add(custom);
        }

        if (isSelectPlan) {
            listCustoms.add(plan);
        }

        if (isSelectAudit) {
            listCustoms.add(audit);
        }

        if (isSelectAccounting) {
            listCustoms.add(accounting);
        }
        toActivityWithListParams(CustomDetailActivity.class, "list", listCustoms);

    }


    public void setSelect(Boolean isSelect, TextView textView) {
        if (isSelect) {
            textView.setBackground(getResources().getDrawable(R.drawable.bg_regtangle_grey));
        } else {
            textView.setBackground(getResources().getDrawable(R.drawable.bg_regtangle_grey_xml));
        }
    }
}
