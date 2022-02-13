package com.example.depressivesymptomatologyresearch;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class weight_questionnaire extends AppCompatActivity {

    boolean increased = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight_questionnaire);

        // setting click listener on submit button
        Button nextBtn = findViewById(R.id.nextBtn);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean answered = checkAnswered();
                if (answered) { // navigate to next page
                    // get scores (no update in this page)
                    int weightScore = getWeightScore(); // getScore
                    int appetiteScore = Integer.parseInt(getIntent().getStringExtra("APPETITE_SCORE"));
                    int total_score = Integer.parseInt(getIntent().getStringExtra("SCORE"));
                    total_score += Math.max(appetiteScore, weightScore); // add to total score

                    // launch next page
                    Intent next = new Intent(getApplicationContext(), concentration_questionnaire.class);
                    next.putExtra("SCORE", "" + total_score);
                    Log.d("SCORE_CHECK", "--- " + total_score); // testing
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
    private int getWeightScore() {
        int weightValue = -1;
        if(increased){
            RadioGroup incrweightGrp = findViewById(R.id.incrweightgroupRB);
            switch (incrweightGrp.getCheckedRadioButtonId()) {
                case R.id.zeroincrweightRB:
                    weightValue = 0;
                    break;
                case R.id.oneincrweightRB:
                    weightValue = 1;
                    break;
                case R.id.twoincrweightRB:
                    weightValue = 2;
                    break;
                case R.id.threeincrweightRB:
                    weightValue = 3;
                    break;
            }
        } else {
            RadioGroup decrweightGrp = findViewById(R.id.decrweightgroupRB);
            switch (decrweightGrp.getCheckedRadioButtonId()) {
                case R.id.zerodecrweightRB:
                    weightValue = 0;
                    break;
                case R.id.onedecrweightRB:
                    weightValue = 1;
                    break;
                case R.id.twodecrweightRB:
                    weightValue = 2;
                    break;
                case R.id.threedecrweightRB:
                    weightValue = 3;
                    break;
            }
        }
        return weightValue;
    }

    // returns True if all questions on current page have been answered; else returns False
    private boolean checkAnswered() {
        RadioGroup weightGrp = findViewById(R.id.weightchoiceRB);
        if(increased){
            RadioGroup incrweightGrp = findViewById(R.id.incrweightgroupRB);
            return(weightGrp.getCheckedRadioButtonId() != -1
                    && incrweightGrp.getCheckedRadioButtonId() != -1);
        }
        else{
            RadioGroup decrweightGrp = findViewById(R.id.decrweightgroupRB);
            return(weightGrp.getCheckedRadioButtonId() != -1
                    && decrweightGrp.getCheckedRadioButtonId() != -1);
        }
    }

    // updating variables and display if user weight increased
    public void setIncreased(View view) {
        increased = true;
        RadioGroup decrweightGroup = findViewById(R.id.decrweightgroupRB);
        RadioGroup incrweightGroup = findViewById(R.id.incrweightgroupRB);
        incrweightGroup.setVisibility(decrweightGroup.VISIBLE);
        decrweightGroup.setVisibility(decrweightGroup.INVISIBLE);
    }

    // updating variables and display if user weight decreased
    public void setDecreased(View view) {
        increased = false;
        RadioGroup decrweightGroup = findViewById(R.id.decrweightgroupRB);
        RadioGroup incrweightGroup = findViewById(R.id.incrweightgroupRB);
        decrweightGroup.setVisibility(decrweightGroup.VISIBLE);
        incrweightGroup.setVisibility(decrweightGroup.INVISIBLE);
    }
}