package com.yuas.pecker.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yuas.pecker.R;
import com.yuas.pecker.bean.pecker.FileInfoBean;
import com.yuas.pecker.bean.pecker.SaveAlarmAnalyzeBean;
import com.yuas.pecker.constant.AppConstant;
import com.yuas.pecker.fragment.AlarmingBeforeMonthNewFragment;
import com.yuas.pecker.fragment.AlarmingBeforeYearFragment;
import com.yuas.pecker.network.control.AlarmingAnalyzeControl;
import com.yuas.pecker.network.control.UploadProgressListener;
import com.yuas.pecker.observer.CommonDialogObserver;
import com.yuas.pecker.observer.RxHelper;
import com.yuas.pecker.utils.Loger;
import com.yuas.pecker.view.widget.SimpleToast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;

//按照类别 启动 预警体验页面
public class AlarmAnalyzeWithTypeActivity extends BaseActivity implements AlarmingBeforeYearFragment.FragmentListener,
        AlarmingBeforeMonthNewFragment.FragmentMonthListener {


    @BindView(R.id.ll_tab)
    LinearLayout llTab;
    @BindView(R.id.rl_analyze_first)
    RelativeLayout rlAnalyzeFirst;

    @BindView(R.id.rl_analyze_second)
    RelativeLayout rlAnalyzeSecond;

    @BindView(R.id.rl_analyze_third)
    RelativeLayout rlAnalyzeThird;

    @BindView(R.id.rl_analyze_fourth)
    RelativeLayout rlAnalyzeFourth;

    @BindView(R.id.tv_analyze_title_first)
    TextView tvAnalyzeTitleFirst;

    @BindView(R.id.tv_analyze_title_second)
    TextView tvAnalyzeTitleSeond;

    @BindView(R.id.tv_analyze_title_third)
    TextView tvAnalyzeTitleThird;

    @BindView(R.id.tv_analyze_title_fourth)
    TextView tvAnalyzeTitleFourth;

    @BindView(R.id.tv_analyze_submit_first)
    TextView tvAnalyzeSubmitFirst;

    @BindView(R.id.tv_state_first)
    TextView tvStateFirst;

    @BindView(R.id.tv_analyze_submit_second)
    TextView tvAnalyzeSubmitSecond;

    @BindView(R.id.tv_state_second)
    TextView tvStateSecond;


    @BindView(R.id.tv_analyze_submit_third)
    TextView tvAnalyzeSubmitThird;

    @BindView(R.id.tv_state_third)
    TextView tvStateThird;

    @BindView(R.id.tv_analyze_submit_fourth)
    TextView tvAnalyzeSubmitFourth;

    @BindView(R.id.tv_state_fourth)
    TextView tvStateFourth;


    @BindView(R.id.fl_tab)
    FrameLayout flTab;
    @BindView(R.id.tv_before_info)
    TextView tvBeforeInfo;
    @BindView(R.id.tv_save)
    TextView tvSave;
    private static final String TAG = AlarmAnalyzeWithTypeActivity.class.getSimpleName();

    AlarmingBeforeYearFragment fragmentYear;
    AlarmingBeforeMonthNewFragment fragmentMonth;
    @BindView(R.id.textview_title)
    TextView tvTitle;

    @BindView(R.id.tv_tab_year)
    TextView tvTabYear;
    @BindView(R.id.tv_tab_month)
    TextView tvTabMonth;


    protected static final int REQUEST_CODE_FIRST = 1100;

    protected static final int REQUEST_CODE_SECOND = 1101;

    protected static final int REQUEST_CODE_THIRD = 1102;

    protected static final int REQUEST_CODE_FOURTH = 1103;


    protected List<String> excelsList = new ArrayList<>();
    protected int excelSize = 0;
    protected int optionalSize = 0;
    protected int witch = -1;
    protected String userId;
    protected int excelIndex = -1;

    Boolean isExpand = false;
    protected int requestCode = 0;
    private String intentType;
    private String analyzeType = "";
    private String uploadExcelType;
    private String uploadTimeType = "1";//本页面上的都是本年度的，上月度/年度在fragment里
    //    boolean firstVisibility = true;
    boolean secondVisibility, thirdVisibility, fourthVisibility = false;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_alarming);
        userId = "24";
        tvTitle.setText(getResources().getText(R.string.alarming_analyze));
        //  intent.putExtra(AppConstant.KEY_TYPE, intentTye);
        intentType = getIntent().getStringExtra(AppConstant.KEY_TYPE);
        analyzeType = getIntent().getStringExtra(AppConstant.KEY_TYPE_ANALYZE);
        Loger.e(TAG + "analyzeType" + analyzeType + "intentType" + intentType);
        initViewEvent();
        initViewByType(intentType);
        for (int i = 0; i < 4; i++) {
            excelsList.add(i, "");
        }

    }

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
        rlAnalyzeSecond.setVisibility((secondVisibility) ? View.VISIBLE : View.GONE);
        rlAnalyzeThird.setVisibility((thirdVisibility) ? View.VISIBLE : View.GONE);
        rlAnalyzeFourth.setVisibility((fourthVisibility) ? View.VISIBLE : View.GONE);


    }


    @Override
    protected void initViewEvent() {
        tvSave.setOnClickListener(this);
        tvBeforeInfo.setOnClickListener(this);
        tvAnalyzeSubmitFirst.setOnClickListener(this);
        tvAnalyzeSubmitSecond.setOnClickListener(this);
        tvAnalyzeSubmitThird.setOnClickListener(this);
        tvAnalyzeSubmitFourth.setOnClickListener(this);
        tvTabMonth.setOnClickListener(this);
        tvTabYear.setOnClickListener(this);

    }
