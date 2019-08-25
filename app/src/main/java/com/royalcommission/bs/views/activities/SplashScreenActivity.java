package com.royalcommission.bs.views.activities;

import android.content.Intent;
import android.os.Bundle;

import com.royalcommission.bs.R;

public class SplashScreenActivity extends BaseActivity {

    private static final String TAG = "SPLASH";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        doSplash();
    }

    private void doSplash() {
        runOnUIThread(this::login, SPLASH_SCREEN_INTERVAL);
    }

    private void login() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        if (isGreaterThanLolipop())
            SplashScreenActivity.this.overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        finish();
    }
}
