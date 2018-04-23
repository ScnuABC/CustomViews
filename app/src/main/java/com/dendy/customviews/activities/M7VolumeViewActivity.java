package com.dendy.customviews.activities;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dendy.customviews.view.LineCircleProgressView;
import com.dendy.customviews.view.LineVolumeView;
import com.dendy.customviews.view.TouchRotateButton;
import com.dendy.view.R;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2018/3/15.
 */

public class M7VolumeViewActivity extends Activity implements View.OnClickListener {

    private static final String TAG = M7VolumeViewActivity.class.getSimpleName();

    private TextView tv_curVolume;
    private Button btn_reduce;
    private Button btn_plus;
    private TouchRotateButton mTouchRotateButton;
    private LineCircleProgressView mLineCircleProgressView;


    SharedPreferences sharedPreferences ;

    private LineVolumeView lineVolumeView;
    private TextView tv_line_curvolume;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.m7volumeview);
//        requestWindowFeature(Window.F);
        sharedPreferences = getSharedPreferences("FiiOMusic",MODE_PRIVATE);
        initView();

    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpUI();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        setResult(0x00);
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_resuc:
                int curvolume = mLineCircleProgressView.getCurProgress();
                if(curvolume <= 0){
                    mLineCircleProgressView.setCurProgress(0);
                    tv_curVolume.setText(0 + "");
                    lineVolumeView.setCurVolume(0);
                    tv_line_curvolume.setText((0 +""));
                }else {
                    mLineCircleProgressView.setCurProgress(curvolume -1);
                    tv_curVolume.setText((curvolume - 1) + "");
                    lineVolumeView.setCurVolume(curvolume - 1);
                    tv_line_curvolume.setText((curvolume -1) + "");
                }
                mTouchRotateButton.setCurDegree(mLineCircleProgressView.getCurProgress() * 5 + 30);
                Log.e(TAG,"onClick btn_resuc curDegree = " + mLineCircleProgressView.getCurProgress() * 5);
                sharedPreferences.edit().putFloat("curDegree",mLineCircleProgressView.getCurProgress() * 5).commit();
                break;
            case R.id.btn_plus:
                int curVolume_1 = mLineCircleProgressView.getCurProgress();
                Log.i(TAG, "onClick: curVolume_1 = " + curVolume_1);
                if(curVolume_1 == 60){
                    mLineCircleProgressView.setCurProgress(60);
                    tv_curVolume.setText(60 + "");
                    lineVolumeView.setCurVolume(60);
                    tv_line_curvolume.setText(60 + "");
                }else {
                    mLineCircleProgressView.setCurProgress(curVolume_1 + 1);
                    tv_curVolume.setText((curVolume_1 + 1)+ "");
                    lineVolumeView.setCurVolume(curVolume_1 +1);
                    tv_line_curvolume.setText((curVolume_1 + 1) + "");
                }

                mTouchRotateButton.setCurDegree(mLineCircleProgressView.getCurProgress() * 5 + 30);
                Log.e(TAG,"onClick btn_plus curDegree = " + mLineCircleProgressView.getCurProgress() * 5);
                sharedPreferences.edit().putFloat("curDegree",mLineCircleProgressView.getCurProgress() * 5).commit();
                break;
        }

    }



    private void initView(){
        tv_curVolume = (TextView) findViewById(R.id.tv_curvolume);
        mTouchRotateButton = (TouchRotateButton) findViewById(R.id.trb);
        mLineCircleProgressView = (LineCircleProgressView) findViewById(R.id.lcp);

        mTouchRotateButton = (TouchRotateButton) findViewById(R.id.trb);
        mTouchRotateButton.setBgImageInt(R.drawable.img_volume_bg);
        mTouchRotateButton.setBgPressImageInt(R.drawable.img_volume_top);
        mLineCircleProgressView = (LineCircleProgressView) findViewById( R.id.lcp);
        lineVolumeView = (LineVolumeView) findViewById(R.id.lvv);
        tv_line_curvolume = (TextView) findViewById(R.id.tv_line_curvolume);

        mTouchRotateButton.setOnChangeDegreeListening(new TouchRotateButton.OnChangeDegreeListening() {
            @Override
            public void onChangeDegree(float curDegree) {
                Log.e(TAG,"onChangeDegree curDegree = " + mTouchRotateButton.getCurDegree());
                int value = new BigDecimal(curDegree - 30).setScale(0, BigDecimal.ROUND_HALF_UP).intValue() / 5;
                Log.i(TAG,"onChangeDegree value = " + value);
                mLineCircleProgressView.setCurProgress(value);
                lineVolumeView.setCurVolume(value);
                tv_curVolume.setText(value + "");
                tv_line_curvolume.setText(value + "");
                sharedPreferences.edit().putFloat("curDegree",curDegree).commit();
            }
        });
        btn_reduce = (Button) findViewById(R.id.btn_resuc);
        btn_plus = (Button) findViewById(R.id.btn_plus);
        btn_reduce.setOnClickListener(this);
        btn_plus.setOnClickListener(this);

    }
    public void setUpUI(){
        float curDegree = sharedPreferences.getFloat("curDegree",0);
        Log.i(TAG,"setUpUI curDegree = " + curDegree);
        mTouchRotateButton.setCurDegree(curDegree + 30);
        int tempCurVolume = (int) (Float.valueOf(curDegree) / 5);
        Log.e(TAG,"setUpUI tempCurVolume = " + tempCurVolume);
        tv_curVolume.setText(tempCurVolume + "");
        mLineCircleProgressView.setCurProgress(tempCurVolume);
        lineVolumeView.setCurVolume(tempCurVolume);
        tv_line_curvolume.setText(tempCurVolume + "");
    }

}
