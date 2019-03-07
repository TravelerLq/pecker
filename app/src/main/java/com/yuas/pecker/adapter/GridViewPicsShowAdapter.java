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

import java.util.List;

public class GridViewPicsShowAdapter extends BaseAdapter {

    private Context context;
    private List<PicBean> list;
    private LayoutInflater layoutInflater;
    private OnItemClickListener onItemClickListener;
    private static String TAG = GridViewPicsShowAdapter.class.getSimpleName();


    public GridViewPicsShowAdapter(Context context, List<PicBean> list) {
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
        int counts = list == null ? 0 : list.size();
        return counts;
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

        void viewImage(int pos);


    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
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
        if (list != null && position < list.size()) {
            //显示图片、image点击查看大图
            PicBean picBean = list.get(position);
            Glide.with(context)
                    .load(picBean.getPhotoPath())
                    .thumbnail(0.1f)
                    .into(viewHolder.imgHeader);
            viewHolder.imgHeader.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.viewImage(position);
                    }
                }
            });

        }

        return convertView;
    }


    class ViewHolder {
        ImageView imgHeader;
        ImageButton btnDelete;

    }
}
