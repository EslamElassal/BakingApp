package com.example.bakingapp.Actvities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.bakingapp.API.APIManager;
import com.example.bakingapp.Adapters.MealDetailsAdapter;
import com.example.bakingapp.Database.IngredientDatabase;
import com.example.bakingapp.Database.IngredientEntry;
import com.example.bakingapp.Database.IngredientExceuter;
import com.example.bakingapp.Models.Meal;
import com.example.bakingapp.Models.Steps;
import com.example.bakingapp.MyApp;
import com.example.bakingapp.R;
import com.example.bakingapp.Widget.MealService;
import com.example.bakingapp.Widget.MealWidget;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MealDetails extends AppCompatActivity implements   MealDetailsAdapter.ListIngredientsItemClickListener{
    RecyclerView mDetailsRecyclerView;
     MealDetailsAdapter mDetailsAdapter;
    ImageView MealImage;
    List<Meal> mMeals=null;
    public static Activity activity;
    int ID;
    String Image;
    private IngredientDatabase mDb;
      @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_details);
        activity=this;
        MealImage = (ImageView) findViewById(R.id.meal_details_image);
        Intent a = getIntent();
         if(a.hasExtra("activity")){
            if((a.getStringExtra("activity")).equals("YES"))
            {

        ID=a.getIntExtra("id",0);
        Image=a.getStringExtra("image");
        Picasso.with(this)
                .load(Image)
                .into(MealImage);
            }
            else
            {
                ID=Integer.parseInt(MyApp.getSharedPrefrences("id","0"));
                Image=MyApp.getSharedPrefrences("image","");
                 Picasso.with(this)
                        .load(Image)
                        .into(MealImage);
            }
         }
          mDetailsRecyclerView=(RecyclerView)findViewById(R.id.meal_details_ingredients_recyclerview);
         LinearLayoutManager layoutManagerIngredients = new LinearLayoutManager(this);

        mDetailsRecyclerView.setLayoutManager(layoutManagerIngredients);
        mDetailsRecyclerView.setHasFixedSize(true);
