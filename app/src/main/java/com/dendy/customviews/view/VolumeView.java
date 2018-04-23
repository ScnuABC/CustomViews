package com.dendy.customviews.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.dendy.view.R;

/**
 * Created by Administrator on 2017/9/9.
 */

public class VolumeView extends View {
    private static final String TAG = VolumeView.class.getSimpleName();
    /*画笔*/
    private Paint mPaint;
    /*音量*/
    private int volume = 0;
    /*音量调节图片*/
    private Bitmap bm_reduce, bm_increase;

    /*按下，起来手势是否在增减图片上*/
    boolean downInReduc = false;
    boolean downInIncrease = false;
    boolean upInReduc = false;
    boolean upInIncrease = false;
    boolean downInVolume = false;
    boolean moveInVolume = false;

    /*计算音量增减变量参考值*/
    private double mTouchSlop =100;
    private double downX = 0 ,downY = 0;
    private final int maxInCreaseVolume = 50; ;
    private int currentMaxVolume = 0;
    private int currentMinVolume = 0;

    public VolumeView(Context context) {
        this(context, null);
    }

    public VolumeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VolumeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        volume = context.getSharedPreferences("volume", Context.MODE_PRIVATE).getInt("volume", 56);
        bm_reduce = BitmapFactory.decodeResource(context.getResources(), R.drawable.icon_reduce);
        bm_increase = BitmapFactory.decodeResource(context.getResources(), R.drawable.icon_increase);
//        mTouchSlop = getHeight()/10;
//        Log.e(TAG,"VolumeView mTouchSlop = " + mTouchSlop);
        Log.i(TAG,"VolumeView getWidth = " + getWidth() + " : getHeight = " + getHeight());


        Log.e(TAG,"cos(120) = " + Math.cos(120));
        Log.e(TAG,"cos(150) = " + Math.cos(180));
        Log.e(TAG,"cos(270) = " + Math.cos(270));
        Log.e(TAG,"cos(300) = " + Math.cos(300));
        Log.e(TAG,"cos(480) = " + Math.cos(480));

        Log.e(TAG,"cos(0) = " + Math.cos(0));

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /*VolumeText*/
        int volumeTextX = getWidth() / 2;
        int volumeTextY = (int) (getHeight() * 0.04);
        mPaint.setColor(Color.WHITE);  //设置画笔颜色
        mPaint.setStrokeWidth(2);//设置画笔宽度
        mPaint.setAntiAlias(true); //指定是否使用抗锯齿功能，如果使用，会使绘图速度变慢
        mPaint.setTextSize((float) (getHeight() * 0.04));//设置文字大小
        mPaint.setStyle(Paint.Style.FILL);//绘图样式，设置为填充
        mPaint.setFakeBoldText(true);
        //样式设置
        mPaint.setFakeBoldText(true);//设置是否为粗体文字
        mPaint.setTextAlign(Paint.Align.CENTER);
        Log.e(TAG, "Setting Over begin to draw");
        canvas.drawText(volume + "", volumeTextX, volumeTextY, mPaint);
        /***************************END VOLUMETEXT*********************************/
//        mPaint.setColor(Color.BLUE);
//        canvas.drawLine(0, (float) (getHeight() * 0.05), getWidth(), (float) (getHeight() * 0.05), mPaint);
        /**********************************************************************************************/
        /****************************MAX DRAW************************************/
        mPaint.setColor(Color.WHITE);
        mPaint.setTextSize((float) (getHeight() * 0.02));
        canvas.drawText("MAX", volumeTextX, (float) (getHeight() * 0.08), mPaint);
        /*画五条基准音量线*/
        mPaint.setColor(Color.argb(15,255,255,255));
        canvas.drawLine(0, (float) (getHeight() * 0.1), getWidth(), (float) (getHeight() * 0.1), mPaint);
        canvas.drawLine(0, (float) ((getHeight() * 0.1) + (getHeight() * 0.8 * 0.25)), getWidth(), (float) ((getHeight() * 0.1) + (getHeight() * 0.8 * 0.25)), mPaint);
        canvas.drawLine(0, (float) ((getHeight() * 0.1) + (getHeight() * 0.8 * 0.5)), getWidth(), (float) ((getHeight() * 0.1) + (getHeight() * 0.8 * 0.5)), mPaint);
        canvas.drawLine(0, (float) ((getHeight() * 0.1) + (getHeight() * 0.8 * 0.75)), getWidth(), (float) ((getHeight() * 0.1) + (getHeight() * 0.8 * 0.75)), mPaint);
        canvas.drawLine(0, (float) (getHeight() * 0.9), getWidth(), (float) (getHeight() * 0.9), mPaint);
        /*画右边标志音量数字*/
        canvas.drawText("150", getWidth() - 40, (float) (getHeight() * 0.1) + 14, mPaint);
        canvas.drawText("100", getWidth() - 40, (float) ((getHeight() * 0.1) + (getHeight() * 0.8 * 0.25)) + 14, mPaint);
        canvas.drawText("50", getWidth() - 40, (float) ((getHeight() * 0.1) + (getHeight() * 0.8 * 0.5)) + 14, mPaint);
        canvas.drawText("0", getWidth() - 40, (float) ((getHeight() * 0.1) + (getHeight() * 0.8 * 0.75)) + 14, mPaint);
        /*画左右对称三根线*/
        mPaint.setColor(Color.argb(90,255,255,255));
        canvas.drawLine(0, (float) ((getHeight() * 0.1) + (getHeight() * 0.8 * 0.25)), volumeTextX, (float) (getHeight() * 0.1), mPaint);
        canvas.drawLine(getWidth(), (float) ((getHeight() * 0.1) + (getHeight() * 0.8 * 0.25)), volumeTextX, (float) (getHeight() * 0.1), mPaint);

