package com.workaround.ajeesh.ajr_22012018_workaround_intents;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.workaround.ajeesh.ajr_22012018_workaround_intents.Helpers.LogHelper;
import com.workaround.ajeesh.ajr_22012018_workaround_intents.Services.InstantService;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class IntentMasterActivity extends AppCompatActivity {
    String logName = "WWI-MASTER-ACT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_master);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Intent intent = getIntent();
        LogHelper.LogThreadId(logName, "Main Activity is running on : " + intent.toString());

        String createdTime =
                (new SimpleDateFormat("HH:mm:ss", Locale.US)).format(System.currentTimeMillis());

        TextView masterPageTextView = findViewById(R.id.masterPageTitleText);
        masterPageTextView.append("Activity Created time: " + createdTime);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
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
                //onClickActivityShowOther(item);
                break;
            case R.id.menuShowPSHomePage:
                //onClickActivityShowOther(item);
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


    private void onClickActivityShowOther(MenuItem item) {
        Intent intent = new Intent(this, OtherActivity.class);
        startActivity(intent);
    }

    private void onClickServiceRun(MenuItem item) {
        Intent serviceIntent = new Intent(this, InstantService.class);
        startService(serviceIntent);
    }

    private void onClickMenuExit(MenuItem item) {
        finish();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        LogHelper.LogThreadId(logName, "A new intent created OnNewIntent: " + intent.toString());
    }
}
