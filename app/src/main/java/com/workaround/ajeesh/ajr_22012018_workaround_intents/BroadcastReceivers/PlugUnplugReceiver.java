package com.workaround.ajeesh.ajr_22012018_workaround_intents.BroadcastReceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.workaround.ajeesh.ajr_22012018_workaround_intents.Helpers.LogHelper;
import com.workaround.ajeesh.ajr_22012018_workaround_intents.Helpers.ToastHelper;

public class PlugUnplugReceiver extends BroadcastReceiver {
    String logName = "WWI-PWR-RCVR";

    @Override
    public void onReceive(Context context, Intent intent) {
        LogHelper.LogThreadId(logName, "Broadcast Receiver for Plug/Unplug Receiver has been initiated from the context "
                + context.getPackageName());
        String action = intent.getAction();
        String makeTextToDisplay = "";
        if (action != null) {
            if (action.equals(Intent.ACTION_POWER_CONNECTED)) {
                makeTextToDisplay = "Power is connected.";
            } else if (action.equals(Intent.ACTION_POWER_DISCONNECTED)) {
                makeTextToDisplay = "Power is disconnected";
            }
        }
        ToastHelper.showMessageLocally(context, makeTextToDisplay);
    }
}
