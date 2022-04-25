package com.example.habitbuilder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // setting listener on "Settings" button
        Button settingsBtn = findViewById(R.id.settingsBtn);
        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //take user to settings page
                Intent nextIntent = new Intent(getApplicationContext(), Settings.class);
                startActivity(nextIntent);
            }
        });

        // setting listener on "Who am I" button
        Button identityBtn = findViewById(R.id.identityBtn);
        identityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //take user to identity page
                Intent nextIntent = new Intent(getApplicationContext(), Identity.class);
                startActivity(nextIntent);
            }
        });

        // setting listener on "Habits" button
        Button habitsBtn = findViewById(R.id.habitsBtn);
        habitsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //take user to habits page
                Intent nextIntent = new Intent(getApplicationContext(), Habits.class);
                startActivity(nextIntent);
            }
        });

        // setting listener on "Scorecard" button
        Button scorecardBtn = findViewById(R.id.scorecardBtn);
        scorecardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //take user to scorecard page
                Intent nextIntent = new Intent(getApplicationContext(), Scorecard.class);
                startActivity(nextIntent);
            }
        });

        // setting listener on "Tracker" button
        Button trackerBtn = findViewById(R.id.trackerBtn);
        trackerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //take user to settings page
                Intent nextIntent = new Intent(getApplicationContext(), Tracker.class);
                startActivity(nextIntent);
            }
        });
    }

    //disabling back button
    @Override
    public void onBackPressed() {}
}