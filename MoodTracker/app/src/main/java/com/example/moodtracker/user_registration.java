package com.example.moodtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class user_registration extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);
        DBClass db = DBClass.getDBInstance(this);

        //login button that links back to login page
        Button gotolgnPage = findViewById(R.id.gotolgnpage);
        gotolgnPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(startIntent);
            }
        });

        // sign up button to add new user to database
        Button signupBtn = findViewById(R.id.signupBtn);
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //user info validation
                String enterName = ((EditText) findViewById(R.id.entername)).getText().toString();
                String enterAgeTxt = ((EditText) findViewById(R.id.enterage)).getText().toString();
                String enterGen = ((EditText) findViewById(R.id.entergender)).getText().toString();
                String signupUname = ((EditText) findViewById(R.id.signupuname)).getText().toString();
                String signupPwd = ((EditText) findViewById(R.id.signuppassword)).getText().toString();
                TextView signuperrorMsg= findViewById(R.id.signuperrormsg);

                // checking if all information is provided
                if(!enterName.equals("") && !enterAgeTxt.equals("") && !enterGen.equals("")
                        && !signupUname.equals("") && !signupPwd.equals("")) {
                    // checking username conditions
                    if(signupUname.length() != 5)                               // has length 5
                        signuperrorMsg.setText("username must be of length 5");
                    else if(!signupUname.matches("^[a-zA-Z0-9]*$"))       // is alphanumeric
                        signuperrorMsg.setText("username must be alphanumeric");
                    else if(!db.unameIsAvailable(signupUname))                  // not taken
                        signuperrorMsg.setText("username already exists, must be unique");
                        // checking password conditions
                    else if(signupPwd.length() != 8)                            // length 8
                        signuperrorMsg.setText("password must be of length 8");
                    else if(Character.isLowerCase(signupPwd.charAt(0)))         // start uppercase
                        signuperrorMsg.setText("password must start Uppercase");
                    else { // passed all checks
                        // hash password
                        String hashPwd = db.hashPassword(signupPwd);
                        if(!hashPwd.equals("")) { // successfully hashed!
                            // add new user info to database
                            db.addUser(signupUname, hashPwd, enterName, Integer.parseInt(enterAgeTxt), enterGen);
                        }

                        //take user back to login page to sign in
                        Intent nextIntent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(nextIntent);
                    }
                }
                else { // missing information -> print helpful message
                    signuperrorMsg.setText("All fields must be completed");
                }
            }
        });

    }
}