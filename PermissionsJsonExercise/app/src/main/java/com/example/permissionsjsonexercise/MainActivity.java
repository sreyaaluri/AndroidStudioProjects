package com.example.permissionsjsonexercise;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Please check the AndroidManifest.xml for the permissions.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestAppPermissions();
        Button bsave=findViewById(R.id.sbmt_btn);
        EditText nametxt=findViewById(R.id.nametxt);
        EditText agetxt=findViewById(R.id.agetxt);
        bsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name=nametxt.getText().toString();
                String age=agetxt.getText().toString();
                try {
                    writeFile("Info.txt",name,age); //filename:info.txt
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
/*
    private boolean hasReadPermissions() {   //check if the permission for reading external storage is enabled.
        return (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
    }
*/
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

    public void writeFile(String filename,String name,String age) throws JSONException, FileNotFoundException {
        /*
        What is JSONException?
        Thrown to indicate a problem with the JSON API. Such problems include:

            Attempts to parse or construct malformed documents
            Use of null as a name
            Use of numeric types not available to JSON, such as NaNs or infinities.
            Lookups using an out of range index or nonexistent name
            Type mismatches on lookups
         */
        String myDir = Environment.getExternalStorageDirectory() +"/Documents/"+filename; //creating a file in the internal storage/Documents folder on phone.
        Log.d("PrintDir","====="+myDir);
        File file = new File(myDir);    //creating a file object
        JSONObject actJSON = new JSONObject();   //create a JSONObject
        actJSON.put("Name",name);
        actJSON.put("Age",age);

        //Write to the file and store in internal storage
        FileOutputStream fOut = new FileOutputStream(file, true); //create a file output stream for writing data to file
        OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);  //converts character stream into byte stream
        try {
            myOutWriter.append(actJSON.toString() + "\n");  //write JSONObject to file
            myOutWriter.close();
            fOut.close();
        }
        catch (Exception e){
            e.printStackTrace();  //to handle exceptions and errors.
        }

    }

}