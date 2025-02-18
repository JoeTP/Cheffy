package com.example.cheffy.features.home.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cheffy.R;
import com.example.cheffy.repository.models.category.CategoryResponse;
import com.example.cheffy.repository.models.category.CategoryResponse.Category;
import com.example.cheffy.repository.models.ingredient.IngredientResponse;
import com.example.cheffy.repository.models.meal.MealsResponse;
import com.example.cheffy.utils.Flags;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeRecyclerAdapter<T> extends RecyclerView.Adapter<HomeRecyclerAdapter.ViewHolder> {
    private static final String TAG = "TEST";
    List<T> list;
    Context context;
    OnCardClick onCardClickListener;

    public HomeRecyclerAdapter(List<T> list, Context context, OnCardClick onCardClickListener) {
        this.list = list;
        this.context = context;
        this.onCardClickListener = onCardClickListener;
    }

    public void updateList(List<T> list) {
//        this.list.clear();
        this.list = list;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public HomeRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.home_card, parent, false);
        RecyclerView.ViewHolder vh = new ViewHolder(view);
        return (ViewHolder) vh;
    }

    @Override
    public void onBindViewHolder(@NonNull HomeRecyclerAdapter.ViewHolder holder, int position) {

        holder.layout.setOnClickListener(v -> onCardClickListener.onCardClick(list.get(position)));

        if (list.get(position) instanceof CategoryResponse.Category) {
            Category category = (CategoryResponse.Category) list.get(position);
            holder.bind(category.getStrCategoryThumb(), category.getStrCategory());
        } else if (list.get(position) instanceof MealsResponse.Meal) {
            MealsResponse.Meal meal = (MealsResponse.Meal) list.get(position);
            String flag = Flags.getFlagURL(meal.getStrArea());
                holder.bind(flag, meal.getStrArea());
        }else if(list.get(position) instanceof IngredientResponse.Meal){
            IngredientResponse.Meal ingredient = (IngredientResponse.Meal) list.get(position);
            String url = "https://www.themealdb.com/images/ingredients/"+ingredient.getStrIngredient()+
                    "-Small.png";
            holder.bind(url, ingredient.getStrIngredient());

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView imageView;
        TextView tvTitle;
        ConstraintLayout layout;
        View view;

        public void bind(String url, String title) {
            Glide.with(context)
                    .load(url)
                    .placeholder(R.drawable.testimg)
                    .into(imageView);
            tvTitle.setText(title);
        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            layout = itemView.findViewById(R.id.cardLayout);
            view = itemView;
        }
    }
}
