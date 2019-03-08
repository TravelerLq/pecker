package com.yuas.pecker.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.yuas.pecker.utils.Loger;

public class MyIntentService extends IntentService {

    private static final String TAG = MyIntentService.class.getSimpleName();

    public MyIntentService() {
        super("MyIntentService");
    }

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */




    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        // 是运行在一个工作线程中的，可以downloadApp()
        Loger.e(TAG + "----onHandleIntent------------");


    }

    @Override
    public void onCreate() {
        super.onCreate();

        Loger.e(TAG + "------onCreate------------");
    }

    @Override
    public void onStart(@Nullable Intent intent, int startId) {
        super.onStart(intent, startId);
        Loger.e(TAG + "-----onStart------------");
    }


    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        Loger.e(TAG + "----onStartCommand------------");
        return super.onStartCommand(intent, flags, startId);

    }
}
