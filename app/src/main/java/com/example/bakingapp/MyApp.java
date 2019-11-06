package com.example.bakingapp;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class MyApp extends Application {
    public static  MyApp app;

   public MyApp()
    {
        app=this;
    }

    public static MyApp getAppInstanace() {
        return app;
    }

    public static void saveSharedPrefrences(String key, String value) {

        //save to value with the key in SharedPreferences
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(MyApp.getAppInstanace()).edit();
        editor.putString(key, value);
        editor.commit();
    }
    public static String getSharedPrefrences(String key, String defValue) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MyApp.getAppInstanace());
        return sharedPreferences.getString(key, defValue);
    }
}
