package com.example.cheffy.features.search.view;

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
import com.example.cheffy.repository.models.meal.MealsResponse;
import com.example.cheffy.utils.ConnectionChecker;
import com.example.cheffy.utils.Flags;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SearchRecyclerAdapter extends RecyclerView.Adapter<SearchRecyclerAdapter.ViewHolder> {
    List<MealsResponse.Meal> list;
    Context context;
    OnMealCardClick listener;

    public SearchRecyclerAdapter(List<MealsResponse.Meal> list, Context context, OnMealCardClick listener) {
        this.list = list;
        this.context = context;
        this.listener = listener;
    }

    public void updateList(List<MealsResponse.Meal> list) {
        this.list = list;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public SearchRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.meal_card, parent, false);
        RecyclerView.ViewHolder vh = new ViewHolder(view);
        return (ViewHolder) vh;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchRecyclerAdapter.ViewHolder holder, int position) {

        MealsResponse.Meal meal = list.get(position);
        String flag = Flags.getFlagURL(meal.getStrArea());
        Log.i("TEST", "onBindViewHolder: " + flag);

        Glide.with(context)
                .load(meal.getStrMealThumb())
                .placeholder(R.drawable.testimg)
                .into(holder.ivMeal);
        holder.layout.setOnClickListener(v -> {
            if(ConnectionChecker.isConnected(context)){
                listener.onCardClick(meal);
            }else {
                Toast.makeText(context, "No internet connection", Toast.LENGTH_SHORT).show();
            }
        });
        holder.tvTitle.setText(meal.getStrMeal());
        if (meal.getId() != null && !meal.getId().isEmpty()) {
            holder.ivFavorite.setImageResource(R.drawable.favorite_select);
            holder.ivFavorite.setVisibility(View.VISIBLE);
            holder.ivFavorite.setOnClickListener(v -> listener.onFavoriteClick(meal));
        } else {
            holder.ivFavorite.setImageResource(R.drawable.favorite_unselect);
            holder.ivFavorite.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView ivMeal;
        TextView tvTitle;
        ImageView ivFavorite;
        ImageView ivCountry;
        ConstraintLayout layout;
        View view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivMeal = itemView.findViewById(R.id.ivMeal);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            ivFavorite = itemView.findViewById(R.id.ivFavorite);
            layout = itemView.findViewById(R.id.mealCardLayout);
            view = itemView;
        }
    }
}
