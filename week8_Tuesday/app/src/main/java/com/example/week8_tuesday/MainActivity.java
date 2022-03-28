package com.example.week8_tuesday;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button saveBtn = findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean allAnswered = true;

                // get values entered by user
                String username = ((EditText) findViewById(R.id.unameTxt)).getText().toString();
                String ageTxt = ((EditText) findViewById(R.id.ageTxt)).getText().toString();
                String email = ((EditText) findViewById(R.id.emailTxt)).getText().toString();
                if(username.equals("") || ageTxt.equals("") || email.equals("")) allAnswered = false;

                // proceed if user entered all info
                if(allAnswered) {
                    // set shared preferences
                    SharedPreferences sharedpreferences = getSharedPreferences("UserDetails", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString("Username", username);
                    editor.putInt("Age", Integer.parseInt(ageTxt));
                    editor.putString("Email", email);
                    Boolean success = editor.commit();

                    if(success) {
                        // launch display page
                        Intent displayIntent = new Intent(getApplicationContext(), Display.class);
                        startActivity(displayIntent);
                    }
                    else {
                        Log.d("ERROR:", "----- couldn't commit to shared preferences!");
                    }

                    // note: commit waits and gets a boolean for success while apply is asynchronous
//                     editor.apply()

                    // launch display page
                    Intent displayIntent = new Intent(getApplicationContext(), Display.class);
                    startActivity(displayIntent);

//                    // note: commit waits and gets a boolean for success while apply is asynchronous
//                    Boolean success = editor.commit();
                    editor.apply();

                }

            }
        });
    }
}