package com.example.cheffy.features.calendar.view;

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
import com.example.cheffy.features.search.view.OnMealCardClick;
import com.example.cheffy.repository.models.meal.MealsResponse;
import com.example.cheffy.repository.models.plan.PlanModel;
import com.example.cheffy.utils.Flags;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.ViewHolder> {

    List<PlanModel> list;
    Context context;
    OnMealCardClick listener;

    public CalendarAdapter(List<PlanModel> list, Context context, OnMealCardClick listener) {
        this.list = list;
        this.context = context;
        this.listener = listener;
    }

    void updateList(List<PlanModel> newList){
        this.list.clear();
        this.list.addAll(newList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.meal_card, parent, false);
        RecyclerView.ViewHolder vh = new ViewHolder(view);
        return (ViewHolder) vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        MealsResponse.Meal meal = list.get(position).getMeal();
        String flag = Flags.getFlagURL(meal.getStrArea());

        Glide.with(context)
                .load(meal.getStrMealThumb())
                .placeholder(R.drawable.testimg)
                .into(holder.ivMeal);
        holder.layout.setOnClickListener(v -> listener.onCardClick(meal));
        holder.tvTitle.setText(meal.getStrMeal());
        holder.ivFavorite.setVisibility(View.GONE);
        if (list.get(position).getDate() != null || !list.get(position).getDate().isEmpty()){
            holder.btnRemovePlan.setVisibility(View.VISIBLE);
            holder.btnRemovePlan.setOnClickListener(v -> listener.onRemovePlanClick(list.get(position)));
        }
//        if (meal.getId() != null && !meal.getId().isEmpty()) {
//            holder.ivFavorite.setImageResource(R.drawable.favorite_select);
//            holder.ivFavorite.setVisibility(View.VISIBLE);
//            holder.ivFavorite.setOnClickListener(v -> listener.onFavoriteClick(meal));
//        } else {
//            holder.ivFavorite.setImageResource(R.drawable.favorite_unselect);
//            holder.ivFavorite.setVisibility(View.GONE);
//        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView ivMeal;
        TextView tvTitle;
        ImageView ivFavorite;
        ImageView btnRemovePlan;
        ConstraintLayout layout;
        View view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivMeal = itemView.findViewById(R.id.ivMeal);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            ivFavorite = itemView.findViewById(R.id.ivFavorite);
            btnRemovePlan = itemView.findViewById(R.id.btnRemovePlan);
            layout = itemView.findViewById(R.id.mealCardLayout);
            view = itemView;
        }
    }
}

