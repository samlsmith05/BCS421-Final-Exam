package com.example.bcs421finalexam;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ChooseDepartmentActivity extends AppCompatActivity {

    private List<Department> departmentList = new ArrayList<>();
    private RecyclerView recyclerView;
    private DepartmentAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_department);

        recyclerView = (RecyclerView) findViewById(R.id.CDRecyclerView);
        mAdapter = new DepartmentAdapter(departmentList);
        RecyclerView.LayoutManager mLayoutManager =
                new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        departmentList.add(new Department(getResources().getString(R.string.CDAIT)));
        departmentList.add(new Department(getResources().getString(R.string.CDASales)));
        departmentList.add(new Department(getResources().getString(R.string.CDAHR)));
        mAdapter.notifyDataSetChanged();

        mAdapter.setOnItemClickListener(new DepartmentAdapter.DepartmentAdapterListener() {
            @Override
            public void onItemClick(int position) {
                Department selectedDepartment = departmentList.get(position);
                String dept = selectedDepartment.getDepartment();

                Intent result = new Intent();
                result.putExtra("dept", dept);

                setResult(RESULT_OK, result);

                finish();

            }
        });

    }
}