//save Last Details Activity Meal Id and Image Url In SharedPreferences To get them in Widget

        mDb = IngredientDatabase.getInstance(this);

        if(a.hasExtra("activity")){
           if((a.getStringExtra("activity")).equals("YES"))
           {

               IngredientExceuter.getInstance().diskIO().execute(new Runnable() {
               @Override
               public void run() {



                   mDb.IngredientDao().delelteIngredients();

               }
           });

               MyApp.saveSharedPrefrences("image",Image);
               MyApp.saveSharedPrefrences("id",ID+"");

               boolean connection = isNetworkAvailable();
               if (connection) {
                   MealsDataWithInsert();
               }
               else
               {
                   Toast.makeText(MealDetails.this,"No Intetnet Connection",Toast.LENGTH_LONG).show();
               }




                }

           else
                {
                    boolean connection = isNetworkAvailable();
                    if (connection) {
                        MealsDataWithoutInsert();
                    }
                    else
                    {
                        Toast.makeText(MealDetails.this,"No Intetnet Connection",Toast.LENGTH_LONG).show();
                    }


           }

       }

    }
    public boolean isNetworkAvailable(){

        ConnectivityManager connectivityManager=(ConnectivityManager) this.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        return networkInfo !=null;
    }
    void StartAlaramManageToUpdateWiget()
    {
        final Intent intent = new Intent(this, MealService.class);
        intent.setAction(MealService.REPEAT_UPDATING_WIDGET_Action);
        final PendingIntent pending = PendingIntent.getService(this, 0, intent, 0);
        final AlarmManager alarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        alarm.cancel(pending);
        long interval = 1000*5;
        alarm.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime(),interval, pending);

    }
    void MealsDataWithInsert()
    {

        final Call<List<Meal>> meals= APIManager.getAPIS().getMeals();
        meals.enqueue(new Callback<List<Meal>>() {
            @Override
            public void onResponse(Call<List<Meal>> call, Response<List<Meal>> response) {
                if (!response.isSuccessful()) {


                    Log.e("Eslam", response.toString());
                    return;
                }

                mMeals=response.body();
                setTitle(mMeals.get(ID-1).getName());
                mDetailsAdapter = new MealDetailsAdapter(mMeals.get(ID-1).getIngredients(),mMeals.get(ID-1).getSteps(),MealDetails.this,MealDetails.this);
                mDetailsRecyclerView.setAdapter(mDetailsAdapter);
                String data="";
                for(int i=0;i<mMeals.size();i++)
                {
                float Quantity=mMeals.get(ID-1).getIngredients().get(i).getQuantity();
                String Ingredient=mMeals.get(ID-1).getIngredients().get(i).getIngredient();
                String Measure=mMeals.get(ID-1).getIngredients().get(i).getMeasure();
                data+=Quantity+" "+Measure+" "+Ingredient+"\n";
                }
                MyApp.saveSharedPrefrences("widgetdata",data);

                       IngredientExceuter.getInstance().diskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            for(int i =0;i<mMeals.get(ID-1).getIngredients().size();i++)
                            {
                            String Ingredient=mMeals.get(ID-1).getIngredients().get(i).getIngredient();
                            String Measure=mMeals.get(ID-1).getIngredients().get(i).getMeasure();
                            float Quantity=mMeals.get(ID-1).getIngredients().get(i).getQuantity();
                            int Id=ID-1;
                            IngredientEntry entry=new IngredientEntry(Id,Quantity,Measure,Ingredient);

                            mDb.IngredientDao().insertIngredient(entry);
                            }
                        }
                    });

                StartAlaramManageToUpdateWiget();

             }


            @Override
            public void onFailure(Call<List<Meal>> call, Throwable t) {

                Log.e("Eslam","Failure");
            }
        });
    }
    void MealsDataWithoutInsert()
    {

        final Call<List<Meal>> meals= APIManager.getAPIS().getMeals();
        meals.enqueue(new Callback<List<Meal>>() {
            @Override
            public void onResponse(Call<List<Meal>> call, Response<List<Meal>> response) {
                if (!response.isSuccessful()) {


                    Log.e("Eslam", response.toString());
                    return;
                }

                mMeals=response.body();
                setTitle(mMeals.get(ID-1).getName());
                mDetailsAdapter = new MealDetailsAdapter(mMeals.get(ID-1).getIngredients(),mMeals.get(ID-1).getSteps(),MealDetails.this,MealDetails.this);
                mDetailsRecyclerView.setAdapter(mDetailsAdapter);


            }


            @Override
            public void onFailure(Call<List<Meal>> call, Throwable t) {

                Log.e("Eslam","Failure");
            }
        });
    }
    int getIngredientsSize()
    {
        if(mMeals.get(ID-1).getIngredients()==null)
            return 0;
        else
            return mMeals.get(ID-1).getIngredients().size();
    }
    int getStepsSize()
    {
        if(mMeals.get(ID-1).getSteps()==null)
            return 0;
        else
            return mMeals.get(ID-1).getSteps().size();

    }

    @Override
    public void onListItemClick(int item) {
        int size=getIngredientsSize();
        if(item>=getIngredientsSize())
        {
            Intent intent = new Intent(this,VideoActivity.class);
            intent.putExtra("id",ID);
            intent.putExtra("desc",mMeals.get(ID-1).getSteps().get(item-getIngredientsSize()).getDescription());
            intent.putExtra("shortdesc",mMeals.get(ID-1).getSteps().get(item-getIngredientsSize()).getShortDescription());
            intent.putExtra("video",mMeals.get(ID-1).getSteps().get(item-getIngredientsSize()).getVideoURL());
            intent.putExtra("title",mMeals.get(ID-1).getName());
             intent.putExtra("stepsindex",item-getIngredientsSize());

            intent.putParcelableArrayListExtra("steps",new ArrayList<Steps>(mMeals.get(ID-1).getSteps()));
             startActivity(intent);
        }

    }
}
