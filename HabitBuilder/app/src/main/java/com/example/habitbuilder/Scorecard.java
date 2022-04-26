package com.example.habitbuilder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Scorecard extends AppCompatActivity {

    DBClass db = DBClass.getDBInstance(this); // getting database instance
    String uname = "";
    Calendar cal = Calendar.getInstance(); // current calendar

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scorecard);

        // getting values from intent and shared preferences
        uname = getSharedPreferences("UserInfo", MODE_PRIVATE).getString("username", "No User");

        // setting listener on header label
        TextView homeView = findViewById(R.id.headLbl);
        homeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // take user to home page
                Intent homeIntent = new Intent(getApplicationContext(), Home.class);
                startActivity(homeIntent);
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

        // setting listener for download button
        Button saveBtn = findViewById(R.id.downloadScorecardBtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    writeFile();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        // adding date change listener for calendar
        CalendarView calView = findViewById(R.id.calendarView);
        int currDay = cal.get(Calendar.DAY_OF_MONTH);     // initializing current day
        int currYear = cal.get(Calendar.YEAR);            // initializing current year
        int currMonth = cal.get(Calendar.MONTH);          // initializing current month
        calView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month,
                                            int dayOfMonth) {
                // checking if user selected date is valid
                if (dayOfMonth <= currDay && year <= currYear && month <= currMonth) { // valid
                    // clear previous scorecard
                    ((LinearLayout) findViewById(R.id.notesContainer)).removeAllViews();
                    // getting selected date
                    Calendar selectedCal = Calendar.getInstance();
                    selectedCal.set(year, month, dayOfMonth);
                    // displaying scorecard from selected date
                    displayScorecard(selectedCal.getTime());
                }
                else {
                    // setting focussed date on calendar to prev date
                    view.setDate(cal.getTimeInMillis());
                    displayScorecard(cal.getTime());
                    // using toast to tell user that alarm needs to be in future
                    Toast.makeText(getApplicationContext(), "Date needs to be in the past", Toast.LENGTH_LONG).show();
                }
            }
        });

        // display scorecard for current date
        displayScorecard(cal.getTime());
    }

    /**
     * private method to display scorecard from given date
     * @param date
     */
    private void displayScorecard(Date date){
        List<Activity> alist = db.getActivitiesByDate(uname, date);
        for(Activity a : alist) {
            addFragment(note.newInstance(a.name, a.rating)); // TOOD add proper param
        }
    }

    /**
     * private method to add a fragement
     * @param fragment
     */
    private void addFragment(Fragment fragment){
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.notesContainer, fragment);
        ft.commit();
    }

    /**
     * method to download habit info
     */
    private void writeFile() throws JSONException, FileNotFoundException {
        Toast.makeText(getApplicationContext(), "Scorecard has been downloaded", Toast.LENGTH_LONG).show();
//        String hname = ((EditText) findViewById(R.id.habitTxt)).getText().toString();
//        String myDir = Environment.getExternalStorageDirectory() +"/Documents/"+hname+".txt"; //creating a file in the internal storage/Documents folder on phone.
//        Log.d("---PrintDir","====="+myDir);
//        File file = new File(myDir);    //creating a file object
//        JSONObject habitJSON = new JSONObject();   //create a JSONObject
//        habitJSON.put("Identity", ((EditText) findViewById(R.id.identityTxt)).getText().toString());
//        habitJSON.put("HabitName", hname);
//        habitJSON.put("Why", ((EditText) findViewById(R.id.connectionTxt)).getText().toString());
//        habitJSON.put("Frequency", ((EditText) findViewById(R.id.freqTxt)).getText().toString());
//        habitJSON.put("Reminder", ((CheckBox) findViewById(R.id.reminderCB)).isChecked() ? "on" : "off");
//        habitJSON.put("Time", hr+":"+min);
//        habitJSON.put("Cue", ((EditText) findViewById(R.id.cueTxt)).getText().toString());
//        habitJSON.put("Craving", ((EditText) findViewById(R.id.cravingTxt)).getText().toString());
//        habitJSON.put("Response", ((EditText) findViewById(R.id.responseTxt)).getText().toString());
//        habitJSON.put("Reward", ((EditText) findViewById(R.id.rewardTxt)).getText().toString());
//
//        //Write to the file and store in internal storage
//        FileOutputStream fOut = new FileOutputStream(file, true); //create a file output stream for writing data to file
//        OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);  //converts character stream into byte stream
//        try {
//            myOutWriter.append(habitJSON.toString() + "\n");  //write JSONObject to file
//            myOutWriter.close();
//            fOut.close();
//            Toast.makeText(getApplicationContext(), "Habit has been downloaded", Toast.LENGTH_LONG).show();
//        }
//        catch (Exception e){
//            e.printStackTrace();  //to handle exceptions and errors.
//        }
    }
}