        canvas.drawLine(0, (float) ((getHeight() * 0.1) + (getHeight() * 0.8 * 0.5)), volumeTextX, (float) ((getHeight() * 0.1) + (getHeight() * 0.8 * 0.25)), mPaint);
        canvas.drawLine(getWidth(), (float) ((getHeight() * 0.1) + (getHeight() * 0.8 * 0.5)), volumeTextX, (float) ((getHeight() * 0.1) + (getHeight() * 0.8 * 0.25)), mPaint);

        canvas.drawLine(0, (float) ((getHeight() * 0.1) + (getHeight() * 0.8 * 0.75)), volumeTextX, (float) ((getHeight() * 0.1) + (getHeight() * 0.8 * 0.5)), mPaint);
        canvas.drawLine(getWidth(), (float) ((getHeight() * 0.1) + (getHeight() * 0.8 * 0.75)), volumeTextX, (float) ((getHeight() * 0.1) + (getHeight() * 0.8 * 0.5)), mPaint);
        canvas.drawLine(0, (float) (getHeight() * 0.9), volumeTextX, (float) ((getHeight() * 0.1) + (getHeight() * 0.8 * 0.75)), mPaint);
        canvas.drawLine(getWidth(), (float) (getHeight() * 0.9), volumeTextX, (float) ((getHeight() * 0.1) + (getHeight() * 0.8 * 0.75)), mPaint);

//        /*画15%透明度的对称线*/
//        mPaint.setColor(Color.argb(15, 255, 255, 255));
//        canvas.drawLine(0, (float) (getHeight() * 0.9), volumeTextX, (float) (getHeight() * 0.1), mPaint);
//        canvas.drawLine(getWidth(), (float) (getHeight() * 0.9), volumeTextX, (float) (getHeight() * 0.1), mPaint);
//
//
//        canvas.drawLine(0, (float) (getHeight() * 0.9), volumeTextX, (float) ((getHeight() * 0.1) + (getHeight() * 0.8 * 0.25)), mPaint);
//        canvas.drawLine(getWidth(), (float) (getHeight() * 0.9), volumeTextX, (float) ((getHeight() * 0.1) + (getHeight() * 0.8 * 0.25)), mPaint);
//
//        canvas.drawLine(0, (float) (getHeight() * 0.9), volumeTextX, (float) ((getHeight() * 0.1) + (getHeight() * 0.8 * 0.5)), mPaint);
//        canvas.drawLine(getWidth(), (float) (getHeight() * 0.9), volumeTextX, (float) ((getHeight() * 0.1) + (getHeight() * 0.8 * 0.5)), mPaint);
//        canvas.drawLine(0, (float) (getHeight() * 0.9), volumeTextX, (float) ((getHeight() * 0.1) + (getHeight() * 0.8 * 0.75)), mPaint);
//        canvas.drawLine(getWidth(), (float) (getHeight() * 0.9), volumeTextX, (float) ((getHeight() * 0.1) + (getHeight() * 0.8 * 0.75)), mPaint);

