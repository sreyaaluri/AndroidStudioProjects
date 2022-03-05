package com.example.moodtracker;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Fragment;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Tracker extends AppCompatActivity {

    private String username = ""; // variable to store username
    private int mood = -1; // variable to store mood
    private int medication = -1; // variable to store mood
    private int medTime = -2; // variable to store mood
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracker);

        //logout button
        Button logoutBtn = findViewById(R.id.logoutBtn);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loggedoutIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(loggedoutIntent);
            }
        });

        username = getIntent().getStringExtra("UNAME");

        // adding on click listener to submit button
        Button submitBtn = findViewById(R.id.submitBtn);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                // retrieving date and user-entered data
                String date = java.time.LocalDate.now().toString();
                int anxiety = ((SeekBar) (findViewById(R.id.anxietyBar))).getProgress();

                // mood, and medication in public variables

                // If no user-entered data -> don't execute rest of code
                if((anxiety==0 && mood==-1 && medication==-1 && medTime==-2) || (medication==1 && medTime==-2)) {
                    TextView msg = findViewById(R.id.errorMsgLbl);
                    msg.setText("Please log all information before submitting");
                    return;
                }

                // adding to database
                DBClass db = DBClass.getDBInstance(Tracker.this);
                db.addTrackerData(username, date, mood, anxiety, medication, medTime);

                // clear form
                clearForm();
            }
        });
    }

    // method to add a fragment
    private void addFragment(Fragment fragment, String tag){
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment f = fm.findFragmentByTag(tag);
        if(f==null) {
            ft.add(R.id.medBtnGrp, fragment, tag);
            ft.commit();
        }
    }

    // method to remove a fragment given tag if exists
    private void removeFragment(String tag) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment f = fm.findFragmentByTag(tag);
        if(f!=null) {
            ft.remove(fm.findFragmentByTag(tag));
            ft.commit();
        }
    }

    private void clearForm() {
        // reset mood
        ((ImageButton) findViewById(R.id.vsadBtn)).setImageResource(R.drawable.vsad);
        ((ImageButton) findViewById(R.id.sadBtn)).setImageResource(R.drawable.sad);
        ((ImageButton) findViewById(R.id.neutralBtn)).setImageResource(R.drawable.neutral);
        ((ImageButton) findViewById(R.id.happyBtn)).setImageResource(R.drawable.happy);
        ((ImageButton) findViewById(R.id.vhappyBtn)).setImageResource(R.drawable.vhappy);

        // reset anxiety
        ((SeekBar) (findViewById(R.id.anxietyBar))).setProgress(0);

        // reset medication
        ((RadioGroup) findViewById(R.id.medBtnGrp)).clearCheck();

        //reset medication time
        if(medication == 1) {
            ((RadioGroup) findViewById(R.id.medTimeBtnGrp)).clearCheck();
            removeFragment("MED_TIMING");
        }

        // reset error msg to show nothing
        ((TextView) (findViewById(R.id.errorMsgLbl))).setText("");

        // reset public variable
        mood = -1;
        medication = -1;
        medTime= -2;
    }

    // toggling medication value based on radio buttons selected
    public void toggleMed(View view) {
        if(view.getId() == R.id.yesRB) {
            medication = 1;
            addFragment(new Medication(), "MED_TIMING");
        }
        else{
            medication = 0;
            removeFragment("MED_TIMING");
        }
    }

    public void toggleMedTime(View view){
        switch(view.getId()) {
            case R.id.yesTimeRB:
                medTime = 1;
                break;
            case R.id.noTimeRB:
                medTime = 0;
                break;
            default:
                medTime = -1;
                break;
        }
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
        mood = 3;
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
        mood = 2;
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
        mood = 1;
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
        mood = 0;
    }

}