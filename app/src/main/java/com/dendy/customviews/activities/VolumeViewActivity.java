package com.dendy.customviews.activities;

import android.app.Activity;
import android.os.Bundle;

import com.dendy.view.R;

/**
 * Created by Administrator on 2018/3/15.
 */

public class VolumeViewActivity extends Activity {
    private static final String TAG = VolumeViewActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volumnview);
    }
}
