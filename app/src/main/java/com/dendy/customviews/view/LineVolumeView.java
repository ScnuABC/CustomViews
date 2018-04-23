package com.dendy.customviews.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.dendy.customviews.utils.DensityUtils;


/**
 * Created by Administrator on 2017/12/30.
 */

public class LineVolumeView extends View {
    private static final String TAG = LineVolumeView.class.getSimpleName();

    private Context mContext;
    private Paint mPaint;
    private int curVolume = 0;

    private int mPaitStrokeWidth;
    private int mBaseWidth;
    private int mBaseLineHeigth;
    private int mBaseInterval;
    private int mBaseLineTop;


    public LineVolumeView(Context context) {
        this(context, null);
    }

    public LineVolumeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LineVolumeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        Log.i(TAG,"LineVolumeView getWidth = " + getWidth() + " : getHeight = " + getHeight());
        mPaitStrokeWidth = DensityUtils.dp2px(context,2);
        mBaseLineHeigth = DensityUtils.dp2px(context,12);
        mBaseInterval = DensityUtils.dp2px(context,2);
        mBaseLineTop = DensityUtils.dp2px(context,6);
        mBaseWidth = DensityUtils.dp2px(context,242);
        initPaint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawDefaultLine(canvas);
//        drawTestLine(canvas);
        drawCurVolumeLine(canvas);
    }

    private void initPaint(){
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(mPaitStrokeWidth);
        mPaint.setStyle(Paint.Style.STROKE);
    }
    private void drawDefaultLine(Canvas canvas){
        mPaint.setColor(Color.parseColor("#494949"));
        int start = DensityUtils.dp2px(mContext,1);
        for(int i = 0 ; i < 60 ; i++){
            canvas.drawLine(i*(2*mBaseInterval) + start,mBaseLineTop,i*(2*mBaseInterval) + start,mBaseLineTop + mBaseLineHeigth,mPaint);
        }
    }

    private void drawTestLine(Canvas canvas){
        int baseIntenrval = getWidth()/60;
        Log.i(TAG, "drawTestLine: baseIntenrval = " + baseIntenrval);
        for(int i = 0 ; i < 60; i++){
            canvas.drawLine(i*baseIntenrval,mBaseLineTop + 10,i*baseIntenrval,mBaseLineTop + mBaseLineHeigth + 30,mPaint);
        }
    }

    private void drawCurVolumeLine(Canvas canvas){
        mPaint.setColor(Color.parseColor("#F5A623"));
        if(curVolume <= 0){
           return;
        }
        int start = DensityUtils.dp2px(mContext,1);
        for(int i = 0 ; i < curVolume ; i++){
            canvas.drawLine(i*(2*mBaseInterval) + start,mBaseLineTop,i*(2*mBaseInterval) + start,mBaseLineTop + mBaseLineHeigth,mPaint);
        }
    }

    public void setCurVolume(int curVolume) {
        this.curVolume = curVolume;
        postInvalidate();
    }
}
