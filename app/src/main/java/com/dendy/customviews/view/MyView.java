package com.dendy.customviews.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

/**
 * Created by Administrator on 2018/2/27.
 */

public class MyView extends View {
    private static final String TAG = MyView.class.getSimpleName();

    Context m_context;
    public MyView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub

        m_context=context;
    }

    //重写OnDraw（）函数，在每次重绘时自主实现绘图
    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);


        //设置画笔基本属性
        Paint paint=new Paint();
        paint.setAntiAlias(true);//抗锯齿功能
        paint.setColor(Color.RED);  //设置画笔颜色
        paint.setStyle(Paint.Style.STROKE);//设置填充样式   Style.FILL/Style.FILL_AND_STROKE/Style.STROKE
        paint.setStrokeWidth(15);//设置画笔宽度
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        paint.setShadowLayer(15, 15, 15, Color.GREEN);//设置阴影

        //设置画布背景颜色
        canvas.drawRGB(255, 255,255);
        float []pts={10,10,100,100,200,200,400,400};
        //画圆
        canvas.drawCircle(190, 200, 150, paint);
        canvas.drawLines(pts,paint);

    }

}
