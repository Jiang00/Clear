package com.aeon.clear.fragments;

import android.app.ActivityManager;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.os.SystemClock;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.aeon.clear.App;
import com.aeon.clear.EmptyView;
import com.aeon.clear.R;
import com.aeon.clear.adapter.CleanMemoryListViewAdapter;
import com.aeon.clear.util.AnimationHelper;
import com.dd.CircularProgressButton;
import com.jaredrummler.android.processes.ProcessManager;
import com.jaredrummler.android.processes.models.AndroidAppProcess;
import com.nhaarman.listviewanimations.appearance.simple.AlphaInAnimationAdapter;
import com.nineoldandroids.animation.IntEvaluator;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.ValueAnimator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 * Created by zheng.shang on 2016/3/23.
 */
public class CleanMemoryFragment extends Fragment {


    public static final String TAG = "CleanMemoryFragment";
    private CircularProgressButton mClear;
    private TextView mText, mSupText;
    private ListView mList;
    private List<String> mDateList;  //需要被清理的进程列表
    private List<HashMap<String, Object>> mRunningAppsNameAndIconAndSize; //正在运行的程序名字和图标
    private CleanMemoryListViewAdapter mAdapter;
    private PackageManager pm;
    private ActivityManager activityManager;
    private int mTotalSize;  //所有进程的总大小
    private CheckBox mSelectAllCheckBox;    //进程全选复选框
    private LinearLayout mHeaderLayout;
    private TextView mCompleteView;
    private boolean bDoClear = false; //a flag to control whether do doClear function.

    public static CleanMemoryFragment newInstance() {
        CleanMemoryFragment fragment = new CleanMemoryFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_clean_memory, container, false);
        initViews(view);

        mClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doClear(getActivity());
                //new CleanMemoryAsyncTask().execute();

                deleteAllList();
            }
        });

        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String, Object> hashMap = (HashMap<String, Object>) mAdapter.getItem(position);
                boolean state = (boolean) hashMap.get("checkBoxState");
                hashMap.put("checkBoxState", !state);
                mRunningAppsNameAndIconAndSize.set(position, hashMap);
                mSelectAllCheckBox.setChecked(false);
                mAdapter.notifyDataSetChanged();

            }
        });

        mSelectAllCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSelectAllCheckBox.isChecked()) {
                    for (int i = 0; i < mRunningAppsNameAndIconAndSize.size(); i++) {
                        HashMap<String, Object> hashMap = (HashMap<String, Object>) mAdapter.getItem(i);
                        hashMap.put("checkBoxState", true);
                        mRunningAppsNameAndIconAndSize.set(i, hashMap);
                    }
                } else {
                    for (int i = 0; i < mRunningAppsNameAndIconAndSize.size(); i++) {
                        HashMap<String, Object> hashMap = (HashMap<String, Object>) mAdapter.getItem(i);
                        hashMap.put("checkBoxState", false);
                        mRunningAppsNameAndIconAndSize.set(i, hashMap);
                    }
                }
                mAdapter.notifyDataSetChanged();

            }
        });
        return view;
    }

    private void deleteAllList() {
        mClear.setProgress(50);
        for (int i = 0; i < mList.getChildCount(); i++) {
            View v = mList.getChildAt(i);
            deletePattern(v, i);
        }
    }


    private void initViews(View view) {
        activityManager = (ActivityManager) getActivity().getSystemService(Context.ACTIVITY_SERVICE);
        mDateList = new ArrayList<>();
        mRunningAppsNameAndIconAndSize = new ArrayList<>();

        mText = (TextView) view.findViewById(R.id.memory_text);
        mText.setTextColor(getResources().getColor(R.color.white));
        mSupText = (TextView) view.findViewById(R.id.memory_sup_text);
        mSupText.setTextColor(getResources().getColor(R.color.white));
        mHeaderLayout = (LinearLayout) view.findViewById(R.id.memory_list_header_layout);
        mClear = (CircularProgressButton) view.findViewById(R.id.memory_clear);
        mClear.setIndeterminateProgressMode(true);//设定无限循环加载
        mList = (ListView) view.findViewById(R.id.list);
        mSelectAllCheckBox = (CheckBox) view.findViewById(R.id.memory_header_checkbox);

        mCompleteView = (TextView) view.findViewById(R.id.memory_complete_text);

        /**
         * 使用第三方库添加列表支持动画：加载动画
         */
        mAdapter = new CleanMemoryListViewAdapter(App.getContextObject(), mRunningAppsNameAndIconAndSize);
        AlphaInAnimationAdapter animationAdapter = new AlphaInAnimationAdapter(mAdapter);
        animationAdapter.setAbsListView(mList);
        mList.setAdapter(animationAdapter);

        new CleanMemoryAsyncTask().execute();
    }


    private void doClear(Context context) {
        long startMemory = getAvailableMemory(App.getContextObject());
        ActivityManager activityManger = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        int size = mDateList.size();
        for (int i = 0; i < size; i++) {
            activityManger.killBackgroundProcesses(mDateList.get(i));
        }

        long secondMemory = getAvailableMemory(App.getContextObject());
        long releaseMemory = secondMemory - startMemory;
//        if (releaseMemory >= 0) {
//            Toast.makeText(App.getContextObject(), "释放了" + releaseMemory + "M内存", Toast.LENGTH_SHORT).show();
//        }
        // getRunningProcess();


    }

    private void deletePattern(final View view, final int position) {

        Animation.AnimationListener al = new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                AnimationHelper.textFastChangeAnimation(mText, mTotalSize / 1024, 0);
            }

            @Override
            public void onAnimationEnd(Animation animation) {

                if (position == mList.getLastVisiblePosition() - mList.getFirstVisiblePosition()) {

                    mRunningAppsNameAndIconAndSize.clear();
                    mAdapter.notifyDataSetChanged();
                    mHeaderLayout.setVisibility(View.GONE);
                    mClear.setVisibility(View.GONE);
                    mList.setVisibility(View.GONE);
                    mCompleteView.setVisibility(View.VISIBLE);
                    if (mCompleteView.getVisibility() == View.VISIBLE) {
                        Toast.makeText(App.getContextObject(), "释放了" + mTotalSize / 1024 + "M内存", Toast.LENGTH_SHORT).show();
                        mCompleteView.setTextColor(Color.WHITE);
                    }
                } else {

                    view.setVisibility(View.GONE);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        };
        slideDeleteCell(view, al, position);

    }

    /**
     * 向左滑动删除动画效果
     *
     * @param view 执行动画的view
     * @param al   动画监听器
     * @param time 控制动画时间，产生层次效果
     */
    private void slideDeleteCell(final View view, Animation.AnimationListener al, int time) {
        final float originWeight = view.getMeasuredWidth();

        TranslateAnimation animation = new TranslateAnimation(0, -originWeight, 0, 0);
        if (al != null) {
            animation.setAnimationListener(al);
        }
        animation.setDuration(300 + 200 * time);

        view.startAnimation(animation);
    }

