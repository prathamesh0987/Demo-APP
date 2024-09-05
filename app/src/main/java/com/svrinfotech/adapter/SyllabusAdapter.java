package com.svrinfotech.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.svrinfotech.R;
import com.svrinfotech.pojo.Admission;

import java.util.List;

/**
 * Created by Durgesh on 13-Mar-18.
 */

public class SyllabusAdapter extends RecyclerView.Adapter<SyllabusAdapter.SyllabusHolder> {

    private List<Admission> admissionList;
    private int lastPosition=-1;
    private Context context;

    public  SyllabusAdapter(List<Admission> admissionList,Context context) {
        this.admissionList=admissionList;
        this.context=context;
    }

    public class SyllabusHolder extends RecyclerView.ViewHolder {
        TextView person,joinFor,joiningDate;
        public SyllabusHolder(View itemView) {
            super(itemView);
            person=itemView.findViewById(R.id.person);
            joinFor=itemView.findViewById(R.id.joinFor);
            joiningDate=itemView.findViewById(R.id.joiningDate);
        }
    }

    @NonNull
    @Override
    public SyllabusHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.syllabus_row,parent,false);
        return new SyllabusHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SyllabusHolder holder, int position) {
        Admission admission=admissionList.get(position);
        StringBuffer stringBuffer=new StringBuffer();
        String name=stringBuffer.append("Name : ").append(admission.getName()).toString();
        holder.person.setText(name);
        stringBuffer=new StringBuffer();
        String jFor=stringBuffer.append("Join For : ").append(admission.getCourse()).toString();
        holder.joinFor.setText(jFor);
        stringBuffer=new StringBuffer();
        String jDate=stringBuffer.append("Joining Date : ").append(admission.getDoj()).toString();
        holder.joiningDate.setText(jDate);
        setAnimation(holder.itemView,position);
    }

    @Override
    public int getItemCount() {
        return admissionList.size();
    }

    public void setAnimation(View viewToAnimate, int position) {
        if(position>lastPosition) {
            Animation animation= AnimationUtils.loadAnimation(context,android.R.anim.slide_in_left);
            animation.setStartOffset(position*300);
            viewToAnimate.startAnimation(animation);
            lastPosition=position;
        }
    }
}
