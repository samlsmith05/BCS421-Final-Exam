package com.example.bcs421finalexam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText mUsernameEditText;
    private EditText mPasswordEditText;
    private Button mLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        mUsernameEditText = findViewById(R.id.MEditTextUsername);
        mPasswordEditText = findViewById(R.id.MEditTextPassword);
        mLoginButton = findViewById(R.id.MButtonLogin);

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEnteredEmail = mUsernameEditText.getText().toString();
                String password = mPasswordEditText.getText().toString();

                mAuth.signInWithEmailAndPassword(userEnteredEmail, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Intent intent = new Intent(MainActivity.this, EmployeeActivity.class);
                            startActivity(intent);
                        }
                        else{
                            String fail = getResources().getString(R.string.MainFailToast);
                            Toast.makeText(getApplicationContext(),fail, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

//                mUsernameEditText.getText().clear();
//                mPasswordEditText.getText().clear();
            }
        });
    }
}
