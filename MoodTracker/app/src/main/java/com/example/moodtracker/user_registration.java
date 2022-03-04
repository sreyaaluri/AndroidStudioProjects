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

        //login button that links back to login page
        Button gotolgnPage = findViewById(R.id.gotolgnpage);
        gotolgnPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(startIntent);
            }
        });

        //user info validation
        DBClass db = DBClass.getDBInstance(this);
        EditText enterName = findViewById(R.id.entername);
        EditText enterAge = findViewById(R.id.enterage);
        EditText enterGen = findViewById(R.id.entergender);
        EditText signupUname = findViewById(R.id.signupuname);
        EditText signupPwd = findViewById(R.id.signuppassword);
        TextView signuperrorMsg= findViewById(R.id.signuperrormsg);

        Button signupBtn = findViewById(R.id.signupbtn);
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean pass = true;

                //check if name, age, gender field is not empty
                if((enterName.getText().toString() == "") && (enterAge.getText().toString() == "")  && (enterGen.getText().toString() == "" )){
                    signuperrorMsg.setText("All fields must be completed");
                    pass = false;
                }

                //check if username is exactly 5
                if(signupUname.getText().length() != 5){
                    signuperrorMsg.setText("username must be of length 5");
                    pass = false;
                }

                //check if username is alphanumeric
                if(!signupUname.getText().toString().matches("^[a-zA-Z0-9]*$")){
                    signuperrorMsg.setText("username must be alphanumeric");
                    pass = false;
                }

                //check if username is already taken
                if(db.authenticateUser(signupUname.getText().toString(), "") == 0){
                    signuperrorMsg.setText("username is taken");
                    pass = false;
                }

                //check if password is exactly 8
                if(signupPwd.getText().length() != 8){
                    signuperrorMsg.setText("password must be of length 8");
                    pass = false;
                }

                //check if password starts with uppercase
                if(startUppercase(signupPwd.getText().toString()) == false){
                    signuperrorMsg.setText("password must start Uppercase");
                    pass = false;
                }

                if(pass) { //if info is valid, hash password and store info in db

                    String hashPwd = "";
                    //hash password
                    try {
                        byte [] hashedPwd = messageDigest(signupPwd.getText().toString());
                        hashPwd = Base64.encodeToString(hashedPwd, 0);
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }

                    if(!hashPwd.equals("")) { // successfully hashed!
                        //add info to db
                        db.addUser(signupUname.getText().toString(), hashPwd, enterName.getText().toString(), Integer.parseInt(enterAge.getText().toString()), enterGen.getText().toString());
                        Log.d("YAYYYY:", "signed up!");
                    }

                    //take user back to login page to sign in
                    Intent nextIntent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(nextIntent);
                }

            }
        });
    }
    //helper method to check if username is password starts with an uppercase
    public boolean startUppercase(String s){
        for(int i = 0; i < s.length(); i++){
            if(Character.isUpperCase(s.charAt(0))) {
                return true;
            }
        }
        return false;
    }

    //password hashing algorithm
    public byte[] messageDigest(String s) throws NoSuchAlgorithmException {
        // Static getInstance method is called with hashing SHA
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        // digest() method called to calculate message digest of an input and return array of byte
        return md.digest(s.getBytes(StandardCharsets.UTF_8));
    }

}