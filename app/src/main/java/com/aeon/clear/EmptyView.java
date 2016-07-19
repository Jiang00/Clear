package com.aeon.clear;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;

/**
 * Created by zheng.shang on 2016/4/6.
 */
public class EmptyView extends View {

    public EmptyView(Context context) {
        super(context);
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);



        Paint p = new Paint();
        p.setColor(getResources().getColor(R.color.colorMemory));

        RectF oval = new RectF();
        oval.left = 20;
        oval.top = 20;
        oval.right = getWidth()-20;
        oval.bottom = getHeight() -20;
        canvas.drawArc(oval,0,90,false,p);


    }
}
