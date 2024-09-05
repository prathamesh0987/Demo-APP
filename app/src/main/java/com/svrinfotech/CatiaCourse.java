package com.svrinfotech;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.svrinfotech.adapter.ViewPagerAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class CatiaCourse extends Fragment {

    ViewPager catiaViewPager;
    private int[] images={R.drawable.catia_syllabus1,R.drawable.catia_syllabus2};
    public CatiaCourse() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_catia_course, container, false);
        catiaViewPager=rootView.findViewById(R.id.catiaViewPager);
        getActivity().setTitle("CATIA COURSE CONTENT");
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        ViewPagerAdapter viewPagerAdapter=new ViewPagerAdapter(getContext(),images);
        catiaViewPager.setAdapter(viewPagerAdapter);
    }
}
