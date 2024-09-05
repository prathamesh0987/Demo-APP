package com.svrinfotech;

import android.content.DialogInterface;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;

import com.google.firebase.database.DatabaseReference;
import com.svrinfotech.pojo.RevisionPoints;

public class ProvideAlertDialogInterface {

    public void setReason(final String point, final DatabaseReference revisionReference, final FragmentActivity fragmentActivity) {
        final AlertDialog.Builder alertDialogBuilder=new AlertDialog.Builder(fragmentActivity);
        final AlertDialog.Builder reasonDialog=new AlertDialog.Builder(fragmentActivity);
        final EditText giveReason=new EditText(fragmentActivity);
        reasonDialog.setView(giveReason);
        reasonDialog.setMessage("Please provide a valid reason");
        reasonDialog.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String reason=giveReason.getText().toString();
                RevisionPoints revisionPoints=new RevisionPoints(point,reason);
                revisionReference.child(point).setValue(revisionPoints);
            }
        });


        alertDialogBuilder.setMessage("Do you want to revise this topic");
        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                reasonDialog.show();
            }
        });

        AlertDialog alertDialog=alertDialogBuilder.create();
        alertDialog.show();
    }

    public void removeReason(String point, DatabaseReference revisionReference) {
        revisionReference.child(point).removeValue();
    }
}