package com.example.weightlosstracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateFood extends AppCompatActivity {

    //Database Variable
    private DatabaseHelper databaseHelper;

    //EditText Variables
    private EditText name;
    private EditText calories;

    //Button Variables
    private Button create;
    private Button back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_food);

        databaseHelper = new DatabaseHelper(CreateFood.this);

        name = (EditText)findViewById(R.id.create_food_name);
        calories = (EditText)findViewById(R.id.create_food_calories);

        create = (Button)findViewById(R.id.create_food_create);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isInserted = databaseHelper.foodInsertData(name.getText().toString(), calories.getText().toString());
                // Return to MainActivity
                Intent intent = new Intent(CreateFood.this, MainActivity.class);
                databaseHelper.foodInsertData(name.getText().toString(), calories.getText().toString());


                intent.putExtra("Name", name.getText().toString());
                intent.putExtra("Calories", calories.getText().toString());
                intent.putExtra("State", "Nutrition");
                startActivity(intent);
            }
        });

        back = (Button)findViewById(R.id.create_food_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateFood.this, MainActivity.class);
                intent.putExtra("State", "Nutrition");
                startActivity(intent);
            }
        });
    }
}
