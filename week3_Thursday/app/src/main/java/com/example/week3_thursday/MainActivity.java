package com.example.week3_thursday;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
public static String uname="";
public static final String EXTRA_MESSAGE =
            "Secure Message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText txt=findViewById(R.id.usernametxt);

        Button btn=findViewById(R.id.btnsbmt);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!txt.getText().equals("")) {
                    uname=txt.getText().toString();
                    Intent messageIntent = new Intent(getApplicationContext(), Dashboard.class);
                    messageIntent.putExtra(EXTRA_MESSAGE, uname);

                    startActivity(messageIntent);
                }

            }
        });

    }
}