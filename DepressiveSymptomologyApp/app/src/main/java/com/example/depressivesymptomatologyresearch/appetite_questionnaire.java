package com.example.depressivesymptomatologyresearch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

public class appetite_questionnaire extends AppCompatActivity {

    boolean increased = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appetite_questionnaire);

        // setting click listener on submit button
        Button nextBtn = findViewById(R.id.nextBtn);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean answered = checkAnswered();
                if(answered){ // navigate to next step
                    // get total score
                    int appetiteScore = getAppetiteScore(); // getScore
                    int total_score = Integer.parseInt(getIntent().getStringExtra("SCORE"));

                    // launch next page
                    Intent next = new Intent(getApplicationContext(), weight_questionnaire.class);
                    next.putExtra("SCORE", ""+total_score);
                    next.putExtra("APPETITE_SCORE", ""+appetiteScore);
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
    private int getAppetiteScore() {
        int apptValue = -1;
        if(increased){
            RadioGroup incrapptGrp = findViewById(R.id.incrappetiteBtnGrp);
            switch(incrapptGrp.getCheckedRadioButtonId()) {
                case R.id.zeroIncrApptRB:
                    apptValue = 0;
                    break;
                case R.id.oneIncrApptRB:
                    apptValue = 1;
                    break;
                case R.id.twoIncrApptRB:
                    apptValue = 2;
                    break;
                case R.id.threeIncrApptRB:
                    apptValue = 3;
                    break;
            }
        } else {
            RadioGroup decrapptGrp = findViewById(R.id.decrappetiteBtnGrp);
            switch (decrapptGrp.getCheckedRadioButtonId()) {
                case R.id.zeroDecrApptRB:
                    apptValue = 0;
                    break;
                case R.id.oneDecrApptRB:
                    apptValue = 1;
                    break;
                case R.id.twoDecrApptRB:
                    apptValue = 2;
                    break;
                case R.id.threeDecrApptRB:
                    apptValue = 3;
                    break;
            }
        }
        return apptValue;
    }

    // returns True if all questions on current page have been answered; else returns False
    private boolean checkAnswered(){
        RadioGroup apptGrp = findViewById(R.id.appetiteBtnGrp);
        if(increased){
            RadioGroup incrapptGrp = findViewById(R.id.incrappetiteBtnGrp);
            return(apptGrp.getCheckedRadioButtonId() != -1
                    && incrapptGrp.getCheckedRadioButtonId() != -1);
        }
        else{
            RadioGroup decrapptGrp = findViewById(R.id.decrappetiteBtnGrp);
            return(apptGrp.getCheckedRadioButtonId() != -1
                    && decrapptGrp.getCheckedRadioButtonId() != -1);
        }
    }

    // updating variables and display if user appetite increased
    public void setIncreased(View view) {
        increased = true;
        RadioGroup decrapptGrp = findViewById(R.id.decrappetiteBtnGrp);
        RadioGroup incrapptGrp = findViewById(R.id.incrappetiteBtnGrp);
        decrapptGrp.setVisibility(decrapptGrp.INVISIBLE);
        incrapptGrp.setVisibility(incrapptGrp.VISIBLE);
    }

    // updating variables and display if user appetite decreased
    public void setDecreased(View view) {
        increased = false;
        RadioGroup decrapptGrp = findViewById(R.id.decrappetiteBtnGrp);
        RadioGroup incrapptGrp = findViewById(R.id.incrappetiteBtnGrp);
        decrapptGrp.setVisibility(decrapptGrp.VISIBLE);
        incrapptGrp.setVisibility(incrapptGrp.INVISIBLE);
    }

}