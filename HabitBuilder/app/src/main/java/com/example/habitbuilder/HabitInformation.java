package com.example.habitbuilder;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Calendar;
import java.util.Locale;

public class HabitInformation extends AppCompatActivity {

    String purpose = "new";
    String habitName;
    DBClass db = DBClass.getDBInstance(this); // getting database instance
    int hr = -1;
    int min = -1;
    boolean remind = false;
    Habit selectedHabit = new Habit();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_information);

        // setting click listener on 'Pick Time' button
        Button pickTimeBtn = findViewById(R.id.timeBtn);
        pickTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popTimePicker(view);
            }
        });

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

        // setting click listener on Download button
        Button saveBtn = findViewById(R.id.downloadHabitBtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isAnswered()){
                    try{
                        writeFile();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                }
            }
        });

        // executing code based on purpose for the add/delete button
        purpose = getIntent().getStringExtra("PURPOSE");
        if(purpose.equals("old")){          // old habit selected -> delete
            // changing "Add" to "Delete" everywhere
            ((Button) findViewById(R.id.addHabitBtn)).setText("DELETE HABIT");

            // get selected habit
            habitName = getIntent().getStringExtra("NAME"); // get habit name
            selectedHabit = getHabit(habitName); // get selected habit from habit name

            // display habit info
            fillHabitInfo();

            // set click listener which deletes a habit
            Button delBtn = findViewById(R.id.addHabitBtn);
            delBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    db.deleteHabitByName(getSharedPreferences("UserInfo", MODE_PRIVATE).getString("username", "No User"), habitName); // delete habit from DB
                    if(selectedHabit.reminder) removeNotif(habitName); // remove notifications associated with habit, if any
                    Toast.makeText(getApplicationContext(), "Habit has been deleted", Toast.LENGTH_LONG).show();
                    Intent habitsIntent = new Intent(getApplicationContext(), Habits.class);
                    startActivity(habitsIntent);
                }
            });
        } else {                            // new habit being added -> add
            // set click listener which adds a habit
            Button addBtn = findViewById(R.id.addHabitBtn);
            addBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(isAnswered()){
                        addHabit();
                        Toast.makeText(getApplicationContext(), "Habit has been added", Toast.LENGTH_LONG).show();
                        Intent habitsIntent = new Intent(getApplicationContext(), Habits.class);
                        startActivity(habitsIntent);
                    } else {
                        Toast.makeText(getApplicationContext(), "All fields must be filled", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    /**
     * onclick method for daily reminder checkbox
     * @param view
     */
    public void toggleRemind(View view){
        remind = ((CheckBox) view).isChecked();
        if(!remind) {
            hr = -1;
            min = -1;
            ((Button) findViewById(R.id.timeBtn)).setText("PICK TIME");
        }
    }

    /**
     * method to show pop-up time picker dialog
     * @param view
     */
    public void popTimePicker(View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMin) {
                hr = selectedHour;
                min = selectedMin;
                ((Button) findViewById(R.id.timeBtn)).setText(String.format(Locale.getDefault(), "%02d:%02d", hr, min));
            }
        };

        int style = AlertDialog.THEME_HOLO_LIGHT;
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, style, onTimeSetListener, hr, min, false);
        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
    }

    /**
     * private helper to get habit info given habit name
     * @param name
     * @return Habit with given name
     */
    private Habit getHabit(String name){
        Habit habit = new Habit();
        for(Habit h : Home.userHabits){
            if(h.hname.equals(name))
                habit = h;
        }
        return habit;
    }

    /**
     * private helper to fill out habit info if habit selected
     */
    private void fillHabitInfo(){
        // filling out habit information on screen
        ((EditText) findViewById(R.id.identityTxt)).setText(selectedHabit.identity);
        ((EditText) findViewById(R.id.habitTxt)).setText(selectedHabit.hname);
        ((EditText) findViewById(R.id.connectionTxt)).setText(selectedHabit.why);
        ((EditText) findViewById(R.id.freqTxt)).setText(selectedHabit.freq);
        ((CheckBox) findViewById(R.id.reminderCB)).setChecked(selectedHabit.reminder);
        if(selectedHabit.reminder) ((Button) findViewById(R.id.timeBtn)).setText(selectedHabit.hr+":"+selectedHabit.min);
        ((EditText) findViewById(R.id.cueTxt)).setText(selectedHabit.cue);
        ((EditText) findViewById(R.id.cravingTxt)).setText(selectedHabit.craving);
        ((EditText) findViewById(R.id.responseTxt)).setText(selectedHabit.response);
        ((EditText) findViewById(R.id.rewardTxt)).setText(selectedHabit.reward);
    }

    /**
     * private helper to add habit to database
     */
    private void addHabit(){
        // get username
        String uname = getSharedPreferences("UserInfo", MODE_PRIVATE).getString("username", "No User");
        // create habit
        Habit h = new Habit(
                ((EditText) findViewById(R.id.identityTxt)).getText().toString(),
                ((EditText) findViewById(R.id.habitTxt)).getText().toString(),
                ((EditText) findViewById(R.id.connectionTxt)).getText().toString(),
                ((EditText) findViewById(R.id.freqTxt)).getText().toString(),
                ((CheckBox) findViewById(R.id.reminderCB)).isChecked(),
                hr,
                min,
                ((EditText) findViewById(R.id.cueTxt)).getText().toString(),
                ((EditText) findViewById(R.id.cravingTxt)).getText().toString(),
                ((EditText) findViewById(R.id.responseTxt)).getText().toString(),
                ((EditText) findViewById(R.id.rewardTxt)).getText().toString()
        );
        db.addHabit(uname, h);

        // update global habits list
        Home.userHabits = db.getHabits(uname);

        // adding notification if necessary
        if(h.reminder) setNotif(((EditText) findViewById(R.id.habitTxt)).getText().toString());
    }

    /**
     * Method to set reminder for newly added habit
     */
    public void setNotif(String hname){
        Habit h = getHabit(hname);

        // setting calendar to desired notif time
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, h.hr);
        cal.set(Calendar.MINUTE, h.min);
        cal.set(Calendar.SECOND, 0);

        // creating a pending intent for the alarm
        Intent intent = new Intent(getApplicationContext(), sendNotification.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), h.alarm_id, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // using alarm manager to create an exactly timed real time wake-up alarm
        AlarmManager alarmManager= (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), AlarmManager.INTERVAL_DAY , pendingIntent);

        // testing
