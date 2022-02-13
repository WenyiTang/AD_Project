package com.example.adproject.helper;

public class MealEdit {

    private Integer id;
    public String mealTime;
    public String description;

    public MealEdit(Integer id, String mealTime, String description) {
        this.id = id;
        this.mealTime = mealTime;
        this.description = description;
    }


    public Integer getId() {
        return id;
    }

    public String getMealTime() {
        return mealTime;
    }

    public String getDescription() {
        return description;
    }
}
