package com.example.week10_alarm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;


// This app creates a notification every 1 minute using alarm manager and broadcast receiver.
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        start_Sensing_new(); //this method will trigger the alarm
    }
    public void start_Sensing_new() {  //testing for upload frequency.

        Log.d("MyActivity", "Alarm On");
        Calendar calendar = Calendar.getInstance();
        long currentTime = System.currentTimeMillis();
        calendar.setTimeInMillis(currentTime);

        //Creating a pending intent for sendNotification class.
        Intent intent = new Intent(this, sendNotification.class);
        PendingIntent pendingIntent = null;


        //Retrieve a PendingIntent that will perform a broadcast. getBroadcast takes 3 input parameters:
        //Context: The Context in which this PendingIntent should perform the broadcast.
        //requestCode: Private request code for the sender
        //Intent: The Intent to be broadcast. This value cannot be null.
        // Flags are constants associated with pending intent. More info at https://developer.android.com/reference/android/app/PendingIntent#FLAG_ONE_SHOT
        pendingIntent = PendingIntent.getBroadcast(this, 40, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        //we are making 'intent' which was to send notification a 'pending intent'
        //'pendingIntent' is a future action to be carried out

/*
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            //pendingIntent = PendingIntent.getActivity(this, 3, intent, PendingIntent.FLAG_MUTABLE);
            pendingIntent = PendingIntent.getBroadcast(this, 40, intent, PendingIntent.FLAG_MUTABLE);

        }
        else {
            pendingIntent = PendingIntent.getBroadcast(this, 40, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        }*/

        //Generating object of alarmManager using getSystemService method. Here ALARM_SERVICE is used to receive alarm manager with intent at a time.
        AlarmManager alarmManager=(AlarmManager)getSystemService(ALARM_SERVICE);

        //this method creates a repeating, exactly timed alarm
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 60*1000, pendingIntent); //1 minute apart
        Log.d("===Sensing alarm===", "One time alert alarm has been created. This alarm will send to a broadcast sensing receiver.");
        //every 1 minute we pass that 'pending intent' which is the sending notification!

        Toast.makeText(this, "Sensing alert alarm has been created. This alarm will send to a broadcast start sensing receiver.", Toast.LENGTH_LONG).show();
    }

}