package com.example.depressivesymptomatologyresearch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

public class mood_questionnaire extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_questionnaire);
        Log.d("LAUNCH_CHECK", "mood activity launched"); // testing

        // setting click listener on submit button
        Button nextBtn = findViewById(R.id.nextBtn);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean answered = checkAnswered();
                if(answered) { // navigate to next step
                    // update score
                    int moodScore = getMoodScore(); // get score
                    int total_score = Integer.parseInt(getIntent().getStringExtra("SCORE")); // get total score
                    total_score += moodScore; // add to total score

                    // launch next page
                    Intent next = new Intent(getApplicationContext(), appetite_questionnaire.class);
                    next.putExtra("SCORE", ""+total_score);
                    Log.d("SCORE_CHECK", "--- "+total_score); // testing
                    startActivity(next);
                }
                else { // display helpful message if question(s) remain unanswered
                    TextView msg = findViewById(R.id.msgLbl);
                    msg.setText("Please answer all questions to continue.");
                }
            }
        });
    }

    // returns the QIDS score for this section based on user's selection
    private int getMoodScore() {
        RadioGroup moodGrp = findViewById(R.id.moodBtnGrp);
        int moodValue = -1;
        switch(moodGrp.getCheckedRadioButtonId()) {
            case R.id.zeroMoodRB:
                moodValue = 0;
                break;
            case R.id.oneMoodRB:
                moodValue = 1;
                break;
            case R.id.twoMoodRB:
                moodValue = 2;
                break;
            case R.id.threeMoodRB:
                moodValue = 3;
                break;
        }
        return moodValue;
    }

    // returns True if all questions on current page have been answered; else returns False
    private boolean checkAnswered() {
        RadioGroup moodGrp = findViewById(R.id.moodBtnGrp);
        return(moodGrp.getCheckedRadioButtonId() != -1);
    }

}