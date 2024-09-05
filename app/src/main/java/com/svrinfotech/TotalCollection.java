package com.svrinfotech;


import android.content.pm.ActivityInfo;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.svrinfotech.pojo.Cell;
import com.svrinfotech.pojo.ColumnHeader;
import com.svrinfotech.pojo.CourseFee;
import com.svrinfotech.pojo.RowHeader;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class TotalCollection extends Fragment {
    private TextView autoCADTotalFee,autoCADCollectedFee,autoCADDueFee,
            swTotalFee,swCollectedFee,swDueFee,
            catiaTotalFee,catiaCollectedFee,catiaDueFee,
            javaTotalFee,javaCollectedFee,javaDueFee,
            androidTotalFee,androidCollectedFee,androidDueFee,
            pythonTotalFee,pythonCollectedFee,pythonDueFee;
    private ArrayList<ColumnHeader> columnHeaderArrayList;
    private ArrayList<RowHeader> rowHeaderArrayList;
    private List<Cell> cellArrayList;
    private List<List<Cell>> finalCellList;
    //TableView tableView;

    private int totalFee,feeCollected,totalDue;
    private DatabaseReference autoCADFeeDatabaseReference,solidworksFeeDatabaseReference,catiaDataFeebaseReference,
            javaFeeDatabaseReference,androidFeeDatabaseReference,pythonFeeDatabaseReference;
    public TotalCollection() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        View rootView=inflater.inflate(R.layout.fragment_total_collection, container, false);

        /*rowHeaderArrayList=new ArrayList<>();
        columnHeaderArrayList=new ArrayList<>();
        finalCellList=new ArrayList<>();

        RowHeader rowHeader=new RowHeader("aCad","A-CAD");
        rowHeaderArrayList.add(rowHeader);
        rowHeader=new RowHeader("sw","SW");
        rowHeaderArrayList.add(rowHeader);
        rowHeader=new RowHeader("cat","CAT");
        rowHeaderArrayList.add(rowHeader);
        rowHeader=new RowHeader("java","JAVA");
        rowHeaderArrayList.add(rowHeader);
        rowHeader=new RowHeader("android","ANDROID");
        rowHeaderArrayList.add(rowHeader);
        rowHeader=new RowHeader("python","PYTHON");
        rowHeaderArrayList.add(rowHeader);

        ColumnHeader columnHeader=new ColumnHeader("totalFee","Total Fee");
        columnHeaderArrayList.add(columnHeader);
        columnHeader=new ColumnHeader("feeCollected","Fee Collected");
        columnHeaderArrayList.add(columnHeader);
        columnHeader=new ColumnHeader("feeDue","Fee Due");
        columnHeaderArrayList.add(columnHeader);*/
        //tableView=rootView.findViewById(R.id.collection_table);


        autoCADTotalFee=rootView.findViewById(R.id.autoCADTotalFee);
        autoCADCollectedFee=rootView.findViewById(R.id.autoCADCollectedFee);
        autoCADDueFee=rootView.findViewById(R.id.autoCADDueFee);

        swTotalFee=rootView.findViewById(R.id.swTotalFee);
        swCollectedFee=rootView.findViewById(R.id.swCollectedFee);
        swDueFee=rootView.findViewById(R.id.swDueFee);

        catiaTotalFee=rootView.findViewById(R.id.catiaTotalFee);
        catiaCollectedFee=rootView.findViewById(R.id.catiaCollectedFee);
        catiaDueFee=rootView.findViewById(R.id.catiaDueFee);

        javaTotalFee=rootView.findViewById(R.id.javaTotalFee);
        javaCollectedFee=rootView.findViewById(R.id.javaCollectedFee);
        javaDueFee=rootView.findViewById(R.id.javaDueFee);

        androidTotalFee=rootView.findViewById(R.id.androidTotalFee);
        androidCollectedFee=rootView.findViewById(R.id.androidCollectedFee);
        androidDueFee=rootView.findViewById(R.id.androidDueFee);

        pythonTotalFee=rootView.findViewById(R.id.pythonTotalFee);
        pythonCollectedFee=rootView.findViewById(R.id.pythonCollectedFee);
        pythonDueFee=rootView.findViewById(R.id.pythonDueFee);

        autoCADFeeDatabaseReference=FirebaseDatabase.getInstance().getReference()
                .child("fee").child("AUTOCAD");

        solidworksFeeDatabaseReference=FirebaseDatabase.getInstance().getReference()
                .child("fee").child("SOLIDWORKS");

        catiaDataFeebaseReference=FirebaseDatabase.getInstance().getReference()
                .child("fee").child("CATIA");

        javaFeeDatabaseReference= FirebaseDatabase.getInstance().getReference()
                .child("fee").child("JAVA");

        androidFeeDatabaseReference=FirebaseDatabase.getInstance().getReference()
                .child("fee").child("ANDROID");

        pythonFeeDatabaseReference=FirebaseDatabase.getInstance().getReference()
                .child("fee").child("PYTHON");

        autoCADFeeDatabaseReference.keepSynced(true);
        solidworksFeeDatabaseReference.keepSynced(true);
        catiaDataFeebaseReference.keepSynced(true);
        javaFeeDatabaseReference.keepSynced(true);
        androidFeeDatabaseReference.keepSynced(true);
        pythonFeeDatabaseReference.keepSynced(true);
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

        //CollectionTabelAdapter collectionTabelAdapter=new CollectionTabelAdapter(getContext());

        //tableView.setAdapter(collectionTabelAdapter);

        //collectionTabelAdapter.setAllItems(columnHeaderArrayList,rowHeaderArrayList,finalCellList);

        autoCADFeeDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> iterator=dataSnapshot.getChildren().iterator();
                while (iterator.hasNext()) {
                    DataSnapshot student=iterator.next();
                    CourseFee courseFee=student.getValue(CourseFee.class);
                    getCalculations(courseFee);
                }
                setValues(autoCADTotalFee,autoCADCollectedFee,autoCADDueFee);
                initializeCollection();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        solidworksFeeDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> iterator=dataSnapshot.getChildren().iterator();
                while (iterator.hasNext()) {
                    DataSnapshot student=iterator.next();
                    CourseFee courseFee=student.getValue(CourseFee.class);
                    getCalculations(courseFee);
                }
                setValues(swTotalFee,swCollectedFee,swDueFee);
                initializeCollection();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        catiaDataFeebaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> iterator=dataSnapshot.getChildren().iterator();

                while (iterator.hasNext()) {
                    DataSnapshot student=iterator.next();
                    CourseFee courseFee=student.getValue(CourseFee.class);
                    getCalculations(courseFee);
                }
                setValues(catiaTotalFee,catiaCollectedFee,catiaDueFee);
                initializeCollection();
             }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        javaFeeDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> iterator=dataSnapshot.getChildren().iterator();
                while (iterator.hasNext()) {
                    DataSnapshot student=iterator.next();
                    CourseFee courseFee=student.getValue(CourseFee.class);
                    getCalculations(courseFee);
                }
                setValues(javaTotalFee,javaCollectedFee,javaDueFee);
                initializeCollection();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        androidFeeDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> iterator=dataSnapshot.getChildren().iterator();
                while (iterator.hasNext()) {
                    DataSnapshot student=iterator.next();
                    CourseFee courseFee=student.getValue(CourseFee.class);
                    getCalculations(courseFee);
                }

                setValues(androidTotalFee,androidCollectedFee,androidDueFee);
                initializeCollection();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        pythonFeeDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> iterator=dataSnapshot.getChildren().iterator();
                while (iterator.hasNext()) {
                    DataSnapshot student=iterator.next();
                    CourseFee courseFee=student.getValue(CourseFee.class);
                    getCalculations(courseFee);
                }
                setValues(pythonTotalFee,pythonCollectedFee,pythonDueFee);
                initializeCollection();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getCalculations(CourseFee courseFee) {
        totalFee+=Double.parseDouble(courseFee.getTotalFee());
        feeCollected+=Double.parseDouble(courseFee.getFeePaid());
        totalDue+=Double.parseDouble(courseFee.getFeeRemaining());
    }

    private void setValues(TextView total,TextView collected, TextView due) {
        /*cellArrayList=new ArrayList<>();
        Log.e("Total Fee : ",String.valueOf(totalFee));*/
        //Cell cell=new Cell("totalFee",String.valueOf(totalFee));
        //cellArrayList.add(cell);
        //Log.e("Collected Fee : ",String.valueOf(feeCollected));
        //cell=new Cell("feeCollected",String.valueOf(feeCollected));
        //cellArrayList.add(cell);
        //Log.e("Collected Fee : ",String.valueOf(totalDue));
        //cell=new Cell("totalDue",String.valueOf(totalDue));
        //cellArrayList.add(cell);
        total.setText(String.valueOf(totalFee));
        collected.setText(String.valueOf(feeCollected));
        due.setText(String.valueOf(totalDue));
        //finalCellList.add(cellArrayList);
    }

    private void initializeCollection() {
        totalFee=0;
        feeCollected=0;
        totalDue=0;
    }
}
