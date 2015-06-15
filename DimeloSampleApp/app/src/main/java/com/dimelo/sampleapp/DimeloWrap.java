package com.dimelo.sampleapp;

import android.content.Context;
import android.support.annotation.Nullable;

import com.dimelo.dimelosdk.main.Dimelo;

import org.json.JSONException;
import org.json.JSONObject;

public class DimeloWrap {

    private Dimelo dimelo;
    static public Dimelo getDimelo(){
        return getInstance().dimelo;
    }

    public static void newDimeloInstance(Context context, @Nullable String gcmRegistrationId, @Nullable Dimelo.DimeloListener listener){
        getInstance().initDimelo(context, gcmRegistrationId, listener);
    }


    static private final boolean DEBUG = false;
    private void initDimelo(Context context, @Nullable String gcmRegistrationId, @Nullable Dimelo.DimeloListener listener){

        // 1. Init with secret and sign internally
        {
            // Prod setup.
            String secret = "YOUR_SECRET";
            String hostname = "YOUR_HOSTNAME";

            if (DEBUG) {
                // Dev setup
                secret = "YOUR_SECRET";
                hostname = "YOUR_HOSTNAME";
            }

            dimelo = Dimelo.createInstance(context);
            dimelo.setApiSecret(secret);
            dimelo.setHostname(hostname);
            if (gcmRegistrationId != null) {
                dimelo.setDeviceToken(gcmRegistrationId);
            }
            if (listener != null) {
                dimelo.setDimeloListener(listener);
            }

            // When any of these properties are set, JWT is recomputed instantly.
//                dimelo.setUserIdentifier("USER_ID");
//                dimelo.setUserName("Guest");
            JSONObject authenticationInfo = new JSONObject();
            try {
                authenticationInfo.put("bankBranch", "Poissonniere-0467");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            dimelo.setAuthenticationInfo(authenticationInfo);
        }
    }

    //
    // Singleton
    //
    private DimeloWrap() {

    }

    private static DimeloWrap getInstance(){
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        private static DimeloWrap instance = new DimeloWrap();
    }
}
