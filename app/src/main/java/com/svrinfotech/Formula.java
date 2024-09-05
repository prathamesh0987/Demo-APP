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
public class Formula extends Fragment {

    ViewPager formulaPager;
    int[] formulae={
            R.drawable.formula01,
            R.drawable.formula02,
            R.drawable.formula03,
            R.drawable.formula04,
            R.drawable.formula05,
            R.drawable.formula06,
            R.drawable.formula07,
            R.drawable.formula08,
            R.drawable.formula09,
            R.drawable.formula10,
            R.drawable.formula11,
            R.drawable.formula12
    };
    public Formula() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_formula, container, false);
        formulaPager=view.findViewById(R.id.formulaPager);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ViewPagerAdapter viewPagerAdapter=new ViewPagerAdapter(getContext(),formulae);
        formulaPager.setAdapter(viewPagerAdapter);
    }
}
