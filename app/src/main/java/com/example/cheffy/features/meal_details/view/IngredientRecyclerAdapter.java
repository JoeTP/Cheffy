package com.example.cheffy.features.meal_details.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cheffy.R;
import com.example.cheffy.features.search.view.OnMealCardClick;
import com.example.cheffy.repository.models.meal.MealsResponse;
import com.example.cheffy.utils.ConnectionChecker;
import com.example.cheffy.utils.Flags;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class IngredientRecyclerAdapter extends RecyclerView.Adapter<IngredientRecyclerAdapter.ViewHolder> {
    List<MealsResponse.Measurement> list;
    Context context;

    public IngredientRecyclerAdapter(List<MealsResponse.Measurement> list, Context context) {
        this.list = list;
        this.context = context;
    }



    @NonNull
    @Override
    public IngredientRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.ingredient_card, parent, false);
        RecyclerView.ViewHolder vh = new ViewHolder(view);
        return (ViewHolder) vh;
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientRecyclerAdapter.ViewHolder holder, int position) {

        MealsResponse.Measurement ingredient = list.get(position);
        holder.tvIngredient.setText(ingredient.getIngredient());
        holder.tvMeasure.setText(ingredient.getMeasure());

        String url = "https://www.themealdb.com/images/ingredients/"+ingredient.getIngredient()+
                "-Small.png";
        Glide.with(context)
                .load(url)
                .placeholder(R.drawable.testimg)
                .into(holder.ivIngredient);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivIngredient;
        TextView tvIngredient, tvMeasure;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivIngredient = itemView.findViewById(R.id.ivIngredient);
            tvIngredient = itemView.findViewById(R.id.tvIngredient);
            tvMeasure = itemView.findViewById(R.id.tvMeasure);
        }
    }
}
