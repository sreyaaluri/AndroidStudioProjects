package com.example.alarm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
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

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    int freq=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //onclick action of set/update alarm button
        Button setupdButton = findViewById(R.id.setupdbtn);
        setupdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                set_Alarm(getApplicationContext());
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
    public void set_Alarm(Context con){
        //retrieving time picked from timepicker widget and calendarView widget
        TimePicker tp = findViewById(R.id.timePicker1);
        Calendar c=Calendar.getInstance();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {

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
        String frequency[] = {"5","10","15"};
        if (spinner != null) {
            spinner.setOnItemSelectedListener(this);
        }
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,frequency);
        spinner.setAdapter(spinnerArrayAdapter);


        //Creating a pending intent for sendNotification class.
        Intent intent = new Intent(this, sendNotification.class);
        PendingIntent pendingIntent = null;

        pendingIntent = PendingIntent.getBroadcast(this, 40, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager=(AlarmManager)getSystemService(ALARM_SERVICE);

        //this method creates a repeating, exactly timed alarm
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), freq, pendingIntent);
        Log.d("===Sensing alarm===", "One time alert alarm has been created. This alarm will send to a broadcast sensing receiver.");
        //every 1 minute we pass that 'pending intent' which is the sending notification!

        Toast.makeText(this, "Alarm has been added", Toast.LENGTH_LONG).show();
    }//end set_alarm method

    //spinner helper methods
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        //when a value is added, set the value selected by spinner to the variable freq
        int spinner_item = (int) adapterView.getItemAtPosition(i);
        freq = spinner_item * 60 * 1000;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        // Auto-generated method stub
    }

    //remove_alarm method
    public void remove_alarm(){
        Intent intent = new Intent(this, sendNotification.class);
        PendingIntent cancelIntent = null;

        cancelIntent = PendingIntent.getBroadcast(this, 40, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager=(AlarmManager)getSystemService(ALARM_SERVICE);
        alarmManager.cancel(cancelIntent);

        Log.d("===removing alarm===", "alarm removed.");
        //every 1 minute we pass that 'pending intent' which is the sending notification!

        /*
        * AlarmManager alarmManager =
        (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    PendingIntent pendingIntent =
        PendingIntent.getService(context, requestId, intent,
                                PendingIntent.FLAG_NO_CREATE);
    if (pendingIntent != null && alarmManager != null) {
        alarmManager.cancel(pendingIntent);
    }*/

        Toast.makeText(this, "Alarm has been removed", Toast.LENGTH_LONG).show();
    }//end remove_alarm method
}//end class