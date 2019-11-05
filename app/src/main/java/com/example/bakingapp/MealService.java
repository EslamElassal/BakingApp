package com.example.bakingapp;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;

import androidx.annotation.Nullable;

import com.example.bakingapp.Actvities.MealDetails;

public class MealService extends IntentService {
   public static String Meal_Details_Activity_Action="Meal_Details_Activity_Action";
    public MealService() {
        super("MealService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if(intent.getAction()==Meal_Details_Activity_Action)
        {
            LaunchMealDetialsActivity();
        }
    }
    public static void startActionUpdatePlantWidgets(Context context) {
        Intent intent = new Intent(context, MealService.class);
        intent.setAction(Meal_Details_Activity_Action);
        context.startService(intent);
    }

    void LaunchMealDetialsActivity()
    {
        SharedPreferences sharedPref =  getApplicationContext().getSharedPreferences("eslam",MODE_PRIVATE);
        String imageUrl =  sharedPref.getString("image","non");
        int id = Integer.parseInt( sharedPref.getString("id","1"));

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, MealWidget.class));

        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.appwidget_text);

        MealWidget.updatePlantWidgets(this, appWidgetManager, id,imageUrl ,appWidgetIds);

    }
}
