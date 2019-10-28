package com.example.weightlosstracker;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // Database Variable
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelper(this);
        databaseHelper.checkDay();

        // Setting up the Navigation
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListner);
        // Instantly display the Nutrition fragment on load if not called by back
        setNavigation(bottomNav);


    }

    // The listener that updates the bottom navigation and changes to the right fragment
    private BottomNavigationView.OnNavigationItemSelectedListener navListner =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.nav_nutrition:
                            selectedFragment = new NutritionFragment();
                            break;
                        case R.id.nav_workout:
                            selectedFragment = new WorkoutFragment();
                            break;
                        case R.id.nav_health:
                            selectedFragment = new HealthFragment();
                            break;
                        case R.id.nav_more:
                            selectedFragment = new MoreFragment();
                            break;
                    }
                    // Displays the fragment
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_containter,
                            selectedFragment).commit();

                    return true;
                }
            };

    /**
     * Determines what fragment to open on.
     * Depending on what getStringExtra() returns determines what fragment
     * @param bottomNav
     */
    private void setNavigation(BottomNavigationView bottomNav) {
        // If this is the first time launching the app then just open like normal
        if (getIntent().getExtras() == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_containter, new NutritionFragment()).commit();
            return;
        }

        // Getting the intent and using it for the argument of the if statement
        Intent intent = getIntent();

        switch (intent.getStringExtra("State")) {
            case "Nutrition":
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_containter, new NutritionFragment()).commit();
                // This sets the selected icon to the More fragment icon
                bottomNav.setSelectedItemId(R.id.nav_nutrition);
                break;
            case "Workout":
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_containter, new WorkoutFragment()).commit();
                // This sets the selected icon to the More fragment icon
                bottomNav.setSelectedItemId(R.id.nav_workout);
                break;
            case "Health":
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_containter, new HealthFragment()).commit();
                // This sets the selected icon to the More fragment icon
                bottomNav.setSelectedItemId(R.id.nav_health);
                break;
            case "More":
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_containter, new MoreFragment()).commit();
                // This sets the selected icon to the More fragment icon
                bottomNav.setSelectedItemId(R.id.nav_more);
                break;
        }

    }
}
