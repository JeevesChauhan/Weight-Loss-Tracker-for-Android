package com.example.weightlosstracker;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class NutritionFragment extends Fragment {

    // Database Variable
    private DatabaseHelper databaseHelper;

    // RecyclerView related Variables
    private ArrayList<NutritionItem> nutritionList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private NutritionAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    // This variable is important for not making it crash
    private View view;

    // Button Variables
    private Button add;

    // TextView Variables
    private TextView total;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_nutrition, container, false);

        databaseHelper = new DatabaseHelper(getContext());

        total = (TextView)view.findViewById(R.id.nutrition_total);


        // Gets the data into the ArrayList
        databaseHelper.getDay();
        nutritionList.add(new NutritionItem(1, "Chicken thights", "300"));
        nutritionList.add(new NutritionItem(1, "Snickers", "250"));
        nutritionList.add(new NutritionItem(1, "Spicy Chicken wings", "520"));
        nutritionList.add(new NutritionItem(1, "Monster energy drink", "400"));

        // Builds the recyclerView
        buildRecyclerView();
        total.setText((String.valueOf(calculateTotalCalories())));

        add = (Button)view.findViewById(R.id.nutrition_add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CreateFood.class);
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
        databaseHelper.nutritionRemoveRecord(nutritionList.get(position).getId());
        // Delete from Recycler View
        nutritionList.remove(position);
        mAdapter.notifyItemRemoved(position);
    }

    /**
     * Calls the database method to get todays routine
     */
    private void getTodaysNutrition() {
        Cursor res = databaseHelper.nutritionGetTodaysFood();
        // If nothing is on the list then res would be null
        if (res == null) {
            return;
        }

        // Goes through the Cursor and adds to the workoutList the values from the record
        while (res.moveToNext()) {
            int id = res.getInt(0);
            String name = res.getString(1);
            String calories = res.getString(2);
            nutritionList.add(new NutritionItem(id, name, calories));
        }
    }


    /**
     * Builds the recyclerView with the given Arraylist
     */
    private void buildRecyclerView() {
        mRecyclerView = view.findViewById(R.id.nutrition_recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new NutritionAdapter(nutritionList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new NutritionAdapter.OnItemClickListner() {
            @Override
            public void onDeleteClick(int position) {
                removeItem(position);
            }
        });

    }

    private int calculateTotalCalories() {
        int total = 0;
        for (NutritionItem n : nutritionList) {
            total += Integer.parseInt(n.getCalories());
        }
        return total;
    }
}
