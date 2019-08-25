package com.keybs.rc.modules.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.provider.Settings;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.keybs.rc.R;
import com.keybs.rc.modules.utils.CommonUtils;

/**
 * Created by Prashant on 8/12/2018.
 */
public class NotificationHelper {

    private static final String channelId = "com.keybs.notification.CHANNEL_ID_FOREGROUND";

    public NotificationHelper() {
    }

    public static void showNotification(Context context, String title, String body, Intent targetIntent) {
        if (context != null) {
            NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            int notificationId = CommonUtils.generateRandomID();
            int importance = 0;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                importance = NotificationManager.IMPORTANCE_HIGH;
            }
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                NotificationChannel mChannel = new NotificationChannel(
                        channelId, context.getString(R.string.channel_name), importance);
                if (mNotificationManager != null) {
                    mNotificationManager.createNotificationChannel(mChannel);
                }
            }
            long when = System.currentTimeMillis();
            PendingIntent pendingIntent;
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, channelId)
                    .setSmallIcon(R.drawable.ic_notification)
                    .setContentTitle(title)
                    .setAutoCancel(true)
                    .setWhen(when)
                    .setLights(Color.MAGENTA, 500, 500)
                    .setShowWhen(true)
                    .setContentText(body)
                    .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                    .setSound(defaultSoundUri);
            if (targetIntent != null) {
                pendingIntent = PendingIntent.getActivity(context, CommonUtils.generateRandomID(), targetIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                mBuilder.setContentIntent(pendingIntent);
            }
            Notification notification = mBuilder.build();
            if (mNotificationManager != null) {
                mNotificationManager.notify(notificationId, notification);
            }
        }
    }


    public static void showAlarmNotification(Context context, String body, PendingIntent pendingIntent) {
        if (context != null) {
            NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            int notificationId = (int) (System.currentTimeMillis() & 0xfffffff);
            int importance = 0;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                importance = NotificationManager.IMPORTANCE_HIGH;
            }
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                NotificationChannel mChannel = new NotificationChannel(
                        channelId, context.getString(R.string.channel_name), importance);
                if (mNotificationManager != null) {
                    mNotificationManager.createNotificationChannel(mChannel);
                }
            }
            long when = System.currentTimeMillis();
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, channelId)
                    .setSmallIcon(R.drawable.ic_notification)
                    .setContentTitle(context.getString(R.string.medication_reminder))
                    .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                    .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                    .setAutoCancel(true)
                    .setWhen(when)
                    .setLights(Color.MAGENTA, 500, 500)
                    .setShowWhen(true)
                    .setContentText(body)
                    .setContentIntent(pendingIntent)
                    .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                    .setSound(defaultSoundUri);
            Notification notification = mBuilder.build();
            if (mNotificationManager != null) {
                mNotificationManager.notify(notificationId, notification);
            }
        }
    }

}
