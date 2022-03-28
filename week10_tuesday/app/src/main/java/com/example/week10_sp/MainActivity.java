package com.example.week10_sp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sp=getSharedPreferences("MyAccount",MODE_PRIVATE);
        Button bsave=findViewById(R.id.savebtn);
        EditText nametxt=findViewById(R.id.nametxt);
        EditText emailtxt=findViewById(R.id.emailtxt);
        EditText agetxt=findViewById(R.id.agetxt);

        SharedPreferences.Editor editor = sp.edit();
        Log.d("External", Environment.getDataDirectory().toString());
        bsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
// load First Fragment
                String name=nametxt.getText().toString();
                String email=emailtxt.getText().toString();
                int age=Integer.parseInt(agetxt.getText().toString());
                editor.putString("name",name);
                editor.putString("email",email);
                editor.putInt("age",age);

                editor.commit();
                Intent launch = new Intent(getApplicationContext(), Display.class);
                startActivity(launch);

            }
        });

    }
}