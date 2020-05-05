package com.example.bcs421finalexam;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class EmployeeFragment extends Fragment {

    private EditText mEmailEditText;
    private EditText mFirstEditText;
    private EditText mLastEditText;
    private EditText mDepartmentEditText;
    private EditText mPositionEditText;
    private FirebaseFirestore db;

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_employee, container, false);

        mEmailEditText = v.findViewById(R.id.EFEditTextEmail);
        mFirstEditText = v.findViewById(R.id.EFEditTextFirst);
        mLastEditText = v.findViewById(R.id.EFEditTextLast);
        mDepartmentEditText = v.findViewById(R.id.EFEditTextDepartment);
        mPositionEditText = v.findViewById(R.id.EFEditTextPosition);
        db = FirebaseFirestore.getInstance();



        return v;
    }

//    Added in (not in specs) but will update the data in this fragment if it's updated
    public void onStart(){
        super.onStart();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String email = user.getEmail();

        db.collection("FinalExamEmployees").whereEqualTo("email", email).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()){
                        String email = document.getString("email");
                        mEmailEditText.setText(email);

                        String first = document.getString("first");
                        mFirstEditText.setText(first);

                        String last = document.getString("last");
                        mLastEditText.setText(last);

                        String department = document.getString("department");
                        mDepartmentEditText.setText(department);

                        String position = document.getString("position");
                        mPositionEditText.setText(position);
                    }
                }
                else {
                    Log.d("MYDEBUG", "Can't get document");
                }
            }
        });
    }

}
