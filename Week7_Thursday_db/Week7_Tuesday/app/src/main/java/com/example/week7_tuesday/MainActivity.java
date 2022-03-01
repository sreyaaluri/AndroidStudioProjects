package com.example.week7_tuesday;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private boolean loaded=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Context c = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn1 = findViewById(R.id.loadbtn);
        Button btn2 = findViewById(R.id.nextbtn);

        EditText nametxt = findViewById(R.id.namedisplay);
        EditText agetxt = findViewById(R.id.agedisplay);

        // perform setOnClickListener event on First Button
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
// load First Fragment
                loaded=false;
                Button edit=findViewById(R.id.editbtn);
                edit.setVisibility(View.INVISIBLE);
                nametxt.setText("");
                agetxt.setText("");
                loadFragment(new RegisterFragment());
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent disp = new Intent(MainActivity.this, display.class);
                startActivity(disp);

            }
        });


    }
    public void displayInfo() {
        EditText nametxt = findViewById(R.id.namedisplay);
        EditText agetxt = findViewById(R.id.agedisplay);

        DBClass db = new DBClass(this, "Users");
        String name = db.selectQuery("name");
        nametxt.setText(name);
        String age = db.selectQuery("age");
        agetxt.setText(age);
        nametxt.setClickable(true);
        agetxt.setClickable(true);
        loaded = true;
               Button edit=findViewById(R.id.editbtn);
        edit.setVisibility(View.VISIBLE);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nvalue=nametxt.getText().toString();
                String agevalue=agetxt.getText().toString();

                db.updateTable("name",nvalue,"age",agevalue);
                String nameval=db.selectQuery("name");
                String ageval=db.selectQuery("age");
                Log.d("==Updated==",nameval+","+ageval);
            }
        });

    }

    private void loadFragment(Fragment fragment) {
// create a FragmentManager
        FragmentManager fm = getFragmentManager();
// create a FragmentTransaction to begin the transaction and replace the Fragment
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
// replace the FrameLayout with new Fragment
        fragmentTransaction.replace(R.id.framelayout1, fragment);

        //   fragmentTransaction.replace(R.id.framelayout1, fragment);
        fragmentTransaction.commit(); // save the changes

    }


}