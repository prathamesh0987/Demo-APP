package com.svrinfotech;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.svrinfotech.pojo.UserPojo;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChangeUserDetails extends Fragment {
    private TextView username,contact,mail;
    private AutoCompleteTextView userStatus;
    private MultiAutoCompleteTextView courses;
    private Button update;
    private UserPojo user;
    private LinearLayout rootLayout;
    public ChangeUserDetails() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_change_user_details, container, false);
        String[] status={"admin","owner","employee","student"};
        String[] coursesList={"AUTOCAD","CATIA","SOLIDWORKS","CREO","N-X","ANSYS","CFD","HYPERMESH","FEAST","JAVA","ANDROID","PYTHON","C PROGRAMMING","CPP","None"};
        init(rootView);
        ArrayAdapter<String> statusAdapter=new ArrayAdapter<>(
                getActivity().getBaseContext(),android.R.layout.simple_list_item_1,status);
        ArrayAdapter<String> coursesAdapter=new ArrayAdapter<>(
                getActivity().getBaseContext(),android.R.layout.simple_dropdown_item_1line,coursesList);


        courses.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        userStatus.setAdapter(statusAdapter);
        courses.setAdapter(coursesAdapter);

        user=(UserPojo) getArguments().getSerializable("user");
        StringBuffer stringBuffer=new StringBuffer("Name : ").append(user.getName());
        username.setText(stringBuffer.toString());
        stringBuffer=new StringBuffer("Contact No : ").append(user.getMobile());
        contact.setText(stringBuffer.toString());
        stringBuffer=new StringBuffer("Mail id : ").append(user.getUsername());
        mail.setText(stringBuffer.toString());

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        String userName=user.getName();
        final DatabaseReference userReference= FirebaseDatabase.getInstance().getReference().child("users").child(userName);
        userReference.keepSynced(true);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> subjectList=new ArrayList<>();
                String stat=userStatus.getText().toString();
                String subjects=courses.getText().toString();
                String[] subjectArray=subjects.split(",");
                for(String subject:subjectArray) {
                    subject=subject.trim();
                    subjectList.add(subject);
                }
                if(!TextUtils.isEmpty(stat) && !TextUtils.isEmpty(subjects)) {
                    int removeObjectAt=subjectArray.length-1;
                    subjectList.remove(removeObjectAt);

                    user.setCourse(subjectList);
                    user.setStatus(stat);
                    userReference.setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Snackbar.make(rootLayout,"User Updated...",Snackbar.LENGTH_LONG).show();
                        }
                    });
                } else {
                    Snackbar.make(rootLayout,"Please Fill Details", Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    private void init(View rootView) {
        username=rootView.findViewById(R.id.currentUser);
        contact=rootView.findViewById(R.id.currentUserContact);
        mail=rootView.findViewById(R.id.currentUserEmail);
        userStatus=rootView.findViewById(R.id.status);
        courses=rootView.findViewById(R.id.course);
        update=rootView.findViewById(R.id.updateUserDetails);
        rootLayout=rootView.findViewById(R.id.changeUserDetailsLayout);
    }
}
