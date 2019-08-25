package com.royalcommission.bs.modules.utils;

import android.app.Activity;
import android.util.Log;


import com.royalcommission.bs.app.AppController;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Prashant on 8/8/2018.
 */
public class LogoutTimerUtil {

    private static Timer logoutTimer;
    private static Timer popupLogoutTimer;
    private static final int AUTO_LOGOUT_TIME_INTERVAL = 150 * 1000;
    private static final int AUTO_LOGOUT_POPUP_TIME_INTERVAL = AUTO_LOGOUT_TIME_INTERVAL;

    public static synchronized void startLogoutPopupTimer(Activity activity, final LogOutListener logOutListener) {
        try {
            if (activity != null && logOutListener != null) {
                activity.runOnUiThread(() -> {
                    clearTimer();
                    logoutTimer = new Timer();
                    logoutTimer.schedule(new TimerTask() {
                        public void run() {
                            clearTimer();
                            if (AppController.isMainActivityScreen()) {
                                logOutListener.doLogout();
                            }
                        }
                    }, AUTO_LOGOUT_TIME_INTERVAL);
                });
            }
        } catch (Exception e) {
            Log.d("StartLogoutPopupTimer: ", String.valueOf(e));
        }
    }

    private static synchronized void clearTimer() {
        if (logoutTimer != null) {
            logoutTimer.cancel();
            logoutTimer.purge();
            logoutTimer = null;
        }
    }

    public static synchronized void startPopupDismissTimer(final Activity activity, final LogOutListener logOutListener) {
        try {
            if (activity != null && logOutListener != null) {
                activity.runOnUiThread(() -> {
                    clearPopupTimer();
                    popupLogoutTimer = new Timer();
                    popupLogoutTimer.schedule(new TimerTask() {
                        public void run() {
                            clearPopupTimer();
                            if (AppController.isMainActivityScreen()) {
                                logOutListener.dismissPopUpThenLogout();
                            }
                        }
                    }, AUTO_LOGOUT_POPUP_TIME_INTERVAL);
                });
            }
        } catch (Exception e) {
            Log.d("StartLogoutPopupTimer: ", String.valueOf(e));
        }
    }

    private static synchronized void clearPopupTimer() {
        if (popupLogoutTimer != null) {
            popupLogoutTimer.cancel();
            popupLogoutTimer.purge();
            popupLogoutTimer = null;
        }
    }

    public interface LogOutListener {
        void doLogout();

        void dismissPopUpThenLogout();
    }
}
