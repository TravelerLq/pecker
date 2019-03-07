package com.yuas.pecker.utils.search;

import android.os.Environment;
import android.os.Handler;
import android.os.Looper;


import com.yuas.pecker.bean.pecker.FileInfoBean;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

class CallbackExecutor {
    private final SearchEngine.SearchEngineCallback callback;
    private final long interval;

    private volatile Handler handler;

    private List<FileInfoBean> cachedItems = new ArrayList<>();
    private File currentDirectory = Environment.getExternalStorageDirectory();
    private volatile boolean isFinished;

    CallbackExecutor(SearchEngine.SearchEngineCallback callback, long interval){
        this.callback = callback;
        this.interval = interval;
    }

    void onFind(FileInfoBean item){
        if(handler == null){
            handler = new Handler(Looper.getMainLooper());
            handler.post(new Timer());
        }
        synchronized(callback){
            cachedItems.add(item);
        }
    }


    void onSearchDirectory(File file) {
        if(handler == null){
            handler = new Handler(Looper.getMainLooper());
            handler.post(new Timer());
        }
        currentDirectory = file;
    }

    void onFinish() {
        isFinished = true;
    }
    class Timer implements Runnable{
        @Override
        public void run() {
            synchronized(callback){
                callback.onFind(cachedItems);
                cachedItems.clear();
                callback.onSearchDirectory(currentDirectory);
                if(isFinished){
                    callback.onFinish();
                    return;
                }
                handler.postDelayed(this, interval);
            }
        }
    }
}
