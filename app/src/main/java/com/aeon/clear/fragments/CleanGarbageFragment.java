package com.aeon.clear.fragments;

import android.app.Fragment;
import android.content.pm.PackageInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.aeon.clear.App;
import com.aeon.clear.DataCleanManager;
import com.aeon.clear.R;
import com.aeon.clear.adapter.CleanGarbageListAdapter;
import com.aeon.clear.util.Util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by zheng.shang on 2016/3/23.
 */
public class CleanGarbageFragment extends Fragment {
    private static final int PAGER_GARBAGE = 1;
    private static final String TAG = "CleanGarbageFragment";
    private ListView mListView;
    private Button mButton;
    private CleanGarbageListAdapter mAdapter;
    private ArrayList<HashMap<String, String>> mListData;
    private TextView mLoad, mGarbageText, mGarbageSupText;
    private boolean isStartAnimation = false;

    public static CleanGarbageFragment newInstance() {
        CleanGarbageFragment fragment = new CleanGarbageFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_clean_garbage, container, false);

        mLoad = (TextView) inflater.inflate(R.layout.clean_garbage_list_item, container, false).findViewById(R.id.garbage_item_load);

        initViews(view);

        return view;
    }


    private void initViews(View view) {
        mGarbageText = (TextView) view.findViewById(R.id.garbage_text);
        mGarbageSupText = (TextView) view.findViewById(R.id.sup_text);
        mListView = (ListView) view.findViewById(R.id.garbage_list);
        mButton = (Button) view.findViewById(R.id.garbage_button);

        //set Garbage text color white
        mGarbageText.setTextColor(Color.WHITE);
        mGarbageSupText.setTextColor(Color.WHITE);

        GradientDrawable gd = (GradientDrawable) mButton.getBackground();
        gd.setColor(getResources().getColor(R.color.colorGarbage)); //设置对应的颜色

        initCleanGarbageList();

        mAdapter = new CleanGarbageListAdapter(getActivity().getApplicationContext(),
                mListData,
                Typeface.createFromAsset(
                        getActivity().getAssets(), "fontawesome-webfont.ttf"));
        mListView.setAdapter(mAdapter);

        mButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                scanCacheFiles();
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    Toast.makeText(App.getContextObject(), DataCleanManager.getCacheSize(getActivity().getCacheDir()), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


    }

    private void scanCacheFiles() {

        if (!isStartAnimation) {
            mButton.setText("取消扫描");
            isStartAnimation = true;

        } else {
            mButton.setText("开始扫描");
            isStartAnimation = false;
        }
        mAdapter.setIsStartAnimation(isStartAnimation);
        mAdapter.notifyDataSetChanged();

        //Cannot get every app's detail dir catch.
        List<PackageInfo> installedApps = Util.getInstalledApps();
        Log.i(TAG, "installedApps size = " + installedApps.size());
        long size = 0, singleCacheSize;
        File file;
        for (PackageInfo pkg : installedApps) {
            String pkgName = pkg.packageName;
            file = new File("/data/data/" + pkgName + "/cache");
            try {
                if (file.exists()) {
                    singleCacheSize = DataCleanManager.getFolderSize(file);
                    size = singleCacheSize;
                    Log.i(TAG, pkgName + "--------->" + singleCacheSize);
                    mGarbageText.setText(size + "");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }


    }

    private void initCleanGarbageList() {
        mListData = new ArrayList<>();
        HashMap<String, String> listItem = new HashMap<>();

        listItem.put("icon", getResources().getString(R.string.fa_inbox));
        listItem.put("cleanItem", getResources().getString(R.string.clean_garbage_cache));
        mListData.add(listItem);

        listItem = new HashMap<>();
        listItem.put("icon", getResources().getString(R.string.fa_file_text_o));
        listItem.put("cleanItem", getResources().getString(R.string.clean_garbage_junk_files));
        mListData.add(listItem);

        listItem = new HashMap<>();
        listItem.put("icon", getResources().getString(R.string.fa_folder));
        listItem.put("cleanItem", getResources().getString(R.string.clean_garbage_unused_packages));
        mListData.add(listItem);

        listItem = new HashMap<>();
        listItem.put("icon", getResources().getString(R.string.fa_ad));
        listItem.put("cleanItem", getResources().getString(R.string.clean_garbage_ad));
        mListData.add(listItem);

        listItem = new HashMap<>();
        listItem.put("icon", getResources().getString(R.string.fa_android));
        listItem.put("cleanItem", getResources().getString(R.string.clean_garbage_uninstall_remain));
        mListData.add(listItem);
    }


}
