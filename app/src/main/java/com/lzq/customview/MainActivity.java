package com.lzq.customview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.lzq.clock.ClockView;

/**
 * @author LZQ
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ClockView mView = findViewById(R.id.main_clock);
        Log.d(TAG, "onCreate: " + (mView == null));
        mView.startRun();
    }
}
