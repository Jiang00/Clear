package com.aeon.clear.util;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.aeon.clear.App;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zheng.shang on 2016/3/24.
 */
public class Util {
    static Bitmap drawableToBitmap(Drawable drawable) // drawable 转换成bitmap
    {
        int width = drawable.getIntrinsicWidth();// 取drawable的长宽
        int height = drawable.getIntrinsicHeight();
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565;// 取drawable的颜色格式
        Bitmap bitmap = Bitmap.createBitmap(width, height, config);// 建立对应bitmap
        Canvas canvas = new Canvas(bitmap);// 建立对应bitmap的画布
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);// 把drawable内容画到画布中
        return bitmap;
    }

    public static List<PackageInfo> getInstalledApps() {

        final PackageManager packageManager = App.getContextObject().getPackageManager();//获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);//获取所有已安装程序的包信息

        return pinfo;
    }

// public static ArrayList<String> getInstalledApps() {
//        ArrayList<String> list = new ArrayList<>();
//        final PackageManager packageManager = App.getContextObject().getPackageManager();//获取packagemanager
//        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);//获取所有已安装程序的包信息
//        if (pinfo != null) {
//            for (int i = 0; i < pinfo.size(); i++) {
//                String packName = pinfo.get(i).packageName;
//                list.add(packName);
//            }
//
//        }
//        return list;
//    }
}
