package com.example.bcs421finalexam;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

public class ChoosePositionActivity extends AppCompatActivity {

    private RadioGroup mPositionsRadioGroup;
    private Button mSavePositionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_position);

        mPositionsRadioGroup = findViewById(R.id.CPARadioGroupPositions);
        mSavePositionButton = findViewById(R.id.CPAButtonSavePosition);

        mSavePositionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedPosition = mPositionsRadioGroup.getCheckedRadioButtonId();
                String position = "";

                switch (selectedPosition){
                    case R.id.CPARadioButtonProgrammer:
                        position = getResources().getString(R.string.CPAProgrammer);
                        break;
                    case R.id.CPARadioButtonManager:
                        position = getResources().getString(R.string.CPAManager);
                        break;
                    case R.id.CPARadioButtonSalesPerson:
                        position = getResources().getString(R.string.CPASalesPerson);
                        break;
                }

                Intent result = new Intent();

                result.putExtra("position", position);

                setResult(RESULT_OK, result);
                finish();
            }
        });
    }
}
