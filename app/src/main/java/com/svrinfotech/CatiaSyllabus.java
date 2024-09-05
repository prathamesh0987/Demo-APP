package com.svrinfotech;


import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

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
public class CatiaSyllabus extends Fragment {
    private CheckBox catiaIntro,catiaDesignConcept,catiaParametricDesign,constrainedSketches,
            animationConstraint,catiaBooleanOperation,machineComponent,threeDText,designTools,
            bottomUpAssembly,topDownAssembly,assemblyWithFullyConstraints,scenes,
            applyMaterial,massCalculation,catiaSave,
            sheetMetalParameters,catiaHem,flange,catiaCorner,bend,catiaFoldUnfold,stamping,bridge,hooper,rolledWall,
            extractSurface,advanceSurface,projection,combine,sweep,biWTemplate,fill,
            catiaMechanismSimulation,kinematicsJoin,catiaProductAnimation,dmuSpaceAnalysis,
            catiaProjectionMethod,frameTitleBlock,sheetBackground,catiaDetailing,catiaBOM,catiaGDT,catiaTolerence,catiaLimitsFits;

    private DatabaseReference syllabusReference,revisionReference;
    private String status,studentName;
    private ProvideAlertDialogInterface provideAlertDialogInterface;
    public CatiaSyllabus() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_catia_syllabus, container, false);
        init(rootView);
        //String sharedPreferenceName="SHARED_PREFERENCE";
        //SharedPreferences sharedPreferences=getActivity().getApplicationContext().getSharedPreferences(sharedPreferenceName,MODE_PRIVATE);
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        String user=firebaseAuth.getCurrentUser().getEmail();

        List<EndUser> loggedUserList=EndUser.find(EndUser.class,"email=?",user);

        EndUser loggedUser=loggedUserList.get(0);

        status=loggedUser.getStatus();
                //getArguments().getString("status");
        String name=loggedUser.getName();
            //sharedPreferences.getString("employeeName",null);
        switch (status) {
            case "student":
                syllabusReference= FirebaseDatabase.getInstance()
                    .getReference()
                    .child("syllabus")
                    .child("CATIA")
                    .child(name);
                break;
            case "employee":if(!name.equals(null)) {
                Admission admission=(Admission) getArguments().getSerializable("admission");
                studentName=admission.getName();
                syllabusReference= FirebaseDatabase.getInstance()
                        .getReference()
                        .child("syllabus")
                        .child("CATIA")
                        .child(name)
                        .child(studentName);
            }
                break;
        }
        syllabusReference.keepSynced(true);
        revisionReference=FirebaseDatabase.getInstance().getReference()
                .child("revision")
                .child("CATIA")
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
                    DataSnapshot keyValue = (DataSnapshot) iterator.next();
                    String name = keyValue.getKey();
                    switch (name) {
                        case "catiaIntro":catiaIntro.setChecked(true);
                            break;
                        case "catiaDesignConcept":catiaDesignConcept.setChecked(true);
                            break;
                        case "catiaParametricDesign":catiaParametricDesign.setChecked(true);
                            break;
                        case "constrainedSketches":constrainedSketches.setChecked(true);
                            break;
                        case "animationConstraint":animationConstraint.setChecked(true);
                            break;
                        case "catiaBooleanOperation":catiaBooleanOperation.setChecked(true);
                            break;
                        case "machineComponent":machineComponent.setChecked(true);
                            break;
                        case "threeDText":threeDText.setChecked(true);
                            break;
                        case "designTools":designTools.setChecked(true);
                            break;
                        case "bottomUpAssembly":bottomUpAssembly.setChecked(true);
                            break;
                        case "topDownAssembly":topDownAssembly.setChecked(true);
                            break;
                        case "assemblyWithFullyConstraints":assemblyWithFullyConstraints.setChecked(true);
                            break;
                        case "scenes":scenes.setChecked(true);
                            break;
                        case "applyMaterial":applyMaterial.setChecked(true);
                            break;
                        case "massCalculation":massCalculation.setChecked(true);
                            break;
                        case "catiaSave":catiaSave.setChecked(true);
                            break;
                        case "sheetMetalParameters":sheetMetalParameters.setChecked(true);
                            break;
                        case "catiaHem":catiaHem.setChecked(true);
                            break;
                        case "flange":flange.setChecked(true);
                            break;
                        case "catiaCorner":catiaCorner.setChecked(true);
                            break;
                        case "bend":bend.setChecked(true);
                            break;
                        case "catiaFoldUnfold":catiaFoldUnfold.setChecked(true);
                            break;
                        case "stamping":stamping.setChecked(true);
                            break;
                        case "bridge":bridge.setChecked(true);
                            break;
                        case "hooper":hooper.setChecked(true);
                            break;
                        case "rolledWall":rolledWall.setChecked(true);
                            break;
                        case "extractSurface":extractSurface.setChecked(true);
                            break;
                        case "advanceSurface":advanceSurface.setChecked(true);
                            break;
                        case "projection":projection.setChecked(true);
                            break;
                        case "combine":combine.setChecked(true);
                            break;
                        case "sweep":sweep.setChecked(true);
                            break;
                        case "biWTemplate":biWTemplate.setChecked(true);
                            break;
                        case "fill":fill.setChecked(true);
                            break;
                        case "catiaMechanismSimulation":catiaMechanismSimulation.setChecked(true);
                            break;
                        case "kinematicsJoin":kinematicsJoin.setChecked(true);
                            break;
                        case "catiaProductAnimation":catiaProductAnimation.setChecked(true);
                            break;
                        case "dmuSpaceAnalysis":dmuSpaceAnalysis.setChecked(true);
                            break;
                        case "catiaProjectionMethod":catiaProjectionMethod.setChecked(true);
                            break;
                        case "frameTitleBlock":frameTitleBlock.setChecked(true);
                            break;
                        case "sheetBackground":sheetBackground.setChecked(true);
                            break;
                        case "catiaDetailing":catiaDetailing.setChecked(true);
                            break;
                        case "catiaBOM":catiaBOM.setChecked(true);
                            break;
                        case "catiaGDT":catiaGDT.setChecked(true);
                            break;
                        case "catiaTolerence":catiaTolerence.setChecked(true);
                            break;
                        case "catiaLimitsFits":catiaLimitsFits.setChecked(true);
                            break;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        catiaIntro.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("catiaIntro").setValue("Introduction To CAD/CAM/CAE");
                    provideAlertDialogInterface.removeReason("Introduction To CAD/CAM/CAE",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Introduction To CAD/CAM/CAE",revisionReference,getActivity());
                    syllabusReference.child("catiaIntro").removeValue();
                }
            }
        });

        catiaDesignConcept.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("catiaDesignConcept").setValue("Design Concept & Procedure");
                    provideAlertDialogInterface.removeReason("Design Concept & Procedure",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Design Concept & Procedure",revisionReference,getActivity());
                    syllabusReference.child("catiaDesignConcept").removeValue();
                }
            }
        });

        catiaParametricDesign.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("catiaParametricDesign").setValue("Concept Of Parameteric Design");
                    provideAlertDialogInterface.removeReason("Concept Of Parameteric Design",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Concept Of Parameteric Design",revisionReference,getActivity());
                    syllabusReference.child("catiaParametricDesign").removeValue();

                }
            }
        });

        constrainedSketches.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("constrainedSketches").setValue("Fully Constrained Sketches");
                    provideAlertDialogInterface.removeReason("Fully Constrained Sketches",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Fully Constrained Sketches",revisionReference,getActivity());
                    syllabusReference.child("constrainedSketches").removeValue();
                }
            }
        });

        animationConstraint.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("animationConstraint").setValue("Animation Constraint");
                    provideAlertDialogInterface.removeReason("Animation Constraint",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Animation Constraint",revisionReference,getActivity());
                    syllabusReference.child("animationConstraint").removeValue();
                }
            }
        });

        catiaBooleanOperation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("catiaBooleanOperation").setValue("Boolean Operation");
                    provideAlertDialogInterface.removeReason("Boolean Operation",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Boolean Operation",revisionReference,getActivity());
                    syllabusReference.child("catiaBooleanOperation").removeValue();
                }
            }
        });

        machineComponent.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("machineComponent").setValue("Machine Component & Products");
                    provideAlertDialogInterface.removeReason("Machine Component & Products",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Machine Component & Products",revisionReference,getActivity());
                    syllabusReference.child("machineComponent").removeValue();
                }
            }
        });

        threeDText.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("threeDText").setValue("3D Text");
                    provideAlertDialogInterface.removeReason("3D Text",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("3D Text",revisionReference,getActivity());
                    syllabusReference.child("threeDText").removeValue();
                }
            }
        });

        designTools.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("designTools").setValue("Design Tools");
                    provideAlertDialogInterface.removeReason("Design Tools",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Design Tools",revisionReference,getActivity());
                    syllabusReference.child("designTools").removeValue();
                }
            }
        });

        bottomUpAssembly.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("bottomUpAssembly").setValue("Bottom Up Assembly");
                    provideAlertDialogInterface.removeReason("Bottom Up Assembly",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Bottom Up Assembly",revisionReference,getActivity());
                    syllabusReference.child("bottomUpAssembly").removeValue();
                }
            }
        });

        topDownAssembly.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("topDownAssembly").setValue("Top Down Assembly");
                    provideAlertDialogInterface.removeReason("Top Down Assembly",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Top Down Assembly",revisionReference,getActivity());
                    syllabusReference.child("topDownAssembly").removeValue();
                }
            }
        });

        assemblyWithFullyConstraints.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("assemblyWithFullyConstraints").setValue("Assembly With Fully Constraint");
                    provideAlertDialogInterface.removeReason("Assembly With Fully Constraint",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Assembly With Fully Constraint",revisionReference,getActivity());
                    syllabusReference.child("assemblyWithFullyConstraints").removeValue();
                }
            }
        });

        scenes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("scenes").setValue("Scenes");
                    provideAlertDialogInterface.removeReason("Scenes",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Scenes",revisionReference,getActivity());
                    syllabusReference.child("scenes").removeValue();
                }
            }
        });

        applyMaterial.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("applyMaterial").setValue("Apply Material");
                    provideAlertDialogInterface.removeReason("Apply Material",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Apply Material",revisionReference,getActivity());
                    syllabusReference.child("applyMaterial").removeValue();
                }
            }
        });

        massCalculation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("massCalculation").setValue("Mass Calculation");
                    provideAlertDialogInterface.removeReason("Mass Calculation",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Mass Calculation",revisionReference,getActivity());
                    syllabusReference.child("massCalculation").removeValue();
                }
            }
        });

        catiaSave.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("catiaSave").setValue("Save As IGES & Step");
                    provideAlertDialogInterface.removeReason("Save As IGES & Step",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Save As IGES & Step",revisionReference,getActivity());
                    syllabusReference.child("catiaSave").removeValue();

                }
            }
        });

        sheetMetalParameters.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("sheetMetalParameters").setValue("Sheet Metal Parameters");
                    provideAlertDialogInterface.removeReason("Sheet Metal Parameters",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Sheet Metal Parameters",revisionReference,getActivity());
                    syllabusReference.child("sheetMetalParameters").removeValue();
                }
            }
        });

        catiaHem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("catiaHem").setValue("Hem");
                    provideAlertDialogInterface.removeReason("Hem",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Hem",revisionReference,getActivity());
                    syllabusReference.child("catiaHem").removeValue();
                }
            }
        });

        flange.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("flange").setValue("Flange");
                    provideAlertDialogInterface.removeReason("Flange",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Flange",revisionReference,getActivity());
                    syllabusReference.child("flange").removeValue();
                }
            }
        });

        catiaCorner.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("catiaCorner").setValue("Corner");
                    provideAlertDialogInterface.removeReason("Corner",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Corner",revisionReference,getActivity());
                    syllabusReference.child("catiaCorner").removeValue();
                }
            }
        });

        bend.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("bend").setValue("Bend");
                    provideAlertDialogInterface.removeReason("Bend",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Bend",revisionReference,getActivity());
                    syllabusReference.child("bend").removeValue();
                }
            }
        });

        catiaFoldUnfold.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("catiaFoldUnfold").setValue("Fold-Unfold");
                    provideAlertDialogInterface.removeReason("Fold-Unfold",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Fold-Unfold",revisionReference,getActivity());
                    syllabusReference.child("catiaFoldUnfold").removeValue();
                }
            }
        });

        stamping.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("stamping").setValue("Stamping");
                } else {
                    syllabusReference.child("stamping").removeValue();
                }
            }
        });

        bridge.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("bridge").setValue("Bridge");
                    provideAlertDialogInterface.removeReason("Bridge",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Bridge",revisionReference,getActivity());
                    syllabusReference.child("bridge").removeValue();
                }
            }
        });

        hooper.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("hooper").setValue("Hooper");
                    provideAlertDialogInterface.removeReason("Hooper",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Hooper",revisionReference,getActivity());
                    syllabusReference.child("hooper").removeValue();
                }
            }
        });

        rolledWall.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("rolledWall").setValue("Rolled Wall");
                    provideAlertDialogInterface.removeReason("Rolled Wall",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Rolled Wall",revisionReference,getActivity());
                    syllabusReference.child("rolledWall").removeValue();
                }
            }
        });

        extractSurface.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("extractSurface").setValue("Extract Surface");
                    provideAlertDialogInterface.removeReason("Extract Surface",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Extract Surface",revisionReference,getActivity());
                    syllabusReference.child("extractSurface").removeValue();
                }
            }
        });

        advanceSurface.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("advanceSurface").setValue("Advance Surface");
                    provideAlertDialogInterface.removeReason("Advance Surface",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Advance Surface",revisionReference,getActivity());
                    syllabusReference.child("advanceSurface").removeValue();
                }
            }
        });

        projection.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("projection").setValue("Projection");
                    provideAlertDialogInterface.removeReason("Projection",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Projection",revisionReference,getActivity());
                    syllabusReference.child("projection").removeValue();
                }
            }
        });

        combine.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("combine").setValue("Combine");
                    provideAlertDialogInterface.removeReason("Combine",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Combine",revisionReference,getActivity());
                    syllabusReference.child("combine").removeValue();
                }
            }
        });

        sweep.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("sweep").setValue("Sweep");
                    provideAlertDialogInterface.removeReason("Sweep",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Sweep",revisionReference,getActivity());
                    syllabusReference.child("sweep").removeValue();
                }
            }
        });

        biWTemplate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("biWTemplate").setValue("BiW Template");
                    provideAlertDialogInterface.removeReason("BiW Template",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("BiW Template",revisionReference,getActivity());
                    syllabusReference.child("biWTemplate").removeValue();
                }
            }
        });

        fill.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("fill").setValue("Fill");
                    provideAlertDialogInterface.removeReason("Fill",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Fill",revisionReference,getActivity());
                    syllabusReference.child("fill").removeValue();
                }
            }
        });

        catiaMechanismSimulation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("catiaMechanismSimulation").setValue("Mechanism Simulation");
                    provideAlertDialogInterface.removeReason("Mechanism Simulation",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Mechanism Simulation",revisionReference,getActivity());
                    syllabusReference.child("catiaMechanismSimulation").removeValue();
                }
            }
        });

        kinematicsJoin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("kinematicsJoin").setValue("Kinematics Join");
                    provideAlertDialogInterface.removeReason("Kinematics Join",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Kinematics Join",revisionReference,getActivity());
                    syllabusReference.child("kinematicsJoin").removeValue();
                }
            }
        });

        catiaProductAnimation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("catiaProductAnimation").setValue("Product Animation");
                    provideAlertDialogInterface.removeReason("Product Animation",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Product Animation",revisionReference,getActivity());
                    syllabusReference.child("catiaProductAnimation").removeValue();
                }
            }
        });

        dmuSpaceAnalysis.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("dmuSpaceAnalysis").setValue("DMU Space Analysis");
                    provideAlertDialogInterface.removeReason("DMU Space Analysis",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("DMU Space Analysis",revisionReference,getActivity());
                    syllabusReference.child("dmuSpaceAnalysis").removeValue();
                }
            }
        });

        catiaProjectionMethod.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("catiaProjectionMethod").setValue("Projection Method");
                    provideAlertDialogInterface.removeReason("Projection Method",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Projection Method",revisionReference,getActivity());
                    syllabusReference.child("catiaProjectionMethod").removeValue();
                }
            }
        });

        frameTitleBlock.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("frameTitleBlock").setValue("Frame & Title Block");
                    provideAlertDialogInterface.removeReason("Frame & Title Block",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Frame & Title Block",revisionReference,getActivity());
                    syllabusReference.child("frameTitleBlock").removeValue();
                }
            }
        });

        sheetBackground.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("sheetBackground").setValue("Sheet Background");
                    provideAlertDialogInterface.removeReason("Sheet Background",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Sheet Background",revisionReference,getActivity());
                    syllabusReference.child("sheetBackground").removeValue();
                }
            }
        });

        catiaDetailing.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("catiaDetailing").setValue("Detailing Drawing");
                    provideAlertDialogInterface.removeReason("Detailing Drawing",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Detailing Drawing",revisionReference,getActivity());
                    syllabusReference.child("catiaDetailing").removeValue();
                }
            }
        });

        catiaBOM.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("catiaBOM").setValue("Bills Of Material");
                    provideAlertDialogInterface.removeReason("Bills Of Material",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Bills Of Material",revisionReference,getActivity());
                    syllabusReference.child("catiaBOM").removeValue();
                }
            }
        });

        catiaGDT.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("catiaGDT").setValue("Geometric Dimensions & Tolerance");
                    provideAlertDialogInterface.removeReason("Geometric Dimensions & Tolerance",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Geometric Dimensions & Tolerance",revisionReference,getActivity());
                    syllabusReference.child("catiaGDT").removeValue();
                }
            }
        });

        catiaTolerence.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("catiaTolerence").setValue("Tolerance");
                    provideAlertDialogInterface.removeReason("Tolerance",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Tolerance",revisionReference,getActivity());
                    syllabusReference.child("catiaTolerence").removeValue();
                }
            }
        });

        catiaLimitsFits.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    syllabusReference.child("catiaLimitsFits").setValue("Limits & Fits");
                    provideAlertDialogInterface.removeReason("Limits & Fits",revisionReference);
                } else {
                    provideAlertDialogInterface.setReason("Limits & Fits",revisionReference,getActivity());
                    syllabusReference.child("catiaLimitsFits").removeValue();
                }
            }
        });
    }


    private void init(View rootView) {
        catiaIntro=rootView.findViewById(R.id.catiaIntro);
        catiaDesignConcept=rootView.findViewById(R.id.catiaDesignConcept);
        catiaParametricDesign=rootView.findViewById(R.id.catiaParametricDesign);
        constrainedSketches=rootView.findViewById(R.id.constrainedSketches);
        animationConstraint=rootView.findViewById(R.id.animationConstraint);
        catiaBooleanOperation=rootView.findViewById(R.id.catiaBooleanOperation);
        machineComponent=rootView.findViewById(R.id.machineComponent);
        threeDText=rootView.findViewById(R.id.threeDText);
        designTools=rootView.findViewById(R.id.designTools);
        bottomUpAssembly=rootView.findViewById(R.id.bottomUpAssembly);
        topDownAssembly=rootView.findViewById(R.id.topDownAssembly);
        assemblyWithFullyConstraints=rootView.findViewById(R.id.assemblyWithFullyConstraints);
        scenes=rootView.findViewById(R.id.scenes);
        applyMaterial=rootView.findViewById(R.id.applyMaterial);
        massCalculation=rootView.findViewById(R.id.massCalculation);
        catiaSave=rootView.findViewById(R.id.catiaSave);
        sheetMetalParameters=rootView.findViewById(R.id.sheetMetalParameters);
        catiaHem=rootView.findViewById(R.id.catiaHem);
        flange=rootView.findViewById(R.id.flange);
        catiaCorner=rootView.findViewById(R.id.catiaCorner);
        bend=rootView.findViewById(R.id.bend);
        catiaFoldUnfold=rootView.findViewById(R.id.catiaFoldUnfold);
        stamping=rootView.findViewById(R.id.stamping);
        bridge=rootView.findViewById(R.id.bridge);
        hooper=rootView.findViewById(R.id.hooper);
        rolledWall=rootView.findViewById(R.id.rolledWall);
        extractSurface=rootView.findViewById(R.id.extractSurface);
        advanceSurface=rootView.findViewById(R.id.advanceSurface);
        projection=rootView.findViewById(R.id.projection);
        combine=rootView.findViewById(R.id.combine);
        sweep=rootView.findViewById(R.id.sweep);
        biWTemplate=rootView.findViewById(R.id.biWTemplate);
        fill=rootView.findViewById(R.id.fill);
        catiaMechanismSimulation=rootView.findViewById(R.id.catiaMechanismSimulation);
        kinematicsJoin=rootView.findViewById(R.id.kinematicsJoin);
        catiaProductAnimation=rootView.findViewById(R.id.catiaProductAnimation);
        dmuSpaceAnalysis=rootView.findViewById(R.id.dmuSpaceAnalysis);
        catiaProjectionMethod=rootView.findViewById(R.id.catiaProjectionMethod);
        frameTitleBlock=rootView.findViewById(R.id.frameTitleBlock);
        sheetBackground=rootView.findViewById(R.id.sheetBackground);
        catiaDetailing=rootView.findViewById(R.id.catiaDetailing);
        catiaBOM=rootView.findViewById(R.id.catiaBOM);
        catiaGDT=rootView.findViewById(R.id.catiaGDT);
        catiaTolerence=rootView.findViewById(R.id.catiaTolerence);
        catiaLimitsFits=rootView.findViewById(R.id.catiaLimitsFits);
        provideAlertDialogInterface=new ProvideAlertDialogInterface();
    }
}