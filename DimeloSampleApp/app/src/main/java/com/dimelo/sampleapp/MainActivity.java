package com.dimelo.sampleapp;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.dimelo.dimelosdk.main.Dimelo;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final String SENDER_ID = "YOUR_SENDER_ID"; // GCM ID

    private String mGcmRegistrationId;
    private Context mContext;
    private SlidingTabFragment mSlidingFragment;

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Bundle extras = intent.getExtras();
        if(extras != null){
            if(extras.containsKey(GcmIntentService.NOTIF_INTENT)){
                createDimelo();
                DimeloWrap.getDimelo().openChatActivity(this);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        // super.onSaveInstanceState(outState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        mContext = getApplicationContext();
        if (checkPlayServices()) {
            mGcmRegistrationId = CacheManager.getRegistrationId(mContext);

            if (mGcmRegistrationId.isEmpty()) {
                // This method will
                // 1) Register to gcm
                // 1) Set mGcmRegistrationId
                // 2) Call gcmIsReady
                registerInBackground();
            }
            else {
                Bundle extras = getIntent().getExtras();
                if(extras != null && extras.containsKey(GcmIntentService.NOTIF_INTENT)){
                    createDimelo();
                    DimeloWrap.getDimelo().openChatActivity(this);
                }
                gcmIsReady();
            }
        } else {
            Log.d("DimeloSampleApp", "No valid Google Play Services APK found.");
        }
    }

    Dimelo.DimeloListener dimeloListener = new Dimelo.DimeloListener() {
//        @Override
//        public void dimeloDisplayChatViewController(Dimelo dimelo) {
//
//        }

        @Override
        public boolean dimeloShouldDisplayNotificationWithText(Dimelo dimelo, String message) {
            if (mSlidingFragment.isAnyChatDisplayed()){
                return false;
            }
            return true;
        }

        @Override
        public void dimeloDidBeginNetworkActivity(Dimelo dimelo) {

        }

        @Override
        public void dimeloDidEndNetworkActivity(Dimelo dimelo) {

        }

        @Override
        public void dimeloChatDidSendMessage() {

        }

        @Override
        public void dimeloChatDidReceiveNewMessages() {

        }


    };

    private void createDimelo(){
        DimeloWrap.newDimeloInstance(this, mGcmRegistrationId, dimeloListener);
    }


    private void gcmIsReady(){
        createDimelo();
        mSlidingFragment = new SlidingTabFragment();

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.slider_container, mSlidingFragment);
        fragmentTransaction.commit();
    }

    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */
    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        9000).show();
            } else {
                Log.d("DimeloSampleApp", "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }


    /**
     * Registers the application with GCM servers asynchronously.
     * <p>
     * Stores the registration ID and app versionCode in the application's
     * shared preferences.
     */
    private void registerInBackground() {
        AsyncTask<?, ?, ?> task = new AsyncTask<Object, Void, String>() {

            private String mLocalGcmRegistrationId;

            @Override
            protected String doInBackground(Object... params) {
                String msg;
                try {
                    GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(mContext);
                    mLocalGcmRegistrationId = gcm.register(SENDER_ID);
                    msg = "Device registered, registration ID=" + mLocalGcmRegistrationId;

                    // Persist the registration ID - no need to register again.
                    CacheManager.storeRegistrationId(mContext, mLocalGcmRegistrationId);
                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();
                }
                return msg;
            }

            @Override
            protected void onPostExecute(String msg) {
                Log.d("DimeloSampleApp", msg);
                mGcmRegistrationId = mLocalGcmRegistrationId;
                gcmIsReady();
            }
        };
        task.execute(null, null, null);
    }


}
