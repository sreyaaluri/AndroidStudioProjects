package com.example.depressivesymptomatologyresearch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class mood_questionnaire extends AppCompatActivity {
    boolean answered = true;
    int total_score = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_questionnaire);

        // setting click listener on submit button
        Button nextBtn = findViewById(R.id.nextBtn);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int moodScore = getMoodScore();

                // navigating to next step
                if(answered) { // navigate to next page if everything is answered
                    // add to total score
                    total_score = Integer.parseInt(getIntent().getStringExtra("SCORE"));
                    total_score += moodScore;
                    // launch next page
                    Intent next = new Intent(getApplicationContext(), appetite_questionnaire.class);
                    next.putExtra("SCORE", total_score);
                    Log.d("SCORE_CHECK", ""+total_score);
                    startActivity(next);
                }
                else { // display helpful message if question(s) remain unanswered
                    TextView msg = findViewById(R.id.msgLbl);
                    msg.setText("Please answer all questions to continue.");
                }
            }
        });
    }

    private int getMoodScore() {
        RadioGroup moodGrp = findViewById(R.id.moodBtnGrp);
        int moodValue = 0;
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
            default:
                answered = false;
        }
        return moodValue;
    }

}