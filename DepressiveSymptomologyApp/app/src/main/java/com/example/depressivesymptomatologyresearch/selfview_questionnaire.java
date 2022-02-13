package com.example.depressivesymptomatologyresearch;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class selfview_questionnaire extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selfview_questionnaire);

        // setting click listener on submit button
        Button nextBtn = findViewById(R.id.nextBtn);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean answered = checkAnswered();
                if(answered) { // navigate to next page
                    // get scores (no update in this page)
                    int selfScore = getSelfViewScore(); // getScore
                    int total_score = Integer.parseInt(getIntent().getStringExtra("SCORE"));
                    total_score += selfScore; // add to total score

                    // launch next page
                    Intent next = new Intent(getApplicationContext(), deaththoughts_questionnaire.class);
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
    private int getSelfViewScore() {
        RadioGroup selfviewGrp = findViewById(R.id.viewmyselfgroupRB);
        int viewValue = -1;
        switch(selfviewGrp.getCheckedRadioButtonId()) {
            case R.id.zeroviewmyselfRB:
                viewValue = 0;
                break;
            case R.id.oneviewmyselfRB:
                viewValue = 1;
                break;
            case R.id.twoviewmyselfRB:
                viewValue = 2;
                break;
            case R.id.threeviewmyselfRB:
                viewValue = 3;
                break;
        }
        return viewValue;
    }

    // returns True if all questions on current page have been answered; else returns False
    private boolean checkAnswered() {
        RadioGroup selfviewGrp = findViewById(R.id.viewmyselfgroupRB);
        return(selfviewGrp.getCheckedRadioButtonId() != -1);
    }
}