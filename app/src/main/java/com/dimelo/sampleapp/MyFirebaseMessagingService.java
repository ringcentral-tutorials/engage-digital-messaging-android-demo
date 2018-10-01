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

    private static final String TAG = "MyFirebaseMsgService";

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            ArrayMap<String, String> json = (ArrayMap<String, String>) remoteMessage.getData();
            final Bundle extras = new Bundle();
            Iterator iter = json.keySet().iterator();
            while (iter.hasNext()) {
                String key = (String) iter.next();
                String value = json.get(key);
                extras.putString(key, value);
            }

            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    MainActivity.setupDimelo(MyFirebaseMessagingService.this);
                    if (Dimelo.consumeReceivedRemoteNotification(MyFirebaseMessagingService.this, extras, null)){
                        // Cool !
                    }
                    else {
                        // Not a dimelo Notification.
                    }
                }
            });
        }
    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        if (Dimelo.isInstantiated())
            Dimelo.getInstance().setDeviceToken(s);
    }
}
