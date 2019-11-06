package com.example.bakingapp.Database;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {IngredientEntry.class},version = 1,exportSchema = false)
  public abstract class IngredientDatabase extends RoomDatabase {
     public static IngredientDatabase sInstance;

    public static final Object LOCK=new Object();
     public static final String DATABASE_NAME="mealingredients";
    public static IngredientDatabase getInstance(Context context){
        if(sInstance==null)
        {}
        synchronized (LOCK){
            sInstance= Room.databaseBuilder(context.getApplicationContext(),
                    IngredientDatabase.class,
                    IngredientDatabase.DATABASE_NAME).build();
        }
        return sInstance;
    }

    public abstract IngredientDao IngredientDao();

}

