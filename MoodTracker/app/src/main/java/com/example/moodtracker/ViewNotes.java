package com.example.moodtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.List;

public class ViewNotes extends AppCompatActivity {

    private String username = ""; // variable to store username

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_notes);
        username = getIntent().getStringExtra("UNAME");

        DBClass db = DBClass.getDBInstance(this);
        List<DiaryEntry> entryList = db.retrieveAllDiaryEntries(username);
        for(DiaryEntry de : entryList) {
            addFragment(Note.newInstance(de.getDate(), de.getNotes()));
        }
    }

    // method to add a fragement
    private void addFragment(Fragment fragment){
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.notesContainer, fragment);
        ft.commit();
    }
}