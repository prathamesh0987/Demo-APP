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
public class AnsysCourse extends Fragment {

    private ViewPager ansysViewPager;
    int[] images={R.drawable.ansys_1,R.drawable.ansys_2,R.drawable.ansys_3,R.drawable.ansys_4};

    public AnsysCourse() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_ansys_course, container, false);
        ansysViewPager=rootView.findViewById(R.id.ansysViewPager);
        getActivity().setTitle("CATIA COURSE CONTENT");
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        ViewPagerAdapter viewPagerAdapter=new ViewPagerAdapter(getContext(),images);
        ansysViewPager.setAdapter(viewPagerAdapter);
    }
}
