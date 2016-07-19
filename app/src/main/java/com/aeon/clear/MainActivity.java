package com.aeon.clear;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.aeon.clear.adapter.ViewPagerAdapter;
import com.aeon.clear.fragments.CleanBatterySaveFragment;
import com.aeon.clear.fragments.CleanGarbageFragment;
import com.aeon.clear.fragments.CleanMemoryFragment;
import com.aeon.clear.fragments.CleanPermissionsFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private TextView mText;
    private ViewPager mPager;
    private ViewPagerAdapter mViewPagerAdapter;
    private List<Fragment> mFragments = new ArrayList<>();
    private TabLayout mTabLayout;
    private ArrayList<Integer> mPagerColorList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initDate();


        mPager.setOffscreenPageLimit(3);
        mPager.setAdapter(mViewPagerAdapter);
        mTabLayout.setupWithViewPager(mPager);

        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            int currentIndex;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentIndex = position;
                mTabLayout.setBackgroundColor(getResources().getColor(mPagerColorList.get(position)));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void initDate() {
//        mText = (TextView) findViewById(R.id.main_text);
        mPager = (ViewPager) findViewById(R.id.main_pager);
        mTabLayout = (TabLayout) findViewById(R.id.sliding_tabs);

        mFragments.add(CleanMemoryFragment.newInstance());
        mFragments.add(CleanGarbageFragment.newInstance());
        mFragments.add(CleanPermissionsFragment.newInstance());
        mFragments.add(CleanBatterySaveFragment.newInstance());

        mViewPagerAdapter = new ViewPagerAdapter(this, getFragmentManager(), mFragments);


        /**
         * 初始化四种pager颜色
         */
        mPagerColorList = new ArrayList<>();
        mPagerColorList.add(R.color.colorMemory);
        mPagerColorList.add(R.color.colorGarbage);
        mPagerColorList.add(R.color.colorPermissions);
        mPagerColorList.add(R.color.colorBattery);
        mTabLayout.setBackgroundColor(getResources().getColor(mPagerColorList.get(0)));
    }


}
