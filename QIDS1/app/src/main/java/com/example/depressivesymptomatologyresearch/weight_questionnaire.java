package com.example.depressivesymptomatologyresearch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class weight_questionnaire extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight_questionnaire);

        RadioGroup weightChoice = findViewById(R.id.weightchoiceRB);
        RadioButton decrweightChoice = findViewById(R.id.decreasedweightRB);
        RadioButton incrweightChoice = findViewById(R.id.increasedweightRB);

        RadioGroup decrweightGroup = findViewById(R.id.decrweightgroupRB);
        RadioGroup incrweightGroup = findViewById(R.id.incrweightgroupRB);

        //display questionnaire based on user's choice
        decrweightChoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                decrweightGroup.setVisibility(decrweightGroup.VISIBLE);
                incrweightGroup.setVisibility(decrweightGroup.INVISIBLE);
            }
        });

        incrweightChoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                incrweightGroup.setVisibility(incrweightGroup.VISIBLE);
                decrweightGroup.setVisibility(incrweightGroup.INVISIBLE);
            }
        });
    }
}