package com.dimelo.sampleapp;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.dimelo.dimelosdk.main.Dimelo;
import com.dimelo.dimelosdk.main.DimeloConnection;

public class MainActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        // Setup Dimelo
        final Dimelo dimelo = RcConfig.setupDimelo(this);
        dimelo.setDimeloListener(dimeloListener);
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        SlidingTabFragment mSlidingFragment = (SlidingTabFragment) supportFragmentManager.findFragmentByTag("mSlidingFragment");
        if (mSlidingFragment == null) {
            mSlidingFragment = new SlidingTabFragment();
            mSlidingFragment.setRetainInstance(true);
            FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.slider_container, mSlidingFragment, "mSlidingFragment");
            fragmentTransaction.commit();
        }
    }

    Dimelo.DimeloListener dimeloListener = new Dimelo.DimeloListener() {

        @Override
        public void onOpen(Dimelo dimelo) {
            super.onOpen(dimelo);
            Log.e("on open : ", "userIdentifier : " + dimelo.getUserIdentifier() + ", userName :" + dimelo.getUserName() + ", authenticationInfo :"+ dimelo.getAuthenticationInfo());
        }

        @Override
        public void onClose(Dimelo dimelo) {
            super.onClose(dimelo);
            Log.e("on open : ", "userIdentifier : " + dimelo.getUserIdentifier() + ", userName :" + dimelo.getUserName() + ", authenticationInfo :"+ dimelo.getAuthenticationInfo());
        }

        @Override
        public void dimeloChatDidSendMessage() {
        }

        @Override
        public void dimeloChatMessageSendFail(DimeloConnection.DimeloError error) {
            // Something went wrong
            // Minimal error management

            String message = "An error occurred";
            if (error.statusCode == DimeloConnection.DimeloError.NO_CONNECTION_ERROR) {
                message = "Please check your Internet connection and try again later.";
            } else if (error.statusCode == DimeloConnection.DimeloError.TIMEOUT_ERROR) {
                message = "The server is not responding, please try again later";
            }
            Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void onBackPressed() {
        SlidingTabFragment mSlidingFragment = (SlidingTabFragment) getSupportFragmentManager().findFragmentByTag("mSlidingFragment");

        if (mSlidingFragment != null) {
            if (mSlidingFragment.onBackPressed() || mSlidingFragment.isHandlingBack()) {
                return;
            }
        }

        super.onBackPressed();
    }

//    /**
//     * Registers the application with GCM servers asynchronously.
//     * <p/>
//     * Stores the registration ID and app versionCode in the application's
//     * shared preferences.
//     */
//    private void registerInBackground() {
//        final Context mContext = getApplicationContext();
//        AsyncTask<Object, Void, String> task = new AsyncTask<Object, Void, String>() {
//
//            private String mGcmRegistrationId;
//
//            @Override
//            protected String doInBackground(Object... params) {
//                String msg;
//                try {
//                    GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(mContext);
//                    mGcmRegistrationId = gcm.register(SENDER_ID);
//                    msg = "Device registered, registration ID=" + mGcmRegistrationId;
//                } catch (IOException ex) {
//                    msg = "Error :" + ex.getMessage();
//                }
//                return msg;
//            }
//
//            @Override
//            protected void onPostExecute(String msg) {
//                Log.d("DimeloSampleApp", msg);
//                Dimelo.getInstance().setDeviceToken(mGcmRegistrationId);
//            }
//        };
//        task.execute(null, null, null);
//    }

}



