package com.example.weightlosstracker;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class NutritionAdapter extends RecyclerView.Adapter<NutritionAdapter.NutritionViewHolder> {

    private ArrayList<NutritionItem> mExampleList;
    private OnItemClickListner mListener;

    // Creating an interface to allow on clicks
    public interface OnItemClickListner {
        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListner listner) {
        mListener = listner;
    }

    public static class NutritionViewHolder extends RecyclerView.ViewHolder {

        // Variables
        public TextView name;
        public TextView calories;
        public ImageView delete;

        public NutritionViewHolder(@NonNull View itemView, final OnItemClickListner listner) {
            super(itemView);
            name = itemView.findViewById(R.id.item_nutrition_name);
            calories = itemView.findViewById(R.id.item_nutrition_calories);
            delete = itemView.findViewById(R.id.item_nutrition_delete);

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listner != null) {
                        int posiition = getAdapterPosition();
                        if (posiition != RecyclerView.NO_POSITION) {
                            listner.onDeleteClick(posiition);
                        }
                    }
                }
            });
        }
    }

    public NutritionAdapter(ArrayList<NutritionItem> nutritionList) {
        mExampleList = nutritionList;
    }

    @NonNull
    @Override
    public NutritionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.nutrition_item, parent, false);
        NutritionViewHolder evh = new NutritionViewHolder(v, mListener);
        return evh;
    }


    @Override
    public void onBindViewHolder(@NonNull NutritionViewHolder nutritionViewHolder, int position) {
        NutritionItem currentItem = mExampleList.get(position);

        nutritionViewHolder.name.setText(currentItem.getName());
        nutritionViewHolder.calories.setText(currentItem.getCalories());
    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }
}
