package com.dimelo.sampleapp;

import android.content.Context;

import com.dimelo.dimelosdk.main.Dimelo;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;

public class RcConfig {
    public static Dimelo setupDimelo(Context context) {
        String secret = BuildConfig.ENGAGE_DIGITAL_MESSAGING_SECRET; //edit in gradle.properties
        String domainName = BuildConfig.ENGAGE_DIGITAL_DOMAIN_NAME; //edit in gradle.properties
        Dimelo.setup(context);

        Dimelo dimelo = Dimelo.getInstance();
        dimelo.initWithApiSecret(secret, domainName, null);
        dimelo.setDebug(true);
        dimelo.setUserName("John Doe");
        dimelo.setThreadsEnabled(true);

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
}
