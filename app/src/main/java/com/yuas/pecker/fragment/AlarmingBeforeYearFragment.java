package com.yuas.pecker.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yuas.pecker.R;
import com.yuas.pecker.activity.ExcelRecycleActivity;
import com.yuas.pecker.bean.pecker.FileInfoBean;
import com.yuas.pecker.constant.AppConstant;
import com.yuas.pecker.network.control.AlarmingAnalyzeControl;
import com.yuas.pecker.network.control.UploadProgressListener;
import com.yuas.pecker.observer.CommonDialogObserver;
import com.yuas.pecker.observer.DialogObserverHolder;
import com.yuas.pecker.observer.RxHelper;
import com.yuas.pecker.utils.Loger;
import com.yuas.pecker.view.widget.SimpleToast;
;

import org.reactivestreams.Subscription;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cn.addapp.pickers.listeners.OnItemPickListener;
import cn.addapp.pickers.listeners.OnSingleWheelListener;
import cn.addapp.pickers.picker.SinglePicker;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

import static android.app.Activity.RESULT_OK;

/**
 * 往期年度
 */

public class AlarmingBeforeYearFragment extends BaseFragment implements DialogObserverHolder, View.OnClickListener {

    private static final String TAG = AlarmingBeforeYearFragment.class.getSimpleName();

    LinearLayout rlFirst;

    LinearLayout rlSecond;
    LinearLayout rlThird;
    LinearLayout rlFourth;

    TextView tvSubmitFrist;
    TextView tvSubmitSecond;
    TextView tvSubmitThird;
    TextView tvSubmitFourth;
    TextView tvAnnexSelectYear;
    boolean secondVisibility, thirdVisibility, fourthVisibility = false;
    private static final int REQUEST_FRAGMENT_UPLOAD = 1019; //excel选择后返回码
    private int whitch = -1;//标志上传excel的位置
    private String intentType;//activity传入的intent类型
    private String uploadExcelType;//上传的类型
    private String uploadTimeType = "3";//表示往期年度
    private String userId = "24";
    protected List<String> excelsList = new ArrayList<>(); //excel集合
    private FragmentListener fragmentListener;
    private List<String> yearList;//年份的集合
    private boolean yearSelect = false;//是否选择了年份


    public static AlarmingBeforeYearFragment newInstance(String argument) {
        AlarmingBeforeYearFragment fragment = new AlarmingBeforeYearFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", argument);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alarming_analyze_before, container, false);
        rlFirst = (LinearLayout) view.findViewById(R.id.rl_before_year_third);
        rlSecond = (LinearLayout) view.findViewById(R.id.rl_before_year_second);
        rlThird = (LinearLayout) view.findViewById(R.id.rl_before_year_third);
        rlFourth = (LinearLayout) view.findViewById(R.id.rl_before_year_fourth);

        tvAnnexSelectYear = (TextView) view.findViewById(R.id.tv_annex_select_year);
        tvSubmitFrist = (TextView) view.findViewById(R.id.tv_before_year_submit_first);
        tvSubmitSecond = (TextView) view.findViewById(R.id.tv_before_year_submit_second);
        tvSubmitThird = (TextView) view.findViewById(R.id.tv_before_year_submit_third);
        tvSubmitFourth = (TextView) view.findViewById(R.id.tv_before_year_submit_fourth);

        tvSubmitFrist.setOnClickListener(this);
        tvSubmitSecond.setOnClickListener(this);
        tvSubmitThird.setOnClickListener(this);
        tvSubmitFourth.setOnClickListener(this);
        tvAnnexSelectYear.setOnClickListener(this);
        if (getArguments() != null) {
            initViewByType(getArguments().getString("type"));
        }

        for (int i = 0; i < 4; i++) {
            excelsList.add(i, "");
        }
        initCalendar();
        return view;
    }

    //根据Activity 传过来的intentType值，来初始化View 的状态

    private void initViewByType(String intentType) {
        switch (intentType) {
            // &印花税 &费用类
            case "1":
            case "3":
            case "6":
                secondVisibility = false;
                thirdVisibility = false;
                fourthVisibility = false;
                break;

            case "2":
            case "9":
                // 增值税 & 全面体检
                secondVisibility = true;
                thirdVisibility = true;
                fourthVisibility = true;
                break;


            case "4":
            case "5":

                //收入类 & 成本类
                secondVisibility = true;
                thirdVisibility = false;
                fourthVisibility = false;
                break;


            case "7":
                //利润类
                secondVisibility = false;
                thirdVisibility = true;
                fourthVisibility = false;
                break;
            case "8":
                //资产类
                secondVisibility = false;
                thirdVisibility = true;
                fourthVisibility = true;
                break;
            default:
                break;
        }

        setView(secondVisibility, thirdVisibility, fourthVisibility);
    }

    // 布局隐藏和显示

    private void setView(boolean secondVisibility, boolean thirdVisibility, boolean fourthVisibility) {
        rlSecond.setVisibility((secondVisibility) ? View.VISIBLE : View.GONE);
        rlThird.setVisibility((thirdVisibility) ? View.VISIBLE : View.GONE);
        rlFourth.setVisibility((fourthVisibility) ? View.VISIBLE : View.GONE);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_before_year_submit_first:
                whitch = 0;
                uploadExcelType = "1";
                break;
            case R.id.tv_before_year_submit_second:
                whitch = 1;
                uploadExcelType = "2";
                break;
            case R.id.tv_before_year_submit_third:
                uploadExcelType = "3";
                whitch = 2;
                break;
            case R.id.tv_before_year_submit_fourth:
                uploadExcelType = "4";
                whitch = 3;
                break;
            case R.id.tv_annex_select_year:
                whitch = -1;
                onListPicker(getActivity(), yearList, tvAnnexSelectYear);
                break;
            default:
                break;

        }


