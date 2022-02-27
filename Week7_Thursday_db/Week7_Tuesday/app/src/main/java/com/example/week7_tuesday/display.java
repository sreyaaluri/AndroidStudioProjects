package com.example.week7_tuesday;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class display extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        TextView dtxt=findViewById(R.id.disptxt);
        DBClass db=new DBClass(this,"Users");
        String res=db.selectConditionQuery("name","age=18");
        dtxt.setText(res);

    }
}