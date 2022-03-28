package com.example.alarm;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    long prevDate;
    int freq = 0;  // to store user defined frequency
    String frequency[] = {"select", "5", "10", "15"}; // frquencies for spinner
    Calendar cal = Calendar.getInstance(); // our main way of storing alarm's date and time
    int ALARM_ID = 0; // for request code (yes, I am a child... XD)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // adding spinner options
        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter ad = new ArrayAdapter(this, android.R.layout.simple_spinner_item, frequency);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(ad);

        // adding selection change listener to spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                // setting frequency based on selected value
                if(pos==0) freq = 0;
                else freq = Integer.parseInt(frequency[pos]) * 60 * 1000;
            }
            public void onNothingSelected(AdapterView<?> parent) {
                // auto-generated method-stub - we do nothing
            }
        });

        // adding date change listener for calendar
        CalendarView calView = findViewById(R.id.calendarView);
        calView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month,
                                            int dayOfMonth) {
                // TODO BONUS
//                long prevTime = cal.getTimeInMillis();  // getting previous time in case invalid
                cal.set(year, month, dayOfMonth);       // updating date in calendar
//                long setTime = cal.getTimeInMillis();   // getting user defined time
//                long currTime = Calendar.getInstance().getTimeInMillis();  // getting current time
//                Log.d("-------SET_DATE", setTime+"");
//                Log.d("-------PREV_DATE", prevTime+"");
//
//                // bonus: checking if new date is in the past
//                if(setTime < currTime) {
//                    // using toast to tell user that alarm needs to be in future
//                    Toast.makeText(getApplicationContext(), "Alarm needs to be in the future", Toast.LENGTH_LONG).show();
//                    cal.setTimeInMillis(prevTime);    // reverting calendar time
//                    view.setDate(prevTime);           // reverting calendar view time
//                }
//                else {
//                    view.setDate(setTime);            // setting time in calendar view
//                }
            }
        });

        // adding onclick listener for set alarm button
        Button setBtn = findViewById(R.id.setBtn);
        setBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M) // to be compatible with method requirements
            @Override
            public void onClick(View view) {
                set_Alarm(getApplicationContext());
            }
        });

        // adding onclick listener for remove alarm button
        Button removeButton = findViewById(R.id.removeBtn);
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                remove_alarm();
            }
        });

    }

    //set_alarm method to add/update the one alarm
    @RequiresApi(api = Build.VERSION_CODES.M) // Android Studio gave a warning so we added this
    public void set_Alarm(Context con){
        // getting time from time picker and setting it as time to calendar
        TimePicker tp = findViewById(R.id.timePicker1);
        int hr = tp.getHour();
        int m = tp.getMinute();
        cal.set(Calendar.HOUR_OF_DAY,hr);
        cal.set(Calendar.MINUTE,m);
        // Note: now all alarm details are in cal and freq contains user selected frequnecy

        // creating a pending intent for the alarm
        Intent intent = new Intent(this, sendNotification.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, ALARM_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // using alarm manager to create an exactly timed real time wake-up alarm
        AlarmManager alarmManager= (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), freq, pendingIntent);

        // using toast to show user a message that alarm has been added
        Toast.makeText(this, "Alarm has been added/updated", Toast.LENGTH_LONG).show();

        // saving alarm information to shared preferences
        SharedPreferences sharedpreferences = getSharedPreferences("AlarmInformation", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("AlarmTime", cal.getTime().toString());
        Log.d("---------Time from cal", cal.getTime().toString());
        editor.apply();
    }

    //remove_alarm method to remove the one alarm
    public void remove_alarm(){
        // creating a pending intent identical to the one used to set alarm for cancellation
        Intent intent = new Intent(this, sendNotification.class);
        PendingIntent cancelIntent = PendingIntent.getBroadcast(this, ALARM_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // using alarm manager to cancel the alarm
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.cancel(cancelIntent);

        // using toast to show user a message that alarm has been deleted
        Toast.makeText(this, "Alarm has been removed", Toast.LENGTH_LONG).show();

        // removing alarm information from shared preferences
        SharedPreferences sharedpreferences = getSharedPreferences("AlarmInformation", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("AlarmTime", "");
        editor.apply();
    }
}