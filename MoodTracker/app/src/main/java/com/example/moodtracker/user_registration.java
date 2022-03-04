package com.example.moodtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class user_registration extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);
    }

    //account creation validation
    public void createAccount(){
        EditText createUname = findViewById(R.id.signupuname);
        TextView errorMsg = findViewById(R.id.signuperrormsg);
        Button signupBtn = findViewById(R.id.signupbtn);

        DBClass db = DBClass.getDBInstance(this);
//        String uname = db.selectQuery("username");

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                //db.updateTable()
                if(createUname.length() < 5) {
                    errorMsg.setText("username must have length of at least 5");
                }

//                if(){
//
//                }
            }//end onclick method

        });//end sign up button on click listener method
    }//end createAccount method
}