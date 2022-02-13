package com.example.depressivesymptomatologyresearch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

public class interest_questionnaire extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interest_questionnaire);

        // setting click listener on submit button
        Button nextBtn = findViewById(R.id.nextBtn);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean answered = checkAnswered();
                if(answered) { // navigate to next page
                    // get scores (no update in this page)
                    int interestScore = getInterestScore(); // getScore
                    int total_score = Integer.parseInt(getIntent().getStringExtra("SCORE"));
                    total_score += interestScore; // add to total score

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

    private int getInterestScore() {
        RadioGroup interestGrp = findViewById(R.id.interestBtnGrp);
        int interestValue = -1;
        switch(interestGrp.getCheckedRadioButtonId()) {
            case R.id.zeroInterestRB:
                interestValue = 0;
                break;
            case R.id.oneInterestRB:
                interestValue = 1;
                break;
            case R.id.twoInterestRB:
                interestValue = 2;
                break;
            case R.id.threeInterestRB:
                interestValue = 3;
                break;
        }
        return interestValue;
    }

    private boolean checkAnswered() {
        RadioGroup interestGrp = findViewById(R.id.interestBtnGrp);
        return(interestGrp.getCheckedRadioButtonId() != -1);
    }
}