package com.example.musicplaylist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

public class yt_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yt);

        Intent intent = getIntent();
        Uri link = intent.getData();

        Intent launch=new Intent(Intent.ACTION_VIEW,link);
        startActivity(launch);
    }
}