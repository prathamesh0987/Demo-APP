package com.svrinfotech;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class TotalAdmissions extends Fragment implements View.OnClickListener {

    private Button cad,catia,solidworks,creo,nx,ansys,cfd,hypermesh,feast,android,java,python;

    public TotalAdmissions() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_total_admissions, container, false);
        init(rootView);
        cad.setOnClickListener(this);
        catia.setOnClickListener(this);
        solidworks.setOnClickListener(this);
        creo.setOnClickListener(this);
        nx.setOnClickListener(this);
        ansys.setOnClickListener(this);
        cfd.setOnClickListener(this);
        hypermesh.setOnClickListener(this);
        feast.setOnClickListener(this);
        android.setOnClickListener(this);
        java.setOnClickListener(this);
        python.setOnClickListener(this);

        // Inflate the layout for this fragment
        return rootView;
    }


    private void init(View view) {
        cad=view.findViewById(R.id.cadAdmission);
        catia=view.findViewById(R.id.catiaAdmission);
        solidworks=view.findViewById(R.id.swAdmission);
        creo=view.findViewById(R.id.creoAdmission);
        nx=view.findViewById(R.id.nxAdmission);
        ansys=view.findViewById(R.id.ansysAdmission);
        cfd=view.findViewById(R.id.cfdAdmission);
        hypermesh=view.findViewById(R.id.hypermeshAdmission);
        feast=view.findViewById(R.id.feastAdmission);
        android=view.findViewById(R.id.androidAdmission);
        java=view.findViewById(R.id.javaAdmission);
        python=view.findViewById(R.id.pythonAdmission);
    }



    private void hideFronView() {
        cad.setVisibility(View.GONE);
        catia.setVisibility(View.GONE);
        solidworks.setVisibility(View.GONE);
        creo.setVisibility(View.GONE);
        nx.setVisibility(View.GONE);
        ansys.setVisibility(View.GONE);
        cfd.setVisibility(View.GONE);
        hypermesh.setVisibility(View.GONE);
        feast.setVisibility(View.GONE);
        android.setVisibility(View.GONE);
        java.setVisibility(View.GONE);
        python.setVisibility(View.GONE);
        getActivity().findViewById(R.id.admissions).setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        Fragment fragment=new ShowRecords();
        Bundle bundle=null;
        switch (v.getId()) {
            case R.id.cadAdmission:
                bundle=new Bundle();
                bundle.putString("subject","AUTOCAD");
                break;
            case R.id.swAdmission:
                bundle=new Bundle();
                bundle.putString("subject","SOLIDWORKS");
                break;
            case R.id.catiaAdmission:
                bundle=new Bundle();
                bundle.putString("subject","CATIA");
                break;
            case R.id.creoAdmission:
                bundle=new Bundle();
                bundle.putString("subject","CREO");
                break;
            case R.id.nxAdmission:
                bundle=new Bundle();
                bundle.putString("subject","N-X");
                break;
            case R.id.ansysAdmission:
                bundle=new Bundle();
                bundle.putString("subject","ANSYS");
                break;
            case R.id.cfdAdmission:
                bundle=new Bundle();
                bundle.putString("subject","CFD");
                break;
            case R.id.hypermeshAdmission:
                bundle=new Bundle();
                bundle.putString("subject","HYPERMESH");
                break;
            case R.id.feastAdmission:
                bundle=new Bundle();
                bundle.putString("subject","FEAST");
                break;
            case R.id.javaAdmission:
                bundle=new Bundle();
                bundle.putString("subject","JAVA");
                break;
            case R.id.androidAdmission:
                bundle=new Bundle();
                bundle.putString("subject","ANDROID");
                break;
            case R.id.pythonAdmission:
                bundle=new Bundle();
                bundle.putString("subject","PYTHON");
                break;
        }
        if(bundle!=null) {
            hideFronView();
            fragment.setArguments(bundle);
            getFragmentManager().beginTransaction().replace(R.id.totalAdmissionLayout,fragment).commit();
        }
    }
}