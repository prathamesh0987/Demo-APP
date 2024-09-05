package com.svrinfotech;


import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.svrinfotech.pojo.Admission;
import com.svrinfotech.pojo.CourseFee;


/**
 * A simple {@link Fragment} subclass.
 */
public class ManageFee extends Fragment {
    private TextView studName,totalFee,feePaid,feeRemaining;
    private EditText payingAmount;
    private Button update;
    private DatabaseReference studFeeDatabaseReference;
    Context context;
    public ManageFee() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_manage_fee, container, false);
        studName=rootView.findViewById(R.id.studName);
        totalFee=rootView.findViewById(R.id.totalFee);
        feePaid=rootView.findViewById(R.id.feePaid);
        feeRemaining=rootView.findViewById(R.id.feeRemaining);
        payingAmount=rootView.findViewById(R.id.payingAmount);
        update=rootView.findViewById(R.id.updateFeeDetails);
        context=getActivity().getBaseContext();
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        final Admission admission=(Admission) getArguments().getSerializable("admission");
        final String name=admission.getName();
        String course=admission.getCourse();
        studFeeDatabaseReference=FirebaseDatabase.getInstance().getReference().child("fee").child(course).child(name);
        studFeeDatabaseReference.keepSynced(true);
        studFeeDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final CourseFee courseFee=dataSnapshot.getValue(CourseFee.class);
                StringBuffer stringBuffer=new StringBuffer();
                stringBuffer=stringBuffer.append("Name : ").append(name);
                studName.setText(stringBuffer.toString());
                stringBuffer=new StringBuffer();
                stringBuffer=stringBuffer.append("Total Fee : ").append(admission.getFee());
                totalFee.setText(stringBuffer.toString());
                stringBuffer=new StringBuffer();
                stringBuffer=stringBuffer.append("Fee Paid : ").append(courseFee.getFeePaid());
                feePaid.setText(stringBuffer.toString());
                stringBuffer=new StringBuffer();
                stringBuffer=stringBuffer.append("Fee Remaining : ").append(courseFee.getFeeRemaining());
                feeRemaining.setText(stringBuffer.toString());

                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String amount=payingAmount.getText().toString().trim();
                        if(!TextUtils.isEmpty(amount)) {
                            Double feePaid=Double.parseDouble(courseFee.getFeePaid())+Double.parseDouble(amount);
                            Double feeRemaining=Double.parseDouble(courseFee.getTotalFee())-feePaid;
                            if(feeRemaining<0) {
                                Toast.makeText(context,
                                        "Please Enter Valid Amount",
                                        Toast.LENGTH_LONG).show();
                            } else  {
                                courseFee.setFeePaid(String.valueOf(feePaid));
                                courseFee.setFeeRemaining(String.valueOf(feeRemaining));
                                studFeeDatabaseReference.setValue(courseFee);
                                payingAmount.setText("");
                            }
                        } else {
                            payingAmount.setError("Please Enter Amount First");
                        }
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
