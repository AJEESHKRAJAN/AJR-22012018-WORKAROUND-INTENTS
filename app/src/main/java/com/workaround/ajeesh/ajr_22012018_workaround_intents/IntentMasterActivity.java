package com.workaround.ajeesh.ajr_22012018_workaround_intents;

import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.workaround.ajeesh.ajr_22012018_workaround_intents.Helpers.DataIntentHelper;
import com.workaround.ajeesh.ajr_22012018_workaround_intents.Helpers.LogHelper;
import com.workaround.ajeesh.ajr_22012018_workaround_intents.Helpers.NotificationHelper;
import com.workaround.ajeesh.ajr_22012018_workaround_intents.Services.InstantService;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class IntentMasterActivity extends AppCompatActivity {
    String logName = "WWI-MASTER-ACT";
    int notificationIdA = 1000;
    int notificationIdB = 1001;
    NotificationHelper notificationHelper = new NotificationHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_master);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Intent intent = getIntent();
        LogHelper.LogThreadId(logName, "Main Activity is running on : " + intent.toString());

        String createdTime =
                (new SimpleDateFormat("HH:mm:ss", Locale.US)).format(System.currentTimeMillis());

        TextView masterPageTextView = findViewById(R.id.masterPageTitleText);
        masterPageTextView.append("Activity Created time: " + createdTime);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_intent_master, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        boolean hanlded = true;
        int id = item.getItemId();


        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.menuShowOtherActivity:
                onClickActivityShowOther(item);
                break;
            case R.id.menuRunService:
                onClickServiceRun(item);
                break;
            case R.id.menuShowHelloWorld:
                onClickActivityShowHelloWorld(item);
                break;
            case R.id.menuShowPSHomePage:
                onClickActivityShowPSHomePage(item);
                break;
            case R.id.menuCreatePendingIntents:
                onClickCreatePendingIntents(item);
                break;
            case R.id.menuShowWallpaperControlPanel:
                showWallpaperControlPanel(item);
                break;
            case R.id.menuDataMatchingIntent:
                onClickDataMatchingIntent(item);
                break;
            case R.id.menuQuit:
                onClickMenuExit(item);
                break;
            default:
                hanlded = super.onOptionsItemSelected(item);
                break;
        }

        return hanlded;
    }


    private void onClickActivityShowPSHomePage(MenuItem item) {
        Intent intent = new Intent("android.intent.action.VIEW");
        Uri uri = Uri.parse("http://www.pluralsight-training.net");
        intent.setData(uri);
        startActivity(intent);
    }

    private void onClickActivityShowHelloWorld(MenuItem item) {
        Intent intent = new Intent(this, ActivityShowHelloWorld.class);
        startActivity(intent);
    }


    private void onClickActivityShowOther(MenuItem item) {
        Intent intent = new Intent(this, OtherActivity.class);
        startActivity(intent);
    }

    private void onClickServiceRun(MenuItem item) {
        //Intent serviceIntent = new Intent(this, InstantService.class);
        Intent serviceIntent = new Intent("com.workaround.ajeesh.ajr_22012018_workaround_intents.action.LOG_TIME");
        LogHelper.LogThreadId(logName, "Package Name:" + this.getPackageName());
        serviceIntent.setPackage(this.getPackageName());
        startService(serviceIntent);
    }

    private void onClickMenuExit(MenuItem item) {
        finish();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        LogHelper.LogThreadId(logName, "A new intent created OnNewIntent: " + intent.toString());
    }


    private void onClickCreatePendingIntents(MenuItem item) {
        PendingIntent pA = createPa();
        PendingIntent pB = createPb();

        boolean isEqual = pA.equals(pB);
        LogHelper.LogThreadId(logName, "PendingIntents are equal:" + (isEqual ? "YES" : "NO"));

        notificationHelper.createNotification(pA, R.drawable.ic_new_ps_logo,
                "Pending Intent A", "This is for Pending Intent A", notificationIdA);
        notificationHelper.createNotification(pB, R.drawable.ic_new_ps_logo,
                "Pending Intent B", "This is for Pending Intent B", notificationIdB);
    }

    private PendingIntent createPa() {
        //Intent intent = new Intent(this, ActivityTargetPendingIntent.class);
        Intent intent = new Intent("com.workaround.ajeesh.action.SHOW_TEST_ACTIVITY");
        return PendingIntent.getActivity(this, 0, intent, 0);
    }

    private PendingIntent createPb() {
        Intent intent = new Intent("com.workaround.ajeesh.action.SHOW_TEST_ACTIVITY");
        intent.putExtra("Trainer", "Pluralsight");
        intent.putExtra("Website", "www.pluralsight-training.net");

        return PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }


    public void showWallpaperControlPanel(MenuItem item) {
        try {
            ComponentName wallpaperComponentName = getWallPaperServiceComponentName();

            ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
            PendingIntent wallpaperControlPanelPendingIntent =
                    am.getRunningServiceControlPanel(wallpaperComponentName);

            try {
                wallpaperControlPanelPendingIntent.send();
            } catch (PendingIntent.CanceledException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            LogHelper.LogThreadId(logName, "Activity Manager: called exception as " + e.getMessage());
        }

    }

    @SuppressWarnings("deprecation")
    private ComponentName getWallPaperServiceComponentName() {
        final String wallpaperServiceClassName = "com.android.internal.service.wallpaper.ImageWallpaper";
        ComponentName wallpaperService = null;
        int index = 1;
        try {
            ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
            LogHelper.LogThreadId(logName, "Activity Manager: " + am.toString());

            @SuppressWarnings("deprecation")
            List<ActivityManager.RunningServiceInfo> services = am.getRunningServices(Integer.MAX_VALUE);
            LogHelper.LogThreadId(logName, "Activity Manager: Running Services Count" + services.size());


            for (ActivityManager.RunningServiceInfo theService : services) {
                LogHelper.LogThreadId(logName,
                        String.format(Locale.US, "Activity Manager: The Running Services of %d service is named as : %s ",
                                index, theService.service));
                if (wallpaperServiceClassName.equalsIgnoreCase(theService.service.getClassName())) {
                    wallpaperService = theService.service;
                    break;
                }
            }


        } catch (Exception e) {
            LogHelper.LogThreadId(logName, "Activity Manager: Exception message " + e.getMessage());
        }
        return wallpaperService;
    }

    private void onClickDataMatchingIntent(MenuItem item) {
        String getSupportedActivityForData = "";
        TextView textView = findViewById(R.id.textViewDisplay);

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://stackoverflow.com/questions/abc789456138/"));

        getSupportedActivityForData = new DataIntentHelper(this).getMatchingActivityForGivenData(intent);

        textView.setText(getSupportedActivityForData);
    }
}
