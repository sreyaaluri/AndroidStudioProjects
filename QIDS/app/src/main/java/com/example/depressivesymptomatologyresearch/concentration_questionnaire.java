package com.example.depressivesymptomatologyresearch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

public class concentration_questionnaire extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_concentration_questionnaire);

        //questionnaire section
        RadioGroup concentrationGroup = findViewById(R.id.concentrationgroupRB);
        TextView errorMsg = findViewById(R.id.errormsg);
        Button nxtbtn = findViewById(R.id.pgfivenextbtn);

        nxtbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //if all sections have been answered the "next" button should work
                if (concentrationGroup.getCheckedRadioButtonId() > -1){
                    Intent pgfiveIntent = new Intent(getApplicationContext(), selfview_questionnaire.class);
                    startActivity(pgfiveIntent);
                }
                //display error message in textview "please fill out each section"
                else{
                    errorMsg.setText("PLEASE FILL SECTION");
                }
            }
        });
    }
}