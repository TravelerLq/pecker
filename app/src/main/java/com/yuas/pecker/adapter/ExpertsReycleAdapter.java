package com.yuas.pecker.adapter;

/**
 * Created by liqing on 18/6/28.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yuas.pecker.R;
import com.yuas.pecker.bean.pecker.ExpertsBean;
import com.yuas.pecker.utils.Loger;
import com.yuas.pecker.view.CircleImageView;

import java.util.List;

/**
 * Created by liqing on 18/3/20.
 */

public class ExpertsReycleAdapter extends RecyclerView.Adapter<ExpertsReycleAdapter.MyViewHolder> implements View.OnClickListener {

    private Context context;
    private List<ExpertsBean> list;
    private LayoutInflater inflater;
    //两种布局
    //上拉加载更多布局
    public static final int view_Foot = 1;
    //主要布局
    public static final int view_Normal = 2;
    //是否隐藏
    public boolean isLoadMore = false;

    public ExpertsReycleAdapter(Context context, List<ExpertsBean> list) {
        this.context = context;
        this.list = list;
        this.inflater = LayoutInflater.from(this.context);
    }


    public static interface OnItemClickListener {
        void OnItemClick(View view, int pos);

    }

    //    public static interface OnItemClickListener {
//        void OnItemClick(View view, int pos);
//    }
    public ExpertsReycleAdapter.OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(ExpertsReycleAdapter.OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public void setList(List<ExpertsBean> listAdd) {

        list.addAll(listAdd);
        notifyDataSetChanged();
    }

    public void refreshList(List<ExpertsBean> listAdd) {
        list.clear();
        list.addAll(listAdd);
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder myViewHolder = null;
        View view = inflater.inflate(R.layout.item_experts_layout, parent, false);
        myViewHolder = new MyViewHolder(view);
        view.setOnClickListener(this);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        ExpertsBean bean = list.get(position);

        String areaGoodAt = context.getResources().getString(R.string.area_good_at);
        String price = context.getResources().getString(R.string.price);
        String name = context.getResources().getString(R.string.name_);
        holder.tvName.setText(name + bean.getTeachName());
        holder.tvArea.setText(areaGoodAt + bean.getTerritory());
        holder.tvPrice.setText(price + bean.getPrice());
        String photo = bean.getPhoto();
        Loger.e("-url" + photo);

        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.ic_image)
                .error(R.drawable.ic_image)
                .fallback(R.drawable.ic_image)
                .override(com.bumptech.glide.request.target.Target.SIZE_ORIGINAL);

        Glide.with(context)
                .load(bean.getPhoto())
                .apply(options)
                .into(holder.imgHeader);

        holder.rlExperts.setTag(position);
        holder.rlExperts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.OnItemClick(v, position);
            }
        });


//       holder.tvName.setOnClickListener(new View.OnClickListener() {
//           @Override
//           public void onClick(View v) {
//              if(mOnItemClickListener!=null){
//                  mOnItemClickListener.OnItemClick(v,position);
//              }
//           }
//       });

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
        RelativeLayout rlExperts;
        TextView tvArea;
        TextView tvPrice;
        TextView tvName;
        CircleImageView imgHeader;
        View view;


        public MyViewHolder(View view) {
            super(view);

            //擅长领域
            tvArea = (TextView) view.findViewById(R.id.tv_area);
            //价格
            tvPrice = (TextView) view.findViewById(R.id.tv_price);
            //name
            tvName = (TextView) view.findViewById(R.id.tv_name);
            imgHeader = (CircleImageView) view.findViewById(R.id.iv_expert_header);
            rlExperts = (RelativeLayout) view.findViewById(R.id.rl_item_expert);

            this.view = view;
        }
    }
}
