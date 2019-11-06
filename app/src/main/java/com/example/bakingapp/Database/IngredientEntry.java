package com.example.bakingapp.Database;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "ingredient")
public class IngredientEntry {

     @PrimaryKey(autoGenerate = true)
     private int id;
     private int meal_id;
     private float quantity;
     private String measure;
     private String ingredient;

       @Ignore
    public IngredientEntry(int meal_id,float quantity, String measure, String ingredient) {
        this.meal_id=meal_id;
        this.quantity=quantity;
        this.measure = measure;
        this.ingredient = ingredient;

    }



    public IngredientEntry(int id, int meal_id, float quantity, String measure, String ingredient) {
        this.id=id;
        this.meal_id=meal_id;
        this.quantity=quantity;
        this.measure = measure;
        this.ingredient = ingredient;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMeal_id() {
        return meal_id;
    }

    public void setMeal_id(int meal_id) {
        this.meal_id = meal_id;
    }
    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }
}