package com.example.habitbuilder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Signup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        DBClass db = DBClass.getDBInstance(this); // getting database instance

        // setting listener on login button
        Button loginBtn = findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //take user to login page
                Intent loginIntent = new Intent(getApplicationContext(), Login.class);
                startActivity(loginIntent);
            }
        });

        // setting listener on sign up button to add new user to database
        Button signupBtn = findViewById(R.id.signupBtn);
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //user info validation
                String name = ((EditText) findViewById(R.id.nameTxt)).getText().toString();
                String uname = ((EditText) findViewById(R.id.unameTxt)).getText().toString();
                String pwd = ((EditText) findViewById(R.id.pwdTxt)).getText().toString();

                // checking if all information is provided
                if(!name.equals("") && !uname.equals("") && !pwd.equals("")) {
                    // checking username availability
                    if(!db.unameIsAvailable(uname))                  // uname not taken
                        Toast.makeText(getApplicationContext(), "username already exists, must be unique", Toast.LENGTH_LONG).show();
                    // checking password conditions
                    else if(pwd.length() < 12)                       // pwd length >= 12
                        Toast.makeText(getApplicationContext(), "password must be at least 12 character", Toast.LENGTH_LONG).show();
                    else if(Character.isLowerCase(pwd.charAt(0)))    // pwd starts uppercase
                        Toast.makeText(getApplicationContext(), "password must start with an uppercase letter", Toast.LENGTH_LONG).show();
                    else { // passed all checks
                        // hash password
                        String hashPwd = db.hashPassword(pwd);
                        if(!hashPwd.equals("")) { // successfully hashed!
                            // add new user info to database
                            db.addUser(uname, hashPwd, name);
                        }

                        //take user back to login page to sign in
                        Intent nextIntent = new Intent(getApplicationContext(), Login.class);
                        startActivity(nextIntent);
                    }
                }
                else { // missing information -> print helpful message
                    Toast.makeText(getApplicationContext(), "All fields must be completed", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}