package com.example.bakingapp.Database;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface IngredientDao {

    @Query("SELECT * FROM ingredient order by id")
    List<IngredientEntry> queryIngredients();

      @Insert
    void insertIngredients(List<IngredientEntry> taskEntries);
    @Insert
    void insertIngredient(IngredientEntry taskEntry);
     @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateIngredient(IngredientEntry taskEntry);
    @Query("DELETE FROM ingredient WHERE id = :mead_id")
    void delelteIngredient(String mead_id);
    @Query("DELETE FROM ingredient")
    void delelteIngredients();

    @Query("SELECT * FROM ingredient where id = :mead_id")
    IngredientEntry queryIngredientById(int mead_id);

}

