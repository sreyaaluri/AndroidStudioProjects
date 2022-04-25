package com.example.habitbuilder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class HabitInformation extends AppCompatActivity {

    String purpose = "new";
    String habitName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_information);

        purpose = getIntent().getStringExtra("PURPOSE");

        // changing/adding display text based on purpose
        if(purpose.equals("old")){
            // changing "Add" to "Delete" everywhere
            ((Button) findViewById(R.id.addHabitBtn)).setText("DELETE HABIT");

            // getting habit name
            habitName = getIntent().getStringExtra("NAME");

            // displaying information about habit
            // TODO get information from database and fill spots
        }
    }
}