// private void collapse(final View view, Animation.AnimationListener al) {
//        final int originHeight = view.getMeasuredHeight();
//
//        Animation animation = new Animation() {
//            @Override
//            protected void applyTransformation(float interpolatedTime, Transformation t) {
//                if (interpolatedTime == 1.0f) {
//                    view.setVisibility(View.GONE);
//                } else {
//                    view.getLayoutParams().height = originHeight - (int) (originHeight * interpolatedTime);
//                    view.requestLayout();
//                }
//            }
//
//            @Override
//            public boolean willChangeBounds() {
//                return true;
//            }
//        };
//        if (al != null) {
//            animation.setAnimationListener(al);
//        }
//        animation.setDuration(300);
//        view.startAnimation(animation);
//    }


    /**
     * 获取可用内存
     */
    public long getAvailableMemory(Context context) {

        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(mi);

        return mi.availMem / (1024 * 1024);
    }


    /**
     * Use a new Thread to load running process to avoid main Thread stock.
     */

    class CleanMemoryAsyncTask extends AsyncTask {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.i(TAG, "loading...");
            AnimationHelper.buttonJumpToLoading(mClear, true);
            mClear.setProgress(50);
        }

        @Override
        protected Object doInBackground(Object[] params) {
            getRunningProcess();
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            mAdapter.notifyDataSetChanged();

            //从0快速增长到指定大小。
            AnimationHelper.textFastChangeAnimation(mText, 0, mTotalSize / 1024);
            AnimationHelper.buttonJumpToLoading(mClear, false);
            mClear.setProgress(0); //恢复到默认样式
        }

        public void getRunningProcess() {
            mTotalSize = 0;
            String pkgName;
            mDateList.clear();
            mRunningAppsNameAndIconAndSize.clear();
            if (Build.VERSION.SDK_INT >= 21) {
                List<AndroidAppProcess> list = ProcessManager.getRunningAppProcesses();
                if (list != null) {
                    for (AndroidAppProcess processInfo : list) {
                        pkgName = processInfo.getPackageName();

                        int myPid = android.os.Process.myPid();
                        if (myPid == processInfo.pid) {
                            continue;
                        }
                        if (pkgName.contains("launcher")) {
                            continue;
                        }
                        if (!pkgName.contains("android")) {
                            if (!mDateList.contains(pkgName)) {
                                mDateList.add(pkgName);
                                getRunningAppsNameAndIcon(pkgName, processInfo.pid);
                            }
                        }
                        //对列表进行排序，以大到小
                        Collections.sort(mRunningAppsNameAndIconAndSize, new Comparator<HashMap<String, Object>>() {
                            @Override
                            public int compare(HashMap<String, Object> lhs, HashMap<String, Object> rhs) {
                                float l = (float) lhs.get("size");
                                float r = (float) rhs.get("size");
                                return l <= r ? 1 : -1;
                            }
                        });
                        // mAdapter.notifyDataSetChanged();
                    }
                }
            }
        }

        //获取正在运行的程序名和图标
        private void getRunningAppsNameAndIcon(String pkgName, int pid) {
            pm = getActivity().getPackageManager();
            String name;
            Drawable icon;
            float size = 0;
            try {
                name = pm.getApplicationLabel(pm.getApplicationInfo(pkgName, PackageManager.GET_META_DATA)).toString();
                icon = pm.getApplicationIcon(pkgName);
                size = activityManager.getProcessMemoryInfo(new int[]{pid})[0].dalvikPrivateDirty;
                mTotalSize += size;

            } catch (PackageManager.NameNotFoundException e) {

                name = pkgName;
                icon = getResources().getDrawable(R.mipmap.ic_launcher);
            }

            if (name.contains("com.") || size == 0) { //过滤掉com.开头的和大小为0的进程
                mDateList.remove(pkgName);
            } else {
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("icon", icon);
                hashMap.put("name", name);
                hashMap.put("size", size);
                hashMap.put("checkBoxState", true);
                mRunningAppsNameAndIconAndSize.add(hashMap);
            }
        }


    }
}