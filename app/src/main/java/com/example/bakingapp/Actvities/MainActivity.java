package com.example.bakingapp.Actvities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.bakingapp.API.APIManager;
import com.example.bakingapp.Models.Meal;
import com.example.bakingapp.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
TextView test;
List<Meal> mMeals=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        test=(TextView)findViewById(R.id.testTextView);
        JsonFunction();
    }
    void JsonFunction()
    {

        Call<List<Meal>> meals=APIManager.getAPIS().getMeals();
        meals.enqueue(new Callback<List<Meal>>() {
            @Override
            public void onResponse(Call<List<Meal>> call, Response<List<Meal>>response) {
                if (!response.isSuccessful()) {

                    test.setText("Response : " + response.message());
                    Log.e("Eslam", response.toString());
                    return;
                }

                List<Meal> postResponse = response.body();
                for(int i=0;i<postResponse.size();i++)
                {
test.append(i+"\n\n");
                }

            }


            @Override
            public void onFailure(Call<List<Meal>> call, Throwable t) {

                test.setText(t.getMessage());
            }
        });
    }
}
