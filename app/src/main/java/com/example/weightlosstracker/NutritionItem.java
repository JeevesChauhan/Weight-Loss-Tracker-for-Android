package com.example.weightlosstracker;

public class NutritionItem {

    // ID Variable (used for deletion and addition)
    private int id;

    // TextView Variables
    private String name;
    private String calories;

    // Simple Constructor
    public NutritionItem(int id, String name, String calories) {
        this.id = id;
        this.name = name;
        this.calories = calories;
    }

    /*
    Getters
     */

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getCalories() {
        return this.calories;
    }
}
