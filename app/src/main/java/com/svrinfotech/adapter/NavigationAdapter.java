package com.svrinfotech.adapter;

import android.app.Activity;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.svrinfotech.R;
import com.svrinfotech.pojo.Drawer;

public class NavigationAdapter extends ArrayAdapter<Drawer> {

    private Context context;
    private int layoutResourceId;
    private Drawer[] drawer;


    public NavigationAdapter(@NonNull Context context, int resource, @NonNull Drawer[] drawer) {
        super(context, resource, drawer);
        this.context=context;
        layoutResourceId=resource;
        this.drawer=drawer;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem=convertView;
        LayoutInflater inflater=((Activity)context).getLayoutInflater();
        listItem=inflater.inflate(layoutResourceId,parent,false);
        ImageView imageView=listItem.findViewById(R.id.menu_icon);
        TextView textView=listItem.findViewById(R.id.menu_title);
        Drawer drawerPojo=drawer[position];
        imageView.setImageResource(drawerPojo.getIcon());
        textView.setText(drawerPojo.getName());
        return listItem;    }
}