package com.dimelo.sampleapp;

import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.TaskStackBuilder;

import com.dimelo.dimelosdk.main.ChatActivity;
import com.dimelo.dimelosdk.main.Dimelo;
import com.google.android.gms.gcm.GoogleCloudMessaging;

public class GcmIntentService extends IntentService {

    public GcmIntentService() {
        super("GcmIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        String messageType = gcm.getMessageType(intent);

        if (!extras.isEmpty()) {  // has effect of unparcelling Bundle
            if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals(messageType)
                    || GoogleCloudMessaging.MESSAGE_TYPE_DELETED.equals(messageType)){
                // An error occured
            }
            else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {
                if (Dimelo.consumeReceivedRemoteNotification(this, extras, notifDisplayer)){
                    // Cool !
                }
                else {
                    // Not a dimelo Notification.
                }
            }
        }
        // Release the wake lock provided by the WakefulBroadcastReceiver.
        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }

    Dimelo.BasicNotificationDisplayer notifDisplayer = new Dimelo.BasicNotificationDisplayer() {

        @Override
        public @NonNull PendingIntent createPendingIntent(Context context, String message) {
            return TaskStackBuilder.create(GcmIntentService.this)
                    .addParentStack(ChatActivity.class)
                    .addNextIntent(new Intent(context, ChatActivity.class).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP))
                    .getPendingIntent(1, PendingIntent.FLAG_CANCEL_CURRENT);
        }

    };

}
