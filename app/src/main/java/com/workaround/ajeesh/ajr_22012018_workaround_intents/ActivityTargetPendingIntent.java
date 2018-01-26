package com.workaround.ajeesh.ajr_22012018_workaround_intents;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.workaround.ajeesh.ajr_22012018_workaround_intents.Helpers.LogHelper;

import java.util.Set;

public class ActivityTargetPendingIntent extends AppCompatActivity {
    String logName = "WWI-TRGT-PEND-INTN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_target_pending_intent);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        LogHelper.LogThreadId(logName, "Target pending activity is created.");

        Intent trgtIntent = getIntent();
        TextView textViewTarget = findViewById(R.id.textViewIntentInfo);
        showIntentInfo(trgtIntent, textViewTarget);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());
    }

    private void showIntentInfo(Intent trgtIntent, TextView textViewTarget) {
        LogHelper.LogThreadId(logName, "Processing Intent info in target pending intents class.");

        StringBuilder infoBuilder = new StringBuilder();

        ComponentName componentName = trgtIntent.getComponent();
        if (componentName != null) {
            infoBuilder.append("Component Name = ");
            infoBuilder.append(componentName.toString());
            infoBuilder.append("\n");
        }

        String action = trgtIntent.getAction();
        infoBuilder.append("Action = ");
        infoBuilder.append(action != null ? action : "** NO Action **");
        infoBuilder.append("\n");

        Bundle extras = trgtIntent.getExtras();
        if (extras == null) {
            infoBuilder.append("** NO Extras **");
        } else {
            infoBuilder.append("** Extras **\n");
            Set<String> keySet = extras.keySet();
            for (String key : keySet) {
                String value = extras.get(key).toString();
                infoBuilder.append(key);
                infoBuilder.append(" = ");
                infoBuilder.append(value);
                infoBuilder.append("\n");
            }
        }

        textViewTarget.setText(infoBuilder.toString());
    }

}
