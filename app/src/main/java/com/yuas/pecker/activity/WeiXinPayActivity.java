package com.yuas.pecker.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.yuas.pecker.R;
import com.yuas.pecker.adapter.GridViewPicsAdapter;
import com.yuas.pecker.bean.pecker.PicBean;
import com.yuas.pecker.bean.pecker.RequestProblemBean;
import com.yuas.pecker.constant.AppConstant;
import com.yuas.pecker.network.control.ExpertsConsultControl;
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
import me.iwf.photopicker.PhotoPicker;

public class WeiXinPayActivity extends BaseActivity {

    private IWXAPI api;
    @BindView(R.id.textview_title)
    TextView tvTitle;

    @BindView(R.id.edt_inuput_problem)
    EditText edtInputProblem;

    @BindView(R.id.grid_view_pics)
    GridView gridViewPics;

    @BindView(R.id.btn_pay)
    Button btn_pay;
    private GridViewPicsAdapter adapter;
    private String answerId = "";
    private List<PicBean> list;
    List<String> uploadPicsStr;
    private ArrayList<String> selectedPhotos = new ArrayList<>();
    private String userId = "24";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professor_consult_layout);
        list = new ArrayList<>();
        uploadPicsStr = new ArrayList<>();
        String appId = "wx76b1e1fa9c0b3bd6"; // 填应用AppId: wx76b1e1fa9c0b3bd6
        api = WXAPIFactory.createWXAPI(this, appId);
        adapter = new GridViewPicsAdapter(WeiXinPayActivity.this, list);
        gridViewPics.setAdapter(adapter);
        tvTitle.setText("专家咨询");
        initViewEvent();

        if (getIntent() != null) {
            answerId = getIntent().getStringExtra(AppConstant.KEY_TYPE);
        }

        adapter.setOnItemClickListener(new GridViewPicsAdapter.OnItemClickListener() {
            @Override
            public void addImage(int pos) {
                SimpleToast.toastMessage("addImage--" + pos, Toast.LENGTH_SHORT);
                takePics();

            }

            @Override
            public void viewImage(int pos) {
                SimpleToast.toastMessage("viewImage--" + pos, Toast.LENGTH_SHORT);
                toActivityWithParams(ViewBigImageActivity.class, "url", AppConstant.BASE_URL_PIC + uploadPicsStr.get(0));

            }

            @Override
            public void deleteImage(int pos) {
                SimpleToast.toastMessage("deleteImage--" + pos, Toast.LENGTH_SHORT);
                list.remove(pos);
                uploadPicsStr.remove(pos);
                adapter.notifyDataSetChanged();
            }
        });

    }


    private void takePics() {
        PhotoPicker.builder()
                .setPhotoCount(1)
                .setShowCamera(true)
                .setPreviewEnabled(false)
                .setSelected(selectedPhotos)
                .start(WeiXinPayActivity.this);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PhotoPicker.REQUEST_CODE:
                    //从选择图片返回的,则得到图片后上传服务器
                    getPics(data);
                    break;
                default:
                    break;
            }
        }
    }

    //从选择图片返回后，得到图片并上传服务器
    private void getPics(Intent data) {
        selectedPhotos.clear();
        selectedPhotos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);

