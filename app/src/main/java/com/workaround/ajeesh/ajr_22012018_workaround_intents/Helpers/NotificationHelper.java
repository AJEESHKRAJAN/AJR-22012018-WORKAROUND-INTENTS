package com.workaround.ajeesh.ajr_22012018_workaround_intents.Helpers;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;

/**
 * Created by ajesh on 24-01-2018.
 */

public class NotificationHelper {
    String logName = "WWI-HLPR-NTFCN";
    Context mContext;

    public NotificationHelper(Context context) {
        LogHelper.LogThreadId(logName, "The Notification Helper class is called from Context : " + context.toString());
        mContext = context;
    }

    public void createNotification(PendingIntent pendingIntent, int notificationIcon, String notificationTitleText,
                                   String notificationBodyText, int notificationId) {
        LogHelper.LogThreadId(logName, "The Notification builder received a pending intent : " + pendingIntent.toString());

        long notificationTimeStamp = System.currentTimeMillis();

        NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);

        Notification.Builder notificationBuilder = new Notification.Builder(mContext);

        notificationBuilder
                .setSmallIcon(notificationIcon)
                .setWhen(notificationTimeStamp)
                .setContentTitle(notificationTitleText)
                .setContentText(notificationBodyText)
                .setContentIntent(pendingIntent);

        Notification notificationForeground = notificationBuilder.getNotification();

        LogHelper.LogThreadId(logName, "The Notification builder: " + notificationBuilder.toString());
        if (notificationManager != null) {
            notificationManager.notify(notificationId, notificationForeground);
        }
    }
}
