package com.example.moodtracker;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DailyDiary extends AppCompatActivity {

    private String username = ""; // variable to store username

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_diary);

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
                // defined required widgets
                TextView notesTxt = findViewById(R.id.diaryEntryTxt);
                CheckBox exerciseCB = findViewById(R.id.exerciseCB);
                CheckBox outsideCB = findViewById(R.id.outsideCB);
                CheckBox socialCB = findViewById(R.id.socialCB);
                CheckBox meditateCB = findViewById(R.id.meditateCB);
                CheckBox readCB = findViewById(R.id.readCB);


                // retrieving date and user-entered data
                String date = java.time.LocalDate.now().toString();
                String entry = notesTxt.getText().toString();
                int exercise = exerciseCB.isChecked() ? 1 : 0;
                int outside = outsideCB.isChecked() ? 1 : 0;
                int socialize = socialCB.isChecked() ? 1 : 0;
                int meditate = meditateCB.isChecked() ? 1 : 0;
                int read = readCB.isChecked() ? 1 : 0;

                // If no user-entered data -> don't execute rest of code
                if(entry.equals("") && exercise==0 && outside==0 && socialize==0 && meditate==0 && read==0)
                    return;

                // making diary entry with user-entered data
                DiaryEntry de = new DiaryEntry(date, entry, exercise, outside, socialize, meditate, read);

                // adding to database
                DBClass db = DBClass.getDBInstance(DailyDiary.this);
                db.addDiaryEntry(username, de);

                // clear form
                exerciseCB.setChecked(false);
                outsideCB.setChecked(false);
                socialCB.setChecked(false);
                meditateCB.setChecked(false);
                readCB.setChecked(false);
                notesTxt.setText(null);
            }
        });
    }

}