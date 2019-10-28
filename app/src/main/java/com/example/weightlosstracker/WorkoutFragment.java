package com.example.weightlosstracker;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

public class WorkoutFragment extends Fragment {

    // Database Variable
    private DatabaseHelper databaseHelper;

    // RecyclerView related Variables
    private ArrayList<WorkoutItem> workoutList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private WorkoutAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
        // This variable is important for not making it crash
    private View view;

    // Button Variables
    private Button add;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_workout, container, false);

        databaseHelper = new DatabaseHelper(getActivity());

        // Gets the data into the ArrayList
        databaseHelper.getDay();
        getTodaysWorkouts();

        // Builds the recyclerView
        buildRecyclerView();

        add = (Button)view.findViewById(R.id.workout_add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), add_Workout.class);
                startActivity(intent);
            }
        });


        return view;
    }

    /**
     * Remove from recyclerView
     * @param position
     */
    public void removeItem(int position) {
        // Delete from WBunch
        databaseHelper.routineRemoveRecord(workoutList.get(position).getId());
        // Delete from Recycler View
        workoutList.remove(position);
        mAdapter.notifyItemRemoved(position);
    }

    /**
     * Calls the database method to get todays routine
     */
    private void getTodaysWorkouts() {
        Cursor res = databaseHelper.routineGetTodaysWorkouts();
        // If nothing is on the list then res would be null
        if (res == null) {
            return;
        }

        // Goes through the Cursor and adds to the workoutList the values from the record
        while (res.moveToNext()) {
            int id = res.getInt(0);
            String name = res.getString(1);
            String sets = res.getString(2);
            String reps = res.getString(3);
            workoutList.add(new WorkoutItem(id, name, sets, reps));
        }
    }


    /**
     * Builds the recyclerView with the given Arraylist
     */
    private void buildRecyclerView() {
        mRecyclerView = view.findViewById(R.id.workout_recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new WorkoutAdapter(workoutList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new WorkoutAdapter.OnItemClickListner() {
            @Override
            public void onDeleteClick(int position) {
                removeItem(position);
            }
        });

    }
}
