package com.dimelo.sampleapp;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;

public class NotificationDisplayer {

    public static final int NOTIFICATION_ID = 1;

    static public void show(Context context, String msg){

        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
                intent, 0);


        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setAutoCancel(true)
                        .setSmallIcon(R.drawable.row_agent_message_bubble)
                        .setContentText(msg)
                        .setContentTitle(context.getResources().getString(R.string.app_name))
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                        // setVibrate and setSound will make the heads-up appear (on Android 5.0 or up)
                        .setVibrate(new long[]{200, 400, 200, 400}) //  { delay, vibrate, sleep, vibrate, sleep, etc..}
                        .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                        ;

        mBuilder.setContentIntent(contentIntent);
        mBuilder.setTicker(msg); // Work on Android lower to 5.0
        notificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }

}
