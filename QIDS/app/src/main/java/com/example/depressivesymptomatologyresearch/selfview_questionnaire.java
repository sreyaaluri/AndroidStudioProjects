package com.example.depressivesymptomatologyresearch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

public class selfview_questionnaire extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selfview_questionnaire);

        //questionnaire section
        RadioGroup viewmyselfGoup = findViewById(R.id.viewmyselfgroupRB);
        TextView errorMsg = findViewById(R.id.errormsg);
        Button nxtbtn = findViewById(R.id.pgsixnextbtn);

        nxtbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //if all sections have been answered the "next" button should work
                if (viewmyselfGoup.getCheckedRadioButtonId() > -1){
                    Intent pgsixIntent = new Intent(getApplicationContext(), deaththoughts_questionnaire.class);
                    startActivity(pgsixIntent);
                }
                //display error message in textview "please fill out each section"
                else{
                    errorMsg.setText("PLEASE FILL SECTION");
                }
            }
        });
    }
}