package com.example.bakingapp.Widget;

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
import com.example.bakingapp.Database.IngredientDatabase;
import com.example.bakingapp.Database.IngredientEntry;
import com.example.bakingapp.Database.IngredientExceuter;
import com.example.bakingapp.MyApp;
import com.example.bakingapp.R;

import java.util.List;

public class MealService extends IntentService {
   public static String Meal_Details_Activity_Action="Meal_Details_Activity_Action";
    public static String REPEAT_UPDATING_WIDGET_Action="REPEAT_UPDATING_WIDGET_Action";
    private IngredientDatabase mDb;

    public MealService() {
        super("MealService");

    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        mDb = IngredientDatabase.getInstance(getApplicationContext());

        if(intent.getAction()==Meal_Details_Activity_Action||intent.getAction()==REPEAT_UPDATING_WIDGET_Action)
        {
            UpdateWidgetData();
        }

    }

    public static void startActionUpdateWidgets(Context context) {
        Intent intent = new Intent(context, MealService.class);
        intent.setAction(Meal_Details_Activity_Action);
        context.startService(intent);
    }

    void UpdateWidgetData() {



        IngredientExceuter.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {

                List<IngredientEntry>entries= mDb.IngredientDao().queryIngredients();
                String data[]=new String[entries.size()];
                for(int i=0;i<entries.size();i++)
                {
                    data[i]=entries.get(i).getQuantity()+" "+entries.get(i).getMeasure()+" "+entries.get(i).getIngredient();
                }
                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(getBaseContext());
                int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(getBaseContext(), MealWidget.class));

                appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.appwidget_text);
                MealWidget.updatePlantWidgets(getBaseContext(), appWidgetManager,data,appWidgetIds);
            }
        });


    }

}
