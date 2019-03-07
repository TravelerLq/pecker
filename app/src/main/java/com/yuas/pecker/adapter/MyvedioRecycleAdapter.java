package com.yuas.pecker.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yuas.pecker.R;
import com.yuas.pecker.bean.pecker.MyvedioResponseBean;


import java.util.List;

public class MyvedioRecycleAdapter extends BaseQuickAdapter<MyvedioResponseBean.ContentBean, BaseViewHolder> {

    List<MyvedioResponseBean.ContentBean> data;

    public MyvedioRecycleAdapter(@Nullable List<MyvedioResponseBean.ContentBean> data) {
        super(R.layout.item_vedio, data);
        this.data = data;

    }

    @Override
    protected void convert(BaseViewHolder helper, MyvedioResponseBean.ContentBean item) {
        helper.setText(R.id.tv_vedio_title, item.getTitle());

        RequestOptions options = new RequestOptions()  //@mipmap/ic_video_player
                .placeholder(R.mipmap.ic_video_player)
                .error(R.mipmap.ic_video_player)
                .fallback(R.mipmap.ic_video_player)
                .override(com.bumptech.glide.request.target.Target.SIZE_ORIGINAL);
        Glide.with(mContext)
                .load(item.getVideoThumbnailUrl())
                .apply(options)
                .into((ImageView) helper.getView(R.id.iv_vedio_screenshot));

    }

    public void setList(List<MyvedioResponseBean.ContentBean> listAdd) {

        data.addAll(listAdd);
        notifyDataSetChanged();
    }


    public void refreshList(List<MyvedioResponseBean.ContentBean> listAdd) {

        data.clear();
        data.addAll(listAdd);
        notifyDataSetChanged();
    }
}
