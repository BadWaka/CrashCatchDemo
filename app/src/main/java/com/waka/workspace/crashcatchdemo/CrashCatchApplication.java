package com.waka.workspace.crashcatchdemo;

import android.app.Application;

/**
 * Created by waka on 2016/1/13.
 */
public class CrashCatchApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CrashCatchHandler crashCatchHandler = CrashCatchHandler.getInstance();//获得单例
        crashCatchHandler.init(getApplicationContext());//初始化,传入context
    }
}
