package com.example.exercise1_week3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent messageIntent = new Intent(this, Screen2.class);
        messageIntent.setData(Uri.parse("https://www.richmond.edu/"));
        startActivity(messageIntent);

    }
}