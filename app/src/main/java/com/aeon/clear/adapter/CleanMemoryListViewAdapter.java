package com.aeon.clear.adapter;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.Transformation;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.aeon.clear.R;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;

/**
 * Created by zheng.shang on 2016/3/25.
 */
public class CleanMemoryListViewAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    List<HashMap<String, Object>> ItemInfoList;

    public CleanMemoryListViewAdapter(Context context, List<HashMap<String, Object>> ItemInfoList) {

        this.ItemInfoList = ItemInfoList;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    static class ViewHolder {
        TextView name;
        ImageView icon;
        TextView size;
        CheckBox checkBox;
    }


    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return ItemInfoList.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return ItemInfoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder holder = null;

        if (holder == null) {
            convertView = inflater.inflate(R.layout.memory_listview_item, null);
            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.list_name);
            holder.icon = (ImageView) convertView.findViewById(R.id.list_icon);
            holder.size = (TextView) convertView.findViewById(R.id.memory_item_size);
            holder.checkBox = (CheckBox) convertView.findViewById(R.id.memory_item_checkbox);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.icon.setImageDrawable((Drawable) ItemInfoList.get(position).get("icon"));
        holder.name.setText((CharSequence) ItemInfoList.get(position).get("name"));
        holder.checkBox.setChecked((Boolean) ItemInfoList.get(position).get("checkBoxState"));
        float size = (float) ItemInfoList.get(position).get("size");
        String type;
        if (size >= 1024) {
            size = size / 1024;
            type = "Mb";
        } else {
            type = "Kb";
        }
        holder.size.setText((float) Math.round(size * 10) / 10 + type);

        return convertView;
    }

}
