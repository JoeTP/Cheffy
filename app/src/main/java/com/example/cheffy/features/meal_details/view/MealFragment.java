package com.example.cheffy.features.meal_details.view;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.example.cheffy.R;
import com.example.cheffy.features.meal_details.contract.MealContract;
import com.example.cheffy.features.meal_details.presenter.MealPresenter;
import com.example.cheffy.repository.database.meal.MealsLocalSourceImpl;
import com.example.cheffy.repository.models.meal.MealsResponse;
import com.example.cheffy.repository.MealDataRepositoryImpl;
import com.example.cheffy.repository.network.meal.MealsRemoteSourceImpl;
import com.example.cheffy.utils.Flags;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;


public class MealFragment extends Fragment implements MealContract.View {

    private static final String TAG = "TEST";
    MealPresenter presenter;
    ImageView btnBack;
    ImageView ivMeal;
    ImageView ivFlag;
    TextView tvMealTitle;
    TextView tvInstructions;
    YouTubePlayerView videoView;
    ImageView btnFavorite;
    MealsResponse.Meal mealArg;
    MealsResponse.Meal fullMeal;

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (videoView != null) {
            videoView.release();
        }
    }

    private void initUI(View view) {
        btnBack = view.findViewById(R.id.btnBack);
        ivMeal = view.findViewById(R.id.ivMeal);
        tvMealTitle = view.findViewById(R.id.tvMealTitle);
        tvInstructions = view.findViewById(R.id.tvInstructions);
        videoView = view.findViewById(R.id.videoView);
        btnFavorite = view.findViewById(R.id.btnFavorite);
        ivFlag = view.findViewById(R.id.ivFlag);
    }

    private void setUI(MealsResponse.Meal meal) {
        Glide.with(getContext()).load(meal.getStrMealThumb()).into(ivMeal);
        Glide.with(getContext()).load(Flags.getFlagURL(meal.getStrArea())).into(ivFlag);
        tvMealTitle.setText(meal.getStrMeal());
        tvInstructions.setText(meal.getStrInstructions());
        getLifecycle().addObserver(videoView);
        videoView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                super.onReady(youTubePlayer);
                if (meal.getStrYoutube() != null && !meal.getStrYoutube().isEmpty()) {
                    String videoId = meal.getStrYoutube().split("=")[1];
                    Log.i(TAG, "YOUTUBE ID: " + videoId);
                    youTubePlayer.cueVideo(videoId, 0);
                }
            }
        });

        btnFavorite.setOnClickListener(v -> {
            if(meal.getIsFavorite() == 1){
                meal.setIsFavorite(0);
                presenter.unfavorite(meal.getIdMeal());
                btnFavorite.setImageResource(R.drawable.favorite_unselect);
            }else{
                meal.setIsFavorite(1);
                presenter.addToFavorite(meal);
                btnFavorite.setImageResource(R.drawable.favorite_select);
            }
        });
    }


    public MealFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meal, container, false);
        initUI(view);
        mealArg = MealFragmentArgs.fromBundle(getArguments()).getMeal();

        presenter = new MealPresenter(this, getMealRepositoryInstance(getContext()));

        presenter.searchForMealById(mealArg.getIdMeal())
                .subscribe(meals -> {
                    fullMeal = meals.get(0);
                    Log.i(TAG, "MEAL ID: " + mealArg.getIdMeal());
                    String formatedInstructions = formatText(fullMeal.getStrInstructions());
                    fullMeal.setStrInstructions(formatedInstructions);
                    setUI(fullMeal);
                });

        btnBack.setOnClickListener(v -> Navigation.findNavController(v).navigateUp());

        return view;
    }

    private String formatText(String inputText) {
        inputText = inputText.replace("\r\n", "\n");
        if (inputText.endsWith(".")) {
            inputText.substring(0, inputText.length() - 1);
        }
        String textWithNewLines = inputText.replace(".", ".\n");
        String[] lines = textWithNewLines.split("\n");
        StringBuilder formattedText = new StringBuilder();
        for (String line : lines) {
            if (!line.trim().isEmpty()) {
                formattedText.append("• ").append(line.trim()).append("\n");
            }
        }
        return formattedText.toString();
    }

    private MealDataRepositoryImpl getMealRepositoryInstance(Context context) {
        return MealDataRepositoryImpl.getInstance(MealsRemoteSourceImpl.getInstance(), MealsLocalSourceImpl.getInstance(context));
    }
}