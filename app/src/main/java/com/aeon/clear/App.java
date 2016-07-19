package com.aeon.clear;

import android.app.Application;
import android.content.Context;

/**
 * Created by zheng.shang on 2016/3/23.
 */
public class App extends Application {
    private static Context mContext;


    @Override
    public void onCreate() {
        mContext = getApplicationContext();
    }

    public static Context getContextObject() {
        return mContext;
    }

}
