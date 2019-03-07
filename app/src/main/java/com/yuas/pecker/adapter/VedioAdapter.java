package com.yuas.pecker.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yuas.pecker.R;
import com.yuas.pecker.bean.pecker.WordExplainBean;

import java.util.List;

//财务报表学习Aapter

public class VedioAdapter extends BaseQuickAdapter<WordExplainBean.VideoListBean, BaseViewHolder> {

    List<WordExplainBean.VideoListBean> data;

    public VedioAdapter(List<WordExplainBean.VideoListBean> data) {
        super(R.layout.item_vedio, data);
        this.data = data;
    }

    @Override
    protected void convert(BaseViewHolder helper, WordExplainBean.VideoListBean item) {
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


}
