package com.example.week8_tuesday;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

public class Display extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        SharedPreferences sharedPref = getSharedPreferences("UserDetails", MODE_PRIVATE);
        String uname = sharedPref.getString("Username", "No username");
        String email = sharedPref.getString("Email", "No email");

        ((TextView) findViewById(R.id.unameLbl)).setText(uname);
        ((TextView) findViewById(R.id.emailLbl)).setText(email);

    }
}