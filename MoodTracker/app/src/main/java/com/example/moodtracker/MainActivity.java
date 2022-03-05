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
    //public static final String uname = "dm9tb";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DBClass db = DBClass.getDBInstance(this);


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

        EditText loginUname = findViewById(R.id.loginuname);
        EditText loginPassword = findViewById(R.id.loginpassword);
        TextView lgnerrorMsg= findViewById(R.id.lgnerrormsg);

        Button loginButton  =findViewById(R.id.loginbutton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean pass = true;
                //check if username exists
                if(db.authenticateUser(loginUname.getText().toString(), "") == -1){
                    lgnerrorMsg.setText("username does not exist");
                    pass = false;
                }

                //first hash the password entered
                if(pass) {
                    String hashPwd = "";
                    //hash password
                    try {
                        byte[] hashedPwd = messageDigest(loginPassword.getText().toString());
                        hashPwd = Base64.encodeToString(hashedPwd, 0);
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }

                    if (!hashPwd.equals("")) { // successfully hashed!
                        //check if password matches username
                        if (db.authenticateUser(loginUname.getText().toString(), hashPwd) == 1) {
                            Log.d("YAYYYY:", "it matches!");
                            Intent homepgIntent = new Intent(getApplicationContext(), HomePage.class);
                            homepgIntent.putExtra("UNAME", loginUname.getText().toString());
                            startActivity(homepgIntent);
                        }
                        else if(db.authenticateUser(loginUname.getText().toString(), hashPwd) == 0) {
                            lgnerrorMsg.setText("Incorrect password");
                            pass = false;
                        }

                    }
                }

                //wrong password
                /*else {
                    lgnerrorMsg.setText("Incorrect password");
                    pass = false;
                }*/
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