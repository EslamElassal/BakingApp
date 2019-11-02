package com.example.bakingapp.API;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIManager {


    public static String baseUrl="https://d17h27t6h515a5.cloudfront.net/";
    private static Retrofit retrofitInstance;
    private static Retrofit getInstance(){
        if(retrofitInstance==null){
            //build

            retrofitInstance = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }
        return retrofitInstance;
    }

    public static Services getAPIS(){
        return getInstance().create(Services.class);
    }


}

