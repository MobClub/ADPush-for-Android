package com.mob.adpush.demo;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowMetrics;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

public class RegionView extends View {

    int oretation;
    private int l, t, r, b;
    private Paint paint;
    private int screenHeight;

    public RegionView(Context context) {
        super(context);
        oretation = Resources.getSystem().getConfiguration().orientation;
        init();
    }

    public RegionView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RegionView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public RegionView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init(){


        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
        paint.setColor(Color.RED);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(l, t, r, b, paint);
    }

    @Override
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        int oretationState = Resources.getSystem().getConfiguration().orientation;
        if(oretation == oretationState)
            return;

        int w = r - l;
        int h = b- t;

        r = h+l;
        b = w + t;


        oretation = oretationState;
    }

    public void setRegion(int left, int top, int right, int bottom){

        l = left;
        t = top;
        r = right;
        b = bottom;
        invalidate();
    }
}
