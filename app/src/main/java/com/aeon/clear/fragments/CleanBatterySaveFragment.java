package com.aeon.clear.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aeon.clear.R;

/**
 * Created by zheng.shang on 2016/3/23.
 */
public class CleanBatterySaveFragment extends Fragment {
    public static final int PAGER_BATTERY = 3;

    public static CleanBatterySaveFragment newInstance() {
        CleanBatterySaveFragment fragment = new CleanBatterySaveFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_clean_battery_save, container, false);
    }
}
