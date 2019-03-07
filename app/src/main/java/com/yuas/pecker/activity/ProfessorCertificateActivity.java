package com.yuas.pecker.activity;

import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.yuas.pecker.R;
import com.yuas.pecker.bean.ProApplyReuqestBean;
import com.yuas.pecker.bean.pecker.ProfessorStateBean;
import com.yuas.pecker.network.control.ExpertsConsultControl;
import com.yuas.pecker.observer.CommonDialogObserver;
import com.yuas.pecker.observer.RxHelper;
import com.yuas.pecker.utils.TimePickerUtils;
import com.yuas.pecker.view.CountEditText;
import com.yuas.pecker.view.widget.SimpleToast;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;

/*
专家认证Activity
 */


public class ProfessorCertificateActivity extends BaseActivity {


    @BindView(R.id.textview_title)
    TextView tvTitle;

    @BindView(R.id.edt_name)
    EditText edtName;

    @BindView(R.id.edt_title_no)
    EditText edtTitelNo;

    @BindView(R.id.edt_age)
    EditText edtAge;

    @BindView(R.id.edt_good_at)
    EditText edtGoodAt;

    @BindView(R.id.tv_select_sex)
    TextView tvSelectSex;

    @BindView(R.id.edt_title)
    EditText edtTitle;

    @BindView(R.id.edt_bank_account)
    EditText edtBankAccount;

    @BindView(R.id.edt_experient)
    CountEditText edtExperient;
    @BindView(R.id.button_back)
    ImageButton buttonBack;

