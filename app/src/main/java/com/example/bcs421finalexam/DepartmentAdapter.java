package com.example.bcs421finalexam;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DepartmentAdapter extends RecyclerView.Adapter<DepartmentAdapter.MyViewHolder> {
    interface DepartmentAdapterListener{
        void onItemClick(int position);
    }

    private List<Department> departmentList;
    private DepartmentAdapterListener listener;

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView mTextViewDept;

        public MyViewHolder(View view){
            super(view);
            mTextViewDept = (TextView) view.findViewById(R.id.DRVITextViewDept);

            view.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                } });

        }
    }

    public DepartmentAdapter(List<Department> departmentList){
        this.departmentList = departmentList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.department_recycler_view_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Department department = departmentList.get(position);
        String dept = department.getDepartment();

        holder.mTextViewDept.setText(dept);
    }

    @Override
    public int getItemCount() {
        return departmentList.size();
    }

    public void setData(List<Department> departmentList) {
        this.departmentList = departmentList;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(DepartmentAdapterListener listener) {
        this.listener = listener;
    }



}
