package com.example.moodtracker;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Fragment;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

public class Tracker extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracker);
    }

    public void toggleMood(View view) {
        ImageButton btn = (ImageButton) view;
        btn.setSelected(true);
    }

    // method to add a fragement
    private void addFragment(Fragment fragment){
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.trackerContainer, fragment);
        ft.commit();
        // TODO use addFragment(new Medication()); somewhere
    }
}