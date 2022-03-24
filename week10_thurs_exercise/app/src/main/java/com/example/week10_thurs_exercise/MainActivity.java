package com.example.week10_thurs_exercise;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonOne  = findViewById(R.id.button1);
        Button buttonTwo  = findViewById(R.id.button2);

        buttonOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start_Sensing_new(); //this method will trigger the alarm
            }
        });
    }

    public void start_Sensing_new() { //testing for upload frequency.
        Log.d("MyActivity", "Alarm On");
        Calendar calendar = Calendar.getInstance();
        long currentTime = System.currentTimeMillis();
        calendar.setTimeInMillis(currentTime);

        //Creating a pending intent for sendNotification class.
        Intent intentOne = new Intent(this, sendNotificationOne.class);
        Intent intentTwo = new Intent(this, sendNotificationTwo.class);
        PendingIntent pendingIntentOne = null;
        PendingIntent pendingIntentTwo = null;

        //Retrieve a PendingIntent that will perform a broadcast. getBroadcast takes 3 input parameters:
        //Context: The Context in which this PendingIntent should perform the broadcast.
        //requestCode: Private request code for the sender
        //Intent: The Intent to be broadcast. This value cannot be null.
        // Flags are constants associated with pending intent. More info at https://developer.android.com/reference/android/app/PendingIntent#FLAG_ONE_SHOT
        //we are making 'intent' which was to send notification a 'pending intent'
        //'pendingIntent' is a future action to be carried out
        pendingIntentOne = PendingIntent.getBroadcast(this, 40, intentOne, PendingIntent.FLAG_UPDATE_CURRENT);
        pendingIntentTwo = PendingIntent.getBroadcast(this, 50, intentTwo, PendingIntent.FLAG_UPDATE_CURRENT);

        //Generating object of alarmManager using getSystemService method. Here ALARM_SERVICE is used to receive alarm manager with intent at a time.
        AlarmManager alarmManager=(AlarmManager)getSystemService(ALARM_SERVICE);

        //this method creates a repeating, exactly timed alarm
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 120*1000, pendingIntentOne); //2 minutes apart
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 180*1000, pendingIntentTwo); //3 minutes apart

        Log.d("===Sensing alarm===", "One time alert alarm has been created. This alarm will send to a broadcast sensing receiver.");
        //every 2/3 minutes we pass that 'pending intent' which is the sending notification!

        Toast.makeText(this, "Sensing alert alarm has been created. This alarm will send to a broadcast start sensing receiver.", Toast.LENGTH_LONG).show();
    }
}