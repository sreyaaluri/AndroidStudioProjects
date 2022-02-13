package com.example.depressivesymptomatologyresearch;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class concentration_questionnaire extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_concentration_questionnaire);

        // setting click listener on submit button
        Button nextBtn = findViewById(R.id.nextBtn);
        nextBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                boolean answered = checkAnswered();
                if(answered) { // navigate to next page
                    // get scores (no update in this page)
                    int concentrationScore = getConcentrationScore(); // getScore
                    int total_score = Integer.parseInt(getIntent().getStringExtra("SCORE"));
                    total_score += concentrationScore; // add to total score

                    // launch next page
                    Intent next = new Intent(getApplicationContext(), selfview_questionnaire.class);
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
    private int getConcentrationScore() {
        RadioGroup interestGrp = findViewById(R.id.concentrationgroupRB);
        int concentrationValue = -1;
        switch(interestGrp.getCheckedRadioButtonId()) {
            case R.id.zeroconcentrationRB:
                concentrationValue = 0;
                break;
            case R.id.oneconcentrationRB:
                concentrationValue = 1;
                break;
            case R.id.twoconcentrationRB:
                concentrationValue = 2;
                break;
            case R.id.threeconcentrationRB:
                concentrationValue = 3;
                break;
        }
        return concentrationValue;
    }

    // returns True if all questions on current page have been answered; else returns False
    private boolean checkAnswered() {
        RadioGroup concentrationGrp = findViewById(R.id.concentrationgroupRB);
        return(concentrationGrp.getCheckedRadioButtonId() != -1);
    }
}