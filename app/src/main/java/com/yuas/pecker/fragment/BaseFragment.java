package com.yuas.pecker.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trello.rxlifecycle2.components.support.RxFragment;
import com.yuas.pecker.bean.BaseBean;
import com.yuas.pecker.constant.AppConstant;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public class BaseFragment extends RxFragment {

    protected FragmentActivity baseActivity;
    protected Unbinder unBinder;




    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        baseActivity=(FragmentActivity)context;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if(container != null) {
            unBinder = ButterKnife.bind(this, container);
        }
        return container;

    }

    /**
     * 页面跳转
     *
     * @param fragment
     * @param clz
     * @param baseBean
     * @param <T>
     */
    protected <T> void toAtivityWithParams(Fragment fragment, Class<T> clz, BaseBean baseBean) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(AppConstant.SERIAL_KEY, baseBean);
        Intent intent = new Intent(getActivity(), clz);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * 跳转页面
     *
     * @param clz 跳转到扫描的类
     * @param <T>
     */
    protected <T> void toActivityWithKeyValue(Fragment fragment, Class<T> clz,String key,String value) {

        Intent intent = new Intent(getActivity(), clz);
        intent.putExtra(key,value);

        startActivity(intent);

    }
    /**
     * 跳转页面
     *
     * @param clz 跳转到的类
     * @param <T>
     */
    protected <T> void toActivity(Fragment fragment, Class<T> clz) {

        Intent intent = new Intent(getActivity(), clz);
        startActivity(intent);

    }




    /**
     * 跳转页面
     *
     * @param clz 跳转到 T 类
     * @param <T>
     */
    protected <T> void toActivityWithId(Fragment fragment, Class<T> clz,String id) {

        Intent intent = new Intent(getActivity(), clz);
        intent.putExtra("id",id);


        startActivity(intent);

    }

    @Override
    public void onDestroyView() {
        if(unBinder!=null){
            unBinder.unbind();
        }
        super.onDestroyView();

    }

    @Override
    public void onDetach() {
        super.onDetach();
        baseActivity=null;
    }
}