        /*计算横线间隔距离并画间隔线和15透明度的三角形*/
        double interval1 = getHeight() * 0.8 * 0.25 / 10;
        for (double heightInterval = getHeight() * 0.1, i = 0; heightInterval < getHeight() * 0.1 + getHeight() * 0.8 * 0.25;
             heightInterval = heightInterval + interval1, i++) {
                mPaint.setColor(Color.argb(15, 255, 255, 255));
                canvas.drawLine(0, (float) heightInterval, getWidth(), (float) heightInterval, mPaint);
                canvas.drawLine(0, (float) (getHeight() * 0.9), getWidth() / 2, (float) heightInterval, mPaint);
                canvas.drawLine(getWidth(), (float) (getHeight() * 0.9), getWidth() / 2, (float) heightInterval, mPaint);
        }
        for (double heightInterval = getHeight() * 0.1 + getHeight() * 0.8 * 0.25; heightInterval < getHeight() * 0.1 + getHeight() * 0.8 * 0.5;
             heightInterval = heightInterval + interval1) {
            mPaint.setColor(Color.argb(15, 255, 255, 255));
            canvas.drawLine(0, (float) heightInterval, getWidth(), (float) heightInterval, mPaint);
            canvas.drawLine(0, (float) (getHeight() * 0.9), getWidth() / 2, (float) heightInterval, mPaint);
            canvas.drawLine(getWidth(), (float) (getHeight() * 0.9), getWidth() / 2, (float) heightInterval, mPaint);
        }
        for (double heightInterval = getHeight() * 0.1 + getHeight() * 0.8 * 0.5; heightInterval < getHeight() * 0.1 + getHeight() * 0.8 * 0.75;
             heightInterval = heightInterval + interval1) {
            mPaint.setColor(Color.argb(15, 255, 255, 255));
            canvas.drawLine(0, (float) heightInterval, getWidth(), (float) heightInterval, mPaint);
            canvas.drawLine(0, (float) (getHeight() * 0.9), getWidth() / 2, (float) heightInterval, mPaint);
            canvas.drawLine(getWidth(), (float) (getHeight() * 0.9), getWidth() / 2, (float) heightInterval, mPaint);
        }
        for (double heightInterval = getHeight() * 0.1 + getHeight() * 0.8 * 0.75; heightInterval < getHeight() * 0.9;
             heightInterval = heightInterval + interval1) {
            mPaint.setColor(Color.argb(15, 255, 255, 255));
            canvas.drawLine(0, (float) heightInterval, getWidth(), (float) heightInterval, mPaint);
            canvas.drawLine(0, (float) (getHeight() * 0.9), getWidth() / 2, (float) heightInterval, mPaint);
            canvas.drawLine(getWidth(), (float) (getHeight() * 0.9), getWidth() / 2, (float) heightInterval, mPaint);
        }
        if(volume >= 150){
            mPaint.setColor(Color.argb(95,255,255,255));
            for(double heightInterval = getHeight() * 0.1 ; heightInterval < getHeight() * 0.1 + getHeight() * 0.8 * 0.75 ; heightInterval = heightInterval + interval1){
                canvas.drawLine(0, (float) (getHeight() * 0.9), getWidth() / 2, (float) heightInterval, mPaint);
                canvas.drawLine(getWidth(), (float) (getHeight() * 0.9), getWidth() / 2, (float) heightInterval, mPaint);
            }
        }
        if((volume > 0 && volume <150)){
            Log.e(TAG,"volume = " + volume);
            mPaint.setColor(Color.argb(95,255,255,255));
//            double heightInterval = getHeight() *0.1 + getHeight()*0.8 *0.75;

            for(double heightInterval = getHeight() * 0.1 + getHeight() * 0.8 * 0.75,i = 0 ;i< volume; heightInterval = heightInterval - interval1,i= i+5){
//                Log.e(TAG,"drawLine");
                canvas.drawLine(0, (float) (getHeight() * 0.9), getWidth() / 2, (float) heightInterval, mPaint);
                canvas.drawLine(getWidth(), (float) (getHeight() * 0.9), getWidth() / 2, (float) heightInterval, mPaint);
            }
        }

