package com.yuas.pecker.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yuas.pecker.R;
import com.yuas.pecker.bean.pecker.LearnFinancialBean;


import java.util.List;

//财务报表学习Aapter

public class ReportsNameRecycleAdapter extends BaseQuickAdapter<LearnFinancialBean, BaseViewHolder> {

    List<LearnFinancialBean> data;

    public ReportsNameRecycleAdapter(List<LearnFinancialBean> data) {
        super(R.layout.item_learn_financial, data);
        this.data = data;
    }

    @Override
    protected void convert(BaseViewHolder helper, LearnFinancialBean item) {
        helper.setText(R.id.tv_learn_title, item.getStatementName());
    }


}
