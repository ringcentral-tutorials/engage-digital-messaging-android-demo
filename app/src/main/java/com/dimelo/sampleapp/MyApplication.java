package com.dimelo.sampleapp;

import android.app.Application;

import com.bugsnag.android.Bugsnag;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //Initialize Bugsnag
        Bugsnag.init(this);
    }
}
