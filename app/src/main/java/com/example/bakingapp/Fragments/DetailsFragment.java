package com.example.bakingapp.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bakingapp.Actvities.MealDetails;
import com.example.bakingapp.Actvities.VideoActivity;
import com.example.bakingapp.Adapters.MealDetailsAdapter;
import com.example.bakingapp.Models.Ingredients;
import com.example.bakingapp.Models.Steps;
import com.example.bakingapp.R;

import java.util.ArrayList;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class DetailsFragment extends Fragment implements MealDetailsAdapter.ListIngredientsItemClickListener{

    RecyclerView mDetailsRecyclerView;
    MealDetailsAdapter mDetailsAdapter;
    List<Ingredients>ingredients;
    List<Steps>steps;
    Context context;
    String Image;

     private static String STEPS_LIST_ID="STEPS_LIST_ID";
    private static String Ingerdients_LIST_ID="Ingerdients_LIST_ID";

    public DetailsFragment() { }


 @Override
public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

     // Load the saved state (the list of images and list index) if there is one
     if (savedInstanceState != null) {
         ingredients = savedInstanceState.getParcelableArrayList(Ingerdients_LIST_ID);
         steps = savedInstanceState.getParcelableArrayList(STEPS_LIST_ID);
     }

     View rootView = inflater.inflate(R.layout.meal_details_setion_fragment, container, false);


     mDetailsRecyclerView = (RecyclerView) rootView.findViewById(R.id.meal_details_ingredients_recyclerview__Fragment);

     mDetailsAdapter = new MealDetailsAdapter(ingredients, steps, DetailsFragment.this, context);
     mDetailsRecyclerView.setAdapter(mDetailsAdapter);

return  rootView;
 }


 public void setSteps(List<Steps> steps) {
        this.steps = steps;
        }

public void setIngerdients(List<Ingredients> ingerdients) {
        this.ingredients = ingerdients;
        }
public void setContext(Context context){this.context=context;}
public void setMealImage(String Image){this.Image= Image; }

 @Override
public void onSaveInstanceState(Bundle currentState) {
        currentState.putParcelableArrayList( STEPS_LIST_ID, new ArrayList<Steps>(steps));
        currentState.putParcelableArrayList( Ingerdients_LIST_ID, new ArrayList<Ingredients>(ingredients));

 }

    int getIngredientsSize()
    {
        if(ingredients==null)
            return 0;
        else
            return ingredients.size();
    }
    int getStepsSize()
    {
        if(steps==null)
            return 0;
        else
            return steps.size();

    }
    @Override
    public void onListItemClick(int item) {
        if(item>=getIngredientsSize())
        {
            VideoFragment videoFragment = new VideoFragment();
            Log.e("Eslam",+(item-getIngredientsSize())+"DetailsFragment");

            videoFragment.setIndex(item-getIngredientsSize());
            videoFragment.setSteps(steps);
            videoFragment.setContext(context);
            FragmentManager fragmentManager =  getActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.Meal_Video_Fragment_FrameLayout_Host, videoFragment)
                    .commit();
        }
    }
}