//        List<File> files = new ArrayList<>();
//        File file;
//        for (int i = 0; i < list.size(); i++) {
//            file = new File(list.get(i).getPhotoPath());
//            files.add(file);
//        }
        List<File> files = new ArrayList<>();
        File file;
        file = new File(selectedPhotos.get(0));
        files.add(file);

        uploadPics(files, uploadProgressListener);

    }

    @Override
    protected void initViewEvent() {
        btn_pay.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        if (view.getId() == R.id.btn_pay) {
            //  payByWX();
//            toActivity(UsersFeedBackActivity.class);
            chedata();
        }
    }

    //检查问题填写完整
    private void chedata() {
        String content = edtInputProblem.getText().toString().trim();
        if (TextUtils.isEmpty(content)) {
            SimpleToast.toastMessage(getResources().getString(R.string.no_empty_content), Toast.LENGTH_SHORT);
            return;
        }

        RequestProblemBean requestProblemBean = new RequestProblemBean();

        requestProblemBean.setQuizzerId(userId);
        requestProblemBean.setDescription(content);
        requestProblemBean.setImageList(uploadPicsStr);
        requestProblemBean.setAnswererId(answerId);
        submitProlem(requestProblemBean);
    }


    private void uploadPics(final List<File> files, UploadProgressListener uploadProgressListener) {
        Observable<String> observable = new ExpertsConsultControl().uploadSinglePic(files.get(0), uploadProgressListener);
        CommonDialogObserver<String> observer = new CommonDialogObserver<String>(WeiXinPayActivity.this) {
            @Override
            public void onNext(String s) {
                super.onNext(s);
                /*
                 {"response":"success","message":"成功","data":{"url":"1548227721233.jpg"}}
                 */
                Loger.e("subFile" + s);
                JSONObject jsonObject = JSON.parseObject(s);
                //int code = jsonObject.getIntValue(AppConstant.JSON_CODE);
                String responseStr = jsonObject.getString("response");
                if (responseStr.equals("success")) {
                    SimpleToast.toastMessage("图片上传成功", Toast.LENGTH_SHORT);

                    JSONObject jsonData = JSON.parseObject(jsonObject.getString("data"));
                    uploadPicsStr.add(jsonData.getString("url"));
                    PicBean picBean = new PicBean();
                    picBean.setPhotoPath(selectedPhotos.get(0));
                    list.add(picBean);
                    for (int i = 0; i < list.size(); i++) {
                        Loger.e("--picBean-----" + list.get(i).getPhotoPath());
                    }
                    adapter.notifyDataSetChanged();

                } else if (responseStr.equals("error")) {
//                    uploadStatus = false;
                    SimpleToast.toastMessage("图片上传失败，请重试", Toast.LENGTH_SHORT);

                }

            }

            @Override
            public void onError(Throwable t) {
                super.onError(t);

                if (t.getMessage().equals("401")) {
                    SimpleToast.toastMessage("登录超时，请重新登录", Toast.LENGTH_SHORT);

                } else {
                    SimpleToast.toastMessage(t.getMessage(), Toast.LENGTH_SHORT);
                }
            }
        };
        RxHelper.bindOnUI(observable, observer);
    }


    //payWxin
    private void payByWX() {

        String url = "https://wxpay.wxutil.com/pub_v2/app/app_pay.php";
        Button payBtn = (Button) findViewById(R.id.btn_pay);
        payBtn.setEnabled(false);

//        try{
//            byte[] buf = Util.httpGet(url);
//            if (buf != null && buf.length > 0) {
//                String content = new String(buf);
////                Log.e("get server pay params:",content);
//                JSONObject json = new JSONObject(content);
//                if(null != json && !json.has("retcode") ){
//                    PayReq req = new PayReq();
//                    //req.appId = "wxf8b4f85f3a794e77";  // ������appId
//                    req.appId			= json.getString("appid");
//                    req.partnerId		= json.getString("partnerid");
//                    req.prepayId		= json.getString("prepayid");
//                    req.nonceStr		= json.getString("noncestr");
//                    req.timeStamp		= json.getString("timestamp");
//                    req.packageValue	= json.getString("package");
//                    req.sign			= json.getString("sign");
//                    req.extData			= "app data"; // optional
//
//                    api.sendReq(req);
//                }else{
//                    Log.d("PAY_GET", "���ش���"+json.getString("retmsg"));
//                    Toast.makeText(PayActivity.this, "���ش���"+json.getString("retmsg"), Toast.LENGTH_SHORT).show();
//                }
//            }else{
//                Log.d("PAY_GET", "�������������");
//                Toast.makeText(PayActivity.this, "�������������", Toast.LENGTH_SHORT).show();
//            }
//        }catch(Exception e){
//            Log.e("PAY_GET", "�쳣��"+e.getMessage());
//            Toast.makeText(PayActivity.this, "�쳣��"+e.getMessage(), Toast.LENGTH_SHORT).show();
//        }
        payBtn.setEnabled(true);
    }

    //提交问题
    private void submitProlem(RequestProblemBean requestProblemBean) {
        Observable<Boolean> observable = new ExpertsConsultControl().submitProblems(requestProblemBean);
        CommonDialogObserver<Boolean> observer = new CommonDialogObserver<Boolean>(this) {
            @Override
            public void onNext(Boolean aBoolean) {
                super.onNext(aBoolean);
                if (aBoolean) {
                    SimpleToast.toastMessage(getResources().getString(R.string.success), Toast.LENGTH_SHORT);
                    //付费提交成功--咨询结果 -问答列表
                    toActivity(QueAnswerRecycleViewActivity.class);
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

    UploadProgressListener uploadProgressListener = new UploadProgressListener() {
        @Override
        public void onProgress(long currentBytesCount, long totalBytesCount) {
            Loger.i("uploadProgressListener = " + currentBytesCount);
        }
    };
}
