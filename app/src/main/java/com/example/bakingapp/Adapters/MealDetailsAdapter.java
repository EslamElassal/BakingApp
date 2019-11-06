package com.example.bakingapp.Adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.bakingapp.Models.Ingredients;
import com.example.bakingapp.Models.Steps;
import com.example.bakingapp.R;

import java.util.List;

public class MealDetailsAdapter extends RecyclerView.Adapter<MealDetailsAdapter.MealDetailsViewHolder> {
     List<Ingredients>ingredients;
     List<Steps>steps;
     Context context;
     private boolean StepTitleFlag=false;

    final private ListIngredientsItemClickListener mOnClickListener;


      public interface ListIngredientsItemClickListener{
        void onListItemClick(int item);
    }

    public MealDetailsAdapter(List<Ingredients>ingredients, List<Steps>steps, ListIngredientsItemClickListener listItemClickListener, Context context) {
        this.ingredients=ingredients;
        this.steps=steps;
        mOnClickListener= listItemClickListener;
        this.context=context;

    }

    @Override
    public int getItemViewType(int position) {
          if((position<getIngredientsSize()))
              return 10;
else
              return 20;

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
    public MealDetailsViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
         boolean shouldAttachToParentImmediately = false;
        int layoutId;
 if(viewType==10)
{
    layoutId = R.layout.ingredients_list_item;
}
else
{
    layoutId = R.layout.steps_list_item;
}

        View view = LayoutInflater.from(context).inflate(layoutId, viewGroup, shouldAttachToParentImmediately);
        view.setFocusable(true);
        return new MealDetailsViewHolder(view);
    }


    @Override
    public void onBindViewHolder(MealDetailsViewHolder holder, int position) {
        holder.bind(position);
    }


    @Override
    public int getItemCount() {
          int sum=getStepsSize()+getIngredientsSize();
        return (sum);
    }


    class MealDetailsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

          TextView MealQuantity;
          TextView MealMeasure;
          TextView MealIngredient;
          TextView MeadDetailsStepsDescription;
          TextView StepsTitle;
          View     Corner;
        public MealDetailsViewHolder(View itemView) {
            super(itemView);

            MealQuantity = (TextView) itemView.findViewById(R.id.meal_details_quantity);
            MealMeasure = (TextView) itemView.findViewById(R.id.meal_details_measure);
            MealIngredient = (TextView) itemView.findViewById(R.id.meal_details_ingredient);
            MeadDetailsStepsDescription = (TextView) itemView.findViewById(R.id.meal_details_shortDescription);
            StepsTitle = (TextView) itemView.findViewById(R.id.StepsTitle);
            Corner=(View)itemView.findViewById(R.id.Corner);
            itemView.setOnClickListener(this);
         }


        void bind(int listIndex) {

            if(listIndex<getIngredientsSize())
            { MealQuantity.setText(ingredients.get(listIndex).getQuantity()+"");
            MealMeasure.setText(ingredients.get(listIndex).getMeasure());
            MealIngredient.setText(ingredients.get(listIndex).getIngredient());}
            else if(listIndex==getIngredientsSize()){
                Corner.setVisibility(View.GONE);
                StepsTitle.setVisibility(View.VISIBLE);
                MeadDetailsStepsDescription.setText(steps.get(listIndex-getIngredientsSize()).getShortDescription());
            }
            else {
                Corner.setVisibility(View.VISIBLE);
                StepsTitle.setVisibility(View.GONE);
                MeadDetailsStepsDescription.setText(steps.get(listIndex-getIngredientsSize()).getShortDescription());
            }

        }

        @Override
        public void onClick(View v) {
            int position=getAdapterPosition();
            mOnClickListener.onListItemClick(position);
        }

     }
}

