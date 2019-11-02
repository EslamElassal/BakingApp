package com.example.bakingapp.ViewModel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.bakingapp.API.APIManager;
import com.example.bakingapp.Models.Meal;

import java.util.List;



public class MealsViewModelFactory extends ViewModelProvider.NewInstanceFactory {



     public MealsViewModelFactory() {

    }

     @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {

        return (T) new MealsViewModel();
    }
}

