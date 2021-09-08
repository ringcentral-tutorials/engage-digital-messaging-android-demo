package com.dimelo.sampleapp;

import android.content.Context;
import android.content.SharedPreferences;

import com.dimelo.dimelosdk.main.Dimelo;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;

public class RcConfig {
      static final String RC_USER_ID = "rc_user_id";
      static final String RC_THREAD_ENABLED = "rc_thread_enabled";

     static Dimelo setupDimelo(Context context) {
        String secret = BuildConfig.ENGAGE_DIGITAL_MESSAGING_SECRET; // Edit in gradle.properties
        String domainName = BuildConfig.ENGAGE_DIGITAL_DOMAIN_NAME; // Edit in gradle.properties
        Dimelo.setup(context);

        Dimelo dimelo = Dimelo.getInstance();
        dimelo.initWithApiSecret(secret, domainName, null);
        dimelo.setDebug(true);
        dimelo.setUserName("John Doe");
        boolean isThreadEnabled = RcConfig.getBooleanValueFromSharedPreference(context, RC_THREAD_ENABLED);
        setThreadsEnabled(context, RC_THREAD_ENABLED, isThreadEnabled);
        String userIdVal = RcConfig.getStringValueFromSharedPreference(context, RC_USER_ID);
        if (userIdVal != null) {
            dimelo.setUserIdentifier(userIdVal);
        }

        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        if (refreshedToken != null) {
            dimelo.setDeviceToken(refreshedToken);
        }

        JSONObject authInfo = new JSONObject();

        try {
            authInfo.put("CustomerId", "0123456789");
            authInfo.put("Dimelo", "Rocks!");
        } catch (JSONException e) {}

        dimelo.setAuthenticationInfo(authInfo);

        return dimelo;
    }

    public static void setConfigMessage(String name, String value){
        JSONObject messageContextInfo = new JSONObject();

        try {
            messageContextInfo.put(name, value);
        } catch (JSONException e) {}

        Dimelo.getInstance().setMessageContextInfo(messageContextInfo);
    }

     static void setThreadsEnabled(Context context, String key, boolean value) {
         SharedPreferences sharedPref = context.getSharedPreferences("RCSHAREDPREF",Context.MODE_PRIVATE);
         SharedPreferences.Editor editor = sharedPref.edit();
         editor.putBoolean(key, value).commit();
        Dimelo.getInstance().setThreadsEnabled(value);
     }

     static void savedStringInsharedPreference(Context context, String key, String value) {
         SharedPreferences sharedPref = context.getSharedPreferences("RCSHAREDPREF",Context.MODE_PRIVATE);
         SharedPreferences.Editor editor = sharedPref.edit();
         editor.putString(key, value).commit();
     }

     static String getStringValueFromSharedPreference(Context context, String key) {
        SharedPreferences sharedPref = context.getSharedPreferences("RCSHAREDPREF",Context.MODE_PRIVATE);
        return sharedPref.getString(key, null);
    }

    static void savedBooleanInsharedPreference(Context context, String key, boolean value) {
        SharedPreferences sharedPref = context.getSharedPreferences("RCSHAREDPREF",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(key, value).commit();
    }

    static boolean getBooleanValueFromSharedPreference(Context context, String key) {
        SharedPreferences sharedPref = context.getSharedPreferences("RCSHAREDPREF",Context.MODE_PRIVATE);
        return sharedPref.getBoolean(key, false);
    }
}
