package com.yuas.pecker.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yuas.pecker.R;
import com.yuas.pecker.activity.AnalyzeParamsConfigActivity;
import com.yuas.pecker.activity.MyVedioRecycleActivity;
import com.yuas.pecker.activity.PeckerAaboutActivity;
import com.yuas.pecker.activity.ProfessorCertificateActivity;
import com.yuas.pecker.activity.VipLoginActivity;


/**
 * 我的（用户信息）
 */


public class UserInfoFragment extends BaseFragment implements View.OnClickListener {

    LinearLayout llVipLogin;
    LinearLayout llAnalyzeParams;

    LinearLayout llMyVedio;
    LinearLayout llExpertsCertificate;

    LinearLayout llAboutPecker;
    TextView tvUnlogoin;;//退出str


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_user_info_pecker, container, false);
        llVipLogin = (LinearLayout) view.findViewById(R.id.ll_vip_login);
        llAnalyzeParams = (LinearLayout) view.findViewById(R.id.ll_analyze_params);
        llMyVedio = (LinearLayout) view.findViewById(R.id.ll_my_vedio);
        llExpertsCertificate = (LinearLayout) view.findViewById(R.id.ll_expert_certificate);
        llAboutPecker = (LinearLayout) view.findViewById(R.id.ll_about_pecker);

        tvUnlogoin=(TextView)view.findViewById(R.id.tv_unlogin) ;
        initEvent();
        return view;
    }


    private void initEvent() {
        llVipLogin.setOnClickListener(this);
        llAnalyzeParams.setOnClickListener(this);
        llMyVedio.setOnClickListener(this);
        llExpertsCertificate.setOnClickListener(this);
        llAboutPecker.setOnClickListener(this);
        tvUnlogoin.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_vip_login:
                //vip登陆
                toActivity(UserInfoFragment.this, VipLoginActivity.class);
                break;
            case R.id.ll_analyze_params:
                //分析参数配置
                toActivity(UserInfoFragment.this, AnalyzeParamsConfigActivity.class);
                break;
            case R.id.ll_my_vedio:
                //我的视频
                toActivity(UserInfoFragment.this, MyVedioRecycleActivity.class);
                break;
            case R.id.ll_expert_certificate:
                //专家认证
                toActivity(UserInfoFragment.this, ProfessorCertificateActivity.class);
              //  getExpertState();

                break;
            case R.id.ll_about_pecker:
                //关于
                toActivity(UserInfoFragment.this, PeckerAaboutActivity.class);
                break;
            case R.id.tv_unlogin:
                //退出
              UserInfoFragment.this.getActivity().finish();
                //
                break;
            default:
                break;
        }
    }

}
