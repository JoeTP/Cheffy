package com.example.cheffy.repository.models.meal;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;

import com.example.cheffy.utils.AppStrings;

import java.util.ArrayList;
import java.util.List;

public class MealsResponse {
    List<Meal> meals;

    public List<Meal> getMeals() {
        return meals;
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }



    @Entity(tableName = AppStrings.MEAL_TABLE_NAME, primaryKeys = {"id", "idMeal"})
    public static class Meal implements Parcelable {


        @NonNull
        private String id;
        @NonNull
        private String idMeal;
        private String strMeal;
        private String strCategory;
        private String strArea;
        private String strInstructions;
        private String strMealThumb;
        private String strYoutube;
        private String strIngredient1;
        private String strIngredient2;
        private String strIngredient3;
        private String strIngredient4;
        private String strIngredient5;
        private String strIngredient6;
        private String strIngredient7;
        private String strIngredient8;
        private String strIngredient9;
        private String strIngredient10;
        private String strIngredient11;
        private String strIngredient12;
        private String strIngredient13;
        private String strIngredient14;
        private String strIngredient15;
        private String strIngredient16;
        private String strIngredient17;
        private String strIngredient18;
        private String strIngredient19;
        private String strIngredient20;
        private String strMeasure1;
        private String strMeasure2;
        private String strMeasure3;
        private String strMeasure4;
        private String strMeasure5;
        private String strMeasure6;
        private String strMeasure7;
        private String strMeasure8;
        private String strMeasure9;
        private String strMeasure10;
        private String strMeasure11;
        private String strMeasure12;
        private String strMeasure13;
        private String strMeasure14;
        private String strMeasure15;
        private String strMeasure16;
        private String strMeasure17;
        private String strMeasure18;
        private String strMeasure19;
        private String strMeasure20;
        private String strSource;
        private int isFavorite;
        private Long timestamp;

        protected Meal(Parcel in) {
            idMeal = in.readString();
            strMeal = in.readString();
            strCategory = in.readString();
            strArea = in.readString();
            strInstructions = in.readString();
            strMealThumb = in.readString();
            strYoutube = in.readString();
            strIngredient1 = in.readString();
            strIngredient2 = in.readString();
            strIngredient3 = in.readString();
            strIngredient4 = in.readString();
            strIngredient5 = in.readString();
            strIngredient6 = in.readString();
            strIngredient7 = in.readString();
            strIngredient8 = in.readString();
            strIngredient9 = in.readString();
            strIngredient10 = in.readString();
            strIngredient11 = in.readString();
            strIngredient12 = in.readString();
            strIngredient13 = in.readString();
            strIngredient14 = in.readString();
            strIngredient15 = in.readString();
            strIngredient16 = in.readString();
            strIngredient17 = in.readString();
            strIngredient18 = in.readString();
            strIngredient19 = in.readString();
            strIngredient20 = in.readString();
            strMeasure1 = in.readString();
            strMeasure2 = in.readString();
            strMeasure3 = in.readString();
            strMeasure4 = in.readString();
            strMeasure5 = in.readString();
            strMeasure6 = in.readString();
            strMeasure7 = in.readString();
            strMeasure8 = in.readString();
            strMeasure9 = in.readString();
            strMeasure10 = in.readString();
            strMeasure11 = in.readString();
            strMeasure12 = in.readString();
            strMeasure13 = in.readString();
            strMeasure14 = in.readString();
            strMeasure15 = in.readString();
            strMeasure16 = in.readString();
            strMeasure17 = in.readString();
            strMeasure18 = in.readString();
            strMeasure19 = in.readString();
            strMeasure20 = in.readString();
            strSource = in.readString();
        }

        public Meal() {
        }

