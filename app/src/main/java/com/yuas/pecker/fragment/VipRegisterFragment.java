package com.yuas.pecker.fragment;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.yuas.pecker.R;
import com.yuas.pecker.bean.pecker.VipRegisterRequestBean;
import com.yuas.pecker.network.control.VipLoginControl;
import com.yuas.pecker.observer.CommonDialogObserver;
import com.yuas.pecker.observer.DialogObserverHolder;
import com.yuas.pecker.observer.RxHelper;
import com.yuas.pecker.utils.TimePickerUtils;
import com.yuas.pecker.view.widget.SimpleToast;

import org.reactivestreams.Subscription;

import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

//琢税鸟 -vip login-手机密码登录

public class VipRegisterFragment extends BaseFragment implements View.OnClickListener, DialogObserverHolder {


    EditText edtTel;
    EditText edtPsw;
    TextView tvVipSure;
    TextView tvSelectCompanyNature, tvSelectVatWay, tvSelectIncomeWay, tvSelectFieldsType;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_vip_register, container, false);
        edtTel = (EditText) view.findViewById(R.id.edt_tel);
        edtPsw = (EditText) view.findViewById(R.id.edt_psw);
        tvVipSure = (TextView) view.findViewById(R.id.tv_vip_sure);
        tvSelectCompanyNature = (TextView) view.findViewById(R.id.tv_select_company_nature);
        tvSelectVatWay = (TextView) view.findViewById(R.id.tv_select_vat_way);
        tvSelectIncomeWay = (TextView) view.findViewById(R.id.tv_select_income_way);
        tvSelectFieldsType = (TextView) view.findViewById(R.id.tv_select_fields_type);

        initEvent();
        return view;
    }


    private void initEvent() {
        tvVipSure.setOnClickListener(this);
        tvSelectCompanyNature.setOnClickListener(this);
        tvSelectVatWay.setOnClickListener(this);
        tvSelectIncomeWay.setOnClickListener(this);
        tvSelectFieldsType.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.tv_vip_sure:
                checkData();
                break;
            case R.id.tv_select_company_nature:
                select(R.array.company_type, tvSelectCompanyNature);
                break;
            case R.id.tv_select_vat_way:
                select(R.array.vat_way, tvSelectVatWay);
                break;
            case R.id.tv_select_income_way:
                select(R.array.income_tax_way, tvSelectIncomeWay);
                break;
            case R.id.tv_select_fields_type:
                select(R.array.fields_type, tvSelectFieldsType);
                break;
            default:
                break;
        }
    }

    private void checkData() {
        String tel = edtTel.getText().toString().trim();
        String psw = edtPsw.getText().toString().trim();
        String companyNature = tvSelectCompanyNature.getText().toString().trim();
        String vatWay = tvSelectVatWay.getText().toString().trim();
        String incomeTaxWay = tvSelectIncomeWay.getText().toString().trim();
        String fieldType = tvSelectFieldsType.getText().toString().trim();

        if (TextUtils.isEmpty(tel)) {
            SimpleToast.toastMessage(getResources().getString(R.string.no_empty_tel), Toast.LENGTH_SHORT);
            return;
        }


        if (TextUtils.isEmpty(psw)) {
            SimpleToast.toastMessage(getResources().getString(R.string.no_empty_psw), Toast.LENGTH_SHORT);
            return;
        }

        String defaultStr = getResources().getString(R.string.select_please);

        if (companyNature.equals(defaultStr) || incomeTaxWay.equals(defaultStr) || vatWay.equals(defaultStr) || fieldType.equals(defaultStr)) {
            SimpleToast.toastMessage(getResources().getString(R.string.no_select), Toast.LENGTH_SHORT);
            return;
        }
        VipRegisterRequestBean registerRequestBean = new VipRegisterRequestBean();

        vipRegister(registerRequestBean);
    }

    //注册
    private void vipRegister(VipRegisterRequestBean registerRequestBean) {
        Observable<Boolean> observable = new VipLoginControl().vipRegister(registerRequestBean);
        CommonDialogObserver<Boolean> observer = new CommonDialogObserver<Boolean>(this) {
            @Override
            public void onNext(Boolean aBoolean) {
                super.onNext(aBoolean);
                SimpleToast.toastMessage(getResources().getString(R.string.success),Toast.LENGTH_SHORT);
            }

            @Override
            public void onError(Throwable t) {
                super.onError(t);
            }
        };
        RxHelper.bindOnUI(observable, observer);
    }


    //select

    private void select(int resId, View view) {

        Resources res = getResources();
        //  String[] status = res.getStringArray(R.array.approval_no);
        String[] status = res.getStringArray(resId);
        List<String> allStatus = Arrays.asList(status);
        TimePickerUtils.getInstance().onListDataPicker(VipRegisterFragment.this.getActivity(), allStatus, view);

    }


    @Override
    public FragmentManager getSupportFragmentManager() {
        return getSupportFragmentManager();
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
}
