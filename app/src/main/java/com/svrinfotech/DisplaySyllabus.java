package com.svrinfotech;

import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.svrinfotech.pojo.Admission;
import com.svrinfotech.pojo.RevisionPoints;
import com.svrinfotech.pojo.UserPojo;

import java.util.ArrayList;
import java.util.Iterator;

import io.netopen.hotbitmapgg.library.view.RingProgressBar;


/**
 * A simple {@link Fragment} subclass.
 */
public class DisplaySyllabus extends Fragment {

    private TextView syllabusCovered,name,revision;
    private StringBuffer checkedSyllabus,revisionPoints;
    private RadioButton employee,student;
    private RingProgressBar ringProgressBar;
    private DatabaseReference revisionReference,coveredReference;
    private String studentName,joinFor;
    private int percentage;
    Admission admission;
    Long total,count;
    Context context;

    public DisplaySyllabus() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_display_syllabus, container, false);
        admission=(Admission) getArguments().getSerializable("admission");
        studentName=admission.getName();
        joinFor=admission.getCourse();
        init(rootView);
        syllabusCovered.setMovementMethod(new ScrollingMovementMethod());
        revision.setMovementMethod(new ScrollingMovementMethod());
        revisionReference.keepSynced(true);
        coveredReference=FirebaseDatabase.getInstance().getReference().child("syllabus");
        coveredReference.keepSynced(true);

        context=getActivity().getApplicationContext();
        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        name.setText(studentName);
        name.setPaintFlags(name.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        student.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    checkedSyllabus = new StringBuffer("Syllabus Covered : ");
                    FirebaseDatabase.getInstance().getReference()
                            .child("syllabus")
                            .child(joinFor)
                            .child(studentName)
                            .addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    Iterator<DataSnapshot> iterator=dataSnapshot.getChildren().iterator();
                                    count=dataSnapshot.getChildrenCount();
                                    Log.e("Total : ",count.toString());
                                    while (iterator.hasNext()) {
                                        Log.e("Student Name : ","in While Loop");
                                        DataSnapshot student=iterator.next();
                                        checkedSyllabus=checkedSyllabus.append(student.getValue(String.class)).append(", ");
                                    }
                                    syllabusCovered.setText(checkedSyllabus.toString());
                                    setPercentage(count);
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    Log.e("Database Error : ",databaseError.getMessage());
                                }
                            });
                    getRevisionPoints();
                }
            }
        });

        employee.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                revision.setVisibility(View.GONE);
                if(isChecked) {
                    checkedSyllabus = new StringBuffer("Syllabus Covered : ");

                    final DatabaseReference userReference=FirebaseDatabase.getInstance().getReference()
                            .child("users");
                    userReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Iterator<DataSnapshot> iterator=dataSnapshot.getChildren().iterator();
                            while (iterator.hasNext()) {
                                DataSnapshot userData=iterator.next();
                                UserPojo user=userData.getValue(UserPojo.class);
                                if(user.getStatus().equals("employee")) {
                                    ArrayList<String> courseList=user.getCourse();
                                    for(String course:courseList) {
                                        if(course.equals(joinFor)) {
                                            DatabaseReference coveredSyllabusReference=
                                                    coveredReference
                                                    .child(joinFor)
                                                    .child(user.getName())
                                                    .child(studentName);

                                            coveredSyllabusReference.keepSynced(true);

                                            coveredSyllabusReference.addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                    Iterator<DataSnapshot> userIterator=dataSnapshot.getChildren().iterator();
                                                    count=dataSnapshot.getChildrenCount();
                                                    while (userIterator.hasNext()) {
                                                        DataSnapshot student=userIterator.next();
                                                        checkedSyllabus=checkedSyllabus.append(student.getValue(String.class)).append(", ");
                                                    }
                                                    syllabusCovered.setText(checkedSyllabus.toString());
                                                    setPercentage(count);
                                                }

                                                @Override
                                                public void onCancelled(DatabaseError databaseError) {

                                                }
                                            });
                                            break;
                                        }
                                    }
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }
        });
    }

    private Float getPercentage(Float count, Integer total) {
        return (count/total)*100;
    }

    private void setPercentage(Long count) {
        switch (joinFor) {
            case "ANDROID":
                percentage=getPercentage(count.floatValue(),23).intValue();
                break;
            case "JAVA":
                percentage=getPercentage(count.floatValue(),34).intValue();
                break;
            case "PYTHON":
                percentage=getPercentage(count.floatValue(),24).intValue();
                break;
            case "AUTOCAD":
                percentage=getPercentage(count.floatValue(),37).intValue();
                break;
            case "SOLIDWORKS":
                percentage=getPercentage(count.floatValue(),32).intValue();
                break;
            case "CATIA":
                percentage=getPercentage(count.floatValue(),45).intValue();
                break;
        }
        ringProgressBar.setProgress(percentage);
    }

    private void getRevisionPoints() {
        revisionPoints=new StringBuffer();
        revisionPoints.append("Points To Be Revised :\n");
        revisionReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> iterator=dataSnapshot.getChildren().iterator();
                while (iterator.hasNext()) {
                    DataSnapshot revisionPoint=iterator.next();
                    RevisionPoints points=revisionPoint.getValue(RevisionPoints.class);
                    revisionPoints.append("Point : ").append(points.getPoint()).append("\nReason : ").append(points.getReason()).append("\n");
                }
                revision.setText(revisionPoints.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void init(View rootView) {
        syllabusCovered = rootView.findViewById(R.id.syllabusCovered);
        name=rootView.findViewById(R.id.name);
        employee=rootView.findViewById(R.id.employee);
        student=rootView.findViewById(R.id.student);
        ringProgressBar=rootView.findViewById(R.id.percentCovered);
        revision=rootView.findViewById(R.id.revision);
        revisionReference=FirebaseDatabase.getInstance().getReference().child("revision").child(joinFor).child(studentName);
    }
}