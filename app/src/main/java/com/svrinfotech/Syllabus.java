package com.svrinfotech;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Syllabus extends Fragment {

    private RecyclerView studentRecyclerView;
    private StudentAdapter studentAdapter;
    private ArrayList<Admission> admissionsArrayList;
    String status,name;
    FragmentManager fragmentManager;
    DatabaseReference fireDatabaseReference;
    ArrayList<String> courses;
    Fragment fragment;
    Context context;
    public Syllabus() {
        // Required empty public constructor
    }

    public ArrayList<Admission> getAdmissionsArrayList() {
        return admissionsArrayList;
    }

    public void setAdmissionsArrayList(ArrayList<Admission> admissionsArrayList) {
        this.admissionsArrayList = admissionsArrayList;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_syllabus, container, false);
        init(rootView);
        fireDatabaseReference=FirebaseDatabase.getInstance().getReference();
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getActivity().getApplicationContext());
        studentRecyclerView.setHasFixedSize(true);
        studentRecyclerView.setLayoutManager(layoutManager);
        studentRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        //String sharedPreferenceName="SHARED_PREFERENCE";
        //SharedPreferences sharedPreferences=getActivity().getApplicationContext().getSharedPreferences(sharedPreferenceName,MODE_PRIVATE);
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        String user=firebaseAuth.getCurrentUser().getEmail();

        List<EndUser> loggedUserList=EndUser.find(EndUser.class,"email=?",user);

        EndUser loggedUser=loggedUserList.get(0);
        status=loggedUser.getStatus();
        Log.e("Status ",status);
                //sharedPreferences.getString("status",null);
        name=loggedUser.getName();
        Log.e("name ",name);
                //sharedPreferences.getString("name",null);
        if(!status.equals("admin") && !status.equals("owner") ) {
            try {
                JSONObject jsonObject=new JSONObject(loggedUser.getHashSet());
                String json=jsonObject.getString("hashSet");
                JSONArray jsonArray=new JSONArray(json);
                for(int i=0;i<jsonArray.length();i++) {
                    courses.add(jsonArray.getString(i));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if(status!=null) {
            /*switch (status) {
                case "employee":
                    for(String subject:courses) {
                        DatabaseReference subjectReference= FirebaseDatabase.getInstance().getReference().child("admission").child(subject);
                        subjectReference.keepSynced(true);

                        subjectReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                Iterator<DataSnapshot> iterator=dataSnapshot.getChildren().iterator();
                                while (iterator.hasNext()) {
                                    DataSnapshot studentData=iterator.next();
                                    Admission admissions=studentData.getValue(Admission.class);
                                    admissionsArrayList.add(admissions);
                                }
                                setAdmissionsArrayList(admissionsArrayList);
                                studentAdapter.notifyDataSetChanged();

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                    studentRecyclerView.setAdapter(studentAdapter);
                    break;
                case "student":
                    Fragment fragment=new StudentSyllabus();
                    hideFrontView();
                    getFragmentManager().beginTransaction().replace(R.id.relativeFrame,fragment).commit();
                    break;
                case "owner":
                case "admin":
                    DatabaseReference userReference=FirebaseDatabase.getInstance().getReference().child("admission");
                    userReference.keepSynced(true);
                    userReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Iterator<DataSnapshot> iterator=dataSnapshot.getChildren().iterator();
                            while (iterator.hasNext()) {
                                DataSnapshot subjectData=iterator.next();
                                Iterator<DataSnapshot> admissionIterator=subjectData.getChildren().iterator();
                                while (admissionIterator.hasNext()) {
                                    DataSnapshot admissionData=admissionIterator.next();
                                    Admission admissions=admissionData.getValue(Admission.class);
                                    admissionsArrayList.add(admissions);
                                }
                            }
                            setAdmissionsArrayList(admissionsArrayList);
                            studentAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    studentRecyclerView.setAdapter(studentAdapter);
                    break;
            }
            studentRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity().getApplicationContext(),
                    studentRecyclerView,
                    new RecyclerTouchListener.ClickListener() {
                        @Override
                        public void onClick(View view, int position) {
                            Admission admission=getAdmissionsArrayList().get(position);
                            Bundle bundle=new Bundle();
                            Fragment fragment=null;
                            switch (status) {
                                case "employee":
                                    switch (admission.getCourse()) {
                                        case "AUTOCAD":fragment=new AutocadSyllabus();
                                            bundle.putString("name",name);
                                            bundle.putString("status",status);
                                            bundle.putString("studentName",admission.getName());
                                            fragment.setArguments(bundle);
                                            studentRecyclerView.setVisibility(View.GONE);
                                            fragmentManager.beginTransaction().replace(R.id.relativeFrame,fragment).commit();
                                            break;
                                        case "SOLIDWORKS":fragment=new SolidworksSyllabus();
                                            bundle.putString("name",name);
                                            bundle.putString("status",status);
                                            bundle.putString("studentName",admission.getName());
                                            fragment.setArguments(bundle);
                                            studentRecyclerView.setVisibility(View.GONE);
                                            fragmentManager.beginTransaction().replace(R.id.relativeFrame,fragment).commit();
                                            break;
                                        case "CATIA":
                                            fragment=new CatiaSyllabus();
                                            bundle=new Bundle();
                                            bundle.putString("name",name);
                                            bundle.putString("status",status);
                                            bundle.putString("studentName",admission.getName());
                                            fragment.setArguments(bundle);
                                            studentRecyclerView.setVisibility(View.GONE);
                                            fragmentManager.beginTransaction().replace(R.id.relativeFrame,fragment).commit();
                                            break;
                                        case "JAVA":
                                            fragment=new JavaSyllabus();
                                            bundle=new Bundle();
                                            bundle.putString("name",name);
                                            bundle.putString("status",status);
                                            bundle.putString("studentName",admission.getName());
                                            fragment.setArguments(bundle);
                                            studentRecyclerView.setVisibility(View.GONE);
                                            fragmentManager.beginTransaction().replace(R.id.relativeFrame,fragment).commit();
                                            break;
                                        case "ANDROID":
                                            fragment=new AndroidSyllabus();
                                            bundle=new Bundle();
                                            bundle.putString("name",name);
                                            bundle.putString("status",status);
                                            bundle.putString("studentName",admission.getName());
                                            fragment.setArguments(bundle);
                                            studentRecyclerView.setVisibility(View.GONE);
                                            fragmentManager.beginTransaction().replace(R.id.relativeFrame,fragment).commit();
                                            break;
                                        case "PYTHON":
                                            fragment=new PythonSyllabus();
                                            bundle=new Bundle();
                                            bundle.putString("name",name);
                                            bundle.putString("status",status);
                                            bundle.putString("studentName",admission.getName());
                                            fragment.setArguments(bundle);
                                            studentRecyclerView.setVisibility(View.GONE);
                                            fragmentManager.beginTransaction().replace(R.id.relativeFrame,fragment).commit();
                                            break;
                                    }
                                    break;
                                case "owner":
                                case "admin":
                                    fragment=new DisplaySyllabus();
                                    bundle.putString("studentName",admission.getName());
                                    bundle.putString("joinFor",admission.getCourse());
                                    fragment.setArguments(bundle);
                                    studentRecyclerView.setVisibility(View.GONE);
                                    fragmentManager.beginTransaction().replace(R.id.relativeFrame,fragment).commit();
                                    break;
                            }
                        }

                        @Override
                        public void onHold(View view, int position) {

                        }
                    }));*/

            switch (status) {
                case "employee":
                    for(String subject:courses) {
                        Log.e("Subject : ",subject);
                        DatabaseReference subjectReference= fireDatabaseReference.child("admission").child(subject);
                        subjectReference.keepSynced(true);

                        subjectReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                Iterator<DataSnapshot> iterator=dataSnapshot.getChildren().iterator();
                                while (iterator.hasNext()) {
                                    DataSnapshot studentData=iterator.next();
                                    Admission admission=studentData.getValue(Admission.class);
                                    admissionsArrayList.add(admission);
                                }
                                setAdmissionsArrayList(admissionsArrayList);
                                studentAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                    studentRecyclerView.setAdapter(studentAdapter);


                    studentRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity().getApplicationContext(),
                            studentRecyclerView,
                            new RecyclerTouchListener.ClickListener() {
                                @Override
                                public void onClick(View view, int position) {
                                    Admission admission=getAdmissionsArrayList().get(position);
                                    Bundle bundle=new Bundle();
                                    bundle.putSerializable("admission",admission);
                                    bundle.putString("status",status);
                                    switch (admission.getCourse()) {
                                        case "AUTOCAD":
                                            fragment=new AutocadSyllabus();
                                            break;
                                        case "CATIA":
                                            fragment=new CatiaSyllabus();
                                            break;
                                        case "SOLIDWORKS":
                                            fragment=new SolidworksSyllabus();
                                            break;
                                        case "JAVA":
                                            fragment=new JavaSyllabus();
                                            break;
                                        case "ANDROID":
                                            fragment=new AndroidSyllabus();
                                            break;
                                        case "PYTHON":
                                            fragment=new PythonSyllabus();
                                            break;
                                    }
                                    fragment.setArguments(bundle);
                                    hideFrontView();
                                    getFragmentManager().beginTransaction().replace(R.id.relativeFrame,fragment).commit();
                                }

                                @Override
                                public void onHold(View view, int position) {

                                }
                            }));

                    break;
                case "student":
                    Fragment fragment=new StudentSyllabus();
                    hideFrontView();
                    getFragmentManager().beginTransaction().replace(R.id.relativeFrame,fragment).commit();
                    break;
                case "owner":
                case "admin":
                    DatabaseReference userReference=fireDatabaseReference.child("admission");
                    userReference.keepSynced(true);
                    userReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Iterator<DataSnapshot> iterator=dataSnapshot.getChildren().iterator();
                            while (iterator.hasNext()) {
                                DataSnapshot subjectData=iterator.next();
                                Iterator<DataSnapshot> admissionIterator=subjectData.getChildren().iterator();
                                while (admissionIterator.hasNext()) {
                                    DataSnapshot admissionData=admissionIterator.next();
                                    Admission admission=admissionData.getValue(Admission.class);
                                    admissionsArrayList.add(admission);
                                }
                            }
                            setAdmissionsArrayList(admissionsArrayList);
                            studentAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    studentRecyclerView.setAdapter(studentAdapter);

                    studentRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity().getApplicationContext(),
                            studentRecyclerView,
                            new RecyclerTouchListener.ClickListener() {
                                @Override
                                public void onClick(View view, int position) {
                                    Admission admission=getAdmissionsArrayList().get(position);
                                    Bundle bundle=new Bundle();
                                    bundle.putSerializable("admission",admission);
                                    Fragment fragment=new DisplaySyllabus();
                                    fragment.setArguments(bundle);
                                    hideFrontView();
                                    getFragmentManager().beginTransaction().replace(R.id.relativeFrame,fragment).commit();
                                }

                                @Override
                                public void onHold(View view, int position) {

                                }
                            }));
                    break;
            }

        }
    }

    private void init(View rootView) {
        studentRecyclerView=rootView.findViewById(R.id.syllabusView);
        admissionsArrayList=new ArrayList<>();
        studentAdapter=new StudentAdapter(admissionsArrayList);
        fragmentManager=getFragmentManager();
        courses=new ArrayList<>();
        context=getActivity().getBaseContext();
    }

    private void hideFrontView() {
        studentRecyclerView.setVisibility(View.GONE);
    }
}
