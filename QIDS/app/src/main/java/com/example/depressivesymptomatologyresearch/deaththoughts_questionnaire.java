package com.example.depressivesymptomatologyresearch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

public class deaththoughts_questionnaire extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deaththoughts_questionnaire);

        //questionnaire section
        RadioGroup deathGroup = findViewById(R.id.deathgroupRB);
        TextView errorMsg = findViewById(R.id.errormsg);
        Button nxtbtn = findViewById(R.id.pgsevennextbtn);

        nxtbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //if all sections have been answered the "next" button should work
                if (deathGroup.getCheckedRadioButtonId() > -1){
                    Intent pgsevenIntent = new Intent(getApplicationContext(), weight_questionnaire.class);
                    startActivity(pgsevenIntent);
                }
                //display error message in textview "please fill out each section"
                else{
                    errorMsg.setText("PLEASE FILL SECTION");
                }
            }
        });
    }
}