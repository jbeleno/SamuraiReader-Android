package com.samuraireader.katana.util;

import android.app.Application;
import android.content.Context;

public class AppSamuraiReader extends Application{
    private static Context context;
    private static final String TAG ="SamuraiReader";

    @Override
    public void onCreate() {
        super.onCreate();

        AppSamuraiReader.context = getApplicationContext();
        MyVolley.init(this);
    }

    public static String getTag(){
        return AppSamuraiReader.TAG;
    }

    public static Context getAppContext() {
        return AppSamuraiReader.context;
    }
}