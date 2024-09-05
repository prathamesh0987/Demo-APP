package com.svrinfotech.holder;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.svrinfotech.R;

public class MessageHolder extends RecyclerView.ViewHolder {
    public TextView user,time,message;

    public MessageHolder(View itemView) {
        super(itemView);
        user=itemView.findViewById(R.id.user);
        time=itemView.findViewById(R.id.time);
        message=itemView.findViewById(R.id.message);
    }

    public void setUser(String user) {
        this.user.setText(user);
    }

    public void setTime(CharSequence time) {
        this.time.setText(time.toString());
    }

    public void setMessage(String message) {
        this.message.setText(message);
    }
}