        public Meal(
                @NonNull String id,
                @NonNull String idMeal,
                    String strMeal,
                    String strCategory,
                    String strArea,
                    String strInstructions,
                    String strMealThumb,
                    String strYoutube,
                    String strIngredient1,
                    String strIngredient2,
                    String strIngredient3,
                    String strIngredient4,
                    String strIngredient5,
                    String strIngredient6,
                    String strIngredient7,
                    String strIngredient8,
                    String strIngredient9,
                    String strIngredient10,
                    String strIngredient11,
                    String strIngredient12,
                    String strIngredient13,
                    String strIngredient14,
                    String strIngredient15,
                    String strIngredient16,
                    String strIngredient17,
                    String strIngredient18,
                    String strIngredient19,
                    String strIngredient20,
                    String strMeasure1,
                    String strMeasure2,
                    String strMeasure3,
                    String strMeasure4,
                    String strMeasure5,
                    String strMeasure6,
                    String strMeasure7,
                    String strMeasure8,
                    String strMeasure9,
                    String strMeasure10,
                    String strMeasure11,
                    String strMeasure12,
                    String strMeasure13,
                    String strMeasure14,
                    String strMeasure15,
                    String strMeasure16,
                    String strMeasure17,
                    String strMeasure18,
                    String strMeasure19,
                    String strMeasure20,
                    String strSource) {
            this.id = id;
            this.idMeal = idMeal;
            this.strMeal = strMeal;
            this.strCategory = strCategory;
            this.strArea = strArea;
            this.strInstructions = strInstructions;
            this.strMealThumb = strMealThumb;
            this.strYoutube = strYoutube;
            this.strIngredient1 = strIngredient1;
            this.strIngredient2 = strIngredient2;
            this.strIngredient3 = strIngredient3;
            this.strIngredient4 = strIngredient4;
            this.strIngredient5 = strIngredient5;
            this.strIngredient6 = strIngredient6;
            this.strIngredient7 = strIngredient7;
            this.strIngredient8 = strIngredient8;
            this.strIngredient9 = strIngredient9;
            this.strIngredient10 = strIngredient10;
            this.strIngredient11 = strIngredient11;
            this.strIngredient12 = strIngredient12;
            this.strIngredient13 = strIngredient13;
            this.strIngredient14 = strIngredient14;
            this.strIngredient15 = strIngredient15;
            this.strIngredient16 = strIngredient16;
            this.strIngredient17 = strIngredient17;
            this.strIngredient18 = strIngredient18;
            this.strIngredient19 = strIngredient19;
            this.strIngredient20 = strIngredient20;
            this.strMeasure1 = strMeasure1;
            this.strMeasure2 = strMeasure2;
            this.strMeasure3 = strMeasure3;
            this.strMeasure4 = strMeasure4;
            this.strMeasure5 = strMeasure5;
            this.strMeasure6 = strMeasure6;
            this.strMeasure7 = strMeasure7;
            this.strMeasure8 = strMeasure8;
            this.strMeasure9 = strMeasure9;
            this.strMeasure10 = strMeasure10;
            this.strMeasure11 = strMeasure11;
            this.strMeasure12 = strMeasure12;
            this.strMeasure13 = strMeasure13;
            this.strMeasure14 = strMeasure14;
            this.strMeasure15 = strMeasure15;
            this.strMeasure16 = strMeasure16;
            this.strMeasure17 = strMeasure17;
            this.strMeasure18 = strMeasure18;
            this.strMeasure19 = strMeasure19;
            this.strMeasure20 = strMeasure20;
            this.strSource = strSource;
        }

        @NonNull
        public String getId() {
            return id;
        }

        public void setId(@NonNull String id) {
            this.id = id;
        }

        public int getIsFavorite() {
            return isFavorite;
        }
        public void setIsFavorite(int isFavorite) {
            this.isFavorite = isFavorite;
        }

        public Long getTimestamp() {
            return timestamp;
        }
        public void setTimestamp(Long timestamp) {
            this.timestamp = timestamp;
        }

        public String getIdMeal() {
            return idMeal;
        }

