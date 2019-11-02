package com.example.bakingapp.Models;

import java.util.List;

public class Meal {
    String name;
    Integer id;
    List<Ingredients>ingredients;
    List<Steps>steps;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Ingredients> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredients> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Steps> getSteps() {
        return steps;
    }

    public void setSteps(List<Steps> steps) {
        this.steps = steps;
    }



    public Meal(String name, Integer id, List<Ingredients> ingredients, List<Steps> steps) {
        this.name = name;
        this.id = id;
        this.ingredients = ingredients;
        this.steps = steps;
    }
}
