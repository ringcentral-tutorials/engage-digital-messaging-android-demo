package com.dimelo.sampleapp;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.dimelo.dimelosdk.main.Dimelo;
import com.dimelo.dimelosdk.main.DimeloConnection;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final String SENDER_ID = BuildConfig.GCM_API_KEY; // GCM ID to be defined in gradle.properties

    private SlidingTabFragment mSlidingFragment;

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        // Keep it empty
        // Prevent super.onSaveInstanceState to affect our fragments
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        // Get GCM Token
        registerInBackground();

        // Setup Dimelo
        setupDimelo();

        // Push Slider Fragment
        mSlidingFragment = new SlidingTabFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.slider_container, mSlidingFragment);
        fragmentTransaction.commit();
    }

    Dimelo.DimeloListener dimeloListener = new Dimelo.DimeloListener() {
//        @Override
//        public boolean dimeloShouldDisplayNotificationWithText(Dimelo dimelo, String message) {
//            // When Chat Fragments are pushed in a viewpager, Dimelo Sdk cannot detect if the chats are visible.
//            // Thus, "dimeloShouldDisplayNotificationWithText" will be called.
//            return !mSlidingFragment.isAnyChatDisplayed();
//        }

        @Override
        public void dimeloChatMessageSendFail(DimeloConnection.DimeloError error) {
            // Something went wrong
            // Minimal error management

            String message = "An error occurred";
            if (error.statusCode == DimeloConnection.DimeloError.NO_CONNECTION_ERROR){
                message = "Please check your Internet connection and try again later.";
            }
            else if (error.statusCode == DimeloConnection.DimeloError.TIMEOUT_ERROR){
                message = "The server is not responding, please try again later";
            }
            Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    };

    private void setupDimelo(){
        String secret = BuildConfig.DIMELO_SDK_SECRET; //edit in gradle.properties
        Dimelo.setup(this);
        Dimelo dimelo = Dimelo.getInstance();
        dimelo.setApiSecret(secret);
        dimelo.setDimeloListener(dimeloListener);
    }

    @Override
    public void onBackPressed() {
        if (mSlidingFragment.isHandlingBack())
            return;
        super.onBackPressed();
    }

    /**
     * Registers the application with GCM servers asynchronously.
     * <p>
     * Stores the registration ID and app versionCode in the application's
     * shared preferences.
     */
    private void registerInBackground() {
        final Context mContext = getApplicationContext();
        AsyncTask<?, ?, ?> task = new AsyncTask<Object, Void, String>() {

            private String mGcmRegistrationId;

            @Override
            protected String doInBackground(Object... params) {
                String msg;
                try {
                    GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(mContext);
                    mGcmRegistrationId = gcm.register(SENDER_ID);
                    msg = "Device registered, registration ID=" + mGcmRegistrationId;
                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();
                }
                return msg;
            }

            @Override
            protected void onPostExecute(String msg) {
                Log.d("DimeloSampleApp", msg);
                Dimelo.getInstance().setDeviceToken(mGcmRegistrationId);
            }
        };
        task.execute(null, null, null);
    }


}
