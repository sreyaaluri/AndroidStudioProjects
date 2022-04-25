package com.example.habitbuilder;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Calendar;

public class Home extends AppCompatActivity {

    public static ArrayList<Habit> userHabits = new ArrayList<>();
    public static int habitId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        DBClass db = DBClass.getDBInstance(this); // getting database instance

        // retrieving user habits
        SharedPreferences userPref = getSharedPreferences("UserInfo", MODE_PRIVATE);
        userHabits = db.getHabits(userPref.getString("username", "No User")); // Assuming username exists

        // setting notifications for user habits that need reminders
//        setNotifs();
        
        // setting listener on "Settings" button
        Button settingsBtn = findViewById(R.id.settingsBtn);
        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // take user to settings page
                Intent nextIntent = new Intent(getApplicationContext(), Settings.class);
                startActivity(nextIntent);
            }
        });

        // setting listener on "Who am I" button
        Button identityBtn = findViewById(R.id.identityBtn);
        identityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // take user to identity page
                Intent nextIntent = new Intent(getApplicationContext(), Identity.class);
                startActivity(nextIntent);
            }
        });

        // setting listener on "Habits" button
        Button habitsBtn = findViewById(R.id.habitsBtn);
        habitsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // take user to habits page
                Intent nextIntent = new Intent(getApplicationContext(), Habits.class);
                startActivity(nextIntent);
            }
        });

        // setting listener on "Scorecard" button
        Button scorecardBtn = findViewById(R.id.scorecardBtn);
        scorecardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // take user to scorecard page
                Intent nextIntent = new Intent(getApplicationContext(), Scorecard.class);
                startActivity(nextIntent);
            }
        });

        // setting listener on "Tracker" button
        Button trackerBtn = findViewById(R.id.trackerBtn);
        trackerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // take user to settings page
                Intent nextIntent = new Intent(getApplicationContext(), Tracker.class);
                startActivity(nextIntent);
            }
        });
    }

    //disabling back button
    @Override
    public void onBackPressed() {}

    /**
     * Method to set reminders for habits when user logs in
     */
    public void setNotifs(){
        // for each habit that needs a notification, add one
        for(Habit h : Home.userHabits){
            if(h.reminder) {
                habitId += 1;
                h.alarm_id = habitId; // setting alarm_id

                // setting calendar to desired notif time
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.HOUR_OF_DAY, h.hr);
                cal.set(Calendar.MINUTE, h.min);

                // creating a pending intent for the alarm
                Intent intent = new Intent(getApplicationContext(), sendNotification.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), h.alarm_id, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                // using alarm manager to create an exactly timed real time wake-up alarm
                AlarmManager alarmManager= (AlarmManager) getSystemService(ALARM_SERVICE);
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), AlarmManager.INTERVAL_DAY , pendingIntent);
            }
        }
    }
}