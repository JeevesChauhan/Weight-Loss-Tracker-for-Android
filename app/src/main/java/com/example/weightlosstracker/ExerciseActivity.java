package com.example.weightlosstracker;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ExerciseActivity extends AppCompatActivity {

    // Database Variable
    private DatabaseHelper databaseHelper;

    // Button Variables
    private Button add;
    private Button back;

    // EditText Variables
    private EditText name;
    private EditText sets;
    private EditText reps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        // Setting up the database
        databaseHelper = new DatabaseHelper(ExerciseActivity.this);

        name = (EditText)findViewById(R.id.exercise_name);
        sets = (EditText)findViewById(R.id.exercise_sets);
        reps = (EditText)findViewById(R.id.exercise_reps);

        add = (Button)findViewById(R.id.exercise_add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Input the data into database
                boolean isInserted = databaseHelper.workoutInsertData(name.getText().toString(), sets.getText().toString(), reps.getText().toString());

                // Toasting result
                if(isInserted) {
                    Toast.makeText(ExerciseActivity.this, "Data inserted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ExerciseActivity.this, "Data failed to insert", Toast.LENGTH_SHORT).show();
                }

                // Return to MainActivity
                Intent intent = new Intent(ExerciseActivity.this, add_Workout.class);
                startActivity(intent);
            }
        });


        // Setting up the back button to take the user back to MainActivity on moreFragment
        back = (Button)findViewById(R.id.exercise_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ExerciseActivity.this, add_Workout.class);
                startActivity(intent);
            }
        });
    }


}
