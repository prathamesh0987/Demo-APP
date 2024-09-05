package com.svrinfotech.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.svrinfotech.R;
import com.svrinfotech.pojo.Admission;

import java.util.List;

/**
 * Created by Durgesh on 03-Mar-18.
 */

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentHolder> {
    private List<Admission> admissionList;

    public StudentAdapter(List<Admission> admissionList) {
        this.admissionList=admissionList;
    }


    public class StudentHolder extends RecyclerView.ViewHolder {
        TextView name,dob,contactno,email,qualification,course,fee,duration,doj;
        public StudentHolder(View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.displayName);
            dob=itemView.findViewById(R.id.displayDOB);
            contactno=itemView.findViewById(R.id.displayContact);
            email=itemView.findViewById(R.id.displayEmail);
            qualification=itemView.findViewById(R.id.displayQualification);
            course=itemView.findViewById(R.id.displayCourse);
            fee=itemView.findViewById(R.id.displayFee);
            duration=itemView.findViewById(R.id.displayDuration);
            doj=itemView.findViewById(R.id.displayDOJ);
        }
    }

    @NonNull
    @Override
    public StudentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.student_row,parent,false);
        return new StudentHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentHolder holder, int position) {
            Admission admission=admissionList.get(position);
            StringBuffer stringBuffer=new StringBuffer();
            stringBuffer=stringBuffer.append("Name : ").append(admission.getName());
            holder.name.setText(stringBuffer);
            stringBuffer=new StringBuffer().append("Date Of Birth : ").append(admission.getDob());
            holder.dob.setText(stringBuffer);
            stringBuffer=new StringBuffer().append("Contact No: ").append(admission.getContactno());
            holder.contactno.setText(stringBuffer);
            stringBuffer=new StringBuffer().append("E-Mail : ").append(admission.getEmail());
            holder.email.setText(stringBuffer);
            stringBuffer=new StringBuffer().append("Qualification : ").append(admission.getQualification());
            holder.qualification.setText(stringBuffer);
            stringBuffer=new StringBuffer().append("Course : ").append(admission.getCourse());
            holder.course.setText(stringBuffer);
            stringBuffer=new StringBuffer().append("Fee : ").append(admission.getFee());
            holder.fee.setText(stringBuffer);
            stringBuffer=new StringBuffer().append("Duration : ").append(admission.getDuration());
            holder.duration.setText(stringBuffer);
            stringBuffer=new StringBuffer().append("Date Of Joining : ").append(admission.getDoj());
            holder.doj.setText(stringBuffer);
    }

    @Override
    public int getItemCount() {
        return admissionList.size();
    }
}