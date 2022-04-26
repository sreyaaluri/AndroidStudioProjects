package com.example.habitbuilder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Identity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identity);
        DBClass db = DBClass.getDBInstance(this); // getting database instance

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

        // retrieving user identity from habits
        SharedPreferences userPref = getSharedPreferences("UserInfo", MODE_PRIVATE);
        Home.userHabits = db.getHabits(userPref.getString("username", "No User")); // Assuming username exists
        ArrayList<String> identity = new ArrayList<>();
        for(Habit h : Home.userHabits) {
            identity.add(h.identity);
        }

        // displaying user's identity names
        ListView habitsList = findViewById(R.id.listviewIdentity);          // retrieving the corresponding list view
        ArrayAdapter adapter = new ArrayAdapter<String>(    // creating an array adapter with simple view
                this,
                android.R.layout.simple_list_item_1,
                identity);
        habitsList.setAdapter(adapter);                     // attaching adapter to listview
    }
}