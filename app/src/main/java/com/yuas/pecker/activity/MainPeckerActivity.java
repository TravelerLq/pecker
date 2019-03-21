package com.yuas.pecker.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.yuas.pecker.R;
import com.yuas.pecker.fragment.AlarmingResultsFragment;
import com.yuas.pecker.fragment.HomePeckerFragment;
import com.yuas.pecker.fragment.QuesAnswerFragment;
import com.yuas.pecker.fragment.UserInfoFragment;
import com.yuas.pecker.service.NoticeMsgService;
import com.yuas.pecker.view.BottomNavigationItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainPeckerActivity extends BaseActivity {


    @BindView(R.id.button_back)
    ImageButton buttonBack;

    @BindView(R.id.tv_header)
    TextView tvHeader;
    @BindView(R.id.textview_title)
    TextView tvTitle;
    private Context mContext;
    private HomePeckerFragment homePeckerFragment;
    private List<BottomNavigationItem> data = new ArrayList<>(); //底部tab view的集合

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_pecker);
        ButterKnife.bind(this);
        mContext = MainPeckerActivity.this;

        tvTitle.setText(getResources().getString(R.string.home_title));
        buttonBack.setVisibility(View.GONE);
        tvHeader.setVisibility(View.GONE);
        homePeckerFragment = new HomePeckerFragment();
        addFragmentNotToStack(R.id.search_edit_frame, homePeckerFragment);

        BottomNavigationItem b1 = (BottomNavigationItem) findViewById(R.id.bni1);
        BottomNavigationItem b2 = (BottomNavigationItem) findViewById(R.id.bni2);
        BottomNavigationItem b3 = (BottomNavigationItem) findViewById(R.id.bni3);
        BottomNavigationItem b4 = (BottomNavigationItem) findViewById(R.id.bni4);


        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
        b4.setOnClickListener(this);
        tvHeader.setOnClickListener(this);

        data.add(b1);
        data.add(b2);
        data.add(b3);
        data.add(b4);
        setClick(0);
        initViewEvent();

    }


    @Override
    protected void initViewEvent() {

        Intent intent = new Intent(MainPeckerActivity.this, NoticeMsgService.class);
        startService(intent);
        //
        registerBrodcast();


    }




    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bni1:
                //啄税鸟
                setClick(0);
                addFragmentNotToStack(R.id.search_edit_frame, new HomePeckerFragment());
                break;
            case R.id.bni2:
                //咨询结果
                setClick(1);
                addFragmentNotToStack(R.id.search_edit_frame, new QuesAnswerFragment());
                //  toActivity(QueAnswerRecycleViewActivity.class);
                //  SimpleToast.toastMessage(getResources().getString(R.string.not_use), Toast.LENGTH_SHORT);
                break;
            case R.id.bni3:
                //预警结果
                setClick(2);
                addFragmentNotToStack(R.id.search_edit_frame, new AlarmingResultsFragment());
                //       SimpleToast.toastMessage(getResources().getString(R.string.not_use), Toast.LENGTH_SHORT);
                break;
            case R.id.bni4:
                //我的
                setClick(3);
                addFragmentNotToStack(R.id.search_edit_frame, new UserInfoFragment());
                break;

            default:
                break;
        }


    }

    private void setClick(int postion) {
        for (int i = 0; i < data.size(); i++) {
            if (i == postion) {
                data.get(i).setClick(true);
            } else {
                data.get(i).setClick(false);
            }

        }
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(receiver);
//        stopService()
        super.onDestroy();


    }
}