    @BindView(R.id.tv_sure)
    TextView tvSure;
    private String userId = "24";
    private String professorId = "4";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professor_certificate);
        tvTitle.setText(getResources().getString(R.string.expert_certification));
        initViewEvent();
        // initTestData();
        getExpertState();
    }


    private void initTestData() {

        edtName.setText("ly");
        edtBankAccount.setText("230897651");
        edtGoodAt.setText("business");
        edtTitle.setText("资深专家");
        edtTitelNo.setText("29050");
        edtAge.setText("49");

    }


    @Override
    protected void initViewEvent() {
        tvSelectSex.setOnClickListener(this);
        tvSure.setOnClickListener(this);
        buttonBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
//            case R.id.button_back:
//                Loger.e("---baseActivity--btnback-");
//                finish();
//                break;
            case R.id.tv_select_sex:
                selectSex();
                break;
            case R.id.tv_sure:
                checkData();
                break;
            default:
                break;
        }
    }


    private void getExpertState() {

        Observable<ProfessorStateBean> observable = new ExpertsConsultControl().getProfessorState(professorId);
        CommonDialogObserver<ProfessorStateBean> observer = new CommonDialogObserver<ProfessorStateBean>(this) {
            @Override
            public void onNext(ProfessorStateBean professorStateBean) {
                super.onNext(professorStateBean);
                if (professorStateBean != null) {
                    initData(professorStateBean);
                } else {
                    SimpleToast.toastMessage("暂无信息，请新增", Toast.LENGTH_SHORT);
                }
            }

            @Override
            public void onError(Throwable t) {
                super.onError(t);
            }
        };
        RxHelper.bindOnUI(observable, observer);

    }

    private void initData(ProfessorStateBean bean) {
        //
        /*
        String name = edtName.getText().toString().trim();
        String age = edtAge.getText().toString().trim();
        String sex = tvSelectSex.getText().toString().trim();
        String titelNo = edtTitelNo.getText().toString().trim();
        String title = edtTitle.getText().toString().trim();
        String goodAt = edtGoodAt.getText().toString().trim();
        String bankAccount = edtBankAccount.getText().toString().trim();
        String experience = edtExperient.getText().toString().trim();
         */
        edtAge.setText(bean.getAge() + "");
        edtName.setText(bean.getTeachName());
        // "sex": 0, 男
        if (bean.getSex() == 0) {
            tvSelectSex.setText("男");
        } else {
            tvSelectSex.setText("女");
        }

        edtTitelNo.setText(bean.getCertificate());
        edtTitle.setText(bean.getTitle());
        edtGoodAt.setText(bean.getTerritory());
        edtBankAccount.setText(bean.getBankAcc());
        edtExperient.setText(bean.getExperience());

    }

    private void selectSex() {


        Resources res = getResources();
        //  String[] status = res.getStringArray(R.array.approval_no);
        String[] status = res.getStringArray(R.array.sex);
        List<String> allStatus = Arrays.asList(status);
        TimePickerUtils.getInstance().onListDataPicker(this, allStatus, tvSelectSex);


    }

    //判空检查
    private void checkData() {
        String name = edtName.getText().toString().trim();
        String age = edtAge.getText().toString().trim();
        String sex = tvSelectSex.getText().toString().trim();
        String titelNo = edtTitelNo.getText().toString().trim();
        String title = edtTitle.getText().toString().trim();
        String goodAt = edtGoodAt.getText().toString().trim();
        String bankAccount = edtBankAccount.getText().toString().trim();
        String experience = edtExperient.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            SimpleToast.toastMessage(getResources().getString(R.string.no_empty_name), Toast.LENGTH_SHORT);
            edtName.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(titelNo)) {
            SimpleToast.toastMessage(getResources().getString(R.string.no_empty_title_cert_no), Toast.LENGTH_SHORT);
            edtTitelNo.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(sex) || sex.equals(getResources().getString(R.string.sex_input))) {
            SimpleToast.toastMessage(getResources().getString(R.string.no_empty_sex), Toast.LENGTH_SHORT);
            return;
        }

        if (TextUtils.isEmpty(age)) {
            SimpleToast.toastMessage(getResources().getString(R.string.no_empty_age), Toast.LENGTH_SHORT);
            edtAge.requestFocus();
            return;
        }


        if (TextUtils.isEmpty(title)) {
            SimpleToast.toastMessage(getResources().getString(R.string.no_empty_title), Toast.LENGTH_SHORT);
            edtTitle.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(goodAt)) {
            SimpleToast.toastMessage(getResources().getString(R.string.no_empty_good), Toast.LENGTH_SHORT);
            edtGoodAt.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(bankAccount)) {
            SimpleToast.toastMessage(getResources().getString(R.string.no_empty_bank), Toast.LENGTH_SHORT);
            edtBankAccount.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(experience)) {
            SimpleToast.toastMessage(getResources().getString(R.string.no_empty_experiment), Toast.LENGTH_SHORT);
            edtExperient.requestFocus();
            return;
        }

        ProApplyReuqestBean applyReuqestBean = new ProApplyReuqestBean();
        applyReuqestBean.setTeachName(name);
        applyReuqestBean.setAge(age);
        applyReuqestBean.setBankAcc(bankAccount);
        applyReuqestBean.setCertificate(titelNo);
        applyReuqestBean.setTerritory(goodAt);
        applyReuqestBean.setExperience(experience);
        if (sex.equals("男")) {
            applyReuqestBean.setSex(1);
        } else {
            applyReuqestBean.setSex(0);
        }

        applyReuqestBean.setUid(userId);
        applyReuqestBean.setTitle(title);
        apply(applyReuqestBean);

    }

    private void apply(ProApplyReuqestBean applyReuqestBean) {
        Observable<Boolean> observable = new ExpertsConsultControl().apply(applyReuqestBean);
        CommonDialogObserver<Boolean> observer = new CommonDialogObserver<Boolean>(this) {
            @Override
            public void onNext(Boolean aBoolean) {
                super.onNext(aBoolean);
                if (aBoolean) {
                    SimpleToast.toastMessage(getResources().getString(R.string.success), Toast.LENGTH_SHORT);
                    //申请成功

                    finish();
                }
            }

            @Override
            public void onError(Throwable t) {
                super.onError(t);
                SimpleToast.toastMessage(getResources().getString(R.string.failed), Toast.LENGTH_SHORT);

            }
        };

        RxHelper.bindOnUI(observable, observer);
    }




}
