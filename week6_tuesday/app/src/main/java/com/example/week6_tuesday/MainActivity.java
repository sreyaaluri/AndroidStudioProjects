package com.example.week6_tuesday;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Fragment;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //decreased weight fragment
        RadioButton decreasedWeight = findViewById(R.id.decreasedweightRB);
        decreasedWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                loadFragment(new decreased_fragment());
            }
        });

        //increased weight fragment
        RadioButton increasedWeight = findViewById(R.id.increasedweightRB);
        increasedWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                loadFragment(new incr_fragment());
            }
        });
    }

    private void loadFragment(Fragment fragment){
        // create a FragmentManager
        FragmentManager fm = getFragmentManager();
        // create a FragmentTransaction to begin the transaction and replace the Fragment
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        // replace the FrameLayout with new Fragment
        fragmentTransaction.replace(R.id.framelayout, fragment);
        fragmentTransaction.commit(); // save the changes }
    }
}