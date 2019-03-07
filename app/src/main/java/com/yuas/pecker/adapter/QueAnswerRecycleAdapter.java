package com.yuas.pecker.adapter;

/**
 * Created by liqing on 18/6/28.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yuas.pecker.R;
import com.yuas.pecker.bean.pecker.QueAnswerBean;
import com.yuas.pecker.constant.AppConstant;


import java.util.List;

/**
 * Created by liqing on 18/3/20.
 */

public class QueAnswerRecycleAdapter extends RecyclerView.Adapter<QueAnswerRecycleAdapter.MyViewHolder> implements View.OnClickListener {

    private Context context;
    private List<QueAnswerBean> list;
    private LayoutInflater inflater;
    private View view;
    private String goodAt;
    private String baseImageUrl;

    //1548653672844.jpg
    public QueAnswerRecycleAdapter(Context context, List<QueAnswerBean> list) {
        this.context = context;
        this.list = list;
        this.inflater = LayoutInflater.from(this.context);
        goodAt = context.getResources().getString(R.string.area_good_at);
        baseImageUrl = AppConstant.BASE_URL_PIC;
    }

    public static interface OnItemClickListener {
        void OnItemClick(View view, int pos);
    }

    public void setList(List<QueAnswerBean> listAdd) {

        list.addAll(listAdd);
        notifyDataSetChanged();
    }

    public void addItem(List<QueAnswerBean> item) {
        list.addAll(item);
        notifyDataSetChanged();
    }

    //    public static interface OnItemClickListener {
//        void OnItemClick(View view, int pos);
//    }
    public QueAnswerRecycleAdapter.OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(QueAnswerRecycleAdapter.OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    //
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder myViewHolder = null;
        view = inflater.inflate(R.layout.item_que_answer_layout, parent, false);
        myViewHolder = new MyViewHolder(view);
        view.setOnClickListener(this);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final QueAnswerBean bean = list.get(position);
        if (bean != null) {
            holder.tvQuestion.setText(bean.getDescription());
            holder.view.setTag(position);
            holder.tvFileds.setText(goodAt + bean.getTerritory());
            //holder
            //默认图片
            Glide.
                    with(context).load(baseImageUrl + bean.getPhoto())
                    .apply(new RequestOptions().placeholder(R.drawable.ic_image).override(20, 20))
                    .into(holder.ivIcon);
        }
        holder.rlQueAnswer.setTag(position);
        holder.rlQueAnswer.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.OnItemClick(v, (int) v.getTag());
        }
    }


    @Override
    public int getItemCount() {

        if (list == null)
            return 0;
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout rlQueAnswer;
        TextView tvQuestion, tvFileds;
        ImageView ivIcon;
        View view;

        public MyViewHolder(View view) {
            super(view);

            tvQuestion = (TextView) view.findViewById(R.id.tv_question);
            tvFileds = (TextView) view.findViewById(R.id.tv_fileds);
            ivIcon = (ImageView) view.findViewById(R.id.iv_icon);
            rlQueAnswer = (RelativeLayout) view.findViewById(R.id.rl_que_answer);
            this.view = view;
        }
    }
}
