package com.aeon.clear.fragments;

import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.aeon.clear.R;
import com.aeon.clear.adapter.WithFontAwesomeListViewAdapter;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by zheng.shang on 2016/3/23.
 */
public class CleanPermissionsFragment extends Fragment {

    private static final int PAGER_PERMISSION = 2;
    private TextView mText;
    private ListView mListView;
    private ArrayList<HashMap<String,String>> mListData;
    private WithFontAwesomeListViewAdapter mAdapter;

    public static CleanPermissionsFragment newInstance() {
        CleanPermissionsFragment fragment = new CleanPermissionsFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_clean_permissions, container, false);

        initViews(view);
        return view;
    }

    private void initViews(View view) {
        mText = (TextView) view.findViewById(R.id.permissions_text);
        mListView = (ListView) view.findViewById(R.id.permissions_list);

        initPermissionsControlListItem();

        mAdapter = new WithFontAwesomeListViewAdapter(getActivity().getApplicationContext(),
                mListData,
                Typeface.createFromAsset(getActivity().getAssets(), "fontawesome-webfont.ttf"),PAGER_PERMISSION);
        mListView.setAdapter(mAdapter);
    }

    //初始化列表项
    private void initPermissionsControlListItem() {
        mListData = new ArrayList<>();
        HashMap<String, String> listItem = new HashMap<>();

        listItem.put("icon", getResources().getString(R.string.fa_power_off));
        listItem.put("cleanItem", getResources().getString(R.string.permissions_auto_run_when_power_up));
        mListData.add(listItem);

        listItem = new HashMap<>();
        listItem.put("icon", getResources().getString(R.string.fa_comment));
        listItem.put("cleanItem", getResources().getString(R.string.permissions_app_notification_manager));
        mListData.add(listItem);

        listItem = new HashMap<>();
        listItem.put("icon", getResources().getString(R.string.fa_shield));
        listItem.put("cleanItem", getResources().getString(R.string.permissions_app_permission_manager));
        mListData.add(listItem);

        listItem = new HashMap<>();
        listItem.put("icon", getResources().getString(R.string.fa_tasks));
        listItem.put("cleanItem", getResources().getString(R.string.permissions_intelligent_background));
        mListData.add(listItem);

    }

}
