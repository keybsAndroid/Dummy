package com.iraqi.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.iraqi.app.AppController;
import com.iraqi.modules.sdk.SDKManager;
import com.iraqi.modules.utils.SharedPreferenceUtils;

import timber.log.Timber;

public class LifecycleService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        SharedPreferenceUtils.saveApplicationStatus(AppController.getInstance(), AppController.APP_DESTROYED);
        if (SDKManager.mServiceBound)
            SDKManager.getInstance().unBindService(AppController.getInstance());
        stopSelf();
        Timber.tag("Lifecycle.Event").e("APP_DESTROYED");
    }
}