        public void setIdMeal(String idMeal) {
            this.idMeal = idMeal;
        }

        public String getStrMeal() {
            return strMeal;
        }

        public void setStrMeal(String strMeal) {
            this.strMeal = strMeal;
        }

        public String getStrCategory() {
            return strCategory;
        }

        public void setStrCategory(String strCategory) {
            this.strCategory = strCategory;
        }

        public String getStrArea() {
            return strArea;
        }

        public void setStrArea(String strArea) {
            this.strArea = strArea;
        }

        public String getStrInstructions() {
            return strInstructions;
        }

        public void setStrInstructions(String strInstructions) {
            this.strInstructions = strInstructions;
        }

        public String getStrMealThumb() {
            return strMealThumb;
        }

        public void setStrMealThumb(String strMealThumb) {
            this.strMealThumb = strMealThumb;
        }

        public String getStrYoutube() {
            return strYoutube;
        }

        public void setStrYoutube(String strYoutube) {
            this.strYoutube = strYoutube;
        }

        public String getStrIngredient1() {
            return strIngredient1;
        }

        public void setStrIngredient1(String strIngredient1) {
            this.strIngredient1 = strIngredient1;
        }

        public String getStrIngredient2() {
            return strIngredient2;
        }

        public void setStrIngredient2(String strIngredient2) {
            this.strIngredient2 = strIngredient2;
        }

        public String getStrIngredient3() {
            return strIngredient3;
        }

        public void setStrIngredient3(String strIngredient3) {
            this.strIngredient3 = strIngredient3;
        }

        public String getStrIngredient4() {
            return strIngredient4;
        }

        public void setStrIngredient4(String strIngredient4) {
            this.strIngredient4 = strIngredient4;
        }

        public String getStrIngredient5() {
            return strIngredient5;
        }

        public void setStrIngredient5(String strIngredient5) {
            this.strIngredient5 = strIngredient5;
        }

        public String getStrIngredient6() {
            return strIngredient6;
        }

        public void setStrIngredient6(String strIngredient6) {
            this.strIngredient6 = strIngredient6;
        }

        public String getStrIngredient7() {
            return strIngredient7;
        }

        public void setStrIngredient7(String strIngredient7) {
            this.strIngredient7 = strIngredient7;
        }

        public String getStrIngredient8() {
            return strIngredient8;
        }

        public void setStrIngredient8(String strIngredient8) {
            this.strIngredient8 = strIngredient8;
        }

        public String getStrIngredient9() {
            return strIngredient9;
        }

        public void setStrIngredient9(String strIngredient9) {
            this.strIngredient9 = strIngredient9;
        }

        public String getStrIngredient10() {
            return strIngredient10;
        }

        public void setStrIngredient10(String strIngredient10) {
            this.strIngredient10 = strIngredient10;
        }

        public String getStrIngredient11() {
            return strIngredient11;
        }

        public void setStrIngredient11(String strIngredient11) {
            this.strIngredient11 = strIngredient11;
        }

        public String getStrIngredient12() {
            return strIngredient12;
        }

        public void setStrIngredient12(String strIngredient12) {
            this.strIngredient12 = strIngredient12;
        }

        public String getStrIngredient13() {
            return strIngredient13;
        }

        public void setStrIngredient13(String strIngredient13) {
            this.strIngredient13 = strIngredient13;
        }

        public String getStrIngredient14() {
            return strIngredient14;
        }

        public void setStrIngredient14(String strIngredient14) {
            this.strIngredient14 = strIngredient14;
        }

        public String getStrIngredient15() {
            return strIngredient15;
        }

        public void setStrIngredient15(String strIngredient15) {
            this.strIngredient15 = strIngredient15;
        }

        public String getStrIngredient16() {
            return strIngredient16;
        }

        public void setStrIngredient16(String strIngredient16) {
            this.strIngredient16 = strIngredient16;
        }

