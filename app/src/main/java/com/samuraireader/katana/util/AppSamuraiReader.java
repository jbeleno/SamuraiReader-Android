/**
 * Title: SamuraiReader Android
 * Version: 1.0
 * Author: Juan Sebastián Beleño Díaz
 * Email: jsbeleno@gmail.com
 * Date: 27/02/2016
 *
 * This is the application class that will control some initial settings
 */

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