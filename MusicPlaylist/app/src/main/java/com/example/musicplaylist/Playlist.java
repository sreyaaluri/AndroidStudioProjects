package com.example.musicplaylist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Playlist extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);

        //song one

        //song two
        Button stwo=findViewById(R.id.songtwo);

        stwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent songIntent = new Intent(getApplicationContext(), yt_activity.class);
                songIntent.setData(Uri.parse("https://www.youtube.com/watch?v=doLMt10ytHY"));
                startActivity(songIntent);
            }
        });
    }
}