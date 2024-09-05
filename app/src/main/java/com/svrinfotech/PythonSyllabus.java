package com.svrinfotech;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.svrinfotech.pojo.Admission;
import com.svrinfotech.pojo.EndUser;

import java.util.Iterator;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class PythonSyllabus extends Fragment {
    private CheckBox pythonIntro,basicSyntax,pythonFeatures,
            identifiers,quotation,indentation,variableTypes,operators,comments,multilineStatement,pyControlStatement,
            sequence,pyString,tuples,list,dictionary,set,pyExceptionHandling,pyFileHandling,
            pyClassesObjects,pyMultiThreading,pyCGI,pyDatabase,pyGUI;

    private DatabaseReference syllabusReference,revisionReference;
    private String studentName;
    private ProvideAlertDialogInterface provideAlertDialogInterface;
    public PythonSyllabus() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView=inflater.inflate(R.layout.fragment_python_syllabus, container, false);
        init(rootView);
//        String sharedPreferenceName="SHARED_PREFERENCE";
//        SharedPreferences sharedPreferences=getActivity().getApplicationContext().getSharedPreferences(sharedPreferenceName,MODE_PRIVATE);
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        String user=firebaseAuth.getCurrentUser().getEmail();

        List<EndUser> loggedUserList=EndUser.find(EndUser.class,"email=?",user);

        EndUser loggedUser=loggedUserList.get(0);
        String status=loggedUser.getStatus();
                //getArguments().getString("status");
        String name=loggedUser.getName();
                //sharedPreferences.getString("employeeName",null);
        switch (status) {
            case "student":
                syllabusReference= FirebaseDatabase.getInstance()
                    .getReference()
                    .child("syllabus")
                    .child("PYTHON")
                    .child(name);
                break;
            case "employee":
                if(!name.equals(null)) {
                Admission admission=(Admission) getArguments().getSerializable("admission");
                studentName=admission.getName();
                syllabusReference= FirebaseDatabase.getInstance()
                        .getReference()
                        .child("syllabus")
                        .child("PYTHON")
                        .child(name)
                        .child(studentName);
            }
                break;
        }
        syllabusReference.keepSynced(true);
        revisionReference=FirebaseDatabase.getInstance().getReference()
                .child("revision")
                .child("PYTHON")
                .child(name);
        revisionReference.keepSynced(true);

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

        syllabusReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterator iterator=dataSnapshot.getChildren().iterator();
                while (iterator.hasNext()) {
                    DataSnapshot keyValue=(DataSnapshot) iterator.next();
                    String name=keyValue.getKey();
                    switch (name) {
                        case "pythonIntro":pythonIntro.setChecked(true);
                            break;
                        case "basicSyntax":basicSyntax.setChecked(true);
                            break;
                        case "pythonFeatures":pythonFeatures.setChecked(true);
                            break;
                        case "identifiers":identifiers.setChecked(true);
                            break;
                        case "quotation":quotation.setChecked(true);
                            break;
                        case "indentation":indentation.setChecked(true);
                            break;
                        case "variableTypes":variableTypes.setChecked(true);
                            break;
                        case "operators":operators.setChecked(true);
                            break;
                        case "comments":comments.setChecked(true);
                            break;
                        case "multilineStatement":multilineStatement.setChecked(true);
                            break;
                        case "pyControlStatement":pyControlStatement.setChecked(true);
                            break;
                        case "sequence":sequence.setChecked(true);
                            break;
                        case "pyString":pyString.setChecked(true);
                            break;
                        case "tuples":tuples.setChecked(true);
                            break;
                        case "list":list.setChecked(true);
                            break;
                        case "dictionary":dictionary.setChecked(true);
                            break;
                        case "set":set.setChecked(true);
                            break;
                        case "pyExceptionHandling":pyExceptionHandling.setChecked(true);
                            break;
                        case "pyFileHandling":pyFileHandling.setChecked(true);
                            break;
                        case "pyClassesObjects":pyClassesObjects.setChecked(true);
                            break;
                        case "pyMultiThreading":pyMultiThreading.setChecked(true);
                            break;
                        case "pyCGI":pyCGI.setChecked(true);
                            break;
                        case "pyDatabase":pyDatabase.setChecked(true);
                            break;
                        case "pyGUI":pyGUI.setChecked(true);
                            break;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        pythonIntro.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("pythonIntro").setValue("Introduction To Python");
                    provideAlertDialogInterface.removeReason("Introduction To Python",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Introduction To Python",revisionReference,getActivity());
                    syllabusReference.child("pythonIntro").removeValue();
                }
            }
        });

        basicSyntax.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("basicSyntax").setValue("Basic Syntax");
                    provideAlertDialogInterface.removeReason("Basic Syntax",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Basic Syntax",revisionReference,getActivity());
                    syllabusReference.child("basicSyntax").removeValue();
                }
            }
        });

        pythonFeatures.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("pythonFeatures").setValue("Python Features");
                    provideAlertDialogInterface.removeReason("Python Features",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Python Features",revisionReference,getActivity());
                    syllabusReference.child("pythonFeatures").removeValue();
                }
            }
        });

        identifiers.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("identifiers").setValue("Identifiers");
                    provideAlertDialogInterface.removeReason("Identifiers",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Identifiers",revisionReference,getActivity());
                    syllabusReference.child("identifiers").removeValue();
                }
            }
        });

        quotation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("quotation").setValue("Quotation");
                    provideAlertDialogInterface.removeReason("Quotation",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Quotation",revisionReference,getActivity());
                    syllabusReference.child("quotation").removeValue();
                }
            }
        });

        indentation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("indentation").setValue("Indentation");
                    provideAlertDialogInterface.removeReason("Indentation",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Indentation",revisionReference,getActivity());
                    syllabusReference.child("indentation").removeValue();
                }
            }
        });

        variableTypes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("variableTypes").setValue("Variable Types");
                    provideAlertDialogInterface.removeReason("Variable Types",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Variable Types",revisionReference,getActivity());
                    syllabusReference.child("variableTypes").removeValue();
                }
            }
        });

        operators.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("operators").setValue("Operators");
                    provideAlertDialogInterface.removeReason("Operators",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Operators",revisionReference,getActivity());
                    syllabusReference.child("operators").removeValue();
                }
            }
        });

        comments.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("comments").setValue("Comments");
                    provideAlertDialogInterface.removeReason("Comments",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Comments",revisionReference,getActivity());
                    syllabusReference.child("comments").removeValue();
                }
            }
        });

        multilineStatement.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("multilineStatement").setValue("Multi-Line Statement");
                    provideAlertDialogInterface.removeReason("Multi-Line Statement",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Multi-Line Statement",revisionReference,getActivity());
                    syllabusReference.child("multilineStatement").removeValue();
                }
            }
        });

        pyControlStatement.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("pyControlStatement").setValue("Control Statement");
                    provideAlertDialogInterface.removeReason("Control Statement",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Control Statement",revisionReference,getActivity());
                    syllabusReference.child("pyControlStatement").removeValue();
                }
            }
        });

        sequence.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("sequence").setValue("Sequence");
                    provideAlertDialogInterface.removeReason("Sequence",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Sequence",revisionReference,getActivity());
                    syllabusReference.child("sequence").removeValue();
                }
            }
        });

        pyString.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("pyString").setValue("String");
                    provideAlertDialogInterface.removeReason("String",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("String",revisionReference,getActivity());
                    syllabusReference.child("pyString").removeValue();
                }
            }
        });

        tuples.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("tuples").setValue("Tuples");
                    provideAlertDialogInterface.removeReason("Tuples",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Tuples",revisionReference,getActivity());
                    syllabusReference.child("tuples").removeValue();
                }
            }
        });

        list.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("list").setValue("List");
                    provideAlertDialogInterface.removeReason("List",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("List",revisionReference,getActivity());
                    syllabusReference.child("list").removeValue();
                }
            }
        });

        dictionary.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("dictionary").setValue("Dictionary");
                    provideAlertDialogInterface.removeReason("Dictionary",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Dictionary",revisionReference,getActivity());
                    syllabusReference.child("dictionary").removeValue();
                }
            }
        });

        set.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("set").setValue("Set");
                    provideAlertDialogInterface.removeReason("Set",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Set",revisionReference,getActivity());
                    syllabusReference.child("set").removeValue();
                }
            }
        });

        pyExceptionHandling.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("pyExceptionHandling").setValue("Exception Handling");
                    provideAlertDialogInterface.removeReason("Exception Handling",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Exception Handling",revisionReference,getActivity());
                    syllabusReference.child("pyExceptionHandling").removeValue();
                }
            }
        });

        pyFileHandling.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("pyFileHandling").setValue("File Handling");
                    provideAlertDialogInterface.removeReason("File Handling",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("File Handling",revisionReference,getActivity());
                    syllabusReference.child("pyFileHandling").removeValue();
                }
            }
        });


        pyClassesObjects.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("pyClassesObjects").setValue("Classes & Object");
                    provideAlertDialogInterface.removeReason("Classes & Object",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Classes & Object",revisionReference,getActivity());
                    syllabusReference.child("pyClassesObjects").removeValue();
                }
            }
        });

        pyMultiThreading.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("pyMultiThreading").setValue("Multi-Threading");
                    provideAlertDialogInterface.removeReason("Multi-Threading",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Multi-Threading",revisionReference,getActivity());
                    syllabusReference.child("pyMultiThreading").removeValue();
                }
            }
        });

        pyCGI.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("pyCGI").setValue("Common Gateway Interface");
                    provideAlertDialogInterface.removeReason("Common Gateway Interface",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Common Gateway Interface",revisionReference,getActivity());
                    syllabusReference.child("pyCGI").removeValue();
                }
            }
        });

        pyDatabase.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("pyDatabase").setValue("Database");
                    provideAlertDialogInterface.removeReason("Database",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Database",revisionReference,getActivity());
                    syllabusReference.child("pyDatabase").removeValue();
                }
            }
        });

        pyGUI.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("pyGUI").setValue("Graphical User Interface");
                    provideAlertDialogInterface.removeReason("Graphical User Interface",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Graphical User Interface",revisionReference,getActivity());
                    syllabusReference.child("pyGUI").removeValue();
                }
            }
        });
    }

    private void init(View rootView) {
        pythonIntro=rootView.findViewById(R.id.pythonIntro);
        basicSyntax=rootView.findViewById(R.id.basicSyntax);
        pythonFeatures=rootView.findViewById(R.id.pythonFeatures);
        identifiers=rootView.findViewById(R.id.identifiers);
        quotation=rootView.findViewById(R.id.quotation);
        indentation=rootView.findViewById(R.id.indentation);
        variableTypes=rootView.findViewById(R.id.variableTypes);
        operators=rootView.findViewById(R.id.operators);
        comments=rootView.findViewById(R.id.comments);
        multilineStatement=rootView.findViewById(R.id.multilineStatement);
        pyControlStatement=rootView.findViewById(R.id.pyControlStatement);
        sequence=rootView.findViewById(R.id.sequence);
        pyString=rootView.findViewById(R.id.pyString);
        tuples=rootView.findViewById(R.id.tuples);
        list=rootView.findViewById(R.id.list);
        dictionary=rootView.findViewById(R.id.dictionary);
        set=rootView.findViewById(R.id.set);
        pyExceptionHandling=rootView.findViewById(R.id.pyExceptionHandling);
        pyFileHandling=rootView.findViewById(R.id.pyFileHandling);
        pyClassesObjects=rootView.findViewById(R.id.pyClassesObjects);
        pyMultiThreading=rootView.findViewById(R.id.pyMultiThreading);
        pyCGI=rootView.findViewById(R.id.pyCGI);
        pyDatabase=rootView.findViewById(R.id.pyDatabase);
        pyGUI=rootView.findViewById(R.id.pyGUI);
        provideAlertDialogInterface=new ProvideAlertDialogInterface();
    }
}
