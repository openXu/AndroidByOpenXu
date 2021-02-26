package com.openxu.core.base;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ProcessLifecycleOwner;

import com.openxu.core.utils.FLog;

import java.util.List;
import java.util.Stack;

/**
 * Author: openXu
 * Time: 2019/2/23 14:49
 * class: BaseApplication
 * Description: Application请继承该基类
 */
public abstract class BaseApplication extends Application {

    private static Stack<Activity> activityStack = new Stack<>();
    private static Context mcontext;
    protected static BaseApplication application;

    @Override
    public void onCreate() {
        application = this;
        mcontext = getApplicationContext();
        super.onCreate();
        //注册App生命周期观察者
        ProcessLifecycleOwner.get().getLifecycle().addObserver(new LifecycleObserver () {
            @OnLifecycleEvent(Lifecycle.Event.ON_START)
            private void onAppForeground() {
                FLog.w("ApplicationObserver: app moved to foreground");
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
            private void onAppBackground() {
                FLog.w("ApplicationObserver: app moved to background");
            }
        });


    }

    public static BaseApplication getApplication() {
        return application;
    }
    public static Context getContext() {
        return mcontext;
    }



    /**
     * 获取当前进程名 com.fzy.hbm
     * @return
     */
    protected String getCurrentProcessName(){
        int pid = android.os.Process.myPid();
        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps != null && !runningApps.isEmpty()) {
            for (ActivityManager.RunningAppProcessInfo processInfo : runningApps) {
                if (processInfo.pid == pid) {
                   return processInfo.processName;
                }
            }
        }
        return "";
    }


}
