package com.example.weightlosstracker;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class WorkoutAdapter extends RecyclerView.Adapter<WorkoutAdapter.WorkoutViewHolder> {

    private ArrayList<WorkoutItem> mExampleList;
    private OnItemClickListner mListener;

    // Creating an interface to allow on clicks
    public interface OnItemClickListner {
        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListner listner) {
        mListener = listner;
    }

    public static class WorkoutViewHolder extends RecyclerView.ViewHolder {

        // Variables
        public TextView name;
        public TextView sets;
        public TextView reps;
        public ImageView delete;

        public WorkoutViewHolder(@NonNull View itemView, final OnItemClickListner listner) {
            super(itemView);
            name = itemView.findViewById(R.id.item_workout_name);
            sets = itemView.findViewById(R.id.item_workout_reps);
            reps = itemView.findViewById(R.id.item_workout_sets);
            delete = itemView.findViewById(R.id.item_workout_delete);

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

    public WorkoutAdapter(ArrayList<WorkoutItem> workoutList) {
        mExampleList = workoutList;
    }

    @NonNull
    @Override
    public WorkoutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.workout_item, parent, false);
        WorkoutViewHolder evh = new WorkoutViewHolder(v, mListener);
        return evh;
    }


    @Override
    public void onBindViewHolder(@NonNull WorkoutViewHolder workoutViewHolder, int position) {
        WorkoutItem currentItem = mExampleList.get(position);

        workoutViewHolder.name.setText(currentItem.getName());
        workoutViewHolder.sets.setText(currentItem.getSets());
        workoutViewHolder.reps.setText(currentItem.getReps());
    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }
}
