package com.example.habitbuilder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class AddActivity extends AppCompatActivity {

    String uname = "";
    String[] rating = {"Bad","Neutral","Good"};
    String purpose = "new";
    String selectedActivityName="";
    String selectedDateTxt = "";
    Calendar cal = Calendar.getInstance();
    DBClass db; // getting database instance

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_activity);
        db = DBClass.getDBInstance(getApplicationContext());

        // getting values from intent and shared preferences
        uname = getSharedPreferences("UserInfo", MODE_PRIVATE).getString("username", "No User");
        purpose = getIntent().getStringExtra("PURPOSE");

        // setting listener on header label
        TextView homeView = findViewById(R.id.headLbl);
        homeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // take user to home page
                Intent homeIntent = new Intent(getApplicationContext(), Home.class);
                startActivity(homeIntent);
            }
        });

        // setting listener on "Settings" button
        Button settingsBtn = findViewById(R.id.settingsBtn);
        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // take user to settings page
                Intent nextIntent = new Intent(getApplicationContext(), Settings.class);
                startActivity(nextIntent);
            }
        });

        // adding spinner options
        Spinner ratingSpinner = findViewById(R.id.ratingSpinner);
        ArrayAdapter ad = new ArrayAdapter(this, android.R.layout.simple_spinner_item, rating);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ratingSpinner.setAdapter(ad);
        ratingSpinner.setSelection(1);

        // display things based on purpose
        if(purpose.equals("old")){          // old activity selected -> delete
            // changing "Add" to "Update" everywhere
            ((TextView) findViewById(R.id.headLbl)).setText("UPDATE ACTIVITY");
            ((Button) findViewById(R.id.addActivityBtn)).setText("UPDATE ACTIVITY");

            // get selected activity name
            selectedActivityName = getIntent().getStringExtra("NAME"); // get activity name

            // display activity info
            fillActivityInfo();
        }

        // adding click listener to add/update button
        Button submitBtn = findViewById(R.id.addActivityBtn);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isAnswered()){
                    // get values
                    String name = ((EditText) findViewById(R.id.activityTxt)).getText().toString();
                    int rating = ((Spinner) findViewById(R.id.ratingSpinner)).getSelectedItemPosition();
                    Date date = cal.getTime();

                    // executing code based on purpose
                    if(purpose.equals("new")) {
                        db.addActivity(uname, date, name, rating);
                        Toast.makeText(getApplicationContext(), "Activity has been added", Toast.LENGTH_LONG).show();
                    }
                    else {
                        db.updateActivity(uname, selectedActivityName, selectedDateTxt, name, rating);
                        Toast.makeText(getApplicationContext(), "Activity has been updated", Toast.LENGTH_LONG).show();
                    }
                    Intent trackerIntent = new Intent(getApplicationContext(), ActivityTracker.class);
                    startActivity(trackerIntent);
                }
                else{
                    Toast.makeText(getApplicationContext(), "All fields must be filled", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    /**
     * private helper to fill out scorecard info if
     */
    private void fillActivityInfo() {
        Date currDate = Calendar.getInstance().getTime();
        Activity selected = db.getActivity(uname, currDate, selectedActivityName);
        selectedDateTxt = selected.dateText;
        ((EditText) findViewById(R.id.activityTxt)).setText(selected.name);
        ((Spinner) findViewById(R.id.ratingSpinner)).setSelection(selected.rating);
    }

    /**
     * private method to check if all fields are filled out
     * @return true if filled, false if not
     */
    private boolean isAnswered(){
        if (((EditText) findViewById(R.id.activityTxt)).getText().toString().equals("")) return false;
        else return true;
    }
}