//

    //1 月度：2  年度：3
    //   uploadExcelType：上传的类型：  所得税 "1" 增值税 "2" 资产负债："3" 利润："4"

    @Override
    public void onClick(View view) {
        super.onClick(view);
        int code;
        super.onClick(view);
        code = -1;
        switch (view.getId()) {
            case R.id.tv_before_info:
                flTabState(isExpand);
                break;
            case R.id.tv_save:
                //save judge the data
                checkData();
                break;
            case R.id.tv_analyze_submit_first:
                //6 所得税类
                code = REQUEST_CODE_FIRST;
                excelIndex = 0;
                uploadExcelType = "1";
                break;
            case R.id.tv_analyze_submit_second:
                //7 增值税类

                excelIndex = 1;
                uploadExcelType = "2";
                code = REQUEST_CODE_FIRST;
                break;
            case R.id.tv_analyze_submit_third:
                //印花税 8
                excelIndex = 2;
                uploadExcelType = "3";
                code = REQUEST_CODE_FIRST;
                break;
            case R.id.tv_analyze_submit_fourth:
                //收入类
                Loger.e("=-profit");
                excelIndex = 3;
                uploadExcelType = "4";
                code = REQUEST_CODE_FIRST;
                break;
            case R.id.tv_tab_year:
                initYearFragment();
                break;
            case R.id.tv_tab_month:
                initMonthFragment();
                break;

            default:
                break;

        }

        //上传报表点击事件
        if (code != -1) {
            selectExcel(code);
        }


    }


    // 去年月度和年度的展开/收起

    private void flTabState(Boolean expand) {
        Loger.e("--expand" + expand);
        if (expand) {
            //是展开，则收起
            llTab.setVisibility(View.GONE);

        } else {
            llTab.setVisibility(View.VISIBLE);
            initYearFragment();
        }

        isExpand = !expand;
    }

    //initData()

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_FIRST) {
            FileInfoBean bean = (FileInfoBean) data.getExtras().getSerializable("file");
            if (bean != null) {
                String path = bean.getAbPath();
                Loger.e(TAG + "excelPAth--" + path);
                //上传啊，带着requestCode 所得税 4、5、6、7  下面增值税 8、9、10
                uploadExcel(excelIndex, uploadExcelType, uploadTimeType, path, uploadProgressListener);

            }

        }
    }

    private void checkData() {

        //第一个所得税，都需要判断不为""
        if (excelsList.get(0).equals("")) {
            SimpleToast.toastMessage(getResources().getString(R.string.report_icomplete), Toast.LENGTH_SHORT);
            return;
        }

        //根据布局去判断 excelList哪些不应该是""

        switch (intentType) {

            // 增值税 & 全面体检
            case "2":
            case "9":
                //全部都要上传
                for (int i = 0; i < 4; i++) {
                    if (excelsList.get(i).equals("")) {
                        SimpleToast.toastMessage(getResources().getString(R.string.report_icomplete), Toast.LENGTH_SHORT);
                        return;
                    }
                }

                break;

            //收入类 & 成本类
            case "4":
            case "5":
//判断 1
                if (excelsList.get(1).equals("")) {
                    SimpleToast.toastMessage(getResources().getString(R.string.report_icomplete), Toast.LENGTH_SHORT);
                    return;
                }

                break;


            case "7":
                //利润类 判断2资产负债表
                if (excelsList.get(2).equals("")) {
                    SimpleToast.toastMessage(getResources().getString(R.string.report_icomplete), Toast.LENGTH_SHORT);
                    return;
                }

                break;
            case "8":
                //资产类  判断 2he 3
                if (excelsList.get(3).equals("")) {
                    SimpleToast.toastMessage(getResources().getString(R.string.report_icomplete), Toast.LENGTH_SHORT);
                    return;
                }
                if (excelsList.get(2).equals("")) {
                    SimpleToast.toastMessage(getResources().getString(R.string.report_icomplete), Toast.LENGTH_SHORT);
                    return;
                }

                break;
            default:
                break;
        }

        allList.clear();
        allList.addAll(excelsList);
        allList.addAll(annexYearList);
        allList.addAll(annexMonthList);

        SaveAlarmAnalyzeBean bean = new SaveAlarmAnalyzeBean();
        bean.setUid(Integer.valueOf(userId));
        bean.setList(allList);
        bean.setType(analyzeType);
        saveExcels(bean);
    }

    //initData()
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
        Loger.i(TAG + "subFile = " + path);
        Observable<String> observable = new AlarmingAnalyzeControl().uploadExcelByNameAndTime(excelType, type, userId, file, uploadProgressListener);
        CommonDialogObserver<String> observer = new CommonDialogObserver<String>(AlarmAnalyzeWithTypeActivity.this) {
            @Override
            public void onNext(String s) {
                super.onNext(s);
                Loger.e(TAG + "--upload --s" + s);
                JSONObject jsonObject = JSON.parseObject(s);
                String status = jsonObject.getString(AppConstant.JSON_RESPONSE);
                if (!status.equals(AppConstant.JSON_SUCCESS)) {
                    SimpleToast.toastMessage(getResources().getString(R.string.excel_data_error), Toast.LENGTH_SHORT);
                    return;
                }
                SimpleToast.toastMessage(getResources().getString(R.string.success_upload), Toast.LENGTH_SHORT);

                /*
                {"response":"error","message":"失败","data":{"errorCode":400002,"msg":"服务器异常"}}
                 */
//                TestResponseBean responseBean = new TestResponseBean();
//                responseBean.setResponse("error");
//                responseBean.setMessage("失败");
//                responseBean.setData(new TestResponseBean.DataBean(400002, "服务器异常"));
//                s = JSON.toJSONString(responseBean);
//                Loger.e(TAG + "--s.toJSONString --s" + s);
                refreshResult(code, file.getName());
                //"response":"success","message":"成功","data":{"dateStr":"1547123759541"}}

                //   JSONObject jsonObject = JSON.parseObject(s);
                JSONObject jsonData = JSON.parseObject(jsonObject.getString("data"));
//                if (jsonData.getInteger("errorCode") ==null) {
//
//                    return;
//                }


                String fileId = jsonData.getString("dateStr");
                excelsList.set(excelIndex, fileId);
                for (int i = 0; i < excelsList.size(); i++) {
                    String itemStr = excelsList.get(i);
                    Loger.e("---uploadexcelId" + itemStr);


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


    @Override
    protected void onResume() {
        super.onResume();

        Loger.e(TAG + "--activity--onResume");
    }

    private void saveExcels(SaveAlarmAnalyzeBean bean) {

        Observable<String> observable = new AlarmingAnalyzeControl().saveAlramAnalyzeData(bean);
        CommonDialogObserver<String> observer = new CommonDialogObserver<String>(AlarmAnalyzeWithTypeActivity.this) {
            @Override
            public void onNext(String s) {
                super.onNext(s);
                Loger.i("save-excel--" + s);
                JSONObject jsonObject = JSON.parseObject(s);
                String status = jsonObject.getString(AppConstant.JSON_RESPONSE);
                if (status.equals(AppConstant.JSON_SUCCESS)) {
                    JSONObject jsonObject1 = jsonObject.getJSONObject(AppConstant.JSON_DATA);
                    List<String> listString = JSON.parseArray(jsonObject1.getString("resList"), String.class);
                    ArrayList<String> arrayList = new ArrayList<>(listString.size());
                    arrayList.addAll(listString);
                    for (int i = 0; i < arrayList.size(); i++) {
                        Loger.e("--arrayList" + arrayList.get(i));
                    }
                    Intent intent = new Intent(AlarmAnalyzeWithTypeActivity.this, AlarmResultActivity.class);
                    intent.putStringArrayListExtra("list", arrayList);
                    startActivity(intent);

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

    private void refreshResult(int code, String fileName) {
        switch (code) {
            case 0:
                setText(tvAnalyzeSubmitFirst, tvStateFirst, fileName);

                break;
            case 1:
                setText(tvAnalyzeSubmitSecond, tvStateSecond, fileName);
                break;

            case 2:
                setText(tvAnalyzeSubmitThird, tvStateThird, fileName);
                break;
            case 3:
                setText(tvAnalyzeSubmitFourth, tvStateFourth, fileName);

            default:
                break;
        }
    }


    private void setText(TextView tvSubmitIncomeFirst, TextView tvStateIncomeFirst, String fileName) {
        tvSubmitIncomeFirst.setText(getResources().getText(R.string.re_upload));
        tvStateIncomeFirst.setText(fileName);
    }


    //选中 年
    private void initYearFragment() {
        if (fragmentYear == null) {
            //fragmentYear = new AlarmingBeforeYearFragment(intentType);
            fragmentYear = AlarmingBeforeYearFragment.newInstance(intentType);
        }
        addFragmentNotToStack(R.id.fl_tab, fragmentYear);
        //tvStoreCombine.setBackground(getResources().getDrawable(R.drawable.bg_combine_select));
        tvTabYear.setBackgroundColor(getResources().getColor(R.color.white));
        tvTabMonth.setBackgroundColor(getResources().getColor(R.color.grey_f6f9fb));
        tvTabYear.setTextColor(getResources().getColor(R.color.blue_528dd9));
        tvTabMonth.setTextColor(getResources().getColor(R.color.grey_97999e));
    }

    //选中 月
    private void initMonthFragment() {
        if (fragmentMonth == null) {
            fragmentMonth = AlarmingBeforeMonthNewFragment.newInstance(intentType);
        }
        addFragmentNotToStack(R.id.fl_tab, fragmentMonth);
        //tvStoreCombine.setBackground(getResources().getDrawable(R.drawable.bg_combine_select));
        tvTabMonth.setBackgroundColor(getResources().getColor(R.color.white));
        tvTabYear.setBackgroundColor(getResources().getColor(R.color.grey_f6f9fb));
        tvTabMonth.setTextColor(getResources().getColor(R.color.blue_528dd9));
        tvTabYear.setTextColor(getResources().getColor(R.color.grey_97999e));
    }

    UploadProgressListener uploadProgressListener = new UploadProgressListener() {
        @Override
        public void onProgress(long currentBytesCount, long totalBytesCount) {
            Loger.i("uploadProgressListener = " + currentBytesCount);
        }
    };

    List<String> annexYearList = new ArrayList<>();
    List<String> annexMonthList = new ArrayList<>();
    List<String> allList = new ArrayList<>();


    @Override
    public void innextExcelIist(List<String> excelListFragment) {
        Loger.e(TAG + "excelYearList" + excelListFragment.size());
        annexYearList.clear();
        annexYearList.addAll(excelListFragment);

    }


    @Override
    public void innextMonthExcelIist(List<String> excelList) {
        for (int i = 0; i < excelsList.size(); i++) {
            Loger.e("---excelMonthList" + excelsList.get(i));
        }
        annexMonthList.clear();
        annexMonthList.addAll(excelList);
    }


}
