package com.example.week4_tuesday;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // course checkboxes
        CheckBox coursePL = findViewById(R.id.coursePLCB);
        CheckBox courseAP = findViewById(R.id.courseAPCB);
        CheckBox courseComb = findViewById(R.id.courseCombCB);
        CheckBox courseGT = findViewById(R.id.courseGTCB);

        // year radio button
        RadioButton freshman = findViewById(R.id.freshmanBtn);
        RadioButton sophomore = findViewById(R.id.sophomoreBtn);
        RadioButton junior = findViewById(R.id.juniorBtn);
        RadioButton senior = findViewById(R.id.seniorBtn);

        // submit button
        Button submitBtn = findViewById(R.id.submitBtn);


        // setting click listener on submit button
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //add code
            }
        });


    }

    public void printYear(View view){
        String classYear = ""; // to store value that should be printed
        RadioButton selected = (RadioButton) view;
        if(selected.isChecked()){
            classYear = selected.getText().toString();
        }

        TextView yearMsg = findViewById(R.id.collegeYearMsgTxt);
        yearMsg.setText("You're a cool " + classYear);
    }

    public void logClick(View view){

    }
}