        public String getStrIngredient17() {
            return strIngredient17;
        }

        public void setStrIngredient17(String strIngredient17) {
            this.strIngredient17 = strIngredient17;
        }

        public String getStrIngredient18() {
            return strIngredient18;
        }

        public void setStrIngredient18(String strIngredient18) {
            this.strIngredient18 = strIngredient18;
        }

        public String getStrIngredient19() {
            return strIngredient19;
        }

        public void setStrIngredient19(String strIngredient19) {
            this.strIngredient19 = strIngredient19;
        }

        public String getStrIngredient20() {
            return strIngredient20;
        }

        public void setStrIngredient20(String strIngredient20) {
            this.strIngredient20 = strIngredient20;
        }

        public String getStrMeasure1() {
            return strMeasure1;
        }

        public void setStrMeasure1(String strMeasure1) {
            this.strMeasure1 = strMeasure1;
        }

        public String getStrMeasure2() {
            return strMeasure2;
        }

        public void setStrMeasure2(String strMeasure2) {
            this.strMeasure2 = strMeasure2;
        }

        public String getStrMeasure3() {
            return strMeasure3;
        }

        public void setStrMeasure3(String strMeasure3) {
            this.strMeasure3 = strMeasure3;
        }

        public String getStrMeasure4() {
            return strMeasure4;
        }

        public void setStrMeasure4(String strMeasure4) {
            this.strMeasure4 = strMeasure4;
        }

        public String getStrMeasure5() {
            return strMeasure5;
        }

        public void setStrMeasure5(String strMeasure5) {
            this.strMeasure5 = strMeasure5;
        }

        public String getStrMeasure6() {
            return strMeasure6;
        }

        public void setStrMeasure6(String strMeasure6) {
            this.strMeasure6 = strMeasure6;
        }

        public String getStrMeasure7() {
            return strMeasure7;
        }

        public void setStrMeasure7(String strMeasure7) {
            this.strMeasure7 = strMeasure7;
        }

        public String getStrMeasure8() {
            return strMeasure8;
        }

        public void setStrMeasure8(String strMeasure8) {
            this.strMeasure8 = strMeasure8;
        }

        public String getStrMeasure9() {
            return strMeasure9;
        }

        public void setStrMeasure9(String strMeasure9) {
            this.strMeasure9 = strMeasure9;
        }

        public String getStrMeasure10() {
            return strMeasure10;
        }

        public void setStrMeasure10(String strMeasure10) {
            this.strMeasure10 = strMeasure10;
        }

        public String getStrMeasure11() {
            return strMeasure11;
        }

        public void setStrMeasure11(String strMeasure11) {
            this.strMeasure11 = strMeasure11;
        }

        public String getStrMeasure12() {
            return strMeasure12;
        }

        public void setStrMeasure12(String strMeasure12) {
            this.strMeasure12 = strMeasure12;
        }

        public String getStrMeasure13() {
            return strMeasure13;
        }

        public void setStrMeasure13(String strMeasure13) {
            this.strMeasure13 = strMeasure13;
        }

        public String getStrMeasure14() {
            return strMeasure14;
        }

        public void setStrMeasure14(String strMeasure14) {
            this.strMeasure14 = strMeasure14;
        }

        public String getStrMeasure15() {
            return strMeasure15;
        }

        public void setStrMeasure15(String strMeasure15) {
            this.strMeasure15 = strMeasure15;
        }

        public String getStrMeasure16() {
            return strMeasure16;
        }

        public void setStrMeasure16(String strMeasure16) {
            this.strMeasure16 = strMeasure16;
        }

        public String getStrMeasure17() {
            return strMeasure17;
        }

        public void setStrMeasure17(String strMeasure17) {
            this.strMeasure17 = strMeasure17;
        }

        public String getStrMeasure18() {
            return strMeasure18;
        }

