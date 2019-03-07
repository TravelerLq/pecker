package com.yuas.pecker.adapter;

/**
 * Created by liqing on 18/6/28.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.yuas.pecker.R;

import java.util.List;

/**
 * Created by liqing on 18/3/20.
 */

public class StringTextOnlyAdapter extends RecyclerView.Adapter<StringTextOnlyAdapter.MyViewHolder> implements View.OnClickListener {

    private Context context;
    private List<String> list;
    private LayoutInflater inflater;

    public StringTextOnlyAdapter(Context context, List<String> list) {
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
    public StringTextOnlyAdapter.OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(StringTextOnlyAdapter.OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder myViewHolder = null;
        View view = inflater.inflate(R.layout.item_text, parent, false);
        myViewHolder = new MyViewHolder(view);
        view.setOnClickListener(this);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        String string = list.get(position);
        String index =String.valueOf(position+1)+" 、";
        if (string != null) {
            holder.tvName.setText(index+string);


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

        TextView tvName;
        View view;


        public MyViewHolder(View view) {
            super(view);
            //fee
            tvName = (TextView) view.findViewById(R.id.tv_name);
            this.view = view;
        }
    }
}
