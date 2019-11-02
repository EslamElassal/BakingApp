package com.example.bakingapp.ViewModel;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.example.bakingapp.API.APIManager;
import com.example.bakingapp.Actvities.MainActivity;
import com.example.bakingapp.Adapters.MealsAdapter;
import com.example.bakingapp.Models.Meal;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MealsViewModel extends ViewModel {


    private List<Meal> mMeals=null;

    public MealsViewModel() {
        final Call<List<Meal>> meals=APIManager.getAPIS().getMeals();
        meals.enqueue(new Callback<List<Meal>>() {
            @Override
            public void onResponse(Call<List<Meal>> call, Response<List<Meal>> response) {
                if (!response.isSuccessful()) {


                    Log.e("Eslam", response.toString());
                    return;
                }

                mMeals =response.body();


            }


            @Override
            public void onFailure(Call<List<Meal>> call, Throwable t) {

                Log.e("Eslam","Failure");
            }
        });


    }


    public List<Meal> getMeal() {
        return mMeals;
    }
}
