package com.example.weightlosstracker;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class add_Workout extends AppCompatActivity {

    // Database Variable
    private DatabaseHelper databaseHelper;

    // RecyclerView related Variables
    private ArrayList<WorkoutItem> workoutList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private AddWorkoutAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    // Button Variables
    private Button back;
    private Button create;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__workout);

        databaseHelper = new DatabaseHelper(this);

        /**
         * These two methods fills the arraylist with everything in the database from TABLE_WORKOUTS
         * And then displays the results into a Recycler view for the user to select
         */
        getAllWorkouts();
        buildRecyclerView();

        create = (Button)findViewById(R.id.add_workout_create);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(add_Workout.this, ExerciseActivity.class);
                startActivity(intent);
            }
        });

        back = (Button)findViewById(R.id.add_workout_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(add_Workout.this, MainActivity.class);
                intent.putExtra("State", "Workout");
                startActivity(intent);
            }
        });
    }

    /**
     * Get all workouts from database
     * Put into an ArrayList for the RecyclerView to display
     */
    private void getAllWorkouts() {
        Cursor res = databaseHelper.workoutGetAllData();
        if (res.getCount() == 0) {
            workoutList.add(new WorkoutItem(0,"No Workout", "0", "0"));
        }
        while (res.moveToNext()) {
            int id = res.getInt(0);
            String name = res.getString(1);
            String sets = res.getString(2);
            String reps = res.getString(3);
            workoutList.add(new WorkoutItem(id, name, sets, reps));
        }
    }

    /**
     * Puts the selected item into the Database
     * Takes the User back to the MainActivity
     */
    private void addWorkout(int position) {
        databaseHelper.routineAddTodayRecords(workoutList.get(position).getId());
        Intent intent = new Intent(add_Workout.this, MainActivity.class);
        intent.putExtra("State", "Workout");
        startActivity(intent);
    }


    /**
     * Create and setup teh Recycler view
     * As well as getting the onclick method for the Item
     */
    private void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.add_workout_recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new AddWorkoutAdapter(workoutList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new AddWorkoutAdapter.OnItemClickListner() {
            @Override
            public void onItemClick(int position) {
                addWorkout(position);
            }
        });

    }
}
