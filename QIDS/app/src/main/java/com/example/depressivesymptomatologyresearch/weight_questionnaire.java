package com.example.depressivesymptomatologyresearch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

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

        //user must choose decreased or increased before next button works
        TextView errorMsg = findViewById(R.id.errormsg);
        Button nxtbtn = findViewById(R.id.pgfournextbtn);

        nxtbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //if all sections have been answered the "next" button should work
                if (weightChoice.getCheckedRadioButtonId() == -1){
                    errorMsg.setText("PLEASE FILL SECTION");
                }
            }
        });

        //user must fill out questionnaire to go to the next page
        nxtbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //if all sections have been answered the "next" button should work
                if ((decrweightGroup.getCheckedRadioButtonId() > -1) || (incrweightGroup.getCheckedRadioButtonId() > -1)){
                    Intent pgfourIntent = new Intent(getApplicationContext(), deaththoughts_questionnaire.class);
                    startActivity(pgfourIntent);
                }
                //display error message in textview "please fill out each section"
                else{
                    errorMsg.setText("PLEASE FILL SECTION");
                }
            }
        });
    }
}