package com.svrinfotech;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class Progressbar extends Fragment {
    private ProgressBar progressBar;

    public Progressbar() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootview=inflater.inflate(R.layout.fragment_progressbar, container, false);
        progressBar=rootview.findViewById(R.id.progressBar);
        progressBar.setIndeterminate(false);
        // Inflate the layout for this fragment
        return rootview;
    }

    @Override
    public void onResume() {
        super.onResume();

    }
}
