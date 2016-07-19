package com.aeon.clear.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aeon.clear.R;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.List;

/**
 * Created by zheng.shang on 2016/3/25.
 */
public class WithFontAwesomeListViewAdapter extends BaseAdapter {
    private static final int PAGER_GARBAGE = 1;
    private static final int PAGER_PERMISSIONS = 2;
    private static final int PAGER_BATTERY = 3;
    private LayoutInflater inflater;
    private Context mContext;
    private Typeface tf;
    List<HashMap<String, String>> ItemInfoList;
    private int mPagerPosition;

    public WithFontAwesomeListViewAdapter(Context context, List<HashMap<String, String>> ItemInfoList, Typeface tf, int pagerPosition) {

        this.ItemInfoList = ItemInfoList;
        this.mContext = context;
        this.tf = tf;
        this.mPagerPosition = pagerPosition;

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    static class ViewHolder {
        TextView name;
        TextView icon;
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
            convertView = inflater.inflate(R.layout.with_fontawesome_listview_item, null);
            holder = new ViewHolder();

            holder.name = (TextView) convertView.findViewById(R.id.list_name);
            holder.icon = (TextView) convertView.findViewById(R.id.list_icon);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.icon.setText(ItemInfoList.get(position).get("icon"));
        holder.name.setText(ItemInfoList.get(position).get("cleanItem"));
        holder.icon.setTypeface(tf);
        holder.name.setTextColor(Color.BLACK);

        int color = 0;
        switch (mPagerPosition) {
            case PAGER_GARBAGE:
                color = mContext.getResources().getColor(R.color.colorGarbage);
                break;
            case PAGER_PERMISSIONS:
                color = mContext.getResources().getColor(R.color.colorPermissions);
                break;
            case PAGER_BATTERY:
                color = mContext.getResources().getColor(R.color.colorBattery);
                break;

        }

        holder.icon.setTextColor(color);
        return convertView;
    }
}
