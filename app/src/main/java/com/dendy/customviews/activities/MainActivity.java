package com.dendy.customviews.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.dendy.view.R;

public class MainActivity extends Activity implements View.OnClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();

   private Button customseekbar,Q5ChannelBalanceSeekBar,VolumeView,M7VolumnView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.customseekBar:
                startActivity(new Intent(this,CustomSeekBarActivity.class));
                break;
            case R.id.Q5ChannelBalanceSeekBar:
                startActivity(new Intent(this,Q5ChannelBalanceSeekBarActivity.class));
                break;
            case R.id.VolumeView:
                startActivity(new Intent(this,VolumeViewActivity.class));
                break;
            case R.id.M7VolumnView:
                startActivity(new Intent(this,M7VolumeViewActivity.class));
                break;
        }
    }

    private void initViews(){
        customseekbar = (Button) findViewById(R.id.customseekBar);
        customseekbar.setOnClickListener(this);
        Q5ChannelBalanceSeekBar = (Button) findViewById(R.id.Q5ChannelBalanceSeekBar);
        Q5ChannelBalanceSeekBar.setOnClickListener(this);
        VolumeView = (Button) findViewById(R.id.VolumeView);
        VolumeView.setOnClickListener(this);
        M7VolumnView = (Button) findViewById(R.id.M7VolumnView);
        M7VolumnView.setOnClickListener(this);
    }
}
