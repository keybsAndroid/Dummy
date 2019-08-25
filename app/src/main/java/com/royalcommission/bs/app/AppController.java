package com.royalcommission.bs.app;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.multidex.MultiDex;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.royalcommission.bs.modules.api.network.APIClient;
import com.royalcommission.bs.modules.api.network.WebService;
import com.royalcommission.bs.modules.utils.LocaleHelper;
import com.royalcommission.bs.modules.utils.SharedPreferenceUtils;
import com.royalcommission.bs.views.activities.HomeActivity;

import timber.log.Timber;

/**
 * Created by Prashant on 10/17/2018.
 */
public class AppController extends Application implements LifecycleObserver {

    public static final String LANGUAGE_ARABIC = "ar";
    public static final String LANGUAGE_ENGLISH = "en";
    @SuppressLint("StaticFieldLeak")
    public static AppController mInstance;
    public static String currentLanguage;
    private static boolean isMainActivity = false;
    public static boolean isVisible = false;
    private static boolean isMainActivityOnResume = false;
    public static final int APP_FORE_GROUNDED = 2;
    public static final int APP_BACK_GROUNDED = 0;
    public static final int APP_DESTROYED = -2;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        MultiDex.install(this);
        initFresco();
        registerActivityLifeCyclesCallbacks();
    }

    private void initFresco() {
        Fresco.initialize(this);
    }

    private void registerActivityLifeCyclesCallbacks() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onActivityStarted(Activity activity) {
                if (activity != null) {
                    isMainActivity = activity instanceof HomeActivity;
                    if (activity instanceof HomeActivity)
                        SharedPreferenceUtils.saveApplicationStatus(AppController.getInstance(), AppController.APP_FORE_GROUNDED);
                }

            }

            @Override
            public void onActivityResumed(Activity activity) {
                if (activity != null) {
                    isMainActivityOnResume = activity instanceof HomeActivity;
                    isVisible = true;
                }
            }

            @Override
            public void onActivityPaused(Activity activity) {
                if (activity != null) {
                    if (activity instanceof HomeActivity)
                        isMainActivityOnResume = false;
                }
            }

            @Override
            public void onActivityStopped(Activity activity) {
                if (activity != null) {
                    isVisible = false;
                    if (activity instanceof HomeActivity)
                        SharedPreferenceUtils.saveApplicationStatus(AppController.getInstance(), AppController.APP_BACK_GROUNDED);
                }

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
            }
        });
    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    public static boolean isMainActivityOnResume() {
        return isMainActivityOnResume;
    }

    public static boolean isMainActivityScreen() {
        return isMainActivity;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
        LocaleHelper.setLocale(base, LANGUAGE_ENGLISH);
    }

    public static String getLanguage() {
        return currentLanguage;
    }

    public static void setLanguage(String currentLang) {
        currentLanguage = currentLang;
    }

    public static String getLanguageCode() {
        return String.valueOf(getLanguage());
    }

    public static boolean isEnglish() {
        return getLanguage().equalsIgnoreCase(LANGUAGE_ENGLISH);
    }

    public static WebService getWebService() {
        return APIClient.getClient();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onAppBackgrounded() {
        SharedPreferenceUtils.saveApplicationStatus(AppController.getInstance(), AppController.APP_BACK_GROUNDED);
        Timber.tag("Lifecycle.Event").d("APP_BACK_GROUNDED");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onAppForegrounded() {
        SharedPreferenceUtils.saveApplicationStatus(AppController.getInstance(), AppController.APP_FORE_GROUNDED);
        Timber.tag("Lifecycle.Event").d("APP_FORE_GROUNDED");
    }

    public static String getApplicationStatus() {
        return SharedPreferenceUtils.getApplicationStatus(AppController.getInstance());
    }
}
