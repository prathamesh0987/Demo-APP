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
public class AndroidSyllabus extends Fragment {
    private CheckBox androidHistory,androidVersions,androidArchitecture,manifestFile,rJava,androidStudio,
            lifeCycle,uiWidgets,implicitIntent,explicitIntent,coreBlocks,
            jsonParsing,recyclerView,sqLite,handler,asyncTask,animation,fragments,
            activity,intent,services,broadcastReceiver,contentProvider;

    private DatabaseReference syllabusReference,revisionReference;
    private String studentName;

    ProvideAlertDialogInterface provideAlertDialogInterface;
    public AndroidSyllabus() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_android_syllabus, container, false);
        init(rootView);
        String sharedPreferenceName="SHARED_PREFERENCE";
        //SharedPreferences sharedPreferences=getActivity().getApplicationContext().getSharedPreferences(sharedPreferenceName,MODE_PRIVATE);
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        String user=firebaseAuth.getCurrentUser().getEmail();
        List<EndUser> loggedUserList=EndUser.find(EndUser.class,"email=?",user);

        EndUser loggedUser=loggedUserList.get(0);
        String status=loggedUser.getStatus();
                //getArguments().getString("status");
        String name=loggedUser.getName();
        switch (status) {
            case "student":
                syllabusReference= FirebaseDatabase.getInstance()
                    .getReference()
                    .child("syllabus")
                    .child("ANDROID")
                    .child(name);
                break;
            case "employee":
                if(!name.equals(null)) {
                Admission admission=(Admission) getArguments().getSerializable("admission");
                studentName=admission.getName();
                syllabusReference= FirebaseDatabase.getInstance()
                        .getReference()
                        .child("syllabus")
                        .child("ANDROID")
                        .child(name)
                        .child(studentName);
            }
                break;
        }
        syllabusReference.keepSynced(true);
        revisionReference=FirebaseDatabase.getInstance().getReference()
                .child("revision")
                .child("ANDROID")
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
                        case "androidHistory":androidHistory.setChecked(true);
                            break;
                        case "androidVersions":androidVersions.setChecked(true);
                            break;
                        case "androidArchitecture":androidArchitecture.setChecked(true);
                            break;
                        case "manifestFile":manifestFile.setChecked(true);
                            break;
                        case "rJava":rJava.setChecked(true);
                            break;
                        case "androidStudio":androidStudio.setChecked(true);
                            break;
                        case "lifeCycle":lifeCycle.setChecked(true);
                            break;
                        case "uiWidgets":uiWidgets.setChecked(true);
                            break;
                        case "implicitIntent":implicitIntent.setChecked(true);
                            break;
                        case "explicitIntent":explicitIntent.setChecked(true);
                            break;
                        case "coreBlocks":coreBlocks.setChecked(true);
                            break;
                        case "jsonParsing":jsonParsing.setChecked(true);
                            break;
                        case "recyclerView":recyclerView.setChecked(true);
                            break;
                        case "sqLite":sqLite.setChecked(true);
                            break;
                        case "handler":handler.setChecked(true);
                            break;
                        case "asyncTask":asyncTask.setChecked(true);
                            break;
                        case "animation":animation.setChecked(true);
                            break;
                        case "fragments":fragments.setChecked(true);
                            break;
                        case "activity":activity.setChecked(true);
                            break;
                        case "intent":intent.setChecked(true);
                            break;
                        case "services":services.setChecked(true);
                            break;
                        case "broadcastReceiver":broadcastReceiver.setChecked(true);
                            break;
                        case "contentProvider":contentProvider.setChecked(true);
                            break;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        androidHistory.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("androidHistory").setValue("Android History");
                    provideAlertDialogInterface.removeReason("Android History",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Android History",revisionReference,getActivity());
                    syllabusReference.child("androidHistory").removeValue();
                }
            }
        });

        androidVersions.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("androidVersions").setValue("Android Versions");
                    provideAlertDialogInterface.removeReason("Android Versions",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Android Versions",revisionReference,getActivity());
                    syllabusReference.child("androidVersions").removeValue();
                }
            }
        });

        androidArchitecture.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("androidArchitecture").setValue("Android Architecture");
                    provideAlertDialogInterface.removeReason("Android Architecture",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Android Architecture",revisionReference,getActivity());
                    syllabusReference.child("androidArchitecture").removeValue();
                }
            }
        });

        manifestFile.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("manifestFile").setValue("Manifest File");
                    provideAlertDialogInterface.removeReason("Manifest File",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Manifest File",revisionReference,getActivity());
                    syllabusReference.child("manifestFile").removeValue();
                }
            }
        });

        rJava.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("rJava").setValue("R.Java");
                    provideAlertDialogInterface.removeReason("R Java",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("R Java",revisionReference,getActivity());
                    syllabusReference.child("rJava").removeValue();
                }
            }
        });

        androidStudio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("androidStudio").setValue("Android Studio");
                    provideAlertDialogInterface.removeReason("Android Studio",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Android Studio",revisionReference,getActivity());
                    syllabusReference.child("androidStudio").removeValue();
                }
            }
        });

        lifeCycle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("lifeCycle").setValue("Activity Life Cycle");
                    provideAlertDialogInterface.removeReason("Activity Life Cycle",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Activity Life Cycle",revisionReference,getActivity());
                    syllabusReference.child("lifeCycle").removeValue();
                }
            }
        });

        uiWidgets.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("uiWidgets").setValue("UI Widgets");
                    provideAlertDialogInterface.removeReason("UI Widgets",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("UI Widgets",revisionReference,getActivity());
                    syllabusReference.child("uiWidgets").removeValue();
                }
            }
        });

        implicitIntent.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("implicitIntent").setValue("Implicit Intent");
                    provideAlertDialogInterface.removeReason("Implicit Intent",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Implicit Intent",revisionReference,getActivity());
                    syllabusReference.child("implicitIntent").removeValue();
                }
            }
        });

        explicitIntent.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("explicitIntent").setValue("Explicit Intent");
                    provideAlertDialogInterface.removeReason("Explicit Intent",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Explicit Intent",revisionReference,getActivity());
                    syllabusReference.child("explicitIntent").removeValue();
                }
            }
        });

        coreBlocks.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("coreBlocks").setValue("Core Building Blocks Of Android");
                    provideAlertDialogInterface.removeReason("Core Building Blocks Of Android",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Core Building Blocks Of Android",revisionReference,getActivity());
                    syllabusReference.child("coreBlocks").removeValue();
                }
            }
        });

        jsonParsing.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("jsonParsing").setValue("JSON Parsing");
                    provideAlertDialogInterface.removeReason("JSON Parsing",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("JSON Parsing",revisionReference,getActivity());
                    syllabusReference.child("jsonParsing").removeValue();
                }
            }
        });


        recyclerView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("recyclerView").setValue("RecyclerView");
                    provideAlertDialogInterface.removeReason("RecyclerView",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("RecyclerView",revisionReference,getActivity());
                    syllabusReference.child("recyclerView").removeValue();
                }
            }
        });

        sqLite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("sqLite").setValue("SQLite");
                    provideAlertDialogInterface.removeReason("SQLite",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("SQLite",revisionReference,getActivity());
                    syllabusReference.child("sqLite").removeValue();
                }
            }
        });

        handler.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("handler").setValue("Handler");
                    provideAlertDialogInterface.removeReason("Handler",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Handler",revisionReference,getActivity());
                    syllabusReference.child("handler").removeValue();
                }
            }
        });

        asyncTask.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("asyncTask").setValue("AsyncTask");
                    provideAlertDialogInterface.removeReason("AsyncTask",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("AsyncTask",revisionReference,getActivity());
                    syllabusReference.child("asyncTask").removeValue();
                }
            }
        });

        animation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("animation").setValue("Animation");
                    provideAlertDialogInterface.removeReason("Animation",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Animation",revisionReference,getActivity());
                    syllabusReference.child("animation").removeValue();
                }
            }
        });

        fragments.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("fragments").setValue("Fragments");
                    provideAlertDialogInterface.removeReason("Fragments",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Fragments",revisionReference,getActivity());
                    syllabusReference.child("fragments").removeValue();
                }
            }
        });

        activity.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("activity").setValue("Activity");
                    provideAlertDialogInterface.removeReason("Activity",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Activity",revisionReference,getActivity());
                    syllabusReference.child("activity").removeValue();
                }
            }
        });

        intent.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("intent").setValue("Intent");
                    provideAlertDialogInterface.removeReason("Intent",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Intent",revisionReference,getActivity());
                    syllabusReference.child("intent").removeValue();
                }
            }
        });

        services.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("services").setValue("Services");
                    provideAlertDialogInterface.removeReason("Services",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Services",revisionReference,getActivity());
                    syllabusReference.child("services").removeValue();
                }
            }
        });

        broadcastReceiver.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("broadcastReceiver").setValue("Broadcast Receiver");
                    provideAlertDialogInterface.removeReason("Broadcast Receiver",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Broadcast Receiver",revisionReference,getActivity());
                    syllabusReference.child("broadcastReceiver").removeValue();
                }
            }
        });


        contentProvider.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("contentProvider").setValue("Content Provider");
                    provideAlertDialogInterface.removeReason("Content Provider",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Content Provider",revisionReference,getActivity());
                    syllabusReference.child("contentProvider").removeValue();
                }
            }
        });

    }

    private void init(View rootView) {
        androidHistory=rootView.findViewById(R.id.androidHistory);
        androidVersions=rootView.findViewById(R.id.androidVersions);
        androidArchitecture=rootView.findViewById(R.id.androidArchitecture);
        manifestFile=rootView.findViewById(R.id.manifestFile);
        rJava=rootView.findViewById(R.id.rJava);
        androidStudio=rootView.findViewById(R.id.androidStudio);
        lifeCycle=rootView.findViewById(R.id.lifeCycle);
        uiWidgets=rootView.findViewById(R.id.uiWidgets);
        implicitIntent=rootView.findViewById(R.id.implicitIntent);
        explicitIntent=rootView.findViewById(R.id.explicitIntent);
        coreBlocks=rootView.findViewById(R.id.coreBlocks);
        jsonParsing=rootView.findViewById(R.id.jsonParsing);
        recyclerView=rootView.findViewById(R.id.recyclerView);
        sqLite=rootView.findViewById(R.id.sqLite);
        handler=rootView.findViewById(R.id.handler);
        asyncTask=rootView.findViewById(R.id.asyncTask);
        animation=rootView.findViewById(R.id.animation);
        fragments=rootView.findViewById(R.id.fragments);
        activity=rootView.findViewById(R.id.activity);
        intent=rootView.findViewById(R.id.intent);
        services=rootView.findViewById(R.id.services);
        broadcastReceiver=rootView.findViewById(R.id.broadcastReceiver);
        contentProvider=rootView.findViewById(R.id.contentProvider);
        provideAlertDialogInterface=new ProvideAlertDialogInterface();
    }
}
