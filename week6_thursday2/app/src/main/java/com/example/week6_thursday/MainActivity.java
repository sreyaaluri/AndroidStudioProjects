package com.example.week6_thursday;

import androidx.fragment.app.Fragment; // remember TODO change these three lines to the next lines
//import android.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // on click to display name and age
        Button displayNameAgeBtn = findViewById(R.id.displayNameNAgeFragBtn);
        displayNameAgeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new name_and_age_frag());
            }
        });

        // on click to display address
        Button displayAddressBtn = findViewById(R.id.displayAddressFragBtn);
        displayAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new address_frag());
            }
        });
    }

    private void loadFragment(Fragment fragment) {
        // create a Fragment Manager
        FragmentManager fm = getSupportFragmentManager();
        // create a Fragment Transaction to begin the transaction and replace the Fragment
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        //replace the FrameLayout with new Fragment
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit(); // save the changes
    }

    public void displayName(String name){
        TextView nameLbl = findViewById(R.id.nameLbl);
        nameLbl.setText(name);
    }

    public void displayAge(int age){
        TextView ageLbl = findViewById(R.id.ageLbl);
        ageLbl.setText(age+"");
    }

    public void displayAddress(String address){
        TextView addressLbl = findViewById(R.id.addressLbl);
        addressLbl.setText(address);
    }
}