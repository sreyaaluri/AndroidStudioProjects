package com.example.depressivesymptomatologyresearch;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class deaththoughts_questionnaire extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deaththoughts_questionnaire);

        // setting click listener on submit button
        Button nextBtn = findViewById(R.id.nextBtn);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean answered = checkAnswered();
                if(answered) { // navigate to next page
                    // get scores (no update in this page)
                    int thoughtsScore = getThoughtsScore(); // getScore
                    int total_score = Integer.parseInt(getIntent().getStringExtra("SCORE"));
                    total_score += thoughtsScore; // add to total score

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

    private int getThoughtsScore() {
        RadioGroup interestGrp = findViewById(R.id.deathgroupRB);
        int thoughtsValue = 0;
        switch(interestGrp.getCheckedRadioButtonId()) {
            case R.id.zerodeathRB:
                thoughtsValue = 0;
                break;
            case R.id.onedeathRB:
                thoughtsValue = 1;
                break;
            case R.id.twodeathRB:
                thoughtsValue = 2;
                break;
            case R.id.threedeathRB:
                thoughtsValue = 3;
                break;
        }
        return thoughtsValue;
    }

    private boolean checkAnswered() {
        RadioGroup deathGrp = findViewById(R.id.deathgroupRB);
        return(deathGrp.getCheckedRadioButtonId() != -1);
    }
}