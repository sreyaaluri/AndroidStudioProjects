package com.example.moodtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DBClass db = DBClass.getDBInstance(this);

        // initializing error message view
        TextView lgnerrorMsg= findViewById(R.id.lgnerrormsg);

        // create account button
        Button createaccntBtn = findViewById(R.id.createaccntbtn);
        createaccntBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //take user to signup page
                Intent signupIntent = new Intent(getApplicationContext(), user_registration.class);
                startActivity(signupIntent);
            }
        });

        // login button
        Button loginButton  =findViewById(R.id.loginbutton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String loginUname = ((EditText) findViewById(R.id.loginuname)).getText().toString();
                String loginPassword = ((EditText) findViewById(R.id.loginpassword)).getText().toString();
                String hashedPwd = db.hashPassword(loginPassword);

                // authenticating user
                int loginToken = db.authenticateUser(loginUname, hashedPwd);
                if(loginToken == -1) // -1 means user does not exist
                    lgnerrorMsg.setText("username does not exist");
                else if(loginToken == 0) // 0 means user exists but password doesn't match
                    lgnerrorMsg.setText("incorrect password");
                else if(loginToken == 1) { // successful login!
                    Intent homepgIntent = new Intent(getApplicationContext(), HomePage.class);
                    homepgIntent.putExtra("UNAME", loginUname);
                    startActivity(homepgIntent);
                }
                else Log.d("FIX:", "Unhandled error in login");
            }
        });

    }

    //password hashing algorithm
    public byte[] messageDigest(String s) throws NoSuchAlgorithmException {
        // Static getInstance method is called with hashing SHA
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        // digest() method called to calculate message digest of an input and return array of byte
        return md.digest(s.getBytes(StandardCharsets.UTF_8));
    }
}