        if (whitch != -1) {
            if (!yearSelect) {
                SimpleToast.toastMessage(getResources().getString(R.string.year_notice), Toast.LENGTH_SHORT);
                return;
            } else {
                selectExcel();
            }

        }
    }

    @Override
    public void onPause() {
        super.onPause();
        Loger.e(TAG+"-onPause" );
        fragmentListener.innextExcelIist(excelsList);

    }

    private void initCalendar() {
        yearList = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        int start = calendar.get(Calendar.YEAR) - 20;
        int end = calendar.get(Calendar.YEAR) - 1;
        for (int i = start; i <= end; i++) {
            yearList.add("" + i);
        }
        //onListPicker

    }

    public void onListPicker(Activity context, final List<String> allStatus, final View view) {
        final SinglePicker<String> picker = new SinglePicker<String>(context, allStatus);
        picker.setCanLoop(false);
        picker.setWheelModeEnable(true);
        picker.setTopPadding(15);
        picker.setSelectedIndex(allStatus.size() / 2);
        picker.setOnSingleWheelListener(new OnSingleWheelListener() {
            @Override
            public void onWheeled(int i, String s) {

            }
        });

        picker.setOnItemPickListener(new OnItemPickListener<String>() {
            @Override
            public void onItemPicked(int i, String s) {
                Loger.e("viewName=" + (view instanceof TextView));
                if (view instanceof TextView) {
                    ((TextView) view).setText(allStatus.get(i));
                    yearSelect = true;

                } else if (view instanceof EditText) {
                    ((EditText) view).setText(allStatus.get(i));
                }

            }
        });
        picker.setWeightEnable(true);

        picker.show();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Loger.e(TAG+"---onActivityResult");
        if (resultCode == RESULT_OK && requestCode == REQUEST_FRAGMENT_UPLOAD) {
            FileInfoBean bean = (FileInfoBean) data.getExtras().getSerializable("file");
            if (bean != null) {
                String path = bean.getAbPath();
                Loger.e("excelPAth--" + path);
                //上传啊，带着requestCode 所得税 4、5、6、7  下面增值税 8、9、10

                uploadExcel(whitch, uploadExcelType, uploadTimeType, path, uploadProgressListener);

            }

        }

    }

    //shnagchuan
    //

    /**
     * @param code                   上传的位置
     * @param excelType              :所得税 "1" 增值税 "2" 资产负债："3" 利润："4"
     * @param type                   "1":本年度 "2"往期月度 "3"："往期"年度
     * @param path
     * @param uploadProgressListener
     */
    private void uploadExcel(final int code, final String excelType, String type, String path,
                             UploadProgressListener uploadProgressListener) {
        final File file = new File(path);
        Loger.i("subFile = " + path);
        Observable<String> observable = new AlarmingAnalyzeControl().uploadExcelByNameAndTime(excelType, type, userId, file, uploadProgressListener);
        CommonDialogObserver<String> observer = new CommonDialogObserver<String>(this) {
            @Override
            public void onNext(String s) {
                super.onNext(s);
                JSONObject jsonObject = JSON.parseObject(s);
                String status = jsonObject.getString(AppConstant.JSON_RESPONSE);
                if (!status.equals(AppConstant.JSON_SUCCESS)) {
                    SimpleToast.toastMessage(getResources().getString(R.string.excel_data_error), Toast.LENGTH_SHORT);
                    return;
                }
                SimpleToast.toastMessage(getResources().getString(R.string.success_upload), Toast.LENGTH_SHORT);
                refreshResult(code, file.getName());
                JSONObject jsonData = JSON.parseObject(jsonObject.getString("data"));

                String fileId = jsonData.getString("dateStr");
                excelsList.set(whitch, fileId);

                for (int i = 0; i < excelsList.size(); i++) {
                    String itemStr = excelsList.get(i);
                    Loger.e(TAG+"---fragment_year_excelList" + itemStr);

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


    private void refreshResult(int code, String fileName) {
        switch (code) {
            case 0:
                setText(tvSubmitFrist, fileName);

                break;
            case 1:
                setText(tvSubmitSecond, fileName);
                break;

            case 2:
                setText(tvSubmitThird, fileName);
                break;
            case 3:
                setText(tvSubmitFourth, fileName);
                break;
            default:
                break;
        }
    }

    private void setText(TextView tvSubmitIncomeFirst, String fileName) {
        tvSubmitIncomeFirst.setText(getResources().getText(R.string.re_upload));
    }

    //选择Excel文件
    protected void selectExcel() {

        Intent intent = new Intent(getActivity(), ExcelRecycleActivity.class);
        startActivityForResult(intent, REQUEST_FRAGMENT_UPLOAD);

    }

    UploadProgressListener uploadProgressListener = new UploadProgressListener() {
        @Override
        public void onProgress(long currentBytesCount, long totalBytesCount) {
            Loger.i("uploadProgressListener = " + currentBytesCount);
        }
    };


    @Override
    public void onAttach(Context context) {
        fragmentListener = (FragmentListener) context;
        super.onAttach(context);


    }

    //


    @Override
    public void onDestroyView() {
      //  fragmentListener.innextExcelIist(excelsList);
        super.onDestroyView();
    }

    public interface FragmentListener {
        void innextExcelIist(List<String> excelList);
    }

    @Override
    public FragmentManager getSupportFragmentManager() {
        return getActivity().getSupportFragmentManager();
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
