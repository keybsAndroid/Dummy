package com.royalcommission.bs.modules.services;

import android.app.job.JobParameters;
import android.app.job.JobService;

import com.royalcommission.bs.app.AppController;
import com.royalcommission.bs.modules.api.model.BaseResponse;
import com.royalcommission.bs.modules.api.model.NotificationMessages;
import com.royalcommission.bs.modules.api.model.NotificationMessagesResponse;
import com.royalcommission.bs.modules.event.MessageEvent;
import com.royalcommission.bs.modules.utils.CommonUtils;
import com.royalcommission.bs.modules.utils.DateUtils;
import com.royalcommission.bs.modules.utils.NotificationHelper;
import com.royalcommission.bs.modules.utils.SharedPreferenceUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.Collections;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class FetchMessageAndNotificationJob extends JobService {

    public static long JOB_PERIODIC_TIME_INTERVAL = 3600000L;

    @Override
    public boolean onStartJob(JobParameters params) {
        Thread thread = new Thread(() -> {
            String patientID = SharedPreferenceUtils.getPatientID(AppController.getInstance());
            String hospitalID = SharedPreferenceUtils.getHospitalID(AppController.getInstance());
            if (CommonUtils.isValidString(patientID) && CommonUtils.isValidString(hospitalID)) {
                AppController.getWebService().getMessageNotifications(patientID, hospitalID)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new DisposableObserver<Object>() {
                            @Override
                            public void onNext(Object object) {
                                NotificationMessagesResponse response = (NotificationMessagesResponse) object;
                                BaseResponse baseResponse = response.getBaseResponse();
                                if (baseResponse != null && baseResponse.getResponseCode() == 1) {
                                    SharedPreferenceUtils.scheduleJobForFirstTime(AppController.getInstance(), true);
                                    List<NotificationMessages> notificationMessagesList = response.getNotificationMessagesList();
                                    if (notificationMessagesList != null && !notificationMessagesList.isEmpty()) {
                                        Collections.sort(notificationMessagesList, new NotificationMessages().new CompareByDate());
                                        Collections.reverse(notificationMessagesList);
                                        List<NotificationMessages> filteredList = DateUtils.getNotificationsListFilteredByToday(notificationMessagesList);
                                        if (!filteredList.isEmpty()) {
                                            if (AppController.getApplicationStatus().equalsIgnoreCase("APP_FORE_GROUNDED")) {
                                                EventBus.getDefault().post(new MessageEvent(AppController.APP_FORE_GROUNDED, filteredList));
                                            } else if (AppController.getApplicationStatus().equalsIgnoreCase("APP_BACK_GROUNDED")) {
                                                EventBus.getDefault().post(new MessageEvent(AppController.APP_BACK_GROUNDED, filteredList));
                                            } else {
                                                showNotifications(filteredList);
                                            }
                                        }
                                    }
                                }
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        });
            }
        });
        thread.start();
        return false;
    }


    private void showNotifications(List<NotificationMessages> notificationMessages) {
        if (notificationMessages != null && !notificationMessages.isEmpty()) {
            for (NotificationMessages notification : notificationMessages) {
                NotificationHelper.showNotification(this, notification.getSms(), null);
            }
        }
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }
}
