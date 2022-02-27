package com.example.week7_thursday;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class courseinfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courseinfo);
        TextView resultView=findViewById(R.id.resultview);
        DBClass db=new DBClass(this,"Net IDs");
        //String res=db.selectConditionQuery("Net ID","");
        dtxt.setText(res);
    }
}