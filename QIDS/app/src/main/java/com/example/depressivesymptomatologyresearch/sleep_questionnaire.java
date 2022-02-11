package com.example.depressivesymptomatologyresearch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

public class sleep_questionnaire extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep_questionnaire);

        //retrieving the user's name section
        Intent intent = getIntent();

        String myName = intent.getStringExtra(MainActivity.MYNAME);

        TextView helloTxt = findViewById(R.id.hellomsg);
        helloTxt.setText("Hello "+ myName);

        //questionnaire section
        RadioGroup sleepgroupOne = findViewById(R.id.sleepgroupRBone);
        RadioGroup sleepgroupTwo = findViewById(R.id.sleepgroupRBtwo);
        RadioGroup sleepgroupThree = findViewById(R.id.sleepgroupRBthree);
        RadioGroup sleepgroupFour = findViewById(R.id.sleepgroupRBfour);
        TextView errorMsg = findViewById(R.id.errormsg);
        Button nxtbtn = findViewById(R.id.pgonenextbtn);

        nxtbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //if all sections have been answered the "next" button should work
                if ((sleepgroupOne.getCheckedRadioButtonId() > -1) && (sleepgroupTwo.getCheckedRadioButtonId() > -1) &&
                        (sleepgroupThree.getCheckedRadioButtonId() > -1) && (sleepgroupFour.getCheckedRadioButtonId() > -1)){
                    Intent pgoneIntent = new Intent(getApplicationContext(), concentration_questionnaire.class);
                    startActivity(pgoneIntent);
                }
                //display error message in textview "please fill out each section"
                else{
                    errorMsg.setText("PLEASE FILL OUT EACH SECTION");
                }
            }
        });

    }
}