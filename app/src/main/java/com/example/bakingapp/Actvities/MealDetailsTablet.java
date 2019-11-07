package com.example.bakingapp.Actvities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.bakingapp.API.APIManager;
import com.example.bakingapp.Adapters.MealDetailsAdapter;
import com.example.bakingapp.Database.IngredientDatabase;
import com.example.bakingapp.Database.IngredientEntry;
import com.example.bakingapp.Database.IngredientExceuter;
import com.example.bakingapp.Fragments.DetailsFragment;
import com.example.bakingapp.Fragments.VideoFragment;
import com.example.bakingapp.Models.Meal;
import com.example.bakingapp.MyApp;
import com.example.bakingapp.R;
import com.example.bakingapp.Widget.MealService;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MealDetailsTablet extends AppCompatActivity {
    List<Meal> mMeals=null;
    int ID;
    String Image;
    private IngredientDatabase mDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_details);
        mDb = IngredientDatabase.getInstance(this);
        Intent a = getIntent();
        if(a.hasExtra("activity")){
            if((a.getStringExtra("activity")).equals("YES"))
            {

                Image=a.getStringExtra("image");
                ID=a.getIntExtra("id",0);


            }
            else
            {
                ID=Integer.parseInt(MyApp.getSharedPrefrences("id","0"));
                Image=MyApp.getSharedPrefrences("image","");


            }
        }

        if(a.hasExtra("activity")){
            if((a.getStringExtra("activity")).equals("YES"))
            {

                IngredientExceuter.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {



                        mDb.IngredientDao().delelteIngredients();

                    }
                });

                 MyApp.saveSharedPrefrences("id",ID+"");
                MyApp.saveSharedPrefrences("image",Image);
                 boolean connection = isNetworkAvailable();
                if (connection) {
                    MealsDataWithInsert();
                }
                else
                {
                    Toast.makeText(MealDetailsTablet.this,"No Intetnet Connection",Toast.LENGTH_LONG).show();
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
                    Toast.makeText(MealDetailsTablet.this,"No Intetnet Connection",Toast.LENGTH_LONG).show();
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
                DetailsFragment detailsFragment = new DetailsFragment();
                detailsFragment.setIngerdients(mMeals.get(ID-1).getIngredients());
                detailsFragment.setSteps(mMeals.get(ID-1).getSteps());
                detailsFragment.setContext(getApplicationContext());
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .add(R.id.Meal_Details_Fragment_FrameLayout_Host, detailsFragment)
                        .commit();

                VideoFragment videoFragment = new VideoFragment();
                Log.e("Eslam","Tablet WithInsert");

                videoFragment.setMealImage(Image);
                videoFragment.setSteps(mMeals.get(ID-1).getSteps());
                 videoFragment.setContext(getApplicationContext());
                FragmentManager fragmentManager2 = getSupportFragmentManager();
                fragmentManager2.beginTransaction()
                        .add(R.id.Meal_Video_Fragment_FrameLayout_Host, videoFragment)
                        .commit();

                StartAlaramManageToUpdateWiget();

            }


            @Override
            public void onFailure(Call<List<Meal>> call, Throwable t) {
                Toast.makeText(MealDetailsTablet.this,"Data Not Available On Server or Check Your Connection",Toast.LENGTH_LONG).show();

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
                DetailsFragment detailsFragment = new DetailsFragment();
                detailsFragment.setIngerdients(mMeals.get(ID-1).getIngredients());
                detailsFragment.setSteps(mMeals.get(ID-1).getSteps());
                detailsFragment.setContext(getApplicationContext());
                detailsFragment.setMealImage(Image);
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .add(R.id.Meal_Details_Fragment_FrameLayout_Host, detailsFragment)
                        .commit();

               VideoFragment videoFragment = new VideoFragment();
                Log.e("Eslam","Tablet WithoutInsert");

                videoFragment.setSteps(mMeals.get(ID-1).getSteps());
                videoFragment.setMealImage(Image);
                 videoFragment.setContext(getApplicationContext());
                FragmentManager fragmentManager2 = getSupportFragmentManager();
                fragmentManager2.beginTransaction()
                        .add(R.id.Meal_Video_Fragment_FrameLayout_Host, videoFragment)
                        .commit();

            }


            @Override
            public void onFailure(Call<List<Meal>> call, Throwable t) {
                Toast.makeText(MealDetailsTablet.this,"Data Not Available On Server or Check Your Connection",Toast.LENGTH_LONG).show();

                Log.e("Eslam","Failure");
            }
        });
    }
}
