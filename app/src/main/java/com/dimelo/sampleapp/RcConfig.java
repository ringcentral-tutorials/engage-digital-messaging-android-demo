package com.dimelo.sampleapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.dimelo.dimelosdk.main.Dimelo;
import com.huawei.hms.api.HuaweiApiAvailability;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;

public class RcConfig {
      static final String RC_USER_ID = "rc_user_id";
      static final String RC_THREAD_ENABLED = "rc_thread_enabled";
      static final String RC_SOURCE_NAME = "rc_source_name";

     static Dimelo setupDimelo(Context context) {
        RcSourceModel rcSource = new RcSourceModel().getSelectedObject(context);
        Dimelo.setup(context);
        Dimelo dimelo = Dimelo.getInstance();

        if ((rcSource.hostname != null) && !(rcSource.hostname.isEmpty())) {
            dimelo.initializeWithApiSecretAndHostName(rcSource.apiSecret, rcSource.domainName + rcSource.hostname, null);
        } else {
            dimelo.initWithApiSecret(rcSource.apiSecret, rcSource.domainName, null);
        }

        dimelo.setDebug(true);
        dimelo.setUserName("John Doe");
        boolean isThreadEnabled = RcConfig.getBooleanValueFromSharedPreference(context, RC_THREAD_ENABLED);
        setThreadsEnabled(context, RC_THREAD_ENABLED, isThreadEnabled);
        String userIdVal = RcConfig.getStringValueFromSharedPreference(context, RC_USER_ID);

        if (userIdVal != null) {
            dimelo.setUserIdentifier(userIdVal);
        }

        /*if (isHmsAvailable(context)) {
            dimelo.setPushNotificationService("hms");
        }*/

       /* String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        if (refreshedToken != null) {
            dimelo.setDeviceToken(refreshedToken);
        }*/

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
        return  resp;
    }
}
