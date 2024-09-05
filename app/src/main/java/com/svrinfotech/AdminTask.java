package com.svrinfotech;


import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.svrinfotech.adapter.UserAdapter;
import com.svrinfotech.pojo.UserPojo;
import com.svrinfotech.touch.RecyclerTouchListener;

import java.util.ArrayList;
import java.util.Iterator;


/**
 * A simple {@link Fragment} subclass.
 */
public class AdminTask extends Fragment {


    public AdminTask() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_admin_task, container, false);
        final RecyclerView userRecyclerView=rootView.findViewById(R.id.usersRecyclerView);
        final ArrayList<UserPojo> userList=new ArrayList<>();
        final UserAdapter userAdapter=new UserAdapter(userList);

        DatabaseReference userReference= FirebaseDatabase.getInstance().getReference().child("users");
        userReference.keepSynced(true);

        userReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> iterator=dataSnapshot.getChildren().iterator();
                Log.e("Count : ",dataSnapshot.getChildrenCount()+"");
                while (iterator.hasNext()) {
                    DataSnapshot users=iterator.next();
                    UserPojo user=users.getValue(UserPojo.class);
                    if(user.getStatus().equals("none")) {
                        userList.add(user);
                        userAdapter.notifyDataSetChanged();
                    }
                }

                userRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity().getBaseContext(),
                        userRecyclerView,
                        new RecyclerTouchListener.ClickListener() {
                            @Override
                            public void onClick(View view, int position) {
                                UserPojo user=userList.get(position);
                                Fragment fragment=new ChangeUserDetails();
                                Bundle bundle=new Bundle();
                                bundle.putSerializable("user",user);
                                fragment.setArguments(bundle);
                                userRecyclerView.setVisibility(View.GONE);
                                getFragmentManager().beginTransaction().replace(R.id.adminTaskLayout,fragment).commit();
                            }

                            @Override
                            public void onHold(View view, int position) {

                            }
                        }));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getActivity().getApplicationContext());
        userRecyclerView.setLayoutManager(layoutManager);
        userRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        userRecyclerView.setAdapter(userAdapter);
        return rootView;
    }
}