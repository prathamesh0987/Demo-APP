package com.svrinfotech.holder;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.svrinfotech.R;

/**
 * Created by Durgesh on 12-Mar-18.
 */

public class SyllabusHolder extends RecyclerView.ViewHolder {
    TextView person,joinFor,joiningDate;
    public SyllabusHolder(View itemView) {
        super(itemView);
        person=itemView.findViewById(R.id.person);
        joinFor=itemView.findViewById(R.id.joinFor);
        joiningDate=itemView.findViewById(R.id.joiningDate);
    }

    public void setName(String personName) {
        this.person.setText(personName);
    }

    public void setJoinFor(String date) {
        joinFor.setText(date);
    }

    public void setJoiningDate(String date) {
        joiningDate.setText(date);
    }
}
