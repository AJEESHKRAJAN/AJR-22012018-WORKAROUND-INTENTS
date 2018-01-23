package com.workaround.ajeesh.ajr_22012018_workaround_intents.Services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import com.workaround.ajeesh.ajr_22012018_workaround_intents.Helpers.LogHelper;
import com.workaround.ajeesh.ajr_22012018_workaround_intents.IntentMasterActivity;
import com.workaround.ajeesh.ajr_22012018_workaround_intents.R;

public class InstantService extends Service {
    int hitCount;
    String logName = "WWI-SVC-INSTNT";
    final int _notificationId = 1;

    public InstantService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        LogHelper.LogThreadId(logName, "Instant Service is created");
        hitCount = 0;
        startInForeground();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        hitCount++;
        LogHelper.LogThreadId(logName, "Service hit count: " + hitCount);
        return START_NOT_STICKY;
    }

    void startInForeground() {
        // Set basic notification information
        int notificationIcon = R.drawable.ic_new_ps_logo;
        String notificationTickerText = "Launching pluralsight service";
        long notificationTimeStamp = System.currentTimeMillis();

        String notificationTitleText = "Pluralsight service";
        String notificationBodyText = "Does non-UI processing";

        try {
            Intent notificationActivityIntent = createNotificationActivityIntent();
            PendingIntent startMyActivityPendingIntent = PendingIntent.getActivity(this, 0, notificationActivityIntent, 0);


            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            Notification.Builder notificationBuilder = new Notification.Builder(this);
            notificationBuilder
                    .setContentInfo(notificationTickerText)
                    .setSmallIcon(notificationIcon)
                    .setWhen(notificationTimeStamp)
                    .setContentTitle(notificationTitleText)
                    .setContentText(notificationBodyText)
                    .setContentIntent(startMyActivityPendingIntent);

            Notification notificationForegrnd = notificationBuilder.getNotification();

            LogHelper.LogThreadId(logName, "The Notification builder: " + notificationBuilder.toString());
            if (notificationManager != null) {
                notificationManager.notify(_notificationId, notificationForegrnd);
            }


        } catch (Exception ex) {
            LogHelper.LogThreadId(logName, ex.getMessage());
        }
    }

    private Intent createNotificationActivityIntent() {
        LogHelper.LogThreadId(logName, "The Master activity is called by pending intent");
        Intent masterActivity = new Intent(this, IntentMasterActivity.class);
        masterActivity.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        return masterActivity;
    }
}
