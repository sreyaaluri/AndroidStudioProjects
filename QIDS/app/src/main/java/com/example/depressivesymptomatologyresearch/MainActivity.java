package com.example.depressivesymptomatologyresearch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static String name = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // adding on click listener to start button
        Button startbutton = findViewById(R.id.startbutton);
        startbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get user name
                EditText nameTxt = findViewById(R.id.myname);
                name = nameTxt.getText().toString();

                // go to information for survey
                Intent startIntent = new Intent(getApplicationContext(), Information.class);
                startActivity(startIntent);
            }
        });
    }
}