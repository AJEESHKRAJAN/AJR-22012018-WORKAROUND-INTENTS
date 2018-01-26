package com.workaround.ajeesh.ajr_22012018_workaround_intents;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.test.mock.MockPackageManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.workaround.ajeesh.ajr_22012018_workaround_intents.BroadcastReceivers.BatteryStatusReceiver;
import com.workaround.ajeesh.ajr_22012018_workaround_intents.Helpers.DataIntentHelper;
import com.workaround.ajeesh.ajr_22012018_workaround_intents.Helpers.LogHelper;
import com.workaround.ajeesh.ajr_22012018_workaround_intents.Helpers.NotificationHelper;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class IntentMasterActivity extends AppCompatActivity {
    String logName = "WWI-MASTER-ACT";
    int notificationIdA = 1000;
    int notificationIdB = 1001;
    final int cameraRequest = 1002;
    final int contactsRequest = 1003;
    NotificationHelper notificationHelper = new NotificationHelper(this);
    private static final int REQUEST_CODE_PERMISSION = 2;
    List<String> mPermission = new ArrayList<String>();
    File createdFileName;
    String[] permissionList = new String[]{
            "android.permission.CAMERA",
            "android.hardware.camera",
            "android.hardware.camera.autofocus",
            "android.hardware.location.gps",
            "android.permission.ACCESS_COARSE_LOCATION",
            "android.permission.ACCESS_FINE_LOCATION",
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE",
            "android.permission.INTERNET",
            "android.permission.ACCESS_NETWORK_STATE",
            "android.permission.GET_TASKS",
            "android.permission.PACKAGE_USAGE_STATS",
            "android.permission.READ_CONTACTS",
            "android.permission.ACCESS_LOCATION_EXTRA_COMMANDS",
            "android.permission.REQUEST_INSTALL_PACKAGES"
    };

    String[] deniedPermissionList = new String[]{};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_master);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        boolean isReadable = isExternalStorageReadable();
        LogHelper.LogThreadId(logName, "External Storage IS READABLE: " + isReadable);
        boolean isWritable = isExternalStorageWritable();
        LogHelper.LogThreadId(logName, "External Storage IS WRITABLE: " + isWritable);
        requestPermission();

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
            case R.id.menuMonitorBatteryStatus:
                onClickMonitorBatteryStatus(item);
                break;
            case R.id.menuShowActivityIn5Seconds:
                onClickShowActivityIn5Seconds(item);
                break;
            case R.id.menuOpenBrowserContent:
                onClickOpenBrowserContent(item);
                break;
            case R.id.menuOpenPhoneDial:
                onClickOpenPhoneDial(item);
                break;
            case R.id.menuTakePicture:
                onClickTakePicture(item);
                break;
            case R.id.menuFindContact:
                onClickFindContact(item);
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

    private void onClickMonitorBatteryStatus(MenuItem item) {
        BatteryStatusReceiver batteryStatusReceiver = new BatteryStatusReceiver();
        registerReceiver(batteryStatusReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }

    private void onClickShowActivityIn5Seconds(MenuItem item) {
        Intent intent = new Intent("com.workaround.ajeesh.action.SHOW_TEST_ACTIVITY");
        intent.putExtra("WierdValue", "Exclusive!!!!");

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        long alarmTime = SystemClock.elapsedRealtime() + 5000;
        LogHelper.LogThreadId(logName, "Alarm Time: " + alarmTime);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        if (alarmManager != null) {
            LogHelper.LogThreadId(logName, "Alarm Manager: " + alarmManager.toString());
            alarmManager.set(AlarmManager.ELAPSED_REALTIME, alarmTime, pendingIntent);
        }
    }

    private void onClickOpenBrowserContent(MenuItem item) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/"));
        startActivity(intent);
    }

    private void onClickOpenPhoneDial(MenuItem item) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:9790793380"));
        startActivity(intent);
    }

    private void onClickTakePicture(MenuItem item) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        boolean isHavingPermission = checkAppPermission();

        try {
            File imagePath = getAlbumStorageDir("default_image.jpg");
            // File imagePath = new File(getFilesDir(), "images");
            assureThatDirectoryExist(imagePath);
            LogHelper.LogThreadId(logName, "Image path : " + imagePath);

            //File newFile = new File(imagePath, "default_image.jpg");
            createdFileName = imagePath;
            Uri contentUri = FileProvider.getUriForFile(this,
                    "com.workaround.ajeesh.ajr_22012018_workaround_intents.fileprovider", createdFileName);

            LogHelper.LogThreadId(logName, "Parcelable File name : " + contentUri);

            intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            } else {
                List<ResolveInfo> resInfoList = getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
                for (ResolveInfo resolveInfo : resInfoList) {
                    String packageName = resolveInfo.activityInfo.packageName;
                    grantUriPermission(packageName, contentUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                }
            }


            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            startActivityForResult(intent, cameraRequest);
        } catch (Exception e) {
            LogHelper.LogThreadId(logName, "Exception : " + e.getMessage());
        }

    }

    private void onClickFindContact(MenuItem item) {
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(intent, contactsRequest);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case cameraRequest:
                    LogHelper.LogThreadId(logName,
                            "Camera request is processed." + data);
                    handleCameraRequest(requestCode, resultCode, data);
                    break;
                case contactsRequest:
                    LogHelper.LogThreadId(logName,
                            "Contacts request is processed.");
                    handleContactRequest(requestCode, resultCode, data);
                    break;
                default:
                    LogHelper.LogThreadId(logName,
                            String.format(Locale.US, "Unrecognized request code: %d", requestCode));
            }
        } else {
            LogHelper.LogThreadId(logName,
                    String.format(Locale.US, "Request failed - resultCode: %d, requestCode:%d", resultCode, requestCode));
        }
    }


    protected void handleCameraRequest(int requestCode, int resultCode, Intent data) {
        Bitmap image;

        if (data != null) {
            image = data.getParcelableExtra("data");
            LogHelper.LogThreadId(logName, "Image is." + image);
        } else {
            File imageFile = createdFileName;
            String imageFileName = imageFile.getAbsolutePath();
            LogHelper.LogThreadId(logName, "Image file name is." + imageFileName);
            image = BitmapFactory.decodeFile(imageFileName);
        }
        int height = image.getHeight();
        int width = image.getWidth();
        LogHelper.LogThreadId(logName, String.format(Locale.US, "Image size .. height:%d  width:%d", height, width));

    }

    private File getImageFile() {
        //File targetDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File targetDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        LogHelper.LogThreadId(logName, "Image file target directory is." + targetDir);
        File imageFileReturned = null;
        if (assureThatDirectoryExist(targetDir)) {
            imageFileReturned = new File(targetDir, "MyPicture.jpg");
            LogHelper.LogThreadId(logName, "Image file returned is." + imageFileReturned);
        }
        return imageFileReturned;
    }

    private boolean assureThatDirectoryExist(File directory) {
        boolean hasCreated;
        hasCreated = directory.exists() || directory.mkdirs();
        return hasCreated;
    }


    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        LogHelper.LogThreadId(logName, "External Storage State: " + state);
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    public File getAlbumStorageDir(String albumName) {
        // Get the directory for the user's public pictures directory.
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), albumName);
        if (!file.mkdirs()) {
            LogHelper.LogThreadId(logName, "Directory not created");
        }
        return file;
    }
   /* @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "Cannot use Location services. Hence Monitoring doesn't work.", Toast.LENGTH_LONG).show();
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }*/

    public boolean checkAppPermission() {
        boolean hasPermission;
        List<String> denial = new ArrayList<>();
        for (String permissionName : permissionList) {
            int res = this.checkCallingOrSelfPermission(permissionName);
            hasPermission = res == PackageManager.PERMISSION_GRANTED;
            if (!hasPermission) {
                denial.add(permissionName);
            }
        }

        deniedPermissionList = denial.toArray(new String[0]);

        return deniedPermissionList.length <= 0;
    }


    public void requestPermission() {
        try {

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                    != MockPackageManager.PERMISSION_GRANTED)
                mPermission.add(Manifest.permission.CAMERA);

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != MockPackageManager.PERMISSION_GRANTED)
                mPermission.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    != MockPackageManager.PERMISSION_GRANTED)
                mPermission.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            for (String s : mPermission) {
                LogHelper.LogThreadId(logName, "Permission List: " + s);
            }

            if (mPermission.size() > 0)

            {
                String[] array = mPermission.toArray(new String[mPermission.size()]);
                LogHelper.LogThreadId(logName, "Manifest Request Permission for : " + mPermission.toString());
                ActivityCompat.requestPermissions(this, array, REQUEST_CODE_PERMISSION);

                // If any permission aboe not allowed by user, this condition will execute every tim, else your else part will work
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        LogHelper.LogThreadId(logName, "" + requestCode);
        if (requestCode == REQUEST_CODE_PERMISSION) {
            if (grantResults.length == mPermission.size()) {
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] == MockPackageManager.PERMISSION_GRANTED) {
                        //don't do anything....
                    } else {
                        mPermission = new ArrayList<String>();
                        requestPermission();
                        break;
                    }
                }

            } else {
                Toast.makeText(this, "permition not granted", Toast.LENGTH_LONG).show();
                mPermission = new ArrayList<String>();
                requestPermission();
            }
        }

    }
    private void handleContactRequest(int requestCode, int resultCode, Intent data) {
        String displayName =
                data.getStringExtra(Intent.EXTRA_SHORTCUT_NAME);
        Uri contactUri = data.getData();

        String email = getContactEmail(contactUri);
        String phoneNumber = getContactPhoneNumber(contactUri);

       LogHelper.LogThreadId(logName, "Selected Contact primary details " + displayName + ", email=" + email + ", phone=" + phoneNumber);

    }

    private String getContactEmail(Uri contactUri){
        return getContactCommonDataItem(contactUri, ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                ContactsContract.CommonDataKinds.Email.CONTACT_ID, ContactsContract.CommonDataKinds.Email.DATA);
    }

    private String getContactPhoneNumber(Uri contactUri){
        return getContactCommonDataItem(contactUri, ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID, ContactsContract.CommonDataKinds.Phone.DATA);
    }

    // A simple helper method to retrieve a single data value for a contact.
    // This is a very simple helper method intended for demonstration purposes only. It assumes that the returned
    //  data value is a string and if there are multiple rows returned from the underlying datastore on the value
    //  from the first row is retrieved.
    private String getContactCommonDataItem(Uri contactUri, Uri dataStoreUri, String idColumnName, String resultColumnName){
        // Get the identifier portion of the contact's URI
        String contactId = contactUri.getLastPathSegment();
        String resultValue = null;

        // Open a query into the specified data store and apply a condition
        //  that the id column's value must match the identifier portion of
        //  the contact's URL
        Cursor cursor  = managedQuery(dataStoreUri, null, idColumnName + "=?",
                new String[] { contactId }, null);

        // As long as at least one row is returned, moved to the first row
        //  and read the value of the result column
        if(cursor.moveToFirst()){
            int colIdx = cursor.getColumnIndex(resultColumnName);
            resultValue = cursor.getString(colIdx);
        }

        return resultValue == null ? "<null>" : resultValue;
    }
}
