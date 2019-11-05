package com.example.bakingapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;

import com.example.bakingapp.Actvities.MainActivity;
import com.example.bakingapp.Actvities.MealDetails;

import static android.content.Context.MODE_PRIVATE;

/**
 * Implementation of App Widget functionality.
 */
public class MealWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,int id,String imageUrl,
                                int appWidgetId) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.meal_widget);
       /* SharedPreferences sharedPref =  context.getApplicationContext().getSharedPreferences("eslam",MODE_PRIVATE);
        String imageUrls =  sharedPref.getString("image","non");
        int ids = Integer.parseInt( sharedPref.getString("id","1"));*/
        Intent intent = new Intent(context.getApplicationContext(), MealDetails.class);
         intent.putExtra("id",id);
        intent.putExtra("image",imageUrl);

        PendingIntent pendingIntent = PendingIntent.getActivity(context.getApplicationContext(),0,intent,0);
        views.setOnClickPendingIntent(R.id.appwidget_text,pendingIntent);
        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object

        views.setTextViewText(R.id.appwidget_text, "Fuck");

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    public static void updatePlantWidgets(Context context, AppWidgetManager appWidgetManager,
                                          int id, String imageUrl, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, id,imageUrl , appWidgetId);
        }
    }
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
       MealService.startActionUpdatePlantWidgets(context);
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

