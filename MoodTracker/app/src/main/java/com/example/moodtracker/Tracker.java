package com.example.moodtracker;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Fragment;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Tracker extends AppCompatActivity {

    private String username = ""; // variable to store username
    private int mood = -1; // variable to store mood
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracker);
        username = getIntent().getStringExtra("UNAME");
    }

    // method to add a fragement
    private void addFragment(Fragment fragment){
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.medBtnGrp, fragment);
        ft.commit();
        // TODO use addFragment(new Medication()); somewhere
    }

    /**
     * the following 5 methods are onClick methods for the mood image buttons
     */

    public void setVHappyMood(View view) {
        // set img as selected
        ((ImageButton) findViewById(R.id.vhappyBtn)).setImageResource(R.drawable.vhappy);

        // set img of all else to not selected
        ((ImageButton) findViewById(R.id.vsadBtn)).setImageResource(R.drawable.d_vsad);
        ((ImageButton) findViewById(R.id.sadBtn)).setImageResource(R.drawable.d_sad);
        ((ImageButton) findViewById(R.id.neutralBtn)).setImageResource(R.drawable.d_neutral);
        ((ImageButton) findViewById(R.id.happyBtn)).setImageResource(R.drawable.d_happy);

        // update mood value
        mood = 4;
    }

    public void setHappyMood(View view) {
        // set img as selected
        ((ImageButton) findViewById(R.id.happyBtn)).setImageResource(R.drawable.happy);

        // set img of all else to not selected
        ((ImageButton) findViewById(R.id.vsadBtn)).setImageResource(R.drawable.d_vsad);
        ((ImageButton) findViewById(R.id.sadBtn)).setImageResource(R.drawable.d_sad);
        ((ImageButton) findViewById(R.id.neutralBtn)).setImageResource(R.drawable.d_neutral);
        ((ImageButton) findViewById(R.id.vhappyBtn)).setImageResource(R.drawable.d_vhappy);

        // update mood value
        mood = 4;
    }

    public void setNeutralMood(View view) {
        // set img as selected
        ((ImageButton) findViewById(R.id.neutralBtn)).setImageResource(R.drawable.neutral);

        // set img of all else to not selected
        ((ImageButton) findViewById(R.id.vsadBtn)).setImageResource(R.drawable.d_vsad);
        ((ImageButton) findViewById(R.id.sadBtn)).setImageResource(R.drawable.d_sad);
        ((ImageButton) findViewById(R.id.happyBtn)).setImageResource(R.drawable.d_happy);
        ((ImageButton) findViewById(R.id.vhappyBtn)).setImageResource(R.drawable.d_vhappy);

        // update mood value
        mood = 4;
    }

    public void setSadMood(View view) {
        // set img as selected
        ((ImageButton) findViewById(R.id.sadBtn)).setImageResource(R.drawable.sad);

        // set img of all else to not selected
        ((ImageButton) findViewById(R.id.vsadBtn)).setImageResource(R.drawable.d_vsad);
        ((ImageButton) findViewById(R.id.neutralBtn)).setImageResource(R.drawable.d_neutral);
        ((ImageButton) findViewById(R.id.happyBtn)).setImageResource(R.drawable.d_happy);
        ((ImageButton) findViewById(R.id.vhappyBtn)).setImageResource(R.drawable.d_vhappy);

        // update mood value
        mood = 4;
    }

    public void setVSadMood(View view) {
        // set img as selected
        ((ImageButton) findViewById(R.id.vsadBtn)).setImageResource(R.drawable.vsad);

        // set img of all else to not selected
        ((ImageButton) findViewById(R.id.sadBtn)).setImageResource(R.drawable.d_sad);
        ((ImageButton) findViewById(R.id.neutralBtn)).setImageResource(R.drawable.d_neutral);
        ((ImageButton) findViewById(R.id.happyBtn)).setImageResource(R.drawable.d_happy);
        ((ImageButton) findViewById(R.id.vhappyBtn)).setImageResource(R.drawable.d_vhappy);

        // update mood value
        mood = 4;
    }

}