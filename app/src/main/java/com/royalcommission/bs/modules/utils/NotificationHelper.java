package com.royalcommission.bs.modules.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.royalcommission.bs.R;


/**
 * Created by Prashant on 8/12/2018.
 */
public class NotificationHelper {

    private static final String channelId = "com.royalcommission.bs.notification.CHANNEL_ID_FOREGROUND";

    public NotificationHelper() {
    }

    public static void showNotification(Context context, String body, Intent targetIntent) {
        if (context != null) {
            NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            int notificationId = CommonUtils.generateRandomID();
            int importance = 0;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                importance = NotificationManager.IMPORTANCE_HIGH;
            }
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                NotificationChannel mChannel = new NotificationChannel(
                        channelId, context.getString(R.string.app_name), importance);
                if (mNotificationManager != null) {
                    mNotificationManager.createNotificationChannel(mChannel);
                }
            }
            long when = System.currentTimeMillis();
            PendingIntent pendingIntent;
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, channelId)
                    .setSmallIcon(R.drawable.ic_notification)
                    //.setContentTitle(title)
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
}
