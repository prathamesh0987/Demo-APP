package com.svrinfotech;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.svrinfotech.pojo.Admission;
import com.svrinfotech.pojo.CourseFee;
import com.svrinfotech.pojo.UserPojo;

import java.util.ArrayList;
import java.util.Iterator;


/**
 * A simple {@link Fragment} subclass.
 */
public class Admissions extends Fragment implements View.OnClickListener {

    DatabaseReference admissionReference;
    private EditText dateofbirth,contactno,email,qualification,coursefee,duration,joining;
    private AutoCompleteTextView name;
    private MultiAutoCompleteTextView courseTextView;
    private Button save;
    RadioGroup gender;
    private static String genderValue;
    private ArrayList<String> courseList;
    private Context baseContext;
    public Admissions() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_admission, container, false);
        init(rootView);
        save.setOnClickListener(this);
        baseContext=getActivity().getBaseContext();
        DatabaseReference userDatabaseReference=FirebaseDatabase.getInstance().getReference().child("users");
        userDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<String> userList=new ArrayList<>();
                Iterator<DataSnapshot> iterator=dataSnapshot.getChildren().iterator();
                while (iterator.hasNext()) {
                    DataSnapshot userData=iterator.next();
                    UserPojo userPojo=userData.getValue(UserPojo.class);
                    String user_name=userPojo.getName();
                    userList.add(user_name);
                }
                ArrayAdapter<String> arrayAdapter=new ArrayAdapter<>(
                        baseContext,
                        android.R.layout.simple_dropdown_item_1line,
                        userList
                );
                name.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Error : ",databaseError.getMessage());
            }
        });
        return rootView;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onResume() {
        super.onResume();

        gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.male:
                        genderValue="male";
                        break;
                    case R.id.female:
                        genderValue="female";
                        break;
                }
            }
        });

        /*String[] courseArray={"CAD","CAE","PROGRAMMING"};
        final String[] cadArray={"AUTOCAD","CATIA","SOLIDWORKS","CREO","N-X"};
        final String[] caeArray={"ANSYS","CFD","HYPERMESH","FEAST"};
        final String[] programmingArray={"JAVA","ANDROID","PYTHON"};*/
        //String[] courseArray={"AUTOCAD","CATIA","SOLIDWORKS","CREO","N-X","ANSYS","CFD","HYPERMESH","FEAST","JAVA","ANDROID","PYTHON"};
        /*ArrayAdapter courseAdapter,cadAdapter,caeAdapter,programmingAdapter;
        courseAdapter=new ArrayAdapter(getContext(),R.layout.support_simple_spinner_dropdown_item,courseArray);
        cadAdapter=new ArrayAdapter(getContext(),R.layout.support_simple_spinner_dropdown_item,cadArray);
        caeAdapter=new ArrayAdapter(getContext(),R.layout.support_simple_spinner_dropdown_item,caeArray);
        programmingAdapter=new ArrayAdapter(getContext(),R.layout.support_simple_spinner_dropdown_item,programmingArray);*/

        /*course.setAdapter(courseAdapter);
        cad.setAdapter(cadAdapter);
        cae.setAdapter(caeAdapter);
        programming.setAdapter(programmingAdapter);

        tempSpinner=new Spinner(getActivity());
        course.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        changeVisibility(cad,tempSpinner);
                        tempSpinner=cad;
                        break;
                    case 1:
                        changeVisibility(cae,tempSpinner);
                        tempSpinner=cae;
                        break;
                    case 2:
                        changeVisibility(programming,tempSpinner);
                        tempSpinner=programming;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        cad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        courseName=cadArray[position];
                        break;
                    case 1:
                        courseName=cadArray[position];
                        break;
                    case 2:
                        courseName=cadArray[position];
                        break;
                    case 3:
                        courseName=cadArray[position];
                        break;
                    case 4:
                        courseName=cadArray[position];
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        cae.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        courseName=caeArray[position];
                        break;
                    case 1:
                        courseName=caeArray[position];
                        break;
                    case 2:
                        courseName=caeArray[position];
                        break;
                    case 3:
                        courseName=caeArray[position];
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        programming.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        courseName=programmingArray[position];
                        break;
                    case 1:
                        courseName=programmingArray[position];
                        break;
                    case 2:
                        courseName=programmingArray[position];
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/

        String[] courseArray={"AUTOCAD","CATIA","SOLIDWORKS","CREO","N-X","ANSYS","CFD","HYPERMESH","FEAST","JAVA","ANDROID","PYTHON","C PROGRAMMING","CPP"};
        ArrayAdapter<String> coursesAdapter=new ArrayAdapter<>(
                getActivity().getBaseContext(),android.R.layout.simple_dropdown_item_1line,courseArray);
        courseTextView.setAdapter(coursesAdapter);
        courseTextView.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
    }


    private void init(View view) {
        admissionReference= FirebaseDatabase.getInstance().getReference().child("admission");
        admissionReference.keepSynced(true);
        save=view.findViewById(R.id.save);
        name=view.findViewById(R.id.studentName);
        dateofbirth=view.findViewById(R.id.dateOfBirth);
        gender=view.findViewById(R.id.genderValue);
        contactno=view.findViewById(R.id.contactNo);
        email=view.findViewById(R.id.email_id);
        qualification=view.findViewById(R.id.qualificationDetails);
        /*course=view.findViewById(R.id.courseName);
        cad=view.findViewById(R.id.cadCourse);
        cae=view.findViewById(R.id.caeCourse);
        programming=view.findViewById(R.id.programmingCourse);*/
        courseTextView=view.findViewById(R.id.coursename);
        coursefee=view.findViewById(R.id.courseFee);
        duration=view.findViewById(R.id.duration);
        joining=view.findViewById(R.id.dateOfJoining);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.save:
                saveRecord();
                break;
        }
    }

    private void saveRecord() {
            final String studentName=name.getText().toString().trim();
            String dobDate=dateofbirth.getText().toString().trim();
            String contactNo=contactno.getText().toString().trim();
            String email_id=email.getText().toString().trim();
            String qualificationDetails=qualification.getText().toString().trim();
            final String courseFee=coursefee.getText().toString().trim();
            String courseDuration=duration.getText().toString().trim();
            String dojDate=joining.getText().toString().trim();
            String joiningFor=courseTextView.getText().toString();
            final Fragment fragment=new Progressbar();
            if(!TextUtils.isEmpty(studentName) && !TextUtils.isEmpty(dobDate) && !TextUtils.isEmpty(contactNo)
                    && !TextUtils.isEmpty(email_id) && !TextUtils.isEmpty(qualificationDetails) && !TextUtils.isEmpty(courseFee)
                    && !TextUtils.isEmpty(courseDuration) && !TextUtils.isEmpty(dojDate) && !TextUtils.isEmpty(joiningFor)) {

            String[] course=joiningFor.split(",");

            Admission admission=new Admission();
            admission.setName(studentName);
            admission.setContactno(contactNo);
            admission.setDob(dobDate);
            admission.setDoj(dojDate);
            admission.setEmail(email_id);
            admission.setDuration(courseDuration);
            admission.setGender(genderValue);
            admission.setQualification(qualificationDetails);
            admission.setFee(courseFee);
            courseList=new ArrayList<>();

            for (String subject:course) {
                subject=subject.trim();
                courseList.add(subject);
            }

            int removeIndexValue=courseList.size()-1;
            courseList.remove(removeIndexValue);
                getFragmentManager().beginTransaction().replace(R.id.admissionLayout,fragment).commit();
                if(course.length>1) {
                    Double fee=Integer.parseInt(courseFee)/Double.valueOf(courseList.size());
                    Double roundFee=Math.round(fee*100.0)/100.0;
                    for(String subject:courseList) {
                        saveWRTSubject(subject,studentName,admission,String.valueOf(roundFee));
                    }
                } else {
                    saveWRTSubject(joiningFor,studentName,admission,courseFee);
                }
                Toast.makeText(getActivity().getApplicationContext(),"Record Saved...",Toast.LENGTH_LONG).show();
                getFragmentManager().beginTransaction().remove(fragment).commit();
            } else {
                Toast.makeText(getActivity().getApplicationContext(),"PLEASE FILL ALL DETAILS",Toast.LENGTH_LONG).show();
            }
    }

    private void saveWRTSubject(final String courseName,final String studentName, Admission admissions,final String courseFee) {
        admissions.setCourse(courseName);
        admissionReference.child(courseName).child(studentName).setValue(admissions).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    CourseFee courseFeeDetails=new CourseFee();
                    courseFeeDetails.setName(studentName);
                    courseFeeDetails.setTotalFee(courseFee);
                    courseFeeDetails.setFeePaid("0");
                    courseFeeDetails.setFeeRemaining(courseFee);
                    FirebaseDatabase.getInstance().getReference().child("fee").child(courseName).child(studentName).setValue(courseFeeDetails);
                    getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    setToInitial();

                } else {
                    Snackbar snackbar=Snackbar.make(getActivity().findViewById(android.R.id.content),
                            "SOMETHING WENT WRONG, RECORD NOT SAVED",
                            Snackbar.LENGTH_LONG);
                    snackbar.setActionTextColor(Color.RED);
                    snackbar.show();
                }
            }
        });
    }

    private void setToInitial() {
        name.setText("");
        dateofbirth.setText("");
        gender.clearCheck();
        contactno.setText("");
        email.setText("");
        qualification.setText("");
        coursefee.setText("");
        duration.setText("");
        joining.setText("");
        courseTextView.setText("");
    }
}