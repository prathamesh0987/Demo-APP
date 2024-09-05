package com.svrinfotech;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.jsibbold.zoomage.ZoomageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShowCourseContent extends Fragment {

    ZoomageView imageView;
    String subject;
    public ShowCourseContent() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_show_course_content, container, false);
        imageView=rootView.findViewById(R.id.courseContents);
        subject=getArguments().getString("course");
        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        switch (subject) {
            case "AUTOCAD":
                setContent("AUTOCAD COURSE CONTENT",R.drawable.cad_syllabus);
                break;
            case "SOLIDWORKS":
                setContent("SOLIDWORKS COURSE CONTENT",R.drawable.solidworks_syllabus);
                break;
            case "JAVA":
                setContent("JAVA COURSE CONTENT",R.drawable.java_syllabus);
                break;
            case "PYTHON":
                setContent("PYTHON COURSE CONTENT",R.drawable.python_syllabus);
                break;
            case "ANDROID":
                setContent("ANDROID COURSE CONTENT",R.drawable.android_syllabus);
                break;

        }
    }

    private void setContent(String title,int id) {
        getActivity().setTitle(title);
        imageView.setImageResource(id);
    }


}
