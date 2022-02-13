package com.example.depressivesymptomatologyresearch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

public class increased_appetite_questionnaire extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_increased_appetite_questionnaire);

        // setting click listener on submit button
        Button nextBtn = findViewById(R.id.nextBtn);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean answered = checkAnswered();
                if(answered) { // navigate to next page
                    // get scores (no update in this page)
                    int appetiteScore = getAppetiteScore();
                    int total_score = Integer.parseInt(getIntent().getStringExtra("SCORE"));

                    // launch next page
                    Intent next = new Intent(getApplicationContext(), interest_questionnaire.class);
                    next.putExtra("SCORE", ""+total_score);
                    Log.d("SCORE_CHECK", "--- "+total_score); // testing
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

    private int getAppetiteScore() {
        RadioGroup apptGrp = findViewById(R.id.appetiteBtnGrp);
        int apptValue = 0;
        switch(apptGrp.getCheckedRadioButtonId()) {
            case R.id.zeroApptRB:
            apptValue = 0;
                break;
            case R.id.oneApptRB:
                apptValue = 1;
                break;
            case R.id.twoApptRB:
                apptValue = 2;
                break;
            case R.id.threeApptRB:
                apptValue = 3;
                break;
        }
        return apptValue;
    }

    private boolean checkAnswered() {
        RadioGroup moodGrp = findViewById(R.id.appetiteBtnGrp);
        return(moodGrp.getCheckedRadioButtonId() != -1);
    }

}