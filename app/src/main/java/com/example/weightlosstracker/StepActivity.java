package com.example.weightlosstracker;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class StepActivity extends AppCompatActivity implements SensorEventListener {

     // Variables
    private Button btnBack;

    private SensorManager mSensorManager;
    private TextView counterTxt;

    // These variables are used to set the step counter
    private float initialSteps;
    private float steps;
    private boolean b = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);

        counterTxt = (TextView) findViewById(R.id.step_counter);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        /**
         * Setting up button and making it take us back to the previous activity
         */
        btnBack = (Button)findViewById(R.id.step_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                // Using this to tell MainActivity to open on the More fragment
                intent.putExtra("State", "More");
                startActivity(intent);
            }
        });
    }

    /**
     * Updates the text on the screen when the listener detects changes to the step counter sensor
     *
     * The way that the step counter works is that it is always running and only resets on device restart.
     * This method, before displaying steps, calculates how many steps there have been since the first execution of this method
     *
     * @param event
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
        // This if statement executes once during an activity life
        // Finds the value that the step counter is already on and sets that to initalSteps
        if(b) {
            initialSteps = event.values[0];
            b = false;
        }
        // steps is equal to the total steps minus the total steps before launching this activity
        steps = event.values[0] - initialSteps;
        counterTxt.setText(String.valueOf(Math.round(steps)));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    /**
     * When starting we check for the sensor and if we have it we set the listner on it, else we Toast to the user that they don't have the sensor
     */
    @Override
    protected void onStart() {
        super.onStart();
        Sensor countSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if(countSensor != null) {
            mSensorManager.registerListener(this, countSensor, mSensorManager.SENSOR_DELAY_UI);

        } else {
            Toast.makeText(this, "Sensor not found", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * When we leave this activity we stop listening to save battery
     */
    @Override
    protected void onStop() {
        super.onStop();
        mSensorManager.unregisterListener(this);
    }
}
