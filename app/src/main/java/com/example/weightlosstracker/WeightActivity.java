package com.example.weightlosstracker;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class WeightActivity extends AppCompatActivity {

    // Database Variable
    private DatabaseHelper databaseHelper;

    // Button Variables
    private Button btnAddLog;
    private Button btnRemoveLog;
    private Button btnBack;

    // EditText Variables
    private EditText textAdd;
    private EditText textRemove;

    // Variables for calculations
    private Date c;
    private SimpleDateFormat df;
    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight);

        // Set up the database for use
        databaseHelper = new DatabaseHelper(this);

        textAdd = (EditText)findViewById(R.id.weight_kg);
        textRemove = (EditText)findViewById(R.id.weight_id);

        // Get the date
        getDate();

        // Back button
        btnBack = (Button)findViewById(R.id.weight_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WeightActivity.this, MainActivity.class);
                intent.putExtra("State", "Health");
                startActivity(intent);
            }
        });

        /**
         * Creating the add button which inputs the EditText of weight_kg into the database to create a new record
         */
        btnAddLog = (Button)findViewById(R.id.weight_add);
        btnAddLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isInserted = databaseHelper.progressInsertData(textAdd.getText().toString(), date);
                // Toasting result
                if(isInserted) {
                    Toast.makeText(WeightActivity.this, "Data inserted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(WeightActivity.this, "Data failed to insert", Toast.LENGTH_SHORT).show();
                }

                // Going back to MainActivity
                Intent intent = new Intent(WeightActivity.this, MainActivity.class);
                intent.putExtra("State", "Health");
                startActivity(intent);
            }
        });

        /**
         * Creating the remove button that inputs the EditText of weight_id to remove the corresponding ID from the database
         */
        btnRemoveLog = (Button)findViewById(R.id.weight_remove);
        btnRemoveLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer deletedRows = databaseHelper.progressRemoveData(textRemove.getText().toString());
                // Toasting the result
                if (deletedRows > 0) {
                    Toast.makeText(WeightActivity.this, "Data deleted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(WeightActivity.this, "Deletion falied", Toast.LENGTH_SHORT).show();
                }

                // Going back to MainActivity
                Intent intent = new Intent(WeightActivity.this, MainActivity.class);
                intent.putExtra("State", "Health");
                startActivity(intent);
            }
        });
    }

    /**
     * Get Date method
     * Gets the time and formats in a dd/MM/yyyy format
     */
    private void getDate() {
        c = Calendar.getInstance().getTime();
        df = new SimpleDateFormat("dd/MM/yyyy");
        date = df.format(c);
    }
}