        /*计算底部三角数值并画三角线*/
        double bottomTopX = getWidth() / 2;
        double bottomTopY = getHeight() * 0.1 + getHeight() * 0.8 * 0.75;
        double bottomBottomLeftX = getWidth() * 0.35;
        double bottomBottomY = getHeight() * 0.9;
        double bottomBottomRightX = getWidth() - getWidth() * 0.35;
        mPaint.setColor(Color.argb(80, 255, 255, 255));
        mPaint.setStrokeWidth(3);
        canvas.drawLine((float) bottomTopX, (float) bottomTopY, (float) bottomBottomLeftX, (float) bottomBottomY, mPaint);
        canvas.drawLine((float) bottomTopX, (float) bottomTopY, (float) bottomBottomRightX, (float) bottomBottomY, mPaint);

        double bottomAngleTopInterval = getHeight() * 0.8 * 0.25 * 0.3 / 20;
        for (double heightInterval = getHeight() * 0.1 + getHeight() * 0.8 * 0.75;
             heightInterval < getHeight() * 0.1 + getHeight() * 0.8 * 0.75 + getHeight() * 0.8 * 0.25 * 0.3; heightInterval = heightInterval + bottomAngleTopInterval) {
            canvas.drawLine((float) bottomBottomLeftX, (float) bottomBottomY, (float) bottomTopX, (float) heightInterval, mPaint);
            canvas.drawLine((float) bottomBottomRightX, (float) bottomBottomY, (float) bottomTopX, (float) heightInterval, mPaint);
        }
        mPaint.setColor(Color.argb(50, 255, 255, 255));
        for (double heightInterval = getHeight() * 0.1 + getHeight() * 0.8 * 0.75 + getHeight() * 0.8 * 0.25 * 0.3;
             heightInterval < getHeight() * 0.1 + getHeight() * 0.8 * 0.75 + getHeight() * 0.8 * 0.25 * 0.6; heightInterval = heightInterval + bottomAngleTopInterval) {
            canvas.drawLine((float) bottomBottomLeftX, (float) bottomBottomY, (float) bottomTopX, (float) heightInterval, mPaint);
            canvas.drawLine((float) bottomBottomRightX, (float) bottomBottomY, (float) bottomTopX, (float) heightInterval, mPaint);
        }
        mPaint.setColor(Color.argb(20, 255, 255, 255));
        for (double heightInterval = getHeight() * 0.1 + getHeight() * 0.8 * 0.75 + getHeight() * 0.8 * 0.25 * 0.6;
             heightInterval < getHeight() * 0.85; heightInterval = heightInterval + bottomAngleTopInterval) {
            canvas.drawLine((float) bottomBottomLeftX, (float) bottomBottomY, (float) bottomTopX, (float) heightInterval, mPaint);
            canvas.drawLine((float) bottomBottomRightX, (float) bottomBottomY, (float) bottomTopX, (float) heightInterval, mPaint);
        }

        mPaint.setColor(Color.WHITE);
        mPaint.setTextSize((float) (getHeight() * 0.02));
        canvas.drawText("MIN", volumeTextX, (float) (getHeight() * 0.92), mPaint);

        mPaint.setColor(Color.WHITE);
        int reduceWidth = bm_reduce.getWidth();
        int reduceHeight = bm_increase.getHeight();
        int increaseWidth = bm_increase.getWidth();
        int increasetHeight = bm_increase.getHeight();
        /*画出调节音量图片*/
        canvas.drawBitmap(bm_reduce, null, new Rect(30, (int) (getHeight() * 0.92), 30 + reduceWidth, (int) (getHeight() * 0.92 + reduceHeight)), mPaint);

        canvas.drawBitmap(bm_increase, null, new Rect(getWidth() - 30 - increaseWidth,
                (int) (getHeight() * 0.92), getWidth() - 30, (int) (getHeight() * 0.92 + increasetHeight)), mPaint);



        int lineHeight = (int) (getHeight() * 0.92 + increasetHeight / 2);
        double originLineX = 30 + reduceWidth + 20;
        double endLineX = getWidth() - (30 + increaseWidth + 20);
        double totalLineWidth = endLineX - originLineX;
        double lineInterval = 18;
        Log.e(TAG, "lineHeight = " + lineHeight + " : originLineX = " + originLineX + " : endLineX = " + endLineX +
                " : totalLineWidth = " + totalLineWidth + " : lineInterval = " + lineInterval);
        mPaint.setColor(Color.argb(50,255,255,255));
        for (double with = originLineX; with <  endLineX; with = with + lineInterval) {
            canvas.drawLine((float) with, (float) lineHeight, (float) (with + lineInterval - 5), lineHeight, mPaint);
        }
        mPaint.setColor(Color.argb(95,255,255,255));
        for(double with = originLineX ,i = 0 ; i < volume ; with = with + lineInterval,i = i+10){
            canvas.drawLine((float) with, (float) lineHeight, (float) (with + lineInterval - 5), lineHeight, mPaint);
        }

    }

