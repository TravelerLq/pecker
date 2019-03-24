package com.yuas.pecker.activity;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.yuas.pecker.R;
import com.yuas.pecker.bean.pecker.CustomMadeRequestBean;
import com.yuas.pecker.bean.pecker.IndustryTypeBean;
import com.yuas.pecker.network.control.CustomMadeControl;
import com.yuas.pecker.observer.CommonDialogObserver;
import com.yuas.pecker.observer.RxHelper;
import com.yuas.pecker.utils.Loger;
import com.yuas.pecker.utils.TimePickerUtils;
import com.yuas.pecker.view.widget.SimpleToast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import cn.addapp.pickers.listeners.OnMoreItemPickListener;
import cn.addapp.pickers.picker.LinkagePicker;
import cn.addapp.pickers.util.DateUtils;
import io.reactivex.Observable;

/**
 * Created by liqing on 2018/12/28.
 * 私人定制详情
 */

public class CustomDetailActivity extends BaseActivity {

    @BindView(R.id.button_back)
    ImageButton backBtn;
    @BindView(R.id.textview_title)
    TextView titleTxt;

    @BindView(R.id.edt_contact)
    EditText edtContact;

    @BindView(R.id.edt_phone)
    EditText edtPhone;

    @BindView(R.id.edt_register_address)
    EditText edtRegisterAddress;

    @BindView(R.id.edt_address_manage)
    EditText edtAddressManage;

    //公司性质选择
    @BindView(R.id.tv_company_nature)
    TextView tvSelectNature;

    //增值税选择
    @BindView(R.id.tv_company_vat)
    TextView tvSelectVat;

    //所得税方式选择
    @BindView(R.id.tv_company_income)
    TextView tvSelectIncome;

    //行业分类选择
    @BindView(R.id.tv_industry_select)
    TextView tvIndustrySelect;

    @BindView(R.id.tv_sure)
    TextView tvSure;

    CustomMadeRequestBean requestBean;

    String select;//请选择str
    private List<String> listNature = new ArrayList<>(); //公司性质集合
    private List<String> listVatWay = new ArrayList<>(); //增值税方式集合
    private List<String> listIncomeWay = new ArrayList<>();//所得税方式集合

    private ArrayList<String> customList = new ArrayList<>();//私人定制项集合

    private List<String> firstIndustry = new ArrayList<>();//第一列的行业集合
    private List<String> secondIndustry = new ArrayList<>();//第二列的行业集合

