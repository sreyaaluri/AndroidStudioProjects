package com.example.habitbuilder;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        // setting listener on "Principles" button
        Button principlesBtn = findViewById(R.id.principlesBtn);
        principlesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // take user to thinner books page on Atomic Habits
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.chrisbehan.ca/posts/atomic-habits"));
                startActivity(browserIntent);
            }
        });

        // setting listener on "Logout" button
        Button logoutBtn = findViewById(R.id.logoutBtn);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // clear user preferences
                SharedPreferences userPreferences = getSharedPreferences("UserInfo", MODE_PRIVATE);
                userPreferences.edit().clear().apply();

                // clear user habits
                Home.userHabits = new ArrayList<Habit>();

                // remove user reminders
                removeNotifs();

                // take user to login page
                Intent loggedoutIntent = new Intent(getApplicationContext(), Login.class);
                startActivity(loggedoutIntent);
            }
        });
    }

    /**
     * Method to remove reminders for habits when user logs out
     */
    public void removeNotifs(){
        // for each habit that has a notification, delete it
        for(Habit h : Home.userHabits){
            if(h.reminder) {
                // setting calendar to desired notif time
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.HOUR_OF_DAY, h.hr);
                cal.set(Calendar.MINUTE, h.min);

                // creating a pending intent, identical to the one used to set alarm, for cancellation
                Intent intent = new Intent(getApplicationContext(), sendNotification.class);
                PendingIntent cancelIntent = PendingIntent.getBroadcast(getApplicationContext(), h.alarm_id, intent, PendingIntent.FLAG_CANCEL_CURRENT);

                // using alarm manager to cancel the alarm
                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                alarmManager.cancel(cancelIntent);
            }
        }
    }
}