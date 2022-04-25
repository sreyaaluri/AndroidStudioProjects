package com.example.habitbuilder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        DBClass db = DBClass.getDBInstance(this); // getting database instance

        // setting listener on singup button
        Button signupBtn = findViewById(R.id.signupBtn);
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //take user to signup page
                Intent signupIntent = new Intent(getApplicationContext(), Signup.class);
                startActivity(signupIntent);
            }
        });

        // setting listener on login button
        Button loginBtn = findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String loginUname = ((EditText) findViewById(R.id.unameTxt)).getText().toString();
                String loginPassword = ((EditText) findViewById(R.id.pwdTxt)).getText().toString();
                String hashedPwd = db.hashPassword(loginPassword);

                // authenticating user
                int loginToken = db.authenticateUser(loginUname, hashedPwd);
                if(loginToken == -1) // -1 means user does not exist
                    Toast.makeText(getApplicationContext(), "username does not exist", Toast.LENGTH_LONG).show();
                else if(loginToken == 0) // 0 means user exists but password doesn't match
                    Toast.makeText(getApplicationContext(), "incorrect password", Toast.LENGTH_LONG).show();
                else if(loginToken == 1) { // successful login!
                    // storing user information in shared preferences
                    SharedPreferences userPreferences = getSharedPreferences("UserPref", MODE_PRIVATE);
                    SharedPreferences.Editor editor = userPreferences.edit();
                    editor.putString("username", loginUname);
                    editor.apply();

                    // redirecting to homepage
                    Intent homeIntent = new Intent(getApplicationContext(), Home.class);
                    startActivity(homeIntent);
                }
                else Log.d("---FIX:", "Unhandled error in login");
            }
        });
    }
}