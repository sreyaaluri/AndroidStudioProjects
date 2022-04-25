package com.example.habitbuilder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class Habits extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habits);
        DBClass db = DBClass.getDBInstance(this); // getting database instance

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

        // retrieving user habits and names
        SharedPreferences userPref = getSharedPreferences("UserInfo", MODE_PRIVATE);
        Home.userHabits = db.getHabits(userPref.getString("username", "No User")); // Assuming username exists
        ArrayList<String> habitNames = new ArrayList<>();
        for(Habit h : Home.userHabits) {
            habitNames.add(h.hname);
        }

        // displaying user's habit names
        ListView habitsList = findViewById(R.id.listviewa); // retrieving the corresponding list view
        ArrayAdapter adapter = new ArrayAdapter<String>(    // creating an array adapter with simple view
                this,
                android.R.layout.simple_list_item_1,
                habitNames);
        habitsList.setAdapter(adapter);                     // attaching adapter to listview

        // adding click listeners to each habit in list
        habitsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // trigger intent to delete habit
                Intent deleteHabitIntent = new Intent(getApplicationContext(), HabitInformation.class);
                deleteHabitIntent.putExtra("PURPOSE", "old");
                deleteHabitIntent.putExtra("NAME", adapter.getItem(i).toString());
                startActivity(deleteHabitIntent);
            }
        });

        // adding click listener to add habit button
        Button addHabitBtn = findViewById(R.id.newHabitBtn);
        addHabitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addHabitIntent = new Intent(getApplicationContext(), HabitInformation.class);
                addHabitIntent.putExtra("PURPOSE", "new");
                startActivity(addHabitIntent);
            }
        });
    }
}