//        Log.d("---NOTIF FOR NEW","set notification for "+ h.hname+ " with id "+ h.alarm_id+" to go at "+cal.getTime().toString());
    }

    /**
     * Method to remove reminder for deleted habit
     */
    public void removeNotif(String hname){
        Habit h = getHabit(hname);
        // setting calendar to desired notif time
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, h.hr);
        cal.set(Calendar.MINUTE, h.min);
        cal.set(Calendar.SECOND,0);

        // creating a pending intent, identical to the one used to set alarm, for cancellation
        Intent intent = new Intent(getApplicationContext(), sendNotification.class);
        PendingIntent cancelIntent = PendingIntent.getBroadcast(getApplicationContext(), h.alarm_id, intent, PendingIntent.FLAG_NO_CREATE);

        // using alarm manager to cancel the alarm
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.cancel(cancelIntent);

        // testing
//        Log.d("---NOTIF FOR DELETE","old notification for "+ h.hname+ " with id "+ h.alarm_id+" to go at "+h.hr+":"+h.min);
    }

    /**
     * private method to check if all fields are filled out
     * @return true if filled, false if not
     */
    private boolean isAnswered(){
        if (((EditText) findViewById(R.id.identityTxt)).getText().toString().equals("") ||
                ((EditText) findViewById(R.id.habitTxt)).getText().toString().equals("") ||
                ((EditText) findViewById(R.id.connectionTxt)).getText().toString().equals("") ||
                ((EditText) findViewById(R.id.freqTxt)).getText().toString().equals("") ||
                ((EditText) findViewById(R.id.cueTxt)).getText().toString().equals("") ||
                ((EditText) findViewById(R.id.cravingTxt)).getText().toString().equals("") ||
                ((EditText) findViewById(R.id.responseTxt)).getText().toString().equals("") ||
                ((EditText) findViewById(R.id.rewardTxt)).getText().toString().equals("")
        ) return false;
        else return true;
    }

    /**
     * method to download habit info
     */
    private void writeFile() throws JSONException, FileNotFoundException {
        String hname = ((EditText) findViewById(R.id.habitTxt)).getText().toString();
        String myDir = Environment.getExternalStorageDirectory() +"/Documents/"+hname+".txt"; //creating a file in the internal storage/Documents folder on phone.
        Log.d("---PrintDir","====="+myDir);
        File file = new File(myDir);    //creating a file object
        JSONObject habitJSON = new JSONObject();   //create a JSONObject
        habitJSON.put("Identity", ((EditText) findViewById(R.id.identityTxt)).getText().toString());
        habitJSON.put("HabitName", hname);
        habitJSON.put("Why", ((EditText) findViewById(R.id.connectionTxt)).getText().toString());
        habitJSON.put("Frequency", ((EditText) findViewById(R.id.freqTxt)).getText().toString());
        habitJSON.put("Reminder", ((CheckBox) findViewById(R.id.reminderCB)).isChecked() ? "on" : "off");
        habitJSON.put("Time", hr+":"+min);
        habitJSON.put("Cue", ((EditText) findViewById(R.id.cueTxt)).getText().toString());
        habitJSON.put("Craving", ((EditText) findViewById(R.id.cravingTxt)).getText().toString());
        habitJSON.put("Response", ((EditText) findViewById(R.id.responseTxt)).getText().toString());
        habitJSON.put("Reward", ((EditText) findViewById(R.id.rewardTxt)).getText().toString());

        //Write to the file and store in internal storage
        FileOutputStream fOut = new FileOutputStream(file, true); //create a file output stream for writing data to file
        OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);  //converts character stream into byte stream
        try {
            myOutWriter.append(habitJSON.toString() + "\n");  //write JSONObject to file
            myOutWriter.close();
            fOut.close();
            Toast.makeText(getApplicationContext(), "Habit has been downloaded", Toast.LENGTH_LONG).show();
        }
        catch (Exception e){
            e.printStackTrace();  //to handle exceptions and errors.
        }
    }
}