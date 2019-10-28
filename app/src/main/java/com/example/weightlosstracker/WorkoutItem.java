package com.example.weightlosstracker;

public class WorkoutItem {

    // ID Variable (used for deletion and addition)
    private int id;

    // TextView Variables
    private String name;
    private String sets;
    private String reps;

    // Simple constructor
    public WorkoutItem(int id,String name, String sets, String reps) {
        this.id = id;
        this.name = name;
        this.sets = sets;
        this.reps = reps;
    }

    /*
    Getters
     */

    public String getName() {
        return this.name;
    }

    public String getSets() {
        return this.sets;
    }

    public String getReps() {
        return this.reps;
    }

    public int getId(){
        return this.id;
    }
}
