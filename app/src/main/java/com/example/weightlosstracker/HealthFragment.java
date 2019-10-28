package com.example.weightlosstracker;

import android.app.DownloadManager;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class HealthFragment extends Fragment {

    // Setting up the Database
    private DatabaseHelper databaseHelper;

    // Button Variables
    private Button btnWeight;
    private Button btnLog;

    // TextView Variables
    private TextView startingWeight;
    private TextView currentWeight;
    private TextView weightLoss;

    // Cursor Variables that will store data given from database
    private Cursor sWeight;
    private Cursor cWeight;

    // Variables for calculations
    private Double start;
    private Double current;
    private Double result;
    private Double weightDiff;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         View view = inflater.inflate(R.layout.fragment_health, container, false);

         // Set up the database for use
         databaseHelper = new DatabaseHelper(getActivity());

         startingWeight = (TextView)view.findViewById(R.id.health_starting_weight);
         currentWeight = (TextView)view.findViewById(R.id.health_current_weight);
         weightLoss = (TextView)view.findViewById(R.id.health_num_diff);

         // Updates the figures that interact with the
        if (databaseHelper.progressGetAllData().getCount() >0) {
            updateFigures();
        }


         // Setting up button and calling a method that is an onclick listener. For the sake easier reading
         btnLog = (Button)view.findViewById(R.id.health_log);
         viewLogs();

         // Setting up button which takes user to another activity
         btnWeight = (Button)view.findViewById(R.id.health_weight);
         btnWeight.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent(getActivity(), WeightActivity.class);
                 startActivity(intent);

             }
         });

        return view;
    }

    /**
     * Gets all the values within the progress table in the database
     * Sends values, along with title, in a StringBuffer to the showMessage() method
     * to display to the user in an alertDialog Box
     */
    public void viewLogs() {
        btnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = databaseHelper.progressGetAllData();
                // If no value then send a Toast and finish method
                if(res.getCount() == 0) {
                    Toast.makeText(getActivity(), "No current log values", Toast.LENGTH_SHORT).show();
                    return;
                }

                // buffer to store the values ready to display
                StringBuffer buffer = new StringBuffer();
                // going through and getting the string values
                while (res.moveToNext()) {
                    buffer.append("ID: " + res.getString(0) + "\n");
                    buffer.append("Weight: " + res.getString(1) + "\n");
                    buffer.append("Date: " + res.getString(2) + "\n\n");
                }
                // Calls the method to display results
                showMessage("Log", buffer.toString());
            }
        });
    }

    /**
     * Displays given values in an alertDialog box
     *
     * @param title
     * @param message
     */
    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    /**
     * By using the calculation variables we get and display the values from the database
     * Getting values for and then displaying:
     *  The Starting weight
     *  The Current weight
     *  The difference
     */
    public void updateFigures() {
        // Getting the value from the database and using moveToNext() on the result
        sWeight = databaseHelper.progressGetMinMaxData("Start");
        sWeight.moveToNext();
        cWeight = databaseHelper.progressGetMinMaxData("Current");
        cWeight.moveToNext();

        // Displaying the value on the appropriate TextViews
        startingWeight.setText(sWeight.getString(1));
        currentWeight.setText(cWeight.getString(1));

        // Getting the starting weight and current weight as doubles
        start = sWeight.getDouble(1);
        current = cWeight.getDouble(1);
        // Calculating the difference
        result = start - current;
        // Rounding up the result to 1 decimal place
        weightDiff = (double)Math.round(result * 10d)/10d;
        // Displaying the rounded up value
        weightLoss.setText(Double.toString(weightDiff));
    }

}
