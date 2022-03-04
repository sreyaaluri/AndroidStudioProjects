package com.example.moodtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //validate user's credentials

    //if username and password field is empty

    //validate info
    public class validated(){
        DBClass db = new DBClass(this, "")
    }
}