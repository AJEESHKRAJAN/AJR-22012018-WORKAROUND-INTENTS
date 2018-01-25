package com.workaround.ajeesh.ajr_22012018_workaround_intents.Helpers;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

/**
 * Created by ajesh on 25-01-2018.
 */

public class DataIntentHelper {

    private Context mContext;
    private String logName = "WWI-DATA-INTNT-HLPR";

    public DataIntentHelper(Context context) {
        LogHelper.LogThreadId(logName, "Data Intent Helper processed with the context of: " + context.getPackageName());
        mContext = context;
    }

    public String getMatchingActivityForGivenData(Intent intent) {
        String returnText;
        Uri intentData = intent.getData();
        String intentDataString = intentData == null ? "Intent contains no data" : intentData.toString();

        String names = "No matches found";
        List<String> activityNames = getMatchingActivityNames(intent);

        if (activityNames.size() > 0) {
            StringBuilder builder = new StringBuilder();
            for (String name : activityNames) {
                builder.append(name);
                builder.append("\n");
            }
            names = builder.toString();
        }
        returnText = "****Intent Data****\n" + intentDataString + "\n\n****Matching Activities****\n" + names;
        return returnText;
    }


    private List<String> getMatchingActivityNames(Intent intent) {
        List<String> activityNames = new LinkedList<>();
        int index = 1;
        PackageManager pm = mContext.getPackageManager();
        LogHelper.LogThreadId(logName, "Data Intent Helper : Package Manager : " + pm.toString());

        List<ResolveInfo> resolveInfoList =
                pm.queryIntentActivities(intent, (PackageManager.MATCH_DEFAULT_ONLY | PackageManager.GET_RESOLVED_FILTER));
        LogHelper.LogThreadId(logName, "Data Intent Helper : Matching Intent Activities List count: " + resolveInfoList.size());

        for (ResolveInfo resolveInfo : resolveInfoList) {
            LogHelper.LogThreadId(logName,
                    String.format(Locale.US, "Data Intent Helper : For the Index list of activity no %d is named as :%s ",
                            index, resolveInfo.activityInfo.name));
            activityNames.add(resolveInfo.activityInfo.name);
            index++;
        }
        return activityNames;
    }
}
