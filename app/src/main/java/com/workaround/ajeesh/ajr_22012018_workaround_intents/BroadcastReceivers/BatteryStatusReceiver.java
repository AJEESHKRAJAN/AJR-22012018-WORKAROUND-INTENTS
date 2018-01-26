package com.workaround.ajeesh.ajr_22012018_workaround_intents.BroadcastReceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;

import com.workaround.ajeesh.ajr_22012018_workaround_intents.Helpers.BatteryStatusHelper;
import com.workaround.ajeesh.ajr_22012018_workaround_intents.Helpers.LogHelper;
import com.workaround.ajeesh.ajr_22012018_workaround_intents.Helpers.ToastHelper;

public class BatteryStatusReceiver extends BroadcastReceiver {
    String logName = "WWI-BTRY-RCVR";

    @Override
    public void onReceive(Context context, Intent intent) {
        LogHelper.LogThreadId(logName, "Battery status receiver has been initiated.");
        BatteryStatusHelper batteryStatusHelper = new BatteryStatusHelper(intent);
        ToastHelper.showMessageLocally(context, batteryStatusHelper.toString());
        LogHelper.LogThreadId(logName, "Battery status information : " + batteryStatusHelper.toString());
    }
}
