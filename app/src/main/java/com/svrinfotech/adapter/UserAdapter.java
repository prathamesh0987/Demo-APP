package com.svrinfotech.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.svrinfotech.R;
import com.svrinfotech.pojo.UserPojo;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserHolder> {

    ArrayList<UserPojo> userList;

    public UserAdapter(ArrayList<UserPojo> userList) {
        this.userList = userList;
    }

    public class UserHolder extends RecyclerView.ViewHolder {
        TextView name,contactno;
        public UserHolder(View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.userName);
            contactno=itemView.findViewById(R.id.contact);
        }
    }


    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.users_row,parent,false);
        return new UserHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserHolder holder, int position) {
        UserPojo user=userList.get(position);
        StringBuffer stringBuffer=new StringBuffer("Name : ").append(user.getName());
        holder.name.setText(stringBuffer.toString());
        stringBuffer=new StringBuffer("Contact No : ").append(user.getMobile());
        holder.contactno.setText(stringBuffer.toString());
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }
}
