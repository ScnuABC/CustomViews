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
 * Created by Administrator on 2017/11/20.
 */

public class LineCircleProgressView extends View {

    private static final String LOG_TAG = LineCircleProgressView.class.getSimpleName();

    private Context mContext;
    private Paint mPaint;

    private static final float INTERVAL_DEGREE = 300/60;
    private float centerX,centerY;

    private static final int MIN = 0;
    private static final int MAX = 60;

    private int min = MIN;
    private int max = MAX;
    private int curProgress = min;

    private int width,height;

    private float circleRadius;
    private float lineLength;
    private float baseLineVolume;
    private float baseLineMinMax;


    public LineCircleProgressView(Context context) {
        this(context,null);
    }

    public LineCircleProgressView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LineCircleProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }


    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        if(width == 0 || height == 0){
            width = getWidth();
            height = getHeight();
            centerX = width/2;
            centerY = height /2;
            circleRadius = Math.min(width,height)/2;
            lineLength = DensityUtils.dp2px(mContext,18);
            Log.e(LOG_TAG,"onMeasure width = " + width + " : height = " + height + " : centerX = " + centerX +
                    " : centerY = " + centerY  + " : circleRadius = " +  circleRadius + " : lineLenght = " + lineLength);
            baseLineVolume = centerY - circleRadius - DensityUtils.dp2px(mContext,15);
            baseLineMinMax = centerY + circleRadius + DensityUtils.dp2px(mContext,20);
        }
        drawLine(canvas);
