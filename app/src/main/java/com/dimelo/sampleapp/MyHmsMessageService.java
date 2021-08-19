package com.dimelo.sampleapp;

import android.text.TextUtils;

import com.dimelo.dimelosdk.main.Dimelo;
import com.huawei.hms.push.HmsMessageService;
import com.huawei.hms.push.RemoteMessage;

public class MyHmsMessageService extends HmsMessageService {

    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);

        if (!TextUtils.isEmpty(token) && Dimelo.isInstantiated()) {
            Dimelo.getInstance().setDeviceToken(token);
        }
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        RcConfig.setupDimelo(MyHmsMessageService.this);

        if (Dimelo.consumeReceivedRemoteNotification(MyHmsMessageService.this, remoteMessage.getDataOfMap(), null)){
            // Cool !
        } else {
            // Not a dimelo Notification.
        }
    }
}
