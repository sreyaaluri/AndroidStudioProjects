package com.example.depressivesymptomatologyresearch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

public class psychomotorchanges_questionnaire extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_psychomotorchanges_questionnaire);

        // setting click listener on submit button
        Button nextBtn = findViewById(R.id.nextBtn);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean answered = checkAnswered();
                if(answered) { // navigate to next page
                    // get scores (no update in this page)
                    int slowedscore = getSlowedScore(); // get slowed score
                    int restlessScore = getRestlessScore(); // get slowed score
                    int total_score = Integer.parseInt(getIntent().getStringExtra("SCORE"));
                    total_score += Math.max(slowedscore, restlessScore); // add to total score

                    // launch next page
                    Intent next = new Intent(getApplicationContext(), End.class);
                    next.putExtra("SCORE", ""+total_score);
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
    private int getSlowedScore() {
        RadioGroup pmGrp = findViewById(R.id.slowedBtnGrp);
        int slowedValue = -1;
        switch(pmGrp.getCheckedRadioButtonId()) {
            case R.id.zeroSlowRB:
                slowedValue = 0;
                break;
            case R.id.oneSlowRB:
                slowedValue = 1;
                break;
            case R.id.twoSlowRB:
                slowedValue = 2;
                break;
            case R.id.threeSlowRB:
                slowedValue = 3;
                break;
        }
        return slowedValue;
    }

    // returns the QIDS score for this section based on user's selection
    private int getRestlessScore() {
        RadioGroup pmGrp = findViewById(R.id.restlessBtnGrp);
        int restlessValue = -1;
        switch(pmGrp.getCheckedRadioButtonId()) {
            case R.id.zeroRestlessRB:
                restlessValue = 0;
                break;
            case R.id.oneRestlessRB:
                restlessValue = 1;
                break;
            case R.id.twoRestlessRB:
                restlessValue = 2;
                break;
            case R.id.threeRestlessRB:
                restlessValue = 3;
                break;
        }
        return restlessValue;
    }

    // returns True if all questions on current page have been answered; else returns False
    private boolean checkAnswered() {
        RadioGroup slowedGrp = findViewById(R.id.slowedBtnGrp);
        RadioGroup restlessGrp = findViewById(R.id.restlessBtnGrp);
        return(slowedGrp.getCheckedRadioButtonId() != -1 && restlessGrp.getCheckedRadioButtonId() != -1);
    }
}