//    @Override
//    protected void onAttachedToWindow() {
//        mTouchSlop = getHeight() / 10;
//        Log.e(TAG,"onAttachedToWindow mTouchSlop = " + mTouchSlop);
//        super.onAttachedToWindow();
//    }

//    @Override
//    protected void onWindowFocusChanged() {
//        mTouchSlop = getHeight() / 10;
//        Log.e(TAG,"onAttachedToWindow mTouchSlop = " + mTouchSlop);
//        super.onAttachedToWindow();
//    }

//    @Override
//    public boolean hasWindowFocus() {
//        mTouchSlop = getHeight() / 10;
//        Log.i(TAG,"mTouchSlop1 = " + mTouchSlop);
//        return super.hasWindowFocus();
//    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        Log.i(TAG,"event : " + event.getAction());
        switch (event.getAction()){

            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                downY = event.getY();
                Log.i(TAG,"ACTION_DOWN downX = " + downX + " : downY = " + downY);
                downInReduc = isTouchInReduce(event);
                downInIncrease = isTouchInIncrease(event);
                downInVolume = isTouchInVolume(event);

                currentMaxVolume = volume + maxInCreaseVolume;
                currentMinVolume = volume - 50;

                Log.i(TAG,"onTouchEvent ACTION_DOWN downInReduc = " +  downInReduc + " : downInIncrease = " + downInIncrease +
                " : downInVolume = " + downInVolume + " : currentMaxVolume = " + currentMaxVolume);

                break;
            case MotionEvent.ACTION_MOVE:
                Log.e(TAG,"onTouchEvent ACTION_MOVE");
                moveInVolume = isTouchInVolume(event);
                Log.i(TAG,"onTouchEvent ACTION_MOVE downInReduc = " +  downInReduc + " : downInIncrease = " + downInIncrease +
                        " : downInVolume = " + downInVolume + " : moveInVolume = " + moveInVolume);
                if(!downInReduc && !downInIncrease && downInVolume && moveInVolume){
//                    if(Math.abs(event.getY() - downY) > mTouchSlop){
                        caculateVolumeByEnvent(event);
//                    }

                }
//                if(Math.abs(event.getY() - downY) > Math.abs(event.getX() - downX)){
//                    if(event.getY() - downY < 0){
//                        if(Math.abs(event.getY() - downY) > mTouchSlop){
//                            double increaseVolume = Math.abs(event.getY() - downY)/mTouchSlop;
//                            Log.e(TAG,"increaseVolume = " + (int)increaseVolume);
////                            if(increaseVolume > 50){
////                                increaseVolume = 50;
////                            }
//                            volume = (int) (volume + increaseVolume);
//                            if(volume > 150){
//                                volume = 150;
//                            }else if(volume < 0){
//                                volume = 0;
//                            }
//                            setVolume(volume);
//                            return true;
//                        }
//                    }else {
//                        if(Math.abs(event.getY() - downY) > mTouchSlop){
//                            double reduceVolume = Math.abs(event.getY() - downY)/mTouchSlop;
//                            volume = (int) (volume - reduceVolume);
//                            if(volume > 150){
//                                volume = 150;
//                            }else if(volume < 0){
//                                volume = 0;
//                            }
//                            setVolume(volume);
//                            return true;
//                        }
//                    }
//                }
                break;
            case MotionEvent.ACTION_UP:
                Log.e(TAG,"onTouchEvent ACTION_UP");
                upInReduc = isTouchInReduce(event);
                upInIncrease = isTouchInIncrease(event);
                /*恢复按下值*/
                downX = 0;
                downY = 0;
                Log.e(TAG,"ACTION_UP downInReduce = " + downInReduc  + " : downInIncrease = " + downInIncrease);
                Log.i(TAG,"onTouchEvent ACTION_UP upInReduc = " + upInReduc + " : upInIncrease = " + upInIncrease);
                if(downInReduc && upInReduc){
                    volume  = volume -10;
                    if(volume < 0){
                        volume = 0;
                    }
                    setVolume(volume);
                    Log.e(TAG,"onTouchEvent ACTION_UP  volume = " + volume );
                    downInReduc = false;
                    downInIncrease = false;
                    upInReduc = false;
                    upInIncrease = false;
                    downInVolume = false;
                    moveInVolume = false;
                    currentMaxVolume = 0;
                    return true;
                }
                if(downInIncrease && upInIncrease){
                    volume = volume + 10;
                    if(volume > 150){
                        volume = 150;
                    }
                    setVolume(volume);
                    downInReduc = false;
                    downInIncrease = false;
                    upInReduc = false;
                    upInIncrease = false;
                    return true;
                }
            {
                downInReduc = false;
                downInIncrease = false;
                upInReduc = false;
                upInIncrease = false;
                downInVolume = false;
                moveInVolume = false;
                currentMaxVolume = 0;
            }

                break;
        }
        return true;
    }

    private boolean isTouchInReduce(MotionEvent event){
        if(event.getX() < 40 || (event.getX() > (40 + bm_reduce.getWidth()))){
            return false;
        }
        if(event.getY() < getHeight() * 0.92 || event.getY() > (getHeight() * 0.92 + bm_reduce.getHeight()) ){
            return false;
        }
        return true;
    }
    private boolean isTouchInIncrease(MotionEvent event){
        if(event.getX() < (getWidth() - (40 + bm_increase.getWidth())) || event.getX() > (getWidth() -40) ){
            return false;
        }
        if(event.getY() < getHeight() * 0.92 || event.getY() > (getHeight() * 0.92 + bm_increase.getHeight()) ){
            return false;
        }
        return true;
    }
    private boolean isTouchInVolume(MotionEvent event){
        if(event.getY() < getHeight() * 0.1 || (event.getY() > getHeight()* 0.9 /*+ (getHeight()*0.8*0.75)*/)){
            return false;
        }
        return true;
    }

    public void setVolume(int volume){
        this.volume = volume;
        this.invalidate();
    }
    public int getVolume(){
        return this.volume;
    }

    private void caculateVolumeByEnvent(MotionEvent event){

//        if(Math.abs(event.getY() - downY) > Math.abs(event.getX() - downX)){
//                    if(event.getY() - downY < 0){
//                        if(Math.abs(event.getY() - downY) > mTouchSlop){
//                            double increaseVolume = Math.abs(event.getY() - downY)/mTouchSlop;
//                            Log.e(TAG,"increaseVolume = " + increaseVolume);
//                            volume = (int) (volume + increaseVolume);
////                            if(volume > currentMaxVolume){
////                                return;
////                            }
//                            if(volume > 150){
//                                volume = 150;
//                            }else if(volume < 0){
//                                volume = 0;
//                            }
//                            setVolume(volume);
//                            return ;
//                        }
//                    }else {
//                        if(Math.abs(event.getY() - downY) > mTouchSlop){
//                            double reduceVolume = Math.abs(event.getY() - downY)/mTouchSlop;
//                            volume = (int) (volume - reduceVolume);
//                            if(volume > 150){
//                                volume = 150;
//                            }else if(volume < 0){
//                                volume = 0;
//                            }
//                            setVolume(volume);
//                            return ;
//                        }
//                    }
//                }
        double totalHeight =getHeight()*0.8; /*getHeight()*0.8*0.75;*/
        double currentHeight = event.getY();
        double intervalWithCurrentHeight = getHeight()*0.9 - currentHeight;/*getHeight()*0.1 + getHeight()*0.8*0.75 - currentHeight;*/
//        if(Math.abs(event.getY() - downX) < mTouchSlop){
//            return;
//        }
        if(intervalWithCurrentHeight == 0 ){
            volume = 0;
            setVolume(volume);
            return;
        }
        if(currentHeight == getHeight()*0.1){
            volume = 150;
            setVolume(volume);
            return;
        }

        double percent = intervalWithCurrentHeight/totalHeight;
        Log.e(TAG,"caculateVolumeByEnvent percent = " + percent);
        if(percent > 1){
            percent =1;
        }
        int caculateVolume = (int) (percent*150);
//        if(caculateVolume > currentMaxVolume){
//            return;
//        }
        Log.e(TAG,"caculateVolumeByEnvent caculateVolume = " + caculateVolume);
        setVolume(caculateVolume);
    }

}
