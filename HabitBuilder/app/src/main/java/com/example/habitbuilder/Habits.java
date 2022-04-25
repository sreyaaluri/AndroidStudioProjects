package com.example.habitbuilder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class Habits extends AppCompatActivity {

    // ListView for Habits
    ListView habitsList;

    // data to be displayed into list TODO get from database
    String[] mobileTypes = {
            "Galaxy Note 8",
            "kekekeke",
            "shdfhsf"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habits);

        // displaying user's habits
        habitsList = findViewById(R.id.listviewa);          // retrieving the corresponding list view
        ArrayAdapter adapter = new ArrayAdapter<String>(    // creating an array adapter with simple view
                this,
                android.R.layout.simple_list_item_1,
                mobileTypes);

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