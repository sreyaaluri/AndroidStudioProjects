package com.example.week3_thursday;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Dashboard extends AppCompatActivity {
public static String userprofile="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Intent intent = getIntent();
        String s=intent.getStringExtra(MainActivity.EXTRA_MESSAGE); //key coming from Extra

        TextView txt=findViewById(R.id.usertxt);
    //    txt.setText("Welcome "+MainActivity.uname); //method 1 with global variable
        txt.setText("Welcome "+s); //using extra, method 2

        Button btnsw=findViewById(R.id.swbtn);
        btnsw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
          //      userprofile="sw"; // method 1
                Intent messageIntent = new Intent(getApplicationContext(), Profile.class);
                messageIntent.putExtra("profile", "sw"); //method 2

                startActivity(messageIntent);
                }

        });

        Button btnat=findViewById(R.id.atbtn);
        btnat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
      //          userprofile="at";
                Intent messageIntent = new Intent(getApplicationContext(), atprofile.class);
                messageIntent.putExtra("profile", "at");

                startActivity(messageIntent);
            }

        });

    }
}