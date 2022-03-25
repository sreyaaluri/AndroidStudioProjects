package com.example.alarm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //onclick action of set/update alarm button
        Button setupdButton = findViewById(R.id.setupdbtn);
        setupdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                set_Alarm();
            }
        });

        //onclick action of remove alarm button
        Button removeButton = findViewById(R.id.removebtn);
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                remove_alarm();
            }
        });
    }

    //set_alarm method
    public void set_Alarm(){
        //retrieving time picked from timepicker widget and calendarView widget
        TimePicker tp = findViewById(R.id.timePicker1);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            Calendar c=Calendar.getInstance();

            int hr = tp.getHour();
            int m = tp.getMinute();

            c.set(Calendar.HOUR_OF_DAY,hr);
            c.set(Calendar.MINUTE,m);

            //Add logic to set date of the calendar object and get time in milliseconds like we did earlier for setting the alarm
            //retrieving values selected from calendarView widget
            Log.d("MyActivity", "Alarm On");
            CalendarView selectDate = findViewById(R.id.calendarView);
            selectDate.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                @Override
                public void onSelectedDayChange(@NonNull CalendarView view, int year, int month,
                                                int dayOfMonth) {
                    String curMonth = String.valueOf(month);
                    String dayofMon = String.valueOf(dayOfMonth);
                    String curYear = String.valueOf(year);
                    c.set(Integer.parseInt(curMonth), Integer.parseInt(dayofMon), Integer.parseInt(curYear));
                    Log.d("Date", curMonth+"/"+dayofMon+"/"+curYear);
                    //long currentTime = System.currentTimeMillis();
                    // c.setTimeInMillis(currentTime);
                }//end onSelectedDayChange() method
            });//end setOnDateChangeListener() method
        }//end if statement

        //read user's spinner selection
        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        //array adapter for spinner
        String frequency[] = {"5 minutes","10 minutes","15 minutes"};
        if (spinner != null) {
            spinner.setOnItemSelectedListener(this);
        }
        ArrayAdapter<String> spinnerArrayAdapter = new
                ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, frequency);
        spinner.setAdapter(spinnerArrayAdapter);


        //Creating a pending intent for sendNotification class.
        Intent intent = new Intent(this, sendNotification.class);
        PendingIntent pendingIntent = null;

        pendingIntent = PendingIntent.getBroadcast(this, 40, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager=(AlarmManager)getSystemService(ALARM_SERVICE);

        //this method creates a repeating, exactly timed alarm
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), 60*1000, pendingIntent); //1 minute apart
        Log.d("===Sensing alarm===", "One time alert alarm has been created. This alarm will send to a broadcast sensing receiver.");
        //every 1 minute we pass that 'pending intent' which is the sending notification!

        Toast.makeText(this, "Alarm has been added", Toast.LENGTH_LONG).show();

    }//end set_alarm method

    //remove_alarm method
    public void remove_alarm(){
        Intent intent = new Intent(this, sendNotification.class);
        PendingIntent cancelIntent = null;

        cancelIntent = PendingIntent.getBroadcast(this, 40, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager=(AlarmManager)getSystemService(ALARM_SERVICE);
        alarmManager.cancel(cancelIntent);

        Log.d("===removing alarm===", "alarm removed.");
        //every 1 minute we pass that 'pending intent' which is the sending notification!

        Toast.makeText(this, "Alarm has been removed", Toast.LENGTH_LONG).show();
    }//end remove_alarm method

}//end class