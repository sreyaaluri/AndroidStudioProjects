package com.example.depressivesymptomatologyresearch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class End extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);

        int total_score = Integer.parseInt(getIntent().getStringExtra("SCORE"));
        TextView scoreLbl = findViewById(R.id.scoreLbl);
        scoreLbl.setText(""+total_score);
    }
}