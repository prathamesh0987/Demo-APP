package com.svrinfotech;


import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.svrinfotech.adapter.StudentAdapter;
import com.svrinfotech.pojo.Admission;
import com.svrinfotech.pojo.EndUser;
import com.svrinfotech.touch.RecyclerTouchListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShowRecords extends Fragment implements View.OnClickListener {

    TextView total;
    RecyclerView displayRecord;
    EditText searchName;
    ImageButton search;
    DatabaseReference admissionReference;
    ArrayList<Admission> admissions,selectedAdmissions;
    StudentAdapter studentAdapter,selectedStudentsAdapter;
    String subject;
    public ShowRecords() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_show_records, container, false);
        subject=getArguments().getString("subject");
        Log.e("Subject : ",subject);
        init(rootView);
        search.setOnClickListener(this);
        // Inflate the layout for this fragment
        return rootView;
    }


    @Override
    public void onResume() {
        super.onResume();

        admissionReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> iterator=dataSnapshot.getChildren().iterator();
                while (iterator.hasNext()) {
                    DataSnapshot childDataSnapshot=iterator.next();
                    Admission admission=childDataSnapshot.getValue(Admission.class);
                    admissions.add(admission);
                    studentAdapter.notifyDataSetChanged();
                }

                if(admissions.isEmpty()) {
                    Snackbar snackbar=Snackbar.make(getActivity().findViewById(android.R.id.content),
                            "NO ADMISSIONS YET FOR THIS SUBJECT",
                            Snackbar.LENGTH_LONG);
                    snackbar.show();
                }

                StringBuffer stringBuffer=new StringBuffer().append("Total : ").append(admissions.size());
                total.setText(stringBuffer.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        final String email= FirebaseAuth.getInstance().getCurrentUser().getEmail();

        List<EndUser> loggedUserList=EndUser.find(EndUser.class,"email=?",email);

        EndUser loggedUser=loggedUserList.get(0);

        final String status=loggedUser.getStatus();

        displayRecord.addOnItemTouchListener(new RecyclerTouchListener(getActivity().getApplicationContext(),
                displayRecord,
                new RecyclerTouchListener.ClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        switch (status) {
                            case "admin":
                                Admission admission=admissions.get(position);
                                Fragment fragment=new ManageFee();
                                Bundle bundle=new Bundle();
                                bundle.putSerializable("admission",admission);
                                fragment.setArguments(bundle);
                                displayRecord.setVisibility(View.GONE);
                                getActivity().findViewById(R.id.total).setVisibility(View.GONE);
                                search.setVisibility(View.GONE);
                                searchName.setVisibility(View.GONE);
                                getFragmentManager().beginTransaction()
                                        .replace(R.id.displayRecordsLayout,fragment)
                                        .commit();
                                break;
                        }
                    }

                    @Override
                    public void onHold(View view, int position) {

                    }
                }));



        /*FirebaseDatabase.getInstance().getReference().child("users").addValueEventListener(new ValueEventListener() {
            String status;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterator iterator=dataSnapshot.getChildren().iterator();
                while (iterator.hasNext()) {
                    DataSnapshot user=(DataSnapshot) iterator.next();
                    Log.e("Data ",user.getKey()+" "+user.getValue());
                    *//*UserPojo userPojo=user.getValue(UserPojo.class);
                    if(userPojo.getUsername().equalsIgnoreCase(email)) {
                        status=userPojo.getStatus();
                    }*//*
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/

        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getActivity().getApplicationContext());
        displayRecord.setLayoutManager(layoutManager);
        displayRecord.setHasFixedSize(true);
        displayRecord.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        displayRecord.setAdapter(studentAdapter);
    }

    private void init(View view) {
        total=view.findViewById(R.id.total);
        displayRecord=view.findViewById(R.id.studentRecord);
        searchName=view.findViewById(R.id.searchName);
        search=view.findViewById(R.id.searchButton);
        admissionReference= FirebaseDatabase.getInstance().getReference().child("admission").child(subject);
        admissionReference.keepSynced(true);
        admissions=new ArrayList<>();
        studentAdapter=new StudentAdapter(admissions);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.searchButton:
                total.setVisibility(View.GONE);
                final String name=searchName.getText().toString().trim();
                selectedAdmissions=new ArrayList<>();
                selectedStudentsAdapter=new StudentAdapter(selectedAdmissions);
                if(TextUtils.isEmpty(name)) {
                    Toast.makeText(getActivity().getApplicationContext(), "Please Enter Name", Toast.LENGTH_LONG).show();
                } else {
                    admissionReference.child(subject).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.hasChild(name)) {
                                Admission admission=dataSnapshot.child(name).getValue(Admission.class);
                                selectedAdmissions.add(admission);
                                selectedStudentsAdapter.notifyDataSetChanged();
                            } else {
                                Toast.makeText(getActivity().getApplicationContext(),"No Records Found",Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    displayRecord.setAdapter(selectedStudentsAdapter);
                }
        }
    }
}
