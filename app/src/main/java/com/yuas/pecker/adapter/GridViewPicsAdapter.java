package com.yuas.pecker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yuas.pecker.R;
import com.yuas.pecker.bean.pecker.PicBean;
import com.yuas.pecker.utils.Loger;


import java.util.List;

public class GridViewPicsAdapter extends BaseAdapter {

    private Context context;
    private List<PicBean> list;
    private LayoutInflater layoutInflater;
    private OnItemClickListener onItemClickListener;
    private static String TAG = GridViewPicsAdapter.class.getSimpleName();

    private int maxCounts = 10;

    public GridViewPicsAdapter(Context context, List<PicBean> list) {
        this.context = context;
        this.list = list;
        layoutInflater = LayoutInflater.from(context);
    }

    /*
    int count = datas == null ? 1 : datas.size() + 1;
        if (count >= maxCount) {
            return maxCount;
        } else {
            return count;
        }
     */
    @Override
    public int getCount() {
        int counts = list == null ? 1 : list.size() + 1;
        if (counts >= maxCounts) {
            return maxCounts;
        } else {
            return counts;
        }

    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    public interface OnItemClickListener {
        void addImage(int pos);

        void viewImage(int pos);

        void deleteImage(int pos);

    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Loger.e(TAG + "pos= " + position);
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.item_pic_layout, parent, false);
            viewHolder.imgHeader = convertView.findViewById(R.id.picItemImg);
            viewHolder.btnDelete = convertView.findViewById(R.id.deleteBtn);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //如果是显示图片的postion
        if (list != null && position < list.size() || list.size() == maxCounts) {
            //显示图片、删除可见、image点击查看大图

            Loger.e(TAG + "pos image= " + position);
            PicBean picBean = list.get(position);
            Glide.with(context)
                    .load(picBean.getPhotoPath())
                    .thumbnail(0.1f)
                    .into(viewHolder.imgHeader);
            viewHolder.btnDelete.setVisibility(View.VISIBLE);
            viewHolder.imgHeader.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.viewImage(position);
                    }
                }
            });
        } else {
            Loger.e(TAG + "pos add= " + position);
            //显示"+"的位置，删除隐藏，点击触发是addImage()
            //  viewHolder.imgHeader.setImageResource(R.drawable.add_feed_back);
            Glide.with(context).load(R.drawable.add_feed_back).into(viewHolder.imgHeader);
            viewHolder.btnDelete.setVisibility(View.GONE);
            viewHolder.imgHeader.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.addImage(position);
                    }
                }
            });
        }

        viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.deleteImage(position);
                }
            }
        });
        return convertView;
    }


    class ViewHolder {
        ImageView imgHeader;
        ImageButton btnDelete;

    }
}
