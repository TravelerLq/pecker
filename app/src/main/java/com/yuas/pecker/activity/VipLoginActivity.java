package com.yuas.pecker.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;


import com.yuas.pecker.R;
import com.yuas.pecker.fragment.VipLoginFragment;
import com.yuas.pecker.fragment.VipRegisterFragment;

import butterknife.BindView;

/**
 * vip登录
 */

public class VipLoginActivity extends BaseActivity implements ViewPager.OnPageChangeListener, TabLayout.OnTabSelectedListener {


    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

    @BindView(R.id.view_page)
    ViewPager viewPager;

    @BindView(R.id.button_back)
    ImageButton buttonBack;

    @BindView(R.id.textview_title)
    TextView tvTitle;
    //
    private String[] tabString = new String[]{"登录", "注册"}; //tab title string array

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vip_login);
        initViewEvent();
        viewPager.addOnPageChangeListener(this);
        tabLayout.addOnTabSelectedListener(this);

        tvTitle.setText(getResources().getString(R.string.vip_login));
        for (int i = 0; i < tabString.length; i++) {
            tabLayout.addTab(tabLayout.newTab().setText(tabString[i]));
        }
        tabLayout.setTabMode(TabLayout.MODE_FIXED);

        // tabLayout.setTabsFromPagerAdapter(tabAdapter);
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return 2;
            }

            @Override
            public Fragment getItem(int position) {
                if (position == 0) {
                    return new VipLoginFragment();
                }
                return new VipRegisterFragment();


            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return tabString[position % tabString.length];
            }
        });

        tabLayout.setupWithViewPager(viewPager);
    }

    // 初始化TabLayout


    @Override
    public void onClick(View view) {
        super.onClick(view);
//        switch (view.getId()) {
//            case R.id.button_back:
//                Loger.e("---baseActivity--btnback-");
//                finish();
//                break;
//        }

    }

    @Override
    protected void initViewEvent() {
        buttonBack.setOnClickListener(this);
    }


    //
    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }


    ////viewPager滑动之后显示触发
    @Override
    public void onPageSelected(int position) {
        tabLayout.getTabAt(position).select();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
