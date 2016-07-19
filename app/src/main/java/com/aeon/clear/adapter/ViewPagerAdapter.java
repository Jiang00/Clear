package com.aeon.clear.adapter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.support.v13.app.FragmentPagerAdapter;

import com.aeon.clear.R;

import java.util.List;

/**
 * Created by zheng.shang on 2016/3/25.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {
    private Context context;
    private List<Fragment> listFragment;

    public ViewPagerAdapter(Context context, FragmentManager fm, List<Fragment> listFragment) {
        super(fm);
        this.context = context;
        this.listFragment = listFragment;
    }

    @Override
    public Fragment getItem(int position) {
        return listFragment.get(position);
    }

    @Override
    public int getCount() {
        return listFragment.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String viewPagerTitle = null;
        switch (position) {
            case 0:
                viewPagerTitle = context.getResources().getString(R.string.clean_memory_title);
                break;
            case 1:
                viewPagerTitle = context.getResources().getString(R.string.clean_garbage_title);
                break;
            case 2:
                viewPagerTitle = context.getResources().getString(R.string.clean_permisson_title);
                break;
            case 3:
                viewPagerTitle = context.getResources().getString(R.string.clean_battery_save_title);
                break;
        }
        return viewPagerTitle;
    }


}