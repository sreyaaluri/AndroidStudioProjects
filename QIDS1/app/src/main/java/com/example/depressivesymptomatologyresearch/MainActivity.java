package com.example.depressivesymptomatologyresearch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    public static String myname = "";
    public static final String MYNAME = "blank";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText mynameTxt = findViewById(R.id.myname);

        Button startbutton = findViewById(R.id.startbutton);

        startbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!mynameTxt.getText().equals("")){
                    myname = mynameTxt.getText().toString();

                    Intent startIntent = new Intent(getApplicationContext(), sleep_questionnaire.class);
                    startIntent.putExtra(MYNAME, myname);

                    startActivity(startIntent);
                }
            }
        });
    }
}