package com.dimelo.sampleapp;

import com.dimelo.dimelosdk.main.Dimelo;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        MainActivity.setupDimelo(MyFirebaseMessagingService.this);
        if (Dimelo.consumeReceivedRemoteNotification(MyFirebaseMessagingService.this, remoteMessage.getData(), null)){
            // Cool !
        }
        else {
            // Not a dimelo Notification.
        }
    }
}
