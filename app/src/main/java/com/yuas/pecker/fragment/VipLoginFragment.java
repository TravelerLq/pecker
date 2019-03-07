package com.yuas.pecker.fragment;

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
import com.yuas.pecker.activity.ProfessorCertificateActivity;
import com.yuas.pecker.network.control.VipLoginControl;
import com.yuas.pecker.observer.CommonDialogObserver;
import com.yuas.pecker.observer.DialogObserverHolder;
import com.yuas.pecker.observer.RxHelper;
import com.yuas.pecker.view.widget.SimpleToast;

import org.reactivestreams.Subscription;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

//琢税鸟 -vip login-手机密码登录

public class VipLoginFragment extends BaseFragment implements View.OnClickListener, DialogObserverHolder {


    EditText edtTel;
    EditText edtPsw;
    TextView tvProfessorCertificate, tvVipLogin, tvVisitorLogin;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_vip_login, container, false);
        edtTel = (EditText) view.findViewById(R.id.edt_tel);
        edtPsw = (EditText) view.findViewById(R.id.edt_psw);
        tvProfessorCertificate = (TextView) view.findViewById(R.id.tv_professor_certificate);
        tvVipLogin = (TextView) view.findViewById(R.id.tv_vip_login);
        tvVisitorLogin = (TextView) view.findViewById(R.id.tv_visitor_login);

        initEvent();
        return view;
    }


    private void initEvent() {
        tvVipLogin.setOnClickListener(this);
        tvVisitorLogin.setOnClickListener(this);
        tvProfessorCertificate.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tv_professor_certificate:
                toActivity(VipLoginFragment.this, ProfessorCertificateActivity.class);
                break;
            case R.id.tv_vip_login:
                checkData();
                break;
            case R.id.tv_visitor_login:
                break;

            default:
                break;
        }
    }


    private void checkData() {
        String tel = edtTel.getText().toString().trim();
        String psw = edtPsw.getText().toString().trim();


        if (TextUtils.isEmpty(tel)) {
            SimpleToast.toastMessage(getResources().getString(R.string.no_empty_tel), Toast.LENGTH_SHORT);
            return;
        }


        if (TextUtils.isEmpty(psw)) {
            SimpleToast.toastMessage(getResources().getString(R.string.no_empty_psw), Toast.LENGTH_SHORT);
            return;
        }


        vipLogin(tel, psw);
    }

    //注册
    private void vipLogin(String userName, String psw) {
        Observable<Boolean> observable = new VipLoginControl().vipLogin(userName, psw);
        CommonDialogObserver<Boolean> observer = new CommonDialogObserver<Boolean>(this) {
            @Override
            public void onNext(Boolean aBoolean) {
                super.onNext(aBoolean);
                SimpleToast.toastMessage(getResources().getString(R.string.success), Toast.LENGTH_SHORT);
            }

            @Override
            public void onError(Throwable t) {
                super.onError(t);
            }
        };
        RxHelper.bindOnUI(observable, observer);
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
