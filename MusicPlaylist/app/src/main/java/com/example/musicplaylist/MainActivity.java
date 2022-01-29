package com.example.musicplaylist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    public static String uname = "";
    public static String userid = "";
    public static final String USERNAME = "none";
    public static final String USERID = "blank";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText unameTxt = findViewById(R.id.usernameTxt);
        EditText usridTxt = findViewById(R.id.useridTxt);

        Button loginBtn = findViewById(R.id.loginBtn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((!unameTxt.getText().equals("")) && (!usridTxt.getText().equals(""))) {
                    uname = unameTxt.getText().toString();
                    userid = usridTxt.getText().toString();

                    Intent loginIntent = new Intent(getApplicationContext(), Homepage.class);
                    loginIntent.putExtra(USERNAME, uname);
                    loginIntent.putExtra(USERID, userid);

                    startActivity(loginIntent);
                }
            }
        });
    }
}