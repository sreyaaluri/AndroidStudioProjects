package com.example.depressivesymptomatologyresearch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

public class appetite_questionnaire extends AppCompatActivity {

    boolean increased = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appetite_questionnaire);

        // setting click listener on submit button
        Button nextBtn = findViewById(R.id.nextBtn);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean answered = checkAnswered();
                if(answered){ // navigate to next step
                    // get total score
                    int total_score = Integer.parseInt(getIntent().getStringExtra("SCORE"));

                    // launch next page
                    if(increased) { // go to questionnaire for increased appetite
                        Intent next = new Intent(getApplicationContext(), increased_appetite_questionnaire.class);
                        next.putExtra("SCORE", ""+total_score);
                        Log.d("SCORE_CHECK", "--- "+total_score); // testing
                        startActivity(next);
                    }
                    else { // go to questionnaire for decreased appetite
                        Intent next = new Intent(getApplicationContext(), decreased_appetite_questionnaire.class);
                        next.putExtra("SCORE", ""+total_score);
                        Log.d("SCORE_CHECK", "--- "+total_score); // testing
                        startActivity(next);
                    }
                }
                else { // display helpful message if question(s) remain unanswered
                    TextView msg = findViewById(R.id.msgLbl);
                    msg.setText("Please answer all questions to continue.");
                }
            }
        });
    }

    public void setIncreased(View view) { increased = true; }

    public void setDecreased(View view) { increased = false; }

    private boolean checkAnswered(){
        RadioButton decreasedRB = findViewById(R.id.decreasedRB);
        RadioButton increasedRB = findViewById(R.id.increasedRB);
        if(decreasedRB.isChecked() || increasedRB.isChecked())
            return true;
        return false;
    }
}