package com.dimelo.sampleapp;

import android.annotation.SuppressLint;
import android.util.Log;

import com.dimelo.dimelosdk.main.Dimelo;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    public static final String TAG = "MyFirebaseMessagingService";
    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */

    @SuppressLint("LongLogTag")
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.e(TAG, remoteMessage.getData() + "");
        if (Dimelo.consumeReceivedRemoteNotification(MyFirebaseMessagingService.this, remoteMessage.getData(), null)){
            // Cool !
        }
        else {
            // Not a dimelo Notification.
        }
    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        if (Dimelo.isInstantiated())
            Dimelo.getInstance().setDeviceToken(s);
    }
}
