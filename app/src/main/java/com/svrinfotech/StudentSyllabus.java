package com.svrinfotech;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.svrinfotech.pojo.EndUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class StudentSyllabus extends Fragment {

    private String name,status;
    Fragment courseFragment;
    private Spinner spinner;
    private ArrayList<String> courseList;
    public StudentSyllabus() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_student_syllabus, container, false);
//        final String sharedPreferenceName="SHARED_PREFERENCE";
//        SharedPreferences sharedPreferences=getActivity().getApplicationContext().getSharedPreferences(sharedPreferenceName, Context.MODE_PRIVATE);
        courseList=new ArrayList<>();
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        String user=firebaseAuth.getCurrentUser().getEmail();

        List<EndUser> loggedUserList=EndUser.find(EndUser.class,"email=?",user);
        Log.e("Size in SS : ",loggedUserList.size()+"");
        EndUser loggedUser=loggedUserList.get(0);
        name=loggedUser.getName();
                //sharedPreferences.getString("name",null);
      //  HashSet hashSet=loggedUser.getHashSet();
        //Log.e("HashSet Size : ",hashSet.size()+"");


        try {
            JSONObject jsonObject=new JSONObject(loggedUser.getHashSet());
            //Log.e("jsonObject",);
            String json=jsonObject.getString("hashSet");
            Log.e("json",json);
            JSONArray jsonArray=new JSONArray(json);
            // Log.e("jsonArray Size",jsonArray.length()+"");
            for(int i=0;i<jsonArray.length();i++) {
                courseList.add(jsonArray.getString(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        spinner=rootView.findViewById(R.id.courseSpinner);
        if(courseList!=null) {
            ArrayAdapter<String> courseAdapter=new ArrayAdapter<>(
                    getActivity().getBaseContext(),android.R.layout.simple_list_item_1,courseList);
            spinner.setAdapter(courseAdapter);
        }
        return  rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String coursename=courseList.get(position);
                switch (coursename) {
                    case "AUTOCAD":
                        courseFragment=new AutocadSyllabus();
                        openFragment();
                        break;
                    case "CATIA":
                        courseFragment=new CatiaSyllabus();
                        openFragment();
                        break;
                    case "SOLIDWORKS":
                        courseFragment=new SolidworksSyllabus();
                        openFragment();
                        break;
                    case "JAVA":
                        courseFragment=new JavaSyllabus();
                        openFragment();
                        break;
                    case "ANDROID":
                        courseFragment=new AndroidSyllabus();
                        openFragment();
                        break;
                    case "PYTHON":
                        courseFragment=new PythonSyllabus();
                        openFragment();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private void openFragment() {
        if(courseFragment!=null) {
            Bundle bundle=new Bundle();
            bundle.putString("name",name);
            bundle.putString("status","student");
            courseFragment.setArguments(bundle);
            getFragmentManager().beginTransaction().replace(R.id.placeSyllabus, courseFragment).commit();
        }
    }

}