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
public class JavaSyllabus extends Fragment {
    private CheckBox javaIntro,features,differenceJavaC,javaEnvironment,
            namingConvention,classesObjects,operators,controlStatement,loops,stringBuffer,stringBuilder,string,keywords,packages,array,
            inheritance,polymorphism,abstraction,encapsulation,
            threads,multiThreads,deadLock,synchronization,exceptionHandling,fileHandling,checkedException,uncheckedException,ioOperation,
            collection,wrapperClasses,database,awtSwing,servlet,jsp;

    private DatabaseReference syllabusReference,revisionReference;
    private ProvideAlertDialogInterface provideAlertDialogInterface;
    String studentName;
    public JavaSyllabus() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_java_syllabus, container, false);
        init(rootView);
        //String sharedPreferenceName="SHARED_PREFERENCE";
        //SharedPreferences sharedPreferences=getActivity().getApplicationContext().getSharedPreferences(sharedPreferenceName,MODE_PRIVATE);
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
                    .child("JAVA")
                    .child(name);
                break;
            case "employee":
                if(!name.equals(null)) {
                Admission admission=(Admission) getArguments().getSerializable("admission");
                studentName=admission.getName();
                syllabusReference= FirebaseDatabase.getInstance()
                        .getReference()
                        .child("syllabus")
                        .child("JAVA")
                        .child(name)
                        .child(studentName);
            }
                break;
        }
        syllabusReference.keepSynced(true);

        revisionReference=FirebaseDatabase.getInstance().getReference()
                .child("revision")
                .child("JAVA")
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
                    DataSnapshot keyValue=(DataSnapshot)iterator.next();
                    String name=keyValue.getKey();
                    switch (name) {
                        case "javaIntro":javaIntro.setChecked(true);
                            break;
                        case "features":features.setChecked(true);
                            break;
                        case "differenceJavaC":differenceJavaC.setChecked(true);
                            break;
                        case "javaEnvironment":javaEnvironment.setChecked(true);
                            break;
                        case "namingConvention":namingConvention.setChecked(true);
                            break;
                        case "classesObjects":classesObjects.setChecked(true);
                            break;
                        case "operators":operators.setChecked(true);
                            break;
                        case "controlStatement":controlStatement.setChecked(true);
                            break;
                        case "loops":loops.setChecked(true);
                            break;
                        case "stringBuffer":stringBuffer.setChecked(true);
                            break;
                        case "stringBuilder":stringBuilder.setChecked(true);
                            break;
                        case "string":string.setChecked(true);
                            break;
                        case "keywords":keywords.setChecked(true);
                            break;
                        case "packages":packages.setChecked(true);
                            break;
                        case "array":array.setChecked(true);
                            break;
                        case "inheritance":inheritance.setChecked(true);
                            break;
                        case "polymorphism":polymorphism.setChecked(true);
                            break;
                        case "abstraction":abstraction.setChecked(true);
                            break;
                        case "encapsulation":encapsulation.setChecked(true);
                            break;
                        case "threads":threads.setChecked(true);
                            break;
                        case "multiThreads":multiThreads.setChecked(true);
                            break;
                        case "deadLock":deadLock.setChecked(true);
                            break;
                        case "synchronization":synchronization.setChecked(true);
                            break;
                        case "exceptionHandling":exceptionHandling.setChecked(true);
                            break;
                        case "fileHandling":fileHandling.setChecked(true);
                            break;
                        case "checkedException":checkedException.setChecked(true);
                            break;
                        case "uncheckedException":uncheckedException.setChecked(true);
                            break;
                        case "ioOperation":ioOperation.setChecked(true);
                            break;
                        case "collection":collection.setChecked(true);
                            break;
                        case "wrapperClasses":wrapperClasses.setChecked(true);
                            break;
                        case "database":database.setChecked(true);
                            break;
                        case "awtSwing":awtSwing.setChecked(true);
                            break;
                        case "servlet":servlet.setChecked(true);
                            break;
                        case "jsp":jsp.setChecked(true);
                            break;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        javaIntro.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("javaIntro").setValue("Introduction To Java");
                    provideAlertDialogInterface.removeReason("Introduction To Java",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Introduction To Java",revisionReference,getActivity());
                    syllabusReference.child("javaIntro").removeValue();
                }
            }
        });

        features.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("features").setValue("Java Features");
                    provideAlertDialogInterface.removeReason("Java Features",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Java Features",revisionReference,getActivity());
                    syllabusReference.child("features").removeValue();
                }
            }
        });

        differenceJavaC.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("differenceJavaC").setValue("Difference Between Java & C++");
                    provideAlertDialogInterface.removeReason("Difference Between Java & C++",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Difference Between Java & C++",revisionReference,getActivity());
                    syllabusReference.child("differenceJavaC").removeValue();
                }
            }
        });

        javaEnvironment.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("javaEnvironment").setValue("Java Environment");
                    provideAlertDialogInterface.removeReason("Java Environment",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Java Environment",revisionReference,getActivity());
                    syllabusReference.child("javaEnvironment").removeValue();
                }
            }
        });

        namingConvention.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("namingConvention").setValue("Naming Convention");
                    provideAlertDialogInterface.removeReason("Naming Convention",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Naming Convention",revisionReference,getActivity());
                    syllabusReference.child("namingConvention").removeValue();
                }
            }
        });

        classesObjects.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("classesObjects").setValue("Classes & Object");
                    provideAlertDialogInterface.removeReason("Classes & Object",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Classes & Object",revisionReference,getActivity());
                    syllabusReference.child("classesObjects").removeValue();
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

        controlStatement.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("controlStatement").setValue("Control Statement");
                    provideAlertDialogInterface.removeReason("Control Statement",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Control Statement",revisionReference,getActivity());
                    syllabusReference.child("controlStatement").removeValue();
                }
            }
        });

        loops.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("loops").setValue("Loops");
                    provideAlertDialogInterface.removeReason("Loops",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Loops",revisionReference,getActivity());
                    syllabusReference.child("loops").removeValue();
                }
            }
        });

        stringBuffer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("stringBuffer").setValue("String Buffer");
                    provideAlertDialogInterface.removeReason("String Buffer",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("String Buffer",revisionReference,getActivity());
                    syllabusReference.child("stringBuffer").removeValue();
                }
            }
        });

        stringBuilder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("stringBuilder").setValue("String Builder");
                    provideAlertDialogInterface.removeReason("String Builder",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("String Builder",revisionReference,getActivity());
                    syllabusReference.child("stringBuilder").removeValue();
                }
            }
        });

        string.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("string").setValue("String");
                    provideAlertDialogInterface.removeReason("String",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("String",revisionReference,getActivity());
                    syllabusReference.child("string").removeValue();
                }
            }
        });

        keywords.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("keywords").setValue("Keywords");
                    provideAlertDialogInterface.removeReason("Keywords",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Keywords",revisionReference,getActivity());
                    syllabusReference.child("keywords").removeValue();
                }
            }
        });

        packages.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("packages").setValue("Packages");
                    provideAlertDialogInterface.removeReason("Packages",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Packages",revisionReference,getActivity());
                    syllabusReference.child("packages").removeValue();
                }
            }
        });

        array.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("array").setValue("Array");
                    provideAlertDialogInterface.removeReason("Array",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Array",revisionReference,getActivity());
                    syllabusReference.child("array").removeValue();
                }
            }
        });

        inheritance.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("inheritance").setValue("Inheritance");
                    provideAlertDialogInterface.removeReason("Inheritance",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Inheritance",revisionReference,getActivity());
                    syllabusReference.child("inheritance").removeValue();
                }
            }
        });

        polymorphism.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("polymorphism").setValue("Polymorphism");
                    provideAlertDialogInterface.removeReason("Polymorphism",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Polymorphism",revisionReference,getActivity());
                    syllabusReference.child("polymorphism").removeValue();
                }
            }
        });

        abstraction.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("abstraction").setValue("Abstraction");
                    provideAlertDialogInterface.removeReason("Abstraction",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Abstraction",revisionReference,getActivity());
                    syllabusReference.child("abstraction").removeValue();
                }
            }
        });

        encapsulation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("encapsulation").setValue("Encapsulation");
                    provideAlertDialogInterface.removeReason("Encapsulation",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Encapsulation",revisionReference,getActivity());
                    syllabusReference.child("encapsulation").removeValue();
                }
            }
        });

        threads.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("threads").setValue("Thread");
                    provideAlertDialogInterface.removeReason("Thread",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Thread",revisionReference,getActivity());
                    syllabusReference.child("threads").removeValue();
                }
            }
        });

        multiThreads.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("multiThreads").setValue("Multi-Threading");
                    provideAlertDialogInterface.removeReason("Multi-Threading",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Multi-Threading",revisionReference,getActivity());
                    syllabusReference.child("multiThreads").removeValue();
                }
            }
        });

        deadLock.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("deadLock").setValue("Dead Lock");
                    provideAlertDialogInterface.removeReason("Dead Lock",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Dead Lock",revisionReference,getActivity());
                    syllabusReference.child("deadLock").removeValue();
                }
            }
        });

        synchronization.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("synchronization").setValue("Synchronized");
                    provideAlertDialogInterface.removeReason("Synchronized",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Synchronized",revisionReference,getActivity());
                    syllabusReference.child("synchronization").removeValue();
                }
            }
        });

        exceptionHandling.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("exceptionHandling").setValue("Exception Handling");
                    provideAlertDialogInterface.removeReason("Exception Handling",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Exception Handling",revisionReference,getActivity());
                    syllabusReference.child("exceptionHandling").removeValue();
                }
            }
        });

        fileHandling.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("fileHandling").setValue("File Handling");
                    provideAlertDialogInterface.removeReason("File Handling",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("File Handling",revisionReference,getActivity());
                    syllabusReference.child("fileHandling").removeValue();
                }
            }
        });

        checkedException.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("checkedException").setValue("Checked Exception");
                    provideAlertDialogInterface.removeReason("Checked Exception",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Checked Exception",revisionReference,getActivity());
                    syllabusReference.child("checkedException").removeValue();
                }
            }
        });

        uncheckedException.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("uncheckedException").setValue("Unchecked Exception");
                    provideAlertDialogInterface.removeReason("Unchecked Exception",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Unchecked Exception",revisionReference,getActivity());
                    syllabusReference.child("uncheckedException").removeValue();
                }
            }
        });

        ioOperation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("ioOperation").setValue("I/O Operations");
                    provideAlertDialogInterface.removeReason("I/O Operations",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("I/O Operations",revisionReference,getActivity());
                    syllabusReference.child("ioOperation").removeValue();
                }
            }
        });

        collection.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("collection").setValue("Collection");
                    provideAlertDialogInterface.removeReason("Collection",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Collection",revisionReference,getActivity());
                    syllabusReference.child("collection").removeValue();
                }
            }
        });

        wrapperClasses.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("wrapperClasses").setValue("Wrapper Classes");
                    provideAlertDialogInterface.removeReason("Wrapper Classes",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Wrapper Classes",revisionReference,getActivity());
                    syllabusReference.child("wrapperClasses").removeValue();
                }
            }
        });

        database.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("database").setValue("Database");
                    provideAlertDialogInterface.removeReason("Database",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Database",revisionReference,getActivity());
                    syllabusReference.child("database").removeValue();
                }
            }
        });

        awtSwing.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("awtSwing").setValue("AWT & Swing");
                    provideAlertDialogInterface.removeReason("AWT & Swing",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("AWT & Swing",revisionReference,getActivity());
                    syllabusReference.child("awtSwing").removeValue();
                }
            }
        });

        servlet.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("servlet").setValue("Servlet");
                    provideAlertDialogInterface.removeReason("Servlet",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Servlet",revisionReference,getActivity());
                    syllabusReference.child("servlet").removeValue();
                }
            }
        });


        jsp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("jsp").setValue("JSP");
                    provideAlertDialogInterface.removeReason("JSP",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("JSP",revisionReference,getActivity());
                    syllabusReference.child("jsp").removeValue();
                }
            }
        });

    }

    private void init(View rootView) {
        javaIntro=rootView.findViewById(R.id.javaIntro);
        features=rootView.findViewById(R.id.features);
        differenceJavaC=rootView.findViewById(R.id.differenceJavaC);
        javaEnvironment=rootView.findViewById(R.id.javaEnvironment);
        namingConvention=rootView.findViewById(R.id.namingConvention);
        classesObjects=rootView.findViewById(R.id.classesObjects);
        operators=rootView.findViewById(R.id.operators);
        controlStatement=rootView.findViewById(R.id.controlStatement);
        loops=rootView.findViewById(R.id.loops);
        stringBuffer=rootView.findViewById(R.id.stringBuffer);
        stringBuilder=rootView.findViewById(R.id.stringBuilder);
        string=rootView.findViewById(R.id.string);
        keywords=rootView.findViewById(R.id.keywords);
        packages=rootView.findViewById(R.id.packages);
        array=rootView.findViewById(R.id.array);
        inheritance=rootView.findViewById(R.id.inheritance);
        polymorphism=rootView.findViewById(R.id.polymorphism);
        abstraction=rootView.findViewById(R.id.abstraction);
        encapsulation=rootView.findViewById(R.id.encapsulation);
        threads=rootView.findViewById(R.id.threads);
        multiThreads=rootView.findViewById(R.id.multiThreads);
        deadLock=rootView.findViewById(R.id.deadLock);
        synchronization=rootView.findViewById(R.id.synchronization);
        exceptionHandling=rootView.findViewById(R.id.exceptionHandling);
        fileHandling=rootView.findViewById(R.id.fileHandling);
        checkedException=rootView.findViewById(R.id.checkedException);
        uncheckedException=rootView.findViewById(R.id.uncheckedException);
        ioOperation=rootView.findViewById(R.id.ioOperation);
        collection=rootView.findViewById(R.id.collection);
        wrapperClasses=rootView.findViewById(R.id.wrapperClasses);
        database=rootView.findViewById(R.id.database);
        awtSwing=rootView.findViewById(R.id.awtSwing);
        servlet=rootView.findViewById(R.id.servlet);
        jsp=rootView.findViewById(R.id.jsp);
        provideAlertDialogInterface=new ProvideAlertDialogInterface();
    }
}