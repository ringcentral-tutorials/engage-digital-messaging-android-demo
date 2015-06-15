package com.dimelo.sampleapp;

import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.dimelo.dimelosdk.main.Dimelo;
import com.google.android.gms.gcm.GoogleCloudMessaging;

public class GcmIntentService extends IntentService {

    public static final String NOTIF_INTENT = "from_gcm_intent_service";

    public GcmIntentService() {
        super("GcmIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        // The getMessageType() intent parameter must be the intent you received
        // in your BroadcastReceiver.
        String messageType = gcm.getMessageType(intent);

        if (!extras.isEmpty()) {  // has effect of unparcelling Bundle
            /*
             * Filter messages based on message type. Since it is likely that GCM
             * will be extended in the future with new message types, just ignore
             * any message types you're not interested in, or that you don't
             * recognize.
             */
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
            Intent notificationIntent = new Intent(context, MainActivity.class);
            notificationIntent.putExtra(GcmIntentService.NOTIF_INTENT, true);
            notificationIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            return PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        }



        @Override
        public int getSmallIcon(String message) {
            return R.drawable.support_icon;
        }

        @Override
        public String getTitle(Context context, String message) {
            return context.getResources().getString(R.string.app_name);
        }

    };
}