        public void setStrMeasure18(String strMeasure18) {
            this.strMeasure18 = strMeasure18;
        }

        public String getStrMeasure19() {
            return strMeasure19;
        }

        public void setStrMeasure19(String strMeasure19) {
            this.strMeasure19 = strMeasure19;
        }

        public String getStrMeasure20() {
            return strMeasure20;
        }

        public void setStrMeasure20(String strMeasure20) {
            this.strMeasure20 = strMeasure20;
        }

        public String getStrSource() {
            return strSource;
        }

        public void setStrSource(String strSource) {
            this.strSource = strSource;
        }
        public List<Measurement> listingIngredient() {
            List<Measurement> ingredients = new ArrayList<>();

            for (int i = 1; i <= 20; i++) {
                try {
                    String ingredient = (String) Meal.class.getDeclaredField("strIngredient" + i).get(this);
                    String measure = (String) Meal.class.getDeclaredField("strMeasure" + i).get(this);

                    if (ingredient != null && measure != null && !ingredient.isEmpty() && !measure.isEmpty()) {
                        ingredients.add(new Measurement(ingredient, measure));
                    }
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            return ingredients;
        }



        public static final Creator<Meal> CREATOR = new Creator<Meal>() {
            @Override
            public Meal createFromParcel(Parcel in) {
                return new Meal(in);
            }

            @Override
            public Meal[] newArray(int size) {
                return new Meal[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(@NonNull Parcel dest, int flags) {
            dest.writeString(this.idMeal);
            dest.writeString(this.strMeal);
            dest.writeString(this.strArea);
            dest.writeString(this.strCategory);
            dest.writeString(this.strInstructions);
            dest.writeString(this.strMealThumb);
            dest.writeString(this.strYoutube);
            dest.writeString(this.strIngredient1);
            dest.writeString(this.strIngredient2);
            dest.writeString(this.strIngredient3);
            dest.writeString(this.strIngredient4);
            dest.writeString(this.strIngredient5);
            dest.writeString(this.strIngredient6);
            dest.writeString(this.strIngredient7);
            dest.writeString(this.strIngredient8);
            dest.writeString(this.strIngredient9);
            dest.writeString(this.strIngredient10);
            dest.writeString(this.strIngredient11);
            dest.writeString(this.strIngredient12);
            dest.writeString(this.strIngredient13);
            dest.writeString(this.strIngredient14);
            dest.writeString(this.strIngredient15);
            dest.writeString(this.strIngredient16);
            dest.writeString(this.strIngredient17);
            dest.writeString(this.strIngredient18);
            dest.writeString(this.strIngredient19);
            dest.writeString(this.strIngredient20);
            dest.writeString(this.strMeasure1);
            dest.writeString(this.strMeasure2);
            dest.writeString(this.strMeasure3);
            dest.writeString(this.strMeasure4);
            dest.writeString(this.strMeasure5);
            dest.writeString(this.strMeasure6);
            dest.writeString(this.strMeasure7);
            dest.writeString(this.strMeasure8);
            dest.writeString(this.strMeasure9);
            dest.writeString(this.strMeasure10);
            dest.writeString(this.strMeasure11);
            dest.writeString(this.strMeasure12);
            dest.writeString(this.strMeasure13);
            dest.writeString(this.strMeasure14);
            dest.writeString(this.strMeasure15);
            dest.writeString(this.strMeasure16);
            dest.writeString(this.strMeasure17);
            dest.writeString(this.strMeasure18);
            dest.writeString(this.strMeasure19);
            dest.writeString(this.strMeasure20);
            dest.writeString(this.strSource);
        }
    }



    public static class Measurement {
        String ingredient;
        String measure;

        public Measurement(String ingredient, String measure) {
            this.ingredient = ingredient;
            this.measure = measure;
        }

        public String getMeasure() {
            return measure;
        }

        public String getIngredient() {
            return ingredient;
        }
    }
}
