package com.aeon.clear.util;

import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.TextView;

import com.aeon.clear.R;
import com.dd.CircularProgressButton;
import com.nineoldandroids.animation.IntEvaluator;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.ValueAnimator;

/**
 * Created by zheng.shang on 2016/4/6.
 */
public class AnimationHelper {
    private static final String TAG = "AnimationHelper";

    /**
     * 文本框数字快速变化动画
     * @param view
     * @param from 起始数字
     * @param to 结束数字
     */
    public static void textFastChangeAnimation(final TextView view, int from, int to) {
        ValueAnimator textAnim = ObjectAnimator.ofInt(from, to);
        textAnim.setDuration(1000);
        textAnim.setTarget(view);
        textAnim.setEvaluator(new IntEvaluator());

        textAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                view.setText(animation.getAnimatedValue().toString());
            }
        });
        textAnim.start();
    }


    /**
     * 按钮往上或往下移动动画。因为使用的是CircularProgressButton，所以还可以显示加载效果
     * @param button
     * @param up true表示从下往上移动，false表示从上往下移动
     */
    public static void buttonJumpToLoading(CircularProgressButton button, boolean up) {
        TranslateAnimation tm;
        if (up) {
            tm = new TranslateAnimation(0, 0, 0, -button.getY() / 3);
        } else {
            tm = new TranslateAnimation(0, 0, -button.getY() / 3, 0);
        }
        tm.setDuration(300);
        //tm.setFillAfter(true);
        button.startAnimation(tm);
    }
}
