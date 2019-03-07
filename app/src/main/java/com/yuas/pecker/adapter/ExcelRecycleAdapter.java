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


import com.yuas.pecker.R;
import com.yuas.pecker.bean.pecker.FileInfoBean;

import java.util.List;

/**
 * Created by liqing on 18/3/20.
 */

public class ExcelRecycleAdapter extends RecyclerView.Adapter<ExcelRecycleAdapter.MyViewHolder> implements View.OnClickListener {

    private Context context;
    private List<FileInfoBean> list;
    private LayoutInflater inflater;
    private View view;


    public ExcelRecycleAdapter(Context context, List<FileInfoBean> list) {
        this.context = context;
        this.list = list;
        this.inflater = LayoutInflater.from(this.context);
    }



    public static interface OnItemClickListener {
        void OnItemClick(View view, int pos);

    }
    public void addItem(List<FileInfoBean> item){
        list.addAll(item);
        notifyDataSetChanged();
    }
    //    public static interface OnItemClickListener {
//        void OnItemClick(View view, int pos);
//    }
    public ExcelRecycleAdapter.OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(ExcelRecycleAdapter.OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder myViewHolder = null;
        view = inflater.inflate(R.layout.item_excel_layout, parent, false);
        myViewHolder = new MyViewHolder(view);
        view.setOnClickListener(this);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final FileInfoBean bean = list.get(position);
        if (bean != null) {
            holder.tvName.setText(bean.getName());
            holder.view.setTag(position);
            holder.tvFilePath.setText(bean.getPath());
            holder.tvFileDetail.setText(bean.getDetail());
//            holder.checkBox.setChecked(item.isChecked());
//            holder.view.setBackgroundColor(item.isChecked()? colorChecked : colorUnchecked);
//            if (bean.getSelect()) {
//                holder.tvSelectState.setImageResource(R.drawable.ic_select);
//            } else {
//                holder.tvSelectState.setImageResource(R.drawable.ic_unselect);
//            }
        }

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
        RelativeLayout rlSelectExcel;
        TextView tvName,tvFileDetail,tvFilePath;

        View view;


        public MyViewHolder(View view) {
            super(view);

            tvName = (TextView) view.findViewById(R.id.tv_name);
            tvFileDetail = (TextView) view.findViewById(R.id.tv_file_detail);
            tvFilePath = (TextView) view.findViewById(R.id.tv_file_path);
            rlSelectExcel = (RelativeLayout) view.findViewById(R.id.rl_select_excel);
            this.view = view;
        }
    }
}