    private List<IndustryTypeBean> industryTypeBeanList = new ArrayList<>();//所有行业的集合


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_detail);
        getIndustry();
        select = getResources().getString(R.string.select_please);
        initViewEvent();
        titleTxt.setText(getResources().getString(R.string.custom_made));
        if (getIntent() != null) {
            customList = getIntent().getStringArrayListExtra("list");
        }
        listNature = Arrays.asList(getResources().getStringArray(R.array.company_type));
        listIncomeWay = Arrays.asList(getResources().getStringArray(R.array.income_tax_way));
        listVatWay = Arrays.asList(getResources().getStringArray(R.array.vat_way));

    }


    @Override
    protected void initViewEvent() {
        tvSelectIncome.setOnClickListener(this);
        tvSelectNature.setOnClickListener(this);
        tvSelectVat.setOnClickListener(this);
        tvIndustrySelect.setOnClickListener(this);
        backBtn.setOnClickListener(this);
        tvSure.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.tv_company_nature:
                TimePickerUtils.getInstance().
                        onListPicker(CustomDetailActivity.this, listNature, tvSelectNature);
                break;
            case R.id.tv_company_vat:
                TimePickerUtils.getInstance().
                        onListPicker(CustomDetailActivity.this, listVatWay, tvSelectVat);
                break;
            case R.id.tv_company_income:

                TimePickerUtils.getInstance().
                        onListPicker(CustomDetailActivity.this, listIncomeWay, tvSelectIncome);
                break;
            case R.id.tv_industry_select:
                onLinkagePicker(tvIndustrySelect);
                break;
            case R.id.tv_sure:
                cheData();
                break;
            default:
                break;
        }

    }

    private void cheData() {
        String contact = edtContact.getText().toString().trim();
        String contactWay = edtPhone.getText().toString().trim();
        String addressRegister = edtRegisterAddress.getText().toString().trim();
        String addressManage =edtAddressManage.getText().toString().trim();
        String nature = tvSelectNature.getText().toString().trim();
        String vat = tvSelectVat.getText().toString().trim();
        String income = tvSelectIncome.getText().toString().trim();
        String industy = tvIndustrySelect.getText().toString().trim();


        if (TextUtils.isEmpty(contact)) {
            SimpleToast.toastMessage(getResources().getString(R.string.no_empty_contact), Toast.LENGTH_SHORT);
            edtContact.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(contactWay)) {
            SimpleToast.toastMessage(getResources().getString(R.string.no_empty_contact_way), Toast.LENGTH_SHORT);
            edtPhone.requestFocus();
            return;
        }

        //企业注册地
        if (TextUtils.isEmpty(addressRegister)) {
            SimpleToast.toastMessage(getResources().getString(R.string.no_empty_address_register), Toast.LENGTH_SHORT);
            edtRegisterAddress.requestFocus();
            return;
        }
        //企业经营地
        if (TextUtils.isEmpty(addressManage)) {
            SimpleToast.toastMessage(getResources().getString(R.string.no_empty_address_manage), Toast.LENGTH_SHORT);
            edtAddressManage.requestFocus();
            return;
        }

        //企业性质
        if (nature.equals(select)||TextUtils.isEmpty(nature)) {
            SimpleToast.toastMessage(getResources().getString(R.string.please_select_nature), Toast.LENGTH_SHORT);
            return;
        }

        if (vat.equals(select)) {
            SimpleToast.toastMessage(getResources().getString(R.string.please_select_vat), Toast.LENGTH_SHORT);
            return;
        }

        if (income.equals(select)) {
            SimpleToast.toastMessage(getResources().getString(R.string.please_select_income), Toast.LENGTH_SHORT);
            return;
        }

        if (industy.equals(select)) {
            SimpleToast.toastMessage(getResources().getString(R.string.please_select_industy), Toast.LENGTH_SHORT);
            return;
        }
        requestBean = new CustomMadeRequestBean();
        requestBean.setIndividuationList(customList);
        requestBean.setNature(nature);
        requestBean.setLinkMan(contact);
        requestBean.setPhone(contactWay);
        requestBean.setIncomeColl(income);
        requestBean.setVatColl(vat);

        submit(requestBean);

    }


    public void onLinkagePicker(final TextView view) {

        final LinkagePicker.DataProvider provider = new LinkagePicker.DataProvider() {

            @Override
            public boolean isOnlyTwo() {
                return true;
            }

            @Override
            public List<String> provideFirstData() {
                Loger.e("---provideFirstData--");

                return firstIndustry;
            }


            @Override
            public List<String> provideSecondData(int firstIndex) {
                Loger.e("---provideSecondData--");
                secondIndustry.clear();
                long start = System.currentTimeMillis();
                long end = 0;
//                //二级下面的KeyValueBean
                List<IndustryTypeBean.SonListBean> secondIndustryList = industryTypeBeanList.get(firstIndex).getSonList();
                for (int i = 0; i < secondIndustryList.size(); i++) {
                    secondIndustry.add(secondIndustryList.get(i).getName());
                }
                end = System.currentTimeMillis();
                Loger.e("provideSecondData--" + "end-start=" + (end - start));
                return secondIndustry;

            }

            @Override
            public List<String> provideThirdData(int firstIndex, int secondIndex) {

                Loger.e("---secondIndex--" + secondIndex);
                ArrayList<String> thirdList = new ArrayList<>();
                for (int i = 1; i <= (firstIndex == 0 ? 12 : 24); i++) {
                    String str = DateUtils.fillZero(i) + "third";
                    if (firstIndex == 0) {
                        str += "￥";
                    } else {
                        str += "$";
                    }
                    thirdList.add(str);
                }
                return thirdList;
            }

        };

        LinkagePicker picker = new LinkagePicker(this, provider);
        picker.setCanLoop(true);
        picker.setSelectedIndex(0, 0);
        //picker.setSelectedItem("12", "9");
        picker.setOnMoreItemPickListener(new OnMoreItemPickListener<String>() {

            @Override
            public void onItemPicked(String first, String second, String third) {
                Loger.e(first + "-" + second + "-" + third);

                view.setText("" + second);

            }

        });

        picker.show();
    }
    //获取行业类别

    private void getIndustry() {
        Observable<List<IndustryTypeBean>> observable = new CustomMadeControl().getIndustryList();

        CommonDialogObserver<List<IndustryTypeBean>> observer = new CommonDialogObserver<List<IndustryTypeBean>>(this) {
            @Override
            public void onNext(List<IndustryTypeBean> list) {
                super.onNext(list);
                if (list.size() > 0) {
                    industryTypeBeanList.addAll(list);
                }
                for (int i = 0; i < list.size(); i++) {
                    firstIndustry.add(list.get(i).getName());
                }

            }

            @Override
            public void onError(Throwable t) {
                super.onError(t);

            }
        };


        RxHelper.bindOnUI(observable, observer);

    }

    //提交预定申请
    private void submit(CustomMadeRequestBean requestBean) {
        Observable<Boolean> observable = new CustomMadeControl().sumitCustomMade(requestBean);

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


    //检测返回按钮

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            cheData();
        }
        return super.onKeyDown(keyCode, event);
    }
}
