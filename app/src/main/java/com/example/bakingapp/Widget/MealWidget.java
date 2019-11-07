package com.example.bakingapp.Widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;

import com.example.bakingapp.Actvities.MainActivity;
import com.example.bakingapp.Actvities.MealDetails;
import com.example.bakingapp.Actvities.MealDetailsTablet;
import com.example.bakingapp.MyApp;
import com.example.bakingapp.R;

import static android.content.Context.MODE_PRIVATE;

/**
 * Implementation of App Widget functionality.
 */
public class MealWidget extends AppWidgetProvider {
     static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,String []Data,
                                int appWidgetId) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.meal_widget);
         Intent intent;
         if(isTablet(context))
         {
            intent  = new Intent(context.getApplicationContext(), MealDetailsTablet.class);
         }
         else{
             intent  = new Intent(context.getApplicationContext(), MealDetails.class);

         }
        intent.putExtra("activity","NO");
        PendingIntent pendingIntent = PendingIntent.getActivity(context.getApplicationContext(),0,intent,0);
        views.setOnClickPendingIntent(R.id.appwidget_text,pendingIntent);
        String data="";
        for(int i=0;i<Data.length;i++)
        {
            data+=Data[i]+"\n";
        }
        // String data = MyApp.getSharedPrefrences("widgetdata","No Data Yet");
        if(data==null)
        { views.setTextViewText(R.id.appwidget_text, "No Data Yet");}
        else{
            views.setTextViewText(R.id.appwidget_text,data);}

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }
public static boolean isTablet(Context context){
    return (context.getResources().getConfiguration().screenLayout& Configuration.SCREENLAYOUT_SIZE_MASK)>=Configuration.SCREENLAYOUT_SIZE_LARGE;

}
    public static void updatePlantWidgets(Context context, AppWidgetManager appWidgetManager,String []data,
                                         int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager ,data, appWidgetId);
        }
    }
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
       MealService.startActionUpdateWidgets(context);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

