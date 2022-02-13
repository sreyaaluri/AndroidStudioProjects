package com.example.depressivesymptomatologyresearch;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class sleep_questionnaire extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep_questionnaire);

        // setting click listener on submit button
        Button nextBtn = findViewById(R.id.nextBtn);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean answered = checkAnswered();
                if(answered) { // navigate to next page
                    // get scores (no update in this page)
                    int fallingScore = getFallingScore(); // get score for falling asleep
                    int nightScore = getNightScore(); // get score for falling asleep
                    int wakingScore = getWakingScore(); // get score for falling asleep
                    int overScore = getOversleepScore(); // get score for falling asleep
                    int total_score = Integer.parseInt(getIntent().getStringExtra("SCORE"));
                    total_score += Math.max(Math.max(Math.max(fallingScore, nightScore), wakingScore), overScore); // add to total score

                    // launch next page
                    Intent next = new Intent(getApplicationContext(), energy_questionnaire.class);
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

    private int getFallingScore() {
        RadioGroup sleepGrp = findViewById(R.id.fallingSleepBtnGrp);
        int sleepValue = 0;
        switch(sleepGrp.getCheckedRadioButtonId()) {
            case R.id.zerofallsleepRB:
                sleepValue = 0;
                break;
            case R.id.onefallsleepRB:
                sleepValue = 1;
                break;
            case R.id.twofallsleepRB:
                sleepValue = 2;
                break;
            case R.id.threefallsleepRB:
                sleepValue = 3;
                break;
        }
        return sleepValue;
    }

    private int getNightScore() {
        RadioGroup sleepGrp = findViewById(R.id.nightSleepBtnGrp);
        int sleepValue = 0;
        switch(sleepGrp.getCheckedRadioButtonId()) {
            case R.id.zeronightsleepRB:
                sleepValue = 0;
                break;
            case R.id.onenightsleepRB:
                sleepValue = 1;
                break;
            case R.id.twonightsleepRB:
                sleepValue = 2;
                break;
            case R.id.threenightsleepRB:
                sleepValue = 3;
                break;
        }
        return sleepValue;
    }

    private int getWakingScore() {
        RadioGroup sleepGrp = findViewById(R.id.wakeupBtnGrp);
        int sleepValue = 0;
        switch(sleepGrp.getCheckedRadioButtonId()) {
            case R.id.zerowakeearlyRB:
                sleepValue = 0;
                break;
            case R.id.onewakeearlyRB:
                sleepValue = 1;
                break;
            case R.id.twowakeearlyRB:
                sleepValue = 2;
                break;
            case R.id.threewakeearlyRB:
                sleepValue = 3;
                break;
        }
        return sleepValue;
    }

    private int getOversleepScore() {
        RadioGroup sleepGrp = findViewById(R.id.sleepTooMuchBtnGrp);
        int sleepValue = 0;
        switch(sleepGrp.getCheckedRadioButtonId()) {
            case R.id.zeromuchsleepRB:
                sleepValue = 0;
                break;
            case R.id.onemuchsleepRB:
                sleepValue = 1;
                break;
            case R.id.twomuchsleepRB:
                sleepValue = 2;
                break;
            case R.id.threemuchsleepRB:
                sleepValue = 3;
                break;
        }
        return sleepValue;
    }

    private boolean checkAnswered() {
        RadioGroup fallingSleepGrp = findViewById(R.id.fallingSleepBtnGrp);
        RadioGroup nightSleepGrp = findViewById(R.id.nightSleepBtnGrp);
        RadioGroup wakeGrp = findViewById(R.id.wakeupBtnGrp);
        RadioGroup sleepMuchGrp = findViewById(R.id.sleepTooMuchBtnGrp);
        return(fallingSleepGrp.getCheckedRadioButtonId() != -1
                && nightSleepGrp.getCheckedRadioButtonId() != -1
                && wakeGrp.getCheckedRadioButtonId() != -1
                && sleepMuchGrp.getCheckedRadioButtonId() != -1);
    }
}