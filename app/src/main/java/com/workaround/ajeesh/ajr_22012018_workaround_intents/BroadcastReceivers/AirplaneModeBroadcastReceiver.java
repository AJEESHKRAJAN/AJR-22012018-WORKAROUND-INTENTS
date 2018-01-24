package com.workaround.ajeesh.ajr_22012018_workaround_intents.BroadcastReceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.workaround.ajeesh.ajr_22012018_workaround_intents.Helpers.LogHelper;

public class AirplaneModeBroadcastReceiver extends BroadcastReceiver {
    String logName = "WWI-BRDCST-AIRPLN";

    @Override
    public void onReceive(Context context, Intent intent) {
        boolean airplaneMode = intent.getBooleanExtra("state", false);

        LogHelper.LogThreadId(logName, "Airplane mode is now : " + (airplaneMode ? "on" : "off"));
    }
}
