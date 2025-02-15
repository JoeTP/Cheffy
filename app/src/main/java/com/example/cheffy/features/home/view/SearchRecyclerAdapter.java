package com.example.cheffy.features.home.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cheffy.R;
import com.example.cheffy.repository.models.meal.MealsResponse;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SearchRecyclerAdapter extends RecyclerView.Adapter<SearchRecyclerAdapter.ViewHolder> {
    List<MealsResponse.Meal> list;
    Context context;
    OnMealCardClick listener;

    public SearchRecyclerAdapter(List<MealsResponse.Meal> list, Context context,
                                 OnMealCardClick listener) {
        this.list = list;
        this.context = context;
        this.listener = listener;
    }

    public void updateList(List<MealsResponse.Meal> list) {
        this.list.clear();
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
        holder.layout.setOnClickListener(v -> listener.onCardClick(meal));
        holder.ivFavorite.setOnClickListener(v -> listener.onFavoriteClick(meal));
        Glide.with(context)
                .load(meal.getStrMealThumb())
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(holder.ivMeal);
        Glide.with(context)
                .load("THE COUNTRY URL HERE")
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(holder.ivCountry);
        holder.tvTitle.setText(meal.getStrMeal());
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

        public void bind(String url, String title) {
            Glide.with(context)
                    .load(url)
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(ivMeal);
            tvTitle.setText(title);
        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivMeal = itemView.findViewById(R.id.ivMeal);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            ivFavorite = itemView.findViewById(R.id.ivFavorite);
            ivCountry = itemView.findViewById(R.id.ivCountry);
            layout = itemView.findViewById(R.id.cardLayout);
            view = itemView;
        }
    }
}
