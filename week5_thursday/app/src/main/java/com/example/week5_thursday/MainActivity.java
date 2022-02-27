package com.example.week5_thursday;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    String items[] = {"Apple, Banana, Orange"};
    double prices[] = {2.5, 1.5, 2.0};
    int selectedIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner itemSpinner = findViewById(R.id.itemSpinner);
        if (itemSpinner != null) {
            itemSpinner.setOnItemSelectedListener(this);
        }

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        itemSpinner.setAdapter(spinnerArrayAdapter);

        Button submitBtn = findViewById(R.id.submitBtn);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                TextView priceTxt = findViewById(R.id.priceLbl);
                double price = prices[selectedIndex] * getQty();
                priceTxt.setText(String.valueOf(price));
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int
            i, long l) {
        selectedIndex = i;
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // Auto-generated method stub }
    }

    private int getQty(){
        EditText qtyTxt = findViewById(R.id.qtyTxt);
        String qty = qtyTxt.getText().toString();
        if(qty.equals("")) return 0;
        return Integer.parseInt(qty);
    }
}
