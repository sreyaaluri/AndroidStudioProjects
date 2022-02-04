package com.example.depressivesymptomatologyresearch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class sleep_questionnaire extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep_questionnaire);

        Intent intent = getIntent();
        //keys coming from Extra
        String myName = intent.getStringExtra(MainActivity.MYNAME);

        TextView helloTxt = findViewById(R.id.hellomsg);

        helloTxt.setText("Hello "+ myName);
    }
}