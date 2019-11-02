package com.example.bakingapp.Adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.graphics.ColorUtils;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bakingapp.Models.Meal;
import com.example.bakingapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MealsAdapter extends RecyclerView.Adapter<MealsAdapter.MealViewHolder> {
     List<Meal>meals;
     List<String>imagesUrls=new ArrayList<>();
     Context context;
     final private ListItemClickListener mOnClickListener;


      public interface ListItemClickListener{
        void onListItemClick(int item);
    }

    public MealsAdapter(List<Meal>meals,ListItemClickListener listItemClickListener,Context context) {
        this.meals=meals;
        mOnClickListener= listItemClickListener;
        this.context=context;
        imagesUrls.add(0,"https://bakingamoment.com/wp-content/uploads/2015/03/4255featured2.jpg");
        imagesUrls.add(1,"https://food.fnr.sndimg.com/content/dam/images/food/fullset/2011/10/25/1/CC_Alton-Brown-Cocoa-Brownies_s4x3.jpg.rend.hgtvcom.826.620.suffix/1371600408087.jpeg");
        imagesUrls.add(2,"http://ksmartstatic.sify.com/cmf-1.0.0/appflow/bawarchi.com/Image/rdgqCphfdieei_bigger.jpg");
        imagesUrls.add(3,"https://food.fnr.sndimg.com/content/dam/images/food/fullset/2008/9/8/1/Cheesecake_Classic.jpg.rend.hgtvcom.826.620.suffix/1371587222109.jpeg");

    }


    @Override
    public MealViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
         LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(R.layout.meals_list_item, viewGroup, shouldAttachToParentImmediately);
        MealViewHolder viewHolder = new MealViewHolder(view);



        return viewHolder;
    }


    @Override
    public void onBindViewHolder(MealViewHolder holder, int position) {
        holder.bind(position);
    }


    @Override
    public int getItemCount() {
        return meals.size();
    }


    class MealViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

         TextView MealNameLabe;
         ImageView MealImage;


        public MealViewHolder(View itemView) {
            super(itemView);

            MealNameLabe = (TextView) itemView.findViewById(R.id.meal_main_name_label);
            MealImage = (ImageView) itemView.findViewById(R.id.meal_main_image_view);
            itemView.setOnClickListener(this);
         }


        void bind(int listIndex) {
            MealNameLabe.setText(meals.get(listIndex).getName());
            Picasso.with(context)
                    .load(imagesUrls.get(listIndex))
                    .into(MealImage);

        }

        @Override
        public void onClick(View v) {
            int position=getAdapterPosition();
            mOnClickListener.onListItemClick(position);
        }

     }
}

