package com.example.moodtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //create account button
        Button createaccntBtn = findViewById(R.id.createaccntbtn);
        createaccntBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //take user back to signup page to sign up
                Intent signupIntent = new Intent(getApplicationContext(), user_registration.class);
                startActivity(signupIntent);
            }
        });

        DBClass db = DBClass.getDBInstance(this);
        EditText loginUname = findViewById(R.id.loginuname);
        EditText loginPassword = findViewById(R.id.loginpassword);
        TextView lgnerrorMsg= findViewById(R.id.lgnerrormsg);

        Button loginButton  =findViewById(R.id.loginbutton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check if username exists
                if(){
                    lgnerrorMsg.setText()
                }
                //validate user credentials

            }
        });

    }
}