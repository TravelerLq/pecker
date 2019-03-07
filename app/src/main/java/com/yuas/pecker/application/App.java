package com.yuas.pecker.application;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class App extends Application {

    private static App globalVar = null;

    private static List<Activity> activityStack = new ArrayList<>();

    public static App getInstance() {
        if(globalVar==null){
            synchronized (App.class){
                if(globalVar==null){
                    globalVar=new App();
                }
            }
        }
        return globalVar;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        globalVar = this;
    }

    public static App getGlobalVar() {
        return globalVar;
    }


    public static Context getContext() {
        return getInstance().getBaseContext();
    }
    /**
     * activity  into the stack
     *
     * @param activity
     */

    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new ArrayList<>();
        }
        activityStack.add(activity);
    }

    /**
     * activity out of the stack
     *@param activity
     */

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */

    public void removeActivity(Activity activity) {
        if (activity != null) {
            if (activityStack != null) {
                activityStack.remove(activity);
                activity.finish();
                activity = null;
            }
        }
    }

    //activity remove all
    public void removeAllActivity() {
        if (activityStack != null) {
            for (Activity activity : activityStack) {
                if (activity != null)
                    activity.finish();
                    activity = null;
            }
        }
    }
}
