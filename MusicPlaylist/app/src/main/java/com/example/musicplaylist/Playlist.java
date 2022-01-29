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

        // song one
        Button songOneBtn=findViewById(R.id.songone);
        songOneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent playIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=z8WunrI7yh0&ab_channel=BoBurnham-Topic"));
                startActivity(playIntent);
            }
        });

        // song two
        Button songTwoBtn=findViewById(R.id.songtwo);
        songTwoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent playIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=doLMt10ytHY"));
                startActivity(playIntent);
            }
        });

        // song three
        Button songThreeBtn=findViewById(R.id.songthree);
        songThreeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent playIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=VDcEJE633rM&ab_channel=StrongInfluenceAgency"));
                startActivity(playIntent);
            }
        });

        // song four
        Button songFourBtn=findViewById(R.id.songfour);
        songFourBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent playIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=7Hnhlwx_TpE&ab_channel=ThePowerImpactDancers"));
                startActivity(playIntent);
            }
        });

        // song five
        Button songFiveBtn=findViewById(R.id.songfive);
        songFiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent playIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=_5aZ6bhho68&list=RD_5aZ6bhho68&ab_channel=Bunny%E5%B0%8F%E5%85%94MusicChannel"));
                startActivity(playIntent);
            }
        });

        // song six
        Button songSixBtn=findViewById(R.id.songsix);
        songSixBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent playIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=fMjasXiIhiQ&ab_channel=KanyeWestVEVO"));
                startActivity(playIntent);
            }
        });

    }
}