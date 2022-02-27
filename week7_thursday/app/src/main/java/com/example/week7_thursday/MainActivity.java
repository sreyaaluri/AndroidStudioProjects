package com.example.week7_thursday;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity {
    private boolean loaded=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button nxtbtn = findViewById(R.id.nxtbutton);
        nxtbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent infDisplay = new Intent(MainActivity.this, courseinfo.class);
                startActivity(infDisplay);
            }
        });
    }

    public void displayInfo() {
        EditText courseName = findViewById(R.id.coursenm);
        EditText courseNum = findViewById(R.id.coursenum);
        EditText netId = findViewById(R.id.netid);
        RadioGroup yrGroup = findViewById(R.id.yrgroup);

        DBClass db = new DBClass(this, "Courses");
        String courName = db.selectQuery("Course name");
        courseName.setText(courName);
        String courNum = db.selectQuery("Course number");
        courseNum.setText(courNum);
        String neId = db.selectQuery("Net ID");
        netId.setText(neId);
        //String classYr = db.selectQuery("Year");
        //yrGroup.setText(classYr);
        courseName.setClickable(true);
        courseNum.setClickable(true);
        netId.setClickable(true);
        yrGroup.setClickable(true);
        loaded=true;
        Button filter=findViewById(R.id.filterbtn);
        /*edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nvalue=nametxt.getText().toString();
                String agevalue=agetxt.getText().toString();

                db.updateTable("name",nvalue,"age",agevalue);
                String nameval=db.selectQuery("name");
                String ageval=db.selectQuery("age");
                Log.d("==Updated==",nameval+","+ageval);
            }
        });*/

    }
}