package com.example.weightlosstracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MoreFragment extends Fragment {

    //TextView Variables
    private TextView date;

    // Button Variables
    private Button btnSteps;

    // Variables for Calculations
    private Date c;
    private SimpleDateFormat df;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_more, container, false);

        date = (TextView)view.findViewById(R.id.more_date);

        // Getting today's date
        c = Calendar.getInstance().getTime();
        // Setting up the format
        df = new SimpleDateFormat("dd/MM/yyyy");
        // Displaying c within the df format given
        date.setText(df.format(c));

        // Setting up button that takes user to the StepActivity
        btnSteps = (Button)view.findViewById(R.id.more_steps);
        btnSteps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Uses this to switch to the other activity
                Intent intent = new Intent(getActivity(), StepActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
