package com.example.wallet.feature.details;

import android.content.Intent;
import android.os.Handler;
import android.widget.ProgressBar;

import com.example.wallet.R;
import com.example.wallet.feature.details.base.BaseActivity;

public class SplashActivity extends BaseActivity {

    private Handler handler;
    private ProgressBar progressBar;
    private int startTime;
    private final int maxTime = 200;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {
        progressBar = findViewById(R.id.progressBar);
        progressBar.setMax(maxTime);
        progressBar.setProgress(0);
        handler = new Handler();
        Thread thread = new Thread(() -> {
            try {
                for (startTime = 0; startTime < maxTime; startTime++) {
                    Thread.sleep(10);
                    handler.post(updateProgress);
                }
            } catch (InterruptedException exc) {
                exc.printStackTrace();
            }
        });
        thread.start();
    }

    Runnable updateProgress = new Runnable() {
        @Override
        public void run() {
            progressBar.setProgress(startTime);
            if (startTime >= maxTime - 1) {
                startActivity(new Intent(SplashActivity.this, BalanceResultActivity.class));
                finish();
            }
        }
    };
}