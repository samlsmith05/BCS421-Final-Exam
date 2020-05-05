package com.example.bcs421finalexam;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class UpdateEmployeeActivity extends AppCompatActivity {

    private EditText mEmailEditText;
    private EditText mFirstEditText;
    private EditText mLastEditText;
    private EditText mDepartmentEditText;
    private EditText mPositionEditText;
    private Button mChooseDeptButton;
    private Button mChoosePositionButton;
    private Button mSaveEmpButton;
    private FirebaseFirestore db;
    private Map<String, Object> EmpMap;
    private String docID;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_employee);

        mEmailEditText = findViewById(R.id.UEAEditTextEmail);
        mFirstEditText = findViewById(R.id.UEAEditTextFirst);
        mLastEditText = findViewById(R.id.UEAEditTextLast);
        mDepartmentEditText = findViewById(R.id.UEAEditTextDepartment);
        mPositionEditText = findViewById(R.id.UEAEditTextPosition);
        mChooseDeptButton = findViewById(R.id.UEAButtonChooseDept);
        mChoosePositionButton = findViewById(R.id.UEAButtonChoosePosition);
        mSaveEmpButton = findViewById(R.id.UEAButtonSaveEmployee);
        db = FirebaseFirestore.getInstance();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String email = user.getEmail();

        db.collection("FinalExamEmployees").whereEqualTo("email", email).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()){
                        String email = document.getString("email");
                        mEmailEditText.setText(email);
                    }
                }
                else {
                    Log.d("MYDEBUG", "Can't get document");
                }
            }
        });

        mChooseDeptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpdateEmployeeActivity.this, ChooseDepartmentActivity.class);
                startActivityForResult(intent, 2);
            }
        });

        mChoosePositionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpdateEmployeeActivity.this, ChoosePositionActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        mSaveEmpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String first = mFirstEditText.getText().toString();
                String last = mLastEditText.getText().toString();
                String department = mDepartmentEditText.getText().toString();
                String position = mPositionEditText.getText().toString();

                EmpMap = new HashMap<>();
                EmpMap.put("first", first);
                EmpMap.put("last", last);
                EmpMap.put("department", department);
                EmpMap.put("position", position);


                db.collection("FinalExamEmployees").whereEqualTo("email", email).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document : task.getResult()){
                                DocumentSnapshot doc = task.getResult().getDocuments().get(0);
                                docID = doc.getId();

                                db.collection("FinalExamEmployees").document(docID).update(EmpMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(getApplicationContext(), "Employee data updated", Toast.LENGTH_SHORT).show();

                                        }
                                        else {
                                            Toast.makeText(getApplicationContext(), "Update FAILED!", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                });
//                                finish();    // did not specify if needed
                            }
                        }
                        else{
                            Log.d("MYDEBUG", "Can't get the document");
                        }
                    }
                });




            }
        });

        mEmailEditText.setFocusable(false);
        mDepartmentEditText.setFocusable(false);
        mPositionEditText.setFocusable(false);


    }

    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent data)
    {

        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode) {
            case 1:
                if (resultCode == ChoosePositionActivity.RESULT_OK) {
                    String p = data.getStringExtra("position");
                    mPositionEditText.setText(p);
                }
                break;
            case 2:
                if (resultCode == ChooseDepartmentActivity.RESULT_OK) {
                    String d = data.getStringExtra("dept");
                    mDepartmentEditText.setText(d);
                }
                break;

        }
    }
}
