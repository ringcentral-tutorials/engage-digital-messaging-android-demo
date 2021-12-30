package com.dimelo.sampleapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;



import com.dimelo.dimelosdk.main.Chat;
import com.dimelo.dimelosdk.main.Dimelo;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;
import com.huawei.hms.api.HuaweiApiAvailability;
import com.rc.rcmapssdk.RcMaps;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;

public class RcConfig {
      static final String RC_USER_ID = "rc_user_id";
      static final String RC_THREAD_ENABLED = "rc_thread_enabled";
      static final String RC_SOURCE_NAME = "rc_source_name";
      static RcMaps maps;

    static Dimelo setupDimelo(Context context) {

        RcSourceModel rcSource = new RcSourceModel().getSelectedObject(context);
        Dimelo.setup(context);
        Dimelo dimelo = Dimelo.getInstance();
        maps = RcMaps.getInstance();
        dimelo.setMapsDependenciesExist(true);
        maps.setMapsLocationApiKey(BuildConfig.RC_MAPS_API_KEY);

        if (rcSource.hostname != null && !rcSource.hostname.isEmpty()) {
            dimelo.initializeWithApiSecretAndHostName(rcSource.apiSecret, rcSource.domainName + rcSource.hostname, null);
        } else {
            dimelo.initWithApiSecret(rcSource.apiSecret, rcSource.domainName, null);
        }

        dimelo.setDebug(true);
        dimelo.setUserName("John Doe");
        dimelo.setMapsApiKey(BuildConfig.RC_MAPS_API_KEY);
        boolean isThreadEnabled = RcConfig.getBooleanValueFromSharedPreference(context, RC_THREAD_ENABLED);
        setThreadsEnabled(context, RC_THREAD_ENABLED, isThreadEnabled);
        String userIdVal = RcConfig.getStringValueFromSharedPreference(context, RC_USER_ID);

        if (userIdVal != null) {
            dimelo.setUserIdentifier(userIdVal);
        }

     /*   if (isHmsAvailable(context)) {
            dimelo.setPushNotificationService("hms");
        }*/
       /*  FirebaseApp.initializeApp(context);
         FirebaseMessaging.getInstance().getToken()
                 .addOnCompleteListener(new OnCompleteListener<String>() {
                     @Override
                     public void onComplete(Task<String> task) {
                         if (task.isSuccessful()) {
                           String refreshedToken =  task.getResult();
                           if (refreshedToken != null){
                               Dimelo.getInstance().setDeviceToken(refreshedToken);
                           }


                             }

                         }

                 });*/

        JSONObject authInfo = new JSONObject();

        try {
            authInfo.put("CustomerId", "0123456789");
            authInfo.put("Dimelo", "Rocks!");
        } catch (JSONException e) {}

        dimelo.setAuthenticationInfo(authInfo);

        return dimelo;
    }

    private static boolean isHmsAvailable(Context context) {
        boolean isAvailable = false;

        if (context != null) {
            int result = HuaweiApiAvailability.getInstance().isHuaweiMobileServicesAvailable(context);
            isAvailable = (com.huawei.hms.api.ConnectionResult.SUCCESS == result);
        }

        return isAvailable;
    }

    public static void setConfigMessage(String name, String value){
        JSONObject messageContextInfo = new JSONObject();

        try {
            messageContextInfo.put(name, value);
        } catch (JSONException e) {}

        Dimelo.getInstance().setMessageContextInfo(messageContextInfo);
    }

     static void setThreadsEnabled(Context context, String key, boolean value) {
         SharedPreferences sharedPref = context.getSharedPreferences("RCSHAREDPREF", Context.MODE_PRIVATE);
         SharedPreferences.Editor editor = sharedPref.edit();
         editor.putBoolean(key, value).commit();
         Dimelo.getInstance().setThreadsEnabled(value);
     }

     static void savedStringInSharedPreference(Context context, String key, String value) {
         SharedPreferences sharedPref = context.getSharedPreferences("RCSHAREDPREF", Context.MODE_PRIVATE);
         SharedPreferences.Editor editor = sharedPref.edit();
         editor.putString(key, value).commit();
     }

     static String getStringValueFromSharedPreference(Context context, String key) {
        SharedPreferences sharedPref = context.getSharedPreferences("RCSHAREDPREF", Context.MODE_PRIVATE);
        return sharedPref.getString(key, null);
    }

    static void savedBooleanInsharedPreference(Context context, String key, boolean value) {
        SharedPreferences sharedPref = context.getSharedPreferences("RCSHAREDPREF", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(key, value).commit();
    }

    static boolean getBooleanValueFromSharedPreference(Context context, String key) {
        SharedPreferences sharedPref = context.getSharedPreferences("RCSHAREDPREF", Context.MODE_PRIVATE);
        return sharedPref.getBoolean(key, false);
    }

    static String readJsonFile (Context context) {
        String resp = "";

        try {
            InputStream is = context.getAssets().open("RcConfigSource.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            resp = new String(buffer, "UTF-8");
        } catch (Exception e) {
            Log.e("RcConfiguration", e.toString());
        }
        return resp;
    }

    public static void clickMap(Chat fragment, Activity activity) {
        maps
              //  .setSendButtonIconColor(Color.RED)
                //.setSendButtonIcon(R.drawable.bank_icon)
                //.setSendButtonBackgroundColor(Color.RED)
                 //.setNavigationBarTitleColor(Color.RED)
                 //.setNavigationBarBackgroundColor(Color.RED)
               // .setNavigationBarTitleFont(Typeface.DEFAULT_BOLD)
             // .setNavigationBarIconColor(Color.RED)
                // .setNavigationBarTitleSize((int) activity.getResources().getDimension(R.dimen.rc_navigation_bar_title_text_size_test))
                // .setButtonTextSize(12)
                .build(activity);

        maps.setMapsListener(new RcMaps.RcMapsListener() {
            @Override
            public void sendMessageLocation(Intent data) {
                super.sendMessageLocation(data);
                Dimelo.getInstance().sendMessageLocation(data, fragment);
            }
        });
    }
}
