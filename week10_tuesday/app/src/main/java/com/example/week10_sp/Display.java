package com.example.week10_sp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Display extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        SharedPreferences sp=getSharedPreferences("MyAccount",MODE_PRIVATE);
        TextView name=findViewById(R.id.namedisp);
        TextView email=findViewById(R.id.emaildisp);
        TextView age=findViewById(R.id.agedisp);
        String name_val=sp.getString("name","No name");
        String email_val=sp.getString("email","No email");
        Integer age_val=sp.getInt("age",0);
        name.setText(name_val.toString());
        email.setText(email_val.toString());
        age.setText(age_val.toString());
    }
}