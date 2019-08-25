package com.royalcommission.bs.views.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.royalcommission.bs.R;
import com.royalcommission.bs.app.AppController;
import com.royalcommission.bs.modules.services.LifecycleService;
import com.royalcommission.bs.modules.utils.CommonUtils;
import com.royalcommission.bs.modules.utils.LocaleHelper;
import com.royalcommission.bs.views.fragments.login.LoginSelectionFragment;

import java.util.Timer;
import java.util.TimerTask;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private Button language;
    private final String TAG = LoginActivity.this.getClass().getName();
    private Handler mHandler;
    private TextView time;
    private Timer timer;
    private static final long ONE_SECOND = 60 * 1000;
    private Handler updateLabel;

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    @SuppressLint("HandlerLeak")
    private void init() {
        time = findViewById(R.id.time);
        TextView date = findViewById(R.id.date);
        language = findViewById(R.id.language);
        language.setOnClickListener(this);
        mHandler = new Handler();
        updateLabel = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                time.setText(CommonUtils.getHomePageTime());
            }
        };
        date.setText(CommonUtils.getHomePageDate());
        reScheduleTimer();
        setLanguage();
        loadHomeFragment(R.id.container);
        scheduleLifeCycleService();
    }

    public void reScheduleTimer() {
        timer = new Timer();
        TimerTask timerTask = new MyTimerTask();
        timer.schedule(timerTask, 0, ONE_SECOND);
    }

    private void scheduleLifeCycleService() {
        startService(new Intent(this, LifecycleService.class));
    }

    private class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            updateLabel.sendEmptyMessage(0);
        }
    }

    public void goToHome() {
        showToastMessage(getString(R.string.login_success));
        Intent intent = new Intent(this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        this.finish();
    }

    private void setLanguage() {
        if (language != null) {
            if (AppController.isEnglish()) {
                language.setText(getString(R.string.arabic_text));
            } else {
                language.setText(getString(R.string.english_text));
            }
        }
    }

    public void loadHomeFragment(int containerId) {
        Runnable mPendingRunnable = () -> addHomeFragment(containerId, new LoginSelectionFragment());
        if (mHandler == null)
            mHandler = new Handler();
        mHandler.post(mPendingRunnable);
    }

    public void loadFragment(int containerId, final Fragment fragment, final boolean addToBackStack, final String tag) {
        if (fragment != null) {
            Runnable mPendingRunnable = () -> addFragment(containerId, fragment, addToBackStack, tag);
            if (mHandler == null)
                mHandler = new Handler();
            mHandler.post(mPendingRunnable);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == language.getId()) {
            if (AppController.isEnglish()) {
                setLanguage(AppController.LANGUAGE_ARABIC);
            } else {
                setLanguage(AppController.LANGUAGE_ENGLISH);
            }
        }
    }

    public void setLanguage(String language) {
        if (language != null && !language.equalsIgnoreCase(AppController.getLanguageCode())) {
            LocaleHelper.setLocale(this, language);
            recreate();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        hideKeyboard();
    }

    @Override
    protected void onDestroy() {
        clearTimer();
        super.onDestroy();
    }

    private void clearTimer() {
        if (timer != null) {
            timer.cancel();
            timer.purge();
        }
    }

    public void clearBackStack() {
        try {
            if (getSupportFragmentManager() != null)
                getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        } catch (IllegalStateException e) {
            Log.d("clearBackStack", String.valueOf(e));
        }
    }

    @Override
    public void onBackPressed() {
        Log.d(TAG, "onBackPressed: ");
        if (getSupportFragmentManager() != null && getSupportFragmentManager().getBackStackEntryCount() > 0) {
            for (Fragment frag : getSupportFragmentManager().getFragments()) {
                if (frag.isVisible()) {
                    FragmentManager childFm = frag.getChildFragmentManager();
                    if (childFm.getBackStackEntryCount() > 0) {
                        childFm.popBackStack();
                        return;
                    }
                }
            }
            super.onBackPressed();
        } else {
            this.finish();
        }
    }

    public Fragment getFragment() {
        return getSupportFragmentManager().findFragmentById(R.id.container);
    }
}
