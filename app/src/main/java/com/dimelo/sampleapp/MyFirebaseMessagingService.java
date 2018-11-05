package com.dimelo.sampleapp;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.util.ArrayMap;
import com.dimelo.dimelosdk.main.Dimelo;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import java.util.Iterator;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

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

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        if (Dimelo.isInstantiated())
            Dimelo.getInstance().setDeviceToken(s);
    }
}
