package com.example.depressivesymptomatologyresearch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

public class energy_questionnaire extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_energy_questionnaire);

        // setting click listener on submit button
        Button nextBtn = findViewById(R.id.nextBtn);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean answered = checkAnswered();
                if(answered) { // navigate to next page
                    // get scores (no update in this page)
                    int energyScore = getEnergyScore(); // getScore
                    int total_score = Integer.parseInt(getIntent().getStringExtra("SCORE"));
                    total_score += energyScore; // add to total score

                    // launch next page
                    Intent next = new Intent(getApplicationContext(), psychomotorchanges_questionnaire.class);
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
    private int getEnergyScore() {
        RadioGroup energyGrp = findViewById(R.id.energyBtnGrp);
        int energyValue = -1;
        switch(energyGrp.getCheckedRadioButtonId()) {
            case R.id.zeroEnergyRB:
                energyValue = 0;
                break;
            case R.id.oneEnergyRB:
                energyValue = 1;
                break;
            case R.id.twoEnergyRB:
                energyValue = 2;
                break;
            case R.id.threeEnergyRB:
                energyValue = 3;
                break;
        }
        return energyValue;
    }

    // returns True if all questions on current page have been answered; else returns False
    private boolean checkAnswered() {
        RadioGroup energyGrp = findViewById(R.id.energyBtnGrp);
        return(energyGrp.getCheckedRadioButtonId() != -1);
    }
}