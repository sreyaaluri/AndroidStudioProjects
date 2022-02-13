package com.example.depressivesymptomatologyresearch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Information extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        // welcoming user if given name
        if(!MainActivity.name.equals("")){
            TextView welcome = findViewById(R.id.welcomeLbl);
            welcome.setText("Hello, "+MainActivity.name+"!");
        }

        // adding on click listener to start button
        Button startbutton = findViewById(R.id.nextBtn);
        startbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // launching start of survey
                Intent startIntent = new Intent(getApplicationContext(), sleep_questionnaire.class);
                startIntent.putExtra("SCORE", "0");
                startActivity(startIntent);
            }
        });
    }
}