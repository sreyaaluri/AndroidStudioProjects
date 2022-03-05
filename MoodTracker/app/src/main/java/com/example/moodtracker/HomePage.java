package com.example.moodtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomePage extends AppCompatActivity {

    // TODO remove hardcoded username
    public static final String uname = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        //logout button
        Button logoutBtn = findViewById(R.id.logoutBtn);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loggedoutIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(loggedoutIntent);
            }
        });

        // adding on click listener to daily diary button
        Button diaryBtn = findViewById(R.id.diaryBtn);
        diaryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // redirecting to daily diary page
                Intent startIntent = new Intent(getApplicationContext(), DailyDiary.class);
                startIntent.putExtra("UNAME", uname);
                startActivity(startIntent);
            }
        });

        // adding on click listener to view notes button
        Button notesBtn = findViewById(R.id.viewNotesBtn);
        notesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // redirecting to view notes page
                Intent startIntent = new Intent(getApplicationContext(), ViewNotes.class);
                startIntent.putExtra("UNAME", uname);
                startActivity(startIntent);
            }
        });

        // adding on click listener to wellness tracker button
        Button trackerBtn = findViewById(R.id.trackerBtn);
        trackerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // redirecting to tracker page
                Intent startIntent = new Intent(getApplicationContext(), Tracker.class);
                startIntent.putExtra("UNAME", uname);
                startActivity(startIntent);
            }
        });
    }
    //disable back button
    @Override
    public void onBackPressed() {}
}