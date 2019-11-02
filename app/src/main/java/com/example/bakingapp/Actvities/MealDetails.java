package com.example.bakingapp.Actvities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.widget.ImageView;

import com.example.bakingapp.API.APIManager;
import com.example.bakingapp.Adapters.MealDetailsAdapter;
import com.example.bakingapp.Models.Meal;
import com.example.bakingapp.Models.Steps;
import com.example.bakingapp.R;
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
    int ID;
    String Image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_details);
        Intent a = getIntent();
        ID=a.getIntExtra("id",0);
        Image=a.getStringExtra("image");
        MealImage = (ImageView) findViewById(R.id.meal_details_image);
        Picasso.with(this)
                .load(Image)
                .into(MealImage);
         mDetailsRecyclerView=(RecyclerView)findViewById(R.id.meal_details_ingredients_recyclerview);
         LinearLayoutManager layoutManagerIngredients = new LinearLayoutManager(this);

        mDetailsRecyclerView.setLayoutManager(layoutManagerIngredients);
        mDetailsRecyclerView.setHasFixedSize(true);


        MealsData();
    }
    void MealsData()
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
