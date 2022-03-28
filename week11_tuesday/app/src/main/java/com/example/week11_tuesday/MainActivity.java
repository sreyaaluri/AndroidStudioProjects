package com.example.week11_tuesday;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;

import java.io.FileNotFoundException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestAppPermissions();
        TextView errMsg = findViewById(R.id.errormsg);
        Button btn=findViewById(R.id.button);
        EditText courseOne=findViewById(R.id.courseone);
        EditText courseTwo=findViewById(R.id.coursetwo);
        EditText courseThree=findViewById(R.id.coursethree);
        EditText courseFour=findViewById(R.id.coursefour);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String one=courseOne.getText().toString();
                String two=courseTwo.getText().toString();
                String three=courseThree.getText().toString();
                String four=courseFour.getText().toString();

                    try {
                        writeFile("scores.txt", one, two, three, four); //filename:scores.txt
                        Intent launch = new Intent(getApplicationContext(), Display.class);
                        startActivity(launch);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
            }
        });
    }

    //permission methods
    private boolean hasWritePermissions() {
        return (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
    }

    private void requestAppPermissions() {
        //  if (hasReadPermissions() && hasWritePermissions()) {
        if (hasWritePermissions()) {

            return;
        }
        ActivityCompat.requestPermissions(this,
                new String[] {
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                }, 0);
    }
}