package com.example.musicplaylist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Homepage extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        Intent intent = getIntent();
        //keys coming from Extra
        String strOne = intent.getStringExtra(MainActivity.USERID);
        String strTwo = intent.getStringExtra(MainActivity.USERNAME);

        TextView usridTxt = findViewById(R.id.signinMessage);
        TextView unameTxt = findViewById(R.id.welcomeMessage);

        usridTxt.setText("Signed in as "+ strOne);
        unameTxt.setText("Hello "+ strTwo);

        //linking the listButton to the playlist page

        Button liButton = findViewById(R.id.listButton);
        liButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent listIntent = new Intent(getApplicationContext(), Playlist.class);
                startActivity(listIntent);
            }
        });
    }
}