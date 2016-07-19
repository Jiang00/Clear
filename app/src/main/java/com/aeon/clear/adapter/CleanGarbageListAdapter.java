package com.aeon.clear.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.aeon.clear.App;
import com.aeon.clear.R;

import java.util.HashMap;
import java.util.List;

/**
 * Created by zheng.shang on 2016/3/25.
 */
public class CleanGarbageListAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private Context mContext;
    private Typeface tf;
    List<HashMap<String, String>> ItemInfoList;
    private boolean isStartAnimation = false;
    private Animation am;
    private boolean isAnimComplete = false;

    public CleanGarbageListAdapter(Context context, List<HashMap<String, String>> ItemInfoList, Typeface tf) {

        this.ItemInfoList = ItemInfoList;
        this.mContext = context;
        this.tf = tf;

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    static class ViewHolder {
        TextView name;
        TextView icon;
        TextView load;
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

    public void setIsStartAnimation(boolean isStartAnimation) {
        this.isStartAnimation = isStartAnimation;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder holder = null;

        if (holder == null) {
            convertView = inflater.inflate(R.layout.clean_garbage_list_item, null);
            holder = new ViewHolder();

            holder.name = (TextView) convertView.findViewById(R.id.garbage_item_name);
            holder.icon = (TextView) convertView.findViewById(R.id.garbage_list_item_icon);
            holder.load = (TextView) convertView.findViewById(R.id.garbage_item_load);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.icon.setText(ItemInfoList.get(position).get("icon"));
        holder.name.setText(ItemInfoList.get(position).get("cleanItem"));
        holder.icon.setTypeface(tf);
        holder.name.setTextColor(Color.BLACK);
        holder.load.setText(App.getContextObject().getResources().getString(R.string.fa_spinner));
        holder.load.setTypeface(tf);

        if (isStartAnimation) {
            holder.load.setVisibility(isStartAnimation == true ? View.VISIBLE : View.INVISIBLE);
            am = AnimationUtils.loadAnimation(App.getContextObject(), R.anim.load_rotate);
            holder.load.startAnimation(am);

        }


        return convertView;
    }

}
