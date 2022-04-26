package com.example.habitbuilder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

public class ActivityTracker extends AppCompatActivity {

    String uname = "";
    DBClass db = DBClass.getDBInstance(this); // getting database instance

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_tracker);

        // getting values from shared preferences
        uname = getSharedPreferences("UserInfo", MODE_PRIVATE).getString("username", "No User");

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

        // adding click listener to add activity button
        Button addActivityBtn = findViewById(R.id.addActivityBtn);
        addActivityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addActivityIntent = new Intent(getApplicationContext(), AddActivity.class);
                addActivityIntent.putExtra("PURPOSE", "new");
                startActivity(addActivityIntent);
            }
        });

        // retrieving user activity info and names for curr date
        Calendar cal = Calendar.getInstance();
        ArrayList<Activity> activitiesToday = db.getActivitiesByDate(uname, cal.getTime());
        ArrayList<String> activityNames = new ArrayList<>();
        for(Activity a : activitiesToday) {
            activityNames.add(a.name);
        }

        // displaying user's activity names
        ListView activities = findViewById(R.id.activitieslistview); // retrieving the corresponding list view
        ArrayAdapter adapter = new ArrayAdapter<String>(    // creating an array adapter with simple view
                this,
                android.R.layout.simple_list_item_1,
                activityNames);
        activities.setAdapter(adapter);                     // attaching adapter to listview

        // adding click listeners to each activity in list
        activities.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // trigger intent to update activity
                Intent deleteActivityIntent = new Intent(getApplicationContext(), AddActivity.class);
                deleteActivityIntent.putExtra("PURPOSE", "old");
                deleteActivityIntent.putExtra("NAME", adapter.getItem(i).toString());
                startActivity(deleteActivityIntent);
            }
        });

    }
}