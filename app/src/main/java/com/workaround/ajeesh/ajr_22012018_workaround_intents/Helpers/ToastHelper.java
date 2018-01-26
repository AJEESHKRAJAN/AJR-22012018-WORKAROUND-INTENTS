package com.workaround.ajeesh.ajr_22012018_workaround_intents.Helpers;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by ajesh on 26-01-2018.
 */

public class ToastHelper {
    public static void showMessageLocally(Context context, CharSequence message) {
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
        toast.show();
    }
}