//        drawText(canvas);
    }


    public void setMin(int min) {
        this.min = min;
    }

    public void setMax(int max) {
        this.max = max;
        postInvalidate();
    }

    public void setCurProgress(int curProgress) {
        if(curProgress > MIN && curProgress < MAX){
            this.curProgress = curProgress;
        }
        if(curProgress <= 0){
            this.curProgress = MIN;
        }
        if(curProgress >= MAX){
            this.curProgress = MAX;
        }
        postInvalidate();
    }

    public int getCurProgress() {
        return curProgress;
    }

    private void drawLine(Canvas canvas) {
        mPaint.setStrokeWidth(4);
        mPaint.setARGB(75,255,255,255);
        for (int i = 0; i <=12; i++) {
                /*第3象限直线*/
            canvas.drawLine((float) (centerX - circleRadius * Math.cos( Math.PI*i * INTERVAL_DEGREE/180)),
                    (float) (centerY + circleRadius * Math.sin( Math.PI*i * INTERVAL_DEGREE/180)),
                    (float) (centerX - (circleRadius - lineLength) * Math.cos(Math.PI*i * INTERVAL_DEGREE/180)),
                    (float) (centerY + (circleRadius - lineLength) * Math.sin(Math.PI*i * INTERVAL_DEGREE/180)), mPaint);
        }
        for (int i = 1; i <=18; i++) {
                /*第2象限直线*/
            canvas.drawLine((float) (centerX - circleRadius * Math.cos( Math.PI* i * INTERVAL_DEGREE/180)),
                    (float) (centerY - circleRadius * Math.sin(Math.PI* i * INTERVAL_DEGREE/180)),
                    (float) (centerX - (circleRadius - lineLength) * Math.cos( Math.PI* i * INTERVAL_DEGREE/180)),
                    (float) (centerY - (circleRadius - lineLength) * Math.sin( Math.PI* i * INTERVAL_DEGREE/180)), mPaint);
        }
        for (int i = 0; i <18; i++) {
                /*第1象限直线*/
            canvas.drawLine((float) (centerX + circleRadius * Math.cos(Math.PI* i * INTERVAL_DEGREE/180)),
                    (float) (centerY - circleRadius * Math.sin(Math.PI* i * INTERVAL_DEGREE/180)),
                    (float) (centerX + (circleRadius - lineLength) * Math.cos( Math.PI* i * INTERVAL_DEGREE/180)),
                    (float) (centerY - (circleRadius - lineLength) * Math.sin( Math.PI* i * INTERVAL_DEGREE/180)), mPaint);
        }
        for (int i = 1; i <=12; i++) {
                /*第4象限直线*/
            canvas.drawLine((float) (centerX + circleRadius * Math.cos( Math.PI* i * INTERVAL_DEGREE/180)),
                    (float) (centerY + circleRadius * Math.sin(Math.PI* i * INTERVAL_DEGREE/180)),
                    (float) (centerX + (circleRadius - lineLength) * Math.cos( Math.PI* i * INTERVAL_DEGREE/180)),
                    (float) (centerY + (circleRadius - lineLength) * Math.sin( Math.PI* i * INTERVAL_DEGREE/180)), mPaint);
        }

        mPaint.setColor(Color.parseColor("#F59F23"));
//        curProgress = 12;

        if(curProgress == 0){
            return;
        }

//        /*音量(48-60]*/
        if(curProgress > 48 && curProgress <=60){
            int temp =  curProgress -48;
//            /*画长白线*/
//            canvas.drawLine((float) (centerX + (circleRadius-lineLength -8) *Math.cos(Math.PI* temp * INTERVAL_DEGREE/180)),
//                    (float)(centerY + (circleRadius-lineLength-8)*Math.sin(Math.PI* temp * INTERVAL_DEGREE/180)),
//                    (float)(centerX + ((circleRadius - 4*lineLength))*Math.cos(Math.PI* temp * INTERVAL_DEGREE/180)),
//                    (float)(centerY + ((circleRadius - 4*lineLength))*Math.sin(Math.PI* temp * INTERVAL_DEGREE/180)),mPaint);
            /*画第4象限*/
            for(int i = temp ; i >= 0; i--){
                canvas.drawLine((float) (centerX + circleRadius*Math.cos(Math.PI* i * INTERVAL_DEGREE/180)),
                        (float)(centerY + circleRadius*Math.sin(Math.PI* i * INTERVAL_DEGREE/180)),
                        (float)(centerX + (circleRadius - lineLength)*Math.cos(Math.PI* i * INTERVAL_DEGREE/180)),
                        (float)(centerY + (circleRadius - lineLength)*Math.sin(Math.PI* i * INTERVAL_DEGREE/180)),mPaint);
            }
            for (int i = 1; i <= 18; i++) {
                /*第1象限直线*/
                canvas.drawLine((float) (centerX + circleRadius * Math.cos(Math.PI* i * INTERVAL_DEGREE/180)),
                        (float) (centerY - circleRadius * Math.sin(Math.PI* i * INTERVAL_DEGREE/180)),
                        (float) (centerX + (circleRadius - lineLength) * Math.cos( Math.PI* i * INTERVAL_DEGREE/180)),
                        (float) (centerY - (circleRadius - lineLength) * Math.sin( Math.PI* i * INTERVAL_DEGREE/180)), mPaint);
            }
            for (int i = 1; i <= 18; i++) {
                /*第2象限直线*/
                canvas.drawLine((float) (centerX - circleRadius * Math.cos( Math.PI* i * INTERVAL_DEGREE/180)),
                        (float) (centerY - circleRadius * Math.sin(Math.PI* i * INTERVAL_DEGREE/180)),
                        (float) (centerX - (circleRadius - lineLength) * Math.cos( Math.PI* i * INTERVAL_DEGREE/180)),
                        (float) (centerY - (circleRadius - lineLength) * Math.sin( Math.PI* i * INTERVAL_DEGREE/180)), mPaint);
            }
            /*画第3象限*/
            for(int i = 0 ; i <= 12 ; i++){
                canvas.drawLine((float) (centerX - circleRadius*Math.cos(Math.PI*i * INTERVAL_DEGREE/180)),
                        (float)(centerY + circleRadius*Math.sin(Math.PI*i * INTERVAL_DEGREE/180)),
                        (float)(centerX - (circleRadius - lineLength)*Math.cos(Math.PI*i * INTERVAL_DEGREE/180)),
                        (float)(centerY + (circleRadius - lineLength)*Math.sin(Math.PI*i * INTERVAL_DEGREE/180)),mPaint);
            }
        }
        /*音量（30-48]*/
        if(curProgress > 30 && curProgress <=48){
//            if(curProgress == 48){
//                canvas.drawLine(centerX + circleRadius,centerY , centerX + (circleRadius - lineLength),centerY,mPaint);
//            }
            int temp = curProgress -30;
//            /*画长白线*/
//            canvas.drawLine((float) (centerX  + (circleRadius - lineLength-8) * Math.sin(Math.PI*temp * INTERVAL_DEGREE/180)),
//                    (float) (centerY  - (circleRadius - lineLength - 8)* Math.cos(Math.PI* temp * INTERVAL_DEGREE/180)),
//                    (float) (centerX + (circleRadius - 4*lineLength) * Math.sin( Math.PI* temp * INTERVAL_DEGREE/180)),
//                    (float) (centerY - (circleRadius - 4*lineLength) * Math.cos( Math.PI* temp * INTERVAL_DEGREE/180)), mPaint);
            /*画第一象限*/
            for (int i = 0; i <= temp; i++) {
                canvas.drawLine((float) (centerX + circleRadius * Math.sin(Math.PI* i * INTERVAL_DEGREE/180)),
                        (float) (centerY - circleRadius * Math.cos(Math.PI* i * INTERVAL_DEGREE/180)),
                        (float) (centerX + (circleRadius - lineLength) * Math.sin( Math.PI* i * INTERVAL_DEGREE/180)),
                        (float) (centerY - (circleRadius - lineLength) * Math.cos( Math.PI* i * INTERVAL_DEGREE/180)), mPaint);
            }
            /*画第二象限*/
            for(int i = 18; i > 0 ; i --){
                canvas.drawLine((float)(centerX - circleRadius*Math.cos(Math.PI*i * INTERVAL_DEGREE/180)),
                        (float)(centerY - circleRadius*Math.sin(Math.PI*i * INTERVAL_DEGREE/180)),
                        (float)(centerX - (circleRadius - lineLength)*Math.cos(Math.PI*i * INTERVAL_DEGREE/180)),
                        (float)(centerY - (circleRadius - lineLength)*Math.sin(Math.PI*i * INTERVAL_DEGREE/180)),mPaint);
            }
            /*画第三象限*/
            for(int i = 0; i <= 12 ; i++){
                canvas.drawLine((float)(centerX - circleRadius*Math.cos(Math.PI*i * INTERVAL_DEGREE/180)),
                        (float)(centerY + circleRadius*Math.sin(Math.PI*i * INTERVAL_DEGREE/180)),
                        (float)(centerX - (circleRadius - lineLength)*Math.cos(Math.PI*i * INTERVAL_DEGREE/180)),
                        (float)(centerY + (circleRadius - lineLength)*Math.sin(Math.PI*i * INTERVAL_DEGREE/180)),mPaint);
            }
        }

        /*音量(12-30]*/
        if(curProgress > 12 && curProgress <= 30){
            int temp = curProgress - 12;
//            /*画长白线*/
//            canvas.drawLine((float)(centerX - ((circleRadius - lineLength)-8)*Math.cos(Math.PI*temp * INTERVAL_DEGREE /180)),
//                    (float)(centerY - ((circleRadius - lineLength)-8)*Math.sin(Math.PI*temp * INTERVAL_DEGREE/180)),
//                    (float)(centerX - ((circleRadius - 4*lineLength))*Math.cos(Math.PI*temp * INTERVAL_DEGREE/180)),
//                    (float)(centerY - ((circleRadius - 4*lineLength))*Math.sin(Math.PI*temp * INTERVAL_DEGREE/180)),mPaint);
             /*画第二象限*/
            for(int i = 0; i <= temp ; i ++){
                canvas.drawLine((float)(centerX - circleRadius*Math.cos(Math.PI*i * INTERVAL_DEGREE /180)),
                        (float)(centerY - circleRadius*Math.sin(Math.PI*i * INTERVAL_DEGREE/180)),
                        (float)(centerX - (circleRadius - lineLength)*Math.cos(Math.PI*i * INTERVAL_DEGREE/180)),
                        (float)(centerY - (circleRadius - lineLength)*Math.sin(Math.PI*i * INTERVAL_DEGREE/180)),mPaint);
            }
             /*画第三象限*/
            for(int i = 0; i <= 12 ; i++){
                canvas.drawLine((float)(centerX - circleRadius*Math.cos(Math.PI*i * INTERVAL_DEGREE /180)),
                        (float)(centerY + circleRadius*Math.sin(Math.PI*i * INTERVAL_DEGREE/180)),
                        (float)(centerX - (circleRadius - lineLength)*Math.cos(Math.PI*i * INTERVAL_DEGREE/180)),
                        (float)(centerY + (circleRadius - lineLength)*Math.sin(Math.PI*i * INTERVAL_DEGREE/180)),mPaint);
            }

        }
        /*音量(0-12]*/
        if(curProgress > 0 && curProgress <=12){
//            /*画长白线*/
//            canvas.drawLine((float)(centerX - (circleRadius - lineLength-8)*Math.cos((Math.PI*(60-curProgress * INTERVAL_DEGREE)/180))),
//                    (float)(centerY + (circleRadius - lineLength-8)*Math.sin((Math.PI*(60-curProgress * INTERVAL_DEGREE)/180))),
//                    (float)(centerX - ((circleRadius - 4*lineLength))*Math.cos((Math.PI*(60-curProgress * INTERVAL_DEGREE)/180))),
//                    (float)(centerY + ((circleRadius - 4*lineLength))*Math.sin((Math.PI*(60-curProgress * INTERVAL_DEGREE)/180))),mPaint);
             /*画第三象限*/
            for(int i = 0; i <=curProgress ; i++){
                canvas.drawLine((float)(centerX - circleRadius*Math.cos((Math.PI*(60-i * INTERVAL_DEGREE)/180))),
                        (float)(centerY + circleRadius*Math.sin((Math.PI*(60-i * INTERVAL_DEGREE)/180))),
                        (float)(centerX - (circleRadius - lineLength)*Math.cos((Math.PI*(60-i * INTERVAL_DEGREE)/180))),
                        (float)(centerY + (circleRadius - lineLength)*Math.sin((Math.PI*(60-i * INTERVAL_DEGREE)/180))),mPaint);
            }
        }

    }

    private void drawText(Canvas canvas) {
        int volumeSize = DensityUtils.sp2px(mContext, 25);
        mPaint.setTextSize(volumeSize);
        mPaint.setFakeBoldText(true);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setARGB(255,255,255,255);
        canvas.drawText(curProgress + "",centerX,baseLineVolume,mPaint);
        int minMaxSize = DensityUtils.sp2px(mContext,15);
        mPaint.setFakeBoldText(false);
        mPaint.setTextSize(minMaxSize);

        Log.i(LOG_TAG,"Math.tan(Math.PI*30/180) = " + Math.tan(Math.PI*30/180) + " : Math.sin(Math.PI*30/180)" + Math.sin(Math.PI*30/180) + " : Math.cos(Math.PI*30/180) = " + Math.cos(Math.PI*30/180));
        Log.i(LOG_TAG,"Math.tan(Math.PI*30/180) = " +  Math.tan(Math.PI*30/180) + " : Math.sin(Math.PI*30/180)" + Math.sin(Math.PI*30/180) + " : Math.cos(Math.PI*30/180) = " + Math.cos(Math.PI*30/180));

        double minX = centerX - ((baseLineMinMax-centerY)*Math.tan(Math.PI*30/180));
        double maxX = centerX + (baseLineMinMax-centerY)*Math.tan(Math.PI*30/180);
        Log.e(LOG_TAG,"drawText minX = " + minX + " : maxX = " + maxX);
        canvas.drawText(MIN + "",(float) minX,baseLineMinMax,mPaint);
        canvas.drawText(MAX + "",(float) maxX, baseLineMinMax,mPaint);
//        int volumeTextSize = DensityUtils.sp2px(mContext,20);
//        canvas.drawText(curProgress + "",centerX,reDuceAndPlusCY + volumeTextSize/2,mPaint);
    }


}
