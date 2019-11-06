package com.example.bakingapp.Actvities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ActionBar;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.bakingapp.API.APIManager;
import com.example.bakingapp.Adapters.MealsAdapter;
import com.example.bakingapp.Models.Meal;
import com.example.bakingapp.R;
import com.example.bakingapp.ViewModel.MealsViewModel;
import com.example.bakingapp.ViewModel.MealsViewModelFactory;

import java.nio.channels.InterruptedByTimeoutException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements MealsAdapter.ListItemClickListener{
 RecyclerView mRecyclerView;
 MealsAdapter mAdapter;
    List<Meal> mMeals=null;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.icon);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.SwipeRefreshLayoutMain);
        mRecyclerView =(RecyclerView)findViewById(R.id.meal_main_recyclerview);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);

                boolean connection = isNetworkAvailable();
                if (connection) {
                    MealsData();

                }
                else
                {
                    Toast.makeText(MainActivity.this,"No Intetnet Connection",Toast.LENGTH_LONG).show();
                }
            }
        });

        boolean connection = isNetworkAvailable();
        if (connection) {
            MealsData();
        }
        else
        {
            Toast.makeText(MainActivity.this,"No Intetnet Connection",Toast.LENGTH_LONG).show();
        }
     }
    public boolean isNetworkAvailable(){

        ConnectivityManager connectivityManager=(ConnectivityManager) this.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        return networkInfo !=null;
    }

    void MealsData()
    {

        Call<List<Meal>> meals=APIManager.getAPIS().getMeals();
        meals.enqueue(new Callback<List<Meal>>() {
            @Override
            public void onResponse(Call<List<Meal>> call, Response<List<Meal>>response) {
                if (!response.isSuccessful()) {


                    Log.e("Eslam", response.toString());
                    return;
                }

                mMeals=response.body();
                mAdapter = new MealsAdapter(mMeals,MainActivity.this,MainActivity.this);
                mRecyclerView.setAdapter(mAdapter);

            }


            @Override
            public void onFailure(Call<List<Meal>> call, Throwable t) {

 Log.e("Eslam","Failure");
             }
        });
            }

    @Override
    public void onListItemClick(int item) {
                Intent intent = new Intent(this,MealDetails.class);
                intent.putExtra("id",mMeals.get(item).getId());
                intent.putExtra("image",mAdapter.imagesUrls.get(item));
                intent.putExtra("activity","YES");
                 startActivity(intent);

    }
}
