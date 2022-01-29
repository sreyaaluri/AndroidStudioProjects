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
        String userID = intent.getStringExtra(MainActivity.USERID);
        String userName = intent.getStringExtra(MainActivity.USERNAME);

        TextView signInTxt = findViewById(R.id.signinMessage);
        TextView welcomeTxt = findViewById(R.id.welcomeMessage);

        signInTxt.setText("Signed in as "+ userID);
        welcomeTxt.setText("Hello "+ userName);

        //linking the listButton to the playlist page

        Button liButton = findViewById(R.id.listButton);
        liButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent playlistIntent = new Intent(getApplicationContext(), Playlist.class);
                startActivity(playlistIntent);
            }
        });
    }
}