package com.dendy.customviews.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.dendy.customviews.utils.DensityUtils;
import com.dendy.view.R;

/**
 * Created by Administrator on 2018/3/9.
 */

public class Q5ChannelBalanceSeekBar extends View {
    private static final String TAG = Q5ChannelBalanceSeekBar.class.getSimpleName();

    private Context context;

    private Bitmap backgroud;
    private Bitmap thumb;
    private Bitmap right;
    private Bitmap left;

    private int with;
    private int height;

    private int center_divide;
    private int current_x;
    private int thumb_with;
    private int right_with;
    private int left_with;

    private Paint mPaint;

    private Bitmap clip_rigth;
    private Bitmap clip_left;


    private int downX = 0;

    private int progress = 0;

    private int perWidth;

    public ResponseTouch responseTouch;

    public Q5ChannelBalanceSeekBar(Context context) {
        super(context);
    }

    public Q5ChannelBalanceSeekBar(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public Q5ChannelBalanceSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        backgroud = BitmapFactory.decodeResource(context.getResources(), R.drawable.img_balance_bg);
        thumb = BitmapFactory.decodeResource(context.getResources(),R.drawable.icon_balance_thumb);
        right = BitmapFactory.decodeResource(context.getResources(),R.drawable.img_balance_r_bar);
        left = BitmapFactory.decodeResource(context.getResources(),R.drawable.img_balance_l_bar);
        right_with = right.getWidth();
        current_x = backgroud.getWidth() / 2;
        thumb_with = thumb.getWidth();
        perWidth = backgroud.getWidth() /20;
        initPaint();


    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        with = DensityUtils.dp2px(context,321);
        height = DensityUtils.dp2px(context,32);
        setMeasuredDimension(with,height);
        center_divide = with/2;
//        perWidth = with / 20;
        Log.i(TAG, "onMeasure: with = " + with + " : height = " + height + " : perWidth = " + perWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);
        drawBackgroudBitmap(canvas);
//        current_x = center_divide + 400;
        drawRightBitmap(canvas);
        drawLeftBitmap(canvas);
        drawThumBitmap(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
               downX = (int) event.getX();
                Log.w(TAG, "onTouchEvent: downX = " + downX);
                break;
            case MotionEvent.ACTION_MOVE:
//                if(isTouchOnThumb(downX)){
                    current_x = (int) event.getX();
                    Log.e(TAG, "onTouchEvent: current_x = " + current_x);
                    invalidate();
//                }
                break;
            case MotionEvent.ACTION_UP:
                progress = caculateSection((int) event.getX());
                Log.i(TAG, "onTouchEvent ACTION_UP: progress = " + progress);
                current_x = progress * perWidth;
                invalidate();
                if(responseTouch != null){
                    responseTouch.response(progress);
                }
                break;
            default:
                break;
        }

        return true;

    }

    private void drawBackgroudBitmap(Canvas canvas){
        int x_1 = 0;
        int y_1 = thumb.getHeight() - backgroud.getHeight();
        Log.i(TAG, "drawBackgroudBitmap: backgroud  width = " + backgroud.getWidth() + " :  heigth = " + backgroud.getHeight());
        canvas.drawBitmap(backgroud,x_1,y_1,mPaint);
    }

    private void drawThumBitmap(Canvas canvas){
        if(current_x <= thumb_with){
            canvas.drawBitmap(thumb,0,0,mPaint);
        }else if(current_x > (with - thumb_with)){
            canvas.drawBitmap(thumb,with - thumb_with,0,mPaint);
        }else if(current_x > thumb_with && current_x < (with - thumb_with) ){
            canvas.drawBitmap(thumb,current_x - thumb_with/2,0,mPaint);
        }
    }

    private void drawLeftBitmap(Canvas canvas){
        if(current_x < center_divide && current_x > 0){
            clip_left = Bitmap.createBitmap(left,current_x,0,center_divide - current_x,left.getHeight());
            canvas.drawBitmap(clip_left,current_x,height - left.getHeight(),mPaint);
        }else if(current_x <=0){
            clip_left = Bitmap.createBitmap(left,0,0,left.getWidth(),left.getHeight());
            canvas.drawBitmap(clip_left,current_x,height - left.getHeight(),mPaint);
        }
    }
    private void drawRightBitmap(Canvas canvas){
        if((current_x > center_divide) && (current_x <= with )){
            clip_rigth = Bitmap.createBitmap(right,0,0,(current_x - center_divide),right.getHeight());
            canvas.drawBitmap(clip_rigth,center_divide,height - backgroud.getHeight(),mPaint);
        }else if(current_x > with){
            clip_rigth = Bitmap.createBitmap(right,0,0,right.getWidth(),right.getHeight());
            canvas.drawBitmap(clip_rigth,center_divide,height - backgroud.getHeight(),mPaint);
        }
    }


    private void initPaint(){
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(3);
        mPaint.setColor(Color.WHITE);
    }

    private boolean isTouchOnThumb(int touchX){
        if(touchX < current_x - thumb_with  || touchX > current_x + thumb_with){
            return false;
        }else {
            return true;
        }
    }

    private int caculateSection(int currentX){
       if(currentX <= 0 || currentX < perWidth / 2){
           return 0;
       }else if(currentX > perWidth/2  && currentX <= perWidth * 3 / 2){
           return  1;
       }else if(currentX > perWidth * 3 / 2 && currentX <= perWidth* 5/2 ){
           return 2;
       }else if(currentX > perWidth* 5/2 && currentX <= perWidth * 7 /2){
           return 3;
       }else if(currentX > perWidth* 7/2 && currentX <= perWidth * 9 /2){
           return 4;
       }else if(currentX > perWidth* 9/2 && currentX <= perWidth * 11 /2){
           return 5;
       }else if(currentX > perWidth* 11/2 && currentX <= perWidth * 13 /2){
           return 6;
       }else if(currentX > perWidth* 13/2 && currentX <= perWidth * 15 /2){
           return 7;
       }else if(currentX > perWidth* 15/2 && currentX <= perWidth * 17 /2){
           return 8;
       }else if(currentX > perWidth* 17/2 && currentX <= perWidth * 19 /2){
           return 9;
       }else if(currentX > perWidth* 19/2 && currentX <= perWidth * 21 /2){
           return 10;
       }else if(currentX > perWidth* 21/2 && currentX <= perWidth * 23 /2){
           return 11;
       }else if(currentX > perWidth* 23/2 && currentX <= perWidth * 25 /2){
           return 12;
       }else if(currentX > perWidth* 25/2 && currentX <= perWidth * 27 /2){
           return 13;
       }else if(currentX > perWidth* 29/2 && currentX <= perWidth * 31 /2){
           return 14;
       }else if(currentX > perWidth* 31/2 && currentX <= perWidth * 33 /2){
           return 15;
       }else if(currentX > perWidth* 33/2 && currentX <= perWidth * 35/2){
           return 16;
       }else if(currentX > perWidth* 35/2 && currentX <= perWidth * 37 /2){
           return 17;
       }else if(currentX > perWidth* 37/2 && currentX <= perWidth * 39 /2){
           return 18;
       }else if(currentX > perWidth* 39/2 && currentX <= perWidth * 41 /2){
           return 19;
       }else{
           return 20;
       }
    }


    public void setProgress(int progress) {
        this.progress = progress;
        current_x = progress * perWidth;
        Log.i(TAG, "setProgress: current_x = " + current_x);
        invalidate();
    }

    public interface ResponseTouch{
        void response(int progress);
    }

    public void setResponseTouch(ResponseTouch responseTouch) {
        this.responseTouch = responseTouch;
    }
}
