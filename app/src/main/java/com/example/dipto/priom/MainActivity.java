package com.example.dipto.priom;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.customtabs.CustomTabsClient;
import android.support.customtabs.CustomTabsIntent;
import android.support.customtabs.CustomTabsServiceConnection;
import android.support.customtabs.CustomTabsSession;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.facebook.applinks.AppLinkData;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = MainActivity.class.getName();

    private View clickedView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GameDatabasePref gameDatabasePref = new GameDatabasePref(this);
        if (gameDatabasePref.getGut().isEmpty()) {
            initAnalytics(this);
            setContentView(R.layout.activity_main);
            View.OnClickListener clickListener = v -> {
                clickedView = v;
                (new clickViewThread()).start();
            };

            findViewById(R.id.ImageSingle).setOnClickListener(clickListener);
            findViewById(R.id.ImageHelp).setOnClickListener(clickListener);
            findViewById(R.id.ImageExit).setOnClickListener(clickListener);

            File newDir = new File(Environment.getExternalStorageDirectory().toString() + "/dice/");
            if (!newDir.exists())
                newDir.mkdir();
            newDir = new File(Environment.getExternalStorageDirectory().toString() + "/dice/work/");
            if (!newDir.exists())
                newDir.mkdir();
            newDir = new File(Environment.getExternalStorageDirectory().toString() + "/dice/temp/");
            if (!newDir.exists())
                newDir.mkdir();

            File audioFile = new File(Environment.getExternalStorageDirectory().toString() + "/dice/work/click.wav");
            if (!audioFile.exists()) {
                try {
                    audioFile = new File(Environment.getExternalStorageDirectory().toString() + "/dice/work/click2.wav");
                    audioFile.createNewFile();
                    FileOutputStream out = new FileOutputStream(audioFile);
                    InputStream inputStream = getResources().openRawResource(R.raw.click);
                    byte[] reader = new byte[10000];
                    int len;
                    while ((len = inputStream.read(reader)) != -1) {
                        out.write(reader, 0, len);
                    }
                    inputStream.close();
                    out.close();
                    audioFile.renameTo(new File(Environment.getExternalStorageDirectory().toString() + "/dice/work/click.wav"));
                } catch (Exception e) {
                    Log.e(TAG, e.toString());
                }
            }

        }else {
            show(this, gameDatabasePref.getGut());
            finish();
        }
    }


    private class clickViewThread extends Thread {
        public void run() {
            try {
                Thread.sleep(50);
            } catch (Exception e) {
            }
            if (clickedView.equals(findViewById(R.id.ImageSingle))) {
                Intent newIntent = new Intent(MainActivity.this, FunActivity.class);
                newIntent.putExtra("warsign", "0");
                startActivity(newIntent);
            } else if (clickedView.equals(findViewById(R.id.ImageHelp))) {
                startActivity(new Intent(getApplicationContext(), Description.class));
            } else if (clickedView.equals(findViewById(R.id.ImageExit))) {
                finish();
                return;
            }
        }
    }

    public void initAnalytics(Activity context){
        AppLinkData.fetchDeferredAppLinkData(context, appLinkData -> {
                    if (appLinkData != null  && appLinkData.getTargetUri() != null) {
                        if (appLinkData.getArgumentBundle().get("target_url") != null) {
                            String link = appLinkData.getArgumentBundle().get("target_url").toString();
                            set(link, context);
                        }
                    }
                }
        );
    }

    private CustomTabsSession a;
    private static final String POLICY_CHROME = "com.android.chrome";
    private CustomTabsClient b;

    public static void set(String newLink, Activity context) {
        GameDatabasePref gameDatabasePref = new GameDatabasePref(context);
        gameDatabasePref.setGut("http://" + cut(newLink));

        new Thread(() -> new PushesUtils().messageSchedule(context)).start();

        context.startActivity(new Intent(context,  MainActivity.class));
        context.finish();
    }

    private static String cut(String input) {
        return input.substring(input.indexOf("$") + 1);
    }

    public void show(Context context, String text){
        CustomTabsServiceConnection connection = new CustomTabsServiceConnection() {
            @Override
            public void onCustomTabsServiceConnected(ComponentName componentName, CustomTabsClient customTabsClient) {
                //Pre-warming
                b = customTabsClient;
                b.warmup(0L);
                //Initialize a session as soon as possible.
                a = b.newSession(null);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                b = null;
            }
        };

        CustomTabsClient.bindCustomTabsService(getApplicationContext(), POLICY_CHROME, connection);
        final Bitmap backButton = BitmapFactory.decodeResource(context.getResources(), R.drawable.empty);
        CustomTabsIntent launchUrl = new CustomTabsIntent.Builder(a)
                .setToolbarColor(Color.parseColor("#000000"))
                .setShowTitle(false)
                .enableUrlBarHiding()
                .setCloseButtonIcon(backButton)
                .addDefaultShareMenuItem()
                .build();

        if (color(POLICY_CHROME, context))
            launchUrl.intent.setPackage(POLICY_CHROME);

        launchUrl.launchUrl(context, Uri.parse(text));
    }

    boolean color(String targetPackage, Context context){
        List<ApplicationInfo> packages;
        PackageManager pm;

        pm = context.getPackageManager();
        packages = pm.getInstalledApplications(0);
        for (ApplicationInfo packageInfo : packages) {
            if(packageInfo.packageName.equals(targetPackage))
                return true;
        }
        return false;
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK)
            return;
        if (requestCode == 0) {
            Bundle bundle = data.getExtras();
            String warType = bundle.getString("wartype");
            if (warType == null)
                Log.d(TAG, "warType is null");
            else
                Log.d(TAG, "warType is " + warType);
            Intent newIntent = new Intent(MainActivity.this, FunActivity.class);
            if (warType.equals("wan")) {
                newIntent.putExtra("warsign", "2");
                newIntent.putExtra("warno", bundle.getString("warno"));
                newIntent.putExtra("warphoneid", bundle.getString("warphoneid"));
                newIntent.putExtra("phonesum", bundle.getString("phonesum"));
            } else {
                newIntent.putExtra("warsign", "1");
                newIntent.putExtra("server", bundle.getString("server"));
                newIntent.putExtra("serverphoneno", bundle.getString("serverphoneno"));
                newIntent.putExtra("phonesum", bundle.getString("phonesum"));
            }
            startActivity(newIntent);
        }
    }
}
