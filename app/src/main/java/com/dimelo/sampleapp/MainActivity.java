package com.dimelo.sampleapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.dimelo.dimelosdk.main.Dimelo;
import com.dimelo.dimelosdk.main.DimeloConnection;

public class MainActivity extends AppCompatActivity {
    public static int RESULT_CODE = 100;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        // Setup Dimelo
        final Dimelo dimelo = RcConfig.setupDimelo(getApplicationContext());
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

    public void updateConfig(View v) {
        Intent intent = new Intent(this, RcConfigurationActivity.class);
        startActivityForResult(intent, RESULT_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_CODE && resultCode == RESULT_CANCELED) {
            finishAffinity();
            System.exit(1);